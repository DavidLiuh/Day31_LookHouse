package com.qf.day31_lookhouse.util;

/**
 * 常量接口
 */
public interface Constants {

    /**
     * 请求码常量
     */
    interface Code{
        int SELECT_CITY_REQUEST = 0x11;//启动选择城市页面时的请求码
        int SELECT_CITY_RESUEST = 0x12;//选择城市以后的返回请求码
    }

    /**
     * 接口链接常量
     */
    interface URL{
        String CITY_SELECT = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&act=kftcitylistnew&channel=71&devid=866500021200250&appname=QQHouse&mod=appkft";
    }

    /**
     * 常量名
     */
    interface KEY_NAME{
        String CITY_ID = "cityId";//城市ID
        String CITY_NAME = "cityName";//城市名称
    }
}
