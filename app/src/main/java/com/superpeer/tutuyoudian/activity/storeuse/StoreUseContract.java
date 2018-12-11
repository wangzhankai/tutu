package com.superpeer.tutuyoudian.activity.storeuse;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface StoreUseContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> activation(String shopId, String feeSettingId);

        Observable<BaseBeanResult> queryFeeSetting();
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);

        void showFeeList(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void activation(String shopId, String feeSettingId);

        public abstract void queryFeeSetting();
    }

}
