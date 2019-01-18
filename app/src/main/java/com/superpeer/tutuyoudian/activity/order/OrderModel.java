package com.superpeer.tutuyoudian.activity.order;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class OrderModel implements OrderContract.Model {
    @Override
    public Observable<BaseBeanResult> getOrderList(String shopId, String page, String pageCount, String type, String shippingType) {
        return Api.getInstance().service.getSingleOrderList(shopId, page, pageCount, type, shippingType).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
