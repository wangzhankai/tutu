package com.superpeer.tutuyoudian.activity.selectshop;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class SelectShopModel implements SelectShopContract.Model {
    @Override
    public Observable<BaseBeanResult> getType(String shopId) {
        return Api.getInstance().service.getType(shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name) {
        return Api.getInstance().service.getGoods(shopId, goodsTypeId, saleState, stock, defaultCurrent, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
