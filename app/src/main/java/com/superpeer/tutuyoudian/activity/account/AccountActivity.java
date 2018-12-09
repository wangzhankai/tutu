package com.superpeer.tutuyoudian.activity.account;

import android.view.View;

import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;

public class AccountActivity extends BaseActivity<AccountPresenter, AccountModel> implements AccountContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("选择账户");

        setToolBarViewStubImageRes(R.mipmap.iv_collage_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
