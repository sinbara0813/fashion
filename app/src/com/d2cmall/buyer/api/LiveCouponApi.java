package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.LIVE_COUPON_URL;

/**
 * Created by rookie on 2018/2/1.
 */

public class LiveCouponApi extends BaseApi{

    private String liveId;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    @Override
    protected String getPath() {
        return LIVE_COUPON_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
