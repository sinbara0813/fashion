package com.d2cmall.buyer.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/2/1 17:24
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RoundBgSpan extends ReplacementSpan {

    public RoundBgSpan() {
        super();
    }
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(ScreenUtil.dip2px(10));
        return ((int)paint.measureText(text, start, end)+ScreenUtil.dip2px(9));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color1 = paint.getColor();
        paint.setColor(Color.parseColor("#fff23365"));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(x, top+ScreenUtil.dip2px(3), x + ((int) paint.measureText(text, start, end)+ScreenUtil.dip2px(8)), bottom-ScreenUtil.dip2px(3)), 8, 8, paint);
        paint.setColor(Color.parseColor("#fff23365"));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(ScreenUtil.dip2px(10));
        canvas.drawText(text, start, end, x+ScreenUtil.dip2px(4), y-ScreenUtil.dip2px(1), paint);
        paint.setColor(color1);
    }
}
