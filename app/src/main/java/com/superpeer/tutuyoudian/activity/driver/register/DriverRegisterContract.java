package com.superpeer.tutuyoudian.activity.driver.register;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface DriverRegisterContract {

    interface Model extends BaseModel{
        //获取协议
        Observable<BaseBeanResult> getAgree(String type);
        //获取验证码
        Observable<BaseBeanResult> getCode(String phone, String type, String deviceId);
        //注册保存
        Observable<BaseBeanResult> register(String loginName, String password, String content, String messageType);
    }

    interface View extends BaseView{
        void showAgree(BaseBeanResult baseBeanResult);

        void showCodeResult(BaseBeanResult baseBeanResult);

        void showRegisterResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract void getAgree(String type);

        public abstract void getCode(String phone, String type, String deviceId);

        public abstract void register(String loginName, String password, String content, String messageType);
    }
}
