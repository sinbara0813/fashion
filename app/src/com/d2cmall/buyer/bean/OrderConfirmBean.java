package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderConfirmBean extends BaseBean implements Serializable {

    /**
     * addresses : [{"id":166973,"isdefault":false,"name":"陈艳飞","mobile":"18767165589","street":"通卡恶魔终于上市","province":"北京市","city":"县","district":"密云县"}]
     * coupons : []
     * disableCoupons : []
     * account : {"id":9182,"mobile":"18767165589","totalAmount":0.01,"availableAmount":0.01,"freezeAmount":0,"setPassword":true}
     * order : {"cartItemIds":[],"cancel":false,"totalAmount":0,"totalPay":0,"orderSn":"Q14607023541219336","paymentType":"ALIPAY","paymentTypeName":"支付宝","orderStatus":"Initial","orderStatusName":"未处理","orderStatusCode":0,"createDate":"2016/04/15 14:39:14","itemTotal":0,"couponAmount":0,"tempId":"c29ee96a5d874b298843f81c884cbc58","shippingRates":0,"promotionAmount":0,"isOver":false,"type":"ordinary","items":[{"deliveryCorpCode":"shunfeng","productPrice":169,"quantity":0,"promotionPrice":0,"totalPrice":0,"itemStatus":"待付款","itemStatusCode":0,"skuSn":"JX0106220900302002","productId":125710,"skuId":171330,"productImg":"/model/1604/8a9c625c66d7d6c0e910ac59fab1690e","productName":"DEAMON 夕又米 气质显瘦独特肌理棉麻面料无袖上衣","color":"黑色","size":"M码","designerId":10403,"designer":"DEAMON","isComment":-1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":0,"combinationPromotionId":0}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private MemberCertificationBean memberCertification;
        private AccountEntity account;

        private OrderEntity order;
        /**
         * id : 166973
         * isdefault : false
         * name : 陈艳飞
         * mobile : 18767165589
         * street : 通卡恶魔终于上市
         * province : 北京市
         * city : 县
         * district : 密云县
         */
        private AddressBean address;
        private String reason;
        private List<CouponsEntity> coupons;
        private List<CouponsEntity> disableCoupons;

        public void setAccount(AccountEntity account) {
            this.account = account;
        }

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public void setAddresses(AddressBean address) {
            this.address = address;
        }

        public void setCoupons(List<CouponsEntity> coupons) {
            this.coupons = coupons;
        }

        public void setDisableCoupons(List<CouponsEntity> disableCoupons) {
            this.disableCoupons = disableCoupons;
        }

        public MemberCertificationBean getMemberCertification() {
            return memberCertification;
        }

        public void setMemberCertification(MemberCertificationBean memberCertification) {
            this.memberCertification = memberCertification;
        }

        public AccountEntity getAccount() {
            return account;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public AddressBean getAddresses() {
            return address;
        }

        public List<CouponsEntity> getCoupons() {
            return coupons;
        }

        public List<CouponsEntity> getDisableCoupons() {
            return disableCoupons;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public static class AccountEntity {
            private int id;
            private String mobile;
            private double totalAmount;
            private double availableAmount;
            private double freezeAmount;
            private boolean setPassword;
            private double redAmount;
            private double actualRed;
            private String redDate;
            private boolean isRed;

            public boolean isRed() {
                return isRed;
            }

            public void setRed(boolean red) {
                isRed = red;
            }

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

            public double getRedAmount() {
                return redAmount;
            }

            public void setRedAmount(double redAmount) {
                this.redAmount = redAmount;
            }

            public String getRedDate() {
                return redDate;
            }

            public void setRedDate(String redDate) {
                this.redDate = redDate;
            }

            public double getActualRed() {
                return actualRed;
            }

            public void setActualRed(double actualRed) {
                this.actualRed = actualRed;
            }
        }

        public static class OrderEntity {
            private boolean cancel;
            private double totalAmount;
            private double localAmount;
            private double totalPay;
            private String orderSn;
            private String paymentType;
            private String paymentTypeName;
            private String orderStatus;
            private String orderStatusName;
            private int orderStatusCode;
            private String createDate;
            private int itemTotal;
            private double couponAmount;
            private String tempId;
            private double shippingRates;
            private double promotionAmount;
            private boolean isOver;
            private String type;
            private List<Long> cartItemIds;
            private String payParams;//支付方式
            private double partnerAmount;

            public double getPartnerAmount() {
                return partnerAmount;
            }

            public void setPartnerAmount(double partnerAmount) {
                this.partnerAmount = partnerAmount;
            }

            public String getPayParams() {
                return payParams;
            }

            public void setPayParams(String payParams) {
                this.payParams = payParams;
            }

            public double getLocalAmount() {
                return localAmount;
            }

            public void setLocalAmount(double localAmount) {
                this.localAmount = localAmount;
            }

            /**
             * deliveryCorpCode : shunfeng
             * productPrice : 169.0
             * quantity : 0
             * promotionPrice : 0
             * totalPrice : 0.0
             * itemStatus : 待付款
             * itemStatusCode : 0
             * skuSn : JX0106220900302002
             * productId : 125710
             * skuId : 171330
             * productImg : /model/1604/8a9c625c66d7d6c0e910ac59fab1690e
             * productName : DEAMON 夕又米 气质显瘦独特肌理棉麻面料无袖上衣
             * color : 黑色
             * size : M码
             * designerId : 10403
             * designer : DEAMON
             * isComment : -1
             * isCod : 1
             * aftering : none
             * crowdItemId : 0
             * orderPromotionId : 0
             * goodPromotionId : 0
             * combinationPromotionId : 0
             */

            private List<ItemsEntity> items;

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

            public List<Long> getCartItemIds() {
                return cartItemIds;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                private String deliveryCorpCode;
                private double productPrice;
                private int quantity;
                private double promotionPrice;
                private double totalPrice;
                private String itemStatus;
                private String itemStatusCode;
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
                private long crowdItemId;
                private long orderPromotionId;
                private long goodPromotionId;
                private long combinationPromotionId;
                private long productCombId;
                private int after;
                private Long flashPromotionId;
                private double partnerRatio;
                private double actualAmount;
                private double localAmount;
                private String productSource;//商品来源
                private String productSn;//商品款号
                private double promotionAmount;
                private double orderPromotionAmount;
                private double taxPrice;
                private int wareHouseId;//仓库id,自己加的不是后台返回的
                private String wareHouseName;//仓库名称,自己加的
                private String taxAmount;//税费,自己加的
                private int isTaxation;//是否包税,1为包税,0为不包税

                public double getTaxPrice() {
                    return taxPrice;
                }

                public void setTaxPrice(double taxPrice) {
                    this.taxPrice = taxPrice;
                }

                public int getIsTaxation() {
                    return isTaxation;
                }

                public void setIsTaxation(int isTaxation) {
                    this.isTaxation = isTaxation;
                }

                public int getWareHouseId() {
                    return wareHouseId;
                }

                public void setWareHouseId(int wareHouseId) {
                    this.wareHouseId = wareHouseId;
                }

                public String getWareHouseName() {
                    return wareHouseName;
                }

                public void setWareHouseName(String wareHouseName) {
                    this.wareHouseName = wareHouseName;
                }

                public String getTaxAmount() {
                    return taxAmount;
                }

                public void setTaxAmount(String taxAmount) {
                    this.taxAmount = taxAmount;
                }

                public double getPromotionAmount() {
                    return promotionAmount;
                }

                public void setPromotionAmount(double promotionAmount) {
                    this.promotionAmount = promotionAmount;
                }

                public double getOrderPromotionAmount() {
                    return orderPromotionAmount;
                }

                public void setOrderPromotionAmount(double orderPromotionAmount) {
                    this.orderPromotionAmount = orderPromotionAmount;
                }

                public String getProductSn() {
                    return productSn;
                }

                public void setProductSn(String productSn) {
                    this.productSn = productSn;
                }

                public String getProductSource() {
                    return productSource;
                }

                public void setProductSource(String productSource) {
                    this.productSource = productSource;
                }

                public double getLocalAmount() {
                    return localAmount;
                }

                public void setLocalAmount(double localAmount) {
                    this.localAmount = localAmount;
                }

                public double getActualAmount() {
                    return actualAmount;
                }

                public void setActualAmount(double actualAmount) {
                    this.actualAmount = actualAmount;
                }

                public double getPartnerRatio() {
                    return partnerRatio;
                }

                public void setPartnerRatio(double partnerRatio) {
                    this.partnerRatio = partnerRatio;
                }

                public Long getFlashPromotionId() {
                    return flashPromotionId;
                }

                public void setFlashPromotionId(Long flashPromotionId) {
                    this.flashPromotionId = flashPromotionId;
                }

                public void setDeliveryCorpCode(String deliveryCorpCode) {
                    this.deliveryCorpCode = deliveryCorpCode;
                }

                public void setProductPrice(double productPrice) {
                    this.productPrice = productPrice;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public void setPromotionPrice(int promotionPrice) {
                    this.promotionPrice = promotionPrice;
                }

                public int getAfter() {
                    return after;
                }

                public void setAfter(int after) {
                    this.after = after;
                }

                public void setTotalPrice(double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public void setItemStatus(String itemStatus) {
                    this.itemStatus = itemStatus;
                }

                public void setItemStatusCode(String itemStatusCode) {
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

                public double getProductPrice() {
                    return productPrice;
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

                public String getItemStatusCode() {
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
                    if (Util.isEmpty(color)) {
                        return "";
                    }
                    return color;
                }

                public String getSize() {
                    if (Util.isEmpty(size)) {
                        return "";
                    }
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

                public long getCrowdItemId() {
                    return crowdItemId;
                }

                public long getOrderPromotionId() {
                    return orderPromotionId;
                }

                public long getGoodPromotionId() {
                    return goodPromotionId;
                }

                public long getCombinationPromotionId() {
                    return combinationPromotionId;
                }

                public long getProductCombId() {
                    return productCombId;
                }

                public void setProductCombId(long productCombId) {
                    this.productCombId = productCombId;
                }
            }
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

            private String districtCode;
            private String city;
            private String provinceCode;
            private String cityCode;
            private String mobile;
            private Object weixin;
            private String province;
            private String street;
            private String district;
            private String name;
            private int id;
            private boolean isdefault;
            private String email;
            private double longitude;
            private double latitude;

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getDistrictCode() {
                return districtCode;
            }

            public void setDistrictCode(String districtCode) {
                this.districtCode = districtCode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvinceCode() {
                return provinceCode;
            }

            public void setProvinceCode(String provinceCode) {
                this.provinceCode = provinceCode;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        public static class CouponsEntity implements Serializable, Identifiable {
            private long id;
            private String name;
            private String type;
            private String code;
            private long amount;
            private long needAmount;
            private String remark;
            private String enabledate;
            private String expiredate;
            private String status;
            private String url;
            private int isExpired;
            private String couponName;
            private String activeDay;

            public void setId(long id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }

            public void setNeedAmount(long needAmount) {
                this.needAmount = needAmount;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setEnabledate(String enabledate) {
                this.enabledate = enabledate;
            }

            public void setExpiredate(String expiredate) {
                this.expiredate = expiredate;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setIsExpired(int isExpired) {
                this.isExpired = isExpired;
            }

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getType() {
                return type;
            }

            public String getCode() {
                return code;
            }

            public long getAmount() {
                return amount;
            }

            public long getNeedAmount() {
                return needAmount;
            }

            public String getRemark() {
                return remark;
            }

            public String getEnabledate() {
                return enabledate;
            }

            public String getExpiredate() {
                return expiredate;
            }

            public String getStatus() {
                return status;
            }

            public String getUrl() {
                return url;
            }

            public int getIsExpired() {
                return isExpired;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public String getActiveDay() {
                return activeDay;
            }

            public void setActiveDay(String activeDay) {
                this.activeDay = activeDay;
            }
        }

        public static class MemberCertificationBean {
            /**
             * id : 206
             * createDate : 1535450446000
             * creator : null
             * modifyDate : 1535450446000
             * lastModifyMan : null
             * realName : 韩仁兵
             * identityCard : 362330199008131793
             * frontPic : null
             * behindPic : null
             * isdefault : 0
             * memberId : 3051879
             * loginCode : 18922222222
             * status : 1
             * authenticate : 1
             */

            private int id;
            private String realName;
            private String identityCard;
            private String cardNo;
            private int isdefault;
            private int memberId;
            private int authenticate;
            private int isUpload;

            public int getIsUpload() {
                return isUpload;
            }

            public void setIsUpload(int isUpload) {
                this.isUpload = isUpload;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getIdentityCard() {
                return identityCard;
            }

            public void setIdentityCard(String identityCard) {
                this.identityCard = identityCard;
            }

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getAuthenticate() {
                return authenticate;
            }

            public void setAuthenticate(int authenticate) {
                this.authenticate = authenticate;
            }
        }
    }
}
