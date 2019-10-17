package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

/**
 * 品牌简介pop
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class BrandSummeryPop implements TransparentPop.InvokeListener {
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private ImageView mivDismiss;
    private TextView tvContent;
    private  String content;

    public BrandSummeryPop(Context context, String content) {
        this.context = context;
        this.content = content;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_brand_summary_pop, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);

        mPop.dismissFromOut();
        mivDismiss= (ImageView) rootView.findViewById(R.id.iv_dismiss);
        tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        tvContent.setText(Html.fromHtml(content));
        mivDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法拦截事件
            }
        });
    }

    public void show(View parent) {
        mPop.show(parent, true);
    }
    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }


    @Override
    public void releaseView(LinearLayout v) {

    }
    public void setDismissListener(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }
}
