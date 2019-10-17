package com.d2cmall.buyer.bean;

public class UpdateInfo {

    private String lasted;
    private String url;
    private String info;
    private String size;
    private String icon;
    private String must;

    public void setLasted(String lasted) {
        this.lasted = lasted;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setMust(String must) {
        this.must = must;
    }

    public String getLasted() {
        return lasted;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return info;
    }

    public String getSize() {
        return size;
    }

    public String getIcon() {
        return icon;
    }

    public String isMust() {
        return must;
    }
}
