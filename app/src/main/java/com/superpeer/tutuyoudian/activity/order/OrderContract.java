package com.superpeer.tutuyoudian.activity.order;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public interface OrderContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getOrderList(String shopId, String page, String pageCount, String type, String shippingType);
    }

    interface View extends BaseView {
        void showList(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getOrderList(String shopId, String page, String pageCount, String type, String shippingType);
    }

}
