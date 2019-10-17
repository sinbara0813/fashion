package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.Util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.TreeMap;

import static com.d2cmall.buyer.Constants.SCREEN_PRODUCT_URL;

/**
 * Created by rookie on 2017/9/22.
 */

public class ScreenApi extends BaseApi {

    private String keywords;
    private String preParam;
    private String appParams;
    private Long designerId;
    private Long promotionId;
    private Long orderPromotionId;

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public void setOrderpromotionID(Long orderpromotionId) {
        this.orderPromotionId = orderpromotionId;
    }

    public void setDesignerId(long designerId) {
        this.designerId = designerId;
    }

    public void setKeyword(String keyword) {
        this.keywords = keyword;
        String content = "keywords=" + keyword;
        String sign = Util.getMD5(content + Constants.APP_SECRET);
        String content1 = "keywords=" + keyword + "&sign=" + sign;
        try {
            appParams = Base64.encode(content1.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<>();
        if (preParam != null) {
            String[] pre = preParam.split("&");
            for (int i = 0; i < pre.length; i++) {
                String s = pre[i];
                if (s.indexOf("=") > 0) {
                    int index = s.indexOf("=");
                    String key = s.substring(0, index);
                    String value = s.substring(index + 1, s.length());
                    params.put(key, value);
                }
            }
        }
        Class clazz = getClass();
        Field[] field = clazz.getDeclaredFields();
        try {
            for (Field f : field) {
                f.setAccessible(true);
                if (f.get(this) != null && !f.getName().equals("preParam")) {
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

    public void setPreParam(String pre) {
        preParam = pre;
    }

    @Override
    protected String getPath() {
        return SCREEN_PRODUCT_URL;
    }

    @Override
    public Method requestMethod() {
        return BaseApi.Method.GET;
    }
}
