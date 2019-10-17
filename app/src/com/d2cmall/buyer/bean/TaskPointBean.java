package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 * Description : TaskPointBean
 */

public class TaskPointBean extends BaseBean {

    /**
     * message :
     * data : null
     * datas : {}
     * list : [{"id":12,"createDate":1539236361000,"creator":"梁君","modifyDate":1540779133000,"lastModifyMan":"梁君","code":"PRODUCT_COMMENT","codeType":"PRODUCT_COMMENT","subCode":null,"name":"评价商品成功","subName":"+500D币","type":"COMMON_TASK","point":500,"max":1,"url":null,"exec":false,"fixed":true,"sort":2,"doAfter":"INTEGRATION","startTime":null,"endTime":null}]
     * total : 1
     * index : 1
     * pageCount : 1
     * pageSize : 1
     * previous : false
     * next : false
     */

    private String message;
    private Object data;
    private DatasBean datas;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
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
         * id : 12
         * createDate : 1539236361000
         * creator : 梁君
         * modifyDate : 1540779133000
         * lastModifyMan : 梁君
         * code : PRODUCT_COMMENT
         * codeType : PRODUCT_COMMENT
         * subCode : null
         * name : 评价商品成功
         * subName : +500D币
         * type : COMMON_TASK
         * point : 500
         * max : 1
         * url : null
         * exec : false
         * fixed : true
         * sort : 2
         * doAfter : INTEGRATION
         * startTime : null
         * endTime : null
         */

        private int point;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }


    }
}
