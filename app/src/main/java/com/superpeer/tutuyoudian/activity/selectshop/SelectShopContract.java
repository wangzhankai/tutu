package com.superpeer.tutuyoudian.activity.selectshop;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public interface SelectShopContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getType(String shopId);

        Observable<BaseBeanResult> getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name);
    }

    interface View extends BaseView{
        void showTypeResult(BaseBeanResult baseBeanResult);

        void showGoodsResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getType(String shopId);

        public abstract void getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name);
    }

}
