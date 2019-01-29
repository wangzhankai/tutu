package com.superpeer.tutuyoudian.activity.search;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class SearchModel implements SearchContract.Model {

    @Override
    public Observable<BaseBeanResult> getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name) {
        return Api.getInstance().service.getGoodsSearch(shopId, goodsTypeId, saleState, stock, defaultCurrent, pageSize, name).map(new Func1<BaseBeanResult, BaseBeanResult>() {
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
