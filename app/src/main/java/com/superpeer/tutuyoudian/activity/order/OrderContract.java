package com.superpeer.tutuyoudian.activity.order;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public interface OrderContract {

    interface Model extends BaseModel {
    }

    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View, Model> {
    }

}
