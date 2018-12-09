package com.superpeer.tutuyoudian.activity.driver.paytype;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverTypePresenter extends DriverTypeContract.Presenter {
    @Override
    public void getPayType(String id) {
        mRxManage.add(mModel.getPayType(id).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {

            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
