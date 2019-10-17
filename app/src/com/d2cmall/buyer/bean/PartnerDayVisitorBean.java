package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 * Description : PartnerDayVisitorBean
 */

public class PartnerDayVisitorBean extends BaseBean {

    /**
     * message :
     * datas : {}
     * list : [{"event":"买手店-访问","pv":0,"uv":12,"todayPv":0,"todayUv":1,"lastDayPv":0,"lastDayUv":1,"pvTable":null,"uvTable":[{"key":"2018-01-27","value":6},{"key":"2018-01-28","value":1},{"key":"2018-01-29","value":1}],"visitors":null}]
     * total : 1
     * index : 1
     * pageCount : 1
     * pageSize : 1
     * next : false
     */

    private String message;
    private DatasBean datas;
    private int total;
    private int index;
    private int pageCount;
    private int pageSize;
    private boolean next;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public static class DatasBean {
    }

    public static class ListBean {
        /**
         * event : 买手店-访问
         * pv : 0
         * uv : 12
         * todayPv : 0
         * todayUv : 1
         * lastDayPv : 0
         * lastDayUv : 1
         * pvTable : null
         * uvTable : [{"key":"2018-01-27","value":6},{"key":"2018-01-28","value":1},{"key":"2018-01-29","value":1}]
         * visitors : null
         */

        private String event;
        private int pv;
        private int uv;
        private int todayPv;
        private int todayUv;
        private int lastDayPv;
        private int lastDayUv;
        private Object pvTable;
        private Object visitors;
        private List<UvTableBean> uvTable;

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getUv() {
            return uv;
        }

        public void setUv(int uv) {
            this.uv = uv;
        }

        public int getTodayPv() {
            return todayPv;
        }

        public void setTodayPv(int todayPv) {
            this.todayPv = todayPv;
        }

        public int getTodayUv() {
            return todayUv;
        }

        public void setTodayUv(int todayUv) {
            this.todayUv = todayUv;
        }

        public int getLastDayPv() {
            return lastDayPv;
        }

        public void setLastDayPv(int lastDayPv) {
            this.lastDayPv = lastDayPv;
        }

        public int getLastDayUv() {
            return lastDayUv;
        }

        public void setLastDayUv(int lastDayUv) {
            this.lastDayUv = lastDayUv;
        }

        public Object getPvTable() {
            return pvTable;
        }

        public void setPvTable(Object pvTable) {
            this.pvTable = pvTable;
        }

        public Object getVisitors() {
            return visitors;
        }

        public void setVisitors(Object visitors) {
            this.visitors = visitors;
        }

        public List<UvTableBean> getUvTable() {
            return uvTable;
        }

        public void setUvTable(List<UvTableBean> uvTable) {
            this.uvTable = uvTable;
        }

        public static class UvTableBean {
            /**
             * key : 2018-01-27
             * value : 6
             */

            private String key;
            private int value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
