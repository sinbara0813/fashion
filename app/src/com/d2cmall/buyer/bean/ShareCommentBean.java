package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 动态详情评论Bean类
 * Author: Blend
 * Date: 16/5/11 13:58
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ShareCommentBean extends BaseBean {

    /**
     * comments : {"index":1,"pageSize":20,"total":7,"previous":false,"next":false,"list":[{"id":766,"memberId":870010,"headPic":"/app/a/16/05/05/6465a97a9d615c0fe523b0c00b141d34","nickName":"宁宝宝🌺🌺🌺","sourceId":2302,"content":"啦啦啦","verified":true,"memberShareUserId":786306,"createDate":"2016/05/11 13:34:04","toMemberId":52527,"toMemberPic":"/app/a/16/05/03/0f56ea5bdc2740eebde2eb8dbc336a7b","toNickName":"问题儿童","toCommentId":763},{"id":764,"memberId":52527,"headPic":"/app/a/16/05/03/0f56ea5bdc2740eebde2eb8dbc336a7b","nickName":"问题儿童","sourceId":2302,"content":"1144444","verified":true,"memberShareUserId":786306,"createDate":"2016/05/11 11:24:47","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":763,"memberId":52527,"headPic":"/app/a/16/05/03/0f56ea5bdc2740eebde2eb8dbc336a7b","nickName":"问题儿童","sourceId":2302,"content":"哈哈哈","verified":true,"memberShareUserId":786306,"createDate":"2016/05/11 11:24:40","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":760,"memberId":48813,"headPic":"http://q.qlogo.cn/qqapp/101265483/BB5A4EE23FCB923FB3BB4CB28C0D30D1/100","nickName":"佛祖","sourceId":2302,"content":"ice 与中国","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 16:38:14","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":758,"memberId":786306,"headPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","nickName":"D2C","sourceId":2302,"content":"哈哈","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 15:05:20","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":757,"memberId":786306,"headPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","nickName":"D2C","sourceId":2302,"content":"哈哈","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 15:05:14","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":749,"memberId":870065,"headPic":"/app/a/16/05/10/b451173b46ac0e9078814c5fc44ede90","nickName":"测试2号","sourceId":2302,"content":"🌺🌺🌺🌺","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 10:10:20","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0}]}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 20
         * total : 7
         * previous : false
         * next : false
         * list : [{"id":766,"memberId":870010,"headPic":"/app/a/16/05/05/6465a97a9d615c0fe523b0c00b141d34","nickName":"宁宝宝🌺🌺🌺","sourceId":2302,"content":"啦啦啦","verified":true,"memberShareUserId":786306,"createDate":"2016/05/11 13:34:04","toMemberId":52527,"toMemberPic":"/app/a/16/05/03/0f56ea5bdc2740eebde2eb8dbc336a7b","toNickName":"问题儿童","toCommentId":763},{"id":764,"memberId":52527,"headPic":"/app/a/16/05/03/0f56ea5bdc2740eebde2eb8dbc336a7b","nickName":"问题儿童","sourceId":2302,"content":"1144444","verified":true,"memberShareUserId":786306,"createDate":"2016/05/11 11:24:47","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":763,"memberId":52527,"headPic":"/app/a/16/05/03/0f56ea5bdc2740eebde2eb8dbc336a7b","nickName":"问题儿童","sourceId":2302,"content":"哈哈哈","verified":true,"memberShareUserId":786306,"createDate":"2016/05/11 11:24:40","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":760,"memberId":48813,"headPic":"http://q.qlogo.cn/qqapp/101265483/BB5A4EE23FCB923FB3BB4CB28C0D30D1/100","nickName":"佛祖","sourceId":2302,"content":"ice 与中国","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 16:38:14","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":758,"memberId":786306,"headPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","nickName":"D2C","sourceId":2302,"content":"哈哈","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 15:05:20","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":757,"memberId":786306,"headPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","nickName":"D2C","sourceId":2302,"content":"哈哈","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 15:05:14","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0},{"id":749,"memberId":870065,"headPic":"/app/a/16/05/10/b451173b46ac0e9078814c5fc44ede90","nickName":"测试2号","sourceId":2302,"content":"🌺🌺🌺🌺","verified":true,"memberShareUserId":786306,"createDate":"2016/05/10 10:10:20","toMemberId":786306,"toMemberPic":"/app/a/16/05/05/d7cb34ed07f3ce3d350df92a62d8ab45","toNickName":"D2C","toCommentId":0}]
         */

        private CommentsEntity comments;

        public CommentsEntity getComments() {
            return comments;
        }

        public void setComments(CommentsEntity comments) {
            this.comments = comments;
        }

        public static class CommentsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;

            private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity> list;

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

            public List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity> getList() {
                return list;
            }

            public void setList(List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity> list) {
                this.list = list;
            }

        }
    }
}
