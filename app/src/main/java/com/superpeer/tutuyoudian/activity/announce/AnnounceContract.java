package com.superpeer.tutuyoudian.activity.announce;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public interface AnnounceContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getNotice(String shopId, String page, String pageSize);
    }

    interface View extends BaseView {
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getNotice(String shopId, String page, String pageSize);
    }

}
