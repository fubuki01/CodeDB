package com.readboy.mentalcalculation.game.MyActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
/*
 排名的Activity
 */
import com.readboy.mentalcalculation.MyView.MyCircleimgView;
import com.readboy.mentalcalculation.R;
import com.readboy.mentalcalculation.other.CurrentAndUsersUtils;
import com.readboy.susuan.bean.UserInfos;
import com.readboy.susuan.util.VolleyUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class RankActivity extends Activity{

    private ArrayList<View> viewlist = new ArrayList<View>();
    private TextView currendgrade;
    private TextView currenrank;
    private TextView gameoovertime;
    private TextView currentuesrname;
    private TextView currentuesraddress;
    private ImageView currentuserpicture;
    private ListView userinfomation;
    private int currentThisGrade;
    private int currentThisRank;
    private UserInfos current = new UserInfos();
    private List<UserInfos> users ;
    VolleyUtil volleyUtil;
    private Context context;
   private Integer[] imageid = {R.drawable.rank1,R.drawable.rank2,R.drawable.rank3,R.drawable.rank4,
            R.drawable.rank5,R.drawable.rank6,R.drawable.rank7,R.drawable.rank8,
            R.drawable.rank9,R.drawable.rank10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("oncreate", "onCreate: ");
        setContentView(R.layout.activity_rank);
        context=this;
        volleyUtil=new VolleyUtil(context);
        users  = new ArrayList<UserInfos>();
        Intent intent=getIntent();
        //得到当前的分数
        currentThisGrade = intent.getExtras().getInt("current_grade");
        //得到当前的排名
        currentThisRank = intent.getExtras().getInt("current_rank");
        findView();   //得到组件的ID
       setCurrentUserInfo();   //设置当前用户的分数和排名和个人信息
       setGameOverTimeWeek();   //设置本周的剩余时间
        showAllUserInfomationRank();   //设置ListView的显示用户内容
    }
    /*
    设置ListView的显示用户内容
     */
    private void showAllUserInfomationRank() {
        final Map<Integer,Object>  pictureBitmap = new HashMap<>();
        final List<Map<String,Object>> listitems = new ArrayList<Map<String,Object>>();
        int index=0;    //显示的人数
        if(users == null){
            Toast.makeText(RankActivity.this,"用户信息加载失败",Toast.LENGTH_SHORT).show();
            return;
        }

        for(UserInfos u : users){
            if(index<10){   //当显示的人数小于10个，则加入到显示列表中
                Map<String,Object> listitem = new HashMap<String, Object>();

                listitem.put("rank",imageid[u.getRank()-1]);   //排名
                listitem.put("picture",u.getAvatar());         //头像
                listitem.put("address",u.getSchool());       //用户学校
                listitem.put("name",u.getName());
                listitem.put("grade",""+u.getScore());
                listitems.add(listitem);
                index++;
            }
            else{
                break;
            }
        }
         /*
         设置适配器
          */
        SimpleAdapter sadapter = new SimpleAdapter(context
                , listitems
                , R.layout.listview_rank
                , new String[]{"rank","picture","address","name","grade"}
                , new int[]{R.id.list_rankimg,R.id.list_headimg,R.id.list_address,R.id.list_name,R.id.list_grade}) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(position>=viewlist.size())
                {
                    View myview = getLayoutInflater().inflate(R.layout.listview_rank,null);
                    ImageView paiming = (ImageView)myview.findViewById(R.id.list_rankimg);
                    TextView name = (TextView) myview.findViewById(R.id.list_name);
                    final ImageView photo = (ImageView) myview.findViewById(R.id.list_headimg);
                    TextView address = (TextView) myview.findViewById(R.id.list_address);
                    TextView grade = (TextView) myview.findViewById(R.id.list_grade);

                    paiming.setImageResource(imageid[position]);
                    name.setText((String) listitems.get(position).get("name"));
                    address.setText((String) listitems.get(position).get("address"));
                    grade.setText((String) listitems.get(position).get("grade"));

                    photo.setTag((String)listitems.get(position).get("picture"));
                    volleyUtil.getAvatar((String) listitems.get(position).get("picture"),photo,0,R.drawable.defaultheadimg);
                    viewlist.add(myview);
                    return myview;
                }
                else
                    return viewlist.get(position);
            }
        };
        userinfomation.setAdapter(sadapter);
        }
    /*
    设置本周的剩余时间
     */
    private void setGameOverTimeWeek() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int day=7-calendar.get(Calendar.DAY_OF_WEEK);
        Time time=new Time();
        time.setToNow();
        int hour = 24-time.hour;
        gameoovertime.setText(day+"天"+hour+"小时");
    }
    /*
    设置当前用户的分数和排名
     */
    private void setCurrentUserInfo() {
        Log.e("oncreate", "onCreate3333: ");
        currendgrade.setText(currentThisGrade+"分");   //设置分数
        currenrank.setText(currentThisRank+"名");      //设置排名
        //得到当前用户的引用
        current = CurrentAndUsersUtils.getCurrent();
        if(current == null) {
            finish();
            return;
        }
        //得到所有用户信息
        users = CurrentAndUsersUtils.getUsers();
        //设置当前用户的名字
       currentuesrname.setText(current.getName());
        //设置当前用户的头像

        volleyUtil.getAvatar(current.getAvatar(),currentuserpicture,0,R.drawable.defaultbigheadimg);

        //设置当前用户的地址
        if("".equals(current.getSchool()) ) {
            currentuesraddress.setText("未填写学校信息");
        }
        else {
            currentuesraddress.setText(current.getSchool());
        }
    }
    /*
     得到组件的ID
     */
    private void findView() {
        //当前的分数
        currendgrade = (TextView) findViewById(R.id.rankdialog_grade);
        //当前的排名
        currenrank = (TextView) findViewById(R.id.rankdialog_rank);
        //结束时间
        gameoovertime = (TextView) findViewById(R.id.rankdialog_time);
        //用户名
        currentuesrname = (TextView) findViewById(R.id.rankdialog_name);
        //用户地址
        currentuesraddress = (TextView) findViewById(R.id.rankdialog_address);
        //用户头像
        currentuserpicture = (ImageView) findViewById(R.id.rankdialog_headimg);
        //显示前十名用户信息
        userinfomation = (ListView) findViewById(R.id.ranklist);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("isback", "onBackPressed ");
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
//        recycleImageView();
    }
    /**
     * 回收ListView的ImageView占用的图像内存
     */
    public void recycleImageView(){
        View view;
        while(viewlist.size()>0) {
            view = viewlist.get(0).findViewById(R.id.list_headimg);
            if(view instanceof MyCircleimgView){
                Drawable drawable=((ImageView) view).getDrawable();
                if(drawable instanceof BitmapDrawable){
                    Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();
                    if (bmp != null && !bmp.isRecycled()){
                        ((ImageView) view).setImageBitmap(null);
                        bmp.recycle();
                        bmp=null;
                    }
                }
            }
            view = viewlist.get(0).findViewById(R.id.list_rankimg);
            if(view instanceof ImageView){
                Drawable drawable=((ImageView) view).getDrawable();
                if(drawable instanceof BitmapDrawable){
                    Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();
                    if (bmp != null && !bmp.isRecycled()){
                        ((ImageView) view).setImageBitmap(null);
                        bmp.recycle();
                        bmp=null;
                    }
                }
            }
            viewlist.remove(0);
        }

    }
}
