package com.superpeer.tutuyoudian.activity.stocksearch;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class StockSearchModel implements StockSearchContract.Model {
    @Override
    public Observable<BaseBeanResult> getStockSearch(String shopId, String name, String defaultCurrent, String pageSize) {
        return Api.getInstance().service.getStockSearch(shopId, name, defaultCurrent, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> modifySaleState(String goodsId, String saleState) {
        return Api.getInstance().service.modifySaleState(goodsId, saleState).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> delMyGoods(String goodsId) {
        return Api.getInstance().service.delMyGoods(goodsId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> updatePrice(String shopId, String goodsBankId, String price, String goodsId) {
        return Api.getInstance().service.updatePrice(shopId, goodsBankId, price, goodsId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

}
