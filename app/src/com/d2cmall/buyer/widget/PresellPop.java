package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Author: PengHong
 * Date: 2016/11/24 11:17
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PresellPop implements TransparentPop.InvokeListener {


    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    @Bind(R.id.btn_sure)
    TextView btnSure;
    @Bind(R.id.toggleButton)
    ToggleButton toggleButton;

    private TransparentPop transparentPop;
    private Context context;
    private View preCellLayout;
    private boolean noNotice=true;

    public PresellPop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        preCellLayout = LayoutInflater.from(context).inflate(R.layout.layout_precell_pop, new LinearLayout(context), false);
        ButterKnife.bind(this, preCellLayout);
        transparentPop = new TransparentPop(context, this);
        transparentPop.setFocusable(false);
        initListener();
    }

    public void initListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (callBack != null) {
                    callBack.cancel();
                }
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.NO_NOTICE, noNotice);
                if (callBack != null) {
                    callBack.sure();
                }
            }
        });
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noNotice = !isChecked;
            }
        });
        preCellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setContent(String s) {
        if (Util.isEmpty(s)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(s);
        }
    }

    public void setContent(int id) {
        tvContent.setText(id);
    }


    public void show(View parent) {
        transparentPop.show(parent, false);
    }

    public void dismiss() {
        if (transparentPop != null) {
            transparentPop.dismiss(false);
        }
    }


    public boolean isShow() {
        return transparentPop.isShowing();
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        int width = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = Math.round(250 * dm.density);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, -2);
        v.addView(preCellLayout, lp);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    public CallBack callBack;

    public void setBack(CallBack callBack) {
        this.callBack = callBack;
    }


    public interface CallBack {
        void sure();

        void cancel();
    }
}
