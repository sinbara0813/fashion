package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 * Description : PartnerCashListBean
 */

public class PartnerCashListBean extends BaseBean {

    /**
     * data : {"partnerCash":{"next":false,"total":7,"previous":false,"index":1,"pageSize":20,"list":[{"applyAccount":"111","statusName":"未支付","sn":"PC18011515575584018","paySn":null,"applyName":"111","refuseReason":null,"createDate":1515575584000,"applyAmount":11,"status":1,"payDate":null},{"applyAccount":"13582525444","statusName":"已支付","sn":"PC18011515401710033","paySn":"123123","applyName":"厉害","refuseReason":null,"createDate":1515401710000,"applyAmount":20,"status":8,"payDate":1515401494000},{"applyAccount":"好好看看","statusName":"已支付","sn":"PC18011515401465130","paySn":"222222222222222213","applyName":"厉害","refuseReason":null,"createDate":1515401465000,"applyAmount":10,"status":8,"payDate":1515401256000},{"applyAccount":"回来啦","statusName":"已支付","sn":"PC18011515220400969","paySn":"2342","applyName":"看看看","refuseReason":null,"createDate":1515220401000,"applyAmount":57,"status":8,"payDate":1515220184000},{"applyAccount":"回来啦","statusName":"已支付","sn":"PC18011515220334916","paySn":"234234","applyName":"看看看","refuseReason":null,"createDate":1515220335000,"applyAmount":58,"status":8,"payDate":1515220119000},{"applyAccount":"回来啦","statusName":"已拒绝","sn":"PC18011515220326954","paySn":null,"applyName":"看看看","refuseReason":"大幅度发个","createDate":1515220327000,"applyAmount":23,"status":-1,"payDate":null},{"applyAccount":"回来啦","statusName":"未支付","sn":"PC18011515220317882","paySn":null,"applyName":"看看看","refuseReason":null,"createDate":1515220318000,"applyAmount":20,"status":1,"payDate":null}]}}
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
         * partnerCash : {"next":false,"total":7,"previous":false,"index":1,"pageSize":20,"list":[{"applyAccount":"111","statusName":"未支付","sn":"PC18011515575584018","paySn":null,"applyName":"111","refuseReason":null,"createDate":1515575584000,"applyAmount":11,"status":1,"payDate":null},{"applyAccount":"13582525444","statusName":"已支付","sn":"PC18011515401710033","paySn":"123123","applyName":"厉害","refuseReason":null,"createDate":1515401710000,"applyAmount":20,"status":8,"payDate":1515401494000},{"applyAccount":"好好看看","statusName":"已支付","sn":"PC18011515401465130","paySn":"222222222222222213","applyName":"厉害","refuseReason":null,"createDate":1515401465000,"applyAmount":10,"status":8,"payDate":1515401256000},{"applyAccount":"回来啦","statusName":"已支付","sn":"PC18011515220400969","paySn":"2342","applyName":"看看看","refuseReason":null,"createDate":1515220401000,"applyAmount":57,"status":8,"payDate":1515220184000},{"applyAccount":"回来啦","statusName":"已支付","sn":"PC18011515220334916","paySn":"234234","applyName":"看看看","refuseReason":null,"createDate":1515220335000,"applyAmount":58,"status":8,"payDate":1515220119000},{"applyAccount":"回来啦","statusName":"已拒绝","sn":"PC18011515220326954","paySn":null,"applyName":"看看看","refuseReason":"大幅度发个","createDate":1515220327000,"applyAmount":23,"status":-1,"payDate":null},{"applyAccount":"回来啦","statusName":"未支付","sn":"PC18011515220317882","paySn":null,"applyName":"看看看","refuseReason":null,"createDate":1515220318000,"applyAmount":20,"status":1,"payDate":null}]}
         */

        private PartnerCashBean partnerCash;

        public PartnerCashBean getPartnerCash() {
            return partnerCash;
        }

        public void setPartnerCash(PartnerCashBean partnerCash) {
            this.partnerCash = partnerCash;
        }

