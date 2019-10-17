package com.d2cmall.buyer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/9/11.
 */

public class SelectListBean implements Serializable {
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> list;

    public List<RelationProductBean.DataBean.ProductsBean.ListBean> getList() {
        return list;
    }

    public void setList(List<RelationProductBean.DataBean.ProductsBean.ListBean> list) {
        this.list = list;
    }
}
