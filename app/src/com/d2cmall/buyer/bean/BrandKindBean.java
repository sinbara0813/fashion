package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/8/2.
 */

public class BrandKindBean extends BaseBean implements Serializable{

    /**
     * data : {"datas":[{"id":83,"createDate":1482829315000,"creator":"zhengqiang","modifyDate":1482831873000,"lastModifyMan":"zhengqiang","code":"1","name":"生活家居","type":"designArea","orderList":6,"pic":"/2016/12/27/09442523f7eec74f85d52176957efac7fbed43.jpg","status":1},{"id":34,"createDate":1504614411820,"creator":null,"modifyDate":1459437091000,"lastModifyMan":null,"code":"11","name":"鞋包","type":"designArea","orderList":5,"pic":"/model/1603/fdc9a1c26b484e7d11a4c47da29a6029","status":1},{"id":33,"createDate":1504614411820,"creator":null,"modifyDate":1499651309000,"lastModifyMan":"孙雅茹","code":"10","name":"饰品","type":"designArea","orderList":4,"pic":"/model/1603/139ed3356a2566a223a0cf876d7944e2","status":1},{"id":9,"createDate":1343211354000,"creator":null,"modifyDate":1459437103000,"lastModifyMan":null,"code":"9","name":"童装","type":"designArea","orderList":3,"pic":"/model/1603/e2d077f3d2bc91730c30052f59092b89","status":1},{"id":8,"createDate":1343211375000,"creator":null,"modifyDate":1459437110000,"lastModifyMan":null,"code":"8","name":"男装","type":"designArea","orderList":2,"pic":"/model/1603/64ec84098a37c7d63f81e2352c776f0b","status":1},{"id":7,"createDate":1343211372000,"creator":null,"modifyDate":1459437116000,"lastModifyMan":null,"code":"7","name":"女装","type":"designArea","orderList":1,"pic":"/model/1603/cf1f062a244d788eab2bdd6f79f131ab","status":1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<DatasBean> datas;

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Serializable{
            /**
             * id : 83
             * createDate : 1482829315000
             * creator : zhengqiang
             * modifyDate : 1482831873000
             * lastModifyMan : zhengqiang
             * code : 1
             * name : 生活家居
             * type : designArea
             * orderList : 6
             * pic : /2016/12/27/09442523f7eec74f85d52176957efac7fbed43.jpg
             * status : 1
             */

            private int id;
            private long createDate;
            private String creator;
            private long modifyDate;
            private String lastModifyMan;
            private String code;
            private String name;
            private String type;
            private int orderList;
            private String pic;
            @SerializedName("status")
            private int statusX;

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

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
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

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }
        }
    }
}
