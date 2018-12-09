package com.superpeer.tutuyoudian.activity.storesendset;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class StoreSendSetModel implements StoreSendSetContract.Model {
    @Override
    public Observable<BaseBeanResult> saveDistributionInfo(String shopId, String deliveryRange, String minMoney, String packingFee, String deliveryTime, String deliveryFee, String openingTime, String closingTime, String shopDayOff) {
        return Api.getInstance().service.saveDistributionInfo(shopId, deliveryRange, minMoney, packingFee, deliveryTime, deliveryFee, openingTime, closingTime, shopDayOff).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
