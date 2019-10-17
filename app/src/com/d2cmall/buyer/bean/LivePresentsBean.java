package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 礼物列表Bean
 * Author: Blend
 * Date: 2017/01/06 11:16
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class LivePresentsBean extends BaseBean {
    /**
     * data : {"presents":[{"price":3.5,"name":"帅哥","id":1,"sort":2,"pic":"www.baidu.com"},{"price":12,"name":"xxx","id":2,"sort":1,"pic":"/2016/12/28/073630bad263771b9154766b0d24ea1e84949a.jpg"},{"price":2,"name":"2","id":11,"sort":0,"pic":"/2017/01/05/101112f34076cd296d264d2cc6171bf9524c87.jpg"},{"price":3,"name":"23","id":12,"sort":0,"pic":"/2017/01/05/1011288fbc42c92fe988f353c5b4260e653abf.jpg"},{"price":4,"name":"4","id":13,"sort":0,"pic":"/2017/01/05/101138d34083193fdf50526f50957c326374ff.png"},{"price":5,"name":"5","id":14,"sort":0,"pic":"/2017/01/05/1011480646afbbd17b8e0d099d3db9544ecc3c.png"},{"price":7,"name":"7","id":15,"sort":0,"pic":"/2017/01/05/10120120fb5144647163f5578e42caaaefafe2.png"},{"price":8,"name":"8","id":16,"sort":0,"pic":"/2017/01/05/1012165dd20b20551d9852b2d3cdc4ee5f931b.png"},{"price":9,"name":"9","id":17,"sort":0,"pic":"/2017/01/05/1012494ffc3e2e41c61f62ca7ae485faca9d95.jpg"},{"price":10,"name":"10","id":18,"sort":0,"pic":"/2017/01/05/1013098c6e0e4ae2aba89d0bcc6a66ba1f0f8e.png"},{"price":11,"name":"11","id":19,"sort":0,"pic":"/2017/01/05/101321acddfdf8a5ae59ca1b27dbc5f118f97d.png"},{"price":12,"name":"12","id":20,"sort":0,"pic":"/2017/01/05/101333f34076cd296d264d2cc6171bf9524c87.jpg"},{"price":13,"name":"13","id":21,"sort":0,"pic":"/2017/01/05/1013483cdc8c8389e3015ea17a05ab139e9951.png"},{"price":14,"name":"14","id":22,"sort":0,"pic":"/2017/01/05/101358756241261247ec14868623662c44c9e6.jpg"},{"price":15,"name":"15","id":23,"sort":0,"pic":"/2017/01/05/101411dc080ef27806d29203b97af06684c744.png"},{"price":16,"name":"16","id":24,"sort":0,"pic":"/2017/01/05/101423d3c8db76f76bb068643963ca6a620261.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<PresentsBean> presents;

        public List<PresentsBean> getPresents() {
            return presents;
        }

        public void setPresents(List<PresentsBean> presents) {
            this.presents = presents;
        }

        public static class PresentsBean {
            /**
             * price : 3.5
             * name : 帅哥
             * id : 1
             * sort : 2
             * pic : www.baidu.com
             */

            private double price;
            private String name;
            private int id;
            private int sort;
            private String pic;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

        }
    }
}
