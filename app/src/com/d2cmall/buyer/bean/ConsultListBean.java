package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class ConsultListBean extends BaseBean {

    /**
     * consults : {"index":1,"pageSize":10,"total":1,"previous":false,"next":false,"list":[{"id":23,"question":"测试","reply":"测试","nickName":"宁宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝","createDate":"2016/05/06 17:25:43","replyDate":"2016/05/06 17:26:05","headPic":"/app/a/16/05/27/71e3a2873acbda7b2080406585ec5655"}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 10
         * total : 1
         * previous : false
         * next : false
         * list : [{"id":23,"question":"测试","reply":"测试","nickName":"宁宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝","createDate":"2016/05/06 17:25:43","replyDate":"2016/05/06 17:26:05","headPic":"/app/a/16/05/27/71e3a2873acbda7b2080406585ec5655"}]
         */

        private ConsultsEntity consults;

        public void setConsults(ConsultsEntity consults) {
            this.consults = consults;
        }

        public ConsultsEntity getConsults() {
            return consults;
        }

        public static class ConsultsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 23
             * question : 测试
             * reply : 测试
             * nickName : 宁宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝宝
             * createDate : 2016/05/06 17:25:43
             * replyDate : 2016/05/06 17:26:05
             * headPic : /app/a/16/05/27/71e3a2873acbda7b2080406585ec5655
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
                private String question;
                private String reply;
                private String nickName;
                private String createDate;
                private String replyDate;
                private String headPic;

                public void setId(int id) {
                    this.id = id;
                }

                public void setQuestion(String question) {
                    this.question = question;
                }

                public void setReply(String reply) {
                    this.reply = reply;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public void setReplyDate(String replyDate) {
                    this.replyDate = replyDate;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public long getId() {
                    return id;
                }

                public String getQuestion() {
                    return question;
                }

                public String getReply() {
                    return reply;
                }

                public String getNickName() {
                    return nickName;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public String getReplyDate() {
                    return replyDate;
                }

                public String getHeadPic() {
                    return headPic;
                }
            }
        }
    }
}
