package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/8/16.
 * Description : JsFuncBean
 */

public class JsFuncBean extends BaseBean {

    /**
     * handlefunc : w_notice_open
     * func : cessback
     * type : page
     */

    private String handlefunc;
    private String func;
    private String type;

    public String getHandlefunc() {
        return handlefunc;
    }

    public void setHandlefunc(String handlefunc) {
        this.handlefunc = handlefunc;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
