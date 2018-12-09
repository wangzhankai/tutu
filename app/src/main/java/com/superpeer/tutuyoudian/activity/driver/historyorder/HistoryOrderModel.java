package com.superpeer.tutuyoudian.activity.driver.historyorder;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class HistoryOrderModel implements HistoryOrderContract.Model {
    @Override
    public Observable<BaseBeanResult> queryHistoryOrder(String runnerId, String defaultCurrent, String pageSize) {
        return Api.getInstance().service.queryHistoryOrder(runnerId, defaultCurrent, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
