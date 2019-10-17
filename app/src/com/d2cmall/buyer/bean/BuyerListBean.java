package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2018/4/17.
 */

public class BuyerListBean extends BaseBean {


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
        private int statusX;//状态 为0试用期
        private int level;//等级
        private String loginCode;
        private String name;//名字
        private String headPic;//头像
        private int memberId;
        private String parentId;
        private int storeId;
        private String masterId;
        private String createDate;//加入日期
        private String expiredDate;
        private String consumeDate;//最后交易日期
        private int inviteBuyer;//邀请的买手数量
        private int inviteDM;//升级的DM数量
        private int inviteNum;//邀请的总数
        private double payAmount;//销售的钱
        private int payCount;//返利的单的数量
        private double payRebates;
        private double upperRebates;
        private SaleStatBean saleStat;
        private SaleStatDMBean saleStatDM;
        private SaleStatSDMBean saleStatSDM;
        private SaleStatAMBean saleStatAM;
        private int id;
        private double weekPayAmount;
        private int weekPayCount;
        private WeekSaleStatBean weekSaleStat;
        private WeekSaleStatDMBean weekSaleStatDM;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getWeekPayAmount() {
            return weekPayAmount;
        }

        public void setWeekPayAmount(double weekPayAmount) {
            this.weekPayAmount = weekPayAmount;
        }

        public int getWeekPayCount() {
            return weekPayCount;
        }

        public void setWeekPayCount(int weekPayCount) {
            this.weekPayCount = weekPayCount;
        }

        public WeekSaleStatBean getWeekSaleStat() {
            return weekSaleStat;
        }

        public void setWeekSaleStat(WeekSaleStatBean weekSaleStat) {
            this.weekSaleStat = weekSaleStat;
        }

        public WeekSaleStatDMBean getWeekSaleStatDM() {
            return weekSaleStatDM;
        }

        public void setWeekSaleStatDM(WeekSaleStatDMBean weekSaleStatDM) {
            this.weekSaleStatDM = weekSaleStatDM;
        }

        public static class SaleStatBean {
            /**
             * partnerId : 1
             * count : 2
             * afterCount : 0
             * payAmount : 0.12
             * partnerRebates : 0.04
             * parentRebates : 0.002
             * superRebates : 0.0
             * masterRebates : 0.0
             */

            private int partnerId;
            private int count;//返利的单数
            private double afterCount;
            private double payAmount;//返利的钱
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

            public double getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(double afterCount) {
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
            private int count;//团队的返利单数
            private double afterCount;
            private double payAmount;//团队的返利的钱
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

            public double getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(double afterCount) {
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
            private double afterCount;
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

            public double getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(double afterCount) {
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
            private double afterCount;
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

            public double getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(double afterCount) {
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

        public static class WeekSaleStatBean {
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
            private double afterCount;
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

            public double getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(double afterCount) {
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

        public static class WeekSaleStatDMBean {
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
            private double afterCount;
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

            public double getAfterCount() {
                return afterCount;
            }

            public void setAfterCount(double afterCount) {
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
