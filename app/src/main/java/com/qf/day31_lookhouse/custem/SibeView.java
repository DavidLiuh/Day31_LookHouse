package com.qf.day31_lookhouse.custem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 侧边字母控件
 */
public class SibeView extends View{
    private static final String TAG = "print";
    //需要绘制的文本
    private String[] str = {"A","B","C","D","E","F","G","H","I","J",
            "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private Paint paint;
    private int textHeight;
    private int index = -1;//被选中文本的下标

    private OnSideSelectedListener onSideSelectedListener;

    public SibeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 接口回调的set方法
     * @param onSideSelectedListener
     */
    public void setOnSideSelectedListener(OnSideSelectedListener onSideSelectedListener) {
        this.onSideSelectedListener = onSideSelectedListener;
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(20);
        paint.setColor(Color.parseColor("#7fb7f2"));
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        textHeight = (int) (paint.descent() - paint.ascent());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < str.length; i++){
            if(index == i){
                paint.setColor(Color.parseColor("#7808f7"));
                canvas.drawText(str[i], getWidth() / 2, textHeight * (i + 1), paint);
                paint.setColor(Color.parseColor("#7fb7f2"));
            } else {
                canvas.drawText(str[i], getWidth() / 2, textHeight * (i + 1), paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureView(widthMeasureSpec, 0), measureView(heightMeasureSpec, 1));
    }

    /**
     * 测量工具方法
     * @param spec 宽或高的空间值
     * @param type 0 - 表示测量宽度 1 - 表示测量高度
     *
     * 测量文本的高度:paint.descent() - paint.ascent()
     * 测量文本的宽度:paint.measureText("xxxx")
     * 测量的画笔，必须为绘制该文本的画笔
     * @return
     */
    private int measureView(int spec, int type){
        int mode = MeasureSpec.getMode(spec);//从空间值中获得模式
        int size = MeasureSpec.getSize(spec);//获得系统的推荐值
        switch (mode){
            case MeasureSpec.EXACTLY:
                //表示精确的值，当空间的宽（高）设置为match_parent时，或者为一个精确的数值时，会是这种模式
                return size;
            case MeasureSpec.AT_MOST:
                //表示尽可能多，当空间的宽（高）设置为warp_content时，是这种模式，这种模式的宽高最大不能超过size
                if(type == 0){
                    //表示需要测量宽度
                    return (int) (paint.measureText(str[0]) + getPaddingLeft() + getPaddingRight());
                } else {
                    //表示需要测量高度
                    return (textHeight * str.length + getPaddingTop() + getPaddingBottom());
                }
            case MeasureSpec.UNSPECIFIED:
                //表示想要多大给多大，该模式通常出现在scrollview这种父容器对子控件宽高不设限的情况
                break;
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();//基于控件的Y值
//        event.getRawY(); //基于屏幕的Y值
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                selectindex(y);
                break;
            case MotionEvent.ACTION_MOVE:
                selectindex(y);
                break;
            case MotionEvent.ACTION_UP:
                this.index = -1;
                invalidate();
                break;
        }
        return true;
    }

    private void selectindex(int y){
        int index = y / textHeight;
        if(index < 0){
            index = 0;
        }
        if(index > str.length - 1){
            index = str.length - 1;
        }

        //防止无效的绘制
        if(index == this.index){
            return;
        }

        /**
         * 调用接口回调方法
         */
        if(onSideSelectedListener != null){
            onSideSelectedListener.onSelected(index, str[index]);
        }

        this.index = index;
        invalidate();
//        Log.d(TAG, "onTouchEvent: 当前点击的下标：" + index + "  " + str[index]);
    }


    /**
     * 定义接口回调
     */
    public interface OnSideSelectedListener{
        void onSelected(int index, String strs);
    }

}
