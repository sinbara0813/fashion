package com.d2cmall.buyer.bean;

/**
 * 推送Bean
 * Author: PengHong
 * Date: 2016/11/21 18:47
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PushBean {
    /**
     * messageType :
     * messageContent : {"msgContent":"","province":"浙江省","minutes":1,"nickName":"大师","designerName":"ZHANGSHUAI"}
     */

    private String messageType;
    /**
     * msgContent :
     * province : 浙江省
     * minutes : 1
     * nickName : 大师
     * designerName : ZHANGSHUAI
     */

    private MessageContent messageContent;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public MessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(MessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public static class MessageContent {
        private String msgContent;
        private String province;
        private int minutes;
        private String nickName;
        private String designerName;
        private String headPic;
        private String url;
        private String title;
        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        private String subTitle;
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private int  type;
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getDesignerName() {
            return designerName;
        }

        public void setDesignerName(String designerName) {
            this.designerName = designerName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
