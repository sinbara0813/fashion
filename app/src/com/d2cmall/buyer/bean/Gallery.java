package com.d2cmall.buyer.bean;

public class Gallery implements Identifiable {

    private static final long serialVersionUID = 5579102958334784832L;
    private long id;
    private String name;
    private int photoCount;
    private String path;
    private boolean isSelected;

    public Gallery(long id, String name, int photoCount, String path) {
        super();
        this.id = id;
        this.name = name;
        this.photoCount = photoCount;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}