package com.superpeer.tutuyoudian.bean;

import java.util.List;
import java.util.Map;

public class BaseCountData {
    private BaseCountObj object;

    public BaseCountObj getObject() {
        return object;
    }

    public void setObject(BaseCountObj object) {
        this.object = object;
    }

    private List<BaseCountList> list;

    public List<BaseCountList> getList() {
        return list;
    }

    public void setList(List<BaseCountList> list) {
        this.list = list;
    }

}
