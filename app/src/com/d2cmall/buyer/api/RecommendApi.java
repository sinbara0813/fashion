package com.d2cmall.buyer.api;

public class RecommendApi extends BaseApi {

    private int p = 1;
    private String designerId;

    @Override
    protected String getPath() {
        return "/v2/api/product/recommend";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }
}
