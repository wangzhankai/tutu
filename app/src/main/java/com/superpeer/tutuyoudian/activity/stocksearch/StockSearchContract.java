package com.superpeer.tutuyoudian.activity.stocksearch;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface StockSearchContract {

    interface Model extends BaseModel {
        Observable<BaseBeanResult> getStockSearch(String shopId, String name, String defaultCurrent, String pageSize);
        //上下架
        Observable<BaseBeanResult> modifySaleState(String goodsId, String saleState);

        //删除
        Observable<BaseBeanResult> delMyGoods(String goodsId);

        //修改价格
        Observable<BaseBeanResult> updatePrice(String shopId, String goodsBankId, String price, String goodsId);
    }

    interface View extends BaseView{
        void showStockResult(BaseBeanResult baseBeanResult);

        void showModifyResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);

        void showUpdate(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getStockSearch(String shopId, String name, String defaultCurrent, String pageSize);

        public abstract void modifySaleState(String goodsId, String saleState);

        public abstract void delMyGoods(String goodsId);

        public abstract void updatePrice(String shopId, String goodsBankId, String price, String goodsId);
    }

}
