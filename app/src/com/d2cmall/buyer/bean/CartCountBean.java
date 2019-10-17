package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

public class CartCountBean extends BaseBean {

    /**
     * cartItemCount : 0
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int cartItemCount;

        public void setCartItemCount(int cartItemCount) {
            this.cartItemCount = cartItemCount;
        }

        public int getCartItemCount() {
            return cartItemCount;
        }
    }
}
