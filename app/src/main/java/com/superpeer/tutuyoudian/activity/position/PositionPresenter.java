package com.superpeer.tutuyoudian.activity.position;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseLocationBean;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;
import com.superpeer.tutuyoudian.listener.OnLocationListener;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public class PositionPresenter extends PositionContract.Presenter {
    @Override
    public void getLocation(String address, String region, String key, OnLocationListener callback) {
        mRxManage.add(mModel.getLocation(address, region, key, callback).subscribe(new RxSubscriber<BaseLocationBean>(mContext, false) {
            @Override
            protected void _onNext(BaseLocationBean baseLocationBean) {
                mView.showLocationResult(baseLocationBean);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void search(String keyword, String referer, String key, String size, String index) {
        mRxManage.add(mModel.search(keyword, referer, key, size, index).subscribe(new RxSubscriber<BaseSearchResult>(mContext, false) {
            @Override
            protected void _onNext(BaseSearchResult baseBeanResult) {
                mView.showSearchResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getLocationTips(String keyword, String region, String region_fix, String page_index, String page_size, String key) {
        mRxManage.add(mModel.getLocationTips(keyword, region, region_fix, page_index, page_size, key).subscribe(new RxSubscriber<BaseSearchResult>(mContext, false) {
            @Override
            protected void _onNext(BaseSearchResult baseBeanResult) {
                mView.showLocationTipResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
