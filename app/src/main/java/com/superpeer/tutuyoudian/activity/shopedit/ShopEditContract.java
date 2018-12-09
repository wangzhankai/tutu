package com.superpeer.tutuyoudian.activity.shopedit;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/22 0022.
 */

public interface ShopEditContract {

    interface Model extends BaseModel{
        //获取商品分类
        Observable<BaseBeanResult> getShopCategory(String shopId);
    }

    interface View extends BaseView{
        void showCategory(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getShopCategory(String shopId);
    }

}
