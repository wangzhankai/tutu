package com.superpeer.tutuyoudian.activity.storedriver;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface DriverListContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> queryRunners(String shopId);
    }

    interface View extends BaseView{
        void showRunnersResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void queryRunners(String shopId);
    }

}
