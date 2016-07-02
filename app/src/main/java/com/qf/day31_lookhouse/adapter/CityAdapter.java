package com.qf.day31_lookhouse.adapter;

import android.content.Context;

import com.qf.day31_lookhouse.R;
import com.qf.day31_lookhouse.base.AbsBaseAdapter_more;
import com.qf.day31_lookhouse.model.CitiyEntity;

/**
 * 城市选择页面的适配器
 */
public class CityAdapter extends AbsBaseAdapter_more<CitiyEntity>{

    public CityAdapter(Context context) {
        super(context, R.layout.item_label, R.layout.item_city);
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public void bindDatas(AbsBaseAdapter_more<CitiyEntity>.ViewHolder viewHolder, CitiyEntity data, int position) {
        if(data.getType() == 0){
            viewHolder.bindTextView(R.id.tv_label, data.getCityname());
        } else if(data.getType() == 1){
            viewHolder.bindTextView(R.id.tv_city, data.getCityname());
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == 1?true:false;
    }

    /**
     * 根据标签获得该标签下的下标
     * @param label
     * @return
     */
    public int getindexByLabel(String label){
        for(int i = 0; i < datas.size(); i++){
            if(datas.get(i).eqLabel(label)){
                return i;
            }
        }
        return -1;
    }
}
