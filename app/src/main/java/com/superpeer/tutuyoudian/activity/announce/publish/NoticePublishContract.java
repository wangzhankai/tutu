package com.superpeer.tutuyoudian.activity.announce.publish;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface NoticePublishContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> publish(String shopId, String content);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void publish(String shopId, String content);
    }

}
