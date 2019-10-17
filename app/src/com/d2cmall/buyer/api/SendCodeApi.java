package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.Util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.TreeMap;

public class SendCodeApi extends BaseApi {

    private String type;
    private String source;
    private String mobile;
    private String nationCode;
    private String appParams;

    public void setType(String type) {
        this.type = type;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    @Override
    protected String getPath() {
        return Constants.SEND_CODE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<>();
        Class clazz = getClass();
        Field[] field = clazz.getDeclaredFields();
        try {
            for (Field f : field) {
                f.setAccessible(true);
                if (f.getName().equals("appParams")) {
                    String content = "mobile=" + mobile;
                    String sign = Util.getMD5(content + Constants.APP_SECRET);
                    String content1 = "mobile=" + mobile + "&sign=" + sign;
                    appParams = Base64.encode(content1.getBytes("UTF-8"));
                }
                if (f.get(this) != null) {
                    if (f.get(this).equals("")){
                        continue;
                    }
                    params.put(f.getName(), f.get(this));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return params;
    }


}
