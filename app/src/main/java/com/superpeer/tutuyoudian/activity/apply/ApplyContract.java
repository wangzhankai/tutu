package com.superpeer.tutuyoudian.activity.apply;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface ApplyContract {

    interface Model extends BaseModel {
        //获取协议
        Observable<BaseBeanResult> getAgreement(String type);
        //获取验证码
        Observable<BaseBeanResult> getCode(String type, String phone, String deviceId);
        //入驻提交
        Observable<BaseBeanResult> register(String phone, String content, String type, String password, String code, String token);
    }

    interface View extends BaseView {
        void showAgreementResult(BaseBeanResult baseBeanResult);

        void showCodeResult(BaseBeanResult baseBeanResult);

        void showRegisterResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getAgreement(String type);

        public abstract void getCode(String type, String phone, String deviceId);

        public abstract void register(String phone, String content, String type, String password, String code, String token);
    }

}
