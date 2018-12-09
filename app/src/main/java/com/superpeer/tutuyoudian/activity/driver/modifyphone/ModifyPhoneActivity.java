package com.superpeer.tutuyoudian.activity.driver.modifyphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class ModifyPhoneActivity extends BaseActivity<ModifyPhonePresenter, ModifyPhoneModel> implements ModifyPhoneContract.View {

    private TextView tvModify;
    private EditText etPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_phone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("修改手机号");

        etPhone = (EditText) findViewById(R.id.etPhone);
        tvModify = (TextView) findViewById(R.id.tvModify);

        tvModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                mPresenter.moidfyPhone(PreferencesUtils.getString(mContext, Constants.SHOP_ID), phone);
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
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
