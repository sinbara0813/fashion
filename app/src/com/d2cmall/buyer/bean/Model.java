package com.d2cmall.buyer.bean;

public class Model {

    //for test
    private int iconRes;
    private String name;
    private String price;

    public Model( int iconRes, String name,String price) {
        this.iconRes = iconRes;
        this.name = name;
        this.price = price;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
