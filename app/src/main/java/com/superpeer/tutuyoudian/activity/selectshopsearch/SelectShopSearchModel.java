package com.superpeer.tutuyoudian.activity.selectshopsearch;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class SelectShopSearchModel implements SelectShopSearchContract.Model {

    @Override
    public Observable<BaseBeanResult> getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name) {
        return Api.getInstance().service.getGoodsSearch(shopId, saleState, stock, defaultCurrent, pageSize, name).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

}
