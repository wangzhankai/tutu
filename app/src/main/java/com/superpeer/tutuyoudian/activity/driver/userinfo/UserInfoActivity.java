package com.superpeer.tutuyoudian.activity.driver.userinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.modifyphone.ModifyPhoneActivity;
import com.superpeer.tutuyoudian.activity.driver.qrcode.QrCodeActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseObject;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter, UserInfoModel> implements UserInfoContract.View {

    private LinearLayout linearQrCode;
    private TextView tvAccountNum;
    private TextView tvUserName;
    private TextView tvPhone;
    private LinearLayout linearPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("个人信息");

        linearQrCode = (LinearLayout) findViewById(R.id.linearQrCode);
        linearPhone = (LinearLayout) findViewById(R.id.linearPhone);
        tvAccountNum = (TextView) findViewById(R.id.tvAccountNum);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);

        BaseObject bean = getUserInfo();
        if(null!=bean){
            initData(bean);
        }

        initListener();
    }

    private void initData(BaseObject bean) {
        if(null!=bean.getLoginName()){
            tvAccountNum.setText(bean.getLoginName());
        }
        if(null!=bean.getUserName()){
            tvUserName.setText(bean.getUserName());
        }
        if(null!=bean.getPhone()){
            tvPhone.setText(bean.getPhone());
        }
    }

    private void initListener() {
        //二维码
        linearQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(QrCodeActivity.class);
            }
        });
        //修改手机号
        linearPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ModifyPhoneActivity.class);
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
}
