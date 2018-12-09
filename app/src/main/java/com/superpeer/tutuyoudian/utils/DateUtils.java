package com.superpeer.tutuyoudian.utils;

import com.superpeer.base_libs.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class DateUtils {

    public static String getCurrentYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String getUUIDFileName() {
        return DateUtils.getCurrentYMD() + "_" + MD5Util.encrypt(UUID.randomUUID().toString());
    }
}
