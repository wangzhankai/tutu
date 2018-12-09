package com.superpeer.tutuyoudian.activity.paypwd.modify.verify;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.activity.paypwd.modify.PayPwdModifyAgainContract;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface PayPwdModifyContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> checkPayPwd(String id, String payPwd, String shopId, String roleType);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void checkPayPwd(String id, String payPwd, String shopId, String roleType);
    }
}
