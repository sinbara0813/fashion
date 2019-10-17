package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class RecommendProductBean extends BaseBean {

    /**
     * recommends : {"index":1,"pageSize":10,"total":10049,"previous":false,"next":true,"list":[{"id":116602,"img":"/model/1511/d15f7f55d2dd5045d378c1071bc6ca62","price":5776,"minPrice":2888,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季 范冰冰 李小璐 张歆艺 陈紫函 霍思燕 谢娜 张天爱 何洁 明星同款 时光之眼系列 大貉子毛拉链连帽贴标眼睛长款羽绒服  女款    ","isTopical":true,"store":1,"mark":1},{"id":116608,"img":"/model/1511/f883ebd9058c860e02073cb1f81ad4f2","price":5976,"minPrice":2988,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季  黄致列 张钧涵 张磊 金志文 王俊凯 王源 易烊千玺 杜江 张杰 陈晓 魏大勋 程星源 明星同款 大貉子毛拉链连帽眼睛贴标长款羽绒服  时光之眼系列男款     ","isTopical":true,"store":1,"mark":1},{"id":101586,"img":"/pi/86/101586/94ac9d4b15338dd42e40767344e0da51","price":3800,"minPrice":3800,"name":"万一方 SAMUEL YANG SIMULATION炫彩发光鞋 荧光鞋 夜光鞋 W00543830001","isTopical":true,"store":1,"mark":1},{"id":116599,"img":"/model/1511/49a25e2349c677534282cf9b837d090b","price":2976,"minPrice":1488,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季 黄致列 张翰 王嘉尔 余明轩 陈晓 tfboys-王源 李茂  明星同款 可隐藏多变眼睛造型酷尚齿轮 短款夹克羽绒服 时光之眼系列 男款    ","isTopical":true,"store":1,"mark":1},{"id":116596,"img":"/model/1511/c8a43fc80a9310483b6d60c616b02c55","price":2776,"minPrice":1388,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依   于莎莎 颖儿 戚薇 郑亦桐 金池 伊一 漆亚灵 徐璐 张子枫 明星同款 囧囧侠第二季 可隐藏多变眼睛造型酷尚齿轮 短款夹克羽绒服 时光之眼系列女款    ","isTopical":true,"store":1,"mark":1},{"id":116586,"img":"/model/1511/c95c3b76113339dacdf5624d3139e9e8","price":2776,"minPrice":1388,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 范冰冰 囧囧侠第二季 中国梦之声艾菲 赵文卓老婆张丹露 明星同款 可隐藏多变眼睛造型视觉齿轮短款羽绒服 时光之眼系列 女款       ","isTopical":true,"store":1,"mark":1},{"id":122122,"img":"/model/1602/c302ca987490c9dedb48341c2ce5a8c7","price":2280,"minPrice":999,"name":"D2C×ZHANGSHUAI  两周年感恩回馈 郑亦桐 范冰冰 明星同款 宝宝熊猫黑白经典条纹礼服裙 联名限量款","isTopical":true,"store":1,"mark":1},{"id":115706,"img":"/model/1510/21754ad4e6ba982c68729d50b7484e19","price":999,"minPrice":999,"name":"MR MODONIN 计广杰 赤目系列 会听你说话的神秘眨眼潮服 男款声控发光猫头鹰卫衣","isTopical":true,"store":1,"mark":1},{"id":116614,"img":"/model/1510/b38e2fcdd6bf6d26fe72ad8ee8eedc24","price":2628,"minPrice":1314,"name":"ZHANGSHUAI X ERAL 黄晓明&angelababy亲身设计款 【以爱之名】系列 立体贴标MING&BABY夹克短款羽绒服 女款     ","isTopical":true,"store":1,"mark":1},{"id":116616,"img":"/model/1510/0ba70e43a5fb5f17a908d7cfa85ad576","price":2826,"minPrice":1413,"name":"ZHANGSHUAI X ERAL 黄晓明&angelababy亲身设计款 【以爱之名】系列 立体贴标MING&BABY夹克短款羽绒服 男款    ","isTopical":true,"store":1,"mark":1}]}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 10
         * total : 10049
         * previous : false
         * next : true
         * list : [{"id":116602,"img":"/model/1511/d15f7f55d2dd5045d378c1071bc6ca62","price":5776,"minPrice":2888,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季 范冰冰 李小璐 张歆艺 陈紫函 霍思燕 谢娜 张天爱 何洁 明星同款 时光之眼系列 大貉子毛拉链连帽贴标眼睛长款羽绒服  女款    ","isTopical":true,"store":1,"mark":1},{"id":116608,"img":"/model/1511/f883ebd9058c860e02073cb1f81ad4f2","price":5976,"minPrice":2988,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季  黄致列 张钧涵 张磊 金志文 王俊凯 王源 易烊千玺 杜江 张杰 陈晓 魏大勋 程星源 明星同款 大貉子毛拉链连帽眼睛贴标长款羽绒服  时光之眼系列男款     ","isTopical":true,"store":1,"mark":1},{"id":101586,"img":"/pi/86/101586/94ac9d4b15338dd42e40767344e0da51","price":3800,"minPrice":3800,"name":"万一方 SAMUEL YANG SIMULATION炫彩发光鞋 荧光鞋 夜光鞋 W00543830001","isTopical":true,"store":1,"mark":1},{"id":116599,"img":"/model/1511/49a25e2349c677534282cf9b837d090b","price":2976,"minPrice":1488,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季 黄致列 张翰 王嘉尔 余明轩 陈晓 tfboys-王源 李茂  明星同款 可隐藏多变眼睛造型酷尚齿轮 短款夹克羽绒服 时光之眼系列 男款    ","isTopical":true,"store":1,"mark":1},{"id":116596,"img":"/model/1511/c8a43fc80a9310483b6d60c616b02c55","price":2776,"minPrice":1388,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依   于莎莎 颖儿 戚薇 郑亦桐 金池 伊一 漆亚灵 徐璐 张子枫 明星同款 囧囧侠第二季 可隐藏多变眼睛造型酷尚齿轮 短款夹克羽绒服 时光之眼系列女款    ","isTopical":true,"store":1,"mark":1},{"id":116586,"img":"/model/1511/c95c3b76113339dacdf5624d3139e9e8","price":2776,"minPrice":1388,"name":"ZHANGSHUAI X ERAL 张帅X艾莱依 范冰冰 囧囧侠第二季 中国梦之声艾菲 赵文卓老婆张丹露 明星同款 可隐藏多变眼睛造型视觉齿轮短款羽绒服 时光之眼系列 女款       ","isTopical":true,"store":1,"mark":1},{"id":122122,"img":"/model/1602/c302ca987490c9dedb48341c2ce5a8c7","price":2280,"minPrice":999,"name":"D2C×ZHANGSHUAI  两周年感恩回馈 郑亦桐 范冰冰 明星同款 宝宝熊猫黑白经典条纹礼服裙 联名限量款","isTopical":true,"store":1,"mark":1},{"id":115706,"img":"/model/1510/21754ad4e6ba982c68729d50b7484e19","price":999,"minPrice":999,"name":"MR MODONIN 计广杰 赤目系列 会听你说话的神秘眨眼潮服 男款声控发光猫头鹰卫衣","isTopical":true,"store":1,"mark":1},{"id":116614,"img":"/model/1510/b38e2fcdd6bf6d26fe72ad8ee8eedc24","price":2628,"minPrice":1314,"name":"ZHANGSHUAI X ERAL 黄晓明&angelababy亲身设计款 【以爱之名】系列 立体贴标MING&BABY夹克短款羽绒服 女款     ","isTopical":true,"store":1,"mark":1},{"id":116616,"img":"/model/1510/0ba70e43a5fb5f17a908d7cfa85ad576","price":2826,"minPrice":1413,"name":"ZHANGSHUAI X ERAL 黄晓明&angelababy亲身设计款 【以爱之名】系列 立体贴标MING&BABY夹克短款羽绒服 男款    ","isTopical":true,"store":1,"mark":1}]
         */

        private RecommendsEntity recommends;

        public RecommendsEntity getRecommends() {
            return recommends;
        }

        public void setRecommends(RecommendsEntity recommends) {
            this.recommends = recommends;
        }

        public static class RecommendsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 116602
             * img : /model/1511/d15f7f55d2dd5045d378c1071bc6ca62
             * price : 5776.0
             * minPrice : 2888.0
             * name : ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季 范冰冰 李小璐 张歆艺 陈紫函 霍思燕 谢娜 张天爱 何洁 明星同款 时光之眼系列 大貉子毛拉链连帽贴标眼睛长款羽绒服  女款
             * isTopical : true
             * store : 1
             * mark : 1
             */

            private List<ListEntity> list;

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

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private String img;
                private double price;
                private double minPrice;
                private String name;
                private boolean isTopical;
                private int store;
                private int mark;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getMinPrice() {
                    return minPrice;
                }

                public void setMinPrice(double minPrice) {
                    this.minPrice = minPrice;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public boolean isIsTopical() {
                    return isTopical;
                }

                public void setIsTopical(boolean isTopical) {
                    this.isTopical = isTopical;
                }

                public int getStore() {
                    return store;
                }

                public void setStore(int store) {
                    this.store = store;
                }

                public int getMark() {
                    return mark;
                }

                public void setMark(int mark) {
                    this.mark = mark;
                }
            }
        }
    }
}
