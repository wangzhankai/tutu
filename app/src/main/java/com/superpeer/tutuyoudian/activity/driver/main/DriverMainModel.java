package com.superpeer.tutuyoudian.activity.driver.main;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.R;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class DriverMainModel implements DriverMainContract.Model {
    /*@Override
    public Observable<BaseBeanResult> getOrderList(String id, String defaultCurrent, String pageSize) {
        return Api.getInstance().service.getOrderList(id, defaultCurrent, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }*/

    @Override
    public Observable<BaseBeanResult> changeReceiptStatus(String id, String status) {
        return Api.getInstance().service.changeReceiptStatus(id, status).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> grabOrder(String orderId, String runnerId) {
        return Api.getInstance().service.grabOrder(orderId, runnerId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> update(String type) {
        return Api.getInstance().service.update(type).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    /*@Override
    public Observable<BaseBeanResult> giveUpOrder(String orderId) {
        return Api.getInstance().service.giveUpOrder(orderId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }*/
}
