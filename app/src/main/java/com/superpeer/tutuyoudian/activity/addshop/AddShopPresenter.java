package com.superpeer.tutuyoudian.activity.addshop;

import com.superpeer.tutuyoudian.api.RxSubscriber;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class AddShopPresenter extends AddShopContract.Presenter {
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

    @Override
    public void upload(String shopId, String goodsId, String name, String bankId, String manufacturer, String barCode, String image, String type, String brand, String specifications, String price, String stock, String vipPrice, String saleState) {
        mRxManage.add(mModel.upload(shopId, goodsId, name, bankId, manufacturer, barCode, image, type, brand, specifications, price, stock, vipPrice, saleState).subscribe(new RxSubscriber<BaseBeanResult>(mContext, true) {
            @Override
            protected void _onNext(BaseBeanResult baseBeanResult) {
                mView.showUploadResult(baseBeanResult);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
