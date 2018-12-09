package com.superpeer.tutuyoudian.activity.modifypwd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.utils.MD5Util;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class ModifyPwdActivity extends BaseActivity<ModifyPwdPresenter, ModifyPwdModel> implements ModifyPwdContract.View {

    private EditText etPwd;
    private EditText etNewPwd;
    private TextView tvSure;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("登录密码修改");

        etPwd = (EditText) findViewById(R.id.etPwd);
        etNewPwd = (EditText) findViewById(R.id.etNewPwd);
        tvSure = (TextView) findViewById(R.id.tvSure);

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etPwd.getText().toString().trim();
                String newPwd = etNewPwd.getText().toString().trim();

                if(TextUtils.isEmpty(pwd)){
                    showShortToast("请输入原密码");
                    return;
                }
                if(TextUtils.isEmpty(newPwd)){
                    showShortToast("请输入新密码");
                    return;
                }
                mPresenter.updatePassword(PreferencesUtils.getString(mContext, Constants.SHOP_ID), MD5Util.encrypt(pwd), MD5Util.encrypt(newPwd), PreferencesUtils.getString(mContext, Constants.SHOP_ID), PreferencesUtils.getString(mContext, Constants.USER_TYPE));
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
                    PreferencesUtils.putString(mContext, Constants.SHOP_ID, "");
                    PreferencesUtils.putString(mContext, Constants.USER_TYPE, "");
                    AppManager.getAppManager().finishAllActivity();
                    startActivity(LoginActivity.class);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
