package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 热搜词
 * Author: Blend
 * Date: 2016/07/01 16:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HotBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 1078
         * keyword : gg
         * number : 0
         * sort : 0
         * system : true
         * type : ALL
         * status : 0
         * url :
         */

        private List<MemberSearchSumListEntity> memberSearchSumList;

        public List<MemberSearchSumListEntity> getMemberSearchSumList() {
            return memberSearchSumList;
        }

        public void setMemberSearchSumList(List<MemberSearchSumListEntity> memberSearchSumList) {
            this.memberSearchSumList = memberSearchSumList;
        }

        public static class MemberSearchSumListEntity implements Identifiable{
            private long id;
            private String keyword;
            private int number;
            private int sort;
            private boolean system;
            private String type;
            private int status;
            private String url;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public boolean isSystem() {
                return system;
            }

            public void setSystem(boolean system) {
                this.system = system;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
