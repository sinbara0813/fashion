package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/7 17:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class OrderBeans extends BaseBean {

    /**
     * data : {"address":{"districtCode":330101,"city":"杭州市","provinceCode":330000,"cityCode":330100,"mobile":"15821453625","weixin":null,"province":"浙江省","street":"挺浓","district":"市辖区","name":"集体照","id":367082,"isdefault":false,"email":null},"coupons":[],"disableCoupons":[],"account":{"totalAmount":0,"setPassword":false,"freezeAmount":0,"availableAmount":0,"mobile":"","id":2270},"order":{"cancel":false,"cartItemIds":[],"city":null,"orderSn":"Q15047777734502397","memo":null,"orderStatus":"Initial","type":"ordinary","paymentType":"ALIPAY","orderStatusName":"未处理","deliverySn":null,"couponAmount":0,"totalPay":0.07,"province":null,"street":null,"paymentSn":null,"contact":null,"id":null,"createDate":"2017/09/07 17:49:33","shippingRates":0,"paymentTypeName":"支付宝","reciver":null,"totalAmount":0.07,"itemTotal":7,"deliveryCorpName":null,"district":null,"orderStatusCode":0,"isOver":false,"endTime":null,"tempId":"e8dc30651caa4967a5056ebfa988ba94","drawee":null,"promotionAmount":0,"items":[{"goodPromotionId":4032,"isComment":-1,"aftering":"none","color":"条文","orderSn":null,"orderId":null,"promotionPrice":0,"totalPrice":0.07,"crowdItemId":0,"type":"ordinary","productName":"测试商品 请勿购买","productCombId":null,"deliveryCorpCode":"other","deliverySn":null,"isCod":0,"itemStatus":"待付款","id":null,"after":1,"skuSn":"Z0054108800112008","skuId":199766,"cartItemId":2459925,"estimateDate":"","designerId":10942,"quantity":7,"productId":101504,"actualAmount":0.07,"productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","deliveryType":0,"designer":"设计师-测试专用","itemStatusCode":0,"orderPromotionId":4043,"reminded":"","size":"XXXXXL码","afterApply":{"reship":0,"exchange":0,"refund":0},"deliveryCorpName":null,"commentId":null,"productPrice":0.01}]}}
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
         * address : {"districtCode":330101,"city":"杭州市","provinceCode":330000,"cityCode":330100,"mobile":"15821453625","weixin":null,"province":"浙江省","street":"挺浓","district":"市辖区","name":"集体照","id":367082,"isdefault":false,"email":null}
         * coupons : []
         * disableCoupons : []
         * account : {"totalAmount":0,"setPassword":false,"freezeAmount":0,"availableAmount":0,"mobile":"","id":2270}
         * order : {"cancel":false,"cartItemIds":[],"city":null,"orderSn":"Q15047777734502397","memo":null,"orderStatus":"Initial","type":"ordinary","paymentType":"ALIPAY","orderStatusName":"未处理","deliverySn":null,"couponAmount":0,"totalPay":0.07,"province":null,"street":null,"paymentSn":null,"contact":null,"id":null,"createDate":"2017/09/07 17:49:33","shippingRates":0,"paymentTypeName":"支付宝","reciver":null,"totalAmount":0.07,"itemTotal":7,"deliveryCorpName":null,"district":null,"orderStatusCode":0,"isOver":false,"endTime":null,"tempId":"e8dc30651caa4967a5056ebfa988ba94","drawee":null,"promotionAmount":0,"items":[{"goodPromotionId":4032,"isComment":-1,"aftering":"none","color":"条文","orderSn":null,"orderId":null,"promotionPrice":0,"totalPrice":0.07,"crowdItemId":0,"type":"ordinary","productName":"测试商品 请勿购买","productCombId":null,"deliveryCorpCode":"other","deliverySn":null,"isCod":0,"itemStatus":"待付款","id":null,"after":1,"skuSn":"Z0054108800112008","skuId":199766,"cartItemId":2459925,"estimateDate":"","designerId":10942,"quantity":7,"productId":101504,"actualAmount":0.07,"productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","deliveryType":0,"designer":"设计师-测试专用","itemStatusCode":0,"orderPromotionId":4043,"reminded":"","size":"XXXXXL码","afterApply":{"reship":0,"exchange":0,"refund":0},"deliveryCorpName":null,"commentId":null,"productPrice":0.01}]}
         */

        private AddressBean address;
        private AccountBean account;
        private OrderBean order;
        private List<?> coupons;
        private List<?> disableCoupons;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public AccountBean getAccount() {
            return account;
        }

        public void setAccount(AccountBean account) {
            this.account = account;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<?> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<?> coupons) {
            this.coupons = coupons;
        }

        public List<?> getDisableCoupons() {
            return disableCoupons;
        }

        public void setDisableCoupons(List<?> disableCoupons) {
            this.disableCoupons = disableCoupons;
        }

        public static class AddressBean {
            /**
             * districtCode : 330101
             * city : 杭州市
             * provinceCode : 330000
             * cityCode : 330100
             * mobile : 15821453625
             * weixin : null
             * province : 浙江省
             * street : 挺浓
             * district : 市辖区
             * name : 集体照
             * id : 367082
             * isdefault : false
             * email : null
             */

            private int districtCode;
            private String city;
            private int provinceCode;
            private int cityCode;
            private String mobile;
            private Object weixin;
            private String province;
            private String street;
            private String district;
            private String name;
            private int id;
            private boolean isdefault;
            private Object email;

            public int getDistrictCode() {
                return districtCode;
            }

            public void setDistrictCode(int districtCode) {
                this.districtCode = districtCode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getProvinceCode() {
                return provinceCode;
            }

            public void setProvinceCode(int provinceCode) {
                this.provinceCode = provinceCode;
            }

            public int getCityCode() {
                return cityCode;
            }

            public void setCityCode(int cityCode) {
                this.cityCode = cityCode;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Object getWeixin() {
                return weixin;
            }

            public void setWeixin(Object weixin) {
                this.weixin = weixin;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsdefault() {
                return isdefault;
            }

            public void setIsdefault(boolean isdefault) {
                this.isdefault = isdefault;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }
        }

        public static class AccountBean {
            /**
             * totalAmount : 0.0
             * setPassword : false
             * freezeAmount : 0.0
             * availableAmount : 0.0
             * mobile :
             * id : 2270
             */

            private double totalAmount;
            private boolean setPassword;
            private double freezeAmount;
            private double availableAmount;
            private String mobile;
            private int id;

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public boolean isSetPassword() {
                return setPassword;
            }

            public void setSetPassword(boolean setPassword) {
                this.setPassword = setPassword;
            }

            public double getFreezeAmount() {
                return freezeAmount;
            }

            public void setFreezeAmount(double freezeAmount) {
                this.freezeAmount = freezeAmount;
            }

            public double getAvailableAmount() {
                return availableAmount;
            }

            public void setAvailableAmount(double availableAmount) {
                this.availableAmount = availableAmount;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class OrderBean {
            /**
             * cancel : false
             * cartItemIds : []
             * city : null
             * orderSn : Q15047777734502397
             * memo : null
             * orderStatus : Initial
             * type : ordinary
             * paymentType : ALIPAY
             * orderStatusName : 未处理
             * deliverySn : null
             * couponAmount : 0
             * totalPay : 0.07
             * province : null
             * street : null
             * paymentSn : null
             * contact : null
             * id : null
             * createDate : 2017/09/07 17:49:33
             * shippingRates : 0
             * paymentTypeName : 支付宝
             * reciver : null
             * totalAmount : 0.07
             * itemTotal : 7
             * deliveryCorpName : null
             * district : null
             * orderStatusCode : 0
             * isOver : false
             * endTime : null
             * tempId : e8dc30651caa4967a5056ebfa988ba94
             * drawee : null
             * promotionAmount : 0
             * items : [{"goodPromotionId":4032,"isComment":-1,"aftering":"none","color":"条文","orderSn":null,"orderId":null,"promotionPrice":0,"totalPrice":0.07,"crowdItemId":0,"type":"ordinary","productName":"测试商品 请勿购买","productCombId":null,"deliveryCorpCode":"other","deliverySn":null,"isCod":0,"itemStatus":"待付款","id":null,"after":1,"skuSn":"Z0054108800112008","skuId":199766,"cartItemId":2459925,"estimateDate":"","designerId":10942,"quantity":7,"productId":101504,"actualAmount":0.07,"productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","deliveryType":0,"designer":"设计师-测试专用","itemStatusCode":0,"orderPromotionId":4043,"reminded":"","size":"XXXXXL码","afterApply":{"reship":0,"exchange":0,"refund":0},"deliveryCorpName":null,"commentId":null,"productPrice":0.01}]
             */

            private boolean cancel;
            private Object city;
            private String orderSn;
            private Object memo;
            private String orderStatus;
            private String type;
            private String paymentType;
            private String orderStatusName;
            private Object deliverySn;
            private int couponAmount;
            private double totalPay;
            private Object province;
            private Object street;
            private Object paymentSn;
            private Object contact;
            private Object id;
            private String createDate;
            private int shippingRates;
            private String paymentTypeName;
            private Object reciver;
            private double totalAmount;
            private int itemTotal;
            private Object deliveryCorpName;
            private Object district;
            private int orderStatusCode;
            private boolean isOver;
            private Object endTime;
            private String tempId;
            private Object drawee;
            private int promotionAmount;
            private List<?> cartItemIds;
            private List<ItemsBean> items;

            public boolean isCancel() {
                return cancel;
            }

            public void setCancel(boolean cancel) {
                this.cancel = cancel;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public Object getMemo() {
                return memo;
            }

            public void setMemo(Object memo) {
                this.memo = memo;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public String getOrderStatusName() {
                return orderStatusName;
            }

            public void setOrderStatusName(String orderStatusName) {
                this.orderStatusName = orderStatusName;
            }

            public Object getDeliverySn() {
                return deliverySn;
            }

            public void setDeliverySn(Object deliverySn) {
                this.deliverySn = deliverySn;
            }

            public int getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(int couponAmount) {
                this.couponAmount = couponAmount;
            }

            public double getTotalPay() {
                return totalPay;
            }

            public void setTotalPay(double totalPay) {
                this.totalPay = totalPay;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getStreet() {
                return street;
            }

            public void setStreet(Object street) {
                this.street = street;
            }

            public Object getPaymentSn() {
                return paymentSn;
            }

            public void setPaymentSn(Object paymentSn) {
                this.paymentSn = paymentSn;
            }

            public Object getContact() {
                return contact;
            }

            public void setContact(Object contact) {
                this.contact = contact;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getShippingRates() {
                return shippingRates;
            }

            public void setShippingRates(int shippingRates) {
                this.shippingRates = shippingRates;
            }

            public String getPaymentTypeName() {
                return paymentTypeName;
            }

            public void setPaymentTypeName(String paymentTypeName) {
                this.paymentTypeName = paymentTypeName;
            }

            public Object getReciver() {
                return reciver;
            }

            public void setReciver(Object reciver) {
                this.reciver = reciver;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public int getItemTotal() {
                return itemTotal;
            }

            public void setItemTotal(int itemTotal) {
                this.itemTotal = itemTotal;
            }

            public Object getDeliveryCorpName() {
                return deliveryCorpName;
            }

            public void setDeliveryCorpName(Object deliveryCorpName) {
                this.deliveryCorpName = deliveryCorpName;
            }

            public Object getDistrict() {
                return district;
            }

            public void setDistrict(Object district) {
                this.district = district;
            }

            public int getOrderStatusCode() {
                return orderStatusCode;
            }

            public void setOrderStatusCode(int orderStatusCode) {
                this.orderStatusCode = orderStatusCode;
            }

            public boolean isIsOver() {
                return isOver;
            }

            public void setIsOver(boolean isOver) {
                this.isOver = isOver;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public String getTempId() {
                return tempId;
            }

            public void setTempId(String tempId) {
                this.tempId = tempId;
            }

            public Object getDrawee() {
                return drawee;
            }

            public void setDrawee(Object drawee) {
                this.drawee = drawee;
            }

            public int getPromotionAmount() {
                return promotionAmount;
            }

            public void setPromotionAmount(int promotionAmount) {
                this.promotionAmount = promotionAmount;
            }

            public List<?> getCartItemIds() {
                return cartItemIds;
            }

            public void setCartItemIds(List<?> cartItemIds) {
                this.cartItemIds = cartItemIds;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * goodPromotionId : 4032
                 * isComment : -1
                 * aftering : none
                 * color : 条文
                 * orderSn : null
                 * orderId : null
                 * promotionPrice : 0.0
                 * totalPrice : 0.07
                 * crowdItemId : 0
                 * type : ordinary
                 * productName : 测试商品 请勿购买
                 * productCombId : null
                 * deliveryCorpCode : other
                 * deliverySn : null
                 * isCod : 0
                 * itemStatus : 待付款
                 * id : null
                 * after : 1
                 * skuSn : Z0054108800112008
                 * skuId : 199766
                 * cartItemId : 2459925
                 * estimateDate :
                 * designerId : 10942
                 * quantity : 7
                 * productId : 101504
                 * actualAmount : 0.07
                 * productImg : /sp/4/101504/cbbc4ed1576ac2817354c96828302fcf
                 * deliveryType : 0
                 * designer : 设计师-测试专用
                 * itemStatusCode : 0
                 * orderPromotionId : 4043
                 * reminded :
                 * size : XXXXXL码
                 * afterApply : {"reship":0,"exchange":0,"refund":0}
                 * deliveryCorpName : null
                 * commentId : null
                 * productPrice : 0.01
                 */

                private int goodPromotionId;
                private int isComment;
                private String aftering;
                private String color;
                private Object orderSn;
                private Object orderId;
                private double promotionPrice;
                private double totalPrice;
                private int crowdItemId;
                private String type;
                private String productName;
                private Object productCombId;
                private String deliveryCorpCode;
                private Object deliverySn;
                private int isCod;
                private String itemStatus;
                private Object id;
                private int after;
                private String skuSn;
                private int skuId;
                private int cartItemId;
                private String estimateDate;
                private int designerId;
                private int quantity;
                private int productId;
                private double actualAmount;
                private String productImg;
                private int deliveryType;
                private String designer;
                private int itemStatusCode;
                private int orderPromotionId;
                private String reminded;
                private String size;
                private AfterApplyBean afterApply;
                private Object deliveryCorpName;
                private Object commentId;
                private double productPrice;

                public int getGoodPromotionId() {
                    return goodPromotionId;
                }

                public void setGoodPromotionId(int goodPromotionId) {
                    this.goodPromotionId = goodPromotionId;
                }

                public int getIsComment() {
                    return isComment;
                }

                public void setIsComment(int isComment) {
                    this.isComment = isComment;
                }

                public String getAftering() {
                    return aftering;
                }

                public void setAftering(String aftering) {
                    this.aftering = aftering;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public Object getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(Object orderSn) {
                    this.orderSn = orderSn;
                }

                public Object getOrderId() {
                    return orderId;
                }

                public void setOrderId(Object orderId) {
                    this.orderId = orderId;
                }

                public double getPromotionPrice() {
                    return promotionPrice;
                }

                public void setPromotionPrice(double promotionPrice) {
                    this.promotionPrice = promotionPrice;
                }

                public double getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public int getCrowdItemId() {
                    return crowdItemId;
                }

                public void setCrowdItemId(int crowdItemId) {
                    this.crowdItemId = crowdItemId;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public Object getProductCombId() {
                    return productCombId;
                }

                public void setProductCombId(Object productCombId) {
                    this.productCombId = productCombId;
                }

                public String getDeliveryCorpCode() {
                    return deliveryCorpCode;
                }

                public void setDeliveryCorpCode(String deliveryCorpCode) {
                    this.deliveryCorpCode = deliveryCorpCode;
                }

                public Object getDeliverySn() {
                    return deliverySn;
                }

                public void setDeliverySn(Object deliverySn) {
                    this.deliverySn = deliverySn;
                }

                public int getIsCod() {
                    return isCod;
                }

                public void setIsCod(int isCod) {
                    this.isCod = isCod;
                }

                public String getItemStatus() {
                    return itemStatus;
                }

                public void setItemStatus(String itemStatus) {
                    this.itemStatus = itemStatus;
                }

                public Object getId() {
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }

                public int getAfter() {
                    return after;
                }

                public void setAfter(int after) {
                    this.after = after;
                }

                public String getSkuSn() {
                    return skuSn;
                }

                public void setSkuSn(String skuSn) {
                    this.skuSn = skuSn;
                }

                public int getSkuId() {
                    return skuId;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public int getCartItemId() {
                    return cartItemId;
                }

                public void setCartItemId(int cartItemId) {
                    this.cartItemId = cartItemId;
                }

                public String getEstimateDate() {
                    return estimateDate;
                }

                public void setEstimateDate(String estimateDate) {
                    this.estimateDate = estimateDate;
                }

                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public double getActualAmount() {
                    return actualAmount;
                }

                public void setActualAmount(double actualAmount) {
                    this.actualAmount = actualAmount;
                }

                public String getProductImg() {
                    return productImg;
                }

                public void setProductImg(String productImg) {
                    this.productImg = productImg;
                }

                public int getDeliveryType() {
                    return deliveryType;
                }

                public void setDeliveryType(int deliveryType) {
                    this.deliveryType = deliveryType;
                }

                public String getDesigner() {
                    return designer;
                }

                public void setDesigner(String designer) {
                    this.designer = designer;
                }

                public int getItemStatusCode() {
                    return itemStatusCode;
                }

                public void setItemStatusCode(int itemStatusCode) {
                    this.itemStatusCode = itemStatusCode;
                }

                public int getOrderPromotionId() {
                    return orderPromotionId;
                }

                public void setOrderPromotionId(int orderPromotionId) {
                    this.orderPromotionId = orderPromotionId;
                }

                public String getReminded() {
                    return reminded;
                }

                public void setReminded(String reminded) {
                    this.reminded = reminded;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public AfterApplyBean getAfterApply() {
                    return afterApply;
                }

                public void setAfterApply(AfterApplyBean afterApply) {
                    this.afterApply = afterApply;
                }

                public Object getDeliveryCorpName() {
                    return deliveryCorpName;
                }

                public void setDeliveryCorpName(Object deliveryCorpName) {
                    this.deliveryCorpName = deliveryCorpName;
                }

                public Object getCommentId() {
                    return commentId;
                }

                public void setCommentId(Object commentId) {
                    this.commentId = commentId;
                }

                public double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(double productPrice) {
                    this.productPrice = productPrice;
                }

                public static class AfterApplyBean {
                    /**
                     * reship : 0
                     * exchange : 0
                     * refund : 0
                     */

                    private int reship;
                    private int exchange;
                    private int refund;

                    public int getReship() {
                        return reship;
                    }

                    public void setReship(int reship) {
                        this.reship = reship;
                    }

                    public int getExchange() {
                        return exchange;
                    }

                    public void setExchange(int exchange) {
                        this.exchange = exchange;
                    }

                    public int getRefund() {
                        return refund;
                    }

                    public void setRefund(int refund) {
                        this.refund = refund;
                    }
                }
            }
        }
    }
}
