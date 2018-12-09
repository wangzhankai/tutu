package com.superpeer.tutuyoudian.activity.cash.withdraw;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.cash.record.CashRecordActivity;
import com.superpeer.tutuyoudian.activity.paytype.PayTypeActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.TvUtils;

import java.math.BigDecimal;

import cloudist.cc.library.TextChangeListener;
import cloudist.cc.library.widget.InputPasswordDialog;
import rx.functions.Action1;

public class CashWithDrawActivity extends BaseActivity<CashWithDrawPresenter, CashWithDrawModel> implements CashWithDrawContract.View {

    private TextView tvRest;
    private TextView tvWithDrawCash;
    private EditText etCash;
    private TextView tvAccount;
    private LinearLayout linearPayType;
    private TextView tvType;
    private TextView tvWithDraw;
    private String accountType = "";
    private TextView etUserName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_with_draw;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("资金提现");

        setToolBarViewStubText("提现记录").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CashRecordActivity.class);
            }
        });

        tvType = (TextView) findViewById(R.id.tvType);
        tvRest = (TextView) findViewById(R.id.tvRest);
        tvWithDrawCash = (TextView) findViewById(R.id.tvWithDrawCash);
        tvAccount = (TextView) findViewById(R.id.tvAccount);
        tvWithDraw = (TextView) findViewById(R.id.tvWithDraw);
        etUserName = (TextView) findViewById(R.id.etUserName);
        etCash = (EditText) findViewById(R.id.etCash);
        linearPayType = (LinearLayout) findViewById(R.id.linearPayType);

        mPresenter.getAccountInfo(PreferencesUtils.getString(mContext, Constants.SHOP_ID));

        initListener();

        initRxBus();

        TvUtils.setTwoDecimal(etCash);

    }

    private void initRxBus() {
        mRxManager.on("selectAccount", new Action1<BaseList>() {
            @Override
            public void call(BaseList bean) {
                tvType.setText(bean.getBankName());
                tvAccount.setText(bean.getBankCard());
                etUserName.setText(bean.getAccountName());
                accountType = bean.getAccountType();
            }
        });
    }

    private void initListener() {
        linearPayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PayTypeActivity.class);
            }
        });
        //提现
        tvWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String money = etCash.getText().toString().trim();
                String canWithDraw = tvWithDrawCash.getText().toString().trim();
                if(TextUtils.isEmpty(money)){
                    showShortToast("请输入提现金额");
                    return;
                }
                if(Double.parseDouble(money)>Double.parseDouble(canWithDraw)){
                    showShortToast("提现金额不能大于可提现金额");
                    return;
                }
                if(TextUtils.isEmpty(accountType)){
                    showShortToast("请选择付款方式");
                    return;
                }
                InputPasswordDialog.newInstance()
                        .setTextChangeListener(new TextChangeListener() {
                            @Override
                            public void textChange(String text) {
                                if(text.length() == 6){
                                    mPresenter.saveWithDraw(PreferencesUtils.getString(mContext, Constants.SHOP_ID), money, accountType);
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

    @Override
    public void showSaveResult(BaseBeanResult baseBeanResult) {
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

    private void initData(BaseObject object) {
        try{
            if(null!=object.getAccountBalance()){
                tvRest.setText(numberFormat.format(new BigDecimal(object.getAccountBalance())));
            }
            if(null!=object.getAvailableBalance()){
                tvWithDrawCash.setText(numberFormat.format(new BigDecimal(object.getAvailableBalance())));
            }
            /*if(null!=object.getAccountType()){
                if("0".equals(object.getAccountType())){
                    tvType.setText("微信");
                }else if("1".equals(object.getAccountType())){
                    tvType.setText("银行卡");
                }else if("2".equals(object.getAccountType())){
                    tvType.setText("微信/银行卡");
                }
            }
            if(null!=object.getAccountName()){
                tvType.setText(object.getAccountName());
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
