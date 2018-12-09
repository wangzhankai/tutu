package com.superpeer.tutuyoudian.activity.announce;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class AnnounceModel implements AnnounceContract.Model{
    @Override
    public Observable<BaseBeanResult> getNotice(String shopId, String page, String pageSize) {
        return Api.getInstance().service.getNotice(shopId, page, pageSize).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
