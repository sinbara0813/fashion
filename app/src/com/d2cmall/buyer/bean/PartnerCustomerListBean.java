package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 * Description : PartnerCustomerListBean
 */

public class PartnerCustomerListBean extends BaseBean {

    /**
     * data : {"invites":{"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"toLoginCode":"8488484","toMemberId":3003933,"toPartnerId":101,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0","toNickname":"🐠","createDate":"2018/01/20 18:32:50"},{"toLoginCode":"15233666566","toMemberId":3003932,"toPartnerId":100,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/QUXEvjrNgm5fy8gx2GZNTwM1C8FOmlLAU2wRko3ktp9xBbMRyA4X3iaL44f8k7py1BKnUKd14D45hibFVUd3iahJw/0","toNickname":"😃 😃 😃 😃","createDate":"2018/01/20 18:28:40"}]}}
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
         * invites : {"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"toLoginCode":"8488484","toMemberId":3003933,"toPartnerId":101,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0","toNickname":"🐠","createDate":"2018/01/20 18:32:50"},{"toLoginCode":"15233666566","toMemberId":3003932,"toPartnerId":100,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/QUXEvjrNgm5fy8gx2GZNTwM1C8FOmlLAU2wRko3ktp9xBbMRyA4X3iaL44f8k7py1BKnUKd14D45hibFVUd3iahJw/0","toNickname":"😃 😃 😃 😃","createDate":"2018/01/20 18:28:40"}]}
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
             * list : [{"toLoginCode":"8488484","toMemberId":3003933,"toPartnerId":101,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqZOFF6icIHf6fNEz9QvGPLLibGJhT3Bv1RCENEPTqrxMQRmjMfZD6lrKT2rTiaRrnicJIOURw9ibKFqtQ/0","toNickname":"🐠","createDate":"2018/01/20 18:32:50"},{"toLoginCode":"15233666566","toMemberId":3003932,"toPartnerId":100,"toHeadPic":"https://wx.qlogo.cn/mmopen/vi_32/QUXEvjrNgm5fy8gx2GZNTwM1C8FOmlLAU2wRko3ktp9xBbMRyA4X3iaL44f8k7py1BKnUKd14D45hibFVUd3iahJw/0","toNickname":"😃 😃 😃 😃","createDate":"2018/01/20 18:28:40"}]
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
                private long toMemberId;
                private long toPartnerId;
                private String toHeadPic;
                private String toNickname;
                private Date createDate;

                public String getToLoginCode() {
                    return toLoginCode;
                }

                public void setToLoginCode(String toLoginCode) {
                    this.toLoginCode = toLoginCode;
                }

                public long getToMemberId() {
                    return toMemberId;
                }

                public void setToMemberId(long toMemberId) {
                    this.toMemberId = toMemberId;
                }

                public long getToPartnerId() {
                    return toPartnerId;
                }

                public void setToPartnerId(long toPartnerId) {
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
