package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 作者:Created by sinbara on 2019/1/14.
 * 邮箱:hrb940258169@163.com
 */

public class FocusBannerBean extends BaseBean {

    /**
     * data : {"next":false,"total":1,"previous":false,"subModule":{"parent":"SQUARE","sequence":2,"isDefault":0,"isCategory":0,"tPic":null,"color":"000000","webUrl":null,"id":105,"title":"热门","tbPic":null,"categoryId":null,"direction":"TOP"},"index":1,"pageSize":20,"version":6,"content":[{"sequence":0,"sectionValues":[{"sequence":0,"sectionDefId":535,"shortTitle":"热门","id":15825,"frontPic":"/2019/01/11/083227e176d6f45ac41cd356d6441c461b6250.png","url":"/product/199631","tags":[]}],"visible":0,"more":0,"id":1,"moduleId":105,"title":"热门banner","type":1,"moreUrl":null,"properties":{"top":0,"left":0,"bottom":10,"width":"750","right":0,"height":"290"}}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<FashionShowHeadBean.DataBean.ContentBean> content;

        public List<FashionShowHeadBean.DataBean.ContentBean> getContent() {
            return content;
        }

        public void setContent(List<FashionShowHeadBean.DataBean.ContentBean> content) {
            this.content = content;
        }

    }
}
