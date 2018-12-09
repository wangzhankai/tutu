package com.superpeer.tutuyoudian.activity.paypwd.modify;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class PayPwdModifyAgainPresenter extends PayPwdModifyAgainContract.Presenter {
    @Override
    public void setPayPwd(String id, String payPwd, String shopId, String type) {
        mRxManage.add(mModel.setPayPwd(id, payPwd, shopId, type).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
