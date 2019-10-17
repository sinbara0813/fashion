package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2017/9/6.
 */

public class TopicDetailApi extends BaseApi {
    private String orderType;
    private Integer page;

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setPage(int page) {
        this.page = page;
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
