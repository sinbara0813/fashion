package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/12/29.
 * Description : CollageListBean
 */

public class CollageListBean extends BaseBean {

    /**
     * data : {"groupList":{"next":false,"total":6,"previous":false,"index":1,"pageSize":20,"list":[{"currentMemberCount":1,"productId":226828,"endDate":1546122964000,"groupSn":"G1546062964455","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1424,"createDate":"2018/12/29 13:54:43","status":4,"initiatorMemberId":3074306},{"currentMemberCount":1,"productId":226828,"endDate":1546122605000,"groupSn":"G1546062605397","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1423,"createDate":"2018/12/29 13:48:44","status":4,"initiatorMemberId":3074305},{"currentMemberCount":1,"productId":226828,"endDate":1543982932000,"groupSn":"G1546062306939","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1422,"createDate":"2018/12/29 13:43:45","status":4,"initiatorMemberId":3074304},{"currentMemberCount":1,"productId":226828,"endDate":1543982843000,"groupSn":"G1546062218003","memberCount":6,"initiatorName":"咯哈登","headPic":"/app/a/18/12/14/8C621DFEE4E23E0BE32BB5EF72A27924","payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1421,"createDate":"2018/12/29 13:40:03","status":4,"initiatorMemberId":3033476},{"currentMemberCount":1,"productId":226828,"endDate":1543982429000,"groupSn":"G1546061803925","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1419,"createDate":"2018/12/29 13:35:22","status":4,"initiatorMemberId":3074303},{"currentMemberCount":1,"productId":226828,"endDate":1543981942000,"groupSn":"G1546061317047","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1418,"createDate":"2018/12/29 13:27:15","status":4,"initiatorMemberId":3074302}]}}
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
         * groupList : {"next":false,"total":6,"previous":false,"index":1,"pageSize":20,"list":[{"currentMemberCount":1,"productId":226828,"endDate":1546122964000,"groupSn":"G1546062964455","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1424,"createDate":"2018/12/29 13:54:43","status":4,"initiatorMemberId":3074306},{"currentMemberCount":1,"productId":226828,"endDate":1546122605000,"groupSn":"G1546062605397","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1423,"createDate":"2018/12/29 13:48:44","status":4,"initiatorMemberId":3074305},{"currentMemberCount":1,"productId":226828,"endDate":1543982932000,"groupSn":"G1546062306939","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1422,"createDate":"2018/12/29 13:43:45","status":4,"initiatorMemberId":3074304},{"currentMemberCount":1,"productId":226828,"endDate":1543982843000,"groupSn":"G1546062218003","memberCount":6,"initiatorName":"咯哈登","headPic":"/app/a/18/12/14/8C621DFEE4E23E0BE32BB5EF72A27924","payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1421,"createDate":"2018/12/29 13:40:03","status":4,"initiatorMemberId":3033476},{"currentMemberCount":1,"productId":226828,"endDate":1543982429000,"groupSn":"G1546061803925","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1419,"createDate":"2018/12/29 13:35:22","status":4,"initiatorMemberId":3074303},{"currentMemberCount":1,"productId":226828,"endDate":1543981942000,"groupSn":"G1546061317047","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1418,"createDate":"2018/12/29 13:27:15","status":4,"initiatorMemberId":3074302}]}
         */

        private GroupListBean groupList;

        public GroupListBean getGroupList() {
            return groupList;
        }

        public void setGroupList(GroupListBean groupList) {
            this.groupList = groupList;
        }

        public static class GroupListBean {
            /**
             * next : false
             * total : 6
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"currentMemberCount":1,"productId":226828,"endDate":1546122964000,"groupSn":"G1546062964455","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1424,"createDate":"2018/12/29 13:54:43","status":4,"initiatorMemberId":3074306},{"currentMemberCount":1,"productId":226828,"endDate":1546122605000,"groupSn":"G1546062605397","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1423,"createDate":"2018/12/29 13:48:44","status":4,"initiatorMemberId":3074305},{"currentMemberCount":1,"productId":226828,"endDate":1543982932000,"groupSn":"G1546062306939","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1422,"createDate":"2018/12/29 13:43:45","status":4,"initiatorMemberId":3074304},{"currentMemberCount":1,"productId":226828,"endDate":1543982843000,"groupSn":"G1546062218003","memberCount":6,"initiatorName":"咯哈登","headPic":"/app/a/18/12/14/8C621DFEE4E23E0BE32BB5EF72A27924","payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1421,"createDate":"2018/12/29 13:40:03","status":4,"initiatorMemberId":3033476},{"currentMemberCount":1,"productId":226828,"endDate":1543982429000,"groupSn":"G1546061803925","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1419,"createDate":"2018/12/29 13:35:22","status":4,"initiatorMemberId":3074303},{"currentMemberCount":1,"productId":226828,"endDate":1543981942000,"groupSn":"G1546061317047","memberCount":6,"initiatorName":null,"headPic":null,"payMemberCount":1,"promotionId":1667,"statusName":"拼团中","id":1418,"createDate":"2018/12/29 13:27:15","status":4,"initiatorMemberId":3074302}]
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
                 * currentMemberCount : 1
                 * productId : 226828
                 * endDate : 1546122964000
                 * groupSn : G1546062964455
                 * memberCount : 6
                 * initiatorName : null
                 * headPic : null
                 * payMemberCount : 1
                 * promotionId : 1667
                 * statusName : 拼团中
                 * id : 1424
                 * createDate : 2018/12/29 13:54:43
                 * status : 4
                 * initiatorMemberId : 3074306
                 */

                private int currentMemberCount;
                private long productId;
                private Date endDate;
                private String groupSn;
                private int memberCount;
                private String initiatorName;
                private String headPic;
                private int payMemberCount;
                private int promotionId;
                private String statusName;
                private int id;
                private Date createDate;
                @SerializedName("status")
                private int statusX;
                private int initiatorMemberId;

                public int getCurrentMemberCount() {
                    return currentMemberCount;
                }

                public void setCurrentMemberCount(int currentMemberCount) {
                    this.currentMemberCount = currentMemberCount;
                }

                public long getProductId() {
                    return productId;
                }

                public void setProductId(long productId) {
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

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
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
            }
        }
    }
}
