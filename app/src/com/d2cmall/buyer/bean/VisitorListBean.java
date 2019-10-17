package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/18 11:41
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class VisitorListBean extends BaseBean {

    /**
     * data : {"customers":{"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"990*****007","backgroud":"","mobile":"990*****007","type":1,"recCode":"3uymae","parentId":4145,"head":null,"thirdPic":null,"d2c":true,"name":"990*****007","nickname":"","id":3031133,"partnerId":0,"email":null,"recId":0,"memberId":3031133,"nationCode":"86"}]}}
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
         * customers : {"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"990*****007","backgroud":"","mobile":"990*****007","type":1,"recCode":"3uymae","parentId":4145,"head":null,"thirdPic":null,"d2c":true,"name":"990*****007","nickname":"","id":3031133,"partnerId":0,"email":null,"recId":0,"memberId":3031133,"nationCode":"86"}]}
         */

        private CustomersBean customers;

        public CustomersBean getCustomers() {
            return customers;
        }

        public void setCustomers(CustomersBean customers) {
            this.customers = customers;
        }

        public static class CustomersBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"designerId":0,"birthDay":null,"distributorId":0,"sex":"女","loginCode":"990*****007","backgroud":"","mobile":"990*****007","type":1,"recCode":"3uymae","parentId":4145,"head":null,"thirdPic":null,"d2c":true,"name":"990*****007","nickname":"","id":3031133,"partnerId":0,"email":null,"recId":0,"memberId":3031133,"nationCode":"86"}]
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
                 * designerId : 0
                 * birthDay : null
                 * distributorId : 0
                 * sex : 女
                 * loginCode : 990*****007
                 * backgroud :
                 * mobile : 990*****007
                 * type : 1
                 * recCode : 3uymae
                 * parentId : 4145
                 * head : null
                 * thirdPic : null
                 * d2c : true
                 * name : 990*****007
                 * nickname :
                 * id : 3031133
                 * partnerId : 0
                 * email : null
                 * recId : 0
                 * memberId : 3031133
                 * nationCode : 86
                 */

                private int designerId;
                private String birthDay;
                private int distributorId;
                private String sex;
                private String loginCode;
                private String backgroud;
                private String mobile;
                private int type;
                private String recCode;
                private int parentId;
                private String head;
                private String thirdPic;
                private boolean d2c;
                private String name;
                private String nickname;
                private int id;
                private int partnerId;
                private int recId;
                private int memberId;
                private String nationCode;

                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public String getBirthDay() {
                    return birthDay;
                }

                public void setBirthDay(String birthDay) {
                    this.birthDay = birthDay;
                }

                public int getDistributorId() {
                    return distributorId;
                }

                public void setDistributorId(int distributorId) {
                    this.distributorId = distributorId;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public String getBackgroud() {
                    return backgroud;
                }

                public void setBackgroud(String backgroud) {
                    this.backgroud = backgroud;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getRecCode() {
                    return recCode;
                }

                public void setRecCode(String recCode) {
                    this.recCode = recCode;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }

                public String getHead() {
                    return head;
                }

                public void setHead(String head) {
                    this.head = head;
                }

                public String getThirdPic() {
                    return thirdPic;
                }

                public void setThirdPic(String thirdPic) {
                    this.thirdPic = thirdPic;
                }

                public boolean isD2c() {
                    return d2c;
                }

                public void setD2c(boolean d2c) {
                    this.d2c = d2c;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getPartnerId() {
                    return partnerId;
                }

                public void setPartnerId(int partnerId) {
                    this.partnerId = partnerId;
                }

                public int getRecId() {
                    return recId;
                }

                public void setRecId(int recId) {
                    this.recId = recId;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public String getNationCode() {
                    return nationCode;
                }

                public void setNationCode(String nationCode) {
                    this.nationCode = nationCode;
                }
            }
        }
    }
}
