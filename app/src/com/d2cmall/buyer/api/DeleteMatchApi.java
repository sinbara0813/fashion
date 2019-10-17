package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 作者:Created by sinbara on 2018/11/26.
 * 邮箱:hrb940258169@163.com
 */

public class DeleteMatchApi extends BaseApi {

    public int id;
    public String pic;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
