package com.d2cmall.buyer.bean;

public class Size {
    private int width;
    private int height;

    public Size() {
        super();
    }

    /**
     * @param width
     * @param height
     */
    public Size(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
