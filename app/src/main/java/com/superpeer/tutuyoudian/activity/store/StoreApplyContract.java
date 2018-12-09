package com.superpeer.tutuyoudian.activity.store;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface StoreApplyContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getCategory(String code);

        Observable<BaseBeanResult> getArea();

        Observable<BaseBeanResult> saveInfo(String shopId, String accountId, String name, String image, String type, String typeName, String businessScope, String areaCode, String longitude, String latitude, String address, String bossName, String phone);
    }

    interface View extends BaseView {
        void showCategory(BaseBeanResult baseBeanResult);

        void showArea(BaseBeanResult baseBeanResult);

        void showSaveResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getCategory(String code);

        public abstract void getArea();

        public abstract void saveInfo(String shopId, String accountId, String name, String image, String type, String typeName, String businessScope, String areaCode, String longitude, String latitude, String address, String bossName, String phone);
    }

}
