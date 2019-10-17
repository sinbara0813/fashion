package com.d2cmall.buyer.api;

public class CartUpdateApi extends BaseApi {

    private int id;
    private int quantity;
    private long goodPromotionId;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setGoodPromotionId(long goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
