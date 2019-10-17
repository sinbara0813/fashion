package com.d2cmall.buyer.bean;

public class WxTokenBean {

    /**
     * access_token : OezXcEiiBSKSxW0eoylIeGg3uyetTl5KQzbJg1u1ib86toxw864PrGFt6ceodrRd2_3LDyqiXlJ0D6-8yr8hP1pWJzw28psrT74U73fmPLoBYW6ki6baR_3x-00mL0isj4HZpghmRW8fMIubu-creg
     * expires_in : 7200
     * refresh_token : OezXcEiiBSKSxW0eoylIeGg3uyetTl5KQzbJg1u1ib86toxw864PrGFt6ceodrRduOeLxg6H8wK5ZmoGzLKtrOJVSwZPQMInpp9-r6OZjuZe80GxVskpUhtZ_JL_KcQJrcMQxJftRs8lUjw0XNO_YA
     * openid : ovEVgwmcDvxfsMtJ0xGcfcZy8ti4
     * scope : snsapi_userinfo
     * unionid : osc3TwH142CUB30WNyDoFnVMxe-0
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public String getScope() {
        return scope;
    }

    public String getUnionid() {
        return unionid;
    }
}
