package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonContentBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private MemberSharesBean memberShares;
        private MemberBean member;
        private Lives lives;

        public Lives getLives() {
            return lives;
        }

        public void setLives(Lives lives) {
            this.lives = lives;
        }

        public MemberSharesBean getMemberShares() {
            return memberShares;
        }

        public void setMemberShares(MemberSharesBean memberShares) {
            this.memberShares = memberShares;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }


        public static class Lives{
            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<LiveListBean.DataBean.LivesBean.ListBean> list;

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

            public List<LiveListBean.DataBean.LivesBean.ListBean> getList() {
                return list;
            }

            public void setList(List<LiveListBean.DataBean.LivesBean.ListBean> list) {
                this.list = list;
            }
        }

        public static class MemberSharesBean {

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
                private Date verifyDate;

                public Date getVerifyDate() {
                    return verifyDate;
                }

                public void setVerifyDate(Date verifyDate) {
                    this.verifyDate = verifyDate;
                }

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

        public static class MemberBean {

            private int designerId;
            private String birthDay;
            private int bindPartnerId;
            private String sex;
            private String loginCode;
            private String backgroud;
            private String mobile;
            private int allowPartner;
            private int type;
            private String recCode;
            private String head;
            private String thirdPic;
            private boolean d2c;
            private String name;
            private String nickname;
            private int id;
            private int partnerId;
            private int recId;
            private String email;
            private int memberId;
            private String nationCode;

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public String getBirthDay() {
                return birthDay;
            }

            public void setBirthDay(String birthDay) {
                this.birthDay = birthDay;
            }

            public int getBindPartnerId() {
                return bindPartnerId;
            }

            public void setBindPartnerId(int bindPartnerId) {
                this.bindPartnerId = bindPartnerId;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getLoginCode() {
                return loginCode;
            }

            public void setLoginCode(String loginCode) {
                this.loginCode = loginCode;
            }

            public String getBackgroud() {
                return backgroud;
            }

            public void setBackgroud(String backgroud) {
                this.backgroud = backgroud;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getAllowPartner() {
                return allowPartner;
            }

            public void setAllowPartner(int allowPartner) {
                this.allowPartner = allowPartner;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRecCode() {
                return recCode;
            }

            public void setRecCode(String recCode) {
                this.recCode = recCode;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getThirdPic() {
                return thirdPic;
            }

            public void setThirdPic(String thirdPic) {
                this.thirdPic = thirdPic;
            }

            public boolean isD2c() {
                return d2c;
            }

            public void setD2c(boolean d2c) {
                this.d2c = d2c;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(int partnerId) {
                this.partnerId = partnerId;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getNationCode() {
                return nationCode;
            }

            public void setNationCode(String nationCode) {
                this.nationCode = nationCode;
            }
        }

    }
}
