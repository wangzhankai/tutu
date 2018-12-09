package com.superpeer.tutuyoudian.activity.collageorder;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface CollageOrderContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getOrderList(String shopId, String page, String pageSize, String orderStatus, String shippingType);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getOrderList(String shopId, String page, String pageSize, String orderStatus, String shippingType);
    }

}
