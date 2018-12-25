package com.superpeer.tutuyoudian.activity.driver.paytype;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverTypePresenter extends DriverTypeContract.Presenter {
    @Override
    public void getPayType(String id) {
        mRxManage.add(mModel.getPayType(id).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showPayType(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveAccount(String roleType, String shopId, String openId, String unionId, String nickName) {
        mRxManage.add(mModel.saveAccount(roleType, shopId, openId, unionId, nickName).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void saveAccountRunner(String id, String roleType, String openId, String unionId, String nickName) {
        mRxManage.add(mModel.saveAccountRunner(id, roleType, openId, unionId, nickName).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
