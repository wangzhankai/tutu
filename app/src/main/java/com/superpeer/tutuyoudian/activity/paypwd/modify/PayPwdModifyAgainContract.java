package com.superpeer.tutuyoudian.activity.paypwd.modify;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.activity.paypwd.setting.PayPwdAgainSettingContract;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface PayPwdModifyAgainContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> setPayPwd(String id, String payPwd, String shopId, String type);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void setPayPwd(String id, String payPwd, String shopId, String type);
    }
}
