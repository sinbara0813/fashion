package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/4/26.
 * Description : MonthTaxBean
 */

public class MonthTaxBean extends BaseBean {

    /**
     * data : {"monthWithdraw":0,"taxAmount":0}
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
         * monthWithdraw : 0.0
         * taxAmount : 0
         */

        private double monthWithdraw;
        private double taxAmount;

        public double getMonthWithdraw() {
            return monthWithdraw;
        }

        public void setMonthWithdraw(double monthWithdraw) {
            this.monthWithdraw = monthWithdraw;
        }

        public double getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
        }
    }
}
