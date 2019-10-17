package com.d2cmall.buyer.bean;

import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Web端消息
 * Author: hrb
 * Date: 2017/03/17 14:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
@MessageTag(value = "D2C:TxtMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class WebMessage extends MessageContent{

    private String userId;
    private String userName;
    private String userPic;
    private String content;
    private int type;

    public WebMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("userId")) {
                this.setUserId(jsonObj.optString("userId"));
            }
            if (jsonObj.has("userName")) {
                this.setUserName(jsonObj.optString("userName"));
            }
            if (jsonObj.has("userPic")) {
                this.setUserPic(jsonObj.optString("userPic"));
            }
            if (jsonObj.has("content")) {
                this.setContent(jsonObj.optString("content"));
            }
            if (jsonObj.has("type")){
                this.setType(jsonObj.optInt("type"));
            }
            if (jsonObj.has("user")) {
                setUserInfo(parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.getUserId())) {
                jsonObj.put("userId", getUserId());
            }

            if (!TextUtils.isEmpty(this.getUserName())) {
                jsonObj.put("userName", this.getUserName());
            }

            if (!TextUtils.isEmpty(this.getUserPic())) {
                jsonObj.put("userPic", this.getUserPic());
            }

            if (!TextUtils.isEmpty(this.getContent())) {
                jsonObj.put("content", this.getContent());
            }

            if (this.getType()!=0){
                jsonObj.put("type",this.getType());
            }

            if (getJSONUserInfo() != null)
                jsonObj.putOpt("user", getJSONUserInfo());

        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WebMessage> CREATOR = new Creator<WebMessage>() {

        @Override
        public WebMessage createFromParcel(Parcel source) {
            return new WebMessage(source);
        }

        @Override
        public WebMessage[] newArray(int size) {
            return new WebMessage[size];
        }
    };

    public WebMessage(Parcel in) {
        setUserId(ParcelUtils.readFromParcel(in));
        setUserName(ParcelUtils.readFromParcel(in));
        setUserPic(ParcelUtils.readFromParcel(in));
        setContent(ParcelUtils.readFromParcel(in));
        setType(ParcelUtils.readIntFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, getUserId());
        ParcelUtils.writeToParcel(dest, getUserName());
        ParcelUtils.writeToParcel(dest, getUserPic());
        ParcelUtils.writeToParcel(dest, getContent());
        ParcelUtils.writeToParcel(dest, getType());
        ParcelUtils.writeToParcel(dest, getUserInfo());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
