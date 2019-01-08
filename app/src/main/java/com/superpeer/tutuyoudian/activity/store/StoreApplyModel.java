package com.superpeer.tutuyoudian.activity.store;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

public class StoreApplyModel implements StoreApplyContract.Model {
    @Override
    public Observable<BaseBeanResult> getCategory(String code) {
        return Api.getInstance().service.getShopType().map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> getArea() {
        return Api.getInstance().service.getAreas().map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> saveInfo(String shopId, String accountId, String name, String image, String type, String typeName, String businessScope, String areaCode, String longitude, String latitude, String address, String bossName, String phone, String detail) {
        Map<String, RequestBody> files = new HashMap<>();
        File file = new File(image);
        files.put("image\";filename=\"" + file.getName()+".jpg", RequestBody.create(MediaType.parse("image/png"), file));

//        File file = new File(image);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody shopIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), shopId);
        RequestBody accountIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), accountId);
        RequestBody nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"), type);
        RequestBody typeNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), typeName);
        RequestBody businessScopeBody = RequestBody.create(MediaType.parse("multipart/form-data"), businessScope);
        RequestBody areaBody = RequestBody.create(MediaType.parse("multipart/form-data"), areaCode);
        RequestBody longitudeBody = RequestBody.create(MediaType.parse("multipart/form-data"), longitude);
        RequestBody latitudeBody = RequestBody.create(MediaType.parse("multipart/form-data"), latitude);
        RequestBody addressBody = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody bossNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), bossName);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody detailed = RequestBody.create(MediaType.parse("multipart/form-data"), detail);

        return Api.getInstance().service.saveShopInfo(shopIdBody, accountIdBody, nameBody, files, typeBody, typeNameBody, businessScopeBody, areaBody, longitudeBody, latitudeBody, addressBody, bossNameBody, phoneBody, detailed).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
