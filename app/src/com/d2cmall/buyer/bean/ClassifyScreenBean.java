package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2018/3/23.
 */

public class ClassifyScreenBean extends BaseBean {
    private String name;
    private List<ClassifyScreenItemBean> list;
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassifyScreenItemBean> getList() {
        return list;
    }

    public void setList(List<ClassifyScreenItemBean> list) {
        this.list = list;
    }

    public static class ClassifyScreenItemBean {
        private String name;
        private boolean isSelect;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
