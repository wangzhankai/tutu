package com.superpeer.tutuyoudian.bean;

/**
 * Created by Administrator on 2018/9/20 0020.
 */

public class BaseBeanResult {

    private String code;
    private String msg;
    private BaseData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseData getData() {
        return data;
    }

    public void setData(BaseData data) {
        this.data = data;
    }
}
