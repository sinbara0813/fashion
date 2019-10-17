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
 * 礼物消息
 * Author: hrb
 * Date: 2017/01/03 10:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
@MessageTag(value = "D2C:PresentMessage", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class PresentMessage extends MessageContent {

    private String presentId;
    private String presentName;
    private String presentUrl;
    protected String extra;
    private int count;

    public PresentMessage(String presentId, String presentName, String presentUrl, int count) {
        this.presentId = presentId;
        this.presentName = presentName;
        this.presentUrl = presentUrl;
        this.count = count;
    }

    public PresentMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("presentId")) {
                this.setPresentId(jsonObj.optString("presentId"));
            }
            if (jsonObj.has("presentName")) {
                this.setPresentName(jsonObj.optString("presentName"));
            }
            if (jsonObj.has("presentUrl")) {
                this.setPresentUrl(jsonObj.optString("presentUrl"));
            }
            if (jsonObj.has("extra")) {
                this.setExtra(jsonObj.optString("extra"));
            }
            if (jsonObj.has("count")) {
                this.setCount(jsonObj.optInt("count"));
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
            if (!TextUtils.isEmpty(this.getPresentId())) {
                jsonObj.put("presentId", getPresentId());
            }

            if (!TextUtils.isEmpty(this.getPresentName())) {
                jsonObj.put("presentName", this.getPresentName());
            }

            if (!TextUtils.isEmpty(this.getPresentUrl())) {
                jsonObj.put("presentUrl", this.getPresentUrl());
            }

            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }

            jsonObj.put("count", this.getCount());

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

    public PresentMessage(Parcel in) {
        setPresentId(ParcelUtils.readFromParcel(in));
        setPresentName(ParcelUtils.readFromParcel(in));
        setPresentUrl(ParcelUtils.readFromParcel(in));
        setCount(ParcelUtils.readIntFromParcel(in));
        setExtra(ParcelUtils.readFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    public static final Creator<PresentMessage> CREATOR = new Creator<PresentMessage>() {

        @Override
        public PresentMessage createFromParcel(Parcel source) {
            return new PresentMessage(source);
        }

        @Override
        public PresentMessage[] newArray(int size) {
            return new PresentMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, getPresentId());
        ParcelUtils.writeToParcel(dest, getPresentName());
        ParcelUtils.writeToParcel(dest, getPresentUrl());
        ParcelUtils.writeToParcel(dest, getCount());
        ParcelUtils.writeToParcel(dest, getExtra());
        ParcelUtils.writeToParcel(dest, getUserInfo());
    }

    public String getPresentId() {
        return presentId;
    }

    public void setPresentId(String presentId) {
        this.presentId = presentId;
    }

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPresentUrl() {
        return presentUrl;
    }

    public void setPresentUrl(String presentUrl) {
        this.presentUrl = presentUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
