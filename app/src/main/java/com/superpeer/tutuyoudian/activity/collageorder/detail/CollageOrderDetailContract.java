package com.superpeer.tutuyoudian.activity.collageorder.detail;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface CollageOrderDetailContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getOrderInfo(String orderId);

        Observable<BaseBeanResult> cancelOrder(String orderId);
        //接单
        Observable<BaseBeanResult> getOrder(String orderId);
        //删除订单
        Observable<BaseBeanResult> delOrder(String orderId);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);

        void showCancelResult(BaseBeanResult baseBeanResult);

        void showGetResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getOrderInfo(String orderId);

        public abstract void cancelOrder(String orderId);

        public abstract void getOrder(String orderId);

        public abstract void delOrder(String orderId);
    }
}
