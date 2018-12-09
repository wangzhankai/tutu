package com.superpeer.tutuyoudian.activity.paypwd.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.paypwd.SuccessActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

import cloudist.cc.library.view.PasswordInputView;

public class PayPwdAgainSettingActivity extends BaseActivity<PayPwdAgainSettingPresenter, PayPwdAgainSettingModel> implements PayPwdAgainSettingContract.View {

    private PasswordInputView password_inputview;
    private TextView tvSure;
    private String pwd = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        pwd = getIntent().getStringExtra("pwd");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_pwd_again_setting;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提现密码设置");

        tvSure = (TextView) findViewById(R.id.tvSure);
        password_inputview = (PasswordInputView) findViewById(R.id.password_inputview);

        password_inputview.bindKeyBoard(getSupportFragmentManager(), "");

        //设置密码
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = password_inputview.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    showShortToast("请输入提现密码");
                    return;
                }
                if(!pwd.equals(password)){
                    showShortToast("两次密码输入不一致，请重新输入");
                    return;
                }
                mPresenter.setPayPwd(PreferencesUtils.getString(mContext, Constants.SHOP_ID), password, PreferencesUtils.getString(mContext, Constants.SHOP_ID), PreferencesUtils.getString(mContext, Constants.USER_TYPE));
            }
        });
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

    @Override
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    mRxManager.post("setPayPwd", "");
                    if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
                        PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
                        mRxManager.post("setSuccess", "");
                    }
                    Intent intent = new Intent(mContext, SuccessActivity.class);
                    intent.putExtra("desc", "提现密码设置成功");
                    startActivity(intent);
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
