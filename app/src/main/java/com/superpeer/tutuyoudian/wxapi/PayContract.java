package com.superpeer.tutuyoudian.wxapi;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface PayContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> payResult(String shopId);
    }

    interface View extends BaseView{
        void showPayResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void payResult(String shopId);
    }

}
