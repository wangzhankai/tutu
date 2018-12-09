package com.superpeer.tutuyoudian.activity.addaccount;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/11/10 0010.
 */

public class AddAccountModel implements AddAccountContract.Model {
    @Override
    public Observable<BaseBeanResult> getBanks() {
        return Api.getInstance().service.getBanks().map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getAreas() {
        return Api.getInstance().service.getAreas().map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getDict(String code) {
        return Api.getInstance().service.getDict(code).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getSubBank(String bankId, String provinceCode, String cityCode) {
        return Api.getInstance().service.getSubBranchName(bankId, provinceCode, cityCode).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        return Api.getInstance().service.saveShopAccount(roleType, shopId, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        return Api.getInstance().service.saveRunnerAccount(id, roleType, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
