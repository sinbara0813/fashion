package com.d2cmall.buyer.api;


public class AddressApi extends BaseApi {

    private String name;
    private String mobile;
//    private int province;
//    private int city;
//    private int district;
    private String street;
    private String email;
    private String weixin;
    private boolean isdefault;

    private String regionPrefix;//省号
    private String regionMiddle;//市号
    private String regionSuffix;//区号
    private Integer id;
    public Double longitude;
    public Double latitude;

    public void setId(int id) {
        this.id = id;
    }

    public void setRegionPrefix(String regionPrefix) {
        this.regionPrefix = regionPrefix;
    }

    public void setRegionMiddle(String regionMiddle) {
        this.regionMiddle = regionMiddle;
    }

    public void setRegionSuffix(String regionSuffix) {
        this.regionSuffix = regionSuffix;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

//    public void setProvince(int province) {
//        this.province = province;
//    }
//
//    public void setCity(int city) {
//        this.city = city;
//    }
//
//    public void setDistrict(int district) {
//        this.district = district;
//    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDefault(boolean aDefault) {
        this.isdefault = aDefault;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
