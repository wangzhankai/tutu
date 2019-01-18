package com.superpeer.tutuyoudian.activity.order;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class OrderPresenter extends OrderContract.Presenter {
    @Override
    public void getOrderList(String shopId, String page, String pageCount, String type, String shippingType) {
        mRxManage.add(mModel.getOrderList(shopId, page, pageCount, type, shippingType).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showList(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
