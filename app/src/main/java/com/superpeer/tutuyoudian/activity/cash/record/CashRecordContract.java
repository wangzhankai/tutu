package com.superpeer.tutuyoudian.activity.cash.record;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public interface CashRecordContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getRecordList(String shopId, String page, String pageSize);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getRecordList(String shopId, String page, String pageSize);
    }

}
