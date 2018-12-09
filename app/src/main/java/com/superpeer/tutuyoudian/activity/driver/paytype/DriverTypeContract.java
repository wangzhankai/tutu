package com.superpeer.tutuyoudian.activity.driver.paytype;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.activity.paytype.PayTypeContract;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface DriverTypeContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getPayType(String id);
    }

    interface View extends BaseView{
        void showPayType(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getPayType(String id);
    }

}
