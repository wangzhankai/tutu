package com.superpeer.tutuyoudian.activity.stocksearch;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.functions.Func1;

public class StockSearchPresenter extends StockSearchContract.Presenter {
    @Override
    public void getStockSearch(String shopId, String name, String defaultCurrent, String pageSize) {
        mRxManage.add(mModel.getStockSearch(shopId, name, defaultCurrent, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showStockResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void modifySaleState(String goodsId, String saleState) {
        mRxManage.add(mModel.modifySaleState(goodsId, saleState).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showModifyResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void delMyGoods(String goodsId) {
        mRxManage.add(mModel.delMyGoods(goodsId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showDeleteResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

}
