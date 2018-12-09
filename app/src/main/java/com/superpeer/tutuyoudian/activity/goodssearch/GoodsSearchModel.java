package com.superpeer.tutuyoudian.activity.goodssearch;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class GoodsSearchModel implements GoodsSearchContract.Model {

    @Override
    public Observable<BaseBeanResult> getGoods(String typeId, String shopId, String page, String size, String name) {
        return Api.getInstance().service.getAllGoods(typeId, shopId, page, size, name).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveGoods(String shopId, String goodsBankId) {
        return Api.getInstance().service.saveGoods(shopId, goodsBankId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

}
