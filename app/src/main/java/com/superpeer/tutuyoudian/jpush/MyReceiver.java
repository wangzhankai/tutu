package com.superpeer.tutuyoudian.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.main.DriverMainActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.PushBean;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.SystemTTS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Log.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                PreferencesUtils.putString(context, Constants.JPUSH_REGISTER_ID, regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.i(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 用户点击打开了通知");

                /*//打开自定义的Activity
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);*/

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.i(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e){

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {

        if("0".equals(PreferencesUtils.getString(context, Constants.USER_TYPE))){
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (BaseActivity.isForeground) {
                Intent msgIntent = new Intent(BaseActivity.MESSAGE_RECEIVED_ACTION);
                msgIntent.putExtra(BaseActivity.KEY_MESSAGE, message);
                msgIntent.putExtra(BaseActivity.KEY_EXTRAS, extras);
                LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
                /*PushBean bean = new Gson().fromJson(extras, PushBean.class);
                if(null!=bean&&null!=bean.getOrderType()&&"1".equals(bean.getOrderType())){
                    NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notification = new Notification();
                    notification.sound= Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification);
                }*/
            }else{
                Intent msgIntent = new Intent(BaseActivity.MESSAGE_RECEIVED_ACTION);
                msgIntent.putExtra(BaseActivity.KEY_MESSAGE, message);
                msgIntent.putExtra(BaseActivity.KEY_EXTRAS, extras);
                LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
            }
        }else{
            if (BaseActivity.isForeground) {
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String message = bundle.getString(JPushInterface.EXTRA_ALERT);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Intent msgIntent = new Intent(BaseActivity.MESSAGE_RECEIVED_ACTION);
                msgIntent.putExtra(BaseActivity.KEY_MESSAGE, message);
                msgIntent.putExtra(BaseActivity.KEY_EXTRAS, extras);
                LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
            }
        }
    }
}