package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/1 15:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainShareBean extends BaseBean {

    /**
     * data : {"shares":[{"role":0,"city":null,"memberName":"匿名_2873968","description":"炒鸡美貌！！","likeMeCount":2,"video":null,"liked":null,"timeLength":0,"memberHead":"","top":1,"street":null,"id":11352,"pics":["/app/f/17/08/24/50D4F4708ED3CABAF1693114B390A957"],"memberId":2873968,"createDate":"2017/08/24 18:17:52","timestamp":1503569872000,"designerId":0,"productId":161913,"picTag":[],"url":null,"commentCount":29,"topicId":0,"x":0,"y":0,"topicName":null,"watchCount":55,"resourceType":"pic"},{"role":0,"city":null,"memberName":"匿名_2873968","description":"八折买的，便宜了好几百，哈哈整个表都是透明的超级棒！","likeMeCount":3,"video":null,"liked":null,"timeLength":0,"memberHead":"","top":1,"street":null,"id":11351,"pics":["/app/f/17/08/24/FCC2F1ED72CEEEEF9F6280E8A5AFEF68","/app/f/17/08/24/434B5C56FF6AA0ADEC32C6D4BDBBB3F0","/app/f/17/08/24/CB750C26EAC8E5A83F70FE72F1435C17"],"memberId":2873968,"createDate":"2017/08/24 18:12:38","timestamp":1503569558000,"designerId":0,"productId":146293,"picTag":[],"url":null,"commentCount":13,"topicId":0,"x":0,"y":0,"topicName":null,"watchCount":11,"resourceType":"pic"},{"role":0,"city":null,"memberName":"匿名_2873968","description":"但愿女朋友会喜欢吧","likeMeCount":6,"video":null,"liked":null,"timeLength":0,"memberHead":"","top":1,"street":null,"id":11350,"pics":["/app/f/17/08/24/F29599B6585002A1A4AE86AF12690B17","/app/f/17/08/24/F6649B02000FAB88D65F4F97499DEE51"],"memberId":2873968,"createDate":"2017/08/24 18:07:40","timestamp":1503569260000,"designerId":0,"productId":167718,"picTag":[],"url":null,"commentCount":7,"topicId":0,"x":0,"y":0,"topicName":null,"watchCount":8,"resourceType":"pic"},{"role":0,"city":null,"memberName":"匿名_2873968","description":"做工不错","likeMeCount":1,"video":null,"liked":null,"timeLength":0,"memberHead":"","top":1,"street":null,"id":11349,"pics":["/app/f/17/08/24/2E8F2BBC0F17C0738E1AD665A50F2C4F","/app/f/17/08/24/83965C0C1FBD58C7C87B8E9C9270132D"],"memberId":2873968,"createDate":"2017/08/24 18:03:48","timestamp":1503569028000,"designerId":0,"productId":163541,"picTag":[],"url":null,"commentCount":9,"topicId":0,"x":0,"y":0,"topicName":null,"watchCount":3,"resourceType":"pic"},{"role":0,"city":null,"memberName":"匿名_2873968","description":"黑帽子遮阳~","likeMeCount":0,"video":null,"liked":null,"timeLength":0,"memberHead":"","top":1,"street":null,"id":11347,"pics":["/app/f/17/08/24/2F7A665F0850E00296B07A24296F6F81"],"memberId":2873968,"createDate":"2017/08/24 18:00:38","timestamp":1503568838000,"designerId":0,"productId":165114,"picTag":[],"url":null,"commentCount":0,"topicId":0,"x":0,"y":0,"topicName":null,"watchCount":0,"resourceType":"pic"},{"role":0,"city":null,"memberName":"匿名_2873968","description":"老公拍的照片~这个郑恺小猎豹同款的小白鞋好舒适！","likeMeCount":1,"video":null,"liked":null,"timeLength":0,"memberHead":"","top":1,"street":null,"id":11345,"pics":["/app/f/17/08/24/C2C2BA8D7D55922E2B1A8455A5AB7465","/app/f/17/08/24/8F08C5C66A188AD9A98856CD626D1252","/app/f/17/08/24/48DAB9ECC35F6EA88C3E484D03A2FC42","/app/f/17/08/24/7AF3BC90C806F2FF1F0AA99D51EB1CD4","/app/f/17/08/24/9DBEF900C95425BA48224B8AA7155B58","/app/f/17/08/24/B573FC1D53611E6F5848FFB4620E7D00","/app/f/17/08/24/4AB00AC17433D6B5F85641CE2E9443FE","/app/f/17/08/24/5187D46D53461CC1E93E4F5E95E15088","/app/f/17/08/24/045A2C705E65EEC3CA437AFAA2FD9792"],"memberId":2873968,"createDate":"2017/08/24 17:54:10","timestamp":1503568450000,"designerId":0,"productId":158935,"picTag":[],"url":null,"commentCount":7,"topicId":0,"x":0,"y":0,"topicName":null,"watchCount":4,"resourceType":"pic"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<SharesBean> shares;

        public List<SharesBean> getShares() {
            return shares;
        }

        public void setShares(List<SharesBean> shares) {
            this.shares = shares;
        }

        public static class SharesBean {
            /**
             * role : 0
             * city : null
             * memberName : 匿名_2873968
             * description : 炒鸡美貌！！
             * likeMeCount : 2
             * video : null
             * liked : null
             * timeLength : 0
             * memberHead :
             * top : 1
             * street : null
             * id : 11352
             * pics : ["/app/f/17/08/24/50D4F4708ED3CABAF1693114B390A957"]
             * memberId : 2873968
             * createDate : 2017/08/24 18:17:52
             * timestamp : 1503569872000
             * designerId : 0
             * productId : 161913
             * picTag : []
             * url : null
             * commentCount : 29
             * topicId : 0
             * x : 0.0
             * y : 0.0
             * topicName : null
             * watchCount : 55
             * resourceType : pic
             */

            private int role;
            private String city;
            private String memberName;
            private String description;
            private int likeMeCount;
            private String video;
            private int liked;
            private int timeLength;
            private String memberHead;
            private int top;
            private String street;
            private int id;
            private int memberId;
            private String createDate;
            private long timestamp;
            private int designerId;
            private int productId;
            private String url;
            private int commentCount;
            private int topicId;
            private double x;
            private double y;
            private String topicName;
            private int watchCount;
            private String resourceType;
            private List<String> pics;
            private List<String> picTag;

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getLikeMeCount() {
                return likeMeCount;
            }

            public void setLikeMeCount(int likeMeCount) {
                this.likeMeCount = likeMeCount;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public int getLiked() {
                return liked;
            }

            public void setLiked(int liked) {
                this.liked = liked;
            }

            public int getTimeLength() {
                return timeLength;
            }

            public void setTimeLength(int timeLength) {
                this.timeLength = timeLength;
            }

            public String getMemberHead() {
                return memberHead;
            }

            public void setMemberHead(String memberHead) {
                this.memberHead = memberHead;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public int getTopicId() {
                return topicId;
            }

            public void setTopicId(int topicId) {
                this.topicId = topicId;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public String getTopicName() {
                return topicName;
            }

            public void setTopicName(String topicName) {
                this.topicName = topicName;
            }

            public int getWatchCount() {
                return watchCount;
            }

            public void setWatchCount(int watchCount) {
                this.watchCount = watchCount;
            }

            public String getResourceType() {
                return resourceType;
            }

            public void setResourceType(String resourceType) {
                this.resourceType = resourceType;
            }

            public List<String> getPics() {
                return pics;
            }

            public void setPics(List<String> pics) {
                this.pics = pics;
            }

            public List<String> getPicTag() {
                return picTag;
            }

            public void setPicTag(List<String> picTag) {
                this.picTag = picTag;
            }
        }
    }
}
