package com.superpeer.tutuyoudian.activity.driver.modifyphone;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class ModifyPhonePresenter extends ModifyPhoneContract.Presenter {
    @Override
    public void moidfyPhone(String id, String phone) {
        mRxManage.add(mModel.modifyPhone(id, phone).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
