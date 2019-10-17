package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

public class UpdateCartBean extends BaseBean {

    /**
     * availableStore : 9
     * quantity : 2
     * promotionPrice : 800.0
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int availableStore;
        private int quantity;
        private double promotionPrice;

        public void setAvailableStore(int availableStore) {
            this.availableStore = availableStore;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public int getAvailableStore() {
            return availableStore;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPromotionPrice() {
            return promotionPrice;
        }
    }
}
