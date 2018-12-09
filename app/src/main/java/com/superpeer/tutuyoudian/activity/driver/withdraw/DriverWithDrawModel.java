package com.superpeer.tutuyoudian.activity.driver.withdraw;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class DriverWithDrawModel implements DriverWithDrawContract.Model {
    @Override
    public Observable<BaseBeanResult> getAccountInfo(String id) {
        return Api.getInstance().service.getAccountInfo(id).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveWithdraw(String id, String money, String type, String pwd) {
        return Api.getInstance().service.saveWithdraw(id, money, type, pwd).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
