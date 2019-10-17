package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/1/23.
 * Description : UserBehaviorEventApi
 */

public class UserBehaviorEventApi extends BaseApi {

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 事件名称
     */
    private String event;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 事件备注
     */
    private String remark;

    @Override
    protected String getPath() {
        return Constants.POST_BEHAVIOR_EVENT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
