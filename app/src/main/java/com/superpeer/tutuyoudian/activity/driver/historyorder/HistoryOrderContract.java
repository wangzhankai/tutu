package com.superpeer.tutuyoudian.activity.driver.historyorder;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface HistoryOrderContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> queryHistoryOrder(String runnerId, String defaultCurrent, String pageSize);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void queryHistoryOrder(String runnerId, String defaultCurrent, String pageSize);;
    }

}
