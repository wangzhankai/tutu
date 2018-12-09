package com.superpeer.tutuyoudian.activity.setpwd;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.utils.AppUtils;
import com.superpeer.base_libs.utils.MD5Util;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class SetPwdActivity extends BaseActivity<SetPwdPresenter, SetPwdModel> implements SetPwdContract.View {

    private TextView tvNext;
    private EditText etNewPwd;
    private EditText etPwdAgain;
    private String phone = "";
    private String code = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_pwd;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("设置新密码");

        etNewPwd = (EditText) findViewById(R.id.etNewPwd);
        etPwdAgain = (EditText) findViewById(R.id.etPwdAgain);
        tvNext = (TextView) findViewById(R.id.tvNext);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etNewPwd.getText().toString().trim();
                String pwdAgain = etPwdAgain.getText().toString().trim();

                if(TextUtils.isEmpty(pwd)){
                    showShortToast("请输入新密码");
                    return;
                }
                if(TextUtils.isEmpty(pwdAgain)){
                    showShortToast("请再次输入新密码");
                    return;
                }

                if(!pwd.equals(pwdAgain)){
                    showShortToast("两次密码不一致");
                    return;
                }

                mPresenter.resetPwd(phone, MD5Util.encrypt(pwd), code, AppUtils.getWLANMACAddress(mContext));
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
    public void showResetResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    startActivity(LoginActivity.class);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
