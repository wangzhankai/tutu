package com.superpeer.tutuyoudian.activity.announce.detail;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public class AnnounceDetailModel implements AnnounceDetailContract.Model {
    @Override
    public Observable<BaseBeanResult> getNoticeDetail(String noticeId, String ShopId) {
        return Api.getInstance().service.getNoticeDetail(noticeId, ShopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
