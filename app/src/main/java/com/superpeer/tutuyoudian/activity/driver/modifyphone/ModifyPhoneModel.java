package com.superpeer.tutuyoudian.activity.driver.modifyphone;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class ModifyPhoneModel implements ModifyPhoneContract.Model {
    @Override
    public Observable<BaseBeanResult> modifyPhone(String id, String phone) {
        return Api.getInstance().service.modifyPhone(id, phone).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
