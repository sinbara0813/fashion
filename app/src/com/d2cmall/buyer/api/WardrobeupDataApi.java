package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/11/27.
 * Description : WardrobeInsertApi
 */

public class WardrobeupDataApi extends BaseApi {
    private String pic ;//衣橱的图片
    private int categoryId  ;//品类ID
    private String price  ;//价格（我这边记的是字符串）
    private String season  ;//季节
    private String scene  ;//场景
    private String tags  ;//标签
    private String color  ;//颜色
    private int id;

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected String getPath() {
        return Constants.WARDROBE_UPDATE_ITEM;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
