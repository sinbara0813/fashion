package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL;

/**
 * Created by rookie on 2018/4/16.
 */

public class BuyerVisitorApi extends BaseApi {

    public String event="买手店-访问";
    public String fieldName="targetId";
    public long fieldValue;
    public Integer p;
    public Integer pageSize;
    public Method method = Method.POST;

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return method;
    }
}
