package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.VideoRecordActivity;
import com.d2cmall.buyer.activity.WardrobeItemDetialActivity;
import com.d2cmall.buyer.util.FastBlur;
import com.d2cmall.buyer.util.Util;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/29 16:25
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionBottomPop implements TransparentPop.InvokeListener {

    @Bind(R.id.add_costume)
    ImageView addCostume;
    @Bind(R.id.show_match)
    ImageView showMatch;
    @Bind(R.id.cancel)
    ImageView cancel;

    private View rootView;
    private TransparentPop mPop;
    private Activity context;
    private boolean isFirst;

    public FashionBottomPop(Activity context) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_fashion_bottom, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        mPop = new TransparentPop(context, this);
        mPop.getParent().setBackgroundResource(R.color.trans_66_color_white);
        mPop.setFocusable(false);
        mPop.setBackgroundDrawable(new BitmapDrawable(context.getResources(),
                FastBlur.getBlurBackgroundDrawer(context)));
        //mPop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
        mPop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
    }

    @OnClick({R.id.add_costume, R.id.show_match, R.id.cancel})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.add_costume:
                if (Util.loginChecked(context, Constants.Login.EXPLORE_CAMERA_LOGIN)) {
                    Intent intent = new Intent(context, WardrobeItemDetialActivity.class);
                    intent.putExtra("action","publish");
                    context.startActivity(intent);
                    //showAlbum();
                }
                dismiss();
                break;
            case R.id.show_match:
                if (Util.loginChecked(context, Constants.Login.EXPLORE_CAMERA_LOGIN)) {
                    Intent intent1 = new Intent(context, VideoRecordActivity.class);
                    intent1.putExtra("channel","wardrobe");
                    context.startActivity(intent1);
                    //showAlbum();
                }
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public void show(View view) {
        mPop.show(view);
    }

    private Animator createAnimator(View view, int duction, float... offer) {
        return ObjectAnimator.ofFloat(view, "translationY", offer).setDuration(duction);
    }

    public boolean isShow() {
        if (mPop != null) {
            return mPop.isShowing();
        }
        return false;
    }

    public void dismiss() {
        mPop.dismiss(true);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isFirst) {
                    int height = rootView.getHeight();
                    if (height > 0) {
                        isFirst = true;
                        int startY = height;
                        List<Animator> animators = new ArrayList<>();

                        animators.add(createAnimator(addCostume, 300, startY, 0));
                        animators.add(createAnimator(showMatch, 400, startY, 0));
                        AnimatorSet resultSet = new AnimatorSet();
                        resultSet.playTogether(animators);
                        resultSet.setInterpolator(new OvershootInterpolator());
                        resultSet.start();
                    }
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }
}
