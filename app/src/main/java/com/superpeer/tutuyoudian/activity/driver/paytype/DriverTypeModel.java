package com.superpeer.tutuyoudian.activity.driver.paytype;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class DriverTypeModel implements DriverTypeContract.Model {
    @Override
    public Observable<BaseBeanResult> getPayType(String id) {
        return Api.getInstance().service.getRunnerType(id).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveAccount(String roleType, String shopId, String openId, String unionId, String nickName) {
        return Api.getInstance().service.saveWxShopAccount(roleType, shopId, openId, unionId, nickName).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveAccountRunner(String id, String roleType, String openId, String unionId, String nickName) {
        return Api.getInstance().service.saveWxRunnerAccount(id, roleType, openId, unionId, nickName).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
