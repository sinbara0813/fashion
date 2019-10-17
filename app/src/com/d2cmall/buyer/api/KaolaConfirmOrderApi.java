package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by rookie on 2018/6/7.
 */

public class KaolaConfirmOrderApi extends BaseApi {

    private String goodsId;//商品款号
    private String skuId;//商品条码
    private String buyAmount;//商品数量
    private String channelSalePrice;//活动后的价格
    private String name;//收货人姓名
    private String provinceName;//省份名称
    private String cityName;//城市名称
    private String districtName;//地区名称
    private String address;//街道名称

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public void setChannelSalePrice(String channelSalePrice) {
        this.channelSalePrice = channelSalePrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected String getPath() {
        return Constants.CREATE_KAOLA_OUTSEA_ORDER;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
