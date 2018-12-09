package com.superpeer.tutuyoudian.activity.addaccount;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/10 0010.
 */

public interface AddAccountContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getBanks();

        Observable<BaseBeanResult> getAreas();

        Observable<BaseBeanResult> getDict(String code);

        Observable<BaseBeanResult> getSubBank(String bankId, String provinceCode, String cityCode);

        Observable<BaseBeanResult> saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);

        Observable<BaseBeanResult> saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);
    }

    interface View extends BaseView{
        void showBank(BaseBeanResult baseBeanResult);

        void showAreas(BaseBeanResult baseBeanResult);

        void showDictResult(BaseBeanResult baseBeanResult);

        void showSubBank(BaseBeanResult baseBeanResult);

        void showSaveResult(BaseBeanResult baseBeanResult);

    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getBanks();

        public abstract void getAreas();

        public abstract void getDict(String code);

        public abstract void getSubBank(String bankId, String provinceCode, String cityCode);

        public abstract void saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);

        public abstract void saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName);
    }

}
