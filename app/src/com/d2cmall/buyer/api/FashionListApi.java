package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 作者:Created by sinbara on 2018/11/23.
 * 邮箱:hrb940258169@163.com
 */

public class FashionListApi extends BaseApi {

    public String beginDate;
    public String endDate;
    public Long memberId;

    @Override
    protected String getPath() {
        return Constants.WARDROBE_COLLOCATION_LIST;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
