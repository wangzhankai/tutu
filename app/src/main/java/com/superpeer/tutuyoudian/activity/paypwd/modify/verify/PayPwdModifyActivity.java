package com.superpeer.tutuyoudian.activity.paypwd.modify.verify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.paypwd.modify.PayPwdModifyAgainActivity;
import com.superpeer.tutuyoudian.activity.paypwd.setting.PayPwdAgainSettingActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

import cloudist.cc.library.view.PasswordInputView;
import rx.functions.Action1;

public class PayPwdModifyActivity extends BaseActivity<PayPwdModifyPresenter, PayPwdModifyModel> implements PayPwdModifyContract.View {

    private PasswordInputView password_inputview;
    private TextView tvSure;
    private String password = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_pwd_modify;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提现密码修改");

        tvSure = (TextView) findViewById(R.id.tvSure);
        password_inputview = (PasswordInputView) findViewById(R.id.password_inputview);

        password_inputview.bindKeyBoard(getSupportFragmentManager(), "");

        //设置密码
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = password_inputview.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    showShortToast("请输入提现密码");
                    return;
                }
                mPresenter.checkPayPwd(PreferencesUtils.getString(mContext, Constants.SHOP_ID), password, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PreferencesUtils.getString(mContext, Constants.USER_TYPE));
            }
        });

        mRxManager.on("modifyPayPwd", new Action1<String>() {
            @Override
            public void call(String s) {
                finish();
            }
        });
    }

    @Override
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    Intent intent = new Intent(mContext, PayPwdModifyAgainActivity.class);
                    intent.putExtra("pwd", password);
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
