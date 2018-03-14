package com.hnran.perfmanagesys.fragment.visit;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.CallRecordAdapter;
import com.hnran.perfmanagesys.adapter.ContactsPhoneNumberAdapter;
import com.hnran.perfmanagesys.adapter.VisitFamilyNumbersMenuAadapter;
import com.hnran.perfmanagesys.service.CallPhoneService;
import com.hnran.perfmanagesys.utils.DensityUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.MultipartRequest;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.visit.VisitContactsActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.readboy.ssm.po.ContactsPhone;
import com.readboy.ssm.po.VisitContactLog;
import com.readboy.ssm.po.VisitFamilyInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseArray;


/**
 * Created by Wyd on 2018/1/2.
 */

public class ContactInformationFragment extends BaseFragment implements ContactsPhoneNumberAdapter.CallBack, CallRecordAdapter.CallBackRecord ,VisitJudgeAndSaveCallBack{



    private static String path = Environment.getExternalStorageDirectory() + "/perfManageSys/CallRecording/";
    private static final String TAG = "ContactInfoFragment";
    private static final int READ_LOCALCONTACTS_CODE = 1;
    private EditText mTvContactNumber;
    private ImageView mLvPhoneContactPeople;
    private boolean playOver = true;

    private ListPopupWindow listPopupWindow = null;
    private VisitFamilyNumbersMenuAadapter adapter = null;
    private LinearLayout mLlFamilyNumbers;
    private TextView mTvFamilyNumbers;
    private ImageView mIvFamilyNumbers;
    private ListView mLvFamilyNumbersPhone;
    private ContactsPhoneNumberAdapter mContactsPhoneNumberAdapter;
    private ListView mLvCallRecord;
    private static CallRecordAdapter mCallRecordAdapter;
    private static String mPhone;
    private MediaPlayer mMediaPlayer;
    private static AlertDialog mAlertDialogRecord = null;
    private static AlertDialog mAlertDialogSave = null;
    private static FragmentActivity activity;

    private static Intent mCallRecordIntent;
    private static List<VisitContactLog> mVisitContactLogs =  new ArrayList<VisitContactLog>();;
    private static String mName;
    private static File mFile;
    private HttpUtils mHttpUtils ;
    private String mWebFilesPath;
    private ImageView mCallRecord;

    //存放家庭成员
    private ArrayList<String> mListFamilyNumbers = new ArrayList<>();
    //msg码
    private final static int GETPHONERECORD = 1;
    private final static int OPERATIONFINISHED = 2;
    private static final int SAVEPHONEFROMLISTCONTACTS = 3;
    private static final int GETCONTACTSCODE = 4;
    private final static int ERROR = 5;

    //存放联系人列表
    private List<VisitFamilyInfo> visitFamilyInfos;

