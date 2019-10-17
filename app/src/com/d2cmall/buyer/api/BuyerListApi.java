package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2018/4/17.
 */

public class BuyerListApi extends BaseApi {

    public String sort;//排序
    public int p;//页码
    public Long parentId;//父级id
    public Long masterId;//当身份为AM时传这个
    public Integer consume;//是否开单 0未开单
    public Integer level;//等级
    public Integer status;//试用期
    @Override
    protected String getPath() {
        return Constants.BUYER_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
