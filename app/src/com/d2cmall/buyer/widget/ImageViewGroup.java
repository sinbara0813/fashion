package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ImagePreviewActivity;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageViewGroup extends ViewGroup {
    private Context mContext;
    private static String TAG = ImageViewGroup.class.getSimpleName();
    private int lastScreen = 0;
    private int curScreen = 0;
    private Scroller mScroller = null;
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    private int mTouchState = TOUCH_STATE_REST;
    public static int SNAP_VELOCITY = 600;
    private int mTouchSlop = 0;
    private float mLastMotionY = 0;
    private VelocityTracker mVelocityTracker = null;
    private PageChangeListener mListener;
    private boolean isTop = true;
    private boolean isBottom;
    private int size;
    private int height;

    public ImageViewGroup(Context context) {
        this(context, null);
    }

    public ImageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.e(TAG, mScroller.getCurrX() + "======" + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        } else {
            if (curScreen == 0) {
                isTop = true;
            } else {
                isTop = false;
            }
            if (curScreen == size - 1 && mScroller.isFinished()) {
                isBottom = true;
            } else {
                isBottom = false;
            }
        }
        Log.i(TAG, "have done the scoller -----");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);
        //getParent().getParent().requestDisallowInterceptTouchEvent(true);
        final int action = ev.getAction();

        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }
        final float x = ev.getX();
        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent move");
                final int yDiff = (int) Math.abs(mLastMotionY - y);
                if (yDiff > mTouchSlop) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;

            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent down");
                mLastMotionY = y;
                Log.e(TAG, mScroller.isFinished() + "");
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent up or cancel");
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        Log.e(TAG, mTouchState + "====" + TOUCH_STATE_REST);
        return mTouchState != TOUCH_STATE_REST;
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                }

                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int detaY = (int) (mLastMotionY - y);
                if (isTop && detaY < 0) { //到顶向下滑

                } else if (isBottom && detaY > 0) { //到底向上滑

                } else {
                    scrollBy(0, detaY);
                }

                Log.e(TAG, "--- MotionEvent.ACTION_MOVE--> detaX is " + detaY);
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:

                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);

                int velocityY = (int) velocityTracker.getYVelocity();

                Log.e(TAG, "---velocityX---" + velocityY);

                if (velocityY > SNAP_VELOCITY && curScreen > 0) {
                    Log.e(TAG, "snap left");
                    snapToScreen(curScreen - 1);
                } else if (velocityY < -SNAP_VELOCITY && curScreen < (getChildCount() - 1)) {
                    Log.e(TAG, "snap right");
                    snapToScreen(curScreen + 1);
                } else {
                    snapToDestination();
                }

                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }

                mTouchState = TOUCH_STATE_REST;

                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }

        return true;
    }

    private void snapToDestination() {
        int destScreen = (getScrollY() + getHeight() / 2) / getHeight();
        snapToScreen(destScreen);
    }

    private void snapToScreen(int whichScreen) {
        if (whichScreen > getChildCount() - 1) {
            whichScreen = getChildCount() - 1;
        }
        if (whichScreen < 0) whichScreen = 0;
        if (curScreen != whichScreen) {
            lastScreen = curScreen;
            curScreen = whichScreen;
            if (mListener != null) {
                mListener.pageChange(lastScreen, curScreen);
            }
        }

        int dy = curScreen * getHeight() - getScrollY();
        height = getHeight();
        mScroller.startScroll(0, getScrollY(), 0, dy, Math.abs(dy) * 1);

        invalidate();
    }

    public void resetView() {
        scrollTo(0, curScreen * height);
    }

    private void init() {
        mScroller = new Scroller(mContext);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void addImages(final List<String> images) {
        if (images == null || images.size() == 0) return;
        removeAllViews();
        size = images.size();
        final Intent intent = new Intent(mContext, ImagePreviewActivity.class);
        final Bundle bundle = new Bundle();
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        List<String> imgList = images;
        if (imgList != null) {
            for (String picUrl : imgList) {
                ImageInfo info = new ImageInfo();
                info.setSingleUrl(Util.getD2cPicUrl(picUrl));//单张图
                info.setThumbnailUrl(Util.getD2cPicUrl(picUrl));//多张缩略图
                info.setBigImageUrl(Util.getD2cPicUrl(picUrl));//大图
                //info.setNeedDown(true);
                imageInfos.add(info);
            }
        }
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, finalI);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(0, 0);
                }
            });
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            UniversalImageLoader.displayImage(mContext,Util.getD2cPicUrl(images.get(i)), imageView, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
            addView(imageView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.i(TAG, "--- start onMeasure --");

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(getWidth(), getHeight());
        }
    }

    private int curPage = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "--- start onLayout --");
        int startLeft = 0;
        int startTop = 0;
        int childCount = getChildCount();
        Log.i(TAG, "--- onLayout childCount is -->" + childCount);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() != View.GONE)
                child.layout(startLeft, startTop,
                        startLeft + getWidth(),
                        startTop + getHeight());

            startTop = startTop + getHeight();
        }
    }

    public boolean isBottom() {
        return isBottom;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setPageChangeListener(PageChangeListener listener) {
        mListener = listener;
    }

    public interface PageChangeListener {
        void pageChange(int lastPage, int currentPage);
    }

    public void recyle() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                if (imageView != null && imageView.getDrawable() != null) {
                    Bitmap old = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    if (old != null) {
                        old.recycle();
                    }
                    imageView.setImageDrawable(null);
                }
            } else {
                continue;
            }
        }
        removeAllViews();
    }
}
