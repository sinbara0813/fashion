package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/3/6.
 * Description : RemoveChildApi
 */

public class RemoveChildApi extends BaseApi {
    public void setChildId(long childId) {
        this.childId = childId;
    }

    private long  childId;
    @Override
    protected String getPath() {
        return Constants.GET_PARTNER_REMOVE_CHILD_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
