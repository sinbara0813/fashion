package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/05 13:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveCouponItemView extends LinearLayout {

    private Context mContext;
    TextView priceTag;
    TextView acount;
    TextView useLimit;
    TextView leadCoupon;
    long id;

    public LiveCouponItemView(Context context) {
        this(context, null);
    }

    public LiveCouponItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.live_coupon_item, null);
        addView(root);
        priceTag = (TextView) findViewById(R.id.price_tag);
        acount = (TextView) findViewById(R.id.acount);
        useLimit = (TextView) findViewById(R.id.use_limit);
        leadCoupon = (TextView) findViewById(R.id.lead_coupon);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                leadCoupon();
            }
        });
    }

    private void leadCoupon() {
        if (!(mContext instanceof Activity) || Util.loginChecked((Activity) mContext, 0)) {
            SimpleApi api = new SimpleApi();
            api.setInterPath(String.format(Constants.LEAD_COUPON_URL, id));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Util.showToast(mContext, "领取成功!");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            });
        }
    }

    public void setData(long id, long price, String limit) {
        this.id = id;
        priceTag.setVisibility(VISIBLE);
        acount.setText(Util.getNumberFormat(price));
        useLimit.setText(limit);
    }

}
