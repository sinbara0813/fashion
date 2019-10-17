package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.DELETE_ACCOUNT_URL;

/**
 * Created by rookie on 2018/1/31.
 */

public class DeleteAccountApi extends BaseApi {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected String getPath() {
        return DELETE_ACCOUNT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
