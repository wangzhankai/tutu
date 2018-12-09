package com.superpeer.tutuyoudian.bean;

public class BaseLocationBean {
    private String status;
    private String message;
    private LocationBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocationBean getResult() {
        return result;
    }

    public void setResult(LocationBean result) {
        this.result = result;
    }
}
