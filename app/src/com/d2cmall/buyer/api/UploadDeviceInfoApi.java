package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/1/23.
 * Description : UploadDeviceInfoApi
 */

public class UploadDeviceInfoApi extends BaseApi {

    public Integer getLpx() {
        return lpx;
    }

    public void setLpx(Integer lpx) {
        this.lpx = lpx;
    }

    public Integer getHpx() {
        return hpx;
    }

    public void setHpx(Integer hpx) {
        this.hpx = hpx;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * mac地址
     */
    private String mac;
    /**
     * 水平像素
     */
    private Integer lpx;
    /**
     * 垂直像素
     */
    private Integer hpx;
    /**
     * 设备商标
     */
    private String deviceBrand;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 分辨率
     */
    private String resolution;
    /**
     * 语言
     */
    private String language;
    /**
     * 时区
     */
    private String timezone;
    /**
     * 对应版本
     */
    private String version;
    @Override
    protected String getPath() {
        return Constants.POST_UPLOAD_DEVICE_INFO_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
