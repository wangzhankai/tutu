package com.superpeer.tutuyoudian.wxapi;

import com.google.gson.Gson;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.activity.driver.main.DriverMainActivity;
import com.superpeer.tutuyoudian.activity.info.StoreInfoActivity;
import com.superpeer.tutuyoudian.activity.main.MainActivity;
import com.superpeer.tutuyoudian.activity.store.StoreApplyActivity;
import com.superpeer.tutuyoudian.activity.storesendset.StoreSendSetActivity;
import com.superpeer.tutuyoudian.activity.storeuse.StoreUseActivity;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.constant.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WXPayEntryActivity extends BaseActivity<PayPresenter, PayModel> implements IWXAPIEventHandler, PayContract.View{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
	private ImageView ivPayStatus;
	private TextView tvPaySuccess;
	private ImageView ivLeft;
	private TextView tvTitle;

	@Override
	public int getLayoutId() {
		return R.layout.pay_result;
	}

	@Override
	public void initPresenter() {
		mPresenter.setVM(this, mModel);
	}

	@Override
	public void initView() {
//		setHeadTitle("支付结果");

		ivLeft = (ImageView) findViewById(R.id.ivLeft);
		ivPayStatus = (ImageView) findViewById(R.id.ivPayStatus);
		tvPaySuccess = (TextView) findViewById(R.id.tvPaySuccess);
		tvTitle = (TextView) findViewById(R.id.tvTitle);

		tvTitle.setText("支付结果");

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);

		ivLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(MainActivity.class);
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		Log.i("base", req.openId);
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if("0".equals(resp.errCode+"")){
			ivPayStatus.setImageResource(R.mipmap.iv_verify_success);
			tvPaySuccess.setText("支付成功");
			mPresenter.payResult(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
		}else if("-2".equals(resp.errCode+"")){
			ivPayStatus.setImageResource(R.mipmap.iv_verify_fail);
			tvPaySuccess.setText("支付取消");
		}else {
			ivPayStatus.setImageResource(R.mipmap.iv_verify_fail);
			tvPaySuccess.setText("支付失败");
		}
	}

	@Override
	public void showPayResult(BaseBeanResult baseBeanResult) {
		try{
			if(null!=baseBeanResult.getMsg()){
				showShortToast(baseBeanResult.getMsg());
			}
			if("1".equals(baseBeanResult.getCode())){
				if(null!=baseBeanResult.getData()&&null!=baseBeanResult.getData().getObject()){
					PreferencesUtils.putString(mContext, Constants.USER_INFO, new Gson().toJson(baseBeanResult.getData().getObject()));
					PreferencesUtils.putString(mContext, Constants.IS_IDENTIFY, baseBeanResult.getData().getObject().getState());
					PreferencesUtils.putString(mContext, Constants.SHOP_NOTICE, baseBeanResult.getData().getObject().getContent());
					PreferencesUtils.putString(mContext, Constants.ACCOUNT_ID, baseBeanResult.getData().getObject().getAccountId());
					PreferencesUtils.putString(mContext, Constants.USER_TYPE, baseBeanResult.getData().getObject().getRoleType());
					if(null!=baseBeanResult.getData().getObject().getRoleType()){
						if ("0".equals(baseBeanResult.getData().getObject().getRoleType())) {
							if(null!=baseBeanResult.getData().getObject().getState()){
								if("0".equals(baseBeanResult.getData().getObject().getState())){
									startActivity(StoreApplyActivity.class);
								}else if("1".equals(baseBeanResult.getData().getObject().getState())){
									startActivity(StoreSendSetActivity.class);
								}else if("2".equals(baseBeanResult.getData().getObject().getState())){
									startActivity(StoreInfoActivity.class);
								}else if("3".equals(baseBeanResult.getData().getObject().getState())){
									startActivity(StoreUseActivity.class);
								}else{
									startActivity(MainActivity.class);
								}
							}
							PreferencesUtils.putString(mContext, Constants.SHOP_ID, baseBeanResult.getData().getObject().getShopId());
							PreferencesUtils.putString(mContext, Constants.SHOP_STATE, baseBeanResult.getData().getObject().getState());
						}else{
							startActivity(DriverMainActivity.class);
							PreferencesUtils.putString(mContext, Constants.SHOP_ID, baseBeanResult.getData().getObject().getId());
						}
						finish();
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
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