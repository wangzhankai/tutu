package com.superpeer.tutuyoudian.activity.adddriver;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class AddDriverModel implements AddDriverContract.Model {
    @Override
    public Observable<BaseBeanResult> getRunnerInfo(String id, String shopId) {
        return Api.getInstance().service.scanQr(id, shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> addRunner(String shopId, String id, String runnerType) {
        return Api.getInstance().service.bindingRunner(shopId, id, runnerType).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
