package com.superpeer.tutuyoudian.activity.driver.orderdetail;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverOrderDetailPresenter extends DriverOrderDetailContract.Presenter {
    @Override
    public void getDetail(String orderId) {
        mRxManage.add(mModel.getDetail(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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

    @Override
    public void cancelOrder(String orderId) {
        mRxManage.add(mModel.cancelOrder(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCancelResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getOrder(String orderId, String runnerId) {
        mRxManage.add(mModel.getOrder(orderId, runnerId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showGetResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void delOrder(String orderId) {
        mRxManage.add(mModel.delOrder(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void confirmOrder(String orderId) {
        mRxManage.add(mModel.confirmOrder(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showConfirmResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
