package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/15.
 */

public class ScreenBrandBean extends BaseBean {

    /**
     * data : {"pager":{"pageNumber":1,"pageSize":40,"totalCount":10,"pageCount":1,"list":[{"name":"ILOVEPRETTY","id":10254},{"name":"Marie Elie","id":10603},{"name":"MICARTSY","id":10046},{"name":"Annakiki","id":10037},{"name":"Awaylee","id":10061},{"name":"GENANX","id":10614},{"name":"ZIZTAR","id":10644},{"name":"ZHANGSHUAI","id":10053},{"name":"KATY HUANG STYLE","id":10472},{"name":"VIISHOW","id":11062}],"startRow":null,"orderByStr":null,"forward":false,"next":false}}
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
         * pager : {"pageNumber":1,"pageSize":40,"totalCount":10,"pageCount":1,"list":[{"name":"ILOVEPRETTY","id":10254},{"name":"Marie Elie","id":10603},{"name":"MICARTSY","id":10046},{"name":"Annakiki","id":10037},{"name":"Awaylee","id":10061},{"name":"GENANX","id":10614},{"name":"ZIZTAR","id":10644},{"name":"ZHANGSHUAI","id":10053},{"name":"KATY HUANG STYLE","id":10472},{"name":"VIISHOW","id":11062}],"startRow":null,"orderByStr":null,"forward":false,"next":false}
         */

        private PagerBean pager;

        public PagerBean getPager() {
            return pager;
        }

        public void setPager(PagerBean pager) {
            this.pager = pager;
        }

        public static class PagerBean {
            /**
             * pageNumber : 1
             * pageSize : 40
             * totalCount : 10
             * pageCount : 1
             * list : [{"name":"ILOVEPRETTY","id":10254},{"name":"Marie Elie","id":10603},{"name":"MICARTSY","id":10046},{"name":"Annakiki","id":10037},{"name":"Awaylee","id":10061},{"name":"GENANX","id":10614},{"name":"ZIZTAR","id":10644},{"name":"ZHANGSHUAI","id":10053},{"name":"KATY HUANG STYLE","id":10472},{"name":"VIISHOW","id":11062}]
             * startRow : null
             * orderByStr : null
             * forward : false
             * next : false
             */

            private int pageNumber;
            private int pageSize;
            private int totalCount;
            private int pageCount;
            private String startRow;
            private String orderByStr;
            private boolean forward;
            private boolean next;
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

            public boolean isForward() {
                return forward;
            }

            public void setForward(boolean forward) {
                this.forward = forward;
            }

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * name : ILOVEPRETTY
                 * id : 10254
                 */

                private String name;
                private int id;
                public boolean isSelected;

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
