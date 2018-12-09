package com.superpeer.tutuyoudian.activity.welcome;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public class WelcomePresenter extends WelcomeContract.Presenter {
    @Override
    public void getAgreement(String type) {
        mRxManage.add(mModel.getAgreement(type).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
