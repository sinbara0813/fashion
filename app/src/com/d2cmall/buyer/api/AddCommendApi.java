package com.d2cmall.buyer.api;

public class AddCommendApi extends BaseApi {

    private String content;
    private String pic;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
