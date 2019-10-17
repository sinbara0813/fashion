package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 * Description : PartnerSaleListBean
 */

public class PartnerSaleListBean extends BaseBean {

    /**
     * data : {"expireDay":"7","partnerBill":{"next":false,"total":3,"previous":false,"index":1,"pageSize":10,"list":[{"goodPromotionId":null,"orderId":390583,"superRatio":0.02,"loginCode":"990*****003","type":"ordinary","signDate":"","productName":"MURMURSTUDIO GLADIATOR系列 针织网布背心","isCod":0,"couponAmount":0,"redAmount":0,"id":580680,"superId":4150,"skuId":125568,"memberId":3031128,"estimateDate":"2018/04/18 23:59:59","designerId":10168,"productId":108701,"productImg":"/sp/1503/ca687664b6ebc5d3199aeced1b99a90d","itemStatusCode":1,"orderPromotionId":null,"size":"M码","afterApply":{"reship":0,"exchange":0,"refund":1},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":780,"masterRatio":0.02,"isComment":-1,"aftering":"none","parentRatio":0.03,"color":"白色","partnerRatio":0.12,"orderSn":"Q15239423718151128","promotionPrice":0,"totalPrice":780,"paymentType":"WALLET","productCombId":null,"deliverySn":null,"deliveryCorpCode":"other","itemStatus":"商品正在调拨","orderPromotionAmount":0,"after":1,"skuSn":"JM0125120300201502","createDate":"2018/04/17 13:19:32","cartItemId":null,"quantity":1,"productSn":"JM01251203002","partnerAmount":0,"actualAmount":780,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":0,"designer":"MURMUR STUDIO","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":null,"partnerId":4147,"expectDate":""},{"goodPromotionId":null,"orderId":390578,"superRatio":0.02,"loginCode":"990*****009","type":"ordinary","signDate":"","productName":"LAC高级珠宝天然蓝宝石戒指女18k金定制镶嵌彩色宝石证书专柜排戒","isCod":0,"couponAmount":0,"redAmount":0,"id":580675,"superId":4150,"skuId":383620,"memberId":3031135,"estimateDate":"","designerId":11411,"productId":194554,"productImg":"/2018/04/10/0701113eba4ab4b33fcb1dbee011139f736686.jpg","itemStatusCode":8,"orderPromotionId":null,"size":"0.1-0.15克拉","afterApply":{"reship":0,"exchange":0,"refund":0},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":1999,"masterRatio":0.02,"isComment":1,"aftering":"refund","parentRatio":0.01,"color":"18K白金","partnerRatio":0.05,"orderSn":"Q15239414161001135","promotionPrice":0,"totalPrice":1999,"paymentType":"WALLET","productCombId":null,"deliverySn":"22222","deliveryCorpCode":"shunfeng","itemStatus":"退款成功","orderPromotionAmount":0,"after":1,"skuSn":"JXYXA1SAa","createDate":"2018/04/17 13:03:36","cartItemId":null,"quantity":1,"productSn":"JXYXA1SA","partnerAmount":0,"actualAmount":1999,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":9,"designer":"LAC","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":4147,"expectDate":""},{"goodPromotionId":null,"orderId":390571,"superRatio":0.02,"loginCode":"990*****003","type":"ordinary","signDate":"2018/04/17 12:23:14","productName":"Unravel Project  字母印图垂直下摆宽松袖口黑色短袖休闲毛衣男装","isCod":0,"couponAmount":0,"redAmount":0,"id":580668,"superId":4150,"skuId":383434,"memberId":3031128,"estimateDate":"","designerId":11392,"productId":194484,"productImg":"/2018/04/09/081950257c48b4a833a9134a7abe2dcc13f8ab.jpg","itemStatusCode":8,"orderPromotionId":null,"size":"185/100A","afterApply":{"reship":0,"exchange":0,"refund":0},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":8600,"masterRatio":0.02,"isComment":1,"aftering":"refund","parentRatio":0.02,"color":"黑色","partnerRatio":0.05,"orderSn":"Q15239388669621128","promotionPrice":0,"totalPrice":8600,"paymentType":"WALLET","productCombId":null,"deliverySn":"1111","deliveryCorpCode":"shunfeng","itemStatus":"退款成功","orderPromotionAmount":0,"after":1,"skuSn":"98B2UNPTP03310007","createDate":"2018/04/17 12:21:07","cartItemId":null,"quantity":1,"productSn":"98B2UNPTP033100","partnerAmount":0,"actualAmount":8600,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":9,"designer":"UNRAVEL PROJECT","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":4147,"expectDate":"2018/04/24 12:23:14"}]}}
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
         * expireDay : 7
         * partnerBill : {"next":false,"total":3,"previous":false,"index":1,"pageSize":10,"list":[{"goodPromotionId":null,"orderId":390583,"superRatio":0.02,"loginCode":"990*****003","type":"ordinary","signDate":"","productName":"MURMURSTUDIO GLADIATOR系列 针织网布背心","isCod":0,"couponAmount":0,"redAmount":0,"id":580680,"superId":4150,"skuId":125568,"memberId":3031128,"estimateDate":"2018/04/18 23:59:59","designerId":10168,"productId":108701,"productImg":"/sp/1503/ca687664b6ebc5d3199aeced1b99a90d","itemStatusCode":1,"orderPromotionId":null,"size":"M码","afterApply":{"reship":0,"exchange":0,"refund":1},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":780,"masterRatio":0.02,"isComment":-1,"aftering":"none","parentRatio":0.03,"color":"白色","partnerRatio":0.12,"orderSn":"Q15239423718151128","promotionPrice":0,"totalPrice":780,"paymentType":"WALLET","productCombId":null,"deliverySn":null,"deliveryCorpCode":"other","itemStatus":"商品正在调拨","orderPromotionAmount":0,"after":1,"skuSn":"JM0125120300201502","createDate":"2018/04/17 13:19:32","cartItemId":null,"quantity":1,"productSn":"JM01251203002","partnerAmount":0,"actualAmount":780,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":0,"designer":"MURMUR STUDIO","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":null,"partnerId":4147,"expectDate":""},{"goodPromotionId":null,"orderId":390578,"superRatio":0.02,"loginCode":"990*****009","type":"ordinary","signDate":"","productName":"LAC高级珠宝天然蓝宝石戒指女18k金定制镶嵌彩色宝石证书专柜排戒","isCod":0,"couponAmount":0,"redAmount":0,"id":580675,"superId":4150,"skuId":383620,"memberId":3031135,"estimateDate":"","designerId":11411,"productId":194554,"productImg":"/2018/04/10/0701113eba4ab4b33fcb1dbee011139f736686.jpg","itemStatusCode":8,"orderPromotionId":null,"size":"0.1-0.15克拉","afterApply":{"reship":0,"exchange":0,"refund":0},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":1999,"masterRatio":0.02,"isComment":1,"aftering":"refund","parentRatio":0.01,"color":"18K白金","partnerRatio":0.05,"orderSn":"Q15239414161001135","promotionPrice":0,"totalPrice":1999,"paymentType":"WALLET","productCombId":null,"deliverySn":"22222","deliveryCorpCode":"shunfeng","itemStatus":"退款成功","orderPromotionAmount":0,"after":1,"skuSn":"JXYXA1SAa","createDate":"2018/04/17 13:03:36","cartItemId":null,"quantity":1,"productSn":"JXYXA1SA","partnerAmount":0,"actualAmount":1999,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":9,"designer":"LAC","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":4147,"expectDate":""},{"goodPromotionId":null,"orderId":390571,"superRatio":0.02,"loginCode":"990*****003","type":"ordinary","signDate":"2018/04/17 12:23:14","productName":"Unravel Project  字母印图垂直下摆宽松袖口黑色短袖休闲毛衣男装","isCod":0,"couponAmount":0,"redAmount":0,"id":580668,"superId":4150,"skuId":383434,"memberId":3031128,"estimateDate":"","designerId":11392,"productId":194484,"productImg":"/2018/04/09/081950257c48b4a833a9134a7abe2dcc13f8ab.jpg","itemStatusCode":8,"orderPromotionId":null,"size":"185/100A","afterApply":{"reship":0,"exchange":0,"refund":0},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":8600,"masterRatio":0.02,"isComment":1,"aftering":"refund","parentRatio":0.02,"color":"黑色","partnerRatio":0.05,"orderSn":"Q15239388669621128","promotionPrice":0,"totalPrice":8600,"paymentType":"WALLET","productCombId":null,"deliverySn":"1111","deliveryCorpCode":"shunfeng","itemStatus":"退款成功","orderPromotionAmount":0,"after":1,"skuSn":"98B2UNPTP03310007","createDate":"2018/04/17 12:21:07","cartItemId":null,"quantity":1,"productSn":"98B2UNPTP033100","partnerAmount":0,"actualAmount":8600,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":9,"designer":"UNRAVEL PROJECT","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":4147,"expectDate":"2018/04/24 12:23:14"}]}
         */

