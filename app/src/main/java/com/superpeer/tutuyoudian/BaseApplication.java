package com.superpeer.tutuyoudian;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.constant.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication;

    //极光推送registerId
    private String registerId;
    private static String token;
    private IWXAPI msgApi;

    public static String getToken() {
        return token;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        token = JPushInterface.getRegistrationID(getAppContext());
        PreferencesUtils.putString(getAppContext(), Constants.JPUSH_REGISTER_ID, token);

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5b8a3db7b27b0a659700004f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        //开启ShareSDK debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.isUmengWx = true;

        PlatformConfig.setWeixin(Constants.APP_ID, Constants.APP_SECRET);

    }

    public static Context getAppContext() { return baseApplication; }

}
