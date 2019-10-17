package com.d2cmall.buyer.api;


public class VidApi extends BaseApi {

    private String vid;

    public void setVid(String vid) {
        this.vid = vid;
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
