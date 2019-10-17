package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/6/6.
 * Description : AdviserBean
 */

public class AdviserBean extends BaseBean {

    /**
     * data : {"partnerCounselor":{"weixin":"D2CSV001","qrCode":"/2018/04/17/112732749207630215bf70db741b589a8b7393.jpg","name":"运营顾问001","description":"","id":1,"headPic":"/app/a/16/09/18/af9cc1c458b9f764adb96b1ddeb76d5f"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * partnerCounselor : {"weixin":"D2CSV001","qrCode":"/2018/04/17/112732749207630215bf70db741b589a8b7393.jpg","name":"运营顾问001","description":"","id":1,"headPic":"/app/a/16/09/18/af9cc1c458b9f764adb96b1ddeb76d5f"}
         */

        private PartnerCounselorBean partnerCounselor;

        public PartnerCounselorBean getPartnerCounselor() {
            return partnerCounselor;
        }

        public void setPartnerCounselor(PartnerCounselorBean partnerCounselor) {
            this.partnerCounselor = partnerCounselor;
        }

        public static class PartnerCounselorBean {
            /**
             * weixin : D2CSV001
             * qrCode : /2018/04/17/112732749207630215bf70db741b589a8b7393.jpg
             * name : 运营顾问001
             * description :
             * id : 1
             * headPic : /app/a/16/09/18/af9cc1c458b9f764adb96b1ddeb76d5f
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
