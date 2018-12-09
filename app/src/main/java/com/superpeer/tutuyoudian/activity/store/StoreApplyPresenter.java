package com.superpeer.tutuyoudian.activity.store;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class StoreApplyPresenter extends StoreApplyContract.Presenter {
    @Override
    public void getCategory(String code) {
        mRxManage.add(mModel.getCategory(code).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCategory(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getArea() {
        mRxManage.add(mModel.getArea().subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showArea(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveInfo(String shopId, String accountId, String name, String image, String type, String typeName, String businessScope, String areaCode, String longitude, String latitude, String address, String bossName, String phone) {
        mRxManage.add(mModel.saveInfo(shopId, accountId, name, image, type, typeName, businessScope, areaCode, longitude, latitude, address, bossName, phone).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSaveResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
