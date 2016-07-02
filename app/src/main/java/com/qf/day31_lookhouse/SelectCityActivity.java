package com.qf.day31_lookhouse;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qf.day31_lookhouse.adapter.CityAdapter;
import com.qf.day31_lookhouse.base.BaseActivity;
import com.qf.day31_lookhouse.custem.LabelView;
import com.qf.day31_lookhouse.custem.SibeView;
import com.qf.day31_lookhouse.model.CitiyEntity;
import com.qf.day31_lookhouse.util.Constants;
import com.qf.day31_lookhouse.util.DownUtil;
import com.qf.day31_lookhouse.util.JsonUtil;

import java.util.List;

/**
 * 城市选择的Activity
 */
public class SelectCityActivity  extends BaseActivity implements DownUtil.OnDownComplete, AdapterView.OnItemClickListener, SibeView.OnSideSelectedListener {

    private static final String TAG = "print";
    private ListView lvCitys;
    private CityAdapter cityAdapter;

    private SibeView sibeView;
    private LabelView labelView;

    @Override
    protected int contentView() {
        return R.layout.activity_selectcity;
    }

    @Override
    protected void init() {
        lvCitys = (ListView) findViewById(R.id.lv_citys);
        cityAdapter = new CityAdapter(this);
        lvCitys.setAdapter(cityAdapter);
        lvCitys.setOnItemClickListener(this);

        sibeView = (SibeView) findViewById(R.id.sidev);
        labelView = (LabelView) findViewById(R.id.label);
        sibeView.setOnSideSelectedListener(this);
    }

    /**
     * 加载数据的方法
     */
    @Override
    protected void loadDatas() {
        DownUtil.downJSON(Constants.URL.CITY_SELECT, this);
    }

    /**
     * 城市JSON下载完成回调事件
     * @param url
     * @param obj
     */
    @Override
    public void onDownSucc(String url, Object obj) {
        if(obj != null){
            //JSON下载完成
            List<CitiyEntity> citysByJSON = JsonUtil.getCitysByJSON((String) obj);
            cityAdapter.setDatas(citysByJSON);
        }
    }

    /**
     * 城市列表点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CitiyEntity citiyEntity = (CitiyEntity) cityAdapter.getItem(position);
        int cityId = citiyEntity.getCityid();//获得被选中的城市ID
        String cityName = citiyEntity.getCityname();//获得被选中的城市名称

        //将选中的城市返回给上一个Activity
        Intent resultIntent = getIntent();
        resultIntent.putExtra(Constants.KEY_NAME.CITY_ID, cityId);
        resultIntent.putExtra(Constants.KEY_NAME.CITY_NAME, cityName);
        setResult(Constants.Code.SELECT_CITY_RESUEST, resultIntent);
        finish();
    }

    /**
     * 侧边控件选择回调事件
     * @param index
     * @param strs
     */
    @Override
    public void onSelected(int index, String strs) {
        labelView.setText(strs);

        /**
         * strs表示当前选中的字母
         * 我们需要通过这个字母找到该字母在ListView中所对应的Item的下标
         */
        int indx = cityAdapter.getindexByLabel(strs);
        if(indx != -1){
            lvCitys.smoothScrollToPositionFromTop(indx, 0);
        }
    }
}
