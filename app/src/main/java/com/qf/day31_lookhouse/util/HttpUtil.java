package com.qf.day31_lookhouse.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ken on 2016/2/15.
 */
public class HttpUtil {

    private static byte[] getBytesByURL(String urlstr){
        try {
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] bs = new byte[1024 * 10];
            while((len = in.read(bs)) != -1){
                out.write(bs, 0, len);
            }

            in.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 下载JSON的方法
     * @param str
     * @return
     */
    public static String getJSONByURL(String str){
        byte[] bs = getBytesByURL(str);
        if(bs != null){
            try {
                return new String(bs, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * 下载图片
     * @param str
     * @return
     */
    public static Bitmap getBitmapByURL(String str){
        byte[] bs = getBytesByURL(str);
        if(bs != null){
            try {
                return new BitmapFactory().decodeByteArray(bs, 0, bs.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
