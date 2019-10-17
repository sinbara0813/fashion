package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 * Description : PartnerGiftsBean
 */

public class PartnerGiftsBean extends BaseBean {

    /**
     * data : {"gifts":{"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"orderSn":"Q15379502368868360","inviteId":1209,"productPic":"/2018/08/10/111521d46ed662b6ec481ce9c22d3eb6e16165.jpg","loginCode":"158*****624","inviteRebate":150,"productName":"测试下单一分钱","createDate":"2018/09/26 16:19:05"}]}}
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
         * gifts : {"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"orderSn":"Q15379502368868360","inviteId":1209,"productPic":"/2018/08/10/111521d46ed662b6ec481ce9c22d3eb6e16165.jpg","loginCode":"158*****624","inviteRebate":150,"productName":"测试下单一分钱","createDate":"2018/09/26 16:19:05"}]}
         */

        private GiftsBean gifts;

        public GiftsBean getGifts() {
            return gifts;
        }

        public void setGifts(GiftsBean gifts) {
            this.gifts = gifts;
        }

        public static class GiftsBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"orderSn":"Q15379502368868360","inviteId":1209,"productPic":"/2018/08/10/111521d46ed662b6ec481ce9c22d3eb6e16165.jpg","loginCode":"158*****624","inviteRebate":150,"productName":"测试下单一分钱","createDate":"2018/09/26 16:19:05"}]
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
                 * orderSn : Q15379502368868360
                 * inviteId : 1209
                 * productPic : /2018/08/10/111521d46ed662b6ec481ce9c22d3eb6e16165.jpg
                 * loginCode : 158*****624
                 * inviteRebate : 150
                 * productName : 测试下单一分钱
                 * createDate : 2018/09/26 16:19:05
                 */

                private String orderSn;
                private int inviteId;
                private String productPic;
                private String loginCode;
                private double inviteRebate;
                private String productName;
                private Date createDate;
                private long productId;

                public long getProductId() {
                    return productId;
                }

                public void setProductId(long productId) {
                    this.productId = productId;
                }
                public String getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(String orderSn) {
                    this.orderSn = orderSn;
                }

                public int getInviteId() {
                    return inviteId;
                }

                public void setInviteId(int inviteId) {
                    this.inviteId = inviteId;
                }

                public String getProductPic() {
                    return productPic;
                }

                public void setProductPic(String productPic) {
                    this.productPic = productPic;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public double getInviteRebate() {
                    return inviteRebate;
                }

                public void setInviteRebate(double inviteRebate) {
                    this.inviteRebate = inviteRebate;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
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
