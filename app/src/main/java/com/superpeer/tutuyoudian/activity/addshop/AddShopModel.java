package com.superpeer.tutuyoudian.activity.addshop;

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

/**
 * Created by Administrator on 2018/10/13 0013.
 */

public class AddShopModel implements AddShopContract.Model {
    @Override
    public Observable<BaseBeanResult> getShopCategory(String shopId) {
        return Api.getInstance().service.getShopCategory(shopId).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }

    @Override
    public Observable<BaseBeanResult> upload(String shopId, String goodsId, String name, String bankId, String manufacturer, String barCode, String image, String type, String brand, String specifications, String price, String stock, String vipPrice, String saleState) {

        /*File file = new File(image);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestFile);*/

        Map<String, RequestBody> files = new HashMap<>();
        File file = new File(image);
        files.put("image\";filename=\"" + file.getName()+".jpg", RequestBody.create(MediaType.parse("image/png"), file));

        RequestBody shopIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), shopId);
        RequestBody goodsIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), goodsId);
        RequestBody nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody bankIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), bankId);
        RequestBody manufacturerBody = RequestBody.create(MediaType.parse("multipart/form-data"), manufacturer);
        RequestBody barCodeBody = RequestBody.create(MediaType.parse("multipart/form-data"), barCode);
        RequestBody typeBody = RequestBody.create(MediaType.parse("multipart/form-data"), type);
        RequestBody brandBody = RequestBody.create(MediaType.parse("multipart/form-data"), brand);
        RequestBody specificationsBody = RequestBody.create(MediaType.parse("multipart/form-data"), specifications);
        RequestBody priceBody = RequestBody.create(MediaType.parse("multipart/form-data"), price);
        RequestBody stockBody = RequestBody.create(MediaType.parse("multipart/form-data"), stock);
        RequestBody vipPriceBody = RequestBody.create(MediaType.parse("multipart/form-data"), vipPrice);
        RequestBody saleStateBody = RequestBody.create(MediaType.parse("multipart/form-data"), saleState);

        return Api.getInstance().service.localUpload(shopIdBody, goodsIdBody, nameBody, bankIdBody, manufacturerBody, barCodeBody, files, typeBody, brandBody, specificationsBody, priceBody, stockBody, vipPriceBody, saleStateBody).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
