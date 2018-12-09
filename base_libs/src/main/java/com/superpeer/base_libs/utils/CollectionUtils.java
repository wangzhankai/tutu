package com.superpeer.base_libs.utils;

import java.util.Collection;

/**
 * 集合操作工具类
 * Created by wangzhankai on 2018/2/22.
 */

public class CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }
}