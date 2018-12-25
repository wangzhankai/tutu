package com.superpeer.tutuyoudian.bean;

public class BaseCountObj {

    private String visitorTotal;
    private String browseTotal;
    private String sendOrderTotal;
    private String payOrderTotal;
    private String totalAmount;

    public String getVisitorTotal() {
        return visitorTotal;
    }

    public void setVisitorTotal(String visitorTotal) {
        this.visitorTotal = visitorTotal;
    }

    public String getBrowseTotal() {
        return browseTotal;
    }

    public void setBrowseTotal(String browseTotal) {
        this.browseTotal = browseTotal;
    }

    public String getSendOrderTotal() {
        return sendOrderTotal;
    }

    public void setSendOrderTotal(String sendOrderTotal) {
        this.sendOrderTotal = sendOrderTotal;
    }

    public String getPayOrderTotal() {
        return payOrderTotal;
    }

    public void setPayOrderTotal(String payOrderTotal) {
        this.payOrderTotal = payOrderTotal;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
