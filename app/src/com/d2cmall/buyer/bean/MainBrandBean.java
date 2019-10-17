package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 15:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainBrandBean extends BaseBean {

    /**
     * data : {"upMarketBrands":[{"brand":{"name":"YOUG X","id":10513,"introPic":"/model/1604/2b1e62be5c310787eb02989cfbca27bc","headPic":"/model/1604/81bc3c88c061815ff8894dcf46b9dd9a"},"products":[{"price":3880,"mainPic":"","id":168962},{"price":4280,"mainPic":"","id":168995},{"price":1980,"mainPic":"","id":169789},{"price":3499,"mainPic":"","id":169920},{"price":3980,"mainPic":"","id":169000},{"price":2380,"mainPic":"","id":168489}]},{"brand":{"name":"9631 cm","id":10753,"introPic":"/2016/11/24/0634560336b038bcd9273ace3c473db22e56d6.jpg","headPic":"/2016/09/02/1248362349271a9c1f116582cc87ca80a9df83.jpg"},"products":[{"price":899,"mainPic":"","id":170009},{"price":699,"mainPic":"","id":170008},{"price":999,"mainPic":"","id":144302},{"price":599,"mainPic":"","id":145963},{"price":799,"mainPic":"","id":158206},{"price":799,"mainPic":"","id":158201}]}],"recommendBrandPic":[{"name":"YOUG X","id":10513,"introPic":"/model/1604/2b1e62be5c310787eb02989cfbca27bc","headPic":"/model/1604/81bc3c88c061815ff8894dcf46b9dd9a"},{"name":"9631 cm","id":10753,"introPic":"/2016/11/24/0634560336b038bcd9273ace3c473db22e56d6.jpg","headPic":"/2016/09/02/1248362349271a9c1f116582cc87ca80a9df83.jpg"},{"name":"ZENESSE","id":11221,"introPic":"/2017/08/16/08022554aa9bb6151fa042c55b73a248081c42.jpg","headPic":"/2017/08/16/072914deea13a03a58d2800df3137462c55a4d.jpg"},{"name":"玖拾玖度","id":10006,"introPic":"/model/1603/a5424a07401254b08c34cd66b7ee5244","headPic":"/model/1603/5d086656c9d647542aa1c03f9b01fa9f"},{"name":"TRACY CHU","id":10390,"introPic":"/model/1511/f4b0ff884982a2006c1711db2fe01225","headPic":"/model/1603/2ff61a7feecabf92f3e8a712012fecf1"},{"name":"Pink bell","id":11032,"introPic":"/2017/03/20/074515ea3e641c89b7341b46f2f758ed134f19.jpg","headPic":"/2017/03/20/034736aca06a721fb229d41b395167e5a62848.jpg"},{"name":"10x1","id":10617,"introPic":"/model/1605/9cb9aa4621e525183c10e8874943de54","headPic":"/model/1605/c0ca409904bdaac5ad33842ced07096b"},{"name":"William's Angel","id":11228,"introPic":"/2017/08/24/100546a18e5784c53e54dfe45d3d500483fff6.jpg","headPic":"/2017/08/24/100543cd4871a83566e3f76aa771a9e2ba2e20.jpg"},{"name":"设计师-测试专用","id":10942,"introPic":"/2017/01/09/07250853c2a29542cdc406ced231327f0bb038.jpg","headPic":"/2017/01/09/07250153c2a29542cdc406ced231327f0bb038.jpg"},{"name":"1011","id":10687,"introPic":"/2017/03/17/024744eff0bf5cc92ee083dd51971e560b1e79.jpg","headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<UpMarketBrandsBean> upMarketBrands;
        private List<RecommendBrandPicBean> recommendBrandPic;

        public List<UpMarketBrandsBean> getUpMarketBrands() {
            return upMarketBrands;
        }

        public void setUpMarketBrands(List<UpMarketBrandsBean> upMarketBrands) {
            this.upMarketBrands = upMarketBrands;
        }

        public List<RecommendBrandPicBean> getRecommendBrandPic() {
            return recommendBrandPic;
        }

        public void setRecommendBrandPic(List<RecommendBrandPicBean> recommendBrandPic) {
            this.recommendBrandPic = recommendBrandPic;
        }

        public static class UpMarketBrandsBean {
            /**
             * brand : {"name":"YOUG X","id":10513,"introPic":"/model/1604/2b1e62be5c310787eb02989cfbca27bc","headPic":"/model/1604/81bc3c88c061815ff8894dcf46b9dd9a"}
             * products : [{"price":3880,"mainPic":"","id":168962},{"price":4280,"mainPic":"","id":168995},{"price":1980,"mainPic":"","id":169789},{"price":3499,"mainPic":"","id":169920},{"price":3980,"mainPic":"","id":169000},{"price":2380,"mainPic":"","id":168489}]
             */

            private BrandBean brand;
            private List<ProductsBean> products;

            public BrandBean getBrand() {
                return brand;
            }

            public void setBrand(BrandBean brand) {
                this.brand = brand;
            }

            public List<ProductsBean> getProducts() {
                return products;
            }

            public void setProducts(List<ProductsBean> products) {
                this.products = products;
            }

            public static class BrandBean {
                /**
                 * name : YOUG X
                 * id : 10513
                 * introPic : /model/1604/2b1e62be5c310787eb02989cfbca27bc
                 * headPic : /model/1604/81bc3c88c061815ff8894dcf46b9dd9a
                 */

                private String name;
                private int id;
                private String introPic;
                private String headPic;

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
            }

            public static class ProductsBean {
                /**
                 * price : 3880.0
                 * mainPic :
                 * id : 168962
                 */

                private double price;
                private String mainPic;
                private int id;

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getMainPic() {
                    return mainPic;
                }

                public void setMainPic(String mainPic) {
                    this.mainPic = mainPic;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }

        public static class RecommendBrandPicBean {
            /**
             * name : YOUG X
             * id : 10513
             * introPic : /model/1604/2b1e62be5c310787eb02989cfbca27bc
             * headPic : /model/1604/81bc3c88c061815ff8894dcf46b9dd9a
             */

            private String name;
            private int id;
            private String introPic;
            private String headPic;

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
        }
    }
}
