package com.superpeer.tutuyoudian.activity.driver.record;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface WithDrawRecordContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> queryWithdrawRecord(String runnerId, String defaultCurrent, String pageSize);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void queryWithdrawRecord(String runnerId, String defaultCurrent, String pageSize);
    }

}
