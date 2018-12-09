package com.superpeer.tutuyoudian.activity.info;

import com.superpeer.base_libs.baserx.RxSchedulers;
import com.superpeer.tutuyoudian.api.Api;
import com.superpeer.tutuyoudian.bean.BaseBeanResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

public class StoreInfoModel implements StoreInfoContract.Model {
    @Override
    public Observable<BaseBeanResult> upload(String shopId, List<String> imgList) {
        Map<String, RequestBody> files = new HashMap<>();
        if(null!=imgList&&imgList.size()>0) {
            for(int i=0; i<imgList.size(); i++){
                File file = new File(imgList.get(i));
                if(i==0){
                    files.put("image\";filename=\"" + "idCard.jpg", RequestBody.create(MediaType.parse("image/png"), file));
                }else if(i==1){
                    files.put("image\";filename=\"" + "businessLicense.jpg", RequestBody.create(MediaType.parse("image/png"), file));
                }else if(i==2){
                    files.put("image\";filename=\"" + "foodBusinessLicense.jpg", RequestBody.create(MediaType.parse("image/png"), file));
                }
            }
        }
        RequestBody shopIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), shopId);
        return Api.getInstance().service.uploadImgs(shopIdBody, files).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
