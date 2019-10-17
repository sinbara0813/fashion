package com.d2cmall.buyer.listener;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by rookie on 2017/9/21.
 */

public class Clickable extends ClickableSpan implements View.OnClickListener {
    private final View.OnClickListener mListener;

    public Clickable(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v);
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);    //去除超链接的下划线
    }
}