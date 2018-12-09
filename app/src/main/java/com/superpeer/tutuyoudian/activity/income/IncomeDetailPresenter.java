package com.superpeer.tutuyoudian.activity.income;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class IncomeDetailPresenter extends IncomeDetailContract.Presenter{
    @Override
    public void getDetail(String shopId, String page, String pageSize) {
        mRxManage.add(mModel.getDetail(shopId, page, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
