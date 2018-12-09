package com.superpeer.tutuyoudian.activity.paypwd.modify.verify;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class PayPwdModifyPresenter extends PayPwdModifyContract.Presenter {

    @Override
    public void checkPayPwd(String id, String payPwd, String shopId, String roleType) {
        mRxManage.add(mModel.checkPayPwd(id, payPwd, shopId, roleType).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
