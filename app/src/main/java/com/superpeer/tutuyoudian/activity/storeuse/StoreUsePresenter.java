package com.superpeer.tutuyoudian.activity.storeuse;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class StoreUsePresenter extends StoreUseContract.Presenter {
    @Override
    public void activation(String shopId, String feeSettingId) {
        mRxManage.add(mModel.activation(shopId, feeSettingId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void queryFeeSetting() {
        mRxManage.add(mModel.queryFeeSetting().subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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
