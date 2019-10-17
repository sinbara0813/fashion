package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/11/29.
 * Description : WardrobeDetailBean
 */

public class WardrobeDetailBean extends BaseBean {

    /**
     * data : {"wardrobe":{"color":"#8C92A0,0.6102;#6D6466,0.3898","nickName":"考拉🐨","price":"158","loginCode":"15888897624","season":"春,夏,秋,冬,","id":10,"pic":"/app/f/18/11/29/222554f1960803dcd557e55f6ed6ab58","topName":"123123","categoryName":"测试","categoryId":1,"scene":"派对,居家,","tags":"浅蓝,灰,小碎花,长袖连衣裙,都市休闲,"}}
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
         * wardrobe : {"color":"#8C92A0,0.6102;#6D6466,0.3898","nickName":"考拉🐨","price":"158","loginCode":"15888897624","season":"春,夏,秋,冬,","id":10,"pic":"/app/f/18/11/29/222554f1960803dcd557e55f6ed6ab58","topName":"123123","categoryName":"测试","categoryId":1,"scene":"派对,居家,","tags":"浅蓝,灰,小碎花,长袖连衣裙,都市休闲,"}
         */

        private WardrobeBean wardrobe;

        public WardrobeBean getWardrobe() {
            return wardrobe;
        }

        public void setWardrobe(WardrobeBean wardrobe) {
            this.wardrobe = wardrobe;
        }

        public static class WardrobeBean {
            /**
             * color : #8C92A0,0.6102;#6D6466,0.3898
             * nickName : 考拉🐨
             * price : 158
             * loginCode : 15888897624
             * season : 春,夏,秋,冬,
             * id : 10
             * pic : /app/f/18/11/29/222554f1960803dcd557e55f6ed6ab58
             * topName : 123123
             * categoryName : 测试
             * categoryId : 1
             * scene : 派对,居家,
             * tags : 浅蓝,灰,小碎花,长袖连衣裙,都市休闲,
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
