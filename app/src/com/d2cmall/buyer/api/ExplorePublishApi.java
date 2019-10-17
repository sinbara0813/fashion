package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

import java.io.Serializable;

/**
 * 朋友圈发布动态
 * Author: Blend
 * Date: 16/4/9 17:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ExplorePublishApi extends BaseApi implements Serializable{

    private String pic;
    public String description;
    private String x;
    private String y;
    private String street;
    public String city;
    private Integer type;
    public String video;
    public String taskIds;
    public long timeLength;
    private long topicId;
    private String productIds;
    public Integer commentId;
    public String temp;
    public String weather;
    public String camera;
    public Integer open;
    public String transactionTime;

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
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
