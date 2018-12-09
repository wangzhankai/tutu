package com.superpeer.tutuyoudian.activity.addshop;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public interface AddShopContract {

    interface Model extends BaseModel{
        //获取商品分类
        Observable<BaseBeanResult> getShopCategory(String shopId);

        //上传
        Observable<BaseBeanResult> upload(String shopId, String goodsId, String name, String bankId,
                                          String manufacturer, String barCode, String image,
                                          String type, String brand, String specifications,
                                          String price, String stock,
                                          String vipPrice, String saleState);
    }

    interface View extends BaseView{
        void showCategory(BaseBeanResult baseBeanResult);

        void showUploadResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getShopCategory(String shopId);

        public abstract void upload(String shopId, String goodsId, String name, String bankId,
                                    String manufacturer, String barCode, String image,
                                    String type, String brand, String specifications,
                                    String price, String stock,
                                    String vipPrice, String saleState);
    }

}
