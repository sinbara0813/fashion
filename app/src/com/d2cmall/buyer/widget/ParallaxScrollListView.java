package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListView;

import com.d2cmall.buyer.R;

public class ParallaxScrollListView extends ListView {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;
    private View mImageView;
    private int mDrawableMaxHeight = -1;
    private int mImageViewHeight = -1;
    private OnTouchEventListener touchListener = new OnTouchEventListener() {
        @Override
        public void onTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mImageViewHeight - 1 < mImageView.getHeight()) {
                    ResetAnimimation animation = new ResetAnimimation(
                            mImageView, mImageViewHeight);
                    animation.setDuration(300);
                    mImageView.startAnimation(animation);
                }
            }
        }
    };
    private int mDefaultImageViewHeight = 0;
    private OnOverScaleListener onOverScaleListener;
    private OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (mImageView.getHeight() <= mDrawableMaxHeight && isTouchEvent) {
                if (deltaY < 0) {
                    if (mImageView.getHeight() - deltaY / 2 >= mImageViewHeight) {
                        int height = mImageView.getHeight() - deltaY / 2 < mDrawableMaxHeight ?
                                mImageView.getHeight() - deltaY / 2 : mDrawableMaxHeight;
                        mImageView.getLayoutParams().height = height;
                        mImageView.requestLayout();
                        if (onOverScaleListener != null) {
                            onOverScaleListener.overScale((float) height / mImageViewHeight);
                        }
                    }
                } else {
                    if (mImageView.getHeight() > mImageViewHeight) {
                        mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY > mImageViewHeight ?
                                mImageView.getHeight() - deltaY : mImageViewHeight;
                        mImageView.requestLayout();
                        return true;
                    }
                }
            }
            return false;
        }
    };
    private GestureDetector mGestureDetector;
    private View firstHeardView;
    private boolean b;

    public ParallaxScrollListView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ParallaxScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxScrollListView(Context context) {
        super(context);
        init(context);
    }

    public void setOnOverScaleListener(OnOverScaleListener onOverScaleListener) {
        this.onOverScaleListener = onOverScaleListener;
    }

    public void init(Context context) {
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        mDefaultImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (firstHeardView != null) {
            if (firstHeardView.getTop() < getPaddingTop() && mImageView.getHeight() > mImageViewHeight) {
                int newHeight = Math.max(mImageView.getHeight() - (getPaddingTop() - firstHeardView.getTop()), mImageViewHeight);
                firstHeardView.layout(firstHeardView.getLeft(), 0, firstHeardView.getRight(), firstHeardView.getHeight());
                mImageView.getLayoutParams().height = newHeight;
                mImageView.requestLayout();
                if (onOverScaleListener != null) {
                    onOverScaleListener.overScale((float) newHeight / mImageViewHeight);
                }
            }
        }
    }

    @Override
    public void addHeaderView(View v) {
        if (firstHeardView == null) {
            firstHeardView = v;
        }
        super.addHeaderView(v);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        scrollByListener.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);

        return super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        touchListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }


    public void setParallaxImageView(View iv) {
        mImageView = iv;
        this.mDefaultImageViewHeight = mDefaultImageViewHeight;
    }

    public void setParallaxImageView(View iv, int mDefaultImageViewHeight) {
        mImageView = iv;
        this.mDefaultImageViewHeight = mDefaultImageViewHeight;
    }

    public void setViewsBounds(double zoomRatio) {
        if (mImageViewHeight == -1) {
            mImageViewHeight = mImageView.getHeight();
            if (mImageViewHeight <= 0) {
                mImageViewHeight = mDefaultImageViewHeight;
            }
            mDrawableMaxHeight =
                    (int) (mImageViewHeight * (zoomRatio > 1 ?
                            zoomRatio : 1));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onInterceptTouchEvent(event) && b;
    }

    private interface OnOverScrollByListener {
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    public interface OnOverScaleListener {
        public void overScale(float scale);
    }


    private interface OnTouchEventListener {
        public void onTouchEvent(MotionEvent ev);
    }

    public class ResetAnimimation extends Animation {
        int targetHeight;
        int originalHeight;
        int extraHeight;
        View mView;

        protected ResetAnimimation(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            if (onOverScaleListener != null) {
                onOverScaleListener.overScale((float) newHeight / mImageViewHeight);
            }
            mView.requestLayout();
        }
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                b = false;
                return b;
            }
            b = true;
            return b;
        }
    }
}
