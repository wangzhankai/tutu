package com.superpeer.tutuyoudian.activity.modifypwd;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class ModifyPwdPresenter extends ModifyPwdContract.Presenter {
    @Override
    public void updatePassword(String id, String oldPwd, String pwd, String shopId, String roleType) {
        mRxManage.add(mModel.updatePassword(id, oldPwd, pwd, shopId, roleType).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
