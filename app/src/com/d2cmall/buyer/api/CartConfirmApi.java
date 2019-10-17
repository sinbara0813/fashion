package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

import java.util.TreeMap;

public class CartConfirmApi extends BaseApi {

    private long[] cartItemIds;
    private long[] goodPromotionId;
    private long[] orderPromotionId;

    @Override
    protected String getPath() {
        return Constants.ORDER_CONFIRM_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    @Override
    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<>();
        if (cartItemIds != null && cartItemIds.length > 0) {
            int length = cartItemIds.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(cartItemIds[i]);
                if (i != length - 1) {
                    builder.append(",");
                }
            }
            params.put("cartItemIds", builder.toString());
        }
        if (goodPromotionId != null && goodPromotionId.length > 0) {
            int length = goodPromotionId.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(goodPromotionId[i]);
                if (i != length - 1) {
                    builder.append(",");
                }
            }
            params.put("goodPromotionId", builder.toString());
        }
        if (orderPromotionId != null && orderPromotionId.length > 0) {
            int length = orderPromotionId.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(orderPromotionId[i]);
                if (i != length - 1) {
                    builder.append(",");
                }
            }
            params.put("orderPromotionId", builder.toString());
        }
        return params;
    }

    public void setCartItemIds(long[] cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public void setGoodPromotionId(long[] goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    public void setOrderPromotionId(long[] orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }
}
