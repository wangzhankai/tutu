package com.superpeer.tutuyoudian.wxapi;

import android.content.Intent;
import android.util.Log;

import com.superpeer.base_libs.utils.PreferencesUtils;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.base.BaseActivity;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.WxBean;
import com.superpeer.tutuyoudian.constant.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends BaseActivity<WXEntryPresenter, WXEntryModel> implements IWXAPIEventHandler, WXEntryContract.View{

    private IWXAPI api;

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
        }
        if(baseResp instanceof SendAuth.Resp){
            SendAuth.Resp newResp = (SendAuth.Resp) baseResp;
            //获取微信传回的code
            String code = newResp.code;
            if("0".equals(PreferencesUtils.getString(mContext, Constants.USER_TYPE))){       //商家
                mPresenter.appOauth(PreferencesUtils.getString(mContext, Constants.SHOP_ID), "", code);
            }else{
                mPresenter.appOauth("", PreferencesUtils.getString(mContext, Constants.SHOP_ID), code);
            }
        }
    }

    @Override
    public void showOauth(BaseBeanResult baseBeanResult) {
        try{
            mRxManager.post("savePayType", "");
            if(null!=baseBeanResult){
                if(null!=baseBeanResult.getMsg()){
                    showShortToast(baseBeanResult.getMsg());
                }
            }
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showInfoResult(WxBean bean) {

    }

    @Override
    public void showToken(WxBean bean) {

    }

    @Override
    public void showSaveResult(BaseBeanResult baseBeanResult) {

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
    public int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}