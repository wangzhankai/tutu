package com.superpeer.tutuyoudian.activity.paypwd.setting;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class PayPwdAgainSettingModel implements PayPwdAgainSettingContract.Model {
    @Override
    public Observable<BaseBeanResult> setPayPwd(String id, String payPwd, String shopId, String type) {
        return Api.getInstance().service.setPayPassword(id, payPwd, shopId, type).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
