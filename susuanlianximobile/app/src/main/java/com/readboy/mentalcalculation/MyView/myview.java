package com.readboy.mentalcalculation.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ZY on 2017/2/21.
 */

public class myview extends View {
    private Canvas canvas;
    private Context context;
    private char[] strpro;
    private String[] strans;
    private Paint p,pl,ps;
    private Rect mBound = new Rect();
    private int anslong = 2;
    private boolean ansisfs = false;
    private boolean ispre = false;
    private boolean passequ = false;

    private int j=0,place=0;

    public myview(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        String[] temp = "?+?+(?+?+?)+?=!@12;23;34;45;56;67;78;89;90;1;1;1;1".split("@");
        strpro = temp[0].toCharArray();
        strans = temp[1].split(";");
        p = new Paint();
        ps = new Paint();
        pl = new Paint();
        p.setColor(Color.argb(255,0,149,255));
        ps.setColor(Color.argb(255,0,149,255));
        pl.setColor(Color.argb(255,0,149,255));
        p.setTextSize(DisplayUtil.sp2px(context,35));
        ps.setTextSize(DisplayUtil.sp2px(context,45));
        pl.setStrokeWidth(4);

        p.getTextBounds(temp[0], 0, temp[0].length(), mBound);
        j=0;
        place=0;
    }

    public void settext(String str,int anslong,boolean ansisfs) {
        if(str.lastIndexOf("%")!=-1)
        {
            ispre=true;
            str=str.substring(0,str.length()-2);
        }

        this.anslong = anslong;
        this.ansisfs = ansisfs;
        String[] temp = str.split("@");
        strpro = temp[0].toCharArray();
        strans = temp[1].split(";");
        p.getTextBounds(temp[0], 0, temp[0].length(), mBound);
        j=0;
        if(strpro[strpro.length-1]!='=')
        {
            place=(1000-(strpro.length-1)*50-anslong*25)/2;
            if(ansisfs && strpro[strpro.length-1]=='!')
            {
                strpro[strpro.length-1]='?';

            }

        }
        else
            place=(1000-(strpro.length)*50-anslong*25)/2;

        for(int i=0;i<strans.length;i++)
            Log.e("00000000000000",strans[i]+"");
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.canvas = canvas;
        for(int i=0;i<strpro.length;i++)
        {
            if(strpro[i]=='?')
                drawfs(canvas);
            else if(strpro[i]=='!')
                drawzs(canvas);
            else if(strpro[i]=='=')
            {
                int deep=80;
                if(ansisfs)
                {
                    deep=55;
                    if(anslong==1)
                        anslong++;
                }

                drawfh(canvas,i);
                drawline(canvas,deep);
                passequ=true;
            }
            else
                drawfh(canvas,i);
        }

        j=0;
        if(strpro[strpro.length-1]!='=')
            place=(1000-(strpro.length-1)*50-anslong*25)/2;
        else
            place=(1000-(strpro.length)*50-anslong*25)/2;
        passequ=false;
    }

    private void drawline(Canvas canvas,int deep) {


        if(ispre){
            canvas.drawLine(DisplayUtil.dip2px(context,place),DisplayUtil.dip2px(context,deep),
                    DisplayUtil.dip2px(context,place+anslong*25+5),DisplayUtil.dip2px(context,deep),pl);
            canvas.drawText("%",DisplayUtil.dip2px(context,place+anslong*25+10),DisplayUtil.dip2px(context,68),p);

        }else {
            canvas.drawLine(DisplayUtil.dip2px(context,place),DisplayUtil.dip2px(context,deep),
                    DisplayUtil.dip2px(context,place+anslong*25),DisplayUtil.dip2px(context,deep),pl);

        }
    }

    private void drawfs(Canvas canvas) {

        if(passequ)
        {
            passequ=false;
            int l;
            if(anslong==strans[j].length()&&anslong>2)
                l=10;
            else if (anslong==strans[j].length()&&anslong<=2)
            {
                l=3;
            }else {
                l=(anslong-strans[j].length())*13;
            }
            canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+l),DisplayUtil.dip2px(context,45),p);

            if(anslong==strans[j].length()&&anslong>2)
                l=10;
            else if (anslong==strans[j].length()&&anslong<=2)
                l=3;
            else
                l=(anslong-strans[j].length())*13;
            canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+l),DisplayUtil.dip2px(context,90),p);

            return;
        }

        if(strans[j].length()==2){
            canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place),DisplayUtil.dip2px(context,45),p);
        }
        else if(strans[j].length()==1){
            canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+13),DisplayUtil.dip2px(context,45),p);
        }

        canvas.drawLine(DisplayUtil.dip2px(context,place),DisplayUtil.dip2px(context,55),
                DisplayUtil.dip2px(context,place+50),DisplayUtil.dip2px(context,55),pl);

        if(strans[j].length()==2){
            canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place),DisplayUtil.dip2px(context,90),p);
        }
        else if(strans[j].length()==1){
            canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+13),DisplayUtil.dip2px(context,90),p);
        }

        place+=50;
        return;
    }

    private void drawzs(Canvas canvas) {
        if (ispre){
            if(strans[j].length()<=2)
            {
                canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+5),DisplayUtil.dip2px(context,68),p);
                place+=50;

            }
            else
            {
                canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+10),DisplayUtil.dip2px(context,68),p);
                place+=50;
            }
        }else {
            if(strans[j].length()==2)
            {
                canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place),DisplayUtil.dip2px(context,68),p);
                place+=50;

            }
            else
            {
                canvas.drawText(strans[j++],DisplayUtil.dip2px(context,place+8),DisplayUtil.dip2px(context,68),p);
                place+=50;
            }
        }
        return;
    }

    private void drawfh(Canvas canvas,int i) {
        int pfh = 0;
        switch (strpro[i])
        {
            case'+': pfh=place+10;break;
            case'-': pfh=place+15;break;
            case'×': pfh=place+15;break;
            case'÷': pfh=place+12;break;
            case'=': pfh=place+7;break;
            case'(': pfh=place+12;break;
            case')': pfh=place+12;break;
            case'%': pfh=place+14;break;
        }
        canvas.drawText(""+strpro[i],DisplayUtil.dip2px(context,pfh),DisplayUtil.dip2px(context,68),ps);
        place+=50;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        //设置宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:   //指定大小
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:   //wrap_content
                width = (int)(getPaddingLeft() + getPaddingRight() + mBound.width()*3.5);
                break;
        }

        //设置高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = (int)(getPaddingTop() + getPaddingBottom() + mBound.height()*3.5);
                break;
        }
        setMeasuredDimension(width, height);
    }
}

class DisplayUtil {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}