    // 为了便于其他类使用handler所以定义成静态的
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /**
                     * 关闭服务方法
                     */
                    stopCallRecordService();
                    /**
                     * 增加条目方法
                     */
                    addCallRecord();
                    break;
            }
        }
    };



    /**
     * 处理本界面的消息
     */
   private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETPHONERECORD:
                    /**
                     * 显示通话记录列表
                     */
                    showListContactLog();

                    /**
                     * 从网络加载通话录音文件
                     */
                    getPhoneRecoreFiles();
                    break;
                case OPERATIONFINISHED:
                    String issuccess = (String)msg.obj;
                    if(issuccess.equals("success")){
                        ToastUtil.showToast(getActivity(), CommonContent.OPERATION_SUCCESS);
                        loadData();
                    }
                    else
                        ToastUtil.showToast(getActivity(), CommonContent.OPERATION_FAILED);
                    break;
                case SAVEPHONEFROMLISTCONTACTS:
                    /**
                     * 保存从通讯录获取的联系人信息
                     */
                    Intent data = (Intent) msg.obj;
                    String contactName = data.getStringExtra("contactName");
                    String contactNumber = data.getStringExtra("contactNumber");
                    saveContcatPhone(contactName,contactNumber);
                    break;
                case GETCONTACTSCODE:
                    /**
                     * 显示联系人列表
                     */
                    showListContacts();
                case ERROR:
                    break;
            }
        }
    };

    /**
     * 显示联系人列表
     */
    private void showListContacts() {
        mContactsPhoneNumberAdapter = new ContactsPhoneNumberAdapter(getActivity(), visitFamilyInfos, this);
        mLvFamilyNumbersPhone.setAdapter(mContactsPhoneNumberAdapter);
    }


    /**
     * 显示从网络获取的通话记录
     */
    private void showListContactLog() {
        mCallRecordAdapter = new CallRecordAdapter(getActivity(), mVisitContactLogs, this);
        mLvCallRecord.setAdapter(mCallRecordAdapter);
    }


    /**
     * 关闭通话监听服务
     */
    private static void stopCallRecordService() {
        activity.stopService(mCallRecordIntent);
        Log.e(TAG, "stopCallRecordService: " + "监听服务已关闭！");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_visit_contact_information, null);
        loadData();
        initVariables();
        initViews(view);
        return view;
    }

    public void initVariables() {

    }

    public void initViews(View view) {
        mLlFamilyNumbers = (LinearLayout) view.findViewById(R.id.ll_family_numbers);
        mTvFamilyNumbers = (TextView) view.findViewById(R.id.tv_family_numbers);
        mIvFamilyNumbers = (ImageView) view.findViewById(R.id.iv_arrow);
        mTvContactNumber = (EditText) view.findViewById(R.id.tv_contact_number);
        mLvPhoneContactPeople = (ImageView) view.findViewById(R.id.iv_phone_contact_people);
        mLvFamilyNumbersPhone = (ListView) view.findViewById(R.id.lv_family_numbers_phone);
        mLvCallRecord = (ListView) view.findViewById(R.id.lv_call_log);


        mLlFamilyNumbers.setOnClickListener(new ShowFamilyNumbers());
        mLvPhoneContactPeople.setOnClickListener(new ShowLocalContacts());



    }


    public void loadData() {

        /**
         * 获取家庭成员信息
         */
        getFamilyNumbers();

        /**
        * 获取文件存放根路径
         */
        getWebFilePath();


        /**
         * 获取联系人列表
         */
        getContacts();


        /**
         * 获取通话记录列表
         */
        requestContactLog();



    }


    /**
     * 获取联系人列表
     */
    private void getContacts() {
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/getContactsInfo"});
        StringRequest getContacts = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                visitFamilyInfos = JSON.parseArray(s, VisitFamilyInfo.class);
                Message msg = new Message();
                msg.what = GETCONTACTSCODE;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);
                mHandler.sendEmptyMessage(ERROR);
                ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("visitorId", PMSApplication.gUser.getTellId());
                map.put("clientNum", activity.getIntent().getStringExtra("Extra_khbh"));
                map.put("memberType", "0");
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(getContacts);
    }


    /**
     * 获取家庭成员信息
     */
    public void getFamilyNumbers() {
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/getFamilyNumberNames"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            mListFamilyNumbers = (ArrayList<String>) JSON.parseArray(response,String.class);
                            if (mListFamilyNumbers!=null){
                                mTvFamilyNumbers.setText(mListFamilyNumbers.get(0));
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(message!=null)
                            mHandler.sendMessage(message);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                mHandler.sendEmptyMessage(ERROR);
                ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("visitorId", PMSApplication.gUser.getTellId());
                map.put("clientNum",activity.getIntent().getStringExtra("Extra_khbh"));
                return map;
            }
        };

        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }

    /**
     * 获取服务器文件存放的跟路径
     */
    private void getWebFilePath() {
        String ghrgh = PMSApplication.gUser.getTellId();
        String khbh = activity.getIntent().getStringExtra("Extra_khbh");
        mWebFilesPath = "http://" + MakeUrl.IPNUMBER + "/"+"res/"+ ghrgh + "/" + khbh + "/" + "video/";
    }


    /**
     * 从服务器获取通话录音文件
     */
    private void getPhoneRecoreFiles() {
        mHttpUtils = new HttpUtils();
        for (VisitContactLog visitContactLog : mVisitContactLogs) {
            String fileName = visitContactLog.getFileName();
            File file = new File(path + fileName);
            /**
             * 判断本地是否存在录音文件
             * 不存在就下载
             */
            if (!file.exists()){
                /**
                 * 下载文件"http://123.207.87.47:8080/1515997799607714486.3gp"
                 * mWebFilesPath + fileName
                 */
                mHttpUtils.download(mWebFilesPath + fileName, path + fileName, true, false, new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        Log.e(TAG, "onSuccess: "+"下载录音文件成功");
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e(TAG, "onFailure: "+"下载录音文件失败"+e.getMessage());
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        Log.e(TAG, "onLoading: "+total +"    "+current + "  "+ isUploading);
                    }
                });
            }

        }
    }


    /**
     * 获取以前通话记录列表
     */
    private void requestContactLog() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MakeUrl.makeURL(new String[]{"contactinfo/findListContactLog.action"}),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e(TAG, "onResponse: " + s);
                        /*获取通话记录*/
                        mVisitContactLogs = parseArray(s, VisitContactLog.class);
                        if (mVisitContactLogs != null) {
                            Message msg = new Message();
                            msg.what = GETPHONERECORD;
                            mHandler.sendMessage(msg);
                        } else {
                            ToastUtil.showToast(getActivity(), CommonContent.ERROR_CONTACTLOG_RESULT);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, "onErrorResponse: " + volleyError.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("clientNum", activity.getIntent().getStringExtra("Extra_khbh"));
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }




    /**
     * 家庭成员点击监听
     */
    private class ShowFamilyNumbers implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (listPopupWindow == null)
                listPopupWindow = new ListPopupWindow(getActivity());

            if (adapter == null) {
                adapter = new VisitFamilyNumbersMenuAadapter(getActivity(), R.layout.item_list_single_text,
                        mListFamilyNumbers.toArray(new String[mListFamilyNumbers.size()]));

                // ListView适配器
                listPopupWindow.setAdapter(adapter);

                // 选择item的监听事件
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        mTvFamilyNumbers.setText(adapter.getItem(pos));
                        listPopupWindow.dismiss();
                    }
                });

                listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //旋转0度是复位ImageView
                        mIvFamilyNumbers.animate().setDuration(500).rotation(0).start();
                    }
                });
            }

            // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
            int viewWidth = v.getWidth();
            listPopupWindow.setAnchorView(v);
            listPopupWindow.setVerticalOffset(DensityUtil.dip2px(getActivity(), 5));
            listPopupWindow.setHorizontalOffset(-15);
            // 对话框的宽高
            listPopupWindow.setWidth(viewWidth - 25);
            listPopupWindow.setModal(true);
            listPopupWindow.show();
            mIvFamilyNumbers.animate().setDuration(500).rotation(180).start();
        }
    }

    /**
     * 读取本地联系人点击监听
     */
    private class ShowLocalContacts implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), VisitContactsActivity.class);
            startActivityForResult(intent, READ_LOCALCONTACTS_CODE);
        }
    }

    /**
     * Activity 回调结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode);
        if (data != null) {
            if (requestCode == READ_LOCALCONTACTS_CODE) {
                String contactName = data.getStringExtra("contactName");
                String contactNumber = data.getStringExtra("contactNumber");
                VisitFamilyInfo visitFamilyInfo = new VisitFamilyInfo();
                visitFamilyInfo.setMemberName(contactName);
                visitFamilyInfo.setPhone(contactNumber);
                visitFamilyInfos.add(visitFamilyInfo);
                mContactsPhoneNumberAdapter.notifyDataSetChanged();
                Message msg = new Message();
                msg.what = SAVEPHONEFROMLISTCONTACTS;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        }

    }

    /**
     * 增加录音条目
     */
    private static void addCallRecordItem() {
        VisitContactLog visitContactLog = new VisitContactLog();
        visitContactLog.setDate(CallPhoneService.mDBFileName);
        visitContactLog.setPhone(mPhone);
        mVisitContactLogs.add(visitContactLog);
        mCallRecordAdapter.notifyDataSetChanged();

//        mFile = new File(path + CallPhoneService.mFileName+".3gp");
        mFile = new File(path + CallPhoneService.mFileName+".amr");
        Log.e(TAG, "addCallRecordItem:文件保存路径 "+ mFile.getAbsolutePath());
        if (mFile.exists()) {
            /**
             * 数据库中插入一条数据并把录音文件
             * 上传文件到服务器
             */
            insertPhoneRecordItem();

            /**
             * 上传录音文件到服务器
             */
            uploadPhoneRecordFile();

        }
    }


    /**
     * 上传录音文件到服务器
     */
    private static void uploadPhoneRecordFile() {
        String url = MakeUrl.makeURL(new String[]{"contactinfo/uploadPhoneRecordFile.action"});

        List<Part> partList = new ArrayList<Part>();
        partList.add(new StringPart("khbh", activity.getIntent().getStringExtra("Extra_khbh"), "UTF-8"));
        partList.add(new StringPart("ghrgh", PMSApplication.gUser.getTellId(), "UTF-8"));
        partList.add(new StringPart("type", "video", "UTF-8"));
        try {
            partList.add(new FilePart("fileupload", mFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        MultipartRequest multipartRequest = new MultipartRequest(url, partList.toArray(new Part[partList.size()]), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG, "onResponse: "+"上传成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: "+"上传失败");
            }
        });
        //将请求加入队列
        VolleyUtil.getInstanceRequestQueue().add(multipartRequest);
    }


    /**
     * 数据库增加录音记录
     */
    private static void insertPhoneRecordItem() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MakeUrl.makeURL(new String[]{"contactinfo/saveContactLog.action"}),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name",mName);
                map.put("phone",mPhone);
