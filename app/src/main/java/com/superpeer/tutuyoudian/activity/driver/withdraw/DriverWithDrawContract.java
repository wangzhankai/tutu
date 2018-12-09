package com.superpeer.tutuyoudian.activity.driver.withdraw;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface DriverWithDrawContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getAccountInfo(String id);

        Observable<BaseBeanResult> saveWithdraw(String id, String money, String type, String pwd);
    }

    interface View extends BaseView{

        void showAccountResult(BaseBeanResult baseBeanResult);

        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract void getAccountId(String id);

        public abstract void saveWithdraw(String id, String money, String type, String pwd);
    }

}
