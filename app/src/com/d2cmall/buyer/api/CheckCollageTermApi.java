package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/7/2.
 * Description : CheckCollageTermApi
 */

public class CheckCollageTermApi extends BaseApi {

    private int groupId;
    private int collageId;
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setCollageId(int collageId) {
        this.collageId = collageId;
    }

    @Override
    protected String getPath() {
        return Constants.GROUP_CHECK_TERM;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
