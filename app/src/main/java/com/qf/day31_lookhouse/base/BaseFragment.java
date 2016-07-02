package com.qf.day31_lookhouse.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment的基类，项目中所有Fragment应该继承于该类
 */
public abstract class BaseFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(contentResid(), container, false);
    }

    /**
     * 该方法不属于Fragment的生命周期方法
     * 该方法会紧跟着onCreateView方法被调用
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        init(view);
        loadDatas();
    }

    /**
     * 子类调用该方法，返回fragment中需要显示的布局ID
     * @return
     */
    protected abstract int contentResid();

    /**
     * 初始化Fragment，由子类具体实现
     * @param view
     */
    protected void init(View view) {}

    /**
     * 加载数据方法，由子类具体实现
     */
    protected void loadDatas() {}
}
