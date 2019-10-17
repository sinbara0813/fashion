package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

import java.util.TreeMap;

/**
 * Created by Administrator on 2018/4/2.
 */

public class SelectCouponApi extends BaseApi {

    public long[] skuIds;
    public long[] productIds;
    public long[] designerIds;
    public String coupons;

    @Override
    protected String getPath() {
        return Constants.SELECT_ORDER_COUPON;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    @Override
    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<>();
        if (skuIds != null && skuIds.length > 0) {
            int length = skuIds.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(skuIds[i]);
                if (i != length - 1) {
                    builder.append(",");
                }
            }
            params.put("skuIds", builder.toString());
        }
        if (productIds != null && productIds.length > 0) {
            int length = productIds.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(productIds[i]);
                if (i != length - 1) {
                    builder.append(",");
                }
            }
            params.put("productIds", builder.toString());
        }
        if (designerIds != null && designerIds.length > 0) {
            int length = designerIds.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(designerIds[i]);
                if (i != length - 1) {
                    builder.append(",");
                }
            }
            params.put("designerIds", builder.toString());
        }
        if (coupons!=null){
            params.put("coupons",coupons);
        }
        return params;
    }
}
