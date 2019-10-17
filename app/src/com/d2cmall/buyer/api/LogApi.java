package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: hrb
 * Date: 2016/12/29 16:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LogApi extends BaseApi {

    private String log;
    private String deviceToken = "null";
    private String app = "APP.Buyer.Android";

    @Override
    protected String getPath() {
        return Constants.APP_LOG_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
