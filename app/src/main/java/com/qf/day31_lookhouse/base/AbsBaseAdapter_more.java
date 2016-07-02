package com.qf.day31_lookhouse.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qf.day31_lookhouse.custem.CacheImageView;

/**
 * 多布局的适配器封装
 * @author Ken
 *
 * @param <T>
 */
public abstract class AbsBaseAdapter_more<T> extends BaseAdapter {

	private Context context;
	protected List<T> datas;
	private int[] resid;//getItemViewType方法的返回值，即为resid数组的下标
	
	public AbsBaseAdapter_more(Context context, int... resid){
		this.context = context;
		this.resid = resid;
		datas = new ArrayList();
	}
	
	public void setDatas(List<T> datas){
		this.datas = datas;
		this.notifyDataSetChanged();
	}
	
	public void addDatas(List<T> datas){
		this.datas.addAll(datas);
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	 public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView != null){
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(resid[getItemViewType(position)], null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		
		//进行赋值的操作
		bindDatas(viewHolder, datas.get(position), position);
		
		return convertView;
	}
	
	public abstract void bindDatas(ViewHolder viewHolder, T data, int position);

	
	
	/**
	 * 返回当前position所对应的Item的类型，必须从0开始，依次递增
	 * 1、谁继承谁实现
	 * 2、前提条件 -- 强制要求数据源中有一个type名称的属性，通过反射得到该属性
	 */
	@Override
	public int getItemViewType(int position) {
		T t = datas.get(position);
		Class cl = t.getClass();
		try {
			Field field = cl.getDeclaredField("type");
			field.setAccessible(true);//开始私有属性的使用权限
			return field.getInt(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return resid.length;
	}

	/**
	 * 创建ViewHolder
	 * @author Ken
	 *
	 */
	protected class ViewHolder{
		/**
		 * 该Map用于缓存布局中的控件
		 */
		Map<Integer, View> cacheMap = new HashMap();
		View layoutView;
		
		/**
		 * 该构造参数 需要传递一个布局对象过来
		 * @param layoutView
		 */
		public ViewHolder(View layoutView){
			this.layoutView = layoutView;
		}
		
		/**
		 * 通过控件ID 找到该控件
		 * @param id
		 * @return
		 */
		public View getView(int id){
			if(cacheMap.containsKey(id)){
				return cacheMap.get(id);
			} else {
				View view = layoutView.findViewById(id);
				cacheMap.put(id, view);
				return view;
			}
		}
		
		/**
		 * 绑定TextView
		 * @param id
		 * @param text
		 * @return
		 */
		public ViewHolder bindTextView(int id, String text){
			TextView tv = (TextView) getView(id);
			tv.setText(text);
			return this;
		}
		
		/**
		 * 绑定CacheImageView
		 * @param id
		 * @param url
		 * @return
		 */
		public ViewHolder bindCacheImageView(int id, String url){
			CacheImageView civ = (CacheImageView) getView(id);
			civ.setUrl(url);
			return this;
		}
	}
}
