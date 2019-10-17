package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

public class OrdersBean extends BaseBean implements Serializable {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable {
        private int countIDX2;//待发货未读数量
        private int countIDX3;//待收货未读数量

        private OrdersEntity orders;
        private int countIDX1;//待付款未读数量

        public void setCountIDX2(int countIDX2) {
            this.countIDX2 = countIDX2;
        }

        public void setCountIDX3(int countIDX3) {
            this.countIDX3 = countIDX3;
        }

        public void setOrders(OrdersEntity orders) {
            this.orders = orders;
        }

        public void setCountIDX1(int countIDX1) {
            this.countIDX1 = countIDX1;
        }

        public int getCountIDX2() {
            return countIDX2;
        }

        public int getCountIDX3() {
            return countIDX3;
        }

        public OrdersEntity getOrders() {
            return orders;
        }

        public int getCountIDX1() {
            return countIDX1;
        }

        public static class OrdersEntity implements Serializable {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;

            private List<ListEntity> list;

            public void setIndex(int index) {
                this.index = index;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public int getIndex() {
                return index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getTotal() {
                return total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public boolean isNext() {
                return next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Identifiable, Cloneable {
                private long id;//自增ID
                private String deliveryCorpName;//物流公司名称
                private String deliverySn;//物流单号
                private String memo;//用户备注
                private String reciver;//收货人
                private String contact;//联系方式
                private String province;//省
                private String city;//市
                private String district;//区
                private String street;//街道
                private boolean cancel;//true=显示取消订单按钮
                private double totalAmount;//订单总价
                private double totalPay;//实际支付金额
                private String orderSn;//订单ID
                private String paymentType;//支付方式码
                private String paymentTypeName;//支付方式
                private String paymentSn;//支付流水号
                private String orderStatus;//订单状态码
                private String orderStatusName;//订单状态
                private double productTotalPrice;//总计
                private double productPromotionAmount;
                private int orderStatusCode;//（旧的）订单状态码
                private String createDate;//创建订单时间
                private String endTime;//超时时间
                private int itemTotal;//商品件数
                private double couponAmount;//优惠券金额
                private String tempId;
                private int shippingRates;//运费
                private double promotionAmount;//满减金额
                private boolean isExcrete;
                private double partnerAmount;
                //collageStatus 订单明细里多这个字段，0：未拼团 1：拼团中 8：拼团成功 只有=1的时候，不能操作，按钮隐藏，其他都和正常订单一样
                private int collageStatus;

                public int getCollageStatus() {
                    return collageStatus;
                }

                public void setCollageStatus(int collageStatus) {
                    this.collageStatus = collageStatus;
                }
                public String getPayParams() {
                    return payParams;
                }

                public void setPayParams(String payParams) {
                    this.payParams = payParams;
                }
                //支付方式
                private String payParams;

                //贸易类型COMMON(一般) CROSS(跨境)
                private int crossBorder;

                public int getCrossBorder() {
                    return crossBorder;
                }

                public void setCrossBorder(int crossBorder) {
                    this.crossBorder = crossBorder;
                }
                /**
                 * 税费金额
                 */
                private double taxAmount;
                /**
                 * 商品来源 D2CMALL(D2C) KAOLA(考拉)
                 */
                private String productSource;

                public double getTaxAmount() {
                    return taxAmount;
                }

                public void setTaxAmount(double taxAmount) {
                    this.taxAmount = taxAmount;
                }


                public String getProductSource() {
                    return productSource;
                }

                public void setProductSource(String productSource) {
                    this.productSource = productSource;
                }


                public double getPartnerAmount() {
                    return partnerAmount;
                }

                public void setPartnerAmount(double partnerAmount) {
                    this.partnerAmount = partnerAmount;
                }

                public boolean isExcrete() {
                    return isExcrete;
                }

                public void setExcrete(boolean excrete) {
                    isExcrete = excrete;
                }

                public double getProductTotalPrice() {
                    return productTotalPrice;
                }

                public void setProductTotalPrice(double productTotalPrice) {
                    this.productTotalPrice = productTotalPrice;
                }

                public double getProductPromotionAmount() {
                    return productPromotionAmount;
                }

                public void setProductPromotionAmount(double productPromotionAmount) {
                    this.productPromotionAmount = productPromotionAmount;
                }

                private boolean isOver;//订单超时
                private String type;//0分销1普通
                private List<Long> cartItemIds;
                private List<ItemsEntity> items;//商品列表
                private String drawee;

                public double getRedAmount() {
                    return redAmount;
                }

                public void setRedAmount(double redAmount) {
                    this.redAmount = redAmount;
                }

                private double redAmount;

                @Override
                public Object clone() {
                    ListEntity stu = null;
                    try {
                        stu = (ListEntity) super.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    return stu;
                }

                public String getDrawee() {
                    return drawee;
                }

                public void setDrawee(String drawee) {
                    this.drawee = drawee;
                }

                public String getAddress() {
                    if (province != null && (province.equals("北京") || province.equals("天津") || province.equals("上海") || province.equals("重庆"))) {
                        return getCity() + getStreet();
                    }
                    return getProvince() + getCity() + getStreet();
                }

                public String getPaymentSn() {
                    return paymentSn;
                }

                public void setPaymentSn(String paymentSn) {
                    this.paymentSn = paymentSn;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setDeliveryCorpName(String deliveryCorpName) {
                    this.deliveryCorpName = deliveryCorpName;
                }

                public void setDeliverySn(String deliverySn) {
                    this.deliverySn = deliverySn;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
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

                public void setTotalAmount(int totalAmount) {
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

                public void setCouponAmount(int couponAmount) {
                    this.couponAmount = couponAmount;
                }

                public void setTempId(String tempId) {
                    this.tempId = tempId;
                }

                public void setShippingRates(int shippingRates) {
                    this.shippingRates = shippingRates;
                }

                public void setPromotionAmount(int promotionAmount) {
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

                public long getId() {
                    return id;
                }

                public String getDeliveryCorpName() {
                    return deliveryCorpName;
                }

                public String getDeliverySn() {
                    return deliverySn;
                }

                public String getMemo() {
                    return memo;
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

                public int getItemTotal() {
                    return itemTotal;
                }

                public double getCouponAmount() {
                    return couponAmount;
                }

                public String getTempId() {
                    return tempId;
                }

                public int getShippingRates() {
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

                public static class ItemsEntity implements Identifiable {
                    private String deliveryCorpCode;//物流公司ID
                    private String deliveryCorpName;//物流公司名称
                    private String deliverySn;//物流单号
                    private long id;//自增ID
                    private double productPrice;//商品价格
                    private int quantity;//商品数量
                    private double promotionPrice;//总的优惠金额
                    private double totalPrice;//小计
                    private String itemStatus;//商品状态
                    private double orderPromotionAmount;
                    private Long flashPromotionId;
                    /**
                     * 税费金额
                     */
                    private double taxAmount;
                    /**
                     * 贸易类型COMMON(一般) CROSS(跨境)
                     */
                    private String productTradeType;
                    /**
                     * 商品来源 D2CMALL(D2C) KAOLA(考拉)
                     */
                    private String productSource;	/**
                     * 保税仓ID
                     */
                    private long warehouseId;
                    /**
                     * 保税仓名称
                     */
                    private String warehouseName;

                    public int getIsTaxation() {
                        return isTaxation;
                    }

                    public void setIsTaxation(int isTaxation) {
                        this.isTaxation = isTaxation;
                    }

                    private int isTaxation;
                    public double getTaxAmount() {
                        return taxAmount;
                    }

                    public void setTaxAmount(double taxAmount) {
                        this.taxAmount = taxAmount;
                    }

                    public String getProductTradeType() {
                        return productTradeType;
                    }

                    public void setProductTradeType(String productTradeType) {
                        this.productTradeType = productTradeType;
                    }

                    public String getProductSource() {
                        return productSource;
                    }

                    public void setProductSource(String productSource) {
                        this.productSource = productSource;
                    }

                    public long getWarehouseId() {
                        return warehouseId;
                    }

                    public void setWarehouseId(long warehouseId) {
                        this.warehouseId = warehouseId;
                    }

                    public String getWarehouseName() {
                        return warehouseName;
                    }

                    public void setWarehouseName(String warehouseName) {
                        this.warehouseName = warehouseName;
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

                    private int itemStatusCode;
                    private String skuSn;//条码
                    private long productId;//商品ID
                    private long skuId;//SKUID
                    private String productImg;//商品图片url
                    private String productName;//商品名称
                    private String color;//颜色
                    private String size;//尺码
                    private int designerId;//设计师ID
                    private String designer;//设计师名牌名
                    private int isComment;//0是假1是真，是否评论
                    private int isCod;//是否货到付款
                    private long crowdItemId;//预售ID
                    private long orderPromotionId;//订单活动ID
                    private long goodPromotionId;//商品活动ID
                    private long combinationPromotionId;//组合商品活动ID
                    private long commentId;//评价ID
                    private String aftering;//售后字段
                    private double actualAmount;
                    private String paymentType;
                    private AfterApplyEntity afterApply;
                    private String reminded;
                    private int after;
                    private String estimateDate;//预计发货时间
                    private String orderSn;
                    private String type;
                    private String productSn;
                    //collageStatus 订单明细里多这个字段，0：未拼团 1：拼团中 8：拼团成功 只有=1的时候，不能操作，按钮隐藏，其他都和正常订单一样
                    private int collageStatus;

                    public int getCollageStatus() {
                        return collageStatus;
                    }

                    public void setCollageStatus(int collageStatus) {
                        this.collageStatus = collageStatus;
                    }


                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getOrderSn() {
                        return orderSn;
                    }

                    public void setOrderSn(String orderSn) {
                        this.orderSn = orderSn;
                    }

                    public int getAfter() {
                        return after;
                    }

                    public void setAfter(int after) {
                        this.after = after;
                    }

                    public int getItemStatusCode() {
                        return itemStatusCode;
                    }

                    public void setItemStatusCode(int itemStatusCode) {
                        this.itemStatusCode = itemStatusCode;
                    }

                    public void setDeliveryCorpCode(String deliveryCorpCode) {
                        this.deliveryCorpCode = deliveryCorpCode;
                    }

                    public void setDeliveryCorpName(String deliveryCorpName) {
                        this.deliveryCorpName = deliveryCorpName;
                    }

                    public void setDeliverySn(String deliverySn) {
                        this.deliverySn = deliverySn;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }

                    public void setProductPrice(int productPrice) {
                        this.productPrice = productPrice;
                    }

                    public void setQuantity(int quantity) {
                        this.quantity = quantity;
                    }

                    public void setPromotionPrice(int promotionPrice) {
                        this.promotionPrice = promotionPrice;
                    }

                    public void setTotalPrice(int totalPrice) {
                        this.totalPrice = totalPrice;
                    }

                    public void setItemStatus(String itemStatus) {
                        this.itemStatus = itemStatus;
                    }

                    public void setSkuSn(String skuSn) {
                        this.skuSn = skuSn;
                    }

                    public void setProductId(long productId) {
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

                    public void setCrowdItemId(long crowdItemId) {
                        this.crowdItemId = crowdItemId;
                    }

                    public void setOrderPromotionId(long orderPromotionId) {
                        this.orderPromotionId = orderPromotionId;
                    }

                    public void setGoodPromotionId(long goodPromotionId) {
                        this.goodPromotionId = goodPromotionId;
                    }

                    public void setCombinationPromotionId(long combinationPromotionId) {
                        this.combinationPromotionId = combinationPromotionId;
                    }

                    public String getDeliveryCorpCode() {
                        return deliveryCorpCode;
                    }

                    public String getDeliveryCorpName() {
                        return deliveryCorpName;
                    }

                    public String getDeliverySn() {
                        return deliverySn;
                    }

                    public long getId() {
                        return id;
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

                    public String getSkuSn() {
                        return skuSn;
                    }

                    public long getProductId() {
                        return productId;
                    }

                    public long getSkuId() {
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


                    public void setSkuId(long skuId) {
                        this.skuId = skuId;
                    }

                    public void setProductPrice(double productPrice) {
                        this.productPrice = productPrice;
                    }

                    public void setPromotionPrice(double promotionPrice) {
                        this.promotionPrice = promotionPrice;
                    }

                    public void setTotalPrice(double totalPrice) {
                        this.totalPrice = totalPrice;
                    }

                    public long getCommentId() {
                        return commentId;
                    }

                    public void setCommentId(long commentId) {
                        this.commentId = commentId;
                    }

                    public String getAftering() {
                        return aftering;
                    }

                    public void setAftering(String aftering) {
                        this.aftering = aftering;
                    }

                    public double getActualAmount() {
                        return actualAmount;
                    }

                    public void setActualAmount(double actualAmount) {
                        this.actualAmount = actualAmount;
                    }

                    public String getPaymentType() {
                        return paymentType;
                    }

                    public void setPaymentType(String paymentType) {
                        this.paymentType = paymentType;
                    }

                    public String getEstimateDate() {
                        return estimateDate;
                    }

                    public void setEstimateDate(String estimateDate) {
                        this.estimateDate = estimateDate;
                    }

                    public AfterApplyEntity getAfterApply() {
                        return afterApply;
                    }

                    public void setAfterApply(AfterApplyEntity afterApply) {
                        this.afterApply = afterApply;
                    }

                    public String getProductSn() {
                        return productSn;
                    }

                    public void setProductSn(String productSn) {
                        this.productSn = productSn;
                    }

                    public static class AfterApplyEntity implements Serializable {
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

                    public String getReminded() {
                        return reminded;
                    }

                    public void setReminded(String reminded) {
                        this.reminded = reminded;
                    }

                    public boolean isAfterAvail() {
                        return this.after > 0;
                    }
                }
            }
        }
    }
}
