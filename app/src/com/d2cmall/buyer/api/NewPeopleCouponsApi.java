package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/7/12.
 * Description : NewPeopleCouponsApi
 */

public class NewPeopleCouponsApi extends BaseApi {


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    private int groupId;
    @Override
    protected String getPath() {
        return Constants.NEW_PEOPLE_COUPONS;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
