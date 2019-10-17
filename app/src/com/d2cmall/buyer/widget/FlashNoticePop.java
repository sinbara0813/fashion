package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 14:17
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashNoticePop implements TransparentPop.InvokeListener {

    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.sure_btn)
    TextView sureBtn;
    @Bind(R.id.close_iv)
    ImageView closeIv;

    private View rootView;
    private TransparentPop pop;

    public FlashNoticePop(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_flash_notice, null);
        ButterKnife.bind(this,rootView);
        String string="活动开始前3分钟将会\n通过手机号提醒你";
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F23365"));
        spannableString.setSpan(colorSpan, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        contentTv.setText(spannableString);
        pop = new TransparentPop(context,false,this);
        pop.setBackGroundResource(R.color.trans_40_color_black);
        pop.setFocusable(false);

/*      RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.1f);
        spannableString.setSpan(sizeSpan, 9, 14, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.4f);
        spannableString.setSpan(sizeSpan1, 14, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan  = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan, 14, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 3, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);*/
    }

    @OnClick({R.id.sure_btn,R.id.close_iv})
    public void click(View view){
        if (pop!=null){
            pop.dismiss(false);
        }
    }

    public void show(View view) {
        pop.show(view, false);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }
}
