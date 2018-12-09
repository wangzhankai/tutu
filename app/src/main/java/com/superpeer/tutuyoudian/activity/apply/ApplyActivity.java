package com.superpeer.tutuyoudian.activity.apply;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.utils.AppUtils;
import com.superpeer.base_libs.utils.MD5Util;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.BaseApplication;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.WebActivity;
import com.superpeer.tutuyoudian.activity.store.StoreApplyActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

import cn.jpush.android.api.JPushInterface;

public class ApplyActivity extends BaseActivity<ApplyPresenter, ApplyModel> implements ApplyContract.View {

    private TextView tvNext;
    private EditText etPhone;
    private LinearLayout linearAgree;
    private ImageView ivAgree;
    private TextView tvAgree;
    private TextView tvGetCode;
    private EditText etCode;
    private EditText etPwd;
    private EditText etRepeatPwd;
    private EditText etInviteCode;

    private boolean isAgree;
    private TimeCount time;
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
        return R.layout.activity_apply;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺入驻");

        linearAgree = (LinearLayout) findViewById(R.id.linearAgree);
        ivAgree = (ImageView) findViewById(R.id.ivAgree);
        tvAgree = (TextView) findViewById(R.id.tvAgree);
        tvNext = (TextView) findViewById(R.id.tvNext);
        tvGetCode = (TextView) findViewById(R.id.tvGetCode);

        etPhone = (EditText) findViewById(R.id.etPhone);
        etCode = (EditText) findViewById(R.id.etCode);
        etPwd = (EditText) findViewById(R.id.etPwd);
        etRepeatPwd = (EditText) findViewById(R.id.etRepeatPwd);
        etInviteCode = (EditText) findViewById(R.id.etInviteCode);

        initListener();
    }

    private void initListener() {
        //服务条款
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("title", "兔兔优店用户服务协议");
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

        //获取验证码
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    showShortToast("请输入手机号");
                    return;
                }
                mPresenter.getCode("0", phone, AppUtils.getWLANMACAddress(mContext));

                /*//验证码倒计时
                if (time != null) {
                    time.cancel();
                }
                time = new TimeCount(60000, 1000);
                time.start();*/
            }
        });


        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                String passwordAgain = etRepeatPwd.getText().toString().trim();
                String inviteCode = etInviteCode.getText().toString().trim();

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

                mPresenter.register(phone, code, "0", MD5Util.encrypt(password), inviteCode, BaseApplication.getToken()==null? JPushInterface.getRegistrationID(mContext):BaseApplication.getToken());
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
    public void showAgreementResult(BaseBeanResult baseBeanResult) {
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
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showRegisterResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getCode()){
                    if(null!=baseBeanResult.getMsg()){
                        showShortToast(baseBeanResult.getMsg());
                    }
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
