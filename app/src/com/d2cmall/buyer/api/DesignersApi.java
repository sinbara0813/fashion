package com.d2cmall.buyer.api;


import com.d2cmall.buyer.Constants;


public class DesignersApi extends BaseApi {

    private String tagId;
    private String designArea;
    private String country;
    private String style;
    private int p;
    private int pageSize;

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setDesignArea(String designArea) {
        this.designArea = designArea;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected String getPath() {
        return Constants.DESIGNERS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
