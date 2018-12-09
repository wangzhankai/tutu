package com.superpeer.tutuyoudian.activity.welcome;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public class WelcomeModel implements WelcomeContract.Model {
    @Override
    public Observable<BaseBeanResult> getAgreement(String type) {
        return Api.getInstance().service.getAgreement(type).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
