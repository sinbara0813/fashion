package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/8/28.
 */

public class IdentityCardAddApi extends BaseApi {

    public String realName;
    public String identityCard;
    public String authenticate="1";
    public Integer id;

    @Override
    protected String getPath() {
        return Constants.CERTIFICATION_ADD;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
