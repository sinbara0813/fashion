package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/26 16:55
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CollageProductDetailBean extends BaseBean {

    /**
     * data : {"product":{"img":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","originalPrice":699,"sellPrice":699,"soonPrice":699,"isCart":1,"flashPrice":0,"categoryName":"T恤","colors":[{"img":"/2018/04/14/0803265be43e95454564e66e4bf9501f634787.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":699,"recomScore":0,"mainPic":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","isTopical":false,"productSellType":"SPOT","id":195201,"sn":"A89","brand":"amorebkk","designerId":11195,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":699,"secondRatio":0.2,"store":1,"designer":"ploy","isSubscribe":0,"consults":0,"collagePromotionId":5,"minPrice":699,"name":"amorebkk ploy 泰国潮牌一字领露肩T恤","collectioned":0,"maxPrice":699,"isAfter":0,"mark":1,"categoryId":1591},"skuList":[{"sizeId":1,"flashStore":5,"color":"黑色","size":"均码","colorId":1,"freezeStore":0,"id":385720,"sn":"A89-F"}],"groupList":[],"collagePromotion":{"beginDate":"2018-06-26 00:00:00","sharePic":null,"shareContent":null,"shareTitle":null,"endDate":"2018-07-08 00:00:00","memberCount":3,"currentCount":0,"name":"拼着买 更便宜","id":6,"sort":2,"status":1,"promotionStatus":1},"ratio":"0.5"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private ProductDetailBean.DataBean.ProductBean product;
        private CollagePromotionBean collagePromotion;
        private String ratio;
        private List<SkuListBean> skuList;
        private List<GroupListBean> groupList;

        private boolean next;

        public boolean isNext() {
            return next;
        }

        public void setNext(boolean next) {
            this.next = next;
        }
        public ProductDetailBean.DataBean.ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductDetailBean.DataBean.ProductBean product) {
            this.product = product;
        }

        public CollagePromotionBean getCollagePromotion() {
            return collagePromotion;
        }

        public void setCollagePromotion(CollagePromotionBean collagePromotion) {
            this.collagePromotion = collagePromotion;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public List<GroupListBean> getGroupList() {
            return groupList;
        }

        public void setGroupList(List<GroupListBean> groupList) {
            this.groupList = groupList;
        }

        public static class CollagePromotionBean {
            /**
             * beginDate : 2018-06-26 00:00:00
             * sharePic : null
             * shareContent : null
             * shareTitle : null
             * endDate : 2018-07-08 00:00:00
             * memberCount : 3
             * currentCount : 0
             * name : 拼着买 更便宜
             * id : 6
             * sort : 2
             * status : 1
             * promotionStatus : 1
             */

            private long beginDate;
            private String sharePic;
            private String shareContent;
            private String shareTitle;
            private long endDate;
            private int memberCount;
            private int currentCount;
            private String name;
            private int id;
            private int sort;
            @SerializedName("status")
            private int statusX;
            private int promotionStatus;//0表示即将开始，1表示进行中，-1表示结束

            public long getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(long beginDate) {
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

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
            }

            public int getCurrentCount() {
                return currentCount;
            }

            public void setCurrentCount(int currentCount) {
                this.currentCount = currentCount;
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
             * sizeId : 1
             * flashStore : 5
             * color : 黑色
             * size : 均码
             * colorId : 1
             * freezeStore : 0
             * id : 385720
             * sn : A89-F
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

        public static class GroupListBean{
            private String groupSn;
            private int initiatorMemberId;
            private String initiatorName;
            private String headPic;
            private int currentMemberCount;
            private int memberCount;
            private long endDate;
            private int promotionId;
            private int id;

            public String getGroupSn() {
                return groupSn;
            }

            public void setGroupSn(String groupSn) {
                this.groupSn = groupSn;
            }

            public int getInitiatorMemberId() {
                return initiatorMemberId;
            }

            public void setInitiatorMemberId(int initiatorMemberId) {
                this.initiatorMemberId = initiatorMemberId;
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

            public int getCurrentMemberCount() {
                return currentMemberCount;
            }

            public void setCurrentMemberCount(int currentMemberCount) {
                this.currentMemberCount = currentMemberCount;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
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
        }
    }
}
