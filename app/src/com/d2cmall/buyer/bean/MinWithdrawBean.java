package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/1/20.
 * Description : 最小提现
 */

public class MinWithdrawBean extends BaseBean {

    /**
     * data : {"minWithdraw":10}
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
         * minWithdraw : 10
         */

        private double minWithdraw;

        public double getMinWithdraw() {
            return minWithdraw;
        }

        public void setMinWithdraw(double minWithdraw) {
            this.minWithdraw = minWithdraw;
        }
    }

}