//                map.put("fileName",CallPhoneService.mFileName+".3gp");
                map.put("fileName",CallPhoneService.mFileName+".amr");
                map.put("date",CallPhoneService.mDBFileName);
                map.put("visitorId", PMSApplication.gUser.getTellId());
                map.put("clientNum",activity.getIntent().getStringExtra("Extra_khbh"));
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }

    /**
     * 打电话按钮点击监听
     *
     * @param view
     */
    @Override
    public void click(View view) {
        final int position = (int) view.getTag();
        mAlertDialogRecord = new AlertDialog.Builder(getActivity()).create();
        mAlertDialogRecord.setCanceledOnTouchOutside(false);
        mAlertDialogRecord.show();
        mAlertDialogRecord.getWindow().setContentView(R.layout.dialog_pop);
        TextView msg = (TextView) mAlertDialogRecord.getWindow().findViewById(R.id.tv_sjd_id);
        msg.setText("是否对通话进行录音？");
        Button cancle = (Button) mAlertDialogRecord.getWindow().findViewById(R.id.alarm_btn_right);
        Button confirm = (Button) mAlertDialogRecord.getWindow().findViewById(R.id.alarm_btn_left);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialogRecord != null) {
                    mAlertDialogRecord.dismiss();
                    mAlertDialogRecord = null;
                }

                /**
                 * 调用系统打电话的功能
                 */
                mPhone = visitFamilyInfos.get(position).getPhone();//电话号码
                mName = visitFamilyInfos.get(position).getMemberName();   //联系人名称
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPhone));
                startActivity(callIntent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialogRecord != null) {
                    mAlertDialogRecord.dismiss();
                    mAlertDialogRecord = null;
                }

                /**
                 * 开启监听录音服务
                 */
                mCallRecordIntent = new Intent(getActivity(), CallPhoneService.class);
                getActivity().startService(mCallRecordIntent);

                /**
                 * 调用系统打电话的功能
                 */
                mPhone = visitFamilyInfos.get(position).getPhone();//电话号码
                mName = visitFamilyInfos.get(position).getMemberName();   //联系人名称
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPhone));
                startActivity(callIntent);
            }
        });

    }


    /**
     * 播放录音按钮点击监听
     *
     * @param view
     */
    @Override
    public void recordClick(View view) {
        String fileName = (String) view.getTag();
        mCallRecord = (ImageView) view.findViewById(R.id.iv_call_record);

        /**
         * playOver防止多次点击
         */
        if (playOver) {
            playOver = false;
            File file = new File(path + "/" + fileName);
            Log.e(TAG, "recordClick文件路径: "+file.getPath());
            if (file.exists()) {
                try {
                    Log.e(TAG, "recordClick: " + " " + file.getPath());
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(file.getPath());
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    ToastUtil.showToast(getActivity(), "未找到文件路径");
                }
                mMediaPlayer.start();
                mCallRecord.setImageResource(R.drawable.ic_call_record_voice_selected);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playOver = true;
                        mCallRecord.setImageResource(R.drawable.ic_call_record_voice);
                    }
                });
            } else {
                ToastUtil.showToast(getActivity(), "抱歉，未找到录音文件");
            }
        }

    }


    /**
     * 增加条目的方法
     * 在广播中调用
     */
    private static void addCallRecord() {
        mAlertDialogSave = new AlertDialog.Builder(activity).create();
        mAlertDialogSave.setCanceledOnTouchOutside(false);
        mAlertDialogSave.show();
        mAlertDialogSave.getWindow().setContentView(R.layout.dialog_pop);
        TextView msg = (TextView) mAlertDialogSave.getWindow().findViewById(R.id.tv_sjd_id);
        msg.setText("是否保存这段录音？");
        Button cancle = (Button) mAlertDialogSave.getWindow().findViewById(R.id.alarm_btn_right);
        Button confirm = (Button) mAlertDialogSave.getWindow().findViewById(R.id.alarm_btn_left);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialogSave != null) {
                    mAlertDialogSave.dismiss();
                    mAlertDialogSave = null;
                }
