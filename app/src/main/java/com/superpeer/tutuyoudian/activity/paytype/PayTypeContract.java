package com.superpeer.tutuyoudian.activity.paytype;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public interface PayTypeContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getPayType(String shopId);

        Observable<BaseBeanResult> saveAccount(String roleType, String shopId, String openId, String unionId, String nickName);

        Observable<BaseBeanResult> saveAccountRunner(String id, String roleType, String openId, String unionId, String nickName);

    }

    interface View extends BaseView{
        void showPayType(BaseBeanResult baseBeanResult);

        void showSaveResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getPayType(String shopId);

        public abstract void saveAccount(String roleType, String shopId, String openId, String unionId, String nickName);

        public abstract void saveAccountRunner(String id, String roleType, String openId, String unionId, String nickName);

    }

}
