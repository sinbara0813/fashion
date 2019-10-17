package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2017/9/27.
 */

public class OrderItemBean extends BaseBean {

    /**
     * data : {"item":{"goodPromotionId":4032,"isComment":-1,"aftering":"none","color":"条文","orderSn":"Q15065808987405965","orderId":325073,"promotionPrice":0,"totalPrice":0.01,"crowdItemId":0,"type":"ordinary","productName":"测试商品 请勿购买","paymentType":"WALLET","productCombId":null,"deliveryCorpCode":"other","deliverySn":null,"isCod":0,"itemStatus":"待发货","id":467598,"after":0,"skuSn":"Z0054108800112007","skuId":199771,"cartItemId":null,"estimateDate":"2017/09/29 23:59:59","order":{"cancel":false,"city":"杭州市","orderSn":"Q15065808987405965","memo":null,"orderStatus":"WaitingForDelivery","type":"ordinary","paymentType":"WALLET","orderStatusName":"待发货","deliverySn":null,"couponAmount":0,"totalPay":0.01,"province":"浙江省","street":"市辖区摸摸摸","paymentSn":"P170915065808787599478","contact":"13658245109","id":325073,"createDate":"2017/09/28 14:41:18","shippingRates":0,"paymentTypeName":"钱包支付","reciver":"明珠行","totalAmount":0.01,"deliveryCorpName":null,"district":"市辖区","orderStatusCode":3,"isOver":false,"tempId":"dd4ac2e818ae42eaad743a3663f7a596","drawee":null,"promotionAmount":0},"designerId":10942,"quantity":1,"productId":101504,"actualAmount":0.01,"paymentTypeName":"钱包支付","productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","deliveryType":0,"designer":"设计师-测试专用","itemStatusCode":1,"orderPromotionId":4043,"reminded":"该笔订单将原路返回到您的D2C钱包","size":"XXXXL码","afterApply":{"reship":0,"exchange":0,"refund":1},"deliveryCorpName":null,"commentId":null,"productPrice":0.01}}
     */

    private OrderItemBean.DataBean data;

    public OrderItemBean.DataBean getData() {
        return data;
    }

