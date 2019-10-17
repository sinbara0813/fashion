package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.GlobalTypeBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/7/4 9:26
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class VideoPop implements TransparentPop.InvokeListener {
    @Bind(R.id.upload_again)
    TextView uploadAgain;
    @Bind(R.id.delete)
    TextView delete;
    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    private View rootView;
    private TransparentPop mPop;


    public VideoPop(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_video_pop, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        mPop = new TransparentPop(context, this);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();
    }

    private void initListener() {
        uploadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAgain.setEnabled(false);
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.UPLOAD_AGAIN));
                dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.setEnabled(false);
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.UPLOAD_DELETE));
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(View parent) {
        mPop.show(parent);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }
}
