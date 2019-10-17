package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class MessagesApi extends BaseApi {

    private Integer v;
    private Integer p;
    private Integer pageSize;

    public void setV(Integer v) {
        this.v = v;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected String getPath() {
        return Constants.MESSAGES_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
