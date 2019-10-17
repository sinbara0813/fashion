package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

import java.io.Serializable;

public class CreateOrderApi extends BaseApi implements Serializable {

    //private String paymentType;
    private String tempId;
    private String memo;
    private long addressId;
    private String coupons;
    private String cartItemIds;
    private String skuId;
    private String quantity;
    //private String price;
    private String goodPromotionId;
    private String orderPromotionId;
    private String productCombId;
    //private String crowdItemId;
    //private String password;
    private String unPayOrderSn;
    private String drawee;
    private Integer  redPacket; //是否用红包
    private Integer parent_id;
    private Double longitude=0.0;
    private Double latitude=0.0;
    private String warehouseId;//仓库id 考拉商品必须穿,D2C自营商品传0,有逗号相隔
    private String warehouseName;//仓库名 同上
    private String taxAmount;//税费,没有传0
    private String identityId;//身份证号码
    private double freight;//考拉的运费

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    @Override
    protected String getPath() {
        return Constants.CREATE_ORDER_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCoupons() {
        return coupons;
    }

    public void setCoupons(String couponsCode) {
        this.coupons = couponsCode;
    }

    public String getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(String cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getGoodPromotionId() {
        return goodPromotionId;
    }

    public void setGoodPromotionId(String goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    public String getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(String orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public String getProductCombId() {
        return productCombId;
    }

    public void setProductCombId(String productCombId) {
        this.productCombId = productCombId;
    }

    public String getNoPayOrderSn() {
        return unPayOrderSn;
    }

    public void setNoPayOrderSn(String unPayOrderSn) {
        this.unPayOrderSn = unPayOrderSn;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public void setRedPacket(int redPacket) {
        this.redPacket = redPacket;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
