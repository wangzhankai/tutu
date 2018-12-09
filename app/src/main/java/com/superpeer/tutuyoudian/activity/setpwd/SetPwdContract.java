package com.superpeer.tutuyoudian.activity.setpwd;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface SetPwdContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> resetPwd(String phone, String pwd, String content, String deviceId);
    }

    interface View extends BaseView {
        void showResetResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void resetPwd(String phone, String pwd, String content, String deviceId);
    }
}
