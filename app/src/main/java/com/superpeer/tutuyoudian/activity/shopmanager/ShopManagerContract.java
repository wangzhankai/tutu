package com.superpeer.tutuyoudian.activity.shopmanager;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

/**
 * Created by Administrator on 2018/10/27 0027.
 */

public interface ShopManagerContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> getCategory(String shopId, String saleState);

        Observable<BaseBeanResult> getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name);

        //库存
        Observable<BaseBeanResult> getStock(String shopId, String goodsTypeId, String page, String pageSize);
        //上下架
        Observable<BaseBeanResult> modifySaleState(String goodsId, String saleState);

        //删除
        Observable<BaseBeanResult> delMyGoods(String goodsId);

        //获取排序列表
        Observable<BaseBeanResult> getSortGoodsInfo(String shopId, String goodsTypeId, String page, String pageSize);

        //排序
        Observable<BaseBeanResult> changeSort(String goodsId, String flag);

        //添加库存
        Observable<BaseBeanResult> addStock(String goodsId, String stock);

        //扫码上传
        Observable<BaseBeanResult> codeUpload(String code, String shopId);

        //修改价格
        Observable<BaseBeanResult> updatePrice(String shopId, String goodsBankId, String price, String goodsId);
    }

    interface View extends BaseView{
        void showCategory(BaseBeanResult baseBeanResult);

        void showGoodsResult(BaseBeanResult baseBeanResult);

        void showModifyResult(BaseBeanResult baseBeanResult);

        void showDeleteResult(BaseBeanResult baseBeanResult);

        void showSortGoodsInfo(BaseBeanResult baseBeanResult);

        void showSortResult(BaseBeanResult baseBeanResult);

        void showAddResult(BaseBeanResult baseBeanResult);

        void showUpload(BaseBeanResult baseBeanResult);

        void showUpdate(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void getCategory(String shopId, String saleState);

        public abstract void getGoods(String shopId, String goodsTypeId, String saleState, String stock, String defaultCurrent, String pageSize, String name);

        public abstract void getStock(String shopId, String goodsTypeId, String defaultCurrent, String pageSize);

        public abstract void modifySaleState(String goodsId, String saleState);

        public abstract void delMyGoods(String goodsId);

        public abstract void getSortGoodsInfo(String shopId, String goodsTypeId, String page, String pageSize);

        public abstract void changeSort(String goodsId, String flag);

        public abstract void addStock(String goodsId, String stock);

        public abstract void codeUpload(String code, String shopId);

        public abstract void updatePrice(String shopId, String goodsBankId, String price, String goodsId);
    }

}
