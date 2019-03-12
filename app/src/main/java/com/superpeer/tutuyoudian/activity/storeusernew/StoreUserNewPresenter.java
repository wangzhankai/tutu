package com.superpeer.tutuyoudian.activity.storeusernew;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class StoreUserNewPresenter extends StoreUserNewContract.Presenter {
    @Override
    public void activation(String shopId, String feeSettingId, String visitCode, String type) {
        mRxManage.add(mModel.activation(shopId, feeSettingId, visitCode, type).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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

    @Override
    public void queryFeeSetting(String phone) {
        mRxManage.add(mModel.queryFeeSetting(phone).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showFeeList(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
