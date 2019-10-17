package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class SelectCouponBean extends BaseBean {

    /**
     * data : {"skuIds":[351509,351487,263273]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<Integer> skuIds;

        public List<Integer> getSkuIds() {
            return skuIds;
        }

        public void setSkuIds(List<Integer> skuIds) {
            this.skuIds = skuIds;
        }
    }
}
