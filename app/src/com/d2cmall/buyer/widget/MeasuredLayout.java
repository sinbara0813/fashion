package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class MeasuredLayout extends LinearLayout {

    private int largestHeight;

    private OnKeyboardHideListener onKeyboardHideListener;
    private int heightPrevious;
    private int heightNow;
    private View mChildOfContent;
    private int usableHeightPrevious;

    public MeasuredLayout(Context context, View view){
        super(context);
        addView(view);
    }

    public MeasuredLayout(Context context, AttributeSet attrs, int layoutId) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(layoutId, this);
        mChildOfContent=getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 键盘弹出
                if (onKeyboardHideListener != null) {
                    onKeyboardHideListener.onKeyboardHide(false);
                }
            } else {
                // 键盘收起
                if (onKeyboardHideListener != null) {
                    onKeyboardHideListener.onKeyboardHide(true);
                }
            }
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //依赖adjustResize属性
        /*final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        largestHeight = Math.max(largestHeight, getHeight());

        if (largestHeight > (proposedheight+200)) {//确保是软件盘弹出
            // Keyboard is shown
            if (onKeyboardHideListener != null) {
                onKeyboardHideListener.onKeyboardHide(false);
            }
        } else {
            // Keyboard is hidden
            if (onKeyboardHideListener != null) {
                onKeyboardHideListener.onKeyboardHide(true);
            }
        }*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface OnKeyboardHideListener {
        void onKeyboardHide(boolean hide);
    }

    public void setOnKeyboardHideListener(OnKeyboardHideListener onKeyboardHideListener) {
        this.onKeyboardHideListener = onKeyboardHideListener;
    }
}
