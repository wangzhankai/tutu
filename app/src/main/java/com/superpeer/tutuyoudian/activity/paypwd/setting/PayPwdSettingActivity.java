package com.superpeer.tutuyoudian.activity.paypwd.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;

import cloudist.cc.library.view.PasswordInputView;
import rx.functions.Action1;

public class PayPwdSettingActivity extends BaseActivity {

    private PasswordInputView password_inputview;
    private TextView tvSure;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_pwd_setting;
    }

    @Override
    public void initPresenter() {

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
                Intent intent = new Intent(mContext, PayPwdAgainSettingActivity.class);
                intent.putExtra("pwd", password);
                startActivity(intent);
            }
        });

        mRxManager.on("setPayPwd", new Action1<String>() {
            @Override
            public void call(String s) {
                finish();
            }
        });
    }

}
