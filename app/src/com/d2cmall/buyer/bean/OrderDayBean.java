package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/17 14:36
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class OrderDayBean extends BaseBean {

    /**
     * message :
     * datas : {}
     * list : [{"gmtModified":null,"partnerId":4145,"status":1,"level":0,"loginCode":"990*****001","name":"匿名_3031125","headPic":null,"memberId":3031125,"parentId":null,"storeId":4142,"masterId":null,"createDate":1523936012000,"expiredDate":null,"consumeDate":1523941261000,"inviteBuyer":1,"inviteDM":1,"inviteNum":2,"payAmount":7370,"payCount":3,"payRebates":509.6,"upperRebates":0,"saleStat":{"partnerId":4145,"count":3,"afterCount":0,"payAmount":7370,"partnerRebates":509.6,"parentRebates":185.1,"superRebates":147.4,"masterRebates":147.4},"saleStatDM":{"partnerId":4145,"count":0,"afterCount":0,"payAmount":0,"partnerRebates":0,"parentRebates":0,"superRebates":0,"masterRebates":0},"saleStatSDM":{"partnerId":4145,"count":0,"afterCount":0,"payAmount":0,"partnerRebates":0,"parentRebates":0,"superRebates":0,"masterRebates":0},"saleStatAM":{"partnerId":4145,"count":9,"afterCount":0,"payAmount":46337,"partnerRebates":3673,"parentRebates":1226.15,"superRebates":926.74,"masterRebates":926.74},"udid":"4145_2018-04-17","startTime":1523894400000,"endTime":1523980799000,"day":"2018-04-17"}]
     * total : 1
     * index : 1
     * pageCount : 1
     * pageSize : 1
     * previous : false
     * next : false
     */

    private String message;
    private DatasBean datas;
    private int total;
    private int index;
    private int pageCount;
    private int pageSize;
    private boolean previous;
    private boolean next;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class DatasBean {
    }

    public static class ListBean {
        /**
         * gmtModified : null
         * partnerId : 4145
         * status : 1
         * level : 0
         * loginCode : 990*****001
         * name : 匿名_3031125
         * headPic : null
         * memberId : 3031125
         * parentId : null
         * storeId : 4142
         * masterId : null
         * createDate : 1523936012000
         * expiredDate : null
         * consumeDate : 1523941261000
         * inviteBuyer : 1
         * inviteDM : 1
         * inviteNum : 2
         * payAmount : 7370.0
         * payCount : 3
         * payRebates : 509.6
         * upperRebates : 0.0
         * saleStat : {"partnerId":4145,"count":3,"afterCount":0,"payAmount":7370,"partnerRebates":509.6,"parentRebates":185.1,"superRebates":147.4,"masterRebates":147.4}
         * saleStatDM : {"partnerId":4145,"count":0,"afterCount":0,"payAmount":0,"partnerRebates":0,"parentRebates":0,"superRebates":0,"masterRebates":0}
         * saleStatSDM : {"partnerId":4145,"count":0,"afterCount":0,"payAmount":0,"partnerRebates":0,"parentRebates":0,"superRebates":0,"masterRebates":0}
         * saleStatAM : {"partnerId":4145,"count":9,"afterCount":0,"payAmount":46337,"partnerRebates":3673,"parentRebates":1226.15,"superRebates":926.74,"masterRebates":926.74}
         * udid : 4145_2018-04-17
         * startTime : 1523894400000
         * endTime : 1523980799000
         * day : 2018-04-17
         */

        private String gmtModified;
        private int partnerId;
        @SerializedName("status")
        private int statusX;
        private int level;
        private String loginCode;
        private String name;
        private String headPic;
        private int memberId;
        private String parentId;
        private int storeId;
        private String masterId;
        private String createDate;
        private String expiredDate;
        private String consumeDate;
        private int inviteBuyer;
        private int inviteDM;
        private int inviteNum;
        private double payAmount;
        private int payCount;
        private double payRebates;
        private double upperRebates;
        private SaleStatBean saleStat;
        private SaleStatBean saleStatDM;
        private SaleStatBean saleStatSDM;
        private SaleStatBean saleStatAM;
        private String udid;
        private String startTime;
        private String endTime;
        private String day;

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(int partnerId) {
            this.partnerId = partnerId;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLoginCode() {
            return loginCode;
        }

        public void setLoginCode(String loginCode) {
            this.loginCode = loginCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getMasterId() {
            return masterId;
        }

        public void setMasterId(String masterId) {
            this.masterId = masterId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }

        public String getConsumeDate() {
            return consumeDate;
        }

        public void setConsumeDate(String consumeDate) {
            this.consumeDate = consumeDate;
        }

        public int getInviteBuyer() {
            return inviteBuyer;
        }

        public void setInviteBuyer(int inviteBuyer) {
            this.inviteBuyer = inviteBuyer;
        }

        public int getInviteDM() {
            return inviteDM;
        }

        public void setInviteDM(int inviteDM) {
            this.inviteDM = inviteDM;
        }

        public int getInviteNum() {
            return inviteNum;
        }

        public void setInviteNum(int inviteNum) {
            this.inviteNum = inviteNum;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public int getPayCount() {
            return payCount;
        }

        public void setPayCount(int payCount) {
            this.payCount = payCount;
        }

        public double getPayRebates() {
            return payRebates;
        }

        public void setPayRebates(double payRebates) {
            this.payRebates = payRebates;
        }

        public double getUpperRebates() {
            return upperRebates;
        }

        public void setUpperRebates(double upperRebates) {
            this.upperRebates = upperRebates;
        }

        public SaleStatBean getSaleStat() {
            return saleStat;
        }

        public void setSaleStat(SaleStatBean saleStat) {
            this.saleStat = saleStat;
        }

        public SaleStatBean getSaleStatDM() {
            return saleStatDM;
        }

        public void setSaleStatDM(SaleStatBean saleStatDM) {
            this.saleStatDM = saleStatDM;
        }

        public SaleStatBean getSaleStatSDM() {
            return saleStatSDM;
        }

        public void setSaleStatSDM(SaleStatBean saleStatSDM) {
            this.saleStatSDM = saleStatSDM;
        }

        public SaleStatBean getSaleStatAM() {
            return saleStatAM;
        }

        public void setSaleStatAM(SaleStatBean saleStatAM) {
            this.saleStatAM = saleStatAM;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public static class SaleStatBean {
            /**
             * partnerId : 4145
             * count : 3
             * afterCount : 0
             * payAmount : 7370.0
             * partnerRebates : 509.6
             * parentRebates : 185.1
             * superRebates : 147.4
             * masterRebates : 147.4
             */

            private int partnerId;
            private int count;
            private int afterCount;
            private double payAmount;
            private double partnerRebates;
            private double parentRebates;
            private double superRebates;
            private double masterRebates;

            public int getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(int partnerId) {
                this.partnerId = partnerId;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(int afterCount) {
                this.afterCount = afterCount;
            }

            public double getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(double payAmount) {
                this.payAmount = payAmount;
            }

            public double getPartnerRebates() {
                return partnerRebates;
            }

            public void setPartnerRebates(double partnerRebates) {
                this.partnerRebates = partnerRebates;
            }

            public double getParentRebates() {
                return parentRebates;
            }

            public void setParentRebates(double parentRebates) {
                this.parentRebates = parentRebates;
            }

            public double getSuperRebates() {
                return superRebates;
            }

            public void setSuperRebates(double superRebates) {
                this.superRebates = superRebates;
            }

            public double getMasterRebates() {
                return masterRebates;
            }

            public void setMasterRebates(double masterRebates) {
                this.masterRebates = masterRebates;
            }
        }

    }
}
