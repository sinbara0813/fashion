package com.d2cmall.buyer.api;

import android.content.Context;
import android.util.Log;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;

import java.lang.reflect.Field;
import java.util.TreeMap;

public abstract class BaseApi {

    public String interPath;
    public boolean isJsonContentType;

    public void setInterPath(String interPath) {
        this.interPath = interPath;
    }

    public enum Method {
        GET,
        POST,
    }

    protected abstract String getPath();

    public abstract Method requestMethod();

    public String getUrl() {
        return Constants.API_URL + getPath();
    }

    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<>();
        Class clazz = getClass();
        Field[] field = clazz.getDeclaredFields();
        try {
            for (Field f : field) {
                f.setAccessible(true);
                if (f.get(this) != null && !f.getName().equals("method")
                        && !f.getName().equals("serialVersionUID")) {
                    if (f.get(this).equals("")){
                        continue;
                    }
                    params.put(f.getName(), f.get(this));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return params;
    }

    public TreeMap<String, Object> handleParams(Context context, TreeMap<String, Object> params) {
        params.put("appVersion", Util.getPageVersionName(context));
        params.put("appTerminal", "APP.Buyer.Android");
        params.put("timestamp",System.currentTimeMillis());
        String sign=Util.formatUrlMap(params,true,false);
        String token = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.TOKEN, "");
        params.put("sign",Util.getMD5(sign+token).toLowerCase());
        return params;
    }

}
