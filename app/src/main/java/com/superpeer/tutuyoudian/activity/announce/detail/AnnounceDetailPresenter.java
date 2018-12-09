package com.superpeer.tutuyoudian.activity.announce.detail;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public class AnnounceDetailPresenter extends AnnounceDetailContract.Presenter {
    @Override
    void getNoticeDetail(String noticeId, String shopId) {
        mRxManage.add(mModel.getNoticeDetail(noticeId, shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showDetail(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
