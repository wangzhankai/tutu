package com.superpeer.tutuyoudian.activity.shopedit;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/22 0022.
 */

public class ShopEditModel implements ShopEditContract.Model {
    @Override
    public Observable<BaseBeanResult> getShopCategory(String shopId) {
        return Api.getInstance().service.getShopCategory(shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
