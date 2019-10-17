package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class SmartSearchBean extends BaseBean {
    /**
     * data : {"list":["吴亦凡同款"]}
     * login : true
     * msg :
     * status : 1
     * success : true
     */
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }


    public static class DataEntity {
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

    }
}
