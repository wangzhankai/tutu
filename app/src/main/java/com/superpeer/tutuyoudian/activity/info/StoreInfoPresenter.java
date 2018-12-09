package com.superpeer.tutuyoudian.activity.info;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import java.util.List;

public class StoreInfoPresenter extends StoreInfoContract.Presenter {
    @Override
    public void upload(String shopId, List<String> imageList) {
        mRxManage.add(mModel.upload(shopId, imageList).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("图片上传中");
            }

            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.stopLoading();
                mView.showResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
