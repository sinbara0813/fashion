package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Fixme
 * Author: hrb
 * Date: 2016/10/21 10:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class AliasImageView extends ImageView {

    public AliasImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AliasImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        super.onDraw(canvas);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        drawable.setFilterBitmap(true);
        super.setImageDrawable(drawable);
    }
}