        private String expireDay;
        private PartnerBillBean partnerBill;

        public String getExpireDay() {
            return expireDay;
        }

        public void setExpireDay(String expireDay) {
            this.expireDay = expireDay;
        }

        public PartnerBillBean getPartnerBill() {
            return partnerBill;
        }

        public void setPartnerBill(PartnerBillBean partnerBill) {
            this.partnerBill = partnerBill;
        }

        public static class PartnerBillBean {
            /**
             * next : false
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 10
             * list : [{"goodPromotionId":null,"orderId":390583,"superRatio":0.02,"loginCode":"990*****003","type":"ordinary","signDate":"","productName":"MURMURSTUDIO GLADIATOR系列 针织网布背心","isCod":0,"couponAmount":0,"redAmount":0,"id":580680,"superId":4150,"skuId":125568,"memberId":3031128,"estimateDate":"2018/04/18 23:59:59","designerId":10168,"productId":108701,"productImg":"/sp/1503/ca687664b6ebc5d3199aeced1b99a90d","itemStatusCode":1,"orderPromotionId":null,"size":"M码","afterApply":{"reship":0,"exchange":0,"refund":1},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":780,"masterRatio":0.02,"isComment":-1,"aftering":"none","parentRatio":0.03,"color":"白色","partnerRatio":0.12,"orderSn":"Q15239423718151128","promotionPrice":0,"totalPrice":780,"paymentType":"WALLET","productCombId":null,"deliverySn":null,"deliveryCorpCode":"other","itemStatus":"商品正在调拨","orderPromotionAmount":0,"after":1,"skuSn":"JM0125120300201502","createDate":"2018/04/17 13:19:32","cartItemId":null,"quantity":1,"productSn":"JM01251203002","partnerAmount":0,"actualAmount":780,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":0,"designer":"MURMUR STUDIO","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":null,"partnerId":4147,"expectDate":""},{"goodPromotionId":null,"orderId":390578,"superRatio":0.02,"loginCode":"990*****009","type":"ordinary","signDate":"","productName":"LAC高级珠宝天然蓝宝石戒指女18k金定制镶嵌彩色宝石证书专柜排戒","isCod":0,"couponAmount":0,"redAmount":0,"id":580675,"superId":4150,"skuId":383620,"memberId":3031135,"estimateDate":"","designerId":11411,"productId":194554,"productImg":"/2018/04/10/0701113eba4ab4b33fcb1dbee011139f736686.jpg","itemStatusCode":8,"orderPromotionId":null,"size":"0.1-0.15克拉","afterApply":{"reship":0,"exchange":0,"refund":0},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":1999,"masterRatio":0.02,"isComment":1,"aftering":"refund","parentRatio":0.01,"color":"18K白金","partnerRatio":0.05,"orderSn":"Q15239414161001135","promotionPrice":0,"totalPrice":1999,"paymentType":"WALLET","productCombId":null,"deliverySn":"22222","deliveryCorpCode":"shunfeng","itemStatus":"退款成功","orderPromotionAmount":0,"after":1,"skuSn":"JXYXA1SAa","createDate":"2018/04/17 13:03:36","cartItemId":null,"quantity":1,"productSn":"JXYXA1SA","partnerAmount":0,"actualAmount":1999,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":9,"designer":"LAC","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":4147,"expectDate":""},{"goodPromotionId":null,"orderId":390571,"superRatio":0.02,"loginCode":"990*****003","type":"ordinary","signDate":"2018/04/17 12:23:14","productName":"Unravel Project  字母印图垂直下摆宽松袖口黑色短袖休闲毛衣男装","isCod":0,"couponAmount":0,"redAmount":0,"id":580668,"superId":4150,"skuId":383434,"memberId":3031128,"estimateDate":"","designerId":11392,"productId":194484,"productImg":"/2018/04/09/081950257c48b4a833a9134a7abe2dcc13f8ab.jpg","itemStatusCode":8,"orderPromotionId":null,"size":"185/100A","afterApply":{"reship":0,"exchange":0,"refund":0},"flashPromotionId":null,"commentId":null,"isAfter":1,"promotionAmount":0,"productPrice":8600,"masterRatio":0.02,"isComment":1,"aftering":"refund","parentRatio":0.02,"color":"黑色","partnerRatio":0.05,"orderSn":"Q15239388669621128","promotionPrice":0,"totalPrice":8600,"paymentType":"WALLET","productCombId":null,"deliverySn":"1111","deliveryCorpCode":"shunfeng","itemStatus":"退款成功","orderPromotionAmount":0,"after":1,"skuSn":"98B2UNPTP03310007","createDate":"2018/04/17 12:21:07","cartItemId":null,"quantity":1,"productSn":"98B2UNPTP033100","partnerAmount":0,"actualAmount":8600,"paymentTypeName":"钱包支付","deliveryType":0,"diffAmount":9,"designer":"UNRAVEL PROJECT","parentId":4146,"masterId":4145,"reminded":"该笔订单将原路返回到您的D2C钱包","deliveryCorpName":"顺丰速递","partnerId":4147,"expectDate":"2018/04/24 12:23:14"}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<ListBean> list;

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {


                /**
                 * goodPromotionId : null
                 * orderId : 390583
                 * superRatio : 0.02
                 * loginCode : 990*****003
                 * type : ordinary
                 * signDate :
                 * productName : MURMURSTUDIO GLADIATOR系列 针织网布背心
                 * isCod : 0
                 * couponAmount : 0
                 * redAmount : 0
                 * id : 580680
                 * superId : 4150
                 * skuId : 125568
                 * memberId : 3031128
                 * estimateDate : 2018/04/18 23:59:59
                 * designerId : 10168
                 * productId : 108701
                 * productImg : /sp/1503/ca687664b6ebc5d3199aeced1b99a90d
                 * itemStatusCode : 1
                 * orderPromotionId : null
                 * size : M码
                 * afterApply : {"reship":0,"exchange":0,"refund":1}
                 * flashPromotionId : null
                 * commentId : null
                 * isAfter : 1
                 * promotionAmount : 0
                 * productPrice : 780
                 * masterRatio : 0.02
                 * isComment : -1
                 * aftering : none
                 * parentRatio : 0.03
                 * color : 白色
                 * partnerRatio : 0.12
                 * orderSn : Q15239423718151128
                 * promotionPrice : 0
                 * totalPrice : 780
                 * paymentType : WALLET
                 * productCombId : null
                 * deliverySn : null
                 * deliveryCorpCode : other
                 * itemStatus : 商品正在调拨
                 * orderPromotionAmount : 0
                 * after : 1
                 * skuSn : JM0125120300201502
                 * createDate : 2018/04/17 13:19:32
                 * cartItemId : null
                 * quantity : 1
                 * productSn : JM01251203002
                 * partnerAmount : 0
                 * actualAmount : 780
                 * paymentTypeName : 钱包支付
                 * deliveryType : 0
                 * diffAmount : 0
                 * designer : MURMUR STUDIO
                 * parentId : 4146
                 * masterId : 4145
                 * reminded : 该笔订单将原路返回到您的D2C钱包
                 * deliveryCorpName : null
                 * partnerId : 4147
                 * expectDate :
                 */
                private String productSource;
                private long goodPromotionId;
                private long orderId;
                private double superRatio;
                private String loginCode;
                private String type;
                private String signDate;
                private String productName;
                private int isCod;
                private double couponAmount;
                private double redAmount;
                private int id;
                private int superId;
                private int skuId;
                private long memberId;
                private String estimateDate;
                private int designerId;
                private int productId;
                private String productImg;
                private int itemStatusCode;
                private long orderPromotionId;
                private String size;
                private AfterApplyBean afterApply;
                private long flashPromotionId;
                private long commentId;
                private int isAfter;
                private double promotionAmount;
                private double productPrice;
                private double masterRatio;
                private int isComment;
                private String aftering;
                private double parentRatio;
                private String color;
                private double partnerRatio;
                private String orderSn;
                private double promotionPrice;
                private double totalPrice;
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
                private double partnerAmount;
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

                private double taxAmount;

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
                public long getGoodPromotionId() {
                    return goodPromotionId;
                }

                public void setGoodPromotionId(long goodPromotionId) {
                    this.goodPromotionId = goodPromotionId;
                }

                public long getOrderId() {
                    return orderId;
                }

                public void setOrderId(long orderId) {
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

                public String getSignDate() {
                    return signDate;
                }

                public void setSignDate(String signDate) {
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

                public double getRedAmount() {
                    return redAmount;
                }

                public void setRedAmount(double redAmount) {
                    this.redAmount = redAmount;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSuperId() {
                    return superId;
                }

                public void setSuperId(int superId) {
                    this.superId = superId;
                }

                public int getSkuId() {
                    return skuId;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
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

                public int getIsAfter() {
                    return isAfter;
                }

                public void setIsAfter(int isAfter) {
                    this.isAfter = isAfter;
                }

                public double getPromotionAmount() {
                    return promotionAmount;
                }

                public void setPromotionAmount(double promotionAmount) {
                    this.promotionAmount = promotionAmount;
                }

                public double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(double productPrice) {
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

                public double getPartnerAmount() {
                    return partnerAmount;
                }

                public void setPartnerAmount(double partnerAmount) {
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
}
