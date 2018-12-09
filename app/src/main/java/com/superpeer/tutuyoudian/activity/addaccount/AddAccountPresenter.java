package com.superpeer.tutuyoudian.activity.addaccount;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/11/10 0010.
 */

public class AddAccountPresenter extends AddAccountContract.Presenter {
    @Override
    public void getBanks() {
        mRxManage.add(mModel.getBanks().subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showBank(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getAreas() {
        mRxManage.add(mModel.getAreas().subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showAreas(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getDict(String code) {
        mRxManage.add(mModel.getDict(code).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showDictResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getSubBank(String bankId, String provinceCode, String cityCode) {
        mRxManage.add(mModel.getSubBank(bankId, provinceCode, cityCode).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSubBank(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveAccount(String roleType, String shopId, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        mRxManage.add(mModel.saveAccount(roleType, shopId, openId, unionId, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSaveResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveAccountRunner(String id, String roleType, String openId, String unionId, String bankCard, String accountName, String bankId, String bankName, String accountType, String accountProvincesCode, String accountProvinces, String accountCityCode, String accountCity, String subbranchId, String subbranchName) {
        mRxManage.add(mModel.saveAccountRunner(id, roleType, openId, unionId, bankCard, accountName, bankId, bankName, accountType, accountProvincesCode, accountProvinces, accountCityCode, accountCity, subbranchId, subbranchName).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSaveResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
