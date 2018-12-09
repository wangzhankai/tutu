package com.superpeer.base_libs.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Toast统一管理类
 */
public class ToastUitl {


    private static Toast toast;
    private static Toast toast2;

    private static Toast initToast(Context mContext, CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(mContext, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(Context mContext, CharSequence message) {
        initToast(mContext, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(Context mContext, int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(mContext, mContext.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(Context mContext,CharSequence message) {
        initToast(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(Context mContext,int strResId) {
        initToast(mContext, mContext.getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(Context mContext,CharSequence message, int duration) {
        initToast(mContext, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context, context.getResources().getText(strResId), duration).show();
    }

//    /**
//     * 显示有image的toast
//     *
//     * @param tvStr
//     * @param imageResource
//     * @return
//     */
//    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
//        if (toast2 == null) {
//            toast2 = new Toast(BaseApplication.getAppContext());
//        }
//        View view = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.toast_custom, null);
//        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
//        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
//        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
//        if (imageResource > 0) {
//            iv.setVisibility(View.VISIBLE);
//            iv.setImageResource(imageResource);
//        } else {
//            iv.setVisibility(View.GONE);
//        }
//        toast2.setView(view);
//        toast2.setGravity(Gravity.CENTER, 0, 0);
//        toast2.show();
//        return toast2;
//
//    }
}
