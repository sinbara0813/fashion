package com.d2cmall.buyer.api;

/**
 * Created by rookie on 2018/4/18.
 */

public class MonthSummaryApi extends BaseApi {
    private String sort="month";

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
