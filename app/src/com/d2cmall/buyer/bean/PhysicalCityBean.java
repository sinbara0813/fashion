package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/10/10.
 */

public class PhysicalCityBean extends BaseBean {

    /**
     * data : {"provinces":["浙江省","云南省","安徽省","重庆市","江苏省","吉林省","北京市","河北省","山东省","辽宁省","广西省","黑龙江省","福建省","湖北省","江西省","新疆省"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> provinces;

        public List<String> getProvinces() {
            return provinces;
        }

        public void setProvinces(List<String> provinces) {
            this.provinces = provinces;
        }
    }
}
