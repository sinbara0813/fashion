package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 * Description : AdviserListBean
 * 运营顾问列表
 */

public class AdviserListBean extends BaseBean {

    /**
     * data : {"counselors":[{"weixin":"1","qrCode":"/2018/04/18/072506a789df743c569e2e0be105c5b3598358.png","name":"1","description":"1","id":4,"headPic":"/2018/04/18/072503a789df743c569e2e0be105c5b3598358.png"},{"weixin":"D2CSV003","qrCode":"/2018/04/18/0520495110c6e9fb979a38ff982bbfd896bc45.jpg","name":"运营顾问003","description":"123","id":3,"headPic":"/2018/04/19/000106cf9e7603e2cbbb13e91792cefb99551e.jpg"},{"weixin":"D2CSV002","qrCode":"/2018/04/17/112526749207630215bf70db741b589a8b7393.jpg","name":"运营顾问002","description":"","id":2,"headPic":"/app/a/16/04/21/ae7886bcf0db5e3c355bb99a8b22808b"},{"weixin":"D2CSV001","qrCode":"/2018/04/17/112732749207630215bf70db741b589a8b7393.jpg","name":"运营顾问001","description":"","id":1,"headPic":"/app/a/16/09/18/af9cc1c458b9f764adb96b1ddeb76d5f"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CounselorsBean> counselors;

        public List<CounselorsBean> getCounselors() {
            return counselors;
        }

        public void setCounselors(List<CounselorsBean> counselors) {
            this.counselors = counselors;
        }

        public static class CounselorsBean {
            /**
             * weixin : 1
             * qrCode : /2018/04/18/072506a789df743c569e2e0be105c5b3598358.png
             * name : 1
             * description : 1
             * id : 4
             * headPic : /2018/04/18/072503a789df743c569e2e0be105c5b3598358.png
             */

            private String weixin;
            private String qrCode;
            private String name;
            private String description;
            private int id;
            private String headPic;

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }
        }
    }
}
