package com.superpeer.tutuyoudian.activity.announce;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class AnnouncePresenter extends AnnounceContract.Presenter{
    @Override
    public void getNotice(String shopId, String page, String pageSize) {
        mRxManage.add(mModel.getNotice(shopId, page, pageSize).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
}
