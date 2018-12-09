package com.superpeer.tutuyoudian.activity.forgot;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class ForgotPresenter extends ForgotContract.Presenter {
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
    public void check(String phone, String content, String type) {
        mRxManage.add(mModel.check(phone, content, type).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCheckResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
