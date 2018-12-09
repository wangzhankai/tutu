package com.superpeer.tutuyoudian.activity.login;


import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface LoginContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> login(String name, String pwd, String token);
    }

    interface View extends BaseView {
        void showLoginResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void login(String name, String pwd, String token);
    }

}
