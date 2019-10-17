package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class DesignerInfoBean extends BaseBean {

    /**
     * designer : {"id":10059,"introPic":"/model/1603/dbfd277eb6e930ac43017205cb49b52e","headPic":"/model/1603/e2a8545a5d01030b2692baa0e702514b","brand":"安博","name":"annboocollection","intro":"<span style=\"font-size: 10.5pt;\"><\/span><p>&nbsp;&nbsp;&nbsp; Annboo的品牌从创立之初，就已融入了设计师安博的个人烙印，近乎苛求的细节和只为气场服务的廓型。设计师不光在形体上，更在一些无形的气场上追求完美。你和annboo，就像是谈恋爱，如果这件衣服和你合拍，那么，你在第一眼就会爱上她。<\/p><p>&nbsp;&nbsp;&nbsp; Annboo品牌创立至今，受到张静初、杨幂、王菲、熊黛林、鲁豫等一线明星和主持人的力挺。进入2014春夏季，更为应景的印花和清爽材质，大胆地穿出王道的经典Style，这也是今年春夏季annboo品牌的诉求。<br /><\/p>","likeCount":101}
     * products : [{"id":122589,"img":"/model/1603/11c598936c426560c6f2267d9521cbd1","price":1680,"minPrice":1680,"name":"ANNBOOCOLLECTION   安博  休闲圆领无袖乐器印花简约风格连衣裙","isTopical":false,"store":1,"mark":1},{"id":122588,"img":"/model/1603/9f9d33f38f560a5375f9a8ea7336a3a4","price":2480,"minPrice":2480,"name":"ANNBOOCOLLECTION   安博 休闲圆领无袖下摆开叉简约风格连衣裙","isTopical":false,"store":1,"mark":1},{"id":122587,"img":"/model/1603/9ca25567b6a79dcc5644f5eb79b5ca1b","price":1360,"minPrice":1360,"name":"ANNBOOCOLLECTION   安博  青春少女风格设计感修身吊带休闲上衣","isTopical":false,"store":1,"mark":1},{"id":122586,"img":"/model/1603/5fe7a66259e8b273498011ff9aab82bc","price":2280,"minPrice":2280,"name":"ANNBOOCOLLECTION   安博   休闲单宁圆领落肩款乐器贴布绣连衣裙","isTopical":false,"store":1,"mark":1},{"id":122585,"img":"/model/1603/4950471bacc9ec885091f83592fbd9d4","price":2380,"minPrice":2380,"name":"ANNBOOCOLLECTION   安博  经典翻领蓬蓬袖口下摆不规则设计连衣裙","isTopical":false,"store":1,"mark":1},{"id":122279,"img":"/model/1602/ee8cde7e9bd1389fef13232b85324de8","price":1580,"minPrice":1580,"name":"ANNBOOCOLLECTION  安博  薄纱镂空印饰立体花朵流苏坠饰卫衣    ","isTopical":false,"store":1,"mark":1},{"id":122281,"img":"/model/1602/e8068b87e7e15bdeaa8b5bb4abee56bf","price":1380,"minPrice":1380,"name":"ANNBOOCOLLECTION  安博 休闲露肩毛须边袖口吊带装饰修身衬衫 ","isTopical":false,"store":1,"mark":1},{"id":122284,"img":"/model/1602/921b7339527fcd02ae2e8710b0e28dfe","price":1680,"minPrice":1680,"name":"ANNBOOCOLLECTION  安博  前襟立体木耳边下摆少女感褶皱休闲衬衫  ","isTopical":false,"store":1,"mark":1},{"id":122289,"img":"/model/1602/7d7f43ea4be8d23106c69dedd5c78049","price":1380,"minPrice":1380,"name":"ANNBOOCOLLECTION  安博 率性小立领清爽浅蓝色休闲衬衫 ","isTopical":false,"store":1,"mark":1},{"id":122290,"img":"/model/1602/e8769d7a08fc8baabfdbdef6de9c0bb4","price":1180,"minPrice":1180,"name":"ANNBOOCOLLECTION  安博  超短设计感下摆流苏坠饰前襟立体贴布装饰笼袖上衣","isTopical":false,"store":1,"mark":1}]
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
         * id : 10059
         * introPic : /model/1603/dbfd277eb6e930ac43017205cb49b52e
         * headPic : /model/1603/e2a8545a5d01030b2692baa0e702514b
         * brand : 安博
         * name : annboocollection
         * intro : <span style="font-size: 10.5pt;"></span><p>&nbsp;&nbsp;&nbsp; Annboo的品牌从创立之初，就已融入了设计师安博的个人烙印，近乎苛求的细节和只为气场服务的廓型。设计师不光在形体上，更在一些无形的气场上追求完美。你和annboo，就像是谈恋爱，如果这件衣服和你合拍，那么，你在第一眼就会爱上她。</p><p>&nbsp;&nbsp;&nbsp; Annboo品牌创立至今，受到张静初、杨幂、王菲、熊黛林、鲁豫等一线明星和主持人的力挺。进入2014春夏季，更为应景的印花和清爽材质，大胆地穿出王道的经典Style，这也是今年春夏季annboo品牌的诉求。<br /></p>
         * likeCount : 101
         */

        private DesignerEntity designer;
        /**
         * id : 122589
         * img : /model/1603/11c598936c426560c6f2267d9521cbd1
         * price : 1680.0
         * minPrice : 1680.0
         * name : ANNBOOCOLLECTION   安博  休闲圆领无袖乐器印花简约风格连衣裙
         * isTopical : false
         * store : 1
         * mark : 1
         */

        private List<ProductsEntity> products;

        public DesignerEntity getDesigner() {
            return designer;
        }

        public void setDesigner(DesignerEntity designer) {
            this.designer = designer;
        }

        public List<ProductsEntity> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsEntity> products) {
            this.products = products;
        }

        public static class DesignerEntity {
            private int id;
            private int hasAppIntro;
            private String introPic;
            private String headPic;
            private String brand;
            private String name;
            private String intro;
            private int likeCount;
            private String country;
            private int attentioned;
            private String video;

            public String getVideo() {
                return video;
            }

            public void setHasAppIntro(int hasAppIntro) {
                this.hasAppIntro = hasAppIntro;
            }

            public int getHasAppIntro() {
                return hasAppIntro;

            }

            public void setVideo(String video) {
                this.video = video;
            }

            public int getAttentioned() {
                return attentioned;
            }

            public void setAttentioned(int attentioned) {
                this.attentioned = attentioned;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntroPic() {
                return introPic;
            }

            public void setIntroPic(String introPic) {
                this.introPic = introPic;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }
        }

        public static class ProductsEntity {
            private int id;
            private String img;
            private double price;
            private double minPrice;
            private String name;
            private boolean isTopical;
            private int store;
            private int mark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
