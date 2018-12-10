package com.superpeer.tutuyoudian.frament.driver;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverOrderPresenter extends DriverOrderContract.Presenter {

    @Override
    public void getOrderList(String id, String orderStatus, String defaultCurrent, String pageSize) {
        mRxManage.add(mModel.getOrderList(id, orderStatus, defaultCurrent, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void grabOrder(String orderId, String runnerId) {
        mRxManage.add(mModel.grabOrder(orderId, runnerId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void giveUpOrder(String orderId) {
        mRxManage.add(mModel.giveUpOrder(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showGiveUpResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void confirmOrder(String orderId) {
        mRxManage.add(mModel.confirmOrder(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
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
