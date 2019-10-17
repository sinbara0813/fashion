package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;


public class DesignerKindBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 15
         * createDate : 1352877509000
         * modifyDate : 1459150128000
         * lastModifyMan : null
         * creator : null
         * code : 13
         * name : 高街潮牌
         * type : style
         * orderList : 5
         * pic : /model/1603/6bf5581a3f9c420bb9f5566c78c9db90
         * status : 1
         */

        private List<DatasEntity> datas;

        public List<DatasEntity> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasEntity> datas) {
            this.datas = datas;
        }

        public static class DatasEntity implements Identifiable {
            private long id;
            private long createDate;
            private long modifyDate;
            private Object lastModifyMan;
            private Object creator;
            private String code;
            private String name;
            private String type;
            private int orderList;
            private String pic;
            private int status;

            public long getId() {
                return id;
            }

            public void setId(long id) {
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

            public Object getLastModifyMan() {
                return lastModifyMan;
            }

            public void setLastModifyMan(Object lastModifyMan) {
                this.lastModifyMan = lastModifyMan;
            }

            public Object getCreator() {
                return creator;
            }

            public void setCreator(Object creator) {
                this.creator = creator;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getOrderList() {
                return orderList;
            }

            public void setOrderList(int orderList) {
                this.orderList = orderList;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
