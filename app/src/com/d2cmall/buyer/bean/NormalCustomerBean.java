package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 * Description : NormalCustomerBean
 */

public class NormalCustomerBean extends BaseBean {

    /**
     * message :
     * datas : {}
     * list : [{"id":2957162,"loginCode":"15669906252#1523267939","loginDate":1523267913000,"loginDevice":"26f354fe0cf13a9","nickname":"丑小鸭的梦","headPic":"http://wx.qlogo.cn/mmopen/vi_32/mkQfLCBnlicPZgzcuHEumPhWOdGcCyp3KvQpnHnF0qMwEIw1MFjYbVQxwRNSxmEZdV75S4fEooQqhsUXBSibFNnA/0","sex":"男","consumeDate":1514001187000,"payAmount":0,"payCount":1}]
     * total : 1
     * index : 1
     * pageCount : 1
     * pageSize : 1
     * previous : false
     * next : false
     */

    private String message;
    private DatasBean datas;
    private int total;
    private int index;
    private int pageCount;
    private int pageSize;
    private boolean previous;
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

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
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
         * id : 2957162
         * loginCode : 15669906252#1523267939
         * loginDate : 1523267913000
         * loginDevice : 26f354fe0cf13a9
         * nickname : 丑小鸭的梦
         * headPic : http://wx.qlogo.cn/mmopen/vi_32/mkQfLCBnlicPZgzcuHEumPhWOdGcCyp3KvQpnHnF0qMwEIw1MFjYbVQxwRNSxmEZdV75S4fEooQqhsUXBSibFNnA/0
         * sex : 男
         * consumeDate : 1514001187000
         * payAmount : 0.0
         * payCount : 1
         */

        private int id;
        private String loginCode;
        private Date loginDate;
        private String loginDevice;
        private String nickname;
        private String headPic;
        private String sex;
        private Date consumeDate;
        private double payAmount;
        private int payCount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginCode() {
            return loginCode;
        }

        public void setLoginCode(String loginCode) {
            this.loginCode = loginCode;
        }

        public Date getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(Date loginDate) {
            this.loginDate = loginDate;
        }

        public String getLoginDevice() {
            return loginDevice;
        }

        public void setLoginDevice(String loginDevice) {
            this.loginDevice = loginDevice;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Date getConsumeDate() {
            return consumeDate;
        }

        public void setConsumeDate(Date consumeDate) {
            this.consumeDate = consumeDate;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public int getPayCount() {
            return payCount;
        }

        public void setPayCount(int payCount) {
            this.payCount = payCount;
        }
    }
}
