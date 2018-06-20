package com.hnran.perfmanagesys.fragment.visit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.platform.comapi.map.F;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.OtherInformationAdapter;
import com.hnran.perfmanagesys.adapter.VisitMenuAadapter;
import com.hnran.perfmanagesys.utils.DensityUtil;
import com.hnran.perfmanagesys.utils.FileNameUtil;
import com.hnran.perfmanagesys.utils.FileUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.MultipartRequest;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.visit.PreviewPhotoActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.readboy.ssm.po.VisitOtherInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;


/**
 * Created by Wyd on 2018/1/2.
 * 拜访 ---其他信息
 */

public class OtherInformationFragment extends BaseFragment implements AdapterView.OnItemClickListener, VisitJudgeAndSaveCallBack {


    //    private static String path = Environment.getExternalStorageDirectory() + "/perfManageSys/";
//    private final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder";
    //文件存放路径
    public static String path = "";

    //打印标记
    private static final String TAG = "OtherInfoFragment";

    //调用相机请求码
    private static final int IMAGE_REQUEST_CODE = 1;
    //预览列表图片请求码
    private static final int PREVIEW_PTOTO_CODE = 2;
    //预览拍摄的图片请求码
    private static final int PREVIEW_CAMERAPHOTO_CODE = 3;

    //msg码
    /*向数据库插入一条数据msg结果码*/
    private static final int ADDOTHERINFOITEMCODE = 1;
    /*获取图片列表msg结果码*/
    private static final int GETLISTOTHERINFOCODE = 2;
    /*删除数据库中的数据结果码*/
    private static final int DELETEPHOTOFROMLIST = 3;


    //标记 -- 0 ：未拍照显示加号  1：显示拍摄的照片
    private static String flag = "0";

    //增加图片图标
    private ImageView mAddPicture;
    //图片--其他信息列表
    private ListView mPictureList;
    //附件类型
    private TextView tvAttachType;
    //附件类型选择下拉图标
    private ImageView ivArrow;
    //附件名称
    private EditText tvAttachName;
    //附件类型存放布局
    private LinearLayout mAttachType;
    //PopupWindow附件类型
    private ListPopupWindow listPopupWindow = null;
    //附件类型适配器
    private VisitMenuAadapter adapter = null;
    //其他信息适配器
    private OtherInformationAdapter otherInformationAdapter;

    //存放其他信息列表
    List<VisitOtherInfo> visitOtherInfos;

    //图片文件
    private File mFile;
    //客户编号
    private String khbh;
    //当前用户id
    private String tellId;
    //当前时间
    private String nowTime;
    //附件名称
    private String fileName;

    //本页面上下文
    private FragmentActivity localActivity;

    //下载工具类对象
    private HttpUtils mHttpUtils;
    //Bitmap工具类
    private BitmapUtils mBitmapUtils;

