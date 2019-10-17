package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class OrderItemsApi extends BaseApi {

    private int itemIndex;
    private int p;
    private int pageSize;
    private Integer commented;

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCommented(Integer commented) {
        this.commented = commented;
    }

    @Override
    protected String getPath() {
        return Constants.ORDER_ITEMS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
