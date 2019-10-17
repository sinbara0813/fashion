package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/7 19:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MyFollowBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private ShareBean.DataEntity.MemberSharesEntity memberShares;

        private LiveListBean.DataBean.LivesBean live;

        public ShareBean.DataEntity.MemberSharesEntity getMemberShares() {
            return memberShares;
        }

        public void setMemberShares(ShareBean.DataEntity.MemberSharesEntity memberShares) {
            this.memberShares = memberShares;
        }

        public LiveListBean.DataBean.LivesBean getLive() {
            return live;
        }

        public void setLive(LiveListBean.DataBean.LivesBean live) {
            this.live = live;
        }

        public static class MemberSharesEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;

            private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> list;

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

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> getList() {
                return list;
            }

            public void setList(List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private long memberId;
                private String memberName;
                private String memberHead;
                private String createDate;
                private String city;
                private String street;
                private double x;
                private double y;
                private String description;
                private int likeMeCount;
                private int commentCount;
                private List<String> pics;
                private long productId;
                private int designerId;
                private int liked;
                private int role;
                private int type;
                private int follow;
                private String video;
                private int timeLength;

                /**
                 * 视频长度 单位秒
                 */
                public int getTimeLength() {
                    return timeLength;
                }

                public void setTimeLength(int timeLength) {
                    this.timeLength = timeLength;
                }

                /**
                 * 获取视频地址
                 */
                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
                }

                public int getFollow() {
                    return follow;
                }

                public void setFollow(int follow) {
                    this.follow = follow;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getRole() {
                    return role;
                }

                public void setRole(int role) {
                    this.role = role;
                }

                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public int getLiked() {
                    return liked;
                }

                public void setLiked(int liked) {
                    this.liked = liked;
                }

                private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.PicTagBean> picTag;

                public List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.PicTagBean> getPicTag() {
                    return picTag;
                }

                public void setPicTag(List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.PicTagBean> picTag) {
                    this.picTag = picTag;
                }

                public static class PicTagBean {
                    private int code;
                    private String picUrl;
                    private String tagName;
                    private int productId;
                    private double productPrice;
                    private String tagX;
                    private String tagY;

                    public int getCode() {
                        return code;
                    }

                    public void setCode(int code) {
                        this.code = code;
                    }

                    public String getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(String picUrl) {
                        this.picUrl = picUrl;
                    }

                    public String getTagName() {
                        return tagName;
                    }

                    public void setTagName(String tagName) {
                        this.tagName = tagName;
                    }

                    public int getProductId() {
                        return productId;
                    }

                    public void setProductId(int productId) {
                        this.productId = productId;
                    }

                    public double getProductPrice() {
                        return productPrice;
                    }

                    public void setProductPrice(double productPrice) {
                        this.productPrice = productPrice;
                    }

                    public String getTagX() {
                        return tagX;
                    }

                    public void setTagX(String tagX) {
                        this.tagX = tagX;
                    }

                    public String getTagY() {
                        return tagY;
                    }

                    public void setTagY(String tagY) {
                        this.tagY = tagY;
                    }
                }

                public long getProductId() {
                    return productId;
                }

                public void setProductId(long productId) {
                    this.productId = productId;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                /**
                 * id : 388
                 * memberId : 844001
                 * headPic : /app/a/16/03/17/7be452b18766e9496334a1d6151e11e6
                 * nickName : 南m🎒🎒🎒🎒🎒
                 * sourceId : 2026
                 * content : 啦啦啦啦
                 * verified : true
                 * memberShareUserId : 844001
                 * createDate : 2016/03/18 09:41:29
                 */


                private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity> comments;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }

                public String getMemberName() {
                    if (Util.isEmpty(memberName)) {
                        memberName = "匿名" + memberId;
                    }
                    return memberName;
                }

                public void setMemberName(String memberName) {
                    this.memberName = memberName;
                }

                public String getMemberHead() {
                    return memberHead;
                }

                public void setMemberHead(String memberHead) {
                    this.memberHead = memberHead;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
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

                public int getCommentCount() {
                    return commentCount;
                }

                public void setCommentCount(int commentCount) {
                    this.commentCount = commentCount;
                }

                public List<String> getPics() {
                    return pics;
                }

                public void setPics(List<String> pics) {
                    this.pics = pics;
                }

                public List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity> getComments() {
                    return comments;
                }

                public void setComments(List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity> comments) {
                    this.comments = comments;
                }

                public static class CommentsEntity implements Identifiable {
                    private long id;
                    private long memberId;
                    private String headPic;
                    private String nickName;
                    private int sourceId;
                    private String content;
                    private int memberShareUserId;
                    private String createDate;
                    private long toMemberId;
                    private String toMemberPic;
                    private String toNickName;
                    private long toCommentId;
                    private String toCommentContent;

                    public  String getToCommentContent(){
                        return toCommentContent;
                    }

                    public long getId() {
                        return id;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }

                    public long getMemberId() {
                        return memberId;
                    }

                    public void setMemberId(long memberId) {
                        this.memberId = memberId;
                    }

                    public String getHeadPic() {
                        return headPic;
                    }

                    public void setHeadPic(String headPic) {
                        this.headPic = headPic;
                    }

                    public String getNickName() {
                        return nickName;
                    }

                    public void setNickName(String nickName) {
                        this.nickName = nickName;
                    }

                    public int getSourceId() {
                        return sourceId;
                    }

                    public void setSourceId(int sourceId) {
                        this.sourceId = sourceId;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public int getMemberShareUserId() {
                        return memberShareUserId;
                    }

                    public void setMemberShareUserId(int memberShareUserId) {
                        this.memberShareUserId = memberShareUserId;
                    }

                    public String getCreateDate() {
                        return createDate;
                    }

                    public void setCreateDate(String createDate) {
                        this.createDate = createDate;
                    }

                    public long getToMemberId() {
                        return toMemberId;
                    }

                    public void setToMemberId(long toMemberId) {
                        this.toMemberId = toMemberId;
                    }

                    public String getToMemberPic() {
                        return toMemberPic;
                    }

                    public void setToMemberPic(String toMemberPic) {
                        this.toMemberPic = toMemberPic;
                    }

                    public String getToNickName() {
                        return toNickName;
                    }

                    public void setToNickName(String toNickName) {
                        this.toNickName = toNickName;
                    }

                    public long getToCommentId() {
                        return toCommentId;
                    }

                    public void setToCommentId(long toCommentId) {
                        this.toCommentId = toCommentId;
                    }
                }
            }
        }
    }

}
