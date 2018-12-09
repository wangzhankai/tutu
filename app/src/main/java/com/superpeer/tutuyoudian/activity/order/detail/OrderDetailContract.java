package com.superpeer.tutuyoudian.activity.order.detail;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface OrderDetailContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getDetail(String orderId);

        Observable<BaseBeanResult> cancelOrder(String orderId);
        //接单
        Observable<BaseBeanResult> getOrder(String orderId);
        //删除订单
        Observable<BaseBeanResult> delOrder(String orderId);
        //订单送达
        Observable<BaseBeanResult> confirmOrder(String orderId);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);

        void showCancelResult(BaseBeanResult baseBeanResult);

        void showGetResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);

        void showConfirmResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getDetail(String orderId);

        public abstract void cancelOrder(String orderId);

        public abstract void getOrder(String orderId);

        public abstract void delOrder(String orderId);

        public abstract void confirmOrder(String orderId);
    }
}
