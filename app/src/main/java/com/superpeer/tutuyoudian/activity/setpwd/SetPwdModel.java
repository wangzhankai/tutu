package com.superpeer.tutuyoudian.activity.setpwd;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.functions.Func1;

public class SetPwdModel implements SetPwdContract.Model {
    @Override
    public rx.Observable<BaseBeanResult> resetPwd(String phone, String pwd, String content, String deviceId) {
        return Api.getInstance().service.reset(phone, pwd, content, deviceId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
