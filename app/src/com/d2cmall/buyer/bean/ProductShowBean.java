package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class ProductShowBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private MembersharesEntity membershares;

        public void setMembershares(MembersharesEntity membershares) {
            this.membershares = membershares;
        }

        public MembersharesEntity getMembershares() {
            return membershares;
        }

        public static class MembersharesEntity {
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

            public static class ListEntity implements Identifiable {
                private int id;
                private long memberId;
                private String memberName;
                private String memberHead;
                private int watchCount;
                private String createDate;
                private String description;
                private String url;
                private int productId;
                private int designerId;
                private int likeMeCount;
                private int commentCount;
                private List<String> pics;
                private int liked;
                private int role;
                private String video;
                private int timeLength;
                private int follow;
                private String topicName;
                private long topicId;

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

                public long getTopicId() {
                    return topicId;
                }

                public void setTopicId(long topicId) {
                    this.topicId = topicId;
                }

                public int getWatchCount() {
                    return watchCount;
                }

                public void setWatchCount(int watchCount) {
                    this.watchCount = watchCount;
                }

                public int getRole() {
                    return role;
                }

                public void setRole(int role) {
                    this.role = role;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }

                public void setMemberName(String memberName) {
                    this.memberName = memberName;
                }

                public void setMemberHead(String memberHead) {
                    this.memberHead = memberHead;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public void setLikeMeCount(int likeMeCount) {
                    this.likeMeCount = likeMeCount;
                }

                public void setCommentCount(int commentCount) {
                    this.commentCount = commentCount;
                }

                public void setPics(List<String> pics) {
                    this.pics = pics;
                }

                public long getId() {
                    return id;
                }

                public long getMemberId() {
                    return memberId;
                }

                public String getMemberName() {
                    return memberName;
                }

                public String getMemberHead() {
                    return memberHead;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public String getDescription() {
                    return description;
                }

                public String getUrl() {
                    return url;
                }

                public int getProductId() {
                    return productId;
                }

                public int getDesignerId() {
                    return designerId;
                }

                public int getLikeMeCount() {
                    return likeMeCount;
                }

                public int getCommentCount() {
                    return commentCount;
                }

                public List<String> getPics() {
                    return pics;
                }

                public int getLiked() {
                    return liked;
                }

                public void setLiked(int liked) {
                    this.liked = liked;
                }

                public int getFollow() {
                    return follow;
                }

                public void setFollow(int follow) {
                    this.follow = follow;
                }

                public String getTopicName() {
                    return topicName;
                }

                public void setTopicName(String topicName) {
                    this.topicName = topicName;
                }
            }
        }
    }
}
