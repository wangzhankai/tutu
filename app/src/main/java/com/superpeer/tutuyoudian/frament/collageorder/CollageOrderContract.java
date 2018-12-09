package com.superpeer.tutuyoudian.frament.collageorder;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface CollageOrderContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getOrderList(String shopId, String page, String pageCount, String type, String shippingType);

        Observable<BaseBeanResult> cancelOrder(String orderId);
        //删除订单
        Observable<BaseBeanResult> delOrder(String orderId);
    }

    interface View extends BaseView {
        void showListResult(BaseBeanResult baseBeanResult);

        void showCancelResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getOrderList(String shopId, String page, String pageCount, String type, String shippingType);

        public abstract void cancelOrder(String orderId);

        public abstract void delOrder(String orderId);

    }

}
