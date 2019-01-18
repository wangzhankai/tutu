package com.superpeer.tutuyoudian.activity.shoplibrary;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class ShopLibraryPresenter extends ShopLibraryContract.Presenter {
    @Override
    public void getGoodsType(String shopId) {
        mRxManage.add(mModel.getGoodsType(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void getGoods(String typeId, String shopId, String page, String size, String name) {
        mRxManage.add(mModel.getGoods(typeId, shopId, page, size, name).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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
        mRxManage.add(mModel.saveGoods(shopId, goodsBankId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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

    @Override
    public void updatePrice(String shopId, String goodsBankId, String price, String goodsId) {
        mRxManage.add(mModel.updatePrice(shopId, goodsBankId, price, goodsId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showUpdate(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
