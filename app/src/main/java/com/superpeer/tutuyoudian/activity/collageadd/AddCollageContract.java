package com.superpeer.tutuyoudian.activity.collageadd;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public interface AddCollageContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> setCollageInfo(String id, String shopId, String goodsId, String title, String goodsNum, String groupDesc,
                                                  String price, String groupNum, String needNum, String shippingType, String keepHour,
                                                  String cancelHour, String noGetHour, String noSendHour);

        Observable<BaseBeanResult> getDetail(String orderId);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);

        void showDetail(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void setCollageInfo(String id, String shopId, String goodsId, String title, String goodsNum, String groupDesc,
                                   String price, String groupNum, String needNum, String shippingType, String keepHour,
                                   String cancelHour, String noGetHour, String noSendHour);

        public abstract void getDetail(String orderId);
    }

}
