package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/13.
 */

public class BrandProductApi extends BaseApi {

    private Integer p;
    private Integer pageSize;
    private String keywords;
    private String o;
    private String preParam;
    private Integer t;
    private Integer c;
    private Integer min;
    private Integer max;
    private String designers;
    private String appParams;
    private String seriesIds;
    private String productSellType;
    private Integer topId;
    private Boolean hasPromotion;//促销
    private String categoryIds;
    private Boolean isAfter;
    private Integer subscribe;//试衣
    private String seriesId;

    public void setAfter(Boolean after) {
        isAfter = after;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public void setHasPromotion(Boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public void setSeriesId(String seriesIds) {
        this.seriesId = seriesIds;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setO(String o) {
        this.o = o;
    }

    public void setPreParam(String preParam) {
        this.preParam = preParam;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public void setAppParams(String appParams) {
        this.appParams = appParams;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
