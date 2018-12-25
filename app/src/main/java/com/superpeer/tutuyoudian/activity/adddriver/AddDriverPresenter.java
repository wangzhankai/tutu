package com.superpeer.tutuyoudian.activity.adddriver;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.tencent.tencentmap.mapsdk.maps.MapView;

public class AddDriverPresenter extends AddDriverContract.Presenter {
    @Override
    public void getRunnerInfo(String id, String shopId) {
        mRxManage.add(mModel.getRunnerInfo(id, shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showRunnerInfo(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void addRunner(String shopId, String id, String runnerType) {
        mRxManage.add(mModel.addRunner(shopId, id, runnerType).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showAddResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