    /**
     * 处理本Fragement消息
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADDOTHERINFOITEMCODE:
                    //插入一条其他信息
                    String result = (String) msg.obj;
                    showInsertResultToast(result);
                    break;
                case GETLISTOTHERINFOCODE:
                    //获取列表成功后从后台获取其他信息文件图片
                    for (VisitOtherInfo visitOtherInfo : visitOtherInfos) {
//                        getOtherInfoFiles(visitOtherInfo.getFileName());
                        getOtherInfoFiles(visitOtherInfo);
                    }
                    /* 注入适配器 */
                    otherInformationAdapter = new OtherInformationAdapter(getActivity(), visitOtherInfos);
                    mPictureList.setAdapter(otherInformationAdapter);
                    break;
                case DELETEPHOTOFROMLIST:
                    //从数据库中删除对应的条目
                    Bundle data = msg.getData();
                    Integer itemId = (Integer) data.get("itemId");
                    String fileName = (String) data.get("fileName");
                    deleteOtherInfo(itemId, fileName);
                    break;
            }
        }
    };


    /**
     * 根据id删除数据库中的数据和对应的文件
     *
     * @param id
     * @param fileName
     */
    private void deleteOtherInfo(final Integer id, final String fileName) {
        //删除本地的
        String filePath = path + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        //删除数据库中的记录和服务器中的文件
        String url = MakeUrl.makeURL(new String[]{"otherInfo/deleteOtherInfoById"});
        StringRequest deleteOtherInfo = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("success")) {
                    ToastUtil.showToast(localActivity, "删除成功");
                } else if (s.equals("fail")) {
                    ToastUtil.showToast(localActivity, "删除失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: " + volleyError.getMessage(), volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(id));
                map.put("fileName", fileName);
                map.put("khbh", khbh);
                map.put("ghrgh", tellId);
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(deleteOtherInfo);
    }


    /**
     * 插入结果吐司提示框
     *
     * @param result 结果信息
     */
    private void showInsertResultToast(String result) {
        if (result.equals("fail")) {
            ToastUtil.showToast(localActivity, CommonContent.OPERATION_FAILED);
        } else {
            ToastUtil.showToast(localActivity, CommonContent.OPERATION_SUCCESS);
            //插入成功 列表增加数据
            VisitOtherInfo visitOtherInfo = new VisitOtherInfo();
            visitOtherInfo.setId(Integer.valueOf(result));
            visitOtherInfo.setAttachType(tvAttachType.getText().toString());
            visitOtherInfo.setAttachName(tvAttachName.getText().toString());
            visitOtherInfo.setFileName(fileName + ".png");
            visitOtherInfo.setDownOver(true);//之后修改的地方
            visitOtherInfos.add(visitOtherInfo);
            otherInformationAdapter.notifyDataSetChanged();
            mAddPicture.setImageResource(R.drawable.ic_addpic_focused);
            tvAttachName.setText("");
            tvAttachName.setHint("请填写附件名称，20字以内");
            flag = "0";

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visit_other_information, null);
        initVariables();
        initViews(view);
        loadData();
        return view;
    }

    /**
     * 初始化变量
     */
    public void initVariables() {
        flag = "0";
        mBitmapUtils = new BitmapUtils(getActivity());
        khbh = getActivity().getIntent().getStringExtra("Extra_khbh");
        tellId = getActivity().getIntent().getStringExtra("ghrgh");
        localActivity = getActivity();
        //存放照片文件的路径
        path = localActivity.getExternalCacheDir().toString();
        mHttpUtils = new HttpUtils();
    }


    //初始化布局
    public void initViews(View view) {
        tvAttachType = (TextView) view.findViewById(R.id.tv_attach_type);
        tvAttachName = (EditText) view.findViewById(R.id.tv_attach_name);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        mAttachType = (LinearLayout) view.findViewById(R.id.bar_attach_type);

        //类型点击监听
        mAttachType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopupWindow(mAttachType);
            }
        });


        mAddPicture = (ImageView) view.findViewById(R.id.iv_add_picture);
        mAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(flag)) {
                    if (mFile.exists()){
                        //显示拍摄的图片
                        Intent intent = new Intent(getActivity(), PreviewPhotoActivity.class);
                        intent.putExtra("path", mFile.getAbsolutePath());
                        intent.putExtra("type", "new");
                        startActivityForResult(intent, PREVIEW_CAMERAPHOTO_CODE);
                    }
                } else {
                    File newdir = new File(path);
                    //拍照
                    Intent intent = new Intent();
                    //intent.setAction("android.media.action.IMAGE_CAPTURE");
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                    fileName = FileNameUtil.genUniqueFileName();
                    //mFile = FileUtil.savePictureInApp(getActivity(), fileName);
                    //mFile = new File(FileUtil.getAppFilesDir(localActivity),fileName+".png");
                    mFile = new File(newdir, fileName + ".png");
                    if (!mFile.exists()) {
                        try {
                            mFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Uri uri = Uri.fromFile(mFile);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                }
            }
        });


        mPictureList = (ListView) view.findViewById(R.id.lv_attach_pictures);
        mPictureList.setOnItemClickListener(this);

    }


    /**
     * 加载数据
     */
    public void loadData() {
        //从后台获取其他信息列表
        getListOtherInfo();

    }


    /**
     * 从后台获取其他信息列表
     */
    private void getListOtherInfo() {
        String url = MakeUrl.makeURL(new String[]{"otherInfo/getListOtherInfo"});
        final StringRequest getListOtherInfo = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                visitOtherInfos = JSON.parseArray(s, VisitOtherInfo.class);
                Message msg = new Message();
                msg.what = GETLISTOTHERINFOCODE;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: " + volleyError.getMessage(), volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("clientNum", khbh);
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(getListOtherInfo);
    }

    /**
     * 从后台获取其他信息文件图片
     */
    private void getOtherInfoFiles(final VisitOtherInfo visitOtherInfo) {
        String url = "http://" + MakeUrl.IPNUMBER + "/" + "res/" + tellId + "/" + khbh + "/" + "image/" + visitOtherInfo.getFileName();
        /*判断本地是否存在此文件 不存在就下载 存在不用下载*/
        File file = new File(path + File.separator + visitOtherInfo.getFileName());
        if (!file.exists()) {
            mHttpUtils.download(url, path + File.separator + visitOtherInfo.getFileName(), true, false, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Log.e(TAG, "onSuccess: " + "下载附件文件" + visitOtherInfo.getFileName() + "成功");
                    visitOtherInfo.setDownOver(true);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.e(TAG, "onFailure: " + "下载附件文件" + visitOtherInfo.getFileName() + "失败");
                }
            });
        }else {
            visitOtherInfo.setDownOver(true);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE) {
            try {
                /*这里我们发送广播让MediaScanner
                扫描我们制定的文件这样在系统的相册
                中我们就可以找到我们拍摄的照片了 */
                Uri uri = Uri.fromFile(mFile);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                getActivity().sendBroadcast(intent);

                //加載本地图片
                mBitmapUtils.display(mAddPicture, mFile.getPath());
                //mAddPicture.setImageBitmap(image);
                flag = "1";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (data != null) {
            if (requestCode == PREVIEW_PTOTO_CODE) {
                String fileName = null;
                int itemId = data.getIntExtra("itemId", -2);
                if (itemId >= 0) {
                    Iterator<VisitOtherInfo> iterator = visitOtherInfos.iterator();
                    while (iterator.hasNext()) {
                        VisitOtherInfo visitOtherInfo = iterator.next();
                        if (visitOtherInfo.getId().equals(itemId)) {
                            iterator.remove();
                            fileName = visitOtherInfo.getFileName();
                            break;
                        }
                    }
                    otherInformationAdapter.notifyDataSetChanged();

                    //删除数据库中对应的条目
                    Message msg = new Message();
                    msg.what = DELETEPHOTOFROMLIST;
                    Bundle bundle = new Bundle();
                    bundle.putInt("itemId", itemId);
                    bundle.putString("fileName", fileName);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            }

            //新拍摄的
            if (requestCode == PREVIEW_CAMERAPHOTO_CODE) {
                mAddPicture.setImageResource(R.drawable.ic_addpic_focused);
                flag = "0";
                //删除文件
                String path = data.getStringExtra("path");
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView tvType = (TextView) view.findViewById(R.id.tv_file_type);
        TextView tvName = (TextView) view.findViewById(R.id.tv_folder_name);
        int itemId = (Integer) tvType.getTag();
        for (VisitOtherInfo visitOtherInfo : visitOtherInfos){
            if (visitOtherInfo.getId()==itemId){
                if (visitOtherInfo.isDownOver()==true){
                    //文件存放路径
                    String path = (String) tvName.getTag();
                    Intent intent = new Intent(getActivity(), PreviewPhotoActivity.class);
                    intent.putExtra("path", path);
                    intent.putExtra("itemId", itemId);
                    intent.putExtra("type", "old");
                    startActivityForResult(intent, PREVIEW_PTOTO_CODE);
                }else {
                    ToastUtil.showToast(localActivity,"图片加载中，请稍后查看");
                }
            }
        }

    }


    public void showListPopupWindow(View view) {
        if (listPopupWindow == null)
            listPopupWindow = new ListPopupWindow(getActivity());

        if (adapter == null) {
            adapter = new VisitMenuAadapter(getActivity(), android.R.layout.simple_list_item_1,
                    new String[]{"房屋", "轿车"});

            // ListView适配器
            listPopupWindow.setAdapter(adapter);

            // 选择item的监听事件
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    tvAttachType.setText(adapter.getItem(pos));
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //旋转0度是复位ImageView
                    ivArrow.animate().setDuration(500).rotation(0).start();
                }
            });
        }

        // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
        int viewWidth = view.getWidth();
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setVerticalOffset(DensityUtil.dip2px(getActivity(), 5));
        listPopupWindow.setHorizontalOffset(viewWidth * 2 / 3);
        // 对话框的宽高
        listPopupWindow.setWidth(viewWidth / 3);
        listPopupWindow.setModal(true);
        listPopupWindow.show();
        ivArrow.animate().setDuration(500).rotation(180).start();
    }

    /**
     * 保存按钮回调 判断条件是否符合
     *
     * @return 符合   true  弹出保存对话框
     * 不符合 false 弹出吐司对话框
     */
    @Override
    public boolean judgeCallBack() {
        //附件名称
        String attachName = tvAttachName.getText().toString();
        //附件种类
        String attachType = tvAttachType.getText().toString();
        if (flag.equals("0")) {
            //没有照片可以保存
            ToastUtil.showToast(localActivity, CommonContent.NOPHOTOSAVE);
            return false;
        } else if ((attachType.equals("")) || (attachType == null)) {
            ToastUtil.showToast(localActivity, CommonContent.PLEASESELECTATTACHTYPE);
            return false;
        } else if ((attachName.equals("")) || (attachName == null)) {
            //附件名称未填写
            ToastUtil.showToast(localActivity, CommonContent.NOATTACHNAME);
            return false;
        }
        return true;
    }

    /**
     * 点击保存按钮回调
     */
    @Override
    public void saveCallBack() {
        //向数据库中插入一条其他信息
        addOtherInfoItem();
        //上传相应其他图片信息到服务器
        uploadImageFile();
    }


    /**
     * 向数据库插入一条其他信息数据
     */
    private void addOtherInfoItem() {
        String url = MakeUrl.makeURL(new String[]{"otherInfo/insertOtherInfo"});
        StringRequest addOtherInfoItem = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG, "onResponse: " + s);
                Message msg = new Message();
                msg.what = ADDOTHERINFOITEMCODE;
                msg.obj = s;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: " + volleyError.getMessage(), volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");//设置日期格式
                // new Date()为取当前系统时间，也可使用当前时间戳
                nowTime = df.format(new Date());
                HashMap<String, String> map = new HashMap<>();
                map.put("attachType", tvAttachType.getText().toString());
                map.put("attachName", tvAttachName.getText().toString());
                map.put("fileName", fileName + ".png");
                map.put("date", nowTime);
                map.put("visitorId", tellId);
                map.put("clientNum", khbh);
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(addOtherInfoItem);
    }

    /**
     * 上传图片到服务器
     */
    private void uploadImageFile() {
        String filePath = path + File.separator + fileName + ".png";
        Log.e(TAG, "uploadImageFile: 上传图片" + filePath);
        File file = new File(filePath);
        if (file.exists()) {
            Log.e(TAG, "uploadImageFile: 上传图片文件是否存在" + file.getAbsolutePath());
            String url = MakeUrl.makeURL(new String[]{"otherInfo/uploadOtherInfoImageFile"});

            List<Part> partList = new ArrayList<Part>();
            partList.add(new StringPart("khbh", khbh, "UTF-8"));
            partList.add(new StringPart("ghrgh", tellId, "UTF-8"));
            partList.add(new StringPart("fileName", fileName + ".png", "UTF-8"));
            partList.add(new StringPart("type", "image", "UTF-8"));
            try {
                partList.add(new FilePart("fileupload", file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            MultipartRequest uploadImageFile = new MultipartRequest(url, partList.toArray(new Part[partList.size()]), new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.e(TAG, "onResponse: " + "上传成功");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(TAG, "onErrorResponse: " + "上传失败");
                }
            });
            //将请求加入队列
            VolleyUtil.getInstanceRequestQueue().add(uploadImageFile);
        }

    }
}
