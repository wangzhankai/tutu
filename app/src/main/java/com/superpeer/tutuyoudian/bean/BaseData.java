package com.superpeer.tutuyoudian.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/9 0009.
 */

public class BaseData {

    private String total;
    private String page;
    private String pageSize;

    private BaseObject object;

    private List<BaseList> list;

    public List<BaseList> getList() {
        return list;
    }

    public void setList(List<BaseList> list) {
        this.list = list;
    }

    public BaseObject getObject() {
        return object;
    }

    public void setObject(BaseObject object) {
        this.object = object;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
