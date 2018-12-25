package com.superpeer.tutuyoudian.activity.cash.withdraw;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public interface CashWithDrawContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getAccountInfo(String shopId);

        Observable<BaseBeanResult> saveWithDraw(String shopId, String money, String type, String payPwd);
    }

    interface View extends BaseView{
        void showAccountResult(BaseBeanResult baseBeanResult);

        void showSaveResult(BaseBeanResult baseBeanResult);

    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getAccountInfo(String shopId);

        public abstract void saveWithDraw(String shopId, String money, String type, String payPwd);
    }

}
