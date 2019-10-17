package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

public class AddressBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private AddressListBean.DataEntity.AddressesEntity.ListEntity address;

        public void setAddress(AddressListBean.DataEntity.AddressesEntity.ListEntity address) {
            this.address = address;
        }

        public AddressListBean.DataEntity.AddressesEntity.ListEntity getAddress() {
            return address;
        }

    }
}
