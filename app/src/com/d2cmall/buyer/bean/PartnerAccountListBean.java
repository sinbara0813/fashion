package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 * Description : PartnerAccountListBean
 */

public class PartnerAccountListBean extends BaseBean {

    /**
     * data : {"partnerLog":{"next":false,"total":5,"previous":false,"index":1,"pageSize":20,"list":[{"sourceSn":"PC18011515401710033","sourceStatus":8,"amount":20,"sourceType":"CASH","statusName":"已完成","id":72,"createDate":1515401559000,"direction":-1},{"sourceSn":"PC18011515401465130","sourceStatus":8,"amount":10,"sourceType":"CASH","statusName":"已完成","id":71,"createDate":1515401326000,"direction":-1},{"sourceSn":"PC18011515220400969","sourceStatus":8,"amount":32,"sourceType":"CASH","statusName":"已完成","id":70,"createDate":1515220262000,"direction":-1},{"sourceSn":"PC18011515220334916","sourceStatus":8,"amount":58,"sourceType":"CASH","statusName":"已完成","id":69,"createDate":1515220189000,"direction":-1},{"sourceSn":"PB18011514944966818","sourceStatus":8,"amount":499.75,"sourceType":"BILL","statusName":"已完成","id":68,"createDate":1515034184000,"direction":1}]}}
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
         * partnerLog : {"next":false,"total":5,"previous":false,"index":1,"pageSize":20,"list":[{"sourceSn":"PC18011515401710033","sourceStatus":8,"amount":20,"sourceType":"CASH","statusName":"已完成","id":72,"createDate":1515401559000,"direction":-1},{"sourceSn":"PC18011515401465130","sourceStatus":8,"amount":10,"sourceType":"CASH","statusName":"已完成","id":71,"createDate":1515401326000,"direction":-1},{"sourceSn":"PC18011515220400969","sourceStatus":8,"amount":32,"sourceType":"CASH","statusName":"已完成","id":70,"createDate":1515220262000,"direction":-1},{"sourceSn":"PC18011515220334916","sourceStatus":8,"amount":58,"sourceType":"CASH","statusName":"已完成","id":69,"createDate":1515220189000,"direction":-1},{"sourceSn":"PB18011514944966818","sourceStatus":8,"amount":499.75,"sourceType":"BILL","statusName":"已完成","id":68,"createDate":1515034184000,"direction":1}]}
         */

        private PartnerLogBean partnerLog;

        public PartnerLogBean getPartnerLog() {
            return partnerLog;
        }

        public void setPartnerLog(PartnerLogBean partnerLog) {
            this.partnerLog = partnerLog;
        }

        public static class PartnerLogBean {
            /**
             * next : false
             * total : 5
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"sourceSn":"PC18011515401710033","sourceStatus":8,"amount":20,"sourceType":"CASH","statusName":"已完成","id":72,"createDate":1515401559000,"direction":-1},{"sourceSn":"PC18011515401465130","sourceStatus":8,"amount":10,"sourceType":"CASH","statusName":"已完成","id":71,"createDate":1515401326000,"direction":-1},{"sourceSn":"PC18011515220400969","sourceStatus":8,"amount":32,"sourceType":"CASH","statusName":"已完成","id":70,"createDate":1515220262000,"direction":-1},{"sourceSn":"PC18011515220334916","sourceStatus":8,"amount":58,"sourceType":"CASH","statusName":"已完成","id":69,"createDate":1515220189000,"direction":-1},{"sourceSn":"PB18011514944966818","sourceStatus":8,"amount":499.75,"sourceType":"BILL","statusName":"已完成","id":68,"createDate":1515034184000,"direction":1}]
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
                 * sourceSn : PC18011515401710033
                 * sourceStatus : 8
                 * amount : 20.0
                 * sourceType : CASH
                 * statusName : 已完成
                 * id : 72
                 * createDate : 1515401559000
                 * direction : -1
                 */

                private String sourceSn;
                private int sourceStatus;
                private double amount;
                private String sourceType;
                private String statusName;
                private int id;
                private Date createDate;
                private int direction;

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                private String typeName;
                public String getSourceSn() {
                    return sourceSn;
                }

                public void setSourceSn(String sourceSn) {
                    this.sourceSn = sourceSn;
                }

                public int getSourceStatus() {
                    return sourceStatus;
                }

                public void setSourceStatus(int sourceStatus) {
                    this.sourceStatus = sourceStatus;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public String getSourceType() {
                    return sourceType;
                }

                public void setSourceType(String sourceType) {
                    this.sourceType = sourceType;
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

                public int getDirection() {
                    return direction;
                }

                public void setDirection(int direction) {
                    this.direction = direction;
                }
            }
        }
    }
}
