package com.superpeer.tutuyoudian.activity.info;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import java.util.List;

import rx.Observable;

public interface StoreInfoContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> upload(String shopId, List<String> imageList);
//        Observable<BaseBeanResult> upload(String shopId, String idCard, String businessLicense, String foodBusinessLicense);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void upload(String shopId, List<String> imageList);
//        public abstract void upload(String shopId, String idCard, String businessLicense, String foodBusinessLicense);
    }

}
