package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

import java.util.TreeMap;

public class CartDeleteApi extends BaseApi {
    private long[] ids;

    @Override
    protected String getPath() {
        return Constants.CART_DELETE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<>();
        if (ids != null && ids.length > 0) {
            int length = ids.length;
            StringBuilder id = new StringBuilder();
            for (int i = 0; i < length; i++) {
                id.append(ids[i]);
                if (i != length - 1) {
                    id.append(",");
                }
            }
            params.put("ids", id.toString());
        }
        return params;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
}
