package com.qf.day31_lookhouse.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * 下载工具类
 * @author Ken
 *
 */
public class DownUtil {
	
	/**
	 * 创建一个线程池，共有5条线程
	 */
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	private static Handler handler = new Handler();
	
	/**
	 * 下载JSON
	 * @param strurl
	 * @param onDownComplete
	 */
	public static void downJSON(final String strurl, final OnDownComplete onDownComplete){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				//子线程中执行，被线程池管理
				final String json = HttpUtil.getJSONByURL(strurl);
				handler.post(new Runnable() {
					@Override
					public void run() {
						onDownComplete.onDownSucc(strurl, json);
					}
				});
			}
		});
	}
	
	/**
	 * 下载BitMap
	 * @param strurl
	 * @param onDownComplete
	 */
	public static void downBitMap(final String strurl, final OnDownComplete onDownComplete){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				//子线程中执行，被线程池管理
				final Bitmap bitmap = HttpUtil.getBitmapByURL(strurl);
				handler.post(new Runnable() {
					@Override
					public void run() {
						onDownComplete.onDownSucc(strurl, bitmap);
					}
				});
			}
		});
	}
	
	public interface OnDownComplete{
		void onDownSucc(String url, Object obj);
	}
}
