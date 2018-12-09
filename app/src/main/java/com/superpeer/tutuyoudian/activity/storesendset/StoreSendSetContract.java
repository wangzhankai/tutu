package com.superpeer.tutuyoudian.activity.storesendset;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface StoreSendSetContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> saveDistributionInfo(String shopId, String deliveryRange, String minMoney, String packingFee,
                                                        String deliveryTime, String deliveryFee, String openingTime, String closingTime, String shopDayOff);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void saveDistributionInfo(String shopId, String deliveryRange, String minMoney, String packingFee,
                                                  String deliveryTime, String deliveryFee, String openingTime, String closingTime, String shopDayOff);
    }

}
