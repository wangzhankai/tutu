package com.superpeer.tutuyoudian.activity.collageadd;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public class AddCollageModel implements AddCollageContract.Model {
    @Override
    public Observable<BaseBeanResult> setCollageInfo(String id, String shopId, String goodsId, String title, String goodsNum, String groupDesc, String price, String groupNum, String needNum, String shippingType, String keepHour, String cancelHour, String noGetHour, String noSendHour) {
        return Api.getInstance().service.setCollageInfo(id, shopId, goodsId, title, goodsNum, groupDesc, price, groupNum, needNum, shippingType, keepHour, cancelHour, noGetHour, noSendHour).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getDetail(String orderId) {
        return Api.getInstance().service.findGroupDetail(orderId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
