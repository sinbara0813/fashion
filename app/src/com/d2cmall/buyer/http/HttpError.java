package com.d2cmall.buyer.http;

import com.android.volley.VolleyError;
import com.d2cmall.buyer.bean.HttpErrorBean;

public class HttpError extends VolleyError {

    private int status;
    private HttpErrorBean httpErrorBean;

    public HttpError(HttpErrorBean httpErrorBean) {
        super(httpErrorBean.getMsg());
        this.httpErrorBean = httpErrorBean;
        this.status = httpErrorBean.getStatus();
    }

    public int getStatus() {
        return status;
    }

    public HttpErrorBean getErrorBean() {
        return httpErrorBean;
    }
}
