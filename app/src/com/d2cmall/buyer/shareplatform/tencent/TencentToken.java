package com.d2cmall.buyer.shareplatform.tencent;

/**
 * @author hrb
 * @Description: QQ token管理类
 * @date 2015-8-6 下午3:39:44
 */
public class TencentToken {
    private String openid;
    private String access_token;
    private String expires_in;

    public TencentToken() {
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
