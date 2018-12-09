package com.superpeer.base_libs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class DateUtils {

    public static String getCurrentYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String getUUIDFileName() {
        return DateUtils.getCurrentYMD() + "_" + MD5Util.encrypt(UUID.randomUUID().toString());
    }

    public static String getStringToDate(String dateString, String pattern) {
        Date date = new Date(Long.parseLong(dateString));
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /*public static String getStringToDate(String dateString, String pattern) {
        *//*SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();*//*
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return sdf.format(new Date(Long.parseLong(dateString)));
    }*/
}
