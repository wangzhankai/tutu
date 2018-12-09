package com.superpeer.tutuyoudian.activity.driver.register;

import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverRegisterPresenter extends DriverRegisterContract.Presenter {
    @Override
    public void getAgree(String type) {
        mRxManage.add(mModel.getAgree("3").subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showAgree(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getCode(String phone, String type, String deviceId) {
        mRxManage.add(mModel.getCode(phone, type, deviceId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCodeResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void register(String loginName, String password, String content, String messageType) {
        mRxManage.add(mModel.register(loginName, password, content, messageType).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showRegisterResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
