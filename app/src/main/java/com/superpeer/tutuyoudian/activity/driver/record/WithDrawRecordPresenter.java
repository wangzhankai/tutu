package com.superpeer.tutuyoudian.activity.driver.record;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class WithDrawRecordPresenter extends WithDrawRecordContract.Presenter {
    @Override
    public void queryWithdrawRecord(String runnerId, String defaultCurrent, String pageSize) {
        mRxManage.add(mModel.queryWithdrawRecord(runnerId, defaultCurrent, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
