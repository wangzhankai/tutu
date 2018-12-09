package com.superpeer.base_libs.utils;

import java.lang.reflect.ParameterizedType;

/**
 * 类转换初始化
 * Created by wangzhankai on 2018/2/8.
 */

public class TUtil {

    public static <T> T getT(Object o, int i){
        try{
            return ((Class<T>)((ParameterizedType)(o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className){
        try{
            return Class.forName(className);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