    public void setData(OrderItemBean.DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * item : {"goodPromotionId":4032,"isComment":-1,"aftering":"none","color":"条文","orderSn":"Q15065808987405965","orderId":325073,"promotionPrice":0,"totalPrice":0.01,"crowdItemId":0,"type":"ordinary","productName":"测试商品 请勿购买","paymentType":"WALLET","productCombId":null,"deliveryCorpCode":"other","deliverySn":null,"isCod":0,"itemStatus":"待发货","id":467598,"after":0,"skuSn":"Z0054108800112007","skuId":199771,"cartItemId":null,"estimateDate":"2017/09/29 23:59:59","order":{"cancel":false,"city":"杭州市","orderSn":"Q15065808987405965","memo":null,"orderStatus":"WaitingForDelivery","type":"ordinary","paymentType":"WALLET","orderStatusName":"待发货","deliverySn":null,"couponAmount":0,"totalPay":0.01,"province":"浙江省","street":"市辖区摸摸摸","paymentSn":"P170915065808787599478","contact":"13658245109","id":325073,"createDate":"2017/09/28 14:41:18","shippingRates":0,"paymentTypeName":"钱包支付","reciver":"明珠行","totalAmount":0.01,"deliveryCorpName":null,"district":"市辖区","orderStatusCode":3,"isOver":false,"tempId":"dd4ac2e818ae42eaad743a3663f7a596","drawee":null,"promotionAmount":0},"designerId":10942,"quantity":1,"productId":101504,"actualAmount":0.01,"paymentTypeName":"钱包支付","productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","deliveryType":0,"designer":"设计师-测试专用","itemStatusCode":1,"orderPromotionId":4043,"reminded":"该笔订单将原路返回到您的D2C钱包","size":"XXXXL码","afterApply":{"reship":0,"exchange":0,"refund":1},"deliveryCorpName":null,"commentId":null,"productPrice":0.01}
         */

        private OrderItemBean.DataBean.ItemBean item;

        public OrderItemBean.DataBean.ItemBean getItem() {
            return item;
        }

        public void setItem(OrderItemBean.DataBean.ItemBean item) {
            this.item = item;
        }
        private int point;
        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }
        public static class ItemBean {
            /**
             * goodPromotionId : 4032
             * isComment : -1
             * aftering : none
             * color : 条文
             * orderSn : Q15065808987405965
             * orderId : 325073
             * promotionPrice : 0.0
             * totalPrice : 0.01
             * crowdItemId : 0
             * type : ordinary
             * productName : 测试商品 请勿购买
             * paymentType : WALLET
             * productCombId : null
             * deliveryCorpCode : other
             * deliverySn : null
             * isCod : 0
             * itemStatus : 待发货
             * id : 467598
             * after : 0
             * skuSn : Z0054108800112007
             * skuId : 199771
             * cartItemId : null
             * estimateDate : 2017/09/29 23:59:59
             * order : {"cancel":false,"city":"杭州市","orderSn":"Q15065808987405965","memo":null,"orderStatus":"WaitingForDelivery","type":"ordinary","paymentType":"WALLET","orderStatusName":"待发货","deliverySn":null,"couponAmount":0,"totalPay":0.01,"province":"浙江省","street":"市辖区摸摸摸","paymentSn":"P170915065808787599478","contact":"13658245109","id":325073,"createDate":"2017/09/28 14:41:18","shippingRates":0,"paymentTypeName":"钱包支付","reciver":"明珠行","totalAmount":0.01,"deliveryCorpName":null,"district":"市辖区","orderStatusCode":3,"isOver":false,"tempId":"dd4ac2e818ae42eaad743a3663f7a596","drawee":null,"promotionAmount":0}
             * designerId : 10942
             * quantity : 1
             * productId : 101504
             * actualAmount : 0.01
             * paymentTypeName : 钱包支付
             * productImg : /sp/4/101504/cbbc4ed1576ac2817354c96828302fcf
             * deliveryType : 0
             * designer : 设计师-测试专用
             * itemStatusCode : 1
             * orderPromotionId : 4043
             * reminded : 该笔订单将原路返回到您的D2C钱包
             * size : XXXXL码
             * afterApply : {"reship":0,"exchange":0,"refund":1}
             * deliveryCorpName : null
             * commentId : null
             * productPrice : 0.01
             */

            private int goodPromotionId;
            private int isComment;
            private String aftering;
            private String color;
            private String orderSn;
            private long orderId;
            private double promotionPrice;
            private double totalPrice;
            private int crowdItemId;
            private String type;
            private String productName;
            private String paymentType;
            private long productCombId;
            private String deliveryCorpCode;
            private String deliverySn;
            private int isCod;
            private String itemStatus;
            private double orderPromotionAmount;

            private double partnerAmount;

            public String getProductSource() {
                return productSource;
            }

            public void setProductSource(String productSource) {
                this.productSource = productSource;
            }

            /**
             * 商品来源 D2CMALL(D2C) KAOLA(考拉)
             */
            private String productSource;
            public double getPartnerAmount() {
                return partnerAmount;
            }

            public void setPartnerAmount(double partnerAmount) {
                this.partnerAmount = partnerAmount;
            }

            private Long flashPromotionId;

            public String getWarehouseName() {
                return warehouseName;
            }

            public void setWarehouseName(String warehouseName) {
                this.warehouseName = warehouseName;
            }

            /**
             * 保税仓名称
             */
            private String warehouseName;
            //税费
            private double taxAmount;
            //贸易类型COMMON(一般) CROSS(跨境)|
            private String productTradeType;
            //            保税仓ID
            private Long warehouseId;
            //是否包税
            private int isTaxation;

            public int getIsTaxation() {
                return isTaxation;
            }

            public void setIsTaxation(int isTaxation) {
                this.isTaxation = isTaxation;
            }

            public String getProductTradeType() {
                return productTradeType;
            }

