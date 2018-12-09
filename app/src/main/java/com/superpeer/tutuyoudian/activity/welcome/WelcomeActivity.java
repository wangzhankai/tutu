package com.superpeer.tutuyoudian.activity.welcome;

import android.os.Handler;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.main.DriverMainActivity;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;

public class WelcomeActivity extends BaseActivity<WelcomePresenter, WelcomeModel> implements WelcomeContract.View {

    @Override
    protected boolean hasHeadTitle() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.getAgreement("0");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(null!=PreferencesUtils.getString(mContext, Constants.SHOP_ID)&&!"".equals(PreferencesUtils.getString(mContext, Constants.SHOP_ID))){
                    if(null!=PreferencesUtils.getString(mContext, Constants.USER_TYPE)&&!"".equals(PreferencesUtils.getString(mContext, Constants.USER_TYPE))){
                        if("0".equals(PreferencesUtils.getString(mContext, Constants.USER_TYPE))){
                            if(null!=PreferencesUtils.getString(mContext, Constants.SHOP_STATE)&&!"".equals(PreferencesUtils.getString(mContext, Constants.SHOP_STATE))){
                                if("4".equals(PreferencesUtils.getString(mContext, Constants.SHOP_STATE))){
                                    startActivity(MainActivity.class);
                                }else{
                                    startActivity(LoginActivity.class);
                                }
                            }
                        }else{
                            startActivity(DriverMainActivity.class);
                        }
                    }
                }else{
                    startActivity(LoginActivity.class);
                }
                finish();
            }
        }, 3000);
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
            if(null!=baseBeanResult&&null!=baseBeanResult.getData()){
                if(null!=baseBeanResult.getData().getObject()){
                    if(null!=baseBeanResult.getData().getObject().getAgreementContent()){
                        PreferencesUtils.putString(mContext, Constants.AGREEMENT, baseBeanResult.getData().getObject().getAgreementContent());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
