package com.superpeer.tutuyoudian.wxapi;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.WxBean;

public class WXEntryPresenter extends WXEntryContract.Presenter {

    @Override
    public void appOauth(String shopId, String runnerId, String code) {
        mRxManage.add(mModel.appOauth(shopId, runnerId, code).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showOauth(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getUserInfo(String token, String appid) {
        mRxManage.add(mModel.getUserInfo(token, appid).subscribe(new RxSubscriber<WxBean>(mContext, false) {
            @Override
            protected void _onNext(WxBean bean) {
                mView.showInfoResult(bean);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void access_token(String appid, String secret, String code, String type) {
        mRxManage.add(mModel.access_token(appid, secret, code, type).subscribe(new RxSubscriber<WxBean>(mContext, false) {
            @Override
            protected void _onNext(WxBean bean) {
                mView.showToken(bean);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        mRxManage.add(mModel.saveAccount(roleType, shopId, openId, unionId, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSaveResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        mRxManage.add(mModel.saveAccountRunner(id, roleType, openId, unionId, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSaveResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
