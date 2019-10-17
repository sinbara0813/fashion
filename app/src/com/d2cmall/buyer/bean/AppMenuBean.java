package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: PengHong
 * Date: 2016/08/26 10:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class AppMenuBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 2
         * createDate : 1472090711000
         * modifyDate : 1472103858000
         * lastModifyMan : wwn
         * creator : wwn
         * name : 测试一下
         * iconUrl : /2016/08/25/0205061405ae82252fb5c13a5261c0ded1ae09.png
         * url : http://baidu.com
         * status : 1
         * sequence : 6
         */

        private List<MenuEntity> menuList;

        public List<MenuEntity> getMenuList() {
            return menuList;
        }

        public void setMenuList(List<MenuEntity> menuList) {
            this.menuList = menuList;
        }

        public static class MenuEntity {
            private int id;
            private long createDate;
            private long modifyDate;
            private String lastModifyMan;
            private String creator;
            private String name;
            private String iconUrl;
            private String url;
            private int status;
            private int sequence;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public long getModifyDate() {
                return modifyDate;
            }

            public void setModifyDate(long modifyDate) {
                this.modifyDate = modifyDate;
            }

            public String getLastModifyMan() {
                return lastModifyMan;
            }

            public void setLastModifyMan(String lastModifyMan) {
                this.lastModifyMan = lastModifyMan;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }
        }
    }
}
