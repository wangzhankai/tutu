package com.superpeer.tutuyoudian.activity.setpwd;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class SetPwdPresenter extends SetPwdContract.Presenter {
    @Override
    public void resetPwd(String phone, String pwd, String content, String deviceId) {
        mRxManage.add(mModel.resetPwd(phone, pwd, content, deviceId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showResetResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));

    }
}
