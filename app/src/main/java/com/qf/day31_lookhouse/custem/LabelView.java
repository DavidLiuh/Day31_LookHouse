package com.qf.day31_lookhouse.custem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


/**
 * 标签自定义View
 */
public class LabelView extends View{

    private Paint paintBg, paintTxt;
    private int radiu = 30;

    private String label;

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //默认为隐藏且不占位
        this.setVisibility(GONE);

        paintBg = new Paint();
        paintBg.setColor(Color.parseColor("#7fb7f2"));
        paintBg.setAntiAlias(true);

        paintTxt = new Paint();
        paintTxt.setAntiAlias(true);
        paintTxt.setTextSize(35);
        paintTxt.setColor(Color.WHITE);
        paintTxt.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(label != null) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radiu, paintBg);
            canvas.drawText(label, getWidth() / 2, getHeight() / 2 + ((paintTxt.descent() - paintTxt.ascent()) / 2 - paintTxt.descent()), paintTxt);
        }
    }


    int count = 2;
    /**
     * 设置文本的方法，供外部调用
     */
    public void setText(String label){
        this.label = label;
        count = 2;
        if(this.getVisibility() == GONE) {
            new TimerThread().start();
        }
        this.setVisibility(VISIBLE);//显示文本
        invalidate();
    }

    private static Handler handler = new Handler();
    class TimerThread extends Thread{
        @Override
        public void run() {
            while(true){
                count--;
                if(count <= 0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setVisibility(GONE);
                        }
                    });
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
