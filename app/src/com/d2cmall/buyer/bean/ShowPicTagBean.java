package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 图片标签
 * Author: hrb
 * Date: 2016/07/19 17:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ShowPicTagBean extends BaseBean {

    /**
     * item : {"code":1468992997,"picUrl":"http://img.d2c.cn/app/f/16/07/07/d00172bf4d019258b4256cb7f1fc2df5","tagName":"asdf","productId":125966,"productPrice":1,"tagX":"50","tagY":"50"}
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
         * code : 1468992997
         * picUrl : http://img.d2c.cn/app/f/16/07/07/d00172bf4d019258b4256cb7f1fc2df5
         * tagName : asdf
         * productId : 125966
         * productPrice : 1.0
         * tagX : 50
         * tagY : 50
         */

        private ItemBean item;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public static class ItemBean {
            private String code;
            private String picUrl;
            private String tagName;
            private int productId;
            private double productPrice;
            private String tagX;
            private String tagY;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public String getTagX() {
                return tagX;
            }

            public void setTagX(String tagX) {
                this.tagX = tagX;
            }

            public String getTagY() {
                return tagY;
            }

            public void setTagY(String tagY) {
                this.tagY = tagY;
            }
        }
    }
}
