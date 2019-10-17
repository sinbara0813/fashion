package com.d2cmall.buyer.bean;

import java.util.HashMap;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/13 13:33
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ActionBean {
    public int type;
    HashMap<String,Object> values;

    public ActionBean(int type){
        this.type=type;
    }

    public void put(String key, Object object){
        if (values==null){
            values=new HashMap<String, Object>();
        }
        values.put(key,object);
    }

    public Object get(String key){
        if (values==null){
            return null;
        }
        if (!hasKey(key)){
            return null;
        }
        return values.get(key);
    }

    public boolean hasKey(String key){
        if (values==null){
            return false;
        }
        return values.containsKey(key);
    }
}
