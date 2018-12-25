package com.superpeer.tutuyoudian.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.listener.OnSureListener;

import java.util.ArrayList;
import java.util.List;

public class DialogUtils {

    public static void showDialog(Context mContext, String message, final OnSureListener onSureListener){
        //创建dialog构造器
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
        //设置title
        normalDialog.setTitle("提示");
        //设置icon
        normalDialog.setIcon(R.mipmap.ic_launcher);
        //设置内容
        normalDialog.setMessage(message);
        //设置按钮
        normalDialog.setPositiveButton("确定"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSureListener.onSure();
                        dialog.dismiss();
                    }
                });
        normalDialog.setNegativeButton("取消"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //创建并显示
        normalDialog.create().show();
    }
}
