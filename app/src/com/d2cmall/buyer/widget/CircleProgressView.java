package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.d2cmall.buyer.R;

/**
 * Created by libo on 2017/11/23.
 */

public class CircleProgressView extends View{
    private Paint progressPaint;
    private Paint maxPaint,minPaint;
    private int curAngle;
    private int startAngle;
    private int progressColor;
    private int maxRadius,minRadius;
    private int strokeWidth;
    private long maxTime;
    private long delayMillis;
    private long lastTime;
    private long firstTime;
    private boolean isDelayVisible;
    private boolean isStart;

    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        maxRadius=array.getInt(R.styleable.CircleProgressView_max_radius,45);
        minRadius=array.getInt(R.styleable.CircleProgressView_min_radius,16);
        strokeWidth=array.getInt(R.styleable.CircleProgressView_stroke_width,4);
        progressColor = array.getColor(R.styleable.CircleProgressView_progress_color,getResources().getColor(R.color.color_green4));
        array.recycle();

        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(dp2px(strokeWidth));

        maxPaint = new Paint();
        maxPaint.setAntiAlias(true);
        maxPaint.setColor(getResources().getColor(R.color.gray_color));

        minPaint = new Paint();
        minPaint.setAntiAlias(true);
        minPaint.setColor(Color.WHITE);

        //起始角度
        startAngle = -90;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long curTime = System.currentTimeMillis();

        //画圆弧
        if (!isStart){
            canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,dp2px(maxRadius*2/3),maxPaint);
            canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,dp2px(minRadius*3/2),minPaint);
        }else {
            if (lastTime==0|| curTime-lastTime>=delayMillis){
                isDelayVisible=true;
                if (lastTime==0){
                    firstTime=System.currentTimeMillis();
                    lastTime=firstTime;
                }else {
                    lastTime=System.currentTimeMillis();
                }
            }
            if (isDelayVisible){
                curAngle=(int)((System.currentTimeMillis()-firstTime)*360/maxTime);
                canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,dp2px(maxRadius),maxPaint);
                canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,dp2px(minRadius),minPaint);
                RectF rectf = new RectF(dp2px(strokeWidth/2),dp2px(strokeWidth/2),dp2px(maxRadius*2-strokeWidth/2),dp2px(maxRadius*2-strokeWidth/2));
                canvas.drawArc(rectf,startAngle,curAngle,false,progressPaint);
            }
            invalidate();
        }
    }

    public void start(){
        isStart=true;
        invalidate();
    }

    public void stop(){
        isStart=false;
        lastTime=0;
        firstTime=0;
        invalidate();
    }

    public void setMaxTime(long maxTime){
        this.maxTime=maxTime;
        this.delayMillis=(long)(maxTime/90f);
    }

    private int dp2px(float dp){
        return (int) (getContext().getResources().getDisplayMetrics().density*dp + 0.5);
    }
}
