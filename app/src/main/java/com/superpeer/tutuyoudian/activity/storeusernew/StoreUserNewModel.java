package com.superpeer.tutuyoudian.activity.storeusernew;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class StoreUserNewModel implements StoreUserNewContract.Model {
    @Override
    public Observable<BaseBeanResult> activation(String shopId, String feeSettingId, String visitCode, String type) {
        return Api.getInstance().service.activationNew(shopId, feeSettingId, visitCode, type).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> queryFeeSetting(String phone) {
        return Api.getInstance().service.queryFeeSettingNew(phone).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
