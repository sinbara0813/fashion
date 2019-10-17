package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 申请分销商
 * Author: Blend
 * Date: 2016/07/21 17:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ApplyDestributionBean extends BaseBean {

    /**
     * partner : {"id":96,"mobile":"990004","sex":"女"}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 96
         * mobile : 990004
         * sex : 女
         */

        private PartnerEntity partner;

        public PartnerEntity getPartner() {
            return partner;
        }

        public void setPartner(PartnerEntity partner) {
            this.partner = partner;
        }

        public static class PartnerEntity {
            private long id;
            private String mobile;
            private String sex;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }
    }
}
