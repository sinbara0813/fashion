package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.RefundReshipActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.util.Util;

/**
 * 买手中心新手指引pop
 * Author: Blend
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class PartnerGuidePop implements TransparentPop.InvokeListener {
    public TextView mTvOpen;
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private ImageView mivDismiss;

    public PartnerGuidePop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_partner_guide_pop, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.dismissFromOut();
        mTvOpen= (TextView) rootView.findViewById(R.id.tv_guide);
        mivDismiss= (ImageView) rootView.findViewById(R.id.iv_dismiss);
        mTvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                toWebActivity();
                //买手指南
            }
        });
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
    private void toWebActivity(){
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra("type",1);
        String url= Constants.SHARE_URL+"/page/maishouzhinan";
        intent.putExtra("url",url);
        intent.putExtra("isShareGone",true);
        context.startActivity(intent);
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
