package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 * Description : NewPeopleCouponsBean
 */

public class NewPeopleCouponsBean  extends BaseBean{

    /**
     * data : {"fixCoupons":{"next":false,"total":3,"previous":false,"index":1,"pageSize":50,"list":[{"enableDate":"2017/12/31 00:00","amount":400,"couponName":"新人有礼 优惠券组2599-400","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券组2599-400","activeDay":0,"expireDate":"2019/01/01 00:00","id":562,"free":1,"isClaim":true,"needAmount":2599},{"enableDate":"2017/12/31 00:00","amount":200,"couponName":"新人有礼 优惠券组1399-200","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券组1399-200","activeDay":0,"expireDate":"2019/01/01 00:00","id":561,"free":1,"isClaim":true,"needAmount":1999},{"enableDate":"2017/12/31 00:00","amount":100,"couponName":"新人有礼 优惠券699-100","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券699-100","activeDay":0,"expireDate":"2019/01/01 00:00","id":560,"free":1,"isClaim":true,"needAmount":799}]}}
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
         * fixCoupons : {"next":false,"total":3,"previous":false,"index":1,"pageSize":50,"list":[{"enableDate":"2017/12/31 00:00","amount":400,"couponName":"新人有礼 优惠券组2599-400","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券组2599-400","activeDay":0,"expireDate":"2019/01/01 00:00","id":562,"free":1,"isClaim":true,"needAmount":2599},{"enableDate":"2017/12/31 00:00","amount":200,"couponName":"新人有礼 优惠券组1399-200","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券组1399-200","activeDay":0,"expireDate":"2019/01/01 00:00","id":561,"free":1,"isClaim":true,"needAmount":1999},{"enableDate":"2017/12/31 00:00","amount":100,"couponName":"新人有礼 优惠券699-100","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券699-100","activeDay":0,"expireDate":"2019/01/01 00:00","id":560,"free":1,"isClaim":true,"needAmount":799}]}
         */

        private FixCouponsBean fixCoupons;

        public FixCouponsBean getFixCoupons() {
            return fixCoupons;
        }

        public void setFixCoupons(FixCouponsBean fixCoupons) {
            this.fixCoupons = fixCoupons;
        }

        public static class FixCouponsBean {
            /**
             * next : false
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 50
             * list : [{"enableDate":"2017/12/31 00:00","amount":400,"couponName":"新人有礼 优惠券组2599-400","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券组2599-400","activeDay":0,"expireDate":"2019/01/01 00:00","id":562,"free":1,"isClaim":true,"needAmount":2599},{"enableDate":"2017/12/31 00:00","amount":200,"couponName":"新人有礼 优惠券组1399-200","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券组1399-200","activeDay":0,"expireDate":"2019/01/01 00:00","id":561,"free":1,"isClaim":true,"needAmount":1999},{"enableDate":"2017/12/31 00:00","amount":100,"couponName":"新人有礼 优惠券699-100","remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\n2、本优惠券仅供新人专享。\n3、本券不得叠加使用，一个订单仅限使用一张。\n4、本券不可兑现，不可找零。\n5、所有解释权仅归D2C全球设计师集成平台所有。","type":"CASH","price":null,"name":"新人有礼 优惠券699-100","activeDay":0,"expireDate":"2019/01/01 00:00","id":560,"free":1,"isClaim":true,"needAmount":799}]
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
                 * enableDate : 2017/12/31 00:00
                 * amount : 400
                 * couponName : 新人有礼 优惠券组2599-400
                 * remark : 1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。
                 2、本优惠券仅供新人专享。
                 3、本券不得叠加使用，一个订单仅限使用一张。
                 4、本券不可兑现，不可找零。
                 5、所有解释权仅归D2C全球设计师集成平台所有。
                 * type : CASH
                 * price : null
                 * name : 新人有礼 优惠券组2599-400
                 * activeDay : 0
                 * expireDate : 2019/01/01 00:00
                 * id : 562
                 * free : 1
                 * isClaim : true
                 * needAmount : 2599
                 */

                private int amount;
                private int needAmount;


                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }


                public int getNeedAmount() {
                    return needAmount;
                }

                public void setNeedAmount(int needAmount) {
                    this.needAmount = needAmount;
                }
            }
        }
    }
}
