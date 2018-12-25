package com.superpeer.tutuyoudian.activity.datacount;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseCountBean;
import com.superpeer.tutuyoudian.bean.BaseCountList;
import com.superpeer.tutuyoudian.bean.BaseRunBean;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class CountModel implements CountContract.Model {
    @Override
    public Observable<BaseCountBean> getVisitData(String shopId, String num) {
        return Api.getInstance().service.getVisitorNum(shopId, num).map(new Func1<BaseCountBean, BaseCountBean>() {
            @Override
            public BaseCountBean call(BaseCountBean baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseCountBean>io_main());
    }

    @Override
    public Observable<BaseCountBean> getOrderNum(String shopId, String num, String pageSize) {
        return Api.getInstance().service.getOrderNum(shopId, num, pageSize).map(new Func1<BaseCountBean, BaseCountBean>() {
            @Override
            public BaseCountBean call(BaseCountBean baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseCountBean>io_main());
    }

    @Override
    public Observable<BaseCountBean> getRunNum(String shopId, String num) {
        return Api.getInstance().service.getRunNum(shopId, num).map(new Func1<BaseCountBean, BaseCountBean>() {
            @Override
            public BaseCountBean call(BaseCountBean baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseCountBean>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getRecord(String shopId, String page, String pageSize) {
        return Api.getInstance().service.getCapitalRecord(shopId, page, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getSaleGoods(String shopId, String num, String pageSize) {
        return Api.getInstance().service.getSaleGoods(shopId, num, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
