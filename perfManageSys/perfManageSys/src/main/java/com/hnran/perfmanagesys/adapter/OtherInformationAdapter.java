package com.hnran.perfmanagesys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.platform.comapi.map.H;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.fragment.visit.OtherInformationFragment;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.readboy.ssm.po.OtherInformation;
import com.readboy.ssm.po.VisitOtherInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Created by Wyd on 2018/1/3.
 */

public class OtherInformationAdapter extends BaseAdapter {

    private static final String TAG = "OtherInformationAdapter";
    String path = OtherInformationFragment.path;
    private List<VisitOtherInfo> visitOtherInfos;
    private Context mCtx;
    private ViewHolder viewHolder;
    private final RequestQueue mQueue;
    private String filePath;


    public OtherInformationAdapter(Context ctx, List<VisitOtherInfo> visitOtherInfos){
        this.visitOtherInfos = visitOtherInfos;
        this.mCtx = ctx;
        mQueue = Volley.newRequestQueue(mCtx);
    }

    @Override
    public int getCount() {
        return visitOtherInfos == null ? 0 : visitOtherInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return visitOtherInfos == null ? 0 : visitOtherInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mCtx).inflate(
                    R.layout.item_attach_pictures,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String fileName = visitOtherInfos.get(position).getFileName();
        filePath = path+ File.separator +fileName;
        File file = new File(filePath);
        if(file.exists()){
            /**
             * 读取本地图片
             */
            viewHolder.ivFolder.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            viewHolder.ivFolder.setImageURI(Uri.fromFile(file));
            BitmapUtils bitmapUtils = new BitmapUtils(mCtx);
            bitmapUtils.display(viewHolder.ivFolder,file.getPath());
        }else {
           viewHolder.ivFolder.setImageResource(R.drawable.ic_photo_loading);
        }
        viewHolder.tvName.setText(visitOtherInfos.get(position).getAttachName());
        viewHolder.tvType.setText(visitOtherInfos.get(position).getAttachType());
        //隐藏显示ID
        viewHolder.tvType.setTag(visitOtherInfos.get(position).getId());
        viewHolder.tvName.setTag(filePath);
        return convertView;
    }


    public static class ViewHolder{
        ImageView ivFolder;
        TextView tvName;
        TextView tvType;

        public ViewHolder(View view){
            this.ivFolder = (ImageView) view.findViewById(R.id.imageview_folder_img);
            this.tvName = (TextView) view.findViewById(R.id.tv_folder_name);
            this.tvType = (TextView) view.findViewById(R.id.tv_file_type);
        }
    }

    public void setData(List<VisitOtherInfo> visitOtherInfos){
        this.visitOtherInfos = visitOtherInfos;
    }



}

