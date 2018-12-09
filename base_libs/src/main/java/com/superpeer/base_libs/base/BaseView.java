package com.superpeer.base_libs.base;

/**
 * Created by wangzhankai on 2018/2/7.
 */

public interface BaseView {
    /**内嵌加载**/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
