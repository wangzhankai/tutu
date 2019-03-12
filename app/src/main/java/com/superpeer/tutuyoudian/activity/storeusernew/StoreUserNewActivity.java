package com.superpeer.tutuyoudian.activity.storeusernew;

import android.app.AlertDialog;
import android.media.tv.TvView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superpeer.base_libs.utils.DialogUtil;
import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseList;
import com.superpeer.tutuyoudian.bean.BaseObject;
import com.superpeer.tutuyoudian.constant.Constants;
import com.superpeer.tutuyoudian.utils.DialogUtils;
import com.superpeer.tutuyoudian.utils.TvUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class StoreUserNewActivity extends BaseActivity<StoreUserNewPresenter, StoreUserNewModel> implements StoreUserNewContract.View {

    private TextView tvCoupon;
    private TextView tvPrice;
    private TextView tvOrignPrice;
    private TextView tvInviteCode;
    private TextView tvWx;
    private IWXAPI msgApi;
    private String phone = "";
    private BaseObject object;
    private String operatorId = "";
    private String id = "";
    private String activationAmount = "";
    private PayReq request;
    private TextView tvCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_user_new;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setHeadTitle("兔兔服务");

        phone = PreferencesUtils.getString(mContext, Constants.INVITATION_CODE);

        tvCoupon = (TextView) findViewById(R.id.tvCoupon);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvOrignPrice = (TextView) findViewById(R.id.tvOrignPrice);
        tvInviteCode = (TextView) findViewById(R.id.tvInviteCode);
        tvWx = (TextView) findViewById(R.id.tvWx);
        tvCode = (TextView) findViewById(R.id.tvCode);

        initListener();

        msgApi = WXAPIFactory.createWXAPI(mContext, null);
        // 将该app注册到微信
        msgApi.registerApp(Constants.APP_ID);

        mPresenter.queryFeeSetting(phone);
    }

    private void initListener() {
        //填写邀请码
        tvInviteCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInviteDialog();
            }
        });

        //微信支付
        tvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mPresenter.activation(PreferencesUtils.getString(mContext, Constants.SHOP_ID),
                            id, phone, activationAmount.equals("")?"0":"1");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void showInviteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View
                .inflate(mContext, R.layout.dialog_update_price, null);
        builder.setView(view);
        builder.setCancelable(true);
        final EditText etPrice= (EditText) view
                .findViewById(R.id.etPrice);//输入内容
        TextView tvCancel=(TextView) view
                .findViewById(R.id.tvCancel);//取消按钮
        TextView tvSure=(TextView)view.findViewById(R.id.tvSure);//确定按钮

        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = etPrice.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    mPresenter.queryFeeSetting(phone);
                }
                dialog.dismiss();
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

    @Override
    public void showFeeList(BaseBeanResult baseBeanResult) {
        try{
            if(null!=baseBeanResult){
                if("1".equals(baseBeanResult.getCode())){
                    if(null!=baseBeanResult.getData()){
                        if(null!=baseBeanResult.getData().getObject()){
                            object = baseBeanResult.getData().getObject();
                            initData();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData() {
        try{
            if(null!=object){
                if(TextUtils.isEmpty(phone)){
                    tvCoupon.setVisibility(View.GONE);
                    tvOrignPrice.setVisibility(View.GONE);
                    tvInviteCode.setVisibility(View.VISIBLE);
                    if(null!=object.getPrice()){
                        tvPrice.setText(object.getPrice());
                    }
                }else{
                    tvCode.setText("邀请优惠码："+phone);
                    tvCoupon.setVisibility(View.VISIBLE);
                    tvOrignPrice.setVisibility(View.VISIBLE);
                    tvInviteCode.setVisibility(View.GONE);
                    if(null!=object.getPrice()){
                        TvUtils.setLine(tvOrignPrice);
                        tvOrignPrice.setText("原价："+object.getPrice()+"/年");
                    }
                    if(null!=object.getActivationAmount()){
                        activationAmount = object.getActivationAmount();
                        tvPrice.setText(object.getActivationAmount());
                    }
                }
                if(null!=object.getOperatorId()){
                    operatorId = object.getOperatorId();
                }
                if(null!=object.getSettingId()){
                    id = object.getSettingId();
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

            msgApi.sendReq(request);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
