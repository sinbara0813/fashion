package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

import java.util.Date;
import java.util.List;

public class ShareBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private MemberSharesEntity memberShares;

        public MemberSharesEntity getMemberShares() {
            return memberShares;
        }

        public void setMemberShares(MemberSharesEntity memberShares) {
            this.memberShares = memberShares;
        }

        public static class MemberSharesEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;

            private List<ListEntity> list;

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

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
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
                private int watchCount;
                private List<String> pics;
                private long productId;
                private int designerId;
                private long topicId;
                private Date verifyDate;
                private int shareCount;//真实分享数
                private int downCount;//真实下载数

                public int getShareCount() {
                    return shareCount;
                }

                public void setShareCount(int shareCount) {
                    this.shareCount = shareCount;
                }

                public int getDownCount() {
                    return downCount;
                }

                public void setDownCount(int downCount) {
                    this.downCount = downCount;
                }

                public Date getVerifyDate() {
                    return verifyDate;
                }

                public void setVerifyDate(Date verifyDate) {
                    this.verifyDate = verifyDate;
                }

                public long getTopicId() {
                    return topicId;
                }

                public void setTopicId(long topicId) {
                    this.topicId = topicId;
                }

                private int liked;
                private int role;
                private int type;
                private int follow;
                private String video;
                private int timeLength;
                private String topicName;
                private List<LikesBean> likes;
                private List<ProductRelationsBean> productRelations;

                public List<ProductRelationsBean> getProductRelations() {
                    return productRelations;
                }

                public void setProductRelations(List<ProductRelationsBean> productRelations) {
                    this.productRelations = productRelations;
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

                public static class ProductRelationsBean {

                    private int designerId;
                    private String img;
                    private int comments;
                    private double originalPrice;
                    private boolean isSpot;
                    private double promotionPrice;
                    private int store;
                    private String designer;
                    private int isCart;
                    private String categoryName;
                    private int consults;
                    private boolean isCrowd;
                    private double price;
                    private double minPrice;
                    private String name;
                    private int recomScore;
                    private int collectioned;
                    private boolean isTopical;
                    private String productSellType;
                    private int id;
                    private int mark;
                    private List<ProductRelationsBean.ColorsBean> colors;
                    private List<ProductRelationsBean.SizesBean> sizes;

                    public int getDesignerId() {
                        return designerId;
                    }

                    public void setDesignerId(int designerId) {
                        this.designerId = designerId;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public int getComments() {
                        return comments;
                    }

                    public void setComments(int comments) {
                        this.comments = comments;
                    }

                    public double getOriginalPrice() {
                        return originalPrice;
                    }

                    public void setOriginalPrice(double originalPrice) {
                        this.originalPrice = originalPrice;
                    }

                    public boolean isIsSpot() {
                        return isSpot;
                    }

                    public void setIsSpot(boolean isSpot) {
                        this.isSpot = isSpot;
                    }

                    public double getPromotionPrice() {
                        return promotionPrice;
                    }

                    public void setPromotionPrice(int promotionPrice) {
                        this.promotionPrice = promotionPrice;
                    }

                    public int getStore() {
                        return store;
                    }

                    public void setStore(int store) {
                        this.store = store;
                    }

                    public String getDesigner() {
                        return designer;
                    }

                    public void setDesigner(String designer) {
                        this.designer = designer;
                    }

                    public int getIsCart() {
                        return isCart;
                    }

                    public void setIsCart(int isCart) {
                        this.isCart = isCart;
                    }

                    public String getCategoryName() {
                        return categoryName;
                    }

                    public void setCategoryName(String categoryName) {
                        this.categoryName = categoryName;
                    }

                    public int getConsults() {
                        return consults;
                    }

                    public void setConsults(int consults) {
                        this.consults = consults;
                    }

                    public boolean isIsCrowd() {
                        return isCrowd;
                    }

                    public void setIsCrowd(boolean isCrowd) {
                        this.isCrowd = isCrowd;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public double getMinPrice() {
                        return minPrice;
                    }

                    public void setMinPrice(int minPrice) {
                        this.minPrice = minPrice;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getRecomScore() {
                        return recomScore;
                    }

                    public void setRecomScore(int recomScore) {
                        this.recomScore = recomScore;
                    }

                    public int getCollectioned() {
                        return collectioned;
                    }

                    public void setCollectioned(int collectioned) {
                        this.collectioned = collectioned;
                    }

                    public boolean isIsTopical() {
                        return isTopical;
                    }

                    public void setIsTopical(boolean isTopical) {
                        this.isTopical = isTopical;
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

                    public int getMark() {
                        return mark;
                    }

                    public void setMark(int mark) {
                        this.mark = mark;
                    }

                    public List<ProductRelationsBean.ColorsBean> getColors() {
                        return colors;
                    }

                    public void setColors(List<ProductRelationsBean.ColorsBean> colors) {
                        this.colors = colors;
                    }

                    public List<ProductRelationsBean.SizesBean> getSizes() {
                        return sizes;
                    }

                    public void setSizes(List<ProductRelationsBean.SizesBean> sizes) {
                        this.sizes = sizes;
                    }

                    public static class ColorsBean {
                        /**
                         * img : /2017/08/21/05295023d73155280b5d3d2bffa9d6190502d6.jpg
                         * code : 0
                         * groupId : 0
                         * name : 颜色
                         * id : 1
                         * value : 黑白
                         */

                        private String img;
                        private String code;
                        private int groupId;
                        private String name;
                        private int id;
                        private String value;

                        public String getImg() {
                            return img;
                        }

                        public void setImg(String img) {
                            this.img = img;
                        }

                        public String getCode() {
                            return code;
                        }

                        public void setCode(String code) {
                            this.code = code;
                        }

                        public int getGroupId() {
                            return groupId;
                        }

                        public void setGroupId(int groupId) {
                            this.groupId = groupId;
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

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }
                    }

                    public static class SizesBean {
                        /**
                         * img :
                         * code : 0
                         * groupId : 0
                         * name : 尺码
                         * id : 1
                         * value : S
                         */

                        private String img;
                        private String code;
                        private int groupId;
                        private String name;
                        private int id;
                        private String value;

                        public String getImg() {
                            return img;
                        }

                        public void setImg(String img) {
                            this.img = img;
                        }

                        public String getCode() {
                            return code;
                        }

                        public void setCode(String code) {
                            this.code = code;
                        }

                        public int getGroupId() {
                            return groupId;
                        }

                        public void setGroupId(int groupId) {
                            this.groupId = groupId;
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

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }
                    }
                }

                public List<LikesBean> getLikes() {
                    return likes;
                }

                public void setLikes(List<LikesBean> likes) {
                    this.likes = likes;
                }

                public static class LikesBean {
                    /**
                     * sharePic : /app/f/17/09/12/71d949da3243530fa72b6870a09ebcde
                     * nickName : 我不知道哦
                     * shareId : 11510
                     * shareName : 误会吗
                     * headPic : /app/a/17/08/30/dc899fa703d96437754c378272e4af67
                     * memberId : 2865965
                     */

                    private String sharePic;
                    private String nickName;
                    private int shareId;
                    private String shareName;
                    private String headPic;
                    private int memberId;

                    public String getSharePic() {
                        return sharePic;
                    }

                    public void setSharePic(String sharePic) {
                        this.sharePic = sharePic;
                    }

                    public String getNickName() {
                        return nickName;
                    }

                    public void setNickName(String nickName) {
                        this.nickName = nickName;
                    }

                    public int getShareId() {
                        return shareId;
                    }

                    public void setShareId(int shareId) {
                        this.shareId = shareId;
                    }

                    public String getShareName() {
                        return shareName;
                    }

                    public void setShareName(String shareName) {
                        this.shareName = shareName;
                    }

                    public String getHeadPic() {
                        return headPic;
                    }

                    public void setHeadPic(String headPic) {
                        this.headPic = headPic;
                    }

                    public int getMemberId() {
                        return memberId;
                    }

                    public void setMemberId(int memberId) {
                        this.memberId = memberId;
                    }
                }

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

                private List<PicTagBean> picTag;

                public List<PicTagBean> getPicTag() {
                    return picTag;
                }

                public void setPicTag(List<PicTagBean> picTag) {
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


                private List<CommentsEntity> comments;

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

                public List<CommentsEntity> getComments() {
                    return comments;
                }

                public void setComments(List<CommentsEntity> comments) {
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
