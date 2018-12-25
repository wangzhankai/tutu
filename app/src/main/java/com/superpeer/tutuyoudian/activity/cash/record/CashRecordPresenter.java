package com.superpeer.tutuyoudian.activity.cash.record;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class CashRecordPresenter extends CashRecordContract.Presenter {
    @Override
    public void getRecordList(String shopId, String page, String pageSize) {
        mRxManage.add(mModel.getRecordList(shopId, page, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
