package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

import java.util.TreeMap;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/6 19:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CalculateApi extends BaseApi {

    public long[] cartItemIds;
    public int[] quantity;
    public long[] goodPromotionId;
    public long[] orderPromotionId;

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
        if (quantity!=null&&quantity.length>0){
            int length=quantity.length;
            StringBuilder builder=new StringBuilder();
            for (int i=0;i<length;i++){
                builder.append(quantity[i]);
                if (i!=length-1){
                    builder.append(",");
                }
            }
            params.put("quantity",builder.toString());
        }
        return params;
    }

    @Override
    protected String getPath() {
        return Constants.CALCULATE_CART;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
