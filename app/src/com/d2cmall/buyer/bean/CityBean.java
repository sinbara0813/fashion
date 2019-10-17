package com.d2cmall.buyer.bean;

import java.util.List;

public class CityBean {
    private int code;
    private String name;
    private List<DistrictBean> children;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictBean> getChildren() {
        return children;
    }

    public void setChildren(List<DistrictBean> children) {
        this.children = children;
    }
}
