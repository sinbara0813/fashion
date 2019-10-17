package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/09 09:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageMarkReadedApi extends BaseApi {
    @Override
    protected String getPath() {
        return Constants.MESSAGE_MARK_READED_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
