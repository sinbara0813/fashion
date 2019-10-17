package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

public class ProductWebDetailBean extends BaseBean {

    /**
     * productData : {"id":123898,"sizetable":{},"desc":"<img src=\"http://img.d2c.cn/model/1603/8e07f62297879d12cb9afa36f5003a99\" alt=\"\" /><img src=\"http://img.d2c.cn/model/1603/d2ec41e75e9454394511dd1b9e25e743\" alt=\"\" /><img src=\"http://img.d2c.cn/model/1603/63fd4b75bf51b779d1541f779220e40e\" alt=\"\" /><img src=\"http://img.d2c.cn/model/1603/7c050f38cfe93aec27d0a477b74e113a\" alt=\"\" /><img src=\"http://img.d2c.cn/model/1603/81e572ab0aedab3e93956c231c165da3\" alt=\"\" /><img src=\"http://img.d2c.cn/model/1603/55f63f95c9c7ea64c57e40eb395c24bb\" alt=\"\" />"}
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
         * id : 123898
         * sizetable : {}
         * desc : <img src="http://img.d2c.cn/model/1603/8e07f62297879d12cb9afa36f5003a99" alt="" /><img src="http://img.d2c.cn/model/1603/d2ec41e75e9454394511dd1b9e25e743" alt="" /><img src="http://img.d2c.cn/model/1603/63fd4b75bf51b779d1541f779220e40e" alt="" /><img src="http://img.d2c.cn/model/1603/7c050f38cfe93aec27d0a477b74e113a" alt="" /><img src="http://img.d2c.cn/model/1603/81e572ab0aedab3e93956c231c165da3" alt="" /><img src="http://img.d2c.cn/model/1603/55f63f95c9c7ea64c57e40eb395c24bb" alt="" />
         */

        private ProductDataEntity productData;

        public void setProductData(ProductDataEntity productData) {
            this.productData = productData;
        }

        public ProductDataEntity getProductData() {
            return productData;
        }

        public static class ProductDataEntity {
            private int id;
            private String desc;

            public void setId(int id) {
                this.id = id;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getId() {
                return id;
            }

            public String getDesc() {
                return desc;
            }
        }
    }
}
