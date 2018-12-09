package com.superpeer.tutuyoudian.activity.storedriver;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverListPresenter extends DriverListContract.Presenter {
    @Override
    public void queryRunners(String shopId) {
        mRxManage.add(mModel.queryRunners(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showRunnersResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
