package com.superpeer.tutuyoudian.activity.goodssearch;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface GoodsSearchContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getGoods(String typeId, String shopId, String page, String size, String name);

        Observable<BaseBeanResult> saveGoods(String shopId, String goodsBankId);

        //修改价格
        Observable<BaseBeanResult> updatePrice(String shopId, String goodsBankId, String price, String goodsId);
    }

    interface View extends BaseView{
        void showGoodsResult(BaseBeanResult baseBeanResult);

        void showSaveResult(BaseBeanResult baseBeanResult);

        void showUpdate(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract  void getGoods(String typeId, String shopId, String page, String size, String name);

        public abstract void saveGoods(String shopId, String goodsBankId);

        public abstract void updatePrice(String shopId, String goodsBankId, String price, String goodsId);
    }

}
