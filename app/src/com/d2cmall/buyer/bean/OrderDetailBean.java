package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class OrderDetailBean extends BaseBean {

    /**
     * account : {"id":9182,"mobile":"18767165589","totalAmount":0.01,"availableAmount":0.01,"freezeAmount":0,"setPassword":true}
     * order : {"id":116614,"cartItemIds":[],"reciver":"陈艳飞","contact":"18767165589","province":"北京","city":"北京市","district":"密云县","street":"密云县通卡恶魔终于上市","cancel":true,"totalAmount":1580,"totalPay":1580,"orderSn":"Q14610453318379336","paymentType":"ALIPAY","paymentTypeName":"支付宝","orderStatus":"WaitingForPay","orderStatusName":"待付款","orderStatusCode":1,"createDate":"2016/04/19 13:54:36","endTime":"2016/04/19 16:54:36","itemTotal":1,"couponAmount":0,"tempId":"5cab1abf91ff48a3901f458c905e2a9b","shippingRates":0,"promotionAmount":0,"isOver":false,"type":"ordinary","items":[{"deliveryCorpCode":"shunfeng","id":154376,"productPrice":1580,"productCombId":0,"quantity":1,"promotionPrice":0,"totalPrice":1580,"itemStatus":"待付款","itemStatusCode":0,"skuSn":"JZ0376120900101502","productId":122015,"skuId":162248,"productImg":"/model/1602/0cd4ce8a4401418c051c50e01be474d5","productName":"FLO NAKED Wendy Zhang张韵文    允儿同款  乔其棉点混纺露肩大泡袖上衣","color":"白色","size":"M码","designerId":10393,"designer":"FLO NAKED","isComment":-1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":0,"combinationPromotionId":0}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 9182
         * mobile : 18767165589
         * totalAmount : 0.01
         * availableAmount : 0.01
         * freezeAmount : 0.0
         * setPassword : true
         */

        private AccountEntity account;
        /**
         * id : 116614
         * cartItemIds : []
         * reciver : 陈艳飞
         * contact : 18767165589
         * province : 北京
         * city : 北京市
         * district : 密云县
         * street : 密云县通卡恶魔终于上市
         * cancel : true
         * totalAmount : 1580.0
         * totalPay : 1580.0
         * orderSn : Q14610453318379336
         * paymentType : ALIPAY
         * paymentTypeName : 支付宝
         * orderStatus : WaitingForPay
         * orderStatusName : 待付款
         * orderStatusCode : 1
         * createDate : 2016/04/19 13:54:36
         * endTime : 2016/04/19 16:54:36
         * itemTotal : 1
         * couponAmount : 0.0
         * tempId : 5cab1abf91ff48a3901f458c905e2a9b
         * shippingRates : 0.0
         * promotionAmount : 0.0
         * isOver : false
         * type : ordinary
         * items : [{"deliveryCorpCode":"shunfeng","id":154376,"productPrice":1580,"productCombId":0,"quantity":1,"promotionPrice":0,"totalPrice":1580,"itemStatus":"待付款","itemStatusCode":0,"skuSn":"JZ0376120900101502","productId":122015,"skuId":162248,"productImg":"/model/1602/0cd4ce8a4401418c051c50e01be474d5","productName":"FLO NAKED Wendy Zhang张韵文    允儿同款  乔其棉点混纺露肩大泡袖上衣","color":"白色","size":"M码","designerId":10393,"designer":"FLO NAKED","isComment":-1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":0,"combinationPromotionId":0}]
         */

        private OrderEntity order;

        public void setAccount(AccountEntity account) {
            this.account = account;
        }

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public AccountEntity getAccount() {
            return account;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public static class AccountEntity {
            private int id;
            private String mobile;
            private double totalAmount;
            private double availableAmount;
            private double freezeAmount;
            private boolean setPassword;

            public void setId(int id) {
                this.id = id;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public void setAvailableAmount(double availableAmount) {
                this.availableAmount = availableAmount;
            }

            public void setFreezeAmount(double freezeAmount) {
                this.freezeAmount = freezeAmount;
            }

            public void setSetPassword(boolean setPassword) {
                this.setPassword = setPassword;
            }

            public int getId() {
                return id;
            }

            public String getMobile() {
                return mobile;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public double getAvailableAmount() {
                return availableAmount;
            }

            public double getFreezeAmount() {
                return freezeAmount;
            }

            public boolean isSetPassword() {
                return setPassword;
            }
        }

        public static class OrderEntity {
            private int id;
            private String reciver;
            private String contact;
            private String province;
            private String city;
            private String district;
            private String street;
            private boolean cancel;
            private double totalAmount;
            private double totalPay;
            private String orderSn;
            private String paymentType;
            private String paymentTypeName;
            private String orderStatus;
            private String orderStatusName;
            private int orderStatusCode;
            private String createDate;
            private String endTime;
            private int itemTotal;
            private double couponAmount;
            private String tempId;
            private double shippingRates;
            private double promotionAmount;
            private boolean isOver;
            private String type;
            private List<Long> cartItemIds;
            /**
             * deliveryCorpCode : shunfeng
             * id : 154376
             * productPrice : 1580.0
             * productCombId : 0
             * quantity : 1
             * promotionPrice : 0.0
             * totalPrice : 1580.0
             * itemStatus : 待付款
             * itemStatusCode : 0
             * skuSn : JZ0376120900101502
             * productId : 122015
             * skuId : 162248
             * productImg : /model/1602/0cd4ce8a4401418c051c50e01be474d5
             * productName : FLO NAKED Wendy Zhang张韵文    允儿同款  乔其棉点混纺露肩大泡袖上衣
             * color : 白色
             * size : M码
             * designerId : 10393
             * designer : FLO NAKED
             * isComment : -1
             * isCod : 1
             * aftering : none
             * crowdItemId : 0
             * orderPromotionId : 0
             * goodPromotionId : 0
             * combinationPromotionId : 0
             */

            private List<ItemsEntity> items;

            public void setId(int id) {
                this.id = id;
            }

            public void setReciver(String reciver) {
                this.reciver = reciver;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public void setCancel(boolean cancel) {
                this.cancel = cancel;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public void setTotalPay(double totalPay) {
                this.totalPay = totalPay;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public void setPaymentTypeName(String paymentTypeName) {
                this.paymentTypeName = paymentTypeName;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public void setOrderStatusName(String orderStatusName) {
                this.orderStatusName = orderStatusName;
            }

            public void setOrderStatusCode(int orderStatusCode) {
                this.orderStatusCode = orderStatusCode;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public void setItemTotal(int itemTotal) {
                this.itemTotal = itemTotal;
            }

            public void setCouponAmount(double couponAmount) {
                this.couponAmount = couponAmount;
            }

            public void setTempId(String tempId) {
                this.tempId = tempId;
            }

            public void setShippingRates(double shippingRates) {
                this.shippingRates = shippingRates;
            }

            public void setPromotionAmount(double promotionAmount) {
                this.promotionAmount = promotionAmount;
            }

            public void setIsOver(boolean isOver) {
                this.isOver = isOver;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setCartItemIds(List<Long> cartItemIds) {
                this.cartItemIds = cartItemIds;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
            }

            public int getId() {
                return id;
            }

            public String getReciver() {
                return reciver;
            }

            public String getContact() {
                return contact;
            }

            public String getProvince() {
                return province;
            }

            public String getCity() {
                return city;
            }

            public String getDistrict() {
                return district;
            }

            public String getStreet() {
                return street;
            }

            public boolean isCancel() {
                return cancel;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public double getTotalPay() {
                return totalPay;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public String getPaymentTypeName() {
                return paymentTypeName;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public String getOrderStatusName() {
                return orderStatusName;
            }

            public int getOrderStatusCode() {
                return orderStatusCode;
            }

            public String getCreateDate() {
                return createDate;
            }

            public String getEndTime() {
                return endTime;
            }

            public int getItemTotal() {
                return itemTotal;
            }

            public double getCouponAmount() {
                return couponAmount;
            }

            public String getTempId() {
                return tempId;
            }

            public double getShippingRates() {
                return shippingRates;
            }

            public double getPromotionAmount() {
                return promotionAmount;
            }

            public boolean isIsOver() {
                return isOver;
            }

            public String getType() {
                return type;
            }

            public List<?> getCartItemIds() {
                return cartItemIds;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                private String deliveryCorpCode;
                private int id;
                private double productPrice;
                private int productCombId;
                private int quantity;
                private double promotionPrice;
                private double totalPrice;
                private String itemStatus;
                private int itemStatusCode;
                private String skuSn;
                private int productId;
                private int skuId;
                private String productImg;
                private String productName;
                private String color;
                private String size;
                private int designerId;
                private String designer;
                private int isComment;
                private int isCod;
                private String aftering;
                private int crowdItemId;
                private int orderPromotionId;
                private int goodPromotionId;
                private int combinationPromotionId;

                public void setDeliveryCorpCode(String deliveryCorpCode) {
                    this.deliveryCorpCode = deliveryCorpCode;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setProductPrice(double productPrice) {
                    this.productPrice = productPrice;
                }

                public void setProductCombId(int productCombId) {
                    this.productCombId = productCombId;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public void setPromotionPrice(double promotionPrice) {
                    this.promotionPrice = promotionPrice;
                }

                public void setTotalPrice(double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public void setItemStatus(String itemStatus) {
                    this.itemStatus = itemStatus;
                }

                public void setItemStatusCode(int itemStatusCode) {
                    this.itemStatusCode = itemStatusCode;
                }

                public void setSkuSn(String skuSn) {
                    this.skuSn = skuSn;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public void setProductImg(String productImg) {
                    this.productImg = productImg;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public void setDesigner(String designer) {
                    this.designer = designer;
                }

                public void setIsComment(int isComment) {
                    this.isComment = isComment;
                }

                public void setIsCod(int isCod) {
                    this.isCod = isCod;
                }

                public void setAftering(String aftering) {
                    this.aftering = aftering;
                }

                public void setCrowdItemId(int crowdItemId) {
                    this.crowdItemId = crowdItemId;
                }

                public void setOrderPromotionId(int orderPromotionId) {
                    this.orderPromotionId = orderPromotionId;
                }

                public void setGoodPromotionId(int goodPromotionId) {
                    this.goodPromotionId = goodPromotionId;
                }

                public void setCombinationPromotionId(int combinationPromotionId) {
                    this.combinationPromotionId = combinationPromotionId;
                }

                public String getDeliveryCorpCode() {
                    return deliveryCorpCode;
                }

                public int getId() {
                    return id;
                }

                public double getProductPrice() {
                    return productPrice;
                }

                public int getProductCombId() {
                    return productCombId;
                }

                public int getQuantity() {
                    return quantity;
                }

                public double getPromotionPrice() {
                    return promotionPrice;
                }

                public double getTotalPrice() {
                    return totalPrice;
                }

                public String getItemStatus() {
                    return itemStatus;
                }

                public int getItemStatusCode() {
                    return itemStatusCode;
                }

                public String getSkuSn() {
                    return skuSn;
                }

                public int getProductId() {
                    return productId;
                }

                public int getSkuId() {
                    return skuId;
                }

                public String getProductImg() {
                    return productImg;
                }

                public String getProductName() {
                    return productName;
                }

                public String getColor() {
                    return color;
                }

                public String getSize() {
                    return size;
                }

                public int getDesignerId() {
                    return designerId;
                }

                public String getDesigner() {
                    return designer;
                }

                public int getIsComment() {
                    return isComment;
                }

                public int getIsCod() {
                    return isCod;
                }

                public String getAftering() {
                    return aftering;
                }

                public int getCrowdItemId() {
                    return crowdItemId;
                }

                public int getOrderPromotionId() {
                    return orderPromotionId;
                }

                public int getGoodPromotionId() {
                    return goodPromotionId;
                }

                public int getCombinationPromotionId() {
                    return combinationPromotionId;
                }
            }
        }
    }
}
