package com.qf.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author:jiang
 * @Date:2019/12/18 14:33
 * @introduction:介绍
 */
public class Creatno {

    public static String creatnumber(){
        //1.当前时间的时分秒
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String format = sdf.format(new Date());
        //随机数
        int i = (int) (Math.random() * 10000);

        return i+format;
    }


}
