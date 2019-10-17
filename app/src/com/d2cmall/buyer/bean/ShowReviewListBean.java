package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/8/24.
 */

public class ShowReviewListBean extends BaseBean {
    private ShowReviewListBean.DataBean data;

    public ShowReviewListBean.DataBean getData() {
        return data;
    }

    public void setData(ShowReviewListBean.DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * comments : {"next":false,"total":4,"previous":false,"index":1,"pageSize":40,"list":[{"sourceId":5474,"toCommentContent":null,"nickName":"555mnkn","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"迷幻","toCommentId":0,"score":null,"id":5709,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2813844,"createDate":"2017/08/15 10:05:48","likes":0},{"sourceId":5474,"toCommentContent":null,"nickName":"匿名_2858077","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"迷幻？","toCommentId":5709,"score":null,"id":5710,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2858077,"createDate":"2017/08/17 11:29:32","likes":0},{"sourceId":5474,"toCommentContent":null,"nickName":"匿名_2858077","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"可以","toCommentId":0,"score":null,"id":5711,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2858077,"createDate":"2017/08/17 11:29:47","likes":0},{"sourceId":5474,"toCommentContent":null,"nickName":"匿名_2858077","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"？","toCommentId":5709,"score":null,"id":5712,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2858077,"createDate":"2017/08/17 11:33:25","likes":0}]}
         */

        private CommentsBean comments;

        public CommentsBean getComments() {
            return comments;
        }

        public void setComments(CommentsBean comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            /**
             * next : false
             * total : 4
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"sourceId":5474,"toCommentContent":null,"nickName":"555mnkn","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"迷幻","toCommentId":0,"score":null,"id":5709,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2813844,"createDate":"2017/08/15 10:05:48","likes":0},{"sourceId":5474,"toCommentContent":null,"nickName":"匿名_2858077","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"迷幻？","toCommentId":5709,"score":null,"id":5710,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2858077,"createDate":"2017/08/17 11:29:32","likes":0},{"sourceId":5474,"toCommentContent":null,"nickName":"匿名_2858077","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"可以","toCommentId":0,"score":null,"id":5711,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2858077,"createDate":"2017/08/17 11:29:47","likes":0},{"sourceId":5474,"toCommentContent":null,"nickName":"匿名_2858077","verified":true,"toMemberId":2813844,"title":null,"hot":1,"headPic":null,"content":"？","toCommentId":5709,"score":null,"id":5712,"toNickName":"555mnkn","memberShareUserId":2813844,"toMemberPic":null,"memberId":2858077,"createDate":"2017/08/17 11:33:25","likes":0}]
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
                 * sourceId : 5474
                 * toCommentContent : null
                 * nickName : 555mnkn
                 * verified : true
                 * toMemberId : 2813844
                 * title : null
                 * hot : 1
                 * headPic : null
                 * content : 迷幻
                 * toCommentId : 0
                 * score : null
                 * id : 5709
                 * toNickName : 555mnkn
                 * memberShareUserId : 2813844
                 * toMemberPic : null
                 * memberId : 2813844
                 * createDate : 2017/08/15 10:05:48
                 * likes : 0
                 */

                private int sourceId;
                private String toCommentContent;
                private String nickName;
                private boolean verified;
                private int toMemberId;
                private String title;
                private int hot;
                private String headPic;
                private String content;
                private int toCommentId;
                private String score;
                private int id;
                private String toNickName;
                private int memberShareUserId;
                private String toMemberPic;
                private int memberId;
                private String createDate;
                private int likes;

                public int getSourceId() {
                    return sourceId;
                }

                public void setSourceId(int sourceId) {
                    this.sourceId = sourceId;
                }

                public String getToCommentContent() {
                    return toCommentContent;
                }

                public void setToCommentContent(String toCommentContent) {
                    this.toCommentContent = toCommentContent;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public boolean isVerified() {
                    return verified;
                }

                public void setVerified(boolean verified) {
                    this.verified = verified;
                }

                public int getToMemberId() {
                    return toMemberId;
                }

                public void setToMemberId(int toMemberId) {
                    this.toMemberId = toMemberId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getHot() {
                    return hot;
                }

                public void setHot(int hot) {
                    this.hot = hot;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getToCommentId() {
                    return toCommentId;
                }

                public void setToCommentId(int toCommentId) {
                    this.toCommentId = toCommentId;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getToNickName() {
                    return toNickName;
                }

                public void setToNickName(String toNickName) {
                    this.toNickName = toNickName;
                }

                public int getMemberShareUserId() {
                    return memberShareUserId;
                }

                public void setMemberShareUserId(int memberShareUserId) {
                    this.memberShareUserId = memberShareUserId;
                }

                public String getToMemberPic() {
                    return toMemberPic;
                }

                public void setToMemberPic(String toMemberPic) {
                    this.toMemberPic = toMemberPic;
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

                public int getLikes() {
                    return likes;
                }

                public void setLikes(int likes) {
                    this.likes = likes;
                }
            }
        }
    }
}
