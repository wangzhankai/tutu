package com.superpeer.tutuyoudian.activity.verify;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class VerifyModel implements VerifyContract.Model {
    @Override
    public Observable<BaseBeanResult> checkPickGoods(String pickGoods, String shopId) {
        return Api.getInstance().service.checkPickGoods(pickGoods, shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
