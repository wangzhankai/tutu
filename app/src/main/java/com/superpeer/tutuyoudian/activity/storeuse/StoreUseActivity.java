package com.superpeer.tutuyoudian.activity.storeuse;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StoreUseActivity extends BaseActivity<StoreUsePresenter, StoreUseModel> implements StoreUseContract.View {

    private IWXAPI msgApi;
    private TextView tvSure;
    private PayReq request;
    private LinearLayout linearWx;
    private LinearLayout linearZfb;
    private ImageView ivWx;
    private ImageView ivZfb;

    private int index =0;
    private String flag = "";

    @Override
    protected void doBeforeSetcontentView() {
        super.doBeforeSetcontentView();
        flag = getIntent().getStringExtra("type");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_use;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("店铺激活");

        tvSure = (TextView) findViewById(R.id.tvSure);
        linearWx = (LinearLayout) findViewById(R.id.linearWx);
        linearZfb = (LinearLayout) findViewById(R.id.linearZfb);
        ivWx = (ImageView) findViewById(R.id.ivWx);
        ivZfb = (ImageView) findViewById(R.id.ivZfb);

        initListener();

        msgApi = WXAPIFactory.createWXAPI(mContext, null);
        // 将该app注册到微信
        msgApi.registerApp(Constants.APP_ID);

        mPresenter.activation(PreferencesUtils.getString(mContext, Constants.SHOP_ID));
    }

    private void initListener() {
        linearWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                ivWx.setImageResource(R.mipmap.iv_select);
                ivZfb.setImageResource(R.mipmap.iv_noselect);
            }
        });

        linearZfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                ivZfb.setImageResource(R.mipmap.iv_select);
                ivWx.setImageResource(R.mipmap.iv_noselect);
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    /*msgApi = WXAPIFactory.createWXAPI(mContext, null);
                    // 将该app注册到微信
                    msgApi.registerApp(Constants.APP_ID);*/
                    msgApi.sendReq(request);
                }catch (Exception e){
                    e.printStackTrace();
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
    public void showResult(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
                if("1".equals(baseBeanResult.getCode())){
                    initReq(baseBeanResult.getData().getObject());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initReq(BaseObject object) {
        try {
            request = new PayReq();
//            request.appId = object.getAppid();
            request.appId = Constants.APP_ID;
            request.partnerId = object.getPartnerid();
            request.prepayId = object.getPrepayid();
            request.packageValue = "Sign=WXPay";
            request.nonceStr = object.getNoncestr();
            request.timeStamp = object.getTimestamp();
            request.sign = object.getSign();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
