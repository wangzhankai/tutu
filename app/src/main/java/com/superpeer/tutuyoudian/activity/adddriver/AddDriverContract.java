package com.superpeer.tutuyoudian.activity.adddriver;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface AddDriverContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getRunnerInfo(String id);

        Observable<BaseBeanResult> addRunner(String shopId, String id);
    }

    interface View extends BaseView{
        void showRunnerInfo(BaseBeanResult baseBeanResult);

        void showAddResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract void getRunnerInfo(String id);

        public abstract void addRunner(String shopId, String id);
    }

}
