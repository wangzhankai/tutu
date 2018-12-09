package com.superpeer.tutuyoudian.activity.adddriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;

public class AddDriverActivity extends BaseActivity<AddDriverPresenter, AddDriverModel> implements AddDriverContract.View{

    private String result = "";
    private TextView tvUserName;
    private TextView tvVerify;
    private TextView tvHasVerify;
    private TextView tvPhone;
    private TextView tvIdentify;
    private TextView tvAdd;
    private BaseObject bean;
    private String type = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        result = getIntent().getStringExtra("result");
        type = getIntent().getStringExtra("type");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_driver;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("添加跑腿");

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvVerify = (TextView) findViewById(R.id.tvVerify);
        tvHasVerify = (TextView) findViewById(R.id.tvHasVerify);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvIdentify = (TextView) findViewById(R.id.tvIdentify);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        if(type.equals("1")){
            tvAdd.setVisibility(View.GONE);
        }else{
            tvAdd.setVisibility(View.VISIBLE);
        }

        mPresenter.getRunnerInfo(result);

        initListener();
    }

    private void initListener() {
        //添加骑手
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=bean) {
                    if (null != bean.getRunnerStatus()) {
                        if ("2".equals(bean.getRunnerStatus())) {       //已认证
                            mPresenter.addRunner(PreferencesUtils.getString(mContext, Constants.SHOP_ID), result);
                        }else{
                            showShortToast("该跑腿未认证");
                        }
                    }else{
                        showShortToast("该跑腿未认证");
                    }
                }else{
                    showShortToast("该跑腿未认证");
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
    public void showRunnerInfo(BaseBeanResult baseBeanResult) {
        try {
            if (null != baseBeanResult) {
                if (null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData().getObject()){
                        bean = baseBeanResult.getData().getObject();
                        initData(baseBeanResult.getData().getObject());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showAddResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    mRxManager.post("driverList", "");
                    finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData(BaseObject bean) {
        if(null!=bean.getUserName()){
            tvUserName.setText(bean.getUserName());
        }
        if(null!=bean.getPhone()){
            tvPhone.setText(bean.getPhone());
        }
        if(null!=bean.getIdentityCard()){
            tvIdentify.setText(bean.getIdentityCard());
        }
        if(null!=bean.getRunnerStatus()){
            if("1".equals(bean.getRunnerStatus())){
                tvVerify.setVisibility(View.VISIBLE);
            }else{
                tvHasVerify.setVisibility(View.VISIBLE);
            }
        }
    }
}
