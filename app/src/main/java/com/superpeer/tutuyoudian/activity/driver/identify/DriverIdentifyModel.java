package com.superpeer.tutuyoudian.activity.driver.identify;

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

public class DriverIdentifyModel implements DriverIdentifyContract.Model {
    @Override
    public Observable<BaseBeanResult> addInfos(String id, List<String> imgList, String userName, String identityCard) {
        Map<String, RequestBody> files = new HashMap<>();
        if(null!=imgList&&imgList.size()>0) {
            for(int i=0; i<imgList.size(); i++){
                File file = new File(imgList.get(i));
                if(i==0){
                    files.put("image\";filename=\"" + "frontIdentity.png", RequestBody.create(MediaType.parse("image/png"), file));
                }else if(i==1){
                    files.put("image\";filename=\"" + "reverseIdentity.png", RequestBody.create(MediaType.parse("image/png"), file));
                }
            }
        }
        RequestBody idBody = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody userNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), userName);
        RequestBody identityCardBody = RequestBody.create(MediaType.parse("multipart/form-data"), identityCard);
        return Api.getInstance().service.addInfos(idBody, files, userNameBody, identityCardBody).map(new Func1<BaseBeanResult, BaseBeanResult>() {
            @Override
            public BaseBeanResult call(BaseBeanResult baseBeanResult) {
                return baseBeanResult;
            }
        }).compose(RxSchedulers.<BaseBeanResult>io_main());
    }
}
