package com.d2cmall.buyer.bean;

import java.util.HashMap;
import java.util.Map;

public class GlobalTypeBean {

    int type;
    String stringValue;
    int intValue;
    Map<String,Object> values;

    public GlobalTypeBean(int type) {
        this.type = type;
    }

    public GlobalTypeBean(int type, int intValue) {
        this.type = type;
        this.intValue = intValue;
    }

    public GlobalTypeBean(int type, String stringValue) {
        this.type = type;
        this.stringValue = stringValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStringValue(String videoUrl) {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void putValue(String key,Object value){
        if (values==null){
            values=new HashMap<>();
        }
        values.put(key,value);
    }

    public Object getValue(String key){
        if (values==null||!values.containsKey(key)){
            return null;
        }
        return values.get(key);
    }
}
