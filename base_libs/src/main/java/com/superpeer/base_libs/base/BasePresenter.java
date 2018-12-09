package com.superpeer.base_libs.base;

import android.content.Context;

import com.superpeer.base_libs.baserx.RxManager;

/**
 * Created by wangzhankai on 2018/2/7.
 */

public class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m){
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }
    public void onStart(){

    }
    public void onDestroy(){
        mRxManage.clear();
    }
}
