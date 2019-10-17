package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;


public class MyPacketBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private AccountEntity account;

        public void setAccount(AccountEntity account) {
            this.account = account;
        }

        public AccountEntity getAccount() {
            return account;
        }

        public static class AccountEntity {
            private int id;
            private String mobile;
            private double totalAmount;
            private double availableAmount;
            private double freezeAmount;
            private boolean setPassword;
            private double redAmount;
            private String redDate;

            public double getRedAmount() {
                return redAmount;
            }

            public void setRedAmount(double redAmount) {
                this.redAmount = redAmount;
            }

            public String getRedDate() {
                return redDate;
            }

            public void setRedDate(String redDate) {
                this.redDate = redDate;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public void setAvailableAmount(double availableAmount) {
                this.availableAmount = availableAmount;
            }

            public void setFreezeAmount(double freezeAmount) {
                this.freezeAmount = freezeAmount;
            }

            public void setSetPassword(boolean setPassword) {
                this.setPassword = setPassword;
            }

            public int getId() {
                return id;
            }

            public String getMobile() {
                return mobile;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public double getAvailableAmount() {
                return availableAmount;
            }

            public double getFreezeAmount() {
                return freezeAmount;
            }

            public boolean isSetPassword() {
                return setPassword;
            }
        }
    }
}
