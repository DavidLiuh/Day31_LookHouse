package com.qf.day31_lookhouse;

import android.app.Application;

import com.qf.day31_lookhouse.util.ShareUtil;

/**
 * 自定义Application
 */
public class AppContext extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化共享参数
        ShareUtil.initShared(this);
    }
}
