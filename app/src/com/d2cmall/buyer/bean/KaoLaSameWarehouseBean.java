package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 * Description : KaoLaSameWarehouseBean
 */

public class KaoLaSameWarehouseBean extends BaseBean {

    /**
     * data : {"items":[{"productSource":"KAOLA","goodPromotionId":null,"orderId":391241,"superRatio":0.02,"loginCode":"179*****222","type":"ordinary","signDate":"","productName":"考拉 跨境 不包税 双规格","isCod":0,"couponAmount":0,"productTradeType":"CROSS","productSaleType":"CONSIGN","redAmount":0,"productSellType":"SPOT","id":581575,"superId":null,"skuId":385955,"memberId":2865965,"estimateDate":"2018/06/15 23:59:59","designerId":11442,"productId":195334,"productImg":"http://haitao.nos.netease.com/irgblckx76_800_800.jpg","itemStatusCode":2,"orderPromotionId":null,"size":"L","afterApply":{"reship":1,"exchange":0,"refund":1},"warehouseId":2,"flashPromotionId":null,"commentId":null,"taxAmount":126.9,"isAfter":0,"promotionAmount":0,"productPrice":270,"masterRatio":0.02,"isComment":-1,"aftering":"none","parentRatio":0.05,"color":"红色","partnerRatio":0.1,"orderSn":"Q15287715311735965","promotionPrice":0,"totalPrice":270,"warehouseName":"下沙保税仓库","paymentType":"WALLET","productCombId":null,"deliverySn":"123456","deliveryCorpCode":"shunfeng","itemStatus":"已发货","orderPromotionAmount":0,"after":0,"skuSn":"58735564-b3496e25c625803b858635a0a354b71f","createDate":"2018/06/12 10:44:32","cartItemId":null,"quantity":1,"productSn":"58735564","partnerAmount":0,"actualAmount":396.9,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":0,"designer":"考拉品牌001","parentId":null,"masterId":null,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":null,"expectDate":""},{"productSource":"KAOLA","goodPromotionId":8031,"orderId":391241,"superRatio":0.02,"loginCode":"179*****222","type":"ordinary","signDate":"","productName":"考拉 跨境 保税","isCod":0,"couponAmount":0,"productTradeType":"CROSS","productSaleType":"CONSIGN","redAmount":0,"productSellType":"SPOT","id":581576,"superId":null,"skuId":385945,"memberId":2865965,"estimateDate":"2018/06/15 23:59:59","designerId":11442,"productId":195323,"productImg":"http://haitao.nos.netease.com/irgblckx76_800_800.jpg","itemStatusCode":2,"orderPromotionId":null,"size":"自定义","afterApply":{"reship":1,"exchange":0,"refund":1},"warehouseId":2,"flashPromotionId":null,"commentId":null,"taxAmount":549.9,"isAfter":0,"promotionAmount":130,"productPrice":1300,"masterRatio":0.02,"isComment":-1,"aftering":"none","parentRatio":0.01,"color":"自定义","partnerRatio":0.01,"orderSn":"Q15287715311735965","promotionPrice":130,"totalPrice":1300,"warehouseName":"下沙保税仓库","paymentType":"WALLET","productCombId":null,"deliverySn":"123456","deliveryCorpCode":"shunfeng","itemStatus":"已发货","orderPromotionAmount":0,"after":0,"skuSn":"26765638-86614c59eb2d26502e5219d91e1184a5","createDate":"2018/06/12 10:44:32","cartItemId":null,"quantity":1,"productSn":"26765638","partnerAmount":0,"actualAmount":1719.9,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":0,"designer":"考拉品牌001","parentId":null,"masterId":null,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":null,"expectDate":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * productSource : KAOLA
             * goodPromotionId : null
             * orderId : 391241
             * superRatio : 0.02
             * loginCode : 179*****222
             * type : ordinary
             * signDate :
             * productName : 考拉 跨境 不包税 双规格
             * isCod : 0
             * couponAmount : 0
             * productTradeType : CROSS
             * productSaleType : CONSIGN
             * redAmount : 0
             * productSellType : SPOT
             * id : 581575
             * superId : null
             * skuId : 385955
             * memberId : 2865965
             * estimateDate : 2018/06/15 23:59:59
             * designerId : 11442
             * productId : 195334
             * productImg : http://haitao.nos.netease.com/irgblckx76_800_800.jpg
             * itemStatusCode : 2
             * orderPromotionId : null
             * size : L
             * afterApply : {"reship":1,"exchange":0,"refund":1}
             * warehouseId : 2
             * flashPromotionId : null
             * commentId : null
             * taxAmount : 126.9
             * isAfter : 0
             * promotionAmount : 0
             * productPrice : 270
             * masterRatio : 0.02
             * isComment : -1
             * aftering : none
             * parentRatio : 0.05
             * color : 红色
             * partnerRatio : 0.1
             * orderSn : Q15287715311735965
             * promotionPrice : 0
             * totalPrice : 270
             * warehouseName : 下沙保税仓库
             * paymentType : WALLET
             * productCombId : null
             * deliverySn : 123456
             * deliveryCorpCode : shunfeng
             * itemStatus : 已发货
             * orderPromotionAmount : 0
             * after : 0
             * skuSn : 58735564-b3496e25c625803b858635a0a354b71f
             * createDate : 2018/06/12 10:44:32
             * cartItemId : null
             * quantity : 1
             * productSn : 58735564
             * partnerAmount : 0
             * actualAmount : 396.9
             * paymentTypeName : 钱包支付
             * deliveryType : 0
             * diffAmount : 0
             * designer : 考拉品牌001
             * parentId : null
             * masterId : null
             * reminded : 该笔订单将原路返回到您的D2C钱包
             * deliveryCorpName : 顺丰速递
             * partnerId : null
             * expectDate :
             */

