package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/6/21 13:22
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProgressView extends View {

    private Paint bgPaint;
    private Paint progressPaint;
    private String bgColor;
    private String progressColor;
    private int progress;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgColor="#32fd5562";
        progressColor="#fd5562";
        bgPaint=new Paint();
        bgPaint.setColor(Color.parseColor(bgColor));
        progressPaint=new Paint();
        progressPaint.setColor(Color.parseColor(progressColor));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();
        canvas.drawRect(0,0,width,height,bgPaint);
        int progressWidth=width*progress/100;
        canvas.drawRect(0,0,progressWidth,height,progressPaint);
    }

    public void setProgress(int progress){
        this.progress=progress;
        invalidate();
    }
}
