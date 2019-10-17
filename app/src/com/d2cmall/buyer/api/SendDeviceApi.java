package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;


public class SendDeviceApi extends BaseApi {

    private long memberId;
    private String deviceLabel;
    private String device;
    private String platform;
    private String clientId;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    protected String getPath() {
        return Constants.SEND_DEVICE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
