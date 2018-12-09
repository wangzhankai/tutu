package com.superpeer.tutuyoudian.activity.collageadd;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public class AddCollagePresenter extends AddCollageContract.Presenter {
    @Override
    public void setCollageInfo(String id, String shopId, String goodsId, String title, String goodsNum, String groupDesc, String price, String groupNum, String needNum, String shippingType, String keepHour, String cancelHour, String noGetHour, String noSendHour) {
        mRxManage.add(mModel.setCollageInfo(id, shopId, goodsId, title, goodsNum, groupDesc, price, groupNum, needNum, shippingType, keepHour, cancelHour, noGetHour, noSendHour).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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

    @Override
    public void getDetail(String orderId) {
        mRxManage.add(mModel.getDetail(orderId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
