package com.superpeer.tutuyoudian.activity.forgot;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.utils.AppUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.setpwd.SetPwdActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class ForgotActivity extends BaseActivity<ForgotPresenter, ForgotModel> implements ForgotContract.View {

    private TextView tvGetCode;
    private EditText etUserName;
    private EditText etCode;
    private TextView tvNext;

    private TimeCount time;

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
        return R.layout.activity_forgot;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("忘记密码");

        tvGetCode = (TextView) findViewById(R.id.tvGetCode);
        tvNext = (TextView) findViewById(R.id.tvNext);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etCode = (EditText) findViewById(R.id.etCode);

        initListener();
    }

    private void initListener() {
        //下一步
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etUserName.getText().toString().trim();
                String code = etCode.getText().toString().trim();

                if(TextUtils.isEmpty(phone)){
                    showShortToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showShortToast("请输入验证码");
                    return;
                }
                mPresenter.check(phone, code, "1");
            }
        });
        //获取验证码
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etUserName.getText().toString().trim();

                if(TextUtils.isEmpty(phone)){
                    showShortToast("请输入手机号");
                    return;
                }

                mPresenter.getCode(phone, "1", AppUtils.getWLANMACAddress(mContext));

                /*//验证码倒计时
                if (time != null) {
                    time.cancel();
                }
                time = new TimeCount(60000, 1000);
                time.start();*/
            }
        });

        etUserName.addTextChangedListener(new TextWatcher() {
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
                String name = etUserName.getText().toString().trim();
                if(!TextUtils.isEmpty(s)){
                    if(!TextUtils.isEmpty(name)){
                        tvNext.setBackgroundResource(R.drawable.bg_orange);
                    }
                }
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
    public void showCheckResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult) {
                if ("1".equals(baseBeanResult.getCode())) {
                    Intent intent = new Intent(mContext, SetPwdActivity.class);
                    intent.putExtra("phone", etUserName.getText().toString().trim());
                    intent.putExtra("code", etCode.getText().toString().trim());
                    startActivity(intent);
                }
                if (null != baseBeanResult.getMsg()) {
                    showShortToast(baseBeanResult.getMsg());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
