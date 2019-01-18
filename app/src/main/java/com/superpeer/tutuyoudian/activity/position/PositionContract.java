package com.superpeer.tutuyoudian.activity.position;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;
import com.superpeer.tutuyoudian.bean.BaseLocationBean;
import com.superpeer.tutuyoudian.bean.BaseSearchResult;
import com.superpeer.tutuyoudian.listener.OnLocationListener;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/13 0013.
 */

public interface PositionContract {

    interface Model extends BaseModel{

        Observable<BaseLocationBean> getLocationBack(String location, String key);

        Observable<BaseLocationBean> getLocation(String address, String region, String key, OnLocationListener callback);

        Observable<BaseSearchResult> search(String keyword, String referer, String key, String size, String index);

        Observable<BaseSearchResult> getLocationTips(String keyword, String region, String region_fix, String page_index, String page_size, String key);
    }

    interface View extends BaseView{

        void showLocationBackResult(BaseLocationBean baseBeanResult);

        void showLocationResult(BaseLocationBean baseBeanResult);

        void showSearchResult(BaseSearchResult baseBeanResult);

        void showLocationTipResult(BaseSearchResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{

        public abstract void getLocationBackResult(String location, String key);

        public abstract void getLocation(String address, String region, String key, OnLocationListener callback);

        public abstract void search(String keyword, String referer, String key, String size, String index);

        public abstract void getLocationTips(String keyword, String region, String region_fix, String page_index, String page_size, String key);
    }

}
