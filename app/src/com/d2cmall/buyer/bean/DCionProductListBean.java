package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 * Description : DCionProductListBean
 */

public class DCionProductListBean extends BaseBean {

    /**
     * data : {"list":{"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"amount":0,"productId":null,"endDate":1534498863000,"name":"测试","description":"34234","id":2,"introPic":"12","type":"CARD","detailPic":"12","point":2000,"startDate":1533721260000}]}}
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
         * list : {"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"amount":0,"productId":null,"endDate":1534498863000,"name":"测试","description":"34234","id":2,"introPic":"12","type":"CARD","detailPic":"12","point":2000,"startDate":1533721260000}]}
         */

        private ListBeanX list;

        public ListBeanX getList() {
            return list;
        }

        public void setList(ListBeanX list) {
            this.list = list;
        }

        public static class ListBeanX {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"amount":0,"productId":null,"endDate":1534498863000,"name":"测试","description":"34234","id":2,"introPic":"12","type":"CARD","detailPic":"12","point":2000,"startDate":1533721260000}]
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
                 * amount : 0
                 * productId : null
                 * endDate : 1534498863000
                 * name : 测试
                 * description : 34234
                 * id : 2
                 * introPic : 12
                 * type : CARD
                 * detailPic : 12
                 * point : 2000
                 * startDate : 1533721260000
                 */

                private double amount;
                private int productId;
                private Date endDate;
                private String name;
                private String description;
                private int id;
                private String introPic;
                private String type;
                private String detailPic;
                private int point;
                private Date startDate;
                private int count;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public Date getEndDate() {
                    return endDate;
                }

                public void setEndDate(Date endDate) {
                    this.endDate = endDate;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIntroPic() {
                    return introPic;
                }

                public void setIntroPic(String introPic) {
                    this.introPic = introPic;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getDetailPic() {
                    return detailPic;
                }

                public void setDetailPic(String detailPic) {
                    this.detailPic = detailPic;
                }

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public Date getStartDate() {
                    return startDate;
                }

                public void setStartDate(Date startDate) {
                    this.startDate = startDate;
                }
            }
        }
    }
}
