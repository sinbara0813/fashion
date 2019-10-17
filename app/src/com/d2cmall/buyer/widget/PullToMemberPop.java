package com.d2cmall.buyer.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.WebActivity;

import butterknife.Bind;

/**
 * 买手中心新手指引pop
 * Author: Blend
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class PullToMemberPop implements TransparentPop.InvokeListener {
    private  ImageView ivHand;
    private TextView tvDismiss;
    private Context context;
    private TransparentPop mPop;
    private View rootView;

    public PullToMemberPop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_pull_to_member, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.dismissFromOut();
        tvDismiss = (TextView) rootView.findViewById(R.id.tv_dismiss);
        ivHand = (ImageView) rootView.findViewById(R.id.iv_hand);
        tvDismiss.setOnClickListener(new View.OnClickListener() {
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
        showAnimation();
    }

    private void showAnimation() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 1);
        animation.setDuration(2000);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.INFINITE);
        AnticipateOvershootInterpolator interpolator = new AnticipateOvershootInterpolator();
        animation.setInterpolator(interpolator);
        ivHand.startAnimation(animation);
    }


    public void show(View parent) {
        mPop.show(parent, true);
    }

    public void dismiss() {
        if(ivHand!=null){
            ivHand.clearAnimation();
        }
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
