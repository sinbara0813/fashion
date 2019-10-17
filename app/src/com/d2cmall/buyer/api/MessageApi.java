package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/07 20:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageApi extends BaseApi {

    public long majorTypeTime6;
    public long majorTypeTime7;

    @Override
    protected String getPath() {
        return Constants.MESSAGE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }

}
