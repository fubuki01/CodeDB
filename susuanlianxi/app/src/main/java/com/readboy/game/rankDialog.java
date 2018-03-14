package com.readboy.game;

import android.app.AlertDialog;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.readboy.mentalcalculation.GetUserChapterName;
import com.readboy.mentalcalculation.R;
import com.readboy.susuan.bean.LoginUser;
import com.readboy.susuan.bean.UserInfos;
import com.readboy.susuan.util.VolleyUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class rankDialog{
    Context context;
    AlertDialog ad;
    Window window;
    Button back_bt;
    Button close_bt;
    TextView time_txt;
    ListView ranklist;
    TextView list11,list12,list13;

    int[] imageid = {R.drawable.rank1,R.drawable.rank2,R.drawable.rank3,R.drawable.rank4,
            R.drawable.rank5,R.drawable.rank6,R.drawable.rank7,R.drawable.rank8,
            R.drawable.rank9,R.drawable.rank10};

    UserInfos current;
    List<UserInfos> users;
   VolleyUtil volleyUtil;

    public rankDialog(Context context,UserInfos current,List<UserInfos> users){
        this.context = context;
        this.current = current;
        this.users = users;
        ad=new android.app.AlertDialog.Builder(context,R.style.finishiDialogStyle).create();
        ad.show();
        WindowManager.LayoutParams lp=ad.getWindow().getAttributes();
        lp.width= WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        ad.getWindow().setAttributes(lp);
//        ad.getWindow().setContentView(R.layout.finish_dialog_w);
//        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window = ad.getWindow();
        window.setAttributes(lp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.rank_dialog);
//        ad=new AlertDialog.Builder(context).create();
//        ad.show();
//        Window window = ad.getWindow();
//        window.setContentView(R.layout.rank_dialog);
    //    ad.setCancelable(false);
        ad.setCanceledOnTouchOutside(false); //可以点击返回键，
        volleyUtil=new VolleyUtil(context);
        findview(window);
        settime();
        setrank();
    }

    private void findview(Window window){
        back_bt = (Button)window.findViewById(R.id.back_bt);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        close_bt = (Button)window.findViewById(R.id.close_bt);
        close_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ad.dismiss();
            }
        });
        time_txt = (TextView)window.findViewById(R.id.deadline);
        ranklist = (ListView)window.findViewById(R.id.ranklist);
        list11 = (TextView)window.findViewById(R.id.list11);
        list12 = (TextView)window.findViewById(R.id.list12);
        list13 = (TextView)window.findViewById(R.id.list13);
    }

    private void settime(){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int day=7-calendar.get(Calendar.DAY_OF_WEEK);
        Time time=new Time();
        time.setToNow();
        int hour = 24-time.hour;
        time_txt.setText("本周结束还剩："+day+"天"+hour+"小时");
    }

    private void setrank(){
//        int userid=current.getUid();  //获取用户的ID
//        String usename="";
//        for(UserInfos u : users){   //拿到用户对应的name
//               if(u.getUid()==userid){
//                   usename=u.getName();
//                   break;
//               }
//        }
//        LoginUser loginUser=volleyUtil.getLoginUser();
//        String username="";
//        try {
//            username = URLDecoder.decode(loginUser.getRealName(), "UTF-8"); //得到当前用户的名字
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        String username=current.getName();  //得到当前用户的名字
        list11.setText(""+current.getRank());
        list12.setText(username);
        list13.setText(""+current.getScore()+"分");

        List<Map<String,Object>> listitems = new ArrayList<Map<String,Object>>();
        Log.e("length",users.size()+"");
        int index=0;    //显示的人数
        for(UserInfos u : users){
            if(index<10){   //当显示的人数小于10个，则加入到显示列表中
                Map<String,Object> listitem = new HashMap<String, Object>();
                listitem.put("rank",imageid[u.getRank()-1]);
//            listitem.put("rank",""+u.getRank());
                listitem.put("name",u.getName());
                listitem.put("grade",""+u.getScore());
                listitems.add(listitem);
                index++;
            }
            else{
                break;
            }
        }

        SpecialAdapter sadapter = new SpecialAdapter(context
                , listitems
                , R.layout.ranklistview
                , new String[]{"rank","name","grade"}
                , new int[]{R.id.list1,R.id.list2,R.id.list3});
        ranklist.setAdapter(sadapter);
    }
}


class SpecialAdapter extends SimpleAdapter{
    private int[] colors = new int[]{0x00000000, 0x07000000};
    public SpecialAdapter(Context context, List<? extends Map<String, ?>> data,
                          int resource, String[] from, int[] to){
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position, convertView, parent);
        int colorPos = position%colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}