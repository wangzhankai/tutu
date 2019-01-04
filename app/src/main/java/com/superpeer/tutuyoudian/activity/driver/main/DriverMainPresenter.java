package com.superpeer.tutuyoudian.activity.driver.main;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

public class DriverMainPresenter extends DriverMainContract.Presenter {
    /*@Override
    public void getOrderList(String id, String defaultCurrent, String pageSize) {
        mRxManage.add(mModel.getOrderList(id, defaultCurrent, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }*/

    @Override
    public void changeReceiptStatus(String id, String status) {
        mRxManage.add(mModel.changeReceiptStatus(id, status).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
    public void update(String type) {
        mRxManage.add(mModel.update(type).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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

    /*@Override
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
    }*/
}
