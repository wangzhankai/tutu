package com.superpeer.tutuyoudian.activity.driver.identify;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import java.util.List;

import rx.Observable;

public interface DriverIdentifyContract {

    interface Model extends BaseModel{
        Observable<BaseBeanResult> addInfos(String id, List<String> imgs, String userName, String identifyNum);
    }

    interface View extends BaseView{
        void showResult(BaseBeanResult baseBeanResult);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        public abstract void addInfos(String id, List<String> imgs, String userName, String identifyNum);
    }

}
