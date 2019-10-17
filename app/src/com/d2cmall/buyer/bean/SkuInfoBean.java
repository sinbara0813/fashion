package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class SkuInfoBean extends BaseBean {

    /**
     * skuInfo : {"item":"L50717F031000100202001","price":199,"Size":[{"Size_id":1,"price":199,"Size":"s","Color":"本白","Color_id":1,"SN":"L50717F031000100201201","store":0,"skuId":164016},{"Size_id":1,"price":199,"Size":"s","Color":"黑色","Color_id":2,"SN":"L50717F031000100202001","store":16,"skuId":164020}],"Color":[{"Size_id":1,"price":199,"Size":"s","Color":"黑色","Color_id":2,"SN":"L50717F031000100202001","store":16,"skuId":164020},{"Size_id":2,"price":199,"Size":"m","Color":"黑色","Color_id":2,"SN":"L50717F031000100202002","store":16,"skuId":164021},{"Size_id":3,"price":199,"Size":"l","Color":"黑色","Color_id":2,"SN":"L50717F031000100202003","store":0,"skuId":164022},{"Size_id":4,"price":199,"Size":"xl","Color":"黑色","Color_id":2,"SN":"L50717F031000100202004","store":0,"skuId":164023}],"id":164020,"store":16,"status":0}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * item : L50717F031000100202001
         * price : 199
         * Size : [{"Size_id":1,"price":199,"Size":"s","Color":"本白","Color_id":1,"SN":"L50717F031000100201201","store":0,"skuId":164016},{"Size_id":1,"price":199,"Size":"s","Color":"黑色","Color_id":2,"SN":"L50717F031000100202001","store":16,"skuId":164020}]
         * Color : [{"Size_id":1,"price":199,"Size":"s","Color":"黑色","Color_id":2,"SN":"L50717F031000100202001","store":16,"skuId":164020},{"Size_id":2,"price":199,"Size":"m","Color":"黑色","Color_id":2,"SN":"L50717F031000100202002","store":16,"skuId":164021},{"Size_id":3,"price":199,"Size":"l","Color":"黑色","Color_id":2,"SN":"L50717F031000100202003","store":0,"skuId":164022},{"Size_id":4,"price":199,"Size":"xl","Color":"黑色","Color_id":2,"SN":"L50717F031000100202004","store":0,"skuId":164023}]
         * id : 164020
         * store : 16
         * status : 0
         */

        private SkuInfoEntity skuInfo;
        private int compareStore;

        public void setSkuInfo(SkuInfoEntity skuInfo) {
            this.skuInfo = skuInfo;
        }

        public SkuInfoEntity getSkuInfo() {
            return skuInfo;
        }

        public int getCompareStore() {
            return compareStore;
        }

        public void setCompareStore(int compareStore) {
            this.compareStore = compareStore;
        }

        public static class SkuInfoEntity {
            private String item;
            private double price;
            private int id;
            private int store;
            private int stock;
            private int status;
            /**
             * Size_id : 1
             * price : 199
             * Size : s
             * Color : 本白
             * Color_id : 1
             * SN : L50717F031000100201201
             * store : 0
             * skuId : 164016
             */

            private List<SizeEntity> Size;
            /**
             * Size_id : 1
             * price : 199
             * Size : s
             * Color : 黑色
             * Color_id : 2
             * SN : L50717F031000100202001
             * store : 16
             * skuId : 164020
             */

            private List<ColorEntity> Color;

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setStore(int store) {
                this.store = store;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setSize(List<SizeEntity> Size) {
                this.Size = Size;
            }

            public void setColor(List<ColorEntity> Color) {
                this.Color = Color;
            }

            public String getItem() {
                return item;
            }

            public double getPrice() {
                return price;
            }

            public int getId() {
                return id;
            }

            public int getStore() {
                return store;
            }

            public int getStatus() {
                return status;
            }

            public List<SizeEntity> getSize() {
                return Size;
            }

            public List<ColorEntity> getColor() {
                return Color;
            }

            public static class SizeEntity {
                private int Size_id;
                private double price;
                private String Size;
                private String Color;
                private int Color_id;
                private String SN;
                private int store;
                private int flashStore;
                private double collagePrice;
                private int stock;
                private long skuId;

                public int getFlashStore() {
                    return flashStore;
                }

                public void setFlashStore(int flashStore) {
                    this.flashStore = flashStore;
                }

                public double getCollagePrice() {
                    return collagePrice;
                }

                public void setCollagePrice(double collagePrice) {
                    this.collagePrice = collagePrice;
                }

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public void setSize_id(int Size_id) {
                    this.Size_id = Size_id;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public void setSize(String Size) {
                    this.Size = Size;
                }

                public void setColor(String Color) {
                    this.Color = Color;
                }

                public void setColor_id(int Color_id) {
                    this.Color_id = Color_id;
                }

                public void setSN(String SN) {
                    this.SN = SN;
                }

                public void setStore(int store) {
                    this.store = store;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public int getSize_id() {
                    return Size_id;
                }

                public double getPrice() {
                    return price;
                }

                public String getSize() {
                    return Size;
                }

                public String getColor() {
                    return Color;
                }

                public int getColor_id() {
                    return Color_id;
                }

                public String getSN() {
                    return SN;
                }

                public int getStore() {
                    return store;
                }

                public long getSkuId() {
                    return skuId;
                }
            }

            public static class ColorEntity {
                private int Size_id;
                private double price;
                private String Size;
                private String Color;
                private int Color_id;
                private String SN;
                private int store;
                private int flashStore;
                private double collagePrice;
                private int stock;
                private long skuId;

                public int getFlashStore() {
                    return flashStore;
                }

                public void setFlashStore(int flashStore) {
                    this.flashStore = flashStore;
                }

                public double getCollagePrice() {
                    return collagePrice;
                }

                public void setCollagePrice(double collagePrice) {
                    this.collagePrice = collagePrice;
                }

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public void setSize_id(int Size_id) {
                    this.Size_id = Size_id;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public void setSize(String Size) {
                    this.Size = Size;
                }

                public void setColor(String Color) {
                    this.Color = Color;
                }

                public void setColor_id(int Color_id) {
                    this.Color_id = Color_id;
                }

                public void setSN(String SN) {
                    this.SN = SN;
                }

                public void setStore(int store) {
                    this.store = store;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public int getSize_id() {
                    return Size_id;
                }

                public double getPrice() {
                    return price;
                }

                public String getSize() {
                    return Size;
                }

                public String getColor() {
                    return Color;
                }

                public int getColor_id() {
                    return Color_id;
                }

                public String getSN() {
                    return SN;
                }

                public int getStore() {
                    return store;
                }

                public long getSkuId() {
                    return skuId;
                }
            }
        }
    }
}
