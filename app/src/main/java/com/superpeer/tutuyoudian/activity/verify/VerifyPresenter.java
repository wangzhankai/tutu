package com.superpeer.tutuyoudian.activity.verify;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class VerifyPresenter extends VerifyContract.Presenter {
    @Override
    public void checkPickGoods(String pickGoods, String shopId) {
        mRxManage.add(mModel.checkPickGoods(pickGoods, shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
