package com.qf.day31_lookhouse;
import android.content.Intent;
import android.widget.RadioGroup;

import com.qf.day31_lookhouse.base.BaseActivity;
import com.qf.day31_lookhouse.fragment.HomeFragment;

/**
 * 首页显示的activity
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;

    @Override
    protected int contentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(this);
        //模拟点击第一个radiobutton
        radioGroup.getChildAt(0).performClick();
    }

    /**
     * 底部tab的点击监听
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_home://首页
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_fragment, new HomeFragment())
                        .commit();
                break;
            case R.id.rb_discover://发现
                break;
            case R.id.rb_msg://消息
                break;
            case R.id.rb_mime://我的
                break;
        }
    }
}
