package com.superpeer.base_libs.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class ConstantsUtils {

    public static void callPhone(Context mContext, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }
    /**
     * 手机号判断
     */
    public static boolean isMobileNO(String mobile) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;

    }

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
    public static boolean isIdentifyNum(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * *
     * * 判断某activity是否处于栈顶
     * * @return  true在栈顶 false不在栈顶
     * */
    public static boolean isActivityTop(Context context, Class cls){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }
}
