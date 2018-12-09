package com.superpeer.tutuyoudian.activity.verify.successorfail;

import com.superpeer.base_libs.base.BaseModel;
import com.superpeer.base_libs.base.BasePresenter;
import com.superpeer.base_libs.base.BaseView;

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public interface VerifySuccessOrFailContract {

    interface Model extends BaseModel{

    }

    interface View extends BaseView{

    }

    abstract class Presenter extends BasePresenter<View, Model>{

    }

}
