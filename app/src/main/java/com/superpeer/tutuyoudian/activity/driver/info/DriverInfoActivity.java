package com.superpeer.tutuyoudian.activity.driver.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.historyorder.HistoryOrderActivity;
import com.superpeer.tutuyoudian.activity.driver.identify.DriverIdentifyActivity;
import com.superpeer.tutuyoudian.activity.driver.paytype.DriverTypeActivity;
import com.superpeer.tutuyoudian.activity.driver.userinfo.UserInfoActivity;
import com.superpeer.tutuyoudian.activity.driver.withdraw.DriverWithDrawActivity;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.activity.paytype.PayTypeActivity;
import com.superpeer.tutuyoudian.activity.safesetting.SafeSettingActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;

import java.math.BigDecimal;

public class DriverInfoActivity extends BaseActivity<DriverInfoPresenter, DriverInfoModel> implements DriverInfoContract.View {

    private TextView tvPersonInfo;
    private TextView tvHistoryOrder;
    private LinearLayout linearWithDraw;
    private LinearLayout linearIdentify;
    private LinearLayout linearSetting;
    private TextView tvIdentifyStatus;
    private TextView tvSettingStatus;
    private TextView tvSafeSetting;
    private TextView tvExit;
    private TextView tvWithDrawCash;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("我的");

        tvSafeSetting = (TextView) findViewById(R.id.tvSafeSetting);
        tvPersonInfo = (TextView) findViewById(R.id.tvPersonInfo);
        tvHistoryOrder = (TextView) findViewById(R.id.tvHistoryOrder);
        tvExit = (TextView) findViewById(R.id.tvExit);
        tvWithDrawCash = (TextView) findViewById(R.id.tvWithDrawCash);
        tvIdentifyStatus = (TextView) findViewById(R.id.tvIdentifyStatus);
        tvSettingStatus = (TextView) findViewById(R.id.tvSettingStatus);
        linearWithDraw = (LinearLayout) findViewById(R.id.linearWithDraw);
        linearIdentify = (LinearLayout) findViewById(R.id.linearIdentify);
        linearSetting = (LinearLayout) findViewById(R.id.linearSetting);

        BaseObject bean = getUserInfo();
        if(null!=bean){
            initData(bean);
        }

        initListener();
    }

    private void initData(BaseObject bean) {
        BigDecimal restMoney = null;
        BigDecimal freezeMoney = null;
        if(null!=bean.getBalanceMoney()){
            restMoney = new BigDecimal(bean.getBalanceMoney());
        }
        if(null!=bean.getFreezeMoney()){
            freezeMoney = new BigDecimal(bean.getFreezeMoney());
        }
        if(null!=restMoney){
            if(null!=freezeMoney){
                tvWithDrawCash.setText(restMoney.add(freezeMoney)+"");
            }else{
                tvWithDrawCash.setText(restMoney+"");
            }
            if(null!=bean.getRunnerStatus()){
                if("1".equals(bean.getRunnerStatus())){
                    tvIdentifyStatus.setText("未认证");
                }else{
                    tvIdentifyStatus.setText("已认证");
                }
            }else{
                tvIdentifyStatus.setText("未认证");
            }
        }
    }

    private void initListener() {
        //退出登录
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putString(mContext, Constants.SHOP_ID, "");
                PreferencesUtils.putString(mContext, Constants.USER_TYPE, "");
                AppManager.getAppManager().finishAllActivity();
                startActivity(LoginActivity.class);
            }
        });
        //安全设置
        tvSafeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SafeSettingActivity.class);
            }
        });
        //个人信息
        tvPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserInfoActivity.class);
            }
        });
        //身份认证
        linearIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverIdentifyActivity.class);
            }
        });
        //提现
        linearWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverWithDrawActivity.class);
            }
        });
        //历史订单
        tvHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HistoryOrderActivity.class);
            }
        });
        //结算设置
        linearSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverTypeActivity.class);
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
