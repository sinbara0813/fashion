package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/13.
 */

public class HotBrandBean extends BaseBean {

    /**
     * data : {"designers":{"next":false,"total":3,"previous":false,"index":1,"pageSize":40,"list":[{"id":10317,"headPic":"/model/1603/5b3d7660ac3c2fc8fecd223f3b8a8715"},{"id":10617,"headPic":"/model/1605/c0ca409904bdaac5ad33842ced07096b"},{"id":10687,"headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png"}]}}
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
         * designers : {"next":false,"total":3,"previous":false,"index":1,"pageSize":40,"list":[{"id":10317,"headPic":"/model/1603/5b3d7660ac3c2fc8fecd223f3b8a8715"},{"id":10617,"headPic":"/model/1605/c0ca409904bdaac5ad33842ced07096b"},{"id":10687,"headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png"}]}
         */

        private DesignersBean designers;

        public DesignersBean getDesigners() {
            return designers;
        }

        public void setDesigners(DesignersBean designers) {
            this.designers = designers;
        }

        public static class DesignersBean {
            /**
             * next : false
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"id":10317,"headPic":"/model/1603/5b3d7660ac3c2fc8fecd223f3b8a8715"},{"id":10617,"headPic":"/model/1605/c0ca409904bdaac5ad33842ced07096b"},{"id":10687,"headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png"}]
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
                 * id : 10317
                 * headPic : /model/1603/5b3d7660ac3c2fc8fecd223f3b8a8715
                 */

                private long id;
                private String headPic;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }
            }
        }
    }
}
