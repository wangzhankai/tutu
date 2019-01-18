package com.superpeer.tutuyoudian.activity.shopmanager;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/27 0027.
 */

public class ShopManagerPresenter extends ShopManagerContract.Presenter {
    @Override
    public void getCategory(String shopId, String saleState) {
        mRxManage.add(mModel.getCategory(shopId, saleState).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name) {
        mRxManage.add(mModel.getGoods(shopId, goodsTypeId, saleState, stock, defaultCurrent, pageSize, name).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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
    public void getStock(String shopId, String goodsTypeId, String defaultCurrent, String pageSize) {
        mRxManage.add(mModel.getStock(shopId, goodsTypeId, defaultCurrent, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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

    @Override
    public void getSortGoodsInfo(String shopId, String goodsTypeId, String page, String pageSize) {
        mRxManage.add(mModel.getSortGoodsInfo(shopId, goodsTypeId, page, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSortGoodsInfo(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void changeSort(String goodsId, String flag) {
        mRxManage.add(mModel.changeSort(goodsId, flag).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSortResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void addStock(String goodsId, String stock) {
        mRxManage.add(mModel.addStock(goodsId, stock).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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

    @Override
    public void codeUpload(String code, String shopId) {
        mRxManage.add(mModel.codeUpload(code, shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showUpload(baseBeanResult);
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
