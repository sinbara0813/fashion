package com.d2cmall.buyer.bean;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ShortBean {

    /**
     * url_short : http://t.cn/ROC6IKO
     * url_long : https://www.2345.com/?38264-0010
     * type : 0
     */

    private String url_short;
    private String url_long;
    private int type;

    public String getUrl_short() {
        return url_short;
    }

    public void setUrl_short(String url_short) {
        this.url_short = url_short;
    }

    public String getUrl_long() {
        return url_long;
    }

    public void setUrl_long(String url_long) {
        this.url_long = url_long;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
