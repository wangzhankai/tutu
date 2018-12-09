package com.superpeer.tutuyoudian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/23 0023.
 */

public class City implements Serializable {

    private String code;
    private String name;
    private String parentCode;
    private List<District> areas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<District> getAreas() {
        return areas;
    }

    public void setAreas(List<District> areas) {
        this.areas = areas;
    }
}
