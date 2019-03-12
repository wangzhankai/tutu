package com.superpeer.tutuyoudian.activity.storeusernew;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface StoreUserNewContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> activation(String shopId, String feeSettingId, String visitCode, String type);

        Observable<BaseBeanResult> queryFeeSetting(String phone);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);

        void showFeeList(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void activation(String shopId, String feeSettingId, String visitCode, String type);

        public abstract void queryFeeSetting(String phone);
    }

}
