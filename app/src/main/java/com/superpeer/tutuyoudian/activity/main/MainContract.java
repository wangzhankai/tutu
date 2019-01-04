package com.superpeer.tutuyoudian.activity.main;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import rx.Observable;

public interface MainContract {

    interface Model extends BaseModel {
        //获取登录公告
        Observable<BaseBeanResult> getLoginNotice(String shopId);
        //营业状态
        Observable<BaseBeanResult> changeStatus(String shopId, String status);
        //获取首页数据
        Observable<BaseBeanResult> getMainData(String shopId);
        //扫码上传
        Observable<BaseBeanResult> codeUpload(String code, String shopId);
        //设置自动接单
        Observable<BaseBeanResult> autoChange(String shopId, String status);
        //获取商品库分类
        Observable<BaseBeanResult> getCategory(String shopId);
        //抢单
        Observable<BaseBeanResult> receiptOrder(String orderId);
        //召唤跑腿
        Observable<BaseBeanResult> callRunner(String shopId, String sendStatus);

        //版本更新
        Observable<BaseBeanResult> update(String type);
    }

    interface View extends BaseView {

        void showNoticeResult(BaseBeanResult baseBeanResult);

        void showChangeResult(BaseBeanResult baseBeanResult);

        void showMainData(BaseBeanResult baseBeanResult);

        void showUpload(BaseBeanResult baseBeanResult);

        void showAutoResult(BaseBeanResult baseBeanResult);

        void showCategoryResult(BaseBeanResult baseBeanResult);

        void showGradResult(BaseBeanResult baseBeanResult);

        void showCallResult(BaseBeanResult baseBeanResult);

        void showUpdate(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getLoginNotice(String shopId);

        public abstract void changeStatus(String shopId, String status);

        public abstract void getMainData(String shopId);

        public abstract void codeUpload(String code, String shopId);

        public abstract void autoChange(String shopId, String status);

        public abstract void getCategory(String shopId);

        public abstract void receiptOrder(String orderId);

        public abstract void callRunner(String shopId, String sendStatus);

        public abstract void update(String type);

    }

}
