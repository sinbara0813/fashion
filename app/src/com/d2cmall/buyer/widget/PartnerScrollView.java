package com.d2cmall.buyer.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by rookie on 2017/12/11.
 */

public class PartnerScrollView extends ScrollView {


    private ScrollViewListener mScrollViewListener = null;
    //------尾部收缩属性end--------

    public PartnerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //初始化
    private void init() {
        setFadingEdgeLength(0);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    /***
     * 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */
    @Override
    protected void onFinishInflate() {
        init();
        super.onFinishInflate();
        //初始化


    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        mScrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged(PartnerScrollView observableScrollView, int x, int y, int oldx, int oldy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }






}
