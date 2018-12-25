package com.superpeer.tutuyoudian.wxapi;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.api.WxApi;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.WxBean;

import rx.Observable;

public interface WXEntryContract {

    interface Model extends BaseModel{

        Observable<BaseBeanResult> appOauth(String shopId, String runnerId, String code);

        Observable<WxBean> access_token(String appid, String secret, String code, String type);

        Observable<WxBean> getUserInfo(String token, String appid);

        Observable<BaseBeanResult> saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);

        Observable<BaseBeanResult> saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);
    }

    interface View extends BaseView{

        void showOauth(BaseBeanResult baseBeanResult);

        void showInfoResult(WxBean bean);

        void showToken(WxBean bean);

        void showSaveResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract void appOauth(String shopId, String runnerId, String code);

        public abstract void getUserInfo(String token, String appid);

        public abstract void access_token(String appid, String secret, String code, String type);

        public abstract void saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);

        public abstract void saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);
    }

}
