package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/28.
 * Description : WardrobeListItemBean
 */

public class WardrobeListItemBean extends BaseBean {

    /**
     * data : {"myWardrobes":{"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"color":"#D4CCBC,1.0","nickName":"考拉🐨","price":"155","loginCode":"15888897624","season":"冬,","id":4,"pic":"/app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a","topName":"123123","categoryName":"测试","categoryId":1,"scene":"旅行,","tags":"浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,"},{"color":"#D4CCBC,1.0","nickName":"考拉🐨","price":"155","loginCode":"15888897624","season":"冬,","id":3,"pic":"/app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a","topName":"123123","categoryName":"测试","categoryId":1,"scene":"旅行,","tags":"浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,"}]}}
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
         * myWardrobes : {"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"color":"#D4CCBC,1.0","nickName":"考拉🐨","price":"155","loginCode":"15888897624","season":"冬,","id":4,"pic":"/app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a","topName":"123123","categoryName":"测试","categoryId":1,"scene":"旅行,","tags":"浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,"},{"color":"#D4CCBC,1.0","nickName":"考拉🐨","price":"155","loginCode":"15888897624","season":"冬,","id":3,"pic":"/app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a","topName":"123123","categoryName":"测试","categoryId":1,"scene":"旅行,","tags":"浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,"}]}
         */

        private MyWardrobesBean myWardrobes;

        public MyWardrobesBean getMyWardrobes() {
            return myWardrobes;
        }

        public void setMyWardrobes(MyWardrobesBean myWardrobes) {
            this.myWardrobes = myWardrobes;
        }

        public static class MyWardrobesBean {
            /**
             * next : false
             * total : 2
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"color":"#D4CCBC,1.0","nickName":"考拉🐨","price":"155","loginCode":"15888897624","season":"冬,","id":4,"pic":"/app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a","topName":"123123","categoryName":"测试","categoryId":1,"scene":"旅行,","tags":"浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,"},{"color":"#D4CCBC,1.0","nickName":"考拉🐨","price":"155","loginCode":"15888897624","season":"冬,","id":3,"pic":"/app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a","topName":"123123","categoryName":"测试","categoryId":1,"scene":"旅行,","tags":"浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,"}]
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

            public static class ListBean implements Serializable {
                /**
                 * color : #D4CCBC,1.0
                 * nickName : 考拉🐨
                 * price : 155
                 * loginCode : 15888897624
                 * season : 冬,
                 * id : 4
                 * pic : /app/f/18/11/28/4a715a9c9b567ce8c17bbb96b132e14a
                 * topName : 123123
                 * categoryName : 测试
                 * categoryId : 1
                 * scene : 旅行,
                 * tags : 浅橘黄,纯色,风衣,优雅,经典百搭,现代,都市优雅,外出,通勤,早春/早秋,
                 */

                private String color;
                private String nickName;
                private String price;
                private String loginCode;
                private String season;
                private int id;
                private String pic;
                private String topName;
                private String categoryName;
                private int categoryId;
                private String scene;
                private String tags;

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public String getSeason() {
                    return season;
                }

                public void setSeason(String season) {
                    this.season = season;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getTopName() {
                    return topName;
                }

                public void setTopName(String topName) {
                    this.topName = topName;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public String getScene() {
                    return scene;
                }

                public void setScene(String scene) {
                    this.scene = scene;
                }

                public String getTags() {
                    return tags;
                }

                public void setTags(String tags) {
                    this.tags = tags;
                }
            }
        }
    }
}
