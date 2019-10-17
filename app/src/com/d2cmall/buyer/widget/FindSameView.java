package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.d2cmall.buyer.listener.FindSameClickListener;
import com.d2cmall.buyer.util.ScreenUtil;

import static com.d2cmall.buyer.util.ScreenUtil.sp2px;

/**
 * Created by rookie on 2018/3/16.
 */

public class FindSameView extends View {

    private View mParentView;

    private int mScreenWidth, parentViewWidth, distanceLeft;

    private int mScreenHeight, parentViewHeight, distanceTop,statusBarHeight;

    private RectF mBgRect,itemRect;

    private Paint mPaint;
    private Path mPath;

    private int mBackgroundColor = 0x00FFFFFF;

    private int mRectBackgroundColor = 0x00FFFFFF;

    private FindSameClickListener findSameClickListener;

    public FindSameView(Context context, Window window, View parentView) {
        super(context);
        mParentView = parentView;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        statusBarHeight= ScreenUtil.getStatusBarHeight(context);
        mBgRect = new RectF(0, 0, mScreenWidth, mScreenHeight);
        addView(window);
        initView();
    }

    public void setFindSameClickListener(FindSameClickListener findSameClickListener){
        this.findSameClickListener=findSameClickListener;
    }

    public void show(){
        invalidate();
    }

    private void initView() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(sp2px(14));
        parentViewWidth = mParentView.getMeasuredWidth();
        parentViewHeight = mParentView.getMeasuredHeight();
        int[] location = new int[2];
        mParentView.getLocationOnScreen(location);
        distanceLeft = location[0];
        distanceTop = location[1];
        itemRect=new RectF(distanceLeft, distanceTop-statusBarHeight, distanceLeft + parentViewWidth, distanceTop + parentViewHeight-statusBarHeight);

    }

    private void addView(Window window) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.addContentView(this, params);
    }

    private void removeView() {
        ViewGroup vg = (ViewGroup) this.getParent();
        if (vg != null) {
            vg.removeView(this);
        }
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    public void setReactBackgroundColor(int color) {
        mRectBackgroundColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(mBgRect, mPaint);

        canvas.save();
        mPaint.setColor(mRectBackgroundColor);
        canvas.drawRect(itemRect, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                if(findSameClickListener!=null&&isPointInRect(new PointF(event.getX(), event.getY()),itemRect)){
                    findSameClickListener.findSame();
                }
                removeView();
                return true;
        }
        return true;
    }


    private boolean isPointInRect(PointF pointF, RectF targetRect) {
        if (pointF.x < targetRect.left) {
            return false;
        }
        if (pointF.x > targetRect.right) {
            return false;
        }
        if (pointF.y < targetRect.top) {
            return false;
        }
        if (pointF.y > targetRect.bottom) {
            return false;
        }
        return true;
    }
}
