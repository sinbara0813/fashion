package com.d2cmall.buyer.http;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * RawRequest
 * 自定义Volley请求类,默认GET
 * Author: Blend
 * Date: 16/4/14 10:53
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RawRequest extends Request<String> {

    private Map<String, String> mMap;
    private Listener<String> mListener;

    public RawRequest(String url, Map<String, String> map, Listener<String> listener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        super.setShouldCache(false);
        mListener = listener;
        mMap = map;

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, null);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
