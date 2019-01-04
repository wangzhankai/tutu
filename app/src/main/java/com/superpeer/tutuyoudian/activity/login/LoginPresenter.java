package com.superpeer.tutuyoudian.activity.login;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void login(String name, String pwd, String token) {
        mRxManage.add(mModel.login(name, pwd, token).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("请稍后");
            }

            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.stopLoading();
                mView.showLoginResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void update(String type) {
        mRxManage.add(mModel.update(type).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showUpdate(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
