package com.d2cmall.buyer.Decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/21 18:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MessageDecoration extends RecyclerView.ItemDecoration {

    private int mLeft;
    private int mTop;
    private int mRight;
    private Paint offerPaint;
    private Paint textPaint;

    public MessageDecoration(int offerColor,int textColor){
        offerPaint=new Paint();
        offerPaint.setColor(offerColor);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(ScreenUtil.dip2px(12));//px单位
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        mLeft=ScreenUtil.dip2px(16);
        mRight=ScreenUtil.dip2px(16);
        mTop=ScreenUtil.dip2px(36)+(int) Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left= mLeft;
        outRect.right= mRight;
        outRect.bottom=0;
        outRect.top = mTop;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount=parent.getChildCount();
        int left=parent.getPaddingLeft();
        int right=parent.getWidth()-parent.getPaddingRight();
        for (int i=0;i<childCount;i++){
            View view=parent.getChildAt(i);
            int top=view.getTop();
            int bottom=view.getBottom();
            c.drawRect(left,top-mTop,right,top,offerPaint);
            c.drawRect(left,top,left+mLeft,bottom,offerPaint);
            c.drawRect(right-mRight,top,right,bottom,offerPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount=parent.getChildCount();
        for (int i=0;i<childCount;i++){
            View view=parent.getChildAt(i);
            String time= (String) view.getTag(R.id.item_time);
            if (time==null){
                return;
            }
            time= DateUtil.getFriendlyTime6(time);
            Rect rect=new Rect();
            textPaint.getTextBounds(time,0,time.length(),rect);
            int top=view.getTop();
            c.drawText(time,(parent.getWidth()-rect.width())/2,top-mTop+ScreenUtil.dip2px(24)+rect.height(),textPaint);
        }
    }
}
