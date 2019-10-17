package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/3/28.
 * Description : PromotionsApi
 */

public class PromotionsApi extends BaseApi {

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

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public void setCategoryId(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    private Integer p;
    private Integer pageSize;
    private String keywords;
    private String o;
    private String preParam;
    private Integer min;
    private Integer max;
    private String designers;
    private String appParams;
    public void setHasPromotion(Boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public void setAfter(Boolean after) {
        isAfter = after;
    }

    private String productSellType;
    private Integer topId;//顶级
    private String categoryIds;//二级
    private Boolean hasPromotion;//促销
    private Integer subscribe;//试衣
    private Boolean isAfter;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }
}
