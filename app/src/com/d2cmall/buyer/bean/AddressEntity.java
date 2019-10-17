package com.d2cmall.buyer.bean;

import java.io.Serializable;

/**
 * 地址实体类
 * Author: YH
 * Date: 2017/07/20 10:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class AddressEntity implements Identifiable ,Serializable{
    public String addressName;
    public String address;
    public boolean isSelected;
    public String cityName;
    public double lat;
    public double lon;

    public AddressEntity(){}
    public AddressEntity(String addressName, boolean isSelected){
        this.addressName=addressName;
        this.isSelected=isSelected;
    }

    @Override
    public long getId() {
        return 0;
    }
}
