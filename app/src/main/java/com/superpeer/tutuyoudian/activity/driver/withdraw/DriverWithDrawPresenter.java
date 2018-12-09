package com.superpeer.tutuyoudian.activity.driver.withdraw;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverWithDrawPresenter extends DriverWithDrawContract.Presenter {
    @Override
    public void getAccountId(String id) {
        mRxManage.add(mModel.getAccountInfo(id).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showAccountResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveWithdraw(String id, String money, String type, String pwd) {
        mRxManage.add(mModel.saveWithdraw(id, money, type, pwd).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
