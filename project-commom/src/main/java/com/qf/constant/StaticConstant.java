package com.qf.constant;

/**
 * @program: hz-shop
 * @description: 静态常量
 * @author: Mr.jiang
 * @create: 2020-03-10 18:16
 **/
public class StaticConstant {
    static String USERKEY ="user:id";

    public static String getUSERKEY() {
        return USERKEY;
    }

    public static void setUSERKEY(String USERKEY) {
        StaticConstant.USERKEY = USERKEY;
    }
}