            private String productSource;
            private long goodPromotionId;
            private int orderId;
            private double superRatio;
            private String loginCode;
            private String type;
            private Date signDate;
            private String productName;
            private int isCod;
            private double couponAmount;
            private String productTradeType;
            private String productSaleType;
            private double redAmount;
            private String productSellType;
            private int id;
            private long superId;
            private int skuId;
            private int memberId;
            private Date estimateDate;
            private int designerId;
            private int productId;
            private String productImg;
            private int itemStatusCode;
            private long orderPromotionId;
            private String size;
            private AfterApplyBean afterApply;
            private int warehouseId;
            private long flashPromotionId;
            private long commentId;
            private double taxAmount;
            private int isAfter;
            private int promotionAmount;
            private int productPrice;
            private double masterRatio;
            private int isComment;
            private String aftering;
            private double parentRatio;
            private String color;
            private double partnerRatio;
            private String orderSn;
            private double promotionPrice;
            private double totalPrice;
            private String warehouseName;
            private String paymentType;
            private long productCombId;
            private String deliverySn;
            private String deliveryCorpCode;
            private String itemStatus;
            private double orderPromotionAmount;
            private int after;
            private String skuSn;
            private Date createDate;
            private long cartItemId;
            private int quantity;
            private String productSn;
            private int partnerAmount;
            private double actualAmount;
            private String paymentTypeName;
            private int deliveryType;
            private double diffAmount;
            private String designer;
            private long parentId;
            private long masterId;
            private String reminded;
            private String deliveryCorpName;
            private long partnerId;
            private Date expectDate;

            public String getProductSource() {
                return productSource;
            }

            public void setProductSource(String productSource) {
                this.productSource = productSource;
            }

            public long getGoodPromotionId() {
                return goodPromotionId;
            }

