package com.qf.day31_lookhouse.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ken on 2016/2/15.
 */
public class ShareUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * 初始化共享参数
     */
    public static void initShared(Context context){
        sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void putString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key){
        return sharedPreferences.getString(key, null);
    }

    public static void putInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key){
        return sharedPreferences.getInt(key, -1);
    }
}
