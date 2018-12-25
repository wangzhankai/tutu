package com.superpeer.tutuyoudian.activity.cash.withdraw;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class CashWithDrawModel implements CashWithDrawContract.Model {
    @Override
    public Observable<BaseBeanResult> getAccountInfo(String shopId) {
        return Api.getInstance().service.getCashData(shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveWithDraw(String shopId, String money, String type, String payPwd) {
        return Api.getInstance().service.saveWithDraw(shopId, money, type, payPwd).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

}
