package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/18.
 */

public class ShowLikeBean extends BaseBean {

    /**
     * data : {"likes":{"next":false,"total":1,"previous":false,"index":1,"pageSize":40,"list":[{"sharePic":"/app/f/17/06/27/15ADFCB4EC40FD8FF3C392D858BDB55D","nickName":"丫","shareId":10224,"shareName":"没事上来看看👀\n有没有人跟我聊聊天💯\n上新待续\u2026\u2026","follow":0,"headPic":"http://wx.qlogo.cn/mmopen/I2fxKtgmKrTibeb2icsgvkkRbzibWbNUzCFOs3ou3jeiaHiaSulewXS8yNgKqXf37gOydoVO7SIEMFPIZiaGZeLAcNWzbEp9nvoNbC/0","memberId":2689063}]}}
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
         * likes : {"next":false,"total":1,"previous":false,"index":1,"pageSize":40,"list":[{"sharePic":"/app/f/17/06/27/15ADFCB4EC40FD8FF3C392D858BDB55D","nickName":"丫","shareId":10224,"shareName":"没事上来看看👀\n有没有人跟我聊聊天💯\n上新待续\u2026\u2026","follow":0,"headPic":"http://wx.qlogo.cn/mmopen/I2fxKtgmKrTibeb2icsgvkkRbzibWbNUzCFOs3ou3jeiaHiaSulewXS8yNgKqXf37gOydoVO7SIEMFPIZiaGZeLAcNWzbEp9nvoNbC/0","memberId":2689063}]}
         */

        private LikesBean likes;

        public LikesBean getLikes() {
            return likes;
        }

        public void setLikes(LikesBean likes) {
            this.likes = likes;
        }

        public static class LikesBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"sharePic":"/app/f/17/06/27/15ADFCB4EC40FD8FF3C392D858BDB55D","nickName":"丫","shareId":10224,"shareName":"没事上来看看👀\n有没有人跟我聊聊天💯\n上新待续\u2026\u2026","follow":0,"headPic":"http://wx.qlogo.cn/mmopen/I2fxKtgmKrTibeb2icsgvkkRbzibWbNUzCFOs3ou3jeiaHiaSulewXS8yNgKqXf37gOydoVO7SIEMFPIZiaGZeLAcNWzbEp9nvoNbC/0","memberId":2689063}]
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
                /**
                 * sharePic : /app/f/17/06/27/15ADFCB4EC40FD8FF3C392D858BDB55D
                 * nickName : 丫
                 * shareId : 10224
                 * shareName : 没事上来看看👀
                 有没有人跟我聊聊天💯
                 上新待续……
                 * follow : 0
                 * headPic : http://wx.qlogo.cn/mmopen/I2fxKtgmKrTibeb2icsgvkkRbzibWbNUzCFOs3ou3jeiaHiaSulewXS8yNgKqXf37gOydoVO7SIEMFPIZiaGZeLAcNWzbEp9nvoNbC/0
                 * memberId : 2689063
                 */

                private String sharePic;
                private String nickName;
                private long shareId;
                private String shareName;
                private int follow;
                private String headPic;
                private long memberId;

                public String getMemberShare() {
                    return memberShare;
                }

                public void setMemberShare(String memberShare) {
                    this.memberShare = memberShare;
                }

                private String memberShare;
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

                public long getShareId() {
                    return shareId;
                }

                public void setShareId(long shareId) {
                    this.shareId = shareId;
                }

                public String getShareName() {
                    return shareName;
                }

                public void setShareName(String shareName) {
                    this.shareName = shareName;
                }

                public int getFollow() {
                    return follow;
                }

                public void setFollow(int follow) {
                    this.follow = follow;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }
            }
        }
    }
}
