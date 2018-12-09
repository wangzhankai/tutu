package com.superpeer.tutuyoudian.activity.driver.identify;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import java.util.List;

public class DriverIdentifyPresenter extends DriverIdentifyContract.Presenter {
    @Override
    public void addInfos(String id, List<String> imgs, String userName, String identityCard) {
        mRxManage.add(mModel.addInfos(id, imgs, userName, identityCard).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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
