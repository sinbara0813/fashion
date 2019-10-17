package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/10/23.
 */

public class SerieBean extends BaseBean {

    /**
     * data : {"series":{"pageNumber":1,"pageSize":40,"totalCount":10,"pageCount":1,"list":[{"name":"2017新品","id":2503},{"name":"2016秋冬系列","id":1974},{"name":"2017春夏系列","id":2542},{"name":"\u201c生枝璀璨\u201d系列","id":1287},{"name":"2015秋冬系列","id":879},{"name":"2016春夏","id":1652},{"name":"2016秋冬","id":2275},{"name":"2016 四季系列","id":1754},{"name":"2017SS","id":2711},{"name":"2016  summer","id":1846}],"startRow":null,"orderByStr":null,"next":false,"forward":false}}
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
         * series : {"pageNumber":1,"pageSize":40,"totalCount":10,"pageCount":1,"list":[{"name":"2017新品","id":2503},{"name":"2016秋冬系列","id":1974},{"name":"2017春夏系列","id":2542},{"name":"\u201c生枝璀璨\u201d系列","id":1287},{"name":"2015秋冬系列","id":879},{"name":"2016春夏","id":1652},{"name":"2016秋冬","id":2275},{"name":"2016 四季系列","id":1754},{"name":"2017SS","id":2711},{"name":"2016  summer","id":1846}],"startRow":null,"orderByStr":null,"next":false,"forward":false}
         */

        private SeriesBean series;

        public SeriesBean getSeries() {
            return series;
        }

        public void setSeries(SeriesBean series) {
            this.series = series;
        }

        public static class SeriesBean {
            /**
             * pageNumber : 1
             * pageSize : 40
             * totalCount : 10
             * pageCount : 1
             * list : [{"name":"2017新品","id":2503},{"name":"2016秋冬系列","id":1974},{"name":"2017春夏系列","id":2542},{"name":"\u201c生枝璀璨\u201d系列","id":1287},{"name":"2015秋冬系列","id":879},{"name":"2016春夏","id":1652},{"name":"2016秋冬","id":2275},{"name":"2016 四季系列","id":1754},{"name":"2017SS","id":2711},{"name":"2016  summer","id":1846}]
             * startRow : null
             * orderByStr : null
             * next : false
             * forward : false
             */

            private int pageNumber;
            private int pageSize;
            private int totalCount;
            private int pageCount;
            private String startRow;
            private String orderByStr;
            private boolean next;
            private boolean forward;
            private List<ListBean> list;

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public String getStartRow() {
                return startRow;
            }

            public void setStartRow(String startRow) {
                this.startRow = startRow;
            }

            public String getOrderByStr() {
                return orderByStr;
            }

            public void setOrderByStr(String orderByStr) {
                this.orderByStr = orderByStr;
            }

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public boolean isForward() {
                return forward;
            }

            public void setForward(boolean forward) {
                this.forward = forward;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * name : 2017新品
                 * id : 2503
                 */

                private String name;
                private int id;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}
