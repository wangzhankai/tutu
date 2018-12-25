package com.superpeer.tutuyoudian.activity.datacount;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseCountBean;
import com.superpeer.tutuyoudian.bean.BaseCountData;
import com.superpeer.tutuyoudian.bean.BaseCountList;
import com.superpeer.tutuyoudian.bean.BaseRunBean;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class CountPresenter extends CountContract.Presenter {
    @Override
    public void getVisitData(String shopId, String num) {
        mRxManage.add(mModel.getVisitData(shopId, num).subscribe(new RxSubscriber<BaseCountBean>(mContext, false) {
            @Override
            protected void _onNext(BaseCountBean baseBeanResult) {
                mView.showVisitResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getOrderNum(String shopId, String num, String pageSize) {
        mRxManage.add(mModel.getOrderNum(shopId, num, pageSize).subscribe(new RxSubscriber<BaseCountBean>(mContext, false) {
            @Override
            protected void _onNext(BaseCountBean baseBeanResult) {
                mView.showOrderResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getRunNum(String shopId, String num) {
        mRxManage.add(mModel.getRunNum(shopId, num).subscribe(new RxSubscriber<BaseCountBean>(mContext, false) {
            @Override
            protected void _onNext(BaseCountBean baseBeanResult) {
                mView.showRunResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getRecord(String shopId, String page, String pageSize) {
        mRxManage.add(mModel.getRecord(shopId, page, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showRecordResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getSaleGoods(String shopId, String num, String pageSize) {
        mRxManage.add(mModel.getSaleGoods(shopId, num, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showSaleResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
