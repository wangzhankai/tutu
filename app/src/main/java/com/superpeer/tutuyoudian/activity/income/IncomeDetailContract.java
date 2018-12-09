package com.superpeer.tutuyoudian.activity.income;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public interface IncomeDetailContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getDetail(String shopId, String page, String pageSize);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getDetail(String shopId, String page, String pageSize);
    }

}
