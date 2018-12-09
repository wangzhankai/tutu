package com.superpeer.tutuyoudian.wxapi;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.api.WxApi;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.WxBean;

import rx.Observable;
import rx.functions.Func1;

public class WXEntryModel implements WXEntryContract.Model {

    @Override
    public Observable<WxBean> access_token(String appid, String secret, String code, String type) {
        return WxApi.getInstance().service.access_token(appid, secret, code, type).map(new Func1<WxBean, WxBean>() {
            @Override
            public WxBean call(WxBean baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<WxBean>io_main());
    }

    @Override
    public Observable<WxBean> getUserInfo(String token, String appid) {
        return Api.getInstance().service.getUserInfo(token, appid).map(new Func1<WxBean, WxBean>() {
            @Override
            public WxBean call(WxBean bean) {
                return bean;
            }
        }).compose(RxSchedulers.<WxBean>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        return Api.getInstance().service.saveShopAccount(roleType, shopId, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        return Api.getInstance().service.saveRunnerAccount(id, roleType, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

}
