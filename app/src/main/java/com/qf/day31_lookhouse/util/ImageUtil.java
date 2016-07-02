package com.qf.day31_lookhouse.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Ken on 2016/2/15.
 */
public class ImageUtil {
    private static final String IMAGE_CACHE_PATH = Environment.getExternalStorageDirectory() + "/image_cache/images";

    /**
     * 判断是否有拓展卡
     * @return
     */
    public static boolean isMounted(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 将图片写入磁盘
     * @param url
     * @param bitmap
     */
    public static void saveImage(String url, Bitmap bitmap){
        if(!isMounted()){
            return;
        }

        File file = new File(IMAGE_CACHE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File(file, "" + url.hashCode())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从磁盘中获取图片
     * @param url
     * @return
     */
    public static Bitmap getImage(String url){
        if(!isMounted()){
            return null;
        }

        File file = new File(IMAGE_CACHE_PATH, "" + url.hashCode());
        if(file.exists()){
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        return null;
    }
}
