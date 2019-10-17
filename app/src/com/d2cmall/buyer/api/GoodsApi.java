package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.Util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.TreeMap;

public class GoodsApi extends BaseApi {

    private Integer p;
    private Integer pageSize;
    private String keywords;
    private String o;
    private String preParam;
    private Integer min;
    private Integer max;
    private String designers;
    private String appParams;
    private String productSellType;
    private Integer topId;//顶级
    private String categoryIds;//二级
    private Boolean hasPromotion;//促销
    private Integer subscribe;//试衣
    private Boolean isAfter;
    //筛选的活动id
    private long promotionId;

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }
    public boolean isAfter() {
        return isAfter;
    }

    public void setAfter(boolean after) {
        isAfter = after;
    }

    public void setCategoryId(String categoryIds) {
        this.categoryIds = categoryIds;
    }


    public void setHasPromotion(Boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public void setO(String o) {
        this.o = o;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    protected String getPath() {
        return Constants.PRODUCTS_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
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
}
