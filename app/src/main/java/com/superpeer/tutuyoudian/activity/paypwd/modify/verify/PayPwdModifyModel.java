package com.superpeer.tutuyoudian.activity.paypwd.modify.verify;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import retrofit2.http.Field;
import rx.Observable;
import rx.functions.Func1;

public class PayPwdModifyModel implements PayPwdModifyContract.Model {

    @Override
    public Observable<BaseBeanResult> checkPayPwd(String id, String payPwd, String shopId, String roleType) {
        return Api.getInstance().service.checkPayPwd(id, payPwd, shopId, roleType).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
