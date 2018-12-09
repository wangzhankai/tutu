package com.superpeer.tutuyoudian.activity.datacount;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseCountBean;
import com.superpeer.tutuyoudian.bean.BaseRunBean;

import rx.Observable;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public interface CountContract {

    interface Model extends BaseModel {
        Observable<BaseCountBean> getVisitData(String shopId, String num);

        Observable<BaseCountBean> getOrderNum(String shopId, String num, String pageSize);

        Observable<BaseRunBean> getRunNum(String shopId, String num);

        Observable<BaseBeanResult> getRecord(String shopId, String page, String pageSize);
    }

    interface View extends BaseView {
        void showVisitResult(BaseCountBean baseBeanResult);

        void showOrderResult(BaseCountBean baseBeanResult);

        void showRunResult(BaseRunBean baseBeanResult);

        void showRecordResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getVisitData(String shopId, String num);

        public abstract void getOrderNum(String shopId, String num, String pageSize);

        public abstract void getRunNum(String shopId, String num);

        public abstract void getRecord(String shopId, String page, String pageSize);

    }

}
