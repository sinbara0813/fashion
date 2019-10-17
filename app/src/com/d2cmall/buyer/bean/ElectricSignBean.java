package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/4/25.
 * Description : ElectricSignBean
 */

public class ElectricSignBean extends BaseBean {

    /**
     * data : {"url":"https%3A%2F%2Fcontract-qa.gongmall.com%2Furl_contract.html%3FcompanyId%3D1z94bM%26data%3DdiZ9a9iQ0NVnZUhx3DbjrofeANvPlM1NS6GipCYu7WsYMitVjA2Veu00qiV5m7vsPcW8cPWGx4iP6XvCrGuCIS9NrJIm1YpksMfvY7fNAIeS7v2Rt14vpcGMdlDksB%2Bf3lkMNf5BEg7E0uH5gVlvfD2cVHCy3l%2BL60iEuF1dF1Qe0JNmIVxnin99i%2BqWjhIqRQ4xJXoZ8Em6MbQhTXl9zQ%3D%3D"}
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
         * url : https%3A%2F%2Fcontract-qa.gongmall.com%2Furl_contract.html%3FcompanyId%3D1z94bM%26data%3DdiZ9a9iQ0NVnZUhx3DbjrofeANvPlM1NS6GipCYu7WsYMitVjA2Veu00qiV5m7vsPcW8cPWGx4iP6XvCrGuCIS9NrJIm1YpksMfvY7fNAIeS7v2Rt14vpcGMdlDksB%2Bf3lkMNf5BEg7E0uH5gVlvfD2cVHCy3l%2BL60iEuF1dF1Qe0JNmIVxnin99i%2BqWjhIqRQ4xJXoZ8Em6MbQhTXl9zQ%3D%3D
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
