package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class MessagesBean extends BaseBean {

    /**
     * messages : {"index":1,"pageSize":50,"total":8,"previous":false,"next":false,"list":[{"id":65,"createDate":"2016/05/05 14:29:07","type":2,"typeName":"商品","title":"测试商品消息","content":"测试商品消息","status":1,"recId":845334,"url":"www.baidu.com","global":0},{"id":59,"createDate":"2016/05/05 14:28:49","type":5,"typeName":"评论","title":"测试消息","content":"YE'S 叶谦 不速之约系列 白色一字叠褶裸肩收腰蓬摆轻礼服 搞活动啦","status":1,"recId":845334,"url":"http://www.d2cmall.com/product/124666","global":0},{"id":53,"createDate":"2016/05/05 14:28:43","type":6,"typeName":"其他","title":"测试消息","content":"测试一下消息通道是通的吗？","status":0,"recId":845334,"url":"","global":0},{"id":31,"createDate":"2016/04/19 09:37:41","type":4,"typeName":"系统","title":"测试系统消息","content":"测试系统消息","status":1,"recId":845334,"url":"","global":0},{"id":30,"createDate":"2016/04/19 09:36:53","type":3,"typeName":"订单","title":"测试订单消息","content":"测试订单消息","status":1,"recId":845334,"url":"","global":0},{"id":29,"createDate":"2016/04/19 09:36:49","type":2,"typeName":"商品","title":"测试商品消息","content":"测试商品消息","status":1,"recId":845334,"url":"","global":0},{"id":28,"createDate":"2016/04/19 09:36:43","type":1,"typeName":"物流","title":"测试物流消息","content":"测试物流消息","status":1,"recId":845334,"url":"","global":0},{"id":27,"createDate":"2016/04/19 09:33:32","type":6,"typeName":"其他","title":"测试消息","content":"测试一下消息通道是通的吗？","status":1,"recId":845334,"url":"","global":0}]}
     * unreadCount : 7
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private MessagesEntity messages;
        private int unreadCount;

        public void setMessages(MessagesEntity messages) {
            this.messages = messages;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }

        public MessagesEntity getMessages() {
            return messages;
        }

        public int getUnreadCount() {
            return unreadCount;
        }

        public static class MessagesEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 65
             * createDate : 2016/05/05 14:29:07
             * type : 2
             * typeName : 商品
             * title : 测试商品消息
             * content : 测试商品消息
             * status : 1
             * recId : 845334
             * url : www.baidu.com
             * global : 0
             */

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
                private long id;
                private String createDate;
                private int type;
                private String typeName;
                private String title;
                private String content;
                private int status;
                private int recId;
                private String url;
                private int global;

                public void setId(int id) {
                    this.id = id;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public void setRecId(int recId) {
                    this.recId = recId;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setGlobal(int global) {
                    this.global = global;
                }

                public long getId() {
                    return id;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public int getType() {
                    return type;
                }

                public String getTypeName() {
                    return typeName;
                }

                public String getTitle() {
                    return title;
                }

                public String getContent() {
                    return content;
                }

                public int getStatus() {
                    return status;
                }

                public int getRecId() {
                    return recId;
                }

                public String getUrl() {
                    return url;
                }

                public int getGlobal() {
                    return global;
                }
            }
        }
    }
}
