package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2017/10/19.
 */

public class QuestionApi extends BaseApi {
    private String mobile;
    private String content;
    private String type;
    private String pic;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    protected String getPath() {
        return Constants.UPDATE_QUESTION;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
