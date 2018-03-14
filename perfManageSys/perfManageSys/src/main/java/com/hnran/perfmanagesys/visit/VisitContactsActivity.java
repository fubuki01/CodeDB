package com.hnran.perfmanagesys.visit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.utils.PinyinComparator;
import com.hnran.perfmanagesys.utils.PinyinUtils;
import com.hnran.perfmanagesys.view.SlideBar;
import com.readboy.ssm.po.ContactsPhone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取手机联系人
 */
public class VisitContactsActivity extends Activity {

    private static final int CONTACT_INFORMATION_CODE = 2;
    private List<ContactsPhone> list;
    private ListView listview;
    private SlideBar slidebar;
    private TextView checked;
    private MyAdapter adapter;

    private void findView() {
        listview = (ListView) findViewById(R.id.listview);
        slidebar = (SlideBar) findViewById(R.id.slidebar);
        checked = (TextView) findViewById(R.id.checked);

        list = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.bg_title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_visit_contacts);
        findView();
        initPhoneNo();
        initListener();
        setData();

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("contactName",list.get(position).getName());
                intent.putExtra("contactNumber",list.get(position).getPhoneNo());
                setResult(CONTACT_INFORMATION_CODE,intent);
                VisitContactsActivity.this.finish();
//                Toast.makeText(view.getContext(), list.get(position).getName() + " , " + list.get(position).getPhoneNo(), Toast.LENGTH_SHORT).show();

            }
        });

        slidebar.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(boolean isTouch, String letter) {
                if (isTouch){
                    // 该字母首次出现的位置
                    int position = adapter.getPositionForSelection(letter.charAt(0));
                    if (position != -1){
                        checked.setText(letter);
                        checked.setVisibility(View.VISIBLE);
                        listview.setSelection(position);
                    }
                }else {
                    checked.setVisibility(View.GONE);
                }
            }
        });

    }

    private void setData() {
        //数据在放在adapter之前需要排序
        Collections.sort(list, new PinyinComparator());
        adapter = new MyAdapter();
        listview.setAdapter(adapter);

    }

    private void initPhoneNo() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cols, null, null, null);
        for (int i = 0; i < cursor.getCount(); i ++){
            cursor.moveToPosition(i);
            // 获取联系人的名字
            int nameFiedColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFiedColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            String pinyin = PinyinUtils.getPingYin(name);
            String fpingYin = pinyin.substring(0, 1).toUpperCase();
            ContactsPhone bean = new ContactsPhone();
            bean.setName(name);
            bean.setPhoneNo(number);
            bean.setPinyin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (fpingYin.matches("[A-Z]")){
                bean.setFistPinyin(fpingYin);
            }else {
                bean.setFistPinyin("#");
            }
            list.add(bean);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new MyViewHolder();
                convertView = View.inflate(VisitContactsActivity.this, R.layout.item_phone_no, null);
                viewHolder.zm = (TextView) convertView.findViewById(R.id.zm);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.phone = (TextView) convertView.findViewById(R.id.phone);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            // 获取首字母的assi值
            int selection = list.get(position).getFistPinyin().charAt(0);
            // 通过首字母的assii值来判断是否显示字母
            int positionForSelection = getPositionForSelection(selection);
            if (position == positionForSelection){
                viewHolder.zm.setVisibility(View.VISIBLE);
                viewHolder.zm.setText(list.get(position).getFistPinyin());
            }else {
                viewHolder.zm.setVisibility(View.GONE);
            }
            viewHolder.name.setText(list.get(position).getName());
            viewHolder.phone.setText(list.get(position).getPhoneNo());

            return convertView;
        }

        /**
         * 获得到每个字符的第一个的位置
         * @param selection
         * @return
         */
        public int getPositionForSelection(int selection) {
            for (int i = 0; i < list.size(); i++) {
                String Fpinyin = list.get(i).getFistPinyin();
                char first = Fpinyin.toUpperCase().charAt(0);
                if (first == selection) {
                    return i;
                }
            }
            return -1;
        }

    }

    static class MyViewHolder{
        TextView zm;
        TextView name;
        TextView phone;
    }

}
