package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 * Description : CollageDetailBean
 */

public class CollageDetailBean extends BaseBean {

    /**
     * data : {"product":{"productSource":"D2CMALL","recommendation":null,"sellPrice":5880,"isShipping":1,"afterMemo":"自您签收商品之日起7天内，D2C为您提供退货服务","firstRatio":0.03,"isCod":0,"subTitle":null,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":5880,"productSaleType":"CONSIGN","isTopical":false,"productSellType":"CUSTOM","id":194087,"designerPic":"","brand":"ZHANGSHUAI","estimateDate":null,"designerId":10053,"imgs":["/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","/2018/04/13/022434d3e1bf35d24330c7e3ddd25e30c832e8.jpg","/2018/04/13/022436d74abb7d8112a66080eed3ed1e9964b2.jpg","/2018/04/13/022438cc8c85271153f1c1a876651fc9090b66.jpg","/2018/04/13/022440f782f39bfb87bb11677a73c15ca4231b.jpg","/2018/04/13/0224429e6f5c10ea763e59477dda548dbf592c.jpg"],"grossRatio":1,"isInstallment":0,"isSubscribe":0,"minPrice":5880,"name":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collectioned":0,"maxPrice":5880,"isTaxation":1,"isAfter":1,"status":0,"img":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","originalPrice":5880,"remark":"预计7个工作日发货","soonPrice":5880,"isCart":1,"video":null,"flashPrice":0,"colors":[{"img":"/2018/04/13/02242912da45c02c6906be3f4a8bb7d07d800e.jpg","code":"024","groupId":0,"name":"颜色","id":158,"value":"黑白"}],"importType":0,"collagePrice":1000,"sn":"JZ00481010002","after":1,"introduction":null,"estimateDay":7,"isSpot":false,"salePrice":5880,"secondRatio":0.16,"productStatus":1,"store":1,"picStyle":0,"mark":1},"skuList":[{"sizeId":26,"flashStore":2,"color":"黑白","size":"S码","colorId":158,"freezeStore":0,"id":382203,"sn":"JZ0048101000202401"},{"sizeId":27,"flashStore":1,"color":"黑白","size":"M码","colorId":158,"freezeStore":1,"id":382204,"sn":"JZ0048101000202402"},{"sizeId":28,"flashStore":1,"color":"黑白","size":"L码","colorId":158,"freezeStore":1,"id":382205,"sn":"JZ0048101000202403"}],"collageGroup":{"currentMemberCount":0,"productId":194087,"endDate":1530115092000,"groupSn":"G1530169128832","memberCount":2,"initiatorName":null,"headPic":null,"payMemberCount":0,"promotionId":15,"collageOrders":[],"id":12,"createDate":"2018/06/27 18:57:20","status":-1,"initiatorMemberId":3031589},"collagePromotion":{"beginDate":1530028800000,"sharePic":null,"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":2,"name":"我是一个伟大的平团","id":15,"sort":0,"status":1,"promotionStatus":1}}
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
         * product : {"productSource":"D2CMALL","recommendation":null,"sellPrice":5880,"isShipping":1,"afterMemo":"自您签收商品之日起7天内，D2C为您提供退货服务","firstRatio":0.03,"isCod":0,"subTitle":null,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":5880,"productSaleType":"CONSIGN","isTopical":false,"productSellType":"CUSTOM","id":194087,"designerPic":"","brand":"ZHANGSHUAI","estimateDate":null,"designerId":10053,"imgs":["/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","/2018/04/13/022434d3e1bf35d24330c7e3ddd25e30c832e8.jpg","/2018/04/13/022436d74abb7d8112a66080eed3ed1e9964b2.jpg","/2018/04/13/022438cc8c85271153f1c1a876651fc9090b66.jpg","/2018/04/13/022440f782f39bfb87bb11677a73c15ca4231b.jpg","/2018/04/13/0224429e6f5c10ea763e59477dda548dbf592c.jpg"],"grossRatio":1,"isInstallment":0,"isSubscribe":0,"minPrice":5880,"name":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collectioned":0,"maxPrice":5880,"isTaxation":1,"isAfter":1,"status":0,"img":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","originalPrice":5880,"remark":"预计7个工作日发货","soonPrice":5880,"isCart":1,"video":null,"flashPrice":0,"colors":[{"img":"/2018/04/13/02242912da45c02c6906be3f4a8bb7d07d800e.jpg","code":"024","groupId":0,"name":"颜色","id":158,"value":"黑白"}],"importType":0,"collagePrice":1000,"sn":"JZ00481010002","after":1,"introduction":null,"estimateDay":7,"isSpot":false,"salePrice":5880,"secondRatio":0.16,"productStatus":1,"store":1,"picStyle":0,"mark":1}
         * skuList : [{"sizeId":26,"flashStore":2,"color":"黑白","size":"S码","colorId":158,"freezeStore":0,"id":382203,"sn":"JZ0048101000202401"},{"sizeId":27,"flashStore":1,"color":"黑白","size":"M码","colorId":158,"freezeStore":1,"id":382204,"sn":"JZ0048101000202402"},{"sizeId":28,"flashStore":1,"color":"黑白","size":"L码","colorId":158,"freezeStore":1,"id":382205,"sn":"JZ0048101000202403"}]
         * collageGroup : {"currentMemberCount":0,"productId":194087,"endDate":1530115092000,"groupSn":"G1530169128832","memberCount":2,"initiatorName":null,"headPic":null,"payMemberCount":0,"promotionId":15,"collageOrders":[],"id":12,"createDate":"2018/06/27 18:57:20","status":-1,"initiatorMemberId":3031589}
         * collagePromotion : {"beginDate":1530028800000,"sharePic":null,"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":2,"name":"我是一个伟大的平团","id":15,"sort":0,"status":1,"promotionStatus":1}
         */
        private ProductDetailBean.DataBean.ProductBean product;
        private CollageGroupBean collageGroup;
        private CollagePromotionBean collagePromotion;
        private List<SkuListBean> skuList;

