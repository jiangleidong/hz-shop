package com.qf.util;

/**
 * @program: hz-shop
 * @description: 拼接字符串的工具类
 * @author: Mr.jiang
 * @create: 2020-03-10 18:27
 **/
public class StringAppend {

    public static String getnewString(String a,String b){
        return new StringBuilder().append(a).append(b).toString();
    }


    public static String getnewString(String a,Long b,String c){
        return new StringBuilder().append(a).append(b).append(c).toString();
    }

    public static String getnewString(String a,Long b){
        return new StringBuilder().append(a).append(b).toString();
    }
}
