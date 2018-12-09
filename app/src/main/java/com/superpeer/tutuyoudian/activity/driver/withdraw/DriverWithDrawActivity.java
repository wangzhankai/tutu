package com.superpeer.tutuyoudian.activity.driver.withdraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.paytype.DriverTypeActivity;
import com.superpeer.tutuyoudian.activity.driver.record.WithDrawRecordActivity;
import com.superpeer.tutuyoudian.activity.paytype.PayTypeActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;

import java.math.BigDecimal;
import java.sql.Driver;

import cloudist.cc.library.TextChangeListener;
import cloudist.cc.library.view.PasswordInputView;
import cloudist.cc.library.widget.InputPasswordDialog;

public class DriverWithDrawActivity extends BaseActivity<DriverWithDrawPresenter, DriverWithDrawModel> implements DriverWithDrawContract.View {

    private TextView tvAccountNum;
    private TextView tvCanWithDraw;
    private EditText etWithDrawCash;
    private TextView tvType;
    private TextView tvAccount;
    private TextView tvName;
    private TextView tvWithDraw;
    private TextView tvContract;

    private String accountType = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_with_draw;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("提现");

        setToolBarViewStubText("提现记录").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WithDrawRecordActivity.class);
            }
        });

        tvAccountNum = (TextView) findViewById(R.id.tvAccountNum);
        tvCanWithDraw = (TextView) findViewById(R.id.tvCanWithDraw);
        etWithDrawCash = (EditText) findViewById(R.id.etWithDrawCash);
        tvType = (TextView) findViewById(R.id.tvType);
        tvAccount = (TextView) findViewById(R.id.tvAccount);
        tvName = (TextView) findViewById(R.id.tvName);
        tvWithDraw = (TextView) findViewById(R.id.tvWithDraw);
        tvContract = (TextView) findViewById(R.id.tvContract);

        mPresenter.getAccountId(PreferencesUtils.getString(mContext, Constants.SHOP_ID));

        initListener();
    }

    private void initListener() {
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverTypeActivity.class);
            }
        });
        //提现
        tvWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String money = etWithDrawCash.getText().toString().trim();
                String canWithDraw = tvCanWithDraw.getText().toString().trim();

                if(TextUtils.isEmpty(money)){
                    showShortToast("请输入提现金额");
                    return;
                }
                if(Double.parseDouble(money)>Double.parseDouble(canWithDraw)){
                    showShortToast("提现金额不能大于可提现金额");
                    return;
                }
                if(TextUtils.isEmpty(accountType)){
                    showShortToast("请选择提现方式");
                    return;
                }
                InputPasswordDialog.newInstance()
                        .setTextChangeListener(new TextChangeListener() {
                            @Override
                            public void textChange(String text) {
                                if(text.length() == 6){
                                    mPresenter.saveWithdraw(PreferencesUtils.getString(mContext, Constants.SHOP_ID), money, accountType, text);
                                }
                            }
                        }).show(getSupportFragmentManager(), "");
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
    public void showAccountResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getData()){
                    if(null!=baseBeanResult.getData().getObject()){
                        initData(baseBeanResult.getData().getObject());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData(BaseObject object) {
        try {
            if (null != object.getBalanceMoney()&&null!=object.getFreezeMoney()) {
                tvAccountNum.setText(new BigDecimal(object.getBalanceMoney()).add(new BigDecimal(object.getFreezeMoney())).doubleValue()+"");
            }
            if (null != object.getBalanceMoney()) {
                tvCanWithDraw.setText(object.getBalanceMoney());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
