package com.qf.day31_lookhouse.custem;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.LruCache;
import android.widget.ImageView;

import com.qf.day31_lookhouse.R;
import com.qf.day31_lookhouse.util.DownUtil;
import com.qf.day31_lookhouse.util.ImageUtil;

public class CacheImageView extends ImageView implements DownUtil.OnDownComplete {
	
	/**
	 * 一级缓存--磁盘缓存
	 * 参数：表示这个内存缓存最大的空间，通常我们给运行时内存1/8
	 */
	private static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)){

		/**
		 * lruCache内存满了以后，再往里面添加数据时，
		 * lruCache会按照最近时间最少使用的策略，进行数据移除，并且回调该方法
		 */
		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			if(evicted){
				//如果该数据从lrucache中被移除，则添加到软引用集合中
				softCache.put(key, new SoftReference<Bitmap>(oldValue));
			}
		}

		/**
		 * 计算缓存数据的大小
		 */
		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getHeight() * value.getRowBytes();
		}
	};
	
	/**
	 * java的四种引用
	 * 强引用：现在所用的所有引用都是强引用，如果程序运行时，内存不足的话，系统宁可抛出内存溢出的异常，也不会去回收强引用的对象
	 * 软引用：如果程序运行时，内存不足的话，会回收软引用对象
	 * 弱引用：无论内存紧不紧张，只要被GC发现，立刻回收
	 * 虚引用：其实没有真的引用，用于通知GC快点回收该资源
	 * @param context
	 * @param attrs
	 */
	private static Map<String, SoftReference<Bitmap>> softCache = new HashMap<String, SoftReference<Bitmap>>();
	

	public CacheImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	public void setUrl(String url){
		this.setTag(url);
		this.setImageResource(R.drawable.icon_logo);
		//从内存和磁盘中寻找
		Bitmap bitmap = getBitmap(url);
		if(bitmap == null){
			//下载
			DownUtil.downBitMap(url, this);
		} else {
			this.setImageBitmap(bitmap);
		}
	}
	
	/**
	 * 从内存中查找图片
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmap(String url){
		Bitmap bitmap = lruCache.get(url);
		if(bitmap == null){
			SoftReference<Bitmap> softBitmap = softCache.get(url);
			if(softBitmap != null){
				bitmap = softBitmap.get();
				if(bitmap != null){
					lruCache.put(url, bitmap);
					softCache.remove(url);
				}
			}
		}
		
		if(bitmap == null){
			bitmap = ImageUtil.getImage(url);
			if(bitmap != null){
				lruCache.put(url, bitmap);
			}
		}
		return bitmap;
	}
	
	/**
	 * 放入缓存的方法
	 * @param url
	 * @param bitmap
	 */
	public static void putBitmap(String url, Bitmap bitmap){
		if(url != null && bitmap != null){
			lruCache.put(url, bitmap);
			ImageUtil.saveImage(url, bitmap);
		}
	}


	/**
	 * 图片下载回调方法
	 */
	@Override
	public void onDownSucc(String url, Object obj) {
		if(obj != null){
			if(url.equals(this.getTag())){
				this.setImageBitmap((Bitmap)obj);
			} 
			
			//放入缓存
			putBitmap(url, (Bitmap)obj);
		}
	}
}
