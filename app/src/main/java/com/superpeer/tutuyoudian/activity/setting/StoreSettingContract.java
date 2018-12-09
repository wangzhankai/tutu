package com.superpeer.tutuyoudian.activity.setting;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface StoreSettingContract {

    interface Model extends BaseModel{

    }

    interface View extends BaseView{

    }

    abstract class Presenter extends BasePresenter<View, Model>{

    }

}
