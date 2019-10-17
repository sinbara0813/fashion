package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 * Description : PartnerMemberListBean
 */

public class PartnerMemberListBean extends BaseBean {

    /**
     * data : {"children":{"next":false,"total":3,"previous":false,"index":1,"pageSize":20,"list":[{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"安然😍😍😍","loginCode":"15899999999","cashAmount":0,"id":65,"storeId":45,"headPic":"/app/a/17/09/08/50102cd269bdeaae2b950d59228c6802","parentId":57},{"totalAmount":499.75,"totalOrderAmount":1999,"level":1,"name":"雪🌸","loginCode":"13588373855","cashAmount":120,"id":41,"storeId":40,"headPic":"/app/a/17/02/27/A620072142B607E7779BA7617CA8E773","parentId":57},{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"果CC，","loginCode":"13003633740","cashAmount":0,"id":4,"storeId":3,"headPic":"/app/a/16/04/01/286c79124141dce0d5de9883a476df69","parentId":57}]}}
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
         * children : {"next":false,"total":3,"previous":false,"index":1,"pageSize":20,"list":[{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"安然😍😍😍","loginCode":"15899999999","cashAmount":0,"id":65,"storeId":45,"headPic":"/app/a/17/09/08/50102cd269bdeaae2b950d59228c6802","parentId":57},{"totalAmount":499.75,"totalOrderAmount":1999,"level":1,"name":"雪🌸","loginCode":"13588373855","cashAmount":120,"id":41,"storeId":40,"headPic":"/app/a/17/02/27/A620072142B607E7779BA7617CA8E773","parentId":57},{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"果CC，","loginCode":"13003633740","cashAmount":0,"id":4,"storeId":3,"headPic":"/app/a/16/04/01/286c79124141dce0d5de9883a476df69","parentId":57}]}
         */

        private ChildrenBean children;

        public ChildrenBean getChildren() {
            return children;
        }

        public void setChildren(ChildrenBean children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * next : false
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"安然😍😍😍","loginCode":"15899999999","cashAmount":0,"id":65,"storeId":45,"headPic":"/app/a/17/09/08/50102cd269bdeaae2b950d59228c6802","parentId":57},{"totalAmount":499.75,"totalOrderAmount":1999,"level":1,"name":"雪🌸","loginCode":"13588373855","cashAmount":120,"id":41,"storeId":40,"headPic":"/app/a/17/02/27/A620072142B607E7779BA7617CA8E773","parentId":57},{"totalAmount":0,"totalOrderAmount":0,"level":2,"name":"果CC，","loginCode":"13003633740","cashAmount":0,"id":4,"storeId":3,"headPic":"/app/a/16/04/01/286c79124141dce0d5de9883a476df69","parentId":57}]
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
                 * totalAmount : 0
                 * totalOrderAmount : 0
                 * level : 2
                 * name : 安然😍😍😍
                 * loginCode : 15899999999
                 * cashAmount : 0
                 * id : 65
                 * storeId : 45
                 * headPic : /app/a/17/09/08/50102cd269bdeaae2b950d59228c6802
                 * parentId : 57
                 */

                private double totalAmount;
                private double totalOrderAmount;
                private int level;
                private String name;
                private String loginCode;
                private double cashAmount;
                private long id;
                private int storeId;
                private String headPic;
                private int parentId;

                public double getTotalAmount() {
                    return totalAmount;
                }

                public void setTotalAmount(double totalAmount) {
                    this.totalAmount = totalAmount;
                }

                public double getTotalOrderAmount() {
                    return totalOrderAmount;
                }

                public void setTotalOrderAmount(double totalOrderAmount) {
                    this.totalOrderAmount = totalOrderAmount;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public double getCashAmount() {
                    return cashAmount;
                }

                public void setCashAmount(double cashAmount) {
                    this.cashAmount = cashAmount;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public int getStoreId() {
                    return storeId;
                }

                public void setStoreId(int storeId) {
                    this.storeId = storeId;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }
            }
        }
    }
}
