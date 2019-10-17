package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

public class CommendDetailBean extends BaseBean {

    /**
     * comment : {"id":155663,"name":"13336199696","nickname":"他","headPic":"/app/a/16/05/25/46c77b0abeec105af6c9d57c37ef2e93","createDate":"2016/06/03 10:15:39","title":"测试商品","content":"jetsjyjgjdgd","productImg":"/model/1605/9262ba09baa8b6eac0da014b0e195021","skuProperty":"颜色:格子, 尺码:M码","productScore":5,"packageScore":5,"deliverySpeedScore":5,"deliveryServiceScore":5,"replys":[]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private ProductCommentListBean.DataEntity.CommentsEntity.ListEntity comment;

        public void setComment(ProductCommentListBean.DataEntity.CommentsEntity.ListEntity comment) {
            this.comment = comment;
        }

        public ProductCommentListBean.DataEntity.CommentsEntity.ListEntity getComment() {
            return comment;
        }

    }
}
