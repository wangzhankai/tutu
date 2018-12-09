package com.superpeer.tutuyoudian.activity.paypwd.modify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.paypwd.SuccessActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

import cloudist.cc.library.view.PasswordInputView;

public class PayPwdModifyAgainActivity extends BaseActivity<PayPwdModifyAgainPresenter, PayPwdModifyAgainModel> implements PayPwdModifyAgainContract.View {

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
        return R.layout.activity_pay_pwd_modify_again;
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
                String password = password_inputview.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    showShortToast("请输入提现密码");
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
                    mRxManager.post("modifyPayPwd", "");
                    Intent intent = new Intent(mContext, SuccessActivity.class);
                    intent.putExtra("desc", "提现密码修改成功");
                    startActivity(intent);
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
