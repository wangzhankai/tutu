package com.superpeer.tutuyoudian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

    public static Long getTime(String time){

        long timeStr = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        try {
            timeStr = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(timeStr>currentTime){
            return (timeStr - currentTime);
        }else{
            return (currentTime - timeStr);
        }
//        return timeStr;
    }

}