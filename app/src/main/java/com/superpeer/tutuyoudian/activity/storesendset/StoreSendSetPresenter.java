package com.superpeer.tutuyoudian.activity.storesendset;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class StoreSendSetPresenter extends StoreSendSetContract.Presenter {
    @Override
    public void saveDistributionInfo(String shopId, String deliveryRange, String minMoney, String packingFee, String deliveryTime, String deliveryFee, String openingTime, String closingTime, String shopDayOff) {
        mRxManage.add(mModel.saveDistributionInfo(shopId, deliveryRange, minMoney, packingFee, deliveryTime, deliveryFee, openingTime, closingTime, shopDayOff).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
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
