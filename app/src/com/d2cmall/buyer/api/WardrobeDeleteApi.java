package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/11/28.
 * Description : WardrobeDeleteApi
 */

public class WardrobeDeleteApi extends BaseApi {
    public void setId(int id) {
        this.id = id;
    }

    private int id;
    @Override
    protected String getPath() {
        return Constants.WARDROBE_DELETE_ITEM;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
