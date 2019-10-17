package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/8/28.
 * Description : BizNoApi
 */

public class BizNoApi extends BaseApi{

    private String name;
    private String certNo;

    public void setName(String name) {
        this.name = name;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    @Override
    protected String getPath() {
        return Constants.CERTIFICATION_BIZNO;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
