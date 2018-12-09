package com.superpeer.tutuyoudian.activity.modifypwd;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface ModifyPwdContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> updatePassword(String id, String oldPwd, String pwd, String shopId, String roleType);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void updatePassword(String id, String oldPwd, String pwd, String shopId, String roleType);
    }

}
