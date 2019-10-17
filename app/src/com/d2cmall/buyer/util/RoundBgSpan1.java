package com.d2cmall.buyer.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;
import android.util.Log;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/2/1 17:24
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RoundBgSpan1 extends ReplacementSpan {

    public RoundBgSpan1() {
        super();
    }
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(ScreenUtil.dip2px(10));
        Log.d("han","size=="+((int)paint.measureText(text, start, end)+ScreenUtil.dip2px(9)));
        return ((int)paint.measureText(text, start, end)+ScreenUtil.dip2px(9));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        paint.setColor(Color.parseColor("#fff23365"));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(x, top+ScreenUtil.dip2px(4), x + ((int) paint.measureText(text, start, end)+ScreenUtil.dip2px(4)), bottom-ScreenUtil.dip2px(4)), ScreenUtil.dip2px(2), ScreenUtil.dip2px(2), paint);
        paint.setColor(Color.parseColor("#fff23365"));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(ScreenUtil.dip2px(10));
        paint.setTypeface(Typeface.DEFAULT);
        paint.setFakeBoldText(false);
        canvas.drawText(text, start, end, x+ScreenUtil.dip2px(2), y-ScreenUtil.dip2px(2), paint);
    }
}
