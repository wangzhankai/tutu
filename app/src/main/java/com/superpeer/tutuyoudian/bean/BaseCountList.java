package com.superpeer.tutuyoudian.bean;

public class BaseCountList {
    private String date;
    private String visitorNum;
    private String browseNum;
    private String payOrderNum;
    private String sendOrderNum;
    private String money;

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public String getSendOrderNum() {
        return sendOrderNum;
    }

    public void setSendOrderNum(String sendOrderNum) {
        this.sendOrderNum = sendOrderNum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(String visitorNum) {
        this.visitorNum = visitorNum;
    }

    public String getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(String browseNum) {
        this.browseNum = browseNum;
    }
}