//                File file = new File(path + "/" + CallPhoneService.mFileName + ".3gp");
                File file = new File(path + "/" + CallPhoneService.mFileName + ".amr");
                if (file.exists()) {
                    file.delete();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialogSave != null) {
                    mAlertDialogSave.dismiss();
                    mAlertDialogSave = null;
                }
                /**
                 * 增加录音条目
                 */
                addCallRecordItem();

            }
        });

    }

    @Override
    public boolean judgeCallBack() {
        return false;
    }

    /**
     * 点击保存按钮监听
     * 增加家庭联系人信息
     */
    @Override
    public void saveCallBack() {
        String memberName = mTvFamilyNumbers.getText().toString();
        String phone = mTvContactNumber.getText().toString();
        saveContcatPhone(memberName,phone);
    }


    /**
     * 保存联系人信息
     */
    private void saveContcatPhone(final String memberName, final String phone){
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/saveFamilyInfo"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            JSONObject jsonObj = JSON.parseObject(response);
                            message = mHandler.obtainMessage(OPERATIONFINISHED,jsonObj.getString("result"));
                        }catch(JSONException e){
                            e.printStackTrace();
                        }catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        if(message!=null)
                            mHandler.sendMessage(message);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                mHandler.sendEmptyMessage(ERROR);
                ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("memberName",memberName);
                map.put("phone",phone);
                map.put("clientNum",activity.getIntent().getStringExtra("Extra_khbh"));
                map.put("visitorId",PMSApplication.gUser.getTellId());
                map.put("memberType","0");
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mCallRecord.setImageResource(R.drawable.ic_call_record_voice);
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
