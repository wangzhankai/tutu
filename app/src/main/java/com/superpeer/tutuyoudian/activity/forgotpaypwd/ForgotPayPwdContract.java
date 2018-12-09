package com.superpeer.tutuyoudian.activity.forgotpaypwd;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.activity.forgot.ForgotContract;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface ForgotPayPwdContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getCode(String phone, String type, String deviceId);

        Observable<BaseBeanResult> check(String phone, String content, String type);
    }

    interface View extends BaseView {
        void showCodeResult(BaseBeanResult baseBeanResult);

        void showCheckResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getCode(String phone, String type, String deviceId);

        public abstract void check(String phone, String content, String type);
    }

}
