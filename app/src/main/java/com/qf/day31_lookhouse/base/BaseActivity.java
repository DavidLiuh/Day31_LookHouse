package com.qf.day31_lookhouse.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * activity的管理基类，
 * 项目中的所有activity都必须继承于该类
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
        init();
        loadDatas();
    }

    /**
     * 该方法由子类去实现，返回需要显示的布局ID
     * @return
     */
    protected abstract int contentView();


    /**
     * 初始化方法，具体实现应该交给子类
     */
    protected void init() {}

    /**
     * 加载数据方法，具体实现交给子类
     */
    protected void loadDatas() {}

    /**
     * 带过场动画的启动Activit的方式
     */
    public void startActivity(Intent intent, int animin, int animout){
        super.startActivity(intent);
        overridePendingTransition(animin, animout);
    }
}
