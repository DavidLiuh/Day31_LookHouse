package com.qf.day31_lookhouse;


import android.content.Intent;
import android.os.Handler;

import com.qf.day31_lookhouse.base.BaseActivity;


/**
 * 欢迎页Activity
 */
public class WelcomeActivity extends BaseActivity{

    private static Handler handler = new Handler();

    @Override
    protected int contentView() {
        return R.layout.activity_welcome;
    }


    /**
     * 初始化
     */
    @Override
    protected void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class), R.anim.activity_in_left, R.anim.activity_out_down);
                finish();
            }
        }, 2000);
    }
}
