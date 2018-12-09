package com.superpeer.tutuyoudian.bean;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

import java.io.Serializable;

public class DizhiBean implements Serializable {
    private LatLng mLatlng;
    private String  address;

    public LatLng getmLatlng() {
        return mLatlng;
    }

    public void setmLatlng(LatLng mLatlng) {
        this.mLatlng = mLatlng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}