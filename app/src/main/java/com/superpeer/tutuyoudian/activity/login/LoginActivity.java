package com.superpeer.tutuyoudian.activity.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.google.gson.Gson;
import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.utils.AppUtils;
import com.superpeer.base_libs.utils.MD5Util;
import com.superpeer.base_libs.utils.MPermissionUtils;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.base_libs.view.LoadingDialog;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.apply.ApplyActivity;
import com.superpeer.tutuyoudian.activity.driver.main.DriverMainActivity;
import com.superpeer.tutuyoudian.activity.driver.register.DriverRegisterActivity;
import com.superpeer.tutuyoudian.activity.forgot.ForgotActivity;
import com.superpeer.tutuyoudian.activity.info.StoreInfoActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.activity.store.StoreApplyActivity;
import com.superpeer.tutuyoudian.activity.storesendset.StoreSendSetActivity;
import com.superpeer.tutuyoudian.activity.storeuse.StoreUseActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;

import rx.functions.Action1;

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {

    private TextView tvForgot;
    private TextView tvApply;
    private TextView tvLogin;
    private EditText etUserName;
    private EditText etPassword;
    private TextView tvDriverRegister;

    //权限
    public String[] permissions = {
            //定位需要
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,

    };

    @Override
    protected boolean hasHeadTitle() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        tvApply = (TextView) findViewById(R.id.tvApply);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvDriverRegister = (TextView) findViewById(R.id.tvDriverRegister);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        initListener();

        initRxBus();

        initPermission();

        mPresenter.update("0");

    }

    private void initPermission() {
        MPermissionUtils.requestPermissionsResult((Activity) mContext, 1, permissions, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied() {
                MPermissionUtils.showTipsDialog(mContext);
            }
        });
    }

    private void initRxBus() {
        mRxManager.on("loginPhone", new Action1<String>() {
            @Override
            public void call(String s) {
                etUserName.setText(s);
            }
        });

        mRxManager.on("password", new Action1<String>() {
            @Override
            public void call(String s) {
                etPassword.setText(s);
            }
        });

    }

    private void initListener() {
        //忘记密码
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ForgotActivity.class);
            }
        });
        //入驻申请
        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ApplyActivity.class);
            }
        });

        //骑手注册
        tvDriverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverRegisterActivity.class);
            }
        });
        //登录
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUserName.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    showShortToast("请输入用户名");
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    showShortToast("请输入密码");
                    return;
                }

                mPresenter.login(name, MD5Util.encrypt(pwd), PreferencesUtils.getString(mContext, Constants.JPUSH_REGISTER_ID));
            }
        });
    }

    @Override
    public void showLoading(String title) {
        LoadingDialog.showDialogForLoading((Activity) mContext, title, true);
    }

    @Override
    public void stopLoading() {
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void showLoginResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
                        PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
                        PreferencesUtils.putString(mContext, Constants.IS_IDENTIFY, baseBeanResult.getData().getObject().getState());
                        PreferencesUtils.putString(mContext, Constants.SHOP_NOTICE, baseBeanResult.getData().getObject().getContent());
                        PreferencesUtils.putString(mContext, Constants.ACCOUNT_ID, baseBeanResult.getData().getObject().getAccountId());
                        PreferencesUtils.putString(mContext, Constants.USER_TYPE, baseBeanResult.getData().getObject().getRoleType());
                        if(null!=baseBeanResult.getData().getObject().getRoleType()){
                            if ("0".equals(baseBeanResult.getData().getObject().getRoleType())) {
                                PreferencesUtils.putString(mContext, Constants.SHOP_ID, baseBeanResult.getData().getObject().getShopId());
                                PreferencesUtils.putString(mContext, Constants.SHOP_STATE, baseBeanResult.getData().getObject().getState());
                                if(null!=baseBeanResult.getData().getObject().getState()){
                                    if("0".equals(baseBeanResult.getData().getObject().getState())){
                                        Intent intent = new Intent(mContext, StoreApplyActivity.class);
                                        intent.putExtra("type", "1");
                                        startActivity(intent);
                                    }else if("1".equals(baseBeanResult.getData().getObject().getState())){
                                        Intent intent = new Intent(mContext, StoreSendSetActivity.class);
                                        intent.putExtra("type", "1");
                                        startActivity(intent);
                                    }else if("2".equals(baseBeanResult.getData().getObject().getState())){
                                        Intent intent = new Intent(mContext, StoreInfoActivity.class);
                                        intent.putExtra("type", "1");
                                        startActivity(intent);
                                    }else if("3".equals(baseBeanResult.getData().getObject().getState())){
                                        Intent intent = new Intent(mContext, StoreUseActivity.class);
                                        intent.putExtra("type", "1");
                                        startActivity(intent);
                                    }else{
                                        startActivity(MainActivity.class);
                                        AppManager.getAppManager().finishAllActivity();
                                    }
                                }
                            }else{
                                PreferencesUtils.putString(mContext, Constants.SHOP_ID, baseBeanResult.getData().getObject().getId());
                                startActivity(DriverMainActivity.class);
                                AppManager.getAppManager().finishAllActivity();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUpdate(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    toUpdate(baseBeanResult);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void toUpdate(final BaseBeanResult baseBeanResult) {
        try {
            BaseObject object = baseBeanResult.getData().getObject();
                    //判断版本号
                    if (AppUtils.getLocalVersion(mContext) < Integer.parseInt(object.getVersionName())) {
//                        AllenVersionChecker.getInstance().downloadOnly(
//                                UIData.create().setTitle(object.getVersionNumber()).setContent("版本更新")
//                                        .setDownloadUrl(object.getVersionSrc())).excuteMission(this);
//                                        .setDownloadUrl("https://imtt.dd.qq.com/16891/D21910E083EA4C497C5BD59A76C5577B.apk?fsname=com.tencent.mm_6.7.3_1360.apk&csr=1bbd")).excuteMission(this);
                        DownloadBuilder builder = AllenVersionChecker.getInstance().downloadOnly(
                                UIData.create().setTitle(object.getVersionNumber()).setContent("版本更新")
                                        .setDownloadUrl(object.getVersionSrc()));
                                builder.excuteMission(this);
                                builder.setForceUpdateListener(new ForceUpdateListener() {
                                    @Override
                                    public void onShouldForceUpdate() {

                                    }
                                });
                    } /*else {
                        showShortToast("当前版本为最新版本");
                    }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
