package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 * Description : PartnerInviteBean
 */

public class PartnerInviteBean extends BaseBean {

    /**
     * data : {"invites":{"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"toLoginCode":"8488484","toMemberId":3003933,"toPartnerId":101,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0","toNickname":"🐠","createDate":"2018/01/20 18:32:50"},{"toLoginCode":"15233666566","toMemberId":3003932,"toPartnerId":100,"toHeadPic":"http://wx.qlogo.cn/mmopen/vi_32/9F93Nb3vVX4Yr1NU1TKwX7Y853J7UEv2p3EBn4V4UZmD5vMQe1Bfxo0AfxUahCRaHEAeWasumXn2gsUtNS1GEw/0","toNickname":"娟娟","createDate":"2018/01/20 18:28:40"}]}}
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
         * invites : {"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"toLoginCode":"8488484","toMemberId":3003933,"toPartnerId":101,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0","toNickname":"🐠","createDate":"2018/01/20 18:32:50"},{"toLoginCode":"15233666566","toMemberId":3003932,"toPartnerId":100,"toHeadPic":"http://wx.qlogo.cn/mmopen/vi_32/9F93Nb3vVX4Yr1NU1TKwX7Y853J7UEv2p3EBn4V4UZmD5vMQe1Bfxo0AfxUahCRaHEAeWasumXn2gsUtNS1GEw/0","toNickname":"娟娟","createDate":"2018/01/20 18:28:40"}]}
         */

        private InvitesBean invites;

        public InvitesBean getInvites() {
            return invites;
        }

        public void setInvites(InvitesBean invites) {
            this.invites = invites;
        }

        public static class InvitesBean {
            /**
             * next : false
             * total : 2
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"toLoginCode":"8488484","toMemberId":3003933,"toPartnerId":101,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0","toNickname":"🐠","createDate":"2018/01/20 18:32:50"},{"toLoginCode":"15233666566","toMemberId":3003932,"toPartnerId":100,"toHeadPic":"http://wx.qlogo.cn/mmopen/vi_32/9F93Nb3vVX4Yr1NU1TKwX7Y853J7UEv2p3EBn4V4UZmD5vMQe1Bfxo0AfxUahCRaHEAeWasumXn2gsUtNS1GEw/0","toNickname":"娟娟","createDate":"2018/01/20 18:28:40"}]
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
                 * toLoginCode : 8488484
                 * toMemberId : 3003933
                 * toPartnerId : 101
                 * toHeadPic : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0
                 * toNickname : 🐠
                 * createDate : 2018/01/20 18:32:50
                 */

                private String toLoginCode;
                private int toMemberId;
                private int toPartnerId;
                private String toHeadPic;
                private String toNickname;
                private Date createDate;

                public String getToLoginCode() {
                    return toLoginCode;
                }

                public void setToLoginCode(String toLoginCode) {
                    this.toLoginCode = toLoginCode;
                }

                public int getToMemberId() {
                    return toMemberId;
                }

                public void setToMemberId(int toMemberId) {
                    this.toMemberId = toMemberId;
                }

                public int getToPartnerId() {
                    return toPartnerId;
                }

                public void setToPartnerId(int toPartnerId) {
                    this.toPartnerId = toPartnerId;
                }

                public String getToHeadPic() {
                    return toHeadPic;
                }

                public void setToHeadPic(String toHeadPic) {
                    this.toHeadPic = toHeadPic;
                }

                public String getToNickname() {
                    return toNickname;
                }

                public void setToNickname(String toNickname) {
                    this.toNickname = toNickname;
                }

                public Date getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(Date createDate) {
                    this.createDate = createDate;
                }
            }
        }
    }
}
