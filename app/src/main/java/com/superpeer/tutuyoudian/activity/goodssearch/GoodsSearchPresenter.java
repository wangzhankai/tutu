package com.superpeer.tutuyoudian.activity.goodssearch;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class GoodsSearchPresenter extends GoodsSearchContract.Presenter {

    @Override
    public void getGoods(String typeId, String shopId, String page, String size, String name) {
        mRxManage.add(mModel.getGoods(typeId, shopId, page, size, name).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showGoodsResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void saveGoods(String shopId, String goodsBankId) {
        mRxManage.add(mModel.saveGoods(shopId, goodsBankId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
