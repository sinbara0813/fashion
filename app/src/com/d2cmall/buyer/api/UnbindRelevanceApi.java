package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2018/1/31.
 */

public class UnbindRelevanceApi extends BaseApi {
    private String unionId;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Override
    protected String getPath() {
        return Constants.UNBIND_RELEVANCE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
