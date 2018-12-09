package com.superpeer.tutuyoudian.activity.collageset;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public interface CollageSetContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getList(String shopId);

        Observable<BaseBeanResult> delete(String groupId);

        Observable<BaseBeanResult> addCollage(String id);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);

        void showAddResult(BaseBeanResult baseBeanResult);

    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getList(String shopId);

        public abstract void delete(String groupId);

        public abstract void addCollage(String id);
    }

}
