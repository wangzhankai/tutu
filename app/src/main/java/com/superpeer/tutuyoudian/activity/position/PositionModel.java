package com.superpeer.tutuyoudian.activity.position;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.TencentApi;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseLocationBean;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;
import com.superpeer.tutuyoudian.listener.OnLocationListener;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public class PositionModel implements PositionContract.Model {
    @Override
    public Observable<BaseLocationBean> getLocation(String address, String region, String key, OnLocationListener callback) {
        return TencentApi.getInstance().service.getLocation(address, region, key, callback).map(new Func1<BaseLocationBean, BaseLocationBean>() {
            @Override
            public BaseLocationBean call(BaseLocationBean baseSearchResult) {
                return baseSearchResult;
            }
        }).compose(RxSchedulers.<BaseLocationBean>io_main());
    }

    @Override
    public Observable<BaseSearchResult> search(String keyword, String referer, String key, String size, String index) {
        return TencentApi.getInstance().service.search(keyword, referer, key, size, index).map(new Func1<BaseSearchResult, BaseSearchResult>() {
            @Override
            public BaseSearchResult call(BaseSearchResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseSearchResult>io_main());
    }

    @Override
    public Observable<BaseSearchResult> getLocationTips(String keyword, String region, String region_fix, String page_index, String page_size, String key) {
        return TencentApi.getInstance().service.getLocationTips(keyword, region, region_fix, page_index, page_size, key).map(new Func1<BaseSearchResult, BaseSearchResult>() {
            @Override
            public BaseSearchResult call(BaseSearchResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseSearchResult>io_main());
    }
}
