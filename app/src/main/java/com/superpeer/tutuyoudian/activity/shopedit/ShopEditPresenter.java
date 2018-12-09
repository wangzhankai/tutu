package com.superpeer.tutuyoudian.activity.shopedit;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/22 0022.
 */

public class ShopEditPresenter extends ShopEditContract.Presenter {
    @Override
    public void getShopCategory(String shopId) {
        mRxManage.add(mModel.getShopCategory(shopId).subscribe(new RxSubscriber<BaseBeanResult>(mContext, false) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showCategory(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
