package com.d2cmall.buyer.bean;

import java.util.List;

public class ProvinceBean {
    private int code;
    private String name;
    private List<CityBean> children;

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

    public List<CityBean> getChildren() {
        return children;
    }

    public void setChildren(List<CityBean> children) {
        this.children = children;
    }
}