        public static class PartnerCashBean {
            /**
             * next : false
             * total : 7
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"applyAccount":"111","statusName":"未支付","sn":"PC18011515575584018","paySn":null,"applyName":"111","refuseReason":null,"createDate":1515575584000,"applyAmount":11,"status":1,"payDate":null},{"applyAccount":"13582525444","statusName":"已支付","sn":"PC18011515401710033","paySn":"123123","applyName":"厉害","refuseReason":null,"createDate":1515401710000,"applyAmount":20,"status":8,"payDate":1515401494000},{"applyAccount":"好好看看","statusName":"已支付","sn":"PC18011515401465130","paySn":"222222222222222213","applyName":"厉害","refuseReason":null,"createDate":1515401465000,"applyAmount":10,"status":8,"payDate":1515401256000},{"applyAccount":"回来啦","statusName":"已支付","sn":"PC18011515220400969","paySn":"2342","applyName":"看看看","refuseReason":null,"createDate":1515220401000,"applyAmount":57,"status":8,"payDate":1515220184000},{"applyAccount":"回来啦","statusName":"已支付","sn":"PC18011515220334916","paySn":"234234","applyName":"看看看","refuseReason":null,"createDate":1515220335000,"applyAmount":58,"status":8,"payDate":1515220119000},{"applyAccount":"回来啦","statusName":"已拒绝","sn":"PC18011515220326954","paySn":null,"applyName":"看看看","refuseReason":"大幅度发个","createDate":1515220327000,"applyAmount":23,"status":-1,"payDate":null},{"applyAccount":"回来啦","statusName":"未支付","sn":"PC18011515220317882","paySn":null,"applyName":"看看看","refuseReason":null,"createDate":1515220318000,"applyAmount":20,"status":1,"payDate":null}]
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
                 * applyAccount : 111
                 * statusName : 未支付
                 * sn : PC18011515575584018
                 * paySn : null
                 * applyName : 111
                 * refuseReason : null
                 * createDate : 1515575584000
                 * applyAmount : 11
                 * status : 1
                 * payDate : null
                 */

                private String applyAccount;
                private String statusName;
                private String sn;
                private String paySn;
                private String payType;
                private String applyName;
                private String refuseReason;
                private Date createDate;
                private double applyAmount;
                public double getApplyTaxAmount() {
                    return applyTaxAmount;
                }

                public void setApplyTaxAmount(double applyTaxAmount) {
                    this.applyTaxAmount = applyTaxAmount;
                }

                private double applyTaxAmount;
                @SerializedName("status")
                private int statusX;
                private Date payDate;

                public String getPayType() {
                    return payType;
                }

                public void setPayType(String payType) {
                    this.payType = payType;
                }
                public String getApplyAccount() {
                    return applyAccount;
                }

                public void setApplyAccount(String applyAccount) {
                    this.applyAccount = applyAccount;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public String getSn() {
                    return sn;
                }

                public void setSn(String sn) {
                    this.sn = sn;
                }

                public String getPaySn() {
                    return paySn;
                }

                public void setPaySn(String paySn) {
                    this.paySn = paySn;
                }

                public String getApplyName() {
                    return applyName;
                }

                public void setApplyName(String applyName) {
                    this.applyName = applyName;
                }

                public String getRefuseReason() {
                    return refuseReason;
                }

                public void setRefuseReason(String refuseReason) {
                    this.refuseReason = refuseReason;
                }

                public Date getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(Date createDate) {
                    this.createDate = createDate;
                }

                public double getApplyAmount() {
                    return applyAmount;
                }

                public void setApplyAmount(double applyAmount) {
                    this.applyAmount = applyAmount;
                }

                public int getStatusX() {
                    return statusX;
                }

                public void setStatusX(int statusX) {
                    this.statusX = statusX;
                }

                public Date getPayDate() {
                    return payDate;
                }

                public void setPayDate(Date payDate) {
                    this.payDate = payDate;
                }
            }
        }
    }
}
