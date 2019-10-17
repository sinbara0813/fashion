package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/4/10.
 * Description : StarRecyclerView用于明星风范,请求处理横向滑动事件,防止滑动时上级VP处理滑动事件
 */

public class StarRecyclerView extends RecyclerView {
    private float downX ;    //按下时 的X坐标
    private float downY ;    //按下时 的Y坐标
    public StarRecyclerView(Context context) {
        super(context);
    }

    public StarRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StarRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        //在触发时获取到起始坐标
        float x= ev.getX();
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //将按下时的坐标存储
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //获取到距离差
                float dx= x-downX;
                float dy = y-downY;
                    //通过距离差判断方向
                    int orientation = getOrientation(dx, dy);
                    switch (orientation) {
                        case 'r':                   //拦截横向的滑动,不让上级的控件处理
                            getParent().requestDisallowInterceptTouchEvent(true);
                           break;
                        case 'l':                    //拦截横向的滑动,不让上级的控件处理
                            getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        case 't':
                            getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                        case 'b':
                            getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx)>Math.abs(dy)){
            //X轴移动
            return dx>0?'r':'l';
        }else{
            //Y轴移动
            return dy>0?'b':'t';
        }
    }


}
