package com.superpeer.tutuyoudian.activity.cash.record;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class CashRecordModel implements CashRecordContract.Model {
    @Override
    public Observable<BaseBeanResult> getRecordList(String shopId, String page, String pageSize) {
        return Api.getInstance().service.getRecord(shopId, page, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
