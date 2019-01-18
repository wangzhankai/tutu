package com.superpeer.tutuyoudian.api;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.baserx.ServerException;
import com.superpeer.base_libs.utils.ConstantsUtils;
import com.superpeer.base_libs.utils.NetWorkUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.LoadingDialog;
import com.superpeer.tutuyoudian.BaseApplication;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.activity.welcome.WelcomeActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

import rx.Subscriber;

/**
 * 订阅封装
 * Created by wangzhankai on 2018/2/22.
 */

/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/

public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog=true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(com.superpeer.base_libs.R.string.loading),true);
    }
    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(com.superpeer.base_libs.R.string.loading),showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading((Activity) mContext,msg,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(com.superpeer.base_libs.R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }else if(e.getLocalizedMessage().contains("403")) {
            try {
                if(!ConstantsUtils.isActivityTop(mContext, WelcomeActivity.class)&&!ConstantsUtils.isActivityTop(mContext, LoginActivity.class)){
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    AppManager.getAppManager().finishAllActivity();
                    PreferencesUtils.putString(mContext, Constants.SHOP_ID, "");
                    PreferencesUtils.putString(mContext, Constants.USER_TYPE, "");
                }
            } catch (Exception ep) {
                ep.printStackTrace();
            }
        }
        //其它
        else {
            _onError(BaseApplication.getAppContext().getString(com.superpeer.base_libs.R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}

