package com.superpeer.tutuyoudian.activity.modifypwd;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

public class ModifyPwdModel implements ModifyPwdContract.Model {
    @Override
    public Observable<BaseBeanResult> updatePassword(String id, String oldPwd, String pwd, String shopId, String roleType) {
        return Api.getInstance().service.updatePassword(id, oldPwd, pwd, shopId, roleType).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
