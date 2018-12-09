package com.superpeer.tutuyoudian.activity.collageorder;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class CollageOrderPresenter extends CollageOrderContract.Presenter {
    @Override
    public void getOrderList(String shopId, String page, String pageSize, String orderStatus, String shippingType) {
        mRxManage.add(mModel.getOrderList(shopId, page, pageSize, orderStatus, shippingType).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
