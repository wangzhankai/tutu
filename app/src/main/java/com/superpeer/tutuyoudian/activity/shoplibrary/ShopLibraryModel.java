package com.superpeer.tutuyoudian.activity.shoplibrary;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class ShopLibraryModel implements ShopLibraryContract.Model {
    @Override
    public Observable<BaseBeanResult> getGoodsType(String shopId) {
        return Api.getInstance().service.getShopCategory(shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

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
