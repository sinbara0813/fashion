package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:  消息列表
 * Date: 2017/09/08 13:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageListBean extends BaseBean {


    /**
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
         */

        private MessagesBean messages;
        private int unreadCount;

        public MessagesBean getMessages() {
            return messages;
        }

        public void setMessages(MessagesBean messages) {
            this.messages = messages;
        }

        public int getUnreadCount() {
            return unreadCount;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }

        public static class MessagesBean {
            /**
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<ListBean> list;

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {

                private OtherBean other;
                private String typeName;
                private int global;
                private String pic;
                private int type;
                private String title;
                private String content;
                private String url;
                private int id;
                private int recId;
                private String createDate;
                @SerializedName("status")
                private int statusX;
                private long timestamp;

                public OtherBean getOther() {
                    return other;
                }

                public void setOther(OtherBean other) {
                    this.other = other;
                }

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

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public static class OtherBean {
                    /**
                     * sharePic : /app/f/17/09/18/99f7b21a4dc69039784401efab06a02d
                     * nickName : 我不知道哦
                     * shareId : 11558
                     * headPic : /app/a/17/09/18/0e89f564552858878f6daea1bfd87a16
                     * memberId : 2865965
                     */

                    private String sharePic;
                    private String nickName;
                    private int shareId;
                    private String headPic;
                    private int memberId;

                    public int getSuccess() {
                        return success;
                    }

                    public void setSuccess(int success) {
                        this.success = success;
                    }

                    private int  success;
                    public String getActive() {
                        return active;
                    }

                    public void setActive(String active) {
                        this.active = active;
                    }

                    private String active;
                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    private String content;

                    public int getMoney() {
                        return money;
                    }

                    public void setMoney(int money) {
                        this.money = money;
                    }

                    private int money;
                    public String getSharePic() {
                        return sharePic;
                    }

                    public void setSharePic(String sharePic) {
                        this.sharePic = sharePic;
                    }

                    public String getNickName() {
                        return nickName;
                    }

                    public void setNickName(String nickName) {
                        this.nickName = nickName;
                    }

                    public int getShareId() {
                        return shareId;
                    }

                    public void setShareId(int shareId) {
                        this.shareId = shareId;
                    }

                    public String getHeadPic() {
                        return headPic;
                    }

                    public void setHeadPic(String headPic) {
                        this.headPic = headPic;
                    }

                    public int getMemberId() {
                        return memberId;
                    }

                    public void setMemberId(int memberId) {
                        this.memberId = memberId;
                    }
                }
            }
        }
    }
}
