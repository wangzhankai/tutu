package com.superpeer.tutuyoudian.activity.main;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class MainPresenter extends MainContract.Presenter {
    @Override
    public void getLoginNotice(String shopId) {
        mRxManage.add(mModel.getLoginNotice(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showNoticeResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void changeStatus(String shopId, String status) {
        mRxManage.add(mModel.changeStatus(shopId, status).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showChangeResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getMainData(String shopId) {
        mRxManage.add(mModel.getMainData(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showMainData(baseBeanResult);
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
    public void autoChange(String shopId, String status) {
        mRxManage.add(mModel.autoChange(shopId, status).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showAutoResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getCategory(String shopId) {
        mRxManage.add(mModel.getCategory(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCategoryResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void receiptOrder(String orderId) {
        mRxManage.add(mModel.receiptOrder(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showGradResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void callRunner(String shopId, String sendStatus) {
        mRxManage.add(mModel.callRunner(shopId, sendStatus).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCallResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
