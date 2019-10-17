package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/9/7.
 */

public class BrandFollowBean extends BaseBean implements Serializable{

    /**
     * data : {"attentions":{"next":false,"total":4,"previous":false,"index":1,"pageSize":40,"list":[{"designerId":10667,"nickName":"��Kitty『美物店』","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"/app/a/17/07/18/DB32E98FF1C3A99327B3B9A6B7715003","memberId":2735460,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"匿名_1905273","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":null,"memberId":1905273,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"感觉自己萌萌哒","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"/app/a/17/07/14/e5cd7efeedb10bc6e8f43fe1b5fcd10f","memberId":2779621,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"perfect妍916","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"http://tva2.sinaimg.cn/crop.0.0.996.996.180/006vBI0Vjw8f50rdcitn4j30ro0rp75u.jpg","memberId":1624605,"designerName":"Barbie/芭比"}]}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * attentions : {"next":false,"total":4,"previous":false,"index":1,"pageSize":40,"list":[{"designerId":10667,"nickName":"��Kitty『美物店』","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"/app/a/17/07/18/DB32E98FF1C3A99327B3B9A6B7715003","memberId":2735460,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"匿名_1905273","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":null,"memberId":1905273,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"感觉自己萌萌哒","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"/app/a/17/07/14/e5cd7efeedb10bc6e8f43fe1b5fcd10f","memberId":2779621,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"perfect妍916","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"http://tva2.sinaimg.cn/crop.0.0.996.996.180/006vBI0Vjw8f50rdcitn4j30ro0rp75u.jpg","memberId":1624605,"designerName":"Barbie/芭比"}]}
         */

        private AttentionsBean attentions;

        public AttentionsBean getAttentions() {
            return attentions;
        }

        public void setAttentions(AttentionsBean attentions) {
            this.attentions = attentions;
        }

        public static class AttentionsBean implements Serializable{
            /**
             * next : false
             * total : 4
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"designerId":10667,"nickName":"��Kitty『美物店』","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"/app/a/17/07/18/DB32E98FF1C3A99327B3B9A6B7715003","memberId":2735460,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"匿名_1905273","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":null,"memberId":1905273,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"感觉自己萌萌哒","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"/app/a/17/07/14/e5cd7efeedb10bc6e8f43fe1b5fcd10f","memberId":2779621,"designerName":"Barbie/芭比"},{"designerId":10667,"nickName":"perfect妍916","designerPic":"/model/1607/11c0ab96d9f2acb1a279067b63965a8d","follow":0,"headPic":"http://tva2.sinaimg.cn/crop.0.0.996.996.180/006vBI0Vjw8f50rdcitn4j30ro0rp75u.jpg","memberId":1624605,"designerName":"Barbie/芭比"}]
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

            public static class ListBean implements Serializable{
                /**
                 * designerId : 10667
                 * nickName : ��Kitty『美物店』
                 * designerPic : /model/1607/11c0ab96d9f2acb1a279067b63965a8d
                 * follow : 0
                 * headPic : /app/a/17/07/18/DB32E98FF1C3A99327B3B9A6B7715003
                 * memberId : 2735460
                 * designerName : Barbie/芭比
                 */

                private int designerId;
                private String nickName;
                private String designerPic;
                private int follow;
                private String headPic;
                private long memberId;
                private String designerName;

                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getDesignerPic() {
                    return designerPic;
                }

                public void setDesignerPic(String designerPic) {
                    this.designerPic = designerPic;
                }

                public int getFollow() {
                    return follow;
                }

                public void setFollow(int follow) {
                    this.follow = follow;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }

                public String getDesignerName() {
                    return designerName;
                }

                public void setDesignerName(String designerName) {
                    this.designerName = designerName;
                }
            }
        }
    }
}
