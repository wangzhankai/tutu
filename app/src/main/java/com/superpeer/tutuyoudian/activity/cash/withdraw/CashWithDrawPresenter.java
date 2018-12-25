package com.superpeer.tutuyoudian.activity.cash.withdraw;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class CashWithDrawPresenter extends CashWithDrawContract.Presenter {
    @Override
    public void getAccountInfo(String shopId) {
        mRxManage.add(mModel.getAccountInfo(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void saveWithDraw(String shopId, String money, String type, String payPwd) {
        mRxManage.add(mModel.saveWithDraw(shopId, money, type, payPwd).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
