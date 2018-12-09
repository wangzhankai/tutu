package com.superpeer.tutuyoudian.bean;

import java.util.List;

public class BaseSearchResult {

    private String status;
    private List<SearchData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SearchData> getData() {
        return data;
    }

    public void setData(List<SearchData> data) {
        this.data = data;
    }

    public class SearchData {
        private String title;
        private String address;
        private SearchLocation location;
        private AdInfo ad_info;

        public SearchLocation getLocation() {
            return location;
        }

        public void setLocation(SearchLocation location) {
            this.location = location;
        }

        public AdInfo getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfo ad_info) {
            this.ad_info = ad_info;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

    public class SearchLocation {
        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }

    public class AdInfo {
        private String adcode;
        private String province;
        private String city;
        private String district;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }
}
