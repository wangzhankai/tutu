package com.superpeer.tutuyoudian.activity.driver.register;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.utils.AppUtils;
import com.superpeer.base_libs.utils.MD5Util;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.WebActivity;
import com.superpeer.tutuyoudian.activity.forgot.ForgotActivity;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class DriverRegisterActivity extends BaseActivity<DriverRegisterPresenter, DriverRegisterModel> implements DriverRegisterContract.View {

    private EditText etPhone;
    private EditText etCode;
    private TextView tvGetCode;
    private EditText etPwd;
    private EditText etRepeatPwd;
    private ImageView ivAgree;
    private TextView tvAgree;
    private TextView tvNext;
    private TimeCount time;

    private boolean isAgree;
    private LinearLayout linearAgree;
    private String agreeContent = "";

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tvGetCode.setText("发送验证码");
            tvGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tvGetCode.setClickable(false);
            tvGetCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_register;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("跑腿注册");

        linearAgree = (LinearLayout) findViewById(R.id.linearAgree);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCode = (EditText) findViewById(R.id.etCode);
        tvGetCode = (TextView) findViewById(R.id.tvGetCode);
        etPwd = (EditText) findViewById(R.id.etPwd);
        etRepeatPwd = (EditText) findViewById(R.id.etRepeatPwd);
        ivAgree = (ImageView) findViewById(R.id.ivAgree);
        tvAgree = (TextView) findViewById(R.id.tvAgree);
        tvNext = (TextView) findViewById(R.id.tvNext);

        mPresenter.getAgree("3");

        initListener();

    }

    private void initListener() {
        //服务条款
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("title", "兔兔优店跑腿服务协议");
                intent.putExtra("content", agreeContent);
                startActivity(intent);
            }
        });
        //
        linearAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAgree = !isAgree;
                if(isAgree){
                    ivAgree.setImageResource(R.mipmap.iv_agree);
                }else{
                    ivAgree.setImageResource(R.mipmap.iv_noagree);
                }
            }
        });
        /**
         * 获取验证码
         */
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();

                if(TextUtils.isEmpty(phone)){
                    showShortToast("请输入手机号");
                    return;
                }

                mPresenter.getCode(phone, "3", AppUtils.getWLANMACAddress(mContext));

                /*
                if (time != null) {
                    time.cancel();
                }
                time = new TimeCount(60000, 1000);
                time.start();*/
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String code = etCode.getText().toString().trim();
                if(!TextUtils.isEmpty(s)){
                    if(!TextUtils.isEmpty(code)){
                        tvNext.setBackgroundResource(R.drawable.bg_orange);
                    }
                }
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = etPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(s)){
                    if(!TextUtils.isEmpty(name)){
                        tvNext.setBackgroundResource(R.drawable.bg_orange);
                    }
                }
            }
        });

        /**
         * 注册
         */
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                String passwordAgain = etRepeatPwd.getText().toString().trim();

                if(TextUtils.isEmpty(phone)){
                    showShortToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showShortToast("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    showShortToast("请输入密码");
                    return;
                }
                if(password.length()<6){
                    showShortToast("请输入6位以上密码");
                    return;
                }
                if(TextUtils.isEmpty(passwordAgain)){
                    showShortToast("请再次输入密码");
                    return;
                }
                if(!password.equals(passwordAgain)){
                    showShortToast("两次密码输入不一致");
                    return;
                }
                if(!isAgree){
                    showShortToast("请先同意服务协议");
                    return;
                }

                mPresenter.register(phone, MD5Util.encrypt(password), code, "3");

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

    /**
     * 密码显示或隐藏 （切换）
     */
    private void showOrHide(EditText etPassword){
        //记住光标开始的位置
        int pos = etPassword.getSelectionStart();
        if(etPassword.getInputType()!= (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)){//隐藏密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{//显示密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        etPassword.setSelection(pos);

    }

    @Override
    public void showAgree(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getObject()){
                        if(null!=baseBeanResult.getData().getObject().getAgreementContent()){
                            agreeContent = baseBeanResult.getData().getObject().getAgreementContent();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showCodeResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult&&null!=baseBeanResult.getMsg()){
                showShortToast(baseBeanResult.getMsg());
            }
            if("1".equals(baseBeanResult.getCode())){
                //验证码倒计时
                if (time != null) {
                    time.cancel();
                }
                time = new TimeCount(60000, 1000);
                time.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showRegisterResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if(null!=baseBeanResult.getCode()){
                    if("1".equals(baseBeanResult.getCode())){
                        mRxManager.post("loginPhone", etPhone.getText().toString().trim());
                        mRxManager.post("password", etPwd.getText().toString().trim());
                        finish();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
