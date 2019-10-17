package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/13 16:42
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PublishProductReportApi extends BaseApi{

    private String pic;
    public String description;
    private String x;
    private String y;
    private String street;
    private String city;
    private Integer type;
    public String video;
    public String taskIds;
    public long timeLength;
    private long topicId;
    private String productIds;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    public void setContent(String content) {
        this.content = content;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    private String productImg;

    private String content;

    public void setProductId(long id) {
        this.productId = id;
    }

    private long productId;

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTopicId(long topicId){
        this.topicId=topicId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Override
    protected String getPath() {
        return Constants.PRODUCT_REPORT_SUBMIT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
