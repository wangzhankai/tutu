package com.superpeer.tutuyoudian.frament.normalorder;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface NormalOrderContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getOrderList(String shopId, String page, String pageCount, String type, String shippingType);

        Observable<BaseBeanResult> cancelOrder(String orderId);
        //接单
        Observable<BaseBeanResult> getOrder(String orderId);
        //删除订单
        Observable<BaseBeanResult> delOrder(String orderId);
        //订单送达
        Observable<BaseBeanResult> confirmOrder(String orderId);
    }

    interface View extends BaseView {
        void showList(BaseBeanResult baseBeanResult);

        void showCancelResult(BaseBeanResult baseBeanResult);

        void showGetResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);

        void showConfirmResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getOrderList(String shopId, String page, String pageCount, String type, String shippingType);

        public abstract void cancelOrder(String orderId);

        public abstract void getOrder(String orderId);

        public abstract void delOrder(String orderId);

        public abstract void confirmOrder(String orderId);
    }

}
