package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2017/12/28.
 */

public class CloseLiveApi extends BaseApi {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    protected String getPath() {
        return Constants.CLOSE_LIVE_STREAM;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
