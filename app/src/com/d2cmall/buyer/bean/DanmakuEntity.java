package com.d2cmall.buyer.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DanmakuEntity implements Parcelable {

    private String avatar;
    private String name;
    private String userId;
    private int role;
    private int type;
    private String text;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.name);
        dest.writeString(this.userId);
        dest.writeInt(this.role);
        dest.writeInt(this.type);
        dest.writeString(this.text);
    }

    public DanmakuEntity() {
    }

    protected DanmakuEntity(Parcel in) {
        this.avatar = in.readString();
        this.name = in.readString();
        this.userId = in.readString();
        this.role = in.readInt();
        this.type = in.readInt();
        this.text = in.readString();
    }

    public static final Creator<DanmakuEntity> CREATOR = new Creator<DanmakuEntity>() {
        @Override
        public DanmakuEntity createFromParcel(Parcel source) {
            return new DanmakuEntity(source);
        }

        @Override
        public DanmakuEntity[] newArray(int size) {
            return new DanmakuEntity[size];
        }
    };
}
