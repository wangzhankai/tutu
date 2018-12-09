package com.superpeer.tutuyoudian.activity.driver.historyorder;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class HistoryOrderPresenter extends HistoryOrderContract.Presenter {
    @Override
    public void queryHistoryOrder(String runnerId, String defaultCurrent, String pageSize) {
        mRxManage.add(mModel.queryHistoryOrder(runnerId, defaultCurrent, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
