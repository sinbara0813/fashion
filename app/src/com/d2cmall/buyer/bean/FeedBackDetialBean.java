package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/27.
 * Description : FeedBackDetialBean消息反馈详情bean
 */

public class FeedBackDetialBean extends BaseBean {

    /**
     * data : {"feedBack":{"nickName":"PHP是世界上最好的语言","id":1234,"pic":null,"reply":"顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大的凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞嘎嘎嘎灌灌灌灌灌嘎嘎嘎嘎嘎嘎嘎","headPic":null,"content":"回复内容多写点。。。。。。"}}
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
         * feedBack : {"nickName":"PHP是世界上最好的语言","id":1234,"pic":null,"reply":"顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大的凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞嘎嘎嘎灌灌灌灌灌嘎嘎嘎嘎嘎嘎嘎","headPic":null,"content":"回复内容多写点。。。。。。"}
         */

        private FeedBackBean feedBack;

        public FeedBackBean getFeedBack() {
            return feedBack;
        }

        public void setFeedBack(FeedBackBean feedBack) {
            this.feedBack = feedBack;
        }

        public static class FeedBackBean {
            /**
             * nickName : PHP是世界上最好的语言
             * id : 1234
             * pic : null
             * reply : 顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大的凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞飞凤飞飞凤飞飞凤飞飞嘎嘎嘎灌灌灌灌灌嘎嘎嘎嘎嘎嘎嘎
             * headPic : null
             * content : 回复内容多写点。。。。。。
             */

            private String nickName;
            private int id;
            private ArrayList<String> pic;
            private String reply;
            private String headPic;
            private String content;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public ArrayList<String> getPic() {
                return pic;
            }

            public void setPic(ArrayList<String> pic) {
                this.pic = pic;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
