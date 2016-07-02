package com.qf.day31_lookhouse.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.qf.day31_lookhouse.R;
import com.qf.day31_lookhouse.SelectCityActivity;
import com.qf.day31_lookhouse.base.BaseFragment;
import com.qf.day31_lookhouse.util.Constants;
import com.qf.day31_lookhouse.util.ShareUtil;

/**
 * 首页显示的Fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "print";
    private Button btn;

    private int cityId = 1;//全局的城市ID，默认为北京

    @Override
    protected int contentResid() {
        return R.layout.fragment_home;
    }

    /**
     * 初始化
     * @param view
     */
    @Override
    protected void init(View view) {
        btn = (Button) view.findViewById(R.id.btn_selectcity);
        btn.setOnClickListener(this);

        //读取共享参数
        int cityId = ShareUtil.getInt(Constants.KEY_NAME.CITY_ID);
        if(cityId != -1){
            this.cityId = cityId;
        }

        String cityName = ShareUtil.getString(Constants.KEY_NAME.CITY_NAME);
        if(cityName != null){
            btn.setText(cityName);
        }
    }

    /**
     * 点击选择城市按钮，回调方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        //用有返回值的方式启动Activity
        startActivityForResult(new Intent(getActivity(), SelectCityActivity.class), Constants.Code.SELECT_CITY_REQUEST);
    }

    /**
     * 选择城市以后，返回选择的城市信息
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.Code.SELECT_CITY_REQUEST && resultCode == Constants.Code.SELECT_CITY_RESUEST){
            int cityId = data.getIntExtra(Constants.KEY_NAME.CITY_ID, -1);//获得城市ID
            String cityName = data.getStringExtra(Constants.KEY_NAME.CITY_NAME);//获得城市名称
            if(cityId != -1 && cityName != null){
                this.cityId = cityId;
                btn.setText(cityName);

                //放入共享参数
                ShareUtil.putInt(Constants.KEY_NAME.CITY_ID, cityId);
                ShareUtil.putString(Constants.KEY_NAME.CITY_NAME, cityName);
            }
        }
    }
}
