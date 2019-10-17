package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.Util;

import java.io.UnsupportedEncodingException;

import static com.d2cmall.buyer.Constants.PRODUCT_THEME;

/**
 * Created by rookie on 2017/9/22.
 */

public class ProductThemeApi extends BaseApi {
    private Integer p;
    private Integer pageSize;
    private String keywords;
    //private String o;
    private String preParam;
//    private Integer min;
//    private Integer max;
//    private String designers;
    private String appParams;
//    private String productSellType;
//    private Integer topId;//顶级
//    private Integer categoryIds;//二级
//    private Integer tagId;//三级
//    private Integer hasPromotion;//促销
//    private Integer subscribe;//试衣

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeyword(String keyword) {
        this.keywords = keyword;
        String content = "keywords=" + keyword;
        String sign = Util.getMD5(content + Constants.APP_SECRET);
        String content1 = "keywords=" + keyword + "&sign=" + sign;
        try {
            appParams = Base64.encode(content1.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    public String getO() {
//        return o;
//    }
//
//    public void setO(String o) {
//        this.o = o;
//    }

    public String getPreParam() {
        return preParam;
    }

    public void setPreParam(String preParam) {
        this.preParam = preParam;
    }

//    public Integer getMin() {
//        return min;
//    }
//
//    public void setMin(Integer min) {
//        this.min = min;
//    }
//
//    public Integer getMax() {
//        return max;
//    }
//
//    public void setMax(Integer max) {
//        this.max = max;
//    }

//    public String getDesigners() {
//        return designers;
//    }
//
//    public void setDesigners(String designers) {
//        this.designers = designers;
//    }

    public String getAppParams() {
        return appParams;
    }

    public void setAppParams(String appParams) {
        this.appParams = appParams;
    }

//    public String getProductSellType() {
//        return productSellType;
//    }
//
//    public void setProductSellType(String productSellType) {
//        this.productSellType = productSellType;
//    }
//
//    public Integer getTopId() {
//        return topId;
//    }
//
//    public void setTopId(Integer topId) {
//        this.topId = topId;
//    }

//    public Integer getCategoryId() {
//        return categoryIds;
//    }
//
//    public void setCategoryId(Integer categoryIds) {
//        this.categoryIds = categoryIds;
//    }
//
//    public Integer getTagId() {
//        return tagId;
//    }
//
//    public void setTagId(Integer tagId) {
//        this.tagId = tagId;
//    }
//
//    public Integer getHasPromotion() {
//        return hasPromotion;
//    }
//
//    public void setHasPromotion(Integer hasPromotion) {
//        this.hasPromotion = hasPromotion;
//    }
//
//    public Integer getSubscribe() {
//        return subscribe;
//    }
//
//    public void setSubscribe(Integer subscribe) {
//        this.subscribe = subscribe;
//    }

    @Override
    protected String getPath() {
        return PRODUCT_THEME;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
