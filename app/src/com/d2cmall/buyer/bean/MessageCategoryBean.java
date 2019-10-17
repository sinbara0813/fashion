package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:    消息Bean对象(消息大类)
 * Date: 2017/09/07 20:33
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageCategoryBean extends BaseBean {


    /**
     * data : {"messages":[{"typeName":"活动精选","global":1,"pic":"/2017/08/29/033128601ddca070efdec8792c3309145c3095.jpg","type":61,"title":"Awaylee 送你个小心心2017-09-08 08:00:17","content":"Awaylee 送你个小心心 2017-09-08 08:00:17","url":"/page/Awayleehuodong","majorType":6,"id":1191512,"unReadCount":258,"recId":0,"createDate":"2017/09/08 08:06:13","status":0,"timestamp":1504829173799},{"typeName":"品牌推荐","global":1,"pic":null,"type":72,"title":"342","content":null,"url":"/product/list?t=6&c=1716","majorType":7,"id":1188724,"unReadCount":17,"recId":0,"createDate":"2017/08/11 14:00:50","status":0,"timestamp":1502431250000},{"typeName":"商品提醒","global":1,"pic":null,"type":2,"title":"货到通知","content":null,"url":"/message/902009","majorType":0,"id":902009,"unReadCount":6,"recId":0,"createDate":"2016/11/03 17:28:23","status":0,"timestamp":1478165303000}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MessagesBean> messages;

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        public static class MessagesBean {
            /**
             * typeName : 活动精选
             * global : 1
             * pic : /2017/08/29/033128601ddca070efdec8792c3309145c3095.jpg
             * type : 61
             * title : Awaylee 送你个小心心2017-09-08 08:00:17
             * content : Awaylee 送你个小心心 2017-09-08 08:00:17
             * url : /page/Awayleehuodong
             * majorType : 6
             * id : 1191512
             * unReadCount : 258
             * recId : 0
             * createDate : 2017/09/08 08:06:13
             * status : 0
             * timestamp : 1504829173799
             */

            private String typeName;
            private int global;
            private String pic;
            private int type;
            private String title;
            private String content;
            private String url;
            private int majorType;
            private int id;
            private int unReadCount;
            private int recId;
            private String createDate;
            @SerializedName("status")
            private int statusX;
            private long timestamp;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public int getGlobal() {
                return global;
            }

            public void setGlobal(int global) {
                this.global = global;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getMajorType() {
                return majorType;
            }

            public void setMajorType(int majorType) {
                this.majorType = majorType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUnReadCount() {
                return unReadCount;
            }

            public void setUnReadCount(int unReadCount) {
                this.unReadCount = unReadCount;
            }

            public int getRecId() {
                return recId;
            }

            public void setRecId(int recId) {
                this.recId = recId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            /* 将字符串转为时间戳 */
            public static long getStringToDate(String time) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
                Date date = new Date();
                try {
                    date = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date.getTime() / 100000;
            }

        }
    }
}
