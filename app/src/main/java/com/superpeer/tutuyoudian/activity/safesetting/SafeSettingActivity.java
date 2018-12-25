package com.superpeer.tutuyoudian.activity.safesetting;

import android.view.View;
import android.widget.LinearLayout;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.paytype.DriverTypeActivity;
import com.superpeer.tutuyoudian.activity.forgotpaypwd.ForgotPayPwdActivity;
import com.superpeer.tutuyoudian.activity.modifypwd.ModifyPwdActivity;
import com.superpeer.tutuyoudian.activity.paypwd.modify.verify.PayPwdModifyActivity;
import com.superpeer.tutuyoudian.activity.paypwd.setting.PayPwdSettingActivity;
import com.superpeer.tutuyoudian.activity.paytype.PayTypeActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;

import rx.functions.Action1;

public class SafeSettingActivity extends BaseActivity{

    private LinearLayout linearPayType;
    private LinearLayout linearPayPwdSetting;
    private LinearLayout linearPayPwdModify;
    private LinearLayout linearLoginPwdModify;
    private LinearLayout linearForgotPayPwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_safe_setting;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        setHeadTitle("安全设置");

        linearPayType = (LinearLayout) findViewById(R.id.linearPayType);
        linearPayPwdSetting = (LinearLayout) findViewById(R.id.linearPayPwdSetting);
        linearPayPwdModify = (LinearLayout) findViewById(R.id.linearPayPwdModify);
        linearLoginPwdModify = (LinearLayout) findViewById(R.id.linearLoginPwdModify);
        linearForgotPayPwd = (LinearLayout) findViewById(R.id.linearForgotPayPwd);

        initData();

        initRxBus();
        initListener();
    }

    private void initData() {
        BaseObject bean = getUserInfo();
        if(null!=bean){
            if(null!=bean.getPayStatus()){
                if("1".equals(bean.getPayStatus())){
                    linearPayPwdSetting.setVisibility(View.GONE);
                    linearPayPwdModify.setVisibility(View.VISIBLE);
                }else{
                    linearPayPwdSetting.setVisibility(View.VISIBLE);
                    linearPayPwdModify.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initRxBus() {
        mRxManager.on("setSuccess", new Action1<String>() {
            @Override
            public void call(String s) {
                initData();
            }
        });
    }

    private void initListener() {
        //结算方式设置
        linearPayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("0".equals(PreferencesUtils.getString(mContext, Constants.USER_TYPE))){
                    startActivity(PayTypeActivity.class);
                }else{
                    startActivity(DriverTypeActivity.class);
                }
            }
        });
        //支付密码设置
        linearPayPwdSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PayPwdSettingActivity.class);
            }
        });
        //支付密码修改
        linearPayPwdModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PayPwdModifyActivity.class);
            }
        });
        //登录密码修改
        linearLoginPwdModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ModifyPwdActivity.class);
            }
        });
        //忘记支付密码
        linearForgotPayPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ForgotPayPwdActivity.class);
            }
        });
    }
}
