package com.superpeer.tutuyoudian.activity.selectshop;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class SelectShopPresenter extends SelectShopContract.Presenter {
    @Override
    public void getType(String shopId) {
        mRxManage.add(mModel.getType(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showTypeResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

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
