package com.d2cmall.buyer.api;

public class ProductCommendApi extends BaseApi {

    private String pic;
    private String content;
    private int productScore;
    private int packageScore;
    private int deliverySpeedScore;
    private int deliveryServiceScore;
    public String video;
    public int score=1;//-1代表差评,1代表好评

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setProductScore(int productScore) {
        this.productScore = productScore;
    }

    public void setPackageScore(int packageScore) {
        this.packageScore = packageScore;
    }

    public void setDeliverySpeedScore(int deliverySpeedScore) {
        this.deliverySpeedScore = deliverySpeedScore;
    }

    public void setDeliveryServiceScore(int deliveryServiceScore) {
        this.deliveryServiceScore = deliveryServiceScore;
    }

}