            public void setGoodPromotionId(long goodPromotionId) {
                this.goodPromotionId = goodPromotionId;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public double getSuperRatio() {
                return superRatio;
            }

            public void setSuperRatio(double superRatio) {
                this.superRatio = superRatio;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Date getSignDate() {
                return signDate;
            }

            public void setSignDate(Date signDate) {
                this.signDate = signDate;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getIsCod() {
                return isCod;
            }

            public void setIsCod(int isCod) {
                this.isCod = isCod;
            }

            public double getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(double couponAmount) {
                this.couponAmount = couponAmount;
            }

            public String getProductTradeType() {
                return productTradeType;
            }

            public void setProductTradeType(String productTradeType) {
                this.productTradeType = productTradeType;
            }

            public String getProductSaleType() {
                return productSaleType;
            }

            public void setProductSaleType(String productSaleType) {
                this.productSaleType = productSaleType;
            }

            public double getRedAmount() {
                return redAmount;
            }

            public void setRedAmount(double redAmount) {
                this.redAmount = redAmount;
            }

            public String getProductSellType() {
                return productSellType;
            }

            public void setProductSellType(String productSellType) {
                this.productSellType = productSellType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getSuperId() {
                return superId;
            }

            public void setSuperId(long superId) {
                this.superId = superId;
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

            public Date getEstimateDate() {
                return estimateDate;
            }

            public void setEstimateDate(Date estimateDate) {
                this.estimateDate = estimateDate;
            }

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public int getItemStatusCode() {
                return itemStatusCode;
            }

            public void setItemStatusCode(int itemStatusCode) {
                this.itemStatusCode = itemStatusCode;
            }

            public long getOrderPromotionId() {
                return orderPromotionId;
            }

            public void setOrderPromotionId(long orderPromotionId) {
                this.orderPromotionId = orderPromotionId;
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

            public int getWarehouseId() {
                return warehouseId;
            }

            public void setWarehouseId(int warehouseId) {
                this.warehouseId = warehouseId;
            }

            public long getFlashPromotionId() {
                return flashPromotionId;
            }

            public void setFlashPromotionId(long flashPromotionId) {
                this.flashPromotionId = flashPromotionId;
            }

            public long getCommentId() {
                return commentId;
            }

            public void setCommentId(long commentId) {
                this.commentId = commentId;
            }

            public double getTaxAmount() {
                return taxAmount;
            }

            public void setTaxAmount(double taxAmount) {
                this.taxAmount = taxAmount;
            }

            public int getIsAfter() {
                return isAfter;
            }

            public void setIsAfter(int isAfter) {
                this.isAfter = isAfter;
            }

            public int getPromotionAmount() {
                return promotionAmount;
            }

            public void setPromotionAmount(int promotionAmount) {
                this.promotionAmount = promotionAmount;
            }

            public int getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(int productPrice) {
                this.productPrice = productPrice;
            }

            public double getMasterRatio() {
                return masterRatio;
            }

            public void setMasterRatio(double masterRatio) {
                this.masterRatio = masterRatio;
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

            public double getParentRatio() {
                return parentRatio;
            }

            public void setParentRatio(double parentRatio) {
                this.parentRatio = parentRatio;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public double getPartnerRatio() {
                return partnerRatio;
            }

            public void setPartnerRatio(double partnerRatio) {
                this.partnerRatio = partnerRatio;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
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

            public String getWarehouseName() {
                return warehouseName;
            }

            public void setWarehouseName(String warehouseName) {
                this.warehouseName = warehouseName;
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

            public String getDeliverySn() {
                return deliverySn;
            }

            public void setDeliverySn(String deliverySn) {
                this.deliverySn = deliverySn;
            }

            public String getDeliveryCorpCode() {
                return deliveryCorpCode;
            }

            public void setDeliveryCorpCode(String deliveryCorpCode) {
                this.deliveryCorpCode = deliveryCorpCode;
            }

            public String getItemStatus() {
                return itemStatus;
            }

            public void setItemStatus(String itemStatus) {
                this.itemStatus = itemStatus;
            }

            public double getOrderPromotionAmount() {
                return orderPromotionAmount;
            }

            public void setOrderPromotionAmount(double orderPromotionAmount) {
                this.orderPromotionAmount = orderPromotionAmount;
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

            public Date getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Date createDate) {
                this.createDate = createDate;
            }

            public long getCartItemId() {
                return cartItemId;
            }

            public void setCartItemId(long cartItemId) {
                this.cartItemId = cartItemId;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getProductSn() {
                return productSn;
            }

            public void setProductSn(String productSn) {
                this.productSn = productSn;
            }

            public int getPartnerAmount() {
                return partnerAmount;
            }

            public void setPartnerAmount(int partnerAmount) {
                this.partnerAmount = partnerAmount;
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

            public int getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(int deliveryType) {
                this.deliveryType = deliveryType;
            }

            public double getDiffAmount() {
                return diffAmount;
            }

            public void setDiffAmount(double diffAmount) {
                this.diffAmount = diffAmount;
            }

            public String getDesigner() {
                return designer;
            }

            public void setDesigner(String designer) {
                this.designer = designer;
            }

            public long getParentId() {
                return parentId;
            }

            public void setParentId(long parentId) {
                this.parentId = parentId;
            }

            public long getMasterId() {
                return masterId;
            }

            public void setMasterId(long masterId) {
                this.masterId = masterId;
            }

            public String getReminded() {
                return reminded;
            }

            public void setReminded(String reminded) {
                this.reminded = reminded;
            }

            public String getDeliveryCorpName() {
                return deliveryCorpName;
            }

            public void setDeliveryCorpName(String deliveryCorpName) {
                this.deliveryCorpName = deliveryCorpName;
            }

            public long getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(long partnerId) {
                this.partnerId = partnerId;
            }

            public Date getExpectDate() {
                return expectDate;
            }

            public void setExpectDate(Date expectDate) {
                this.expectDate = expectDate;
            }

            public static class AfterApplyBean {
                /**
                 * reship : 1
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
