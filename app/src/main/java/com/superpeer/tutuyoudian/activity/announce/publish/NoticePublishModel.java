package com.superpeer.tutuyoudian.activity.announce.publish;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class NoticePublishModel implements NoticePublishContract.Model {
    @Override
    public Observable<BaseBeanResult> publish(String shopId, String content) {
        return Api.getInstance().service.publish(shopId, content).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
