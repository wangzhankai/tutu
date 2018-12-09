package com.superpeer.tutuyoudian.activity.shoplibrary;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public interface ShopLibraryContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getGoodsType(String shopId);

        Observable<BaseBeanResult> getGoods(String typeId, String shopId, String page, String size, String name);

        Observable<BaseBeanResult> saveGoods(String shopId, String goodsBankId);
    }

    interface View extends BaseView{
        void showTypeResult(BaseBeanResult baseBeanResult);

        void showGoodsResult(BaseBeanResult baseBeanResult);

        void showSaveResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract void getGoodsType(String shopId);

        public abstract  void getGoods(String typeId, String shopId, String page, String size, String name);

        public abstract void saveGoods(String shopId, String goodsBankId);
    }

}
