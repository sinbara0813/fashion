package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 关注头像bean
 * Author: Blend
 * Date: 16/4/20 19:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HeadPicBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private List<String> headPics;

        public List<String> getHeadPics() {
            return headPics;
        }

        public void setHeadPics(List<String> headPics) {
            this.headPics = headPics;
        }
    }
}
