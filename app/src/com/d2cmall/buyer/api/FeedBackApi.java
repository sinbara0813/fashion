package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class FeedBackApi extends BaseApi {

    private String memberName;
    private String mobile;
    private String email;
    private String content;
    private String app = "APP.Buyer.Android";

    @Override
    protected String getPath() {
        return Constants.FEED_BACK_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