        public ProductDetailBean.DataBean.ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductDetailBean.DataBean.ProductBean product) {
            this.product = product;
        }

        public CollageGroupBean getCollageGroup() {
            return collageGroup;
        }

        public void setCollageGroup(CollageGroupBean collageGroup) {
            this.collageGroup = collageGroup;
        }

        public CollagePromotionBean getCollagePromotion() {
            return collagePromotion;
        }

        public void setCollagePromotion(CollagePromotionBean collagePromotion) {
            this.collagePromotion = collagePromotion;
        }

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class CollageGroupBean {
            /**
             * currentMemberCount : 0
             * productId : 194087
             * endDate : 1530115092000
             * groupSn : G1530169128832
             * memberCount : 2
             * initiatorName : null
             * headPic : null
             * payMemberCount : 0
             * promotionId : 15
             * collageOrders : []
             * id : 12
             * createDate : 2018/06/27 18:57:20
             * status : -1
             * initiatorMemberId : 3031589
             */

            private int currentMemberCount;
            private int productId;
            private Date endDate;
            private String groupSn;
            private int memberCount;
            private String initiatorName;
            private String headPic;
            private int payMemberCount;
            private int promotionId;
            private int id;
            private Date createDate;
            @SerializedName("status")
            private int statusX;
            private int initiatorMemberId;
            private List<CollageOrder> collageOrders;

            public static class CollageOrder {
                /**
                 * id : JZ0048101000202402
                 * orderSn : JZ0048101000202402
                 * type : JZ0048101000202402
                 * status : JZ0048101000202402
                 * statusName : JZ0048101000202402
                 * groupId : JZ0048101000202402
                 * promotionId : JZ0048101000202402
                 * productId : JZ0048101000202402
                 * productImage : JZ0048101000202402
                 * productName : JZ0048101000202402
                 * skuId : JZ0048101000202402
                 * size : JZ0048101000202402
                 * color : JZ0048101000202402
                 * memberId : JZ0048101000202402
                 * memberName : JZ0048101000202402
                 * loginCode : JZ0048101000202402
                 * headPic : JZ0048101000202402
                 * payParams : JZ0048101000202402
                 * totalAmount : JZ0048101000202402
                 * totalPay : JZ0048101000202402
                 */

                private int id;
                private String orderSn;
                private int type;
                //Status   AFTERCLOSE(-8, 退款成功) CLOSE(-2, 超时关闭)  REFUND(-1，待退款） WAITFORPAY(1,待付款 ) PROCESS(4, 拼团中),SUCESS(8, 拼团成功)
                private int status;
                private String statusName;
                private int groupId;
                private int promotionId;
                private long productId;
                private String productImage;
                private String productName;
                private int skuId;
                private String size;
                private String color;
                private long memberId;
                private String memberName;
                private String loginCode;
                private String headPic;
                private String payParams;
                private double totalAmount;
                private double totalPay;

                public Date getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(Date createDate) {
                    this.createDate = createDate;
                }

                private  Date createDate;
                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(String orderSn) {
                    this.orderSn = orderSn;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public int getGroupId() {
                    return groupId;
                }

                public void setGroupId(int groupId) {
                    this.groupId = groupId;
                }

                public int getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(int promotionId) {
                    this.promotionId = promotionId;
                }

                public long getProductId() {
                    return productId;
                }

                public void setProductId(long productId) {
                    this.productId = productId;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public int getSkuId() {
                    return skuId;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }

                public String getMemberName() {
                    return memberName;
                }

                public void setMemberName(String memberName) {
                    this.memberName = memberName;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public String getPayParams() {
                    return payParams;
                }

                public void setPayParams(String payParams) {
                    this.payParams = payParams;
                }

                public double getTotalAmount() {
                    return totalAmount;
                }

                public void setTotalAmount(double totalAmount) {
                    this.totalAmount = totalAmount;
                }

                public double getTotalPay() {
                    return totalPay;
                }

                public void setTotalPay(double totalPay) {
                    this.totalPay = totalPay;
                }
            }

            public int getCurrentMemberCount() {
                return currentMemberCount;
            }

            public void setCurrentMemberCount(int currentMemberCount) {
                this.currentMemberCount = currentMemberCount;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public Date getEndDate() {
                return endDate;
            }

            public void setEndDate(Date endDate) {
                this.endDate = endDate;
            }

            public String getGroupSn() {
                return groupSn;
            }

            public void setGroupSn(String groupSn) {
                this.groupSn = groupSn;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
            }

            public String getInitiatorName() {
                return initiatorName;
            }

            public void setInitiatorName(String initiatorName) {
                this.initiatorName = initiatorName;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getPayMemberCount() {
                return payMemberCount;
            }

            public void setPayMemberCount(int payMemberCount) {
                this.payMemberCount = payMemberCount;
            }

            public int getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(int promotionId) {
                this.promotionId = promotionId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Date getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Date createDate) {
                this.createDate = createDate;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public int getInitiatorMemberId() {
                return initiatorMemberId;
            }

            public void setInitiatorMemberId(int initiatorMemberId) {
                this.initiatorMemberId = initiatorMemberId;
            }

            public List<CollageOrder> getCollageOrders() {
                return collageOrders;
            }

            public void setCollageOrders(List<CollageOrder> collageOrders) {
                this.collageOrders = collageOrders;
            }

        }

        public static class CollagePromotionBean {
            /**
             * beginDate : 1530028800000
             * sharePic : null
             * shareContent : null
             * shareTitle : null
             * endDate : 1530288000000
             * memberCount : 2
             * name : 我是一个伟大的平团
             * id : 15
             * sort : 0
             * status : 1
             * promotionStatus : 1
             */

            private Date beginDate;
            private String sharePic;
            private String shareContent;
            private String shareTitle;
            private Date endDate;
            private int memberCount;
            private String name;
            private int id;
            private int sort;
            @SerializedName("status")
            private int statusX;
            private int promotionStatus;

            public Date getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(Date beginDate) {
                this.beginDate = beginDate;
            }

            public String getSharePic() {
                return sharePic;
            }

            public void setSharePic(String sharePic) {
                this.sharePic = sharePic;
            }

            public String getShareContent() {
                return shareContent;
            }

            public void setShareContent(String shareContent) {
                this.shareContent = shareContent;
            }

            public String getShareTitle() {
                return shareTitle;
            }

            public void setShareTitle(String shareTitle) {
                this.shareTitle = shareTitle;
            }

            public Date getEndDate() {
                return endDate;
            }

            public void setEndDate(Date endDate) {
                this.endDate = endDate;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public int getPromotionStatus() {
                return promotionStatus;
            }

            public void setPromotionStatus(int promotionStatus) {
                this.promotionStatus = promotionStatus;
            }
        }

        public static class SkuListBean {
            /**
             * sizeId : 26
             * flashStore : 2
             * color : 黑白
             * size : S码
             * colorId : 158
             * freezeStore : 0
             * id : 382203
             * sn : JZ0048101000202401
             */

            private int sizeId;
            private int flashStore;
            private String color;
            private String size;
            private int colorId;
            private int freezeStore;
            private int id;
            private String sn;

            public int getSizeId() {
                return sizeId;
            }

            public void setSizeId(int sizeId) {
                this.sizeId = sizeId;
            }

            public int getFlashStore() {
                return flashStore;
            }

            public void setFlashStore(int flashStore) {
                this.flashStore = flashStore;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public int getColorId() {
                return colorId;
            }

            public void setColorId(int colorId) {
                this.colorId = colorId;
            }

            public int getFreezeStore() {
                return freezeStore;
            }

            public void setFreezeStore(int freezeStore) {
                this.freezeStore = freezeStore;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }
        }
    }
}