            public void setProductTradeType(String productTradeType) {
                this.productTradeType = productTradeType;
            }

            public double getTaxAmount() {
                return taxAmount;
            }

            public void setTaxAmount(double taxAmount) {
                this.taxAmount = taxAmount;
            }

            public Long getWarehouseId() {
                return warehouseId;
            }

            public void setWarehouseId(Long warehouseId) {
                this.warehouseId = warehouseId;
            }


            public Long getFlashPromotionId() {
                return flashPromotionId;
            }

            public void setFlashPromotionId(Long flashPromotionId) {
                this.flashPromotionId = flashPromotionId;
            }

            public double getOrderPromotionAmount() {
                return orderPromotionAmount;
            }

            public void setOrderPromotionAmount(double orderPromotionAmount) {
                this.orderPromotionAmount = orderPromotionAmount;
            }

            public double getRedAmount() {
                return redAmount;
            }

            public void setRedAmount(double redAmount) {
                this.redAmount = redAmount;
            }

            private double redAmount;
            private int id;
            private int after;
            private String skuSn;
            private double couponAmount;

            public double getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(double couponAmount) {
                this.couponAmount = couponAmount;
            }

            private int skuId;
            private long cartItemId;
            private String estimateDate;
            private OrderItemBean.DataBean.ItemBean.OrderBean order;
            private int designerId;
            private int quantity;
            private int productId;
            private double actualAmount;
            private String paymentTypeName;
            private String productImg;
            private int deliveryType;
            private String designer;
            private int itemStatusCode;
            private int orderPromotionId;
            private String reminded;
            private String size;
            private OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity.AfterApplyEntity afterApply;
            private String deliveryCorpName;
            private long commentId;
            private double productPrice;
            //collageStatus 订单明细里多这个字段，0：未拼团 1：拼团中 8：拼团成功 只有=1的时候，不能操作，按钮隐藏，其他都和正常订单一样
            private int collageStatus;

            public int getCollageStatus() {
                return collageStatus;
            }

            public void setCollageStatus(int collageStatus) {
                this.collageStatus = collageStatus;
            }
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

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public long getOrderId() {
                return orderId;
            }

