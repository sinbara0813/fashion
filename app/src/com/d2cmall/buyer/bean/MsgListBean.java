package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 直播消息列表
 * Author: hrb
 * Date: 2016/09/02 17:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MsgListBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * memberId : 849778
         * groupId : 1190
         * msgTimestamp : 2016/09/02 10:41:57
         * msgSeq : 1
         * msgType : TIMCustomElem
         * msgContent : {"Desc":"","Data":{"userAction":1},"Ext":""}
         */

        private List<MsgLogBean> msgLog;

        public List<MsgLogBean> getMsgLog() {
            return msgLog;
        }

        public void setMsgLog(List<MsgLogBean> msgLog) {
            this.msgLog = msgLog;
        }

        public static class MsgLogBean {
            private int memberId;
            private int groupId;
            private String msgTimestamp;
            private int msgSeq;
            private String msgType;
            /**
             * Desc :
             * Data : {"userAction":1}
             * Ext :
             */

            private MsgContentBean msgContent;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public String getMsgTimestamp() {
                return msgTimestamp;
            }

            public void setMsgTimestamp(String msgTimestamp) {
                this.msgTimestamp = msgTimestamp;
            }

            public int getMsgSeq() {
                return msgSeq;
            }

            public void setMsgSeq(int msgSeq) {
                this.msgSeq = msgSeq;
            }

            public String getMsgType() {
                return msgType;
            }

            public void setMsgType(String msgType) {
                this.msgType = msgType;
            }

            public MsgContentBean getMsgContent() {
                return msgContent;
            }

            public void setMsgContent(MsgContentBean msgContent) {
                this.msgContent = msgContent;
            }

            public static class MsgContentBean {
                private String Desc;
                /**
                 * userAction : 1
                 */

                private DataMsgBean Data;
                private String Ext;
                private String Text;

                public String getDesc() {
                    return Desc;
                }

                public void setDesc(String Desc) {
                    this.Desc = Desc;
                }

                public DataMsgBean getData() {
                    return Data;
                }

                public void setData(DataMsgBean Data) {
                    this.Data = Data;
                }

                public String getExt() {
                    return Ext;
                }

                public void setExt(String Ext) {
                    this.Ext = Ext;
                }

                public String getText() {
                    return Text;
                }

                public void setText(String text) {
                    Text = text;
                }

                public static class DataMsgBean {
                    private int userAction;

                    public int getUserAction() {
                        return userAction;
                    }

                    public void setUserAction(int userAction) {
                        this.userAction = userAction;
                    }
                }
            }
        }
    }
}
