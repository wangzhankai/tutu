package com.superpeer.tutuyoudian.activity.selectshopsearch;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class SelectShopSearchPresenter extends SelectShopSearchContract.Presenter {

    @Override
    public void getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name) {
        mRxManage.add(mModel.getGoods(shopId, goodsTypeId, saleState, stock, defaultCurrent, pageSize, name).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
}
