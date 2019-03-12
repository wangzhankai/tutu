package com.superpeer.tutuyoudian.activity.setting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.base.AppManager;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.announce.publish.NoticePublishActivity;
import com.superpeer.tutuyoudian.activity.info.StoreInfoActivity;
import com.superpeer.tutuyoudian.activity.login.LoginActivity;
import com.superpeer.tutuyoudian.activity.safesetting.SafeSettingActivity;
import com.superpeer.tutuyoudian.activity.shopcode.ShopCodeActivity;
import com.superpeer.tutuyoudian.activity.store.StoreApplyActivity;
import com.superpeer.tutuyoudian.activity.storedriver.DriverListActivity;
import com.superpeer.tutuyoudian.activity.storeorder.StoreOrderActivity;
import com.superpeer.tutuyoudian.activity.storesendset.StoreSendSetActivity;
import com.superpeer.tutuyoudian.activity.storeuse.StoreUseActivity;
import com.superpeer.tutuyoudian.activity.storeusernew.StoreUserNewActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.constant.Constants;

import rx.functions.Action1;

public class StoreSettingActivity extends BaseActivity<StoreSettingPresenter, StoreSettingModel> implements StoreSettingContract.View {

    private LinearLayout linearStoreInfo;
    private LinearLayout linearNoticeSet;
    private LinearLayout linearStoreSend;
    private LinearLayout linearStoreUse;
    private TextView tvExit;
    private LinearLayout linearStoreSetting;
    private LinearLayout linearDriver;
    private LinearLayout linearStoreOrder;
    private LinearLayout linearSafeSetting;
    private TextView tvToUse;
    private LinearLayout linearStoreInvite;

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_setting;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺设置");

        linearStoreOrder = (LinearLayout) findViewById(R.id.linearStoreOrder);
        linearStoreInfo = (LinearLayout) findViewById(R.id.linearStoreInfo);
        linearNoticeSet = (LinearLayout) findViewById(R.id.linearNoticeSet);
        linearStoreSend = (LinearLayout) findViewById(R.id.linearStoreSend);
        linearStoreUse = (LinearLayout) findViewById(R.id.linearStoreUse);
        linearStoreSetting = (LinearLayout) findViewById(R.id.linearStoreSetting);
        linearSafeSetting = (LinearLayout) findViewById(R.id.linearSafeSetting);
        linearStoreInvite = (LinearLayout) findViewById(R.id.linearStoreInvite);
        //店铺跑腿
        linearDriver = (LinearLayout) findViewById(R.id.linearDriver);

        tvToUse = (TextView) findViewById(R.id.tvToUse);
        tvExit = (TextView) findViewById(R.id.tvExit);

        if(null!=PreferencesUtils.getString(mContext, Constants.VALIDITYPERIOD)){
            tvToUse.setText(PreferencesUtils.getString(mContext, Constants.VALIDITYPERIOD));
        }

        initRxBus();

        initListener();
    }

    private void initRxBus() {
        mRxManager.on("validityPeriod", new Action1<String>() {
            @Override
            public void call(String s) {
                tvToUse.setText(s);
            }
        });
    }

    private void initListener() {
        //店铺邀请码
        linearStoreInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShopCodeActivity.class);
            }
        });
        //店铺配送订单
        linearStoreOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreOrderActivity.class);
            }
        });
        //店铺基本信息
        linearStoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreApplyActivity.class);
            }
        });
        //店铺设置
        linearNoticeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NoticePublishActivity.class);
            }
        });
        //店铺入驻信息设置
        linearStoreSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreInfoActivity.class);
            }
        });

        //配送设置
        linearStoreSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreSendSetActivity.class);
            }
        });

        //店铺激活
        linearStoreUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreUserNewActivity.class);
            }
        });

        //店铺跑腿
        linearDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DriverListActivity.class);
            }
        });

        //退出登录
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putString(mContext, Constants.SHOP_ID, "");
                PreferencesUtils.putString(mContext, Constants.USER_TYPE, "");
                startActivity(LoginActivity.class);
                AppManager.getAppManager().finishAllActivity();
            }
        });
        //安全设置
        linearSafeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SafeSettingActivity.class);
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
