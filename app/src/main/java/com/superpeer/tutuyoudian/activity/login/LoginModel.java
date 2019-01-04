package com.superpeer.tutuyoudian.activity.login;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.WxBean;

import rx.Observable;
import rx.functions.Func1;

public class LoginModel implements LoginContract.Model {
    @Override
    public rx.Observable<BaseBeanResult> login(String name, String pwd, String token) {
        return Api.getInstance().service.login(name, pwd, token).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> update(String type) {
        return Api.getInstance().service.update("0").map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
