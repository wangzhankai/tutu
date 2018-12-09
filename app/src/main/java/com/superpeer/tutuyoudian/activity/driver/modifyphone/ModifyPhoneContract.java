package com.superpeer.tutuyoudian.activity.driver.modifyphone;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface ModifyPhoneContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> modifyPhone(String messageType, String phone);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void moidfyPhone(String message, String phone);
    }

}
