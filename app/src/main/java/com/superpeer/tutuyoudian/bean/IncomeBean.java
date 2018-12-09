package com.superpeer.tutuyoudian.bean;

public class IncomeBean {

    private float visitorNum;
    private float browseNum;

    private float payOrderNum;
    private float sendOrderNum;

    public float getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(float payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public float getSendOrderNum() {
        return sendOrderNum;
    }

    public void setSendOrderNum(float sendOrderNum) {
        this.sendOrderNum = sendOrderNum;
    }

    public float getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(float visitorNum) {
        this.visitorNum = visitorNum;
    }

    public float getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(float browseNum) {
        this.browseNum = browseNum;
    }
}
