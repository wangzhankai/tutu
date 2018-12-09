package com.superpeer.tutuyoudian.activity.collageorder;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class CollageOrderModel implements CollageOrderContract.Model {
    @Override
    public Observable<BaseBeanResult> getOrderList(String shopId, String page, String pageSize, String orderStatus, String shippingType) {
        return Api.getInstance().service.getGroupOrderList(shopId, page, pageSize, orderStatus, shippingType).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
