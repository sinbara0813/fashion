package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/10/12 14:51
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ProductReportBean extends BaseBean {

    /**
     * data : {"report":{"next":false,"total":2,"previous":false,"index":1,"pageSize":10,"list":[{"productId":156042,"statusName":"待审核","submitDate":1505975399000,"productImg":null,"verifyReason":null,"pic":[],"content":"abc","productName":"ssvsf","status":1,"memberId":2884369},{"productId":156041,"statusName":"待审核","submitDate":1505975361000,"productImg":null,"verifyReason":null,"pic":[],"content":"abc","productName":"zzz","status":1,"memberId":2884369}]}}
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
         * report : {"next":false,"total":2,"previous":false,"index":1,"pageSize":10,"list":[{"productId":156042,"statusName":"待审核","submitDate":1505975399000,"productImg":null,"verifyReason":null,"pic":[],"content":"abc","productName":"ssvsf","status":1,"memberId":2884369},{"productId":156041,"statusName":"待审核","submitDate":1505975361000,"productImg":null,"verifyReason":null,"pic":[],"content":"abc","productName":"zzz","status":1,"memberId":2884369}]}
         */

        private ReportBean report;

        public ReportBean getReport() {
            return report;
        }

        public void setReport(ReportBean report) {
            this.report = report;
        }

        public static class ReportBean {
            /**
             * next : false
             * total : 2
             * previous : false
             * index : 1
             * pageSize : 10
             * list : [{"productId":156042,"statusName":"待审核","submitDate":1505975399000,"productImg":null,"verifyReason":null,"pic":[],"content":"abc","productName":"ssvsf","status":1,"memberId":2884369},{"productId":156041,"statusName":"待审核","submitDate":1505975361000,"productImg":null,"verifyReason":null,"pic":[],"content":"abc","productName":"zzz","status":1,"memberId":2884369}]
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

            public static class ListBean implements Serializable {
                /**
                 * productId : 156042
                 * statusName : 待审核
                 * submitDate : 1505975399000
                 * productImg : null
                 * verifyReason : null
                 * pic : []
                 * content : abc
                 * productName : ssvsf
                 * status : 1
                 * memberId : 2884369
                 */

                private int productId;
                private String statusName;
                private Date submitDate;
                private String productImg;
                private String verifyReason;
                private String content;
                private String productName;
                @SerializedName("status")
                private int statusX;
                private int memberId;
                private List<String> pic;
                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                private int id;
                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public Date getSubmitDate() {
                    return submitDate;
                }

                public void setSubmitDate(Date submitDate) {
                    this.submitDate = submitDate;
                }

                public String getProductImg() {
                    return productImg;
                }

                public void setProductImg(String productImg) {
                    this.productImg = productImg;
                }

                public String getVerifyReason() {
                    return verifyReason;
                }

                public void setVerifyReason(String verifyReason) {
                    this.verifyReason = verifyReason;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public int getStatusX() {
                    return statusX;
                }

                public void setStatusX(int statusX) {
                    this.statusX = statusX;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public List<String> getPic() {
                    return pic;
                }

                public void setPic(List<String> pic) {
                    this.pic = pic;
                }
            }
        }
    }
}
