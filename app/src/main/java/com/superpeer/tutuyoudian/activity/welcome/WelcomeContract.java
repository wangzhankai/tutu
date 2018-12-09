package com.superpeer.tutuyoudian.activity.welcome;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public interface WelcomeContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getAgreement(String type);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getAgreement(String type);
    }

}