            public void setOrderId(long orderId) {
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

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public long getProductCombId() {
                return productCombId;
            }

            public void setProductCombId(long productCombId) {
                this.productCombId = productCombId;
            }

            public String getDeliveryCorpCode() {
                return deliveryCorpCode;
            }

            public void setDeliveryCorpCode(String deliveryCorpCode) {
                this.deliveryCorpCode = deliveryCorpCode;
            }

            public String getDeliverySn() {
                return deliverySn;
            }

            public void setDeliverySn(String deliverySn) {
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public long getCartItemId() {
                return cartItemId;
            }

            public void setCartItemId(long cartItemId) {
                this.cartItemId = cartItemId;
            }

            public String getEstimateDate() {
                return estimateDate;
            }

            public void setEstimateDate(String estimateDate) {
                this.estimateDate = estimateDate;
            }

            public OrderItemBean.DataBean.ItemBean.OrderBean getOrder() {
                return order;
            }

            public void setOrder(OrderItemBean.DataBean.ItemBean.OrderBean order) {
                this.order = order;
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

            public String getPaymentTypeName() {
                return paymentTypeName;
            }

            public void setPaymentTypeName(String paymentTypeName) {
                this.paymentTypeName = paymentTypeName;
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

            public OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity.AfterApplyEntity getAfterApply() {
                return afterApply;
            }

            public void setAfterApply(OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity.AfterApplyEntity afterApply) {
                this.afterApply = afterApply;
            }

            public String getDeliveryCorpName() {
                return deliveryCorpName;
            }

            public void setDeliveryCorpName(String deliveryCorpName) {
                this.deliveryCorpName = deliveryCorpName;
            }

            public long getCommentId() {
                return commentId;
            }

            public void setCommentId(long commentId) {
                this.commentId = commentId;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public static class OrderBean {
                /**
                 * cancel : false
                 * city : 杭州市
                 * orderSn : Q15065808987405965
                 * memo : null
                 * orderStatus : WaitingForDelivery
                 * type : ordinary
                 * paymentType : WALLET
                 * orderStatusName : 待发货
                 * deliverySn : null
                 * couponAmount : 0.0
                 * totalPay : 0.01
                 * province : 浙江省
                 * street : 市辖区摸摸摸
                 * paymentSn : P170915065808787599478
                 * contact : 13658245109
                 * id : 325073
                 * createDate : 2017/09/28 14:41:18
                 * shippingRates : 0.0
                 * paymentTypeName : 钱包支付
                 * reciver : 明珠行
                 * totalAmount : 0.01
                 * deliveryCorpName : null
                 * district : 市辖区
                 * orderStatusCode : 3
                 * isOver : false
                 * tempId : dd4ac2e818ae42eaad743a3663f7a596
                 * drawee : null
                 * promotionAmount : 0.0
                 */

                private boolean cancel;
                private String city;
                private String orderSn;
                private String memo;
                private String orderStatus;
                private String type;
                private String paymentType;
                private String orderStatusName;
                private String deliverySn;
                private double couponAmount;
                private double totalPay;
                private String province;
                private String street;
                private String paymentSn;
                private String contact;
                private int id;
                private String createDate;
                private double shippingRates;
                private String paymentTypeName;
                private String reciver;
                private double totalAmount;
                private String deliveryCorpName;
                private String district;
                private int orderStatusCode;
                private boolean isOver;
                private String tempId;
                private String drawee;
                private double promotionAmount;

                public boolean isCancel() {
                    return cancel;
                }

                public void setCancel(boolean cancel) {
                    this.cancel = cancel;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(String orderSn) {
                    this.orderSn = orderSn;
                }

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
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

                public String getDeliverySn() {
                    return deliverySn;
                }

                public void setDeliverySn(String deliverySn) {
                    this.deliverySn = deliverySn;
                }

                public double getCouponAmount() {
                    return couponAmount;
                }

                public void setCouponAmount(double couponAmount) {
                    this.couponAmount = couponAmount;
                }

                public double getTotalPay() {
                    return totalPay;
                }

                public void setTotalPay(double totalPay) {
                    this.totalPay = totalPay;
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

                public String getPaymentSn() {
                    return paymentSn;
                }

                public void setPaymentSn(String paymentSn) {
                    this.paymentSn = paymentSn;
                }

                public String getContact() {
                    return contact;
                }

                public void setContact(String contact) {
                    this.contact = contact;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public double getShippingRates() {
                    return shippingRates;
                }

                public void setShippingRates(double shippingRates) {
                    this.shippingRates = shippingRates;
                }

                public String getPaymentTypeName() {
                    return paymentTypeName;
                }

                public void setPaymentTypeName(String paymentTypeName) {
                    this.paymentTypeName = paymentTypeName;
                }

                public String getReciver() {
                    return reciver;
                }

                public void setReciver(String reciver) {
                    this.reciver = reciver;
                }

                public double getTotalAmount() {
                    return totalAmount;
                }

                public void setTotalAmount(double totalAmount) {
                    this.totalAmount = totalAmount;
                }

                public String getDeliveryCorpName() {
                    return deliveryCorpName;
                }

                public void setDeliveryCorpName(String deliveryCorpName) {
                    this.deliveryCorpName = deliveryCorpName;
                }

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
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

                public String getTempId() {
                    return tempId;
                }

                public void setTempId(String tempId) {
                    this.tempId = tempId;
                }

                public String getDrawee() {
                    return drawee;
                }

                public void setDrawee(String drawee) {
                    this.drawee = drawee;
                }

                public double getPromotionAmount() {
                    return promotionAmount;
                }

                public void setPromotionAmount(double promotionAmount) {
                    this.promotionAmount = promotionAmount;
                }
            }

            public static class AfterApplyBean {
                /**
                 * reship : 0
                 * exchange : 0
                 * refund : 1
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
