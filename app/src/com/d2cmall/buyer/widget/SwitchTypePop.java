package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 摄像头打开类型pop
 * Author: hrb
 * Date: 2016/08/04 15:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SwitchTypePop implements TransparentPop.InvokeListener {
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_add_tag)
    TextView tvAddTag;
    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.line2)
    View line2;
    private TransparentPop mPop;
    private Context mContext;
    private View rootView;
    private boolean isDetail;
    private boolean canAddTag;
    private boolean isVideo;
    public SwitchTypePop(Context context, boolean isDetail, boolean canAddTag,boolean isVideo) {
        this.mContext = context;
        this.isDetail = isDetail;
        this.canAddTag = canAddTag;
        this.isVideo = isVideo;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.switch_type_pop, new LinearLayout(mContext), false);
        ButterKnife.bind(this, rootView);
        if (isDetail || !canAddTag) {
            line2.setVisibility(View.GONE);
            tvAddTag.setVisibility(View.GONE);
        }
        if(isVideo){
            tvSave.setText("保存视频");
            tvShare.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
        }
        mPop = new TransparentPop(mContext, this);
        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();
    }

    private void initListener() {
        tvSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) {
                    back.clickBack(1);
                }
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) {
                    back.clickBack(2);
                }
            }
        });

        tvAddTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) {
                    back.clickBack(3);
                }
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
        back=null;
    }

    public void setShareGone() {
        tvShare.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
    }


    public interface ClickBack {
        void clickBack(int type);
    }

    private ClickBack back;

    public void setClickBack(ClickBack clickBack) {
        this.back = clickBack;
    }

}
