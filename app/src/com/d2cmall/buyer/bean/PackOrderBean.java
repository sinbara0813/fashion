package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/27 20:40
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PackOrderBean extends BaseBean {

    /**
     * data : {"collageOrder":{"productId":194087,"color":"黑白","orderSn":"Q15301720181532434","groupId":42,"loginCode":"18955555555","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":15,"productName":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collageGroup":{},"totalAmount":1000,"productImage":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","totalPay":1000,"size":"S码","statusName":"待支付","id":47,"skuId":382203,"createDate":"2018/06/28 15:46:58","status":1,"memberId":2434}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * collageOrder : {"productId":194087,"color":"黑白","orderSn":"Q15301720181532434","groupId":42,"loginCode":"18955555555","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":15,"productName":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collageGroup":{},"totalAmount":1000,"productImage":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","totalPay":1000,"size":"S码","statusName":"待支付","id":47,"skuId":382203,"createDate":"2018/06/28 15:46:58","status":1,"memberId":2434}
         */

        private CollageOrderBean collageOrder;

        public CollageOrderBean getCollageOrder() {
            return collageOrder;
        }

        public void setCollageOrder(CollageOrderBean collageOrder) {
            this.collageOrder = collageOrder;
        }

        public static class CollageOrderBean {
            /**
             * productId : 194087
             * color : 黑白
             * orderSn : Q15301720181532434
             * groupId : 42
             * loginCode : 18955555555
             * memberName : null
             * payParams : ALIPAY,WXAPPPAY,WALLET
             * type : 0
             * headPic : null
             * promotionId : 15
             * productName : ZHANGSHUAI 张帅 春装黑白拼色套装 女装
             * collageGroup : {}
             * totalAmount : 1000
             * productImage : /2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg
             * totalPay : 1000
             * size : S码
             * statusName : 待支付
             * id : 47
             * skuId : 382203
             * createDate : 2018/06/28 15:46:58
             * status : 1
             * memberId : 2434
             */

            private int productId;
            private String color;
            private String orderSn;
            private int groupId;
            private String loginCode;
            private String payParams;
            private int type;
            private int promotionId;
            private String productName;
            private CollageGroupBean collageGroup;
            private double totalAmount;
            private String productImage;
            private double totalPay;
            private String size;
            private String statusName;
            private int id;
            private int skuId;
            private int memberId;

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public String getPayParams() {
                return payParams;
            }

            public void setPayParams(String payParams) {
                this.payParams = payParams;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(int promotionId) {
                this.promotionId = promotionId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public CollageGroupBean getCollageGroup() {
                return collageGroup;
            }

            public void setCollageGroup(CollageGroupBean collageGroup) {
                this.collageGroup = collageGroup;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public double getTotalPay() {
                return totalPay;
            }

            public void setTotalPay(double totalPay) {
                this.totalPay = totalPay;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSkuId() {
                return skuId;
            }

            public void setSkuId(int skuId) {
                this.skuId = skuId;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public static class CollageGroupBean {
            }
        }
    }
}
