package com.superpeer.tutuyoudian.activity.verify;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public interface VerifyContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> checkPickGoods(String pickGoods, String shopId);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void checkPickGoods(String pickGoods, String shopId);
    }

}
