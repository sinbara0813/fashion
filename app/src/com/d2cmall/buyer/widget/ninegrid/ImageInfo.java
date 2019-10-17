package com.d2cmall.buyer.widget.ninegrid;

import java.io.Serializable;

public class ImageInfo implements Serializable {
    public String singleUrl;
    public String fourUrl;
    public String thumbnailUrl;
    public String bigImageUrl;
    public int imageViewHeight;
    public int imageViewWidth;
    public int imageViewX;
    public int imageViewY;
    public boolean publishPreview = false;
    public boolean isProduct;//是否是商品详情页图片
    public String productUrl; //商品地址

    public String getSingleUrl() {
        return singleUrl;
    }

    public void setSingleUrl(String singleUrl) {
        this.singleUrl = singleUrl;
    }

    public String getFourUrl() {
        return fourUrl;
    }

    public void setFourUrl(String fourUrl) {
        this.fourUrl = fourUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public int getImageViewHeight() {
        return imageViewHeight;
    }

    public void setImageViewHeight(int imageViewHeight) {
        this.imageViewHeight = imageViewHeight;
    }

    public int getImageViewWidth() {
        return imageViewWidth;
    }

    public void setImageViewWidth(int imageViewWidth) {
        this.imageViewWidth = imageViewWidth;
    }

    public int getImageViewX() {
        return imageViewX;
    }

    public void setImageViewX(int imageViewX) {
        this.imageViewX = imageViewX;
    }

    public int getImageViewY() {
        return imageViewY;
    }

    public void setImageViewY(int imageViewY) {
        this.imageViewY = imageViewY;
    }

    public boolean isPublishPreview() {
        return publishPreview;
    }

    public void setPublishPreview(boolean publishPreview) {
        this.publishPreview = publishPreview;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "imageViewY=" + imageViewY +
                ", imageViewX=" + imageViewX +
                ", imageViewWidth=" + imageViewWidth +
                ", imageViewHeight=" + imageViewHeight +
                ", bigImageUrl='" + bigImageUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
