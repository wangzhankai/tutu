package com.superpeer.tutuyoudian.activity.announce.detail;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public interface AnnounceDetailContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getNoticeDetail(String noticeId, String ShopId);
    }

    interface View extends BaseView{
        void showDetail(BaseBeanResult baseBeanResult);
    }

    public abstract class Presenter extends BasePresenter<View, Model>{
        abstract void getNoticeDetail(String noticeId, String shopId);
    }

}
