package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2018/4/17.
 */

public class MonthSummaryBean extends BaseBean {

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


        private long gmtModified;
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
        private long createDate;
        private long expiredDate;
        private long consumeDate;
        private int inviteBuyer;
        private int inviteDM;
        private int inviteNum;
        private double payAmount;
        private int payCount;
        private double payRebates;
        private double upperRebates;
        private SaleStatBean saleStat;
        private SaleStatDMBean saleStatDM;
        private SaleStatSDMBean saleStatSDM;
        private SaleStatAMBean saleStatAM;
        private String udid;
        private long startTime;
        private long endTime;
        private String month;
        public boolean isFirst;



        public long getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(long gmtModified) {
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

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public long getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(long expiredDate) {
            this.expiredDate = expiredDate;
        }

        public long getConsumeDate() {
            return consumeDate;
        }

        public void setConsumeDate(long consumeDate) {
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

        public SaleStatDMBean getSaleStatDM() {
            return saleStatDM;
        }

        public void setSaleStatDM(SaleStatDMBean saleStatDM) {
            this.saleStatDM = saleStatDM;
        }

        public SaleStatSDMBean getSaleStatSDM() {
            return saleStatSDM;
        }

        public void setSaleStatSDM(SaleStatSDMBean saleStatSDM) {
            this.saleStatSDM = saleStatSDM;
        }

        public SaleStatAMBean getSaleStatAM() {
            return saleStatAM;
        }

        public void setSaleStatAM(SaleStatAMBean saleStatAM) {
            this.saleStatAM = saleStatAM;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public static class SaleStatBean {
            /**
             * partnerId : 1
             * count : 0
             * afterCount : 0
             * payAmount : 0.0
             * partnerRebates : 0.0
             * parentRebates : 0.0
             * superRebates : 0.0
             * masterRebates : 0.0
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

        public static class SaleStatDMBean {
            /**
             * partnerId : 1
             * count : 0
             * afterCount : 0
             * payAmount : 0.0
             * partnerRebates : 0.0
             * parentRebates : 0.0
             * superRebates : 0.0
             * masterRebates : 0.0
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

        public static class SaleStatSDMBean {
            /**
             * partnerId : 1
             * count : 0
             * afterCount : 0
             * payAmount : 0.0
             * partnerRebates : 0.0
             * parentRebates : 0.0
             * superRebates : 0.0
             * masterRebates : 0.0
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

        public static class SaleStatAMBean {
            /**
             * partnerId : 1
             * count : 0
             * afterCount : 0
             * payAmount : 0.0
             * partnerRebates : 0.0
             * parentRebates : 0.0
             * superRebates : 0.0
             * masterRebates : 0.0
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
