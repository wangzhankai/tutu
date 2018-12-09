package com.superpeer.tutuyoudian.activity.driver.main;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface DriverMainContract {

    interface Model extends BaseModel{

//        Observable<BaseBeanResult> getOrderList(String id, String defaultCurrent, String pageSize);

        Observable<BaseBeanResult> changeReceiptStatus(String id, String status);

        Observable<BaseBeanResult> grabOrder(String orderId, String runnerId, String type);

//        Observable<BaseBeanResult> giveUpOrder(String orderId);
    }

    interface View extends BaseView{

//        void showResult(BaseBeanResult baseBeanResult);

        void showChangeResult(BaseBeanResult baseBeanResult);

        void showGradResult(BaseBeanResult baseBeanResult);

//        void showGiveUpResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

//        public abstract void getOrderList(String id, String defaultCurrent, String pageSize);

        public abstract void changeReceiptStatus(String id, String status);

        public abstract void grabOrder(String orderId, String runnerId, String type);

//        public abstract void giveUpOrder(String orderId);

    }

}
