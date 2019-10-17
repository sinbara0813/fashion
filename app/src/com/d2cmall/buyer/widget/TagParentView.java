package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

/**
 * 标签父view
 * Author: hrb
 * Date: 2016/07/22 17:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class TagParentView extends RelativeLayout implements TagView.TagClickBack {

    /**
     * 长按事件响应时间 默认是1000毫秒，也可以自行设置
     */
    private long dragResponseMS = 500;
    private Context mContext;
    private ImageView mDragImageView;
    private Vibrator mVibrator;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private Bitmap mDragBitmap;
    private int mStatusHeight;
    private boolean isDrag = false;//是否可以拖动

    private int mDownX;
    private int mDownY;
    private int moveX;
    private int moveY;

    private int mPoint2ItemTop; //按下的点到所在item的上边缘的距离
    private int mPoint2ItemLeft; //按下的点到所在item的左边缘的距离

    private int mOffset2Top; //按下的点距离屏幕顶部的偏移量
    private int mOffset2Left; //按下的点距离屏幕左边的偏移量

    private int screenX;
    private int screenY;

    private TagView mTragView;
    private TagView lastTragView;

    private int itemWidth;
    private int itemHeight;
    private boolean isCanTrag;

    public TagParentView(Context context) {
        this(context, null);
    }

    public TagParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        Point p = Util.getDeviceSize(mContext);
        screenX = p.x;
        screenY = p.y;
        mStatusHeight = Util.getStatusHeight(mContext);
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setData(ImageInfo info) {
        /*isCanTrag = info.isSelf;
        setVisibility(View.VISIBLE);
        int size = info.getTags().size() > 3 ? 3 : info.getTags().size();
        TagView tagView = null;
        for (int i = 0; i < size; i++) {
            tagView = new TagView(mContext);
            tagView.setListener(this);
            tagView.setData(info, i);
            ImageInfo.TagBean tagBean = info.getTags().get(i);
            if (tagBean.y == 50) {
                tagBean.y = 50 + i * 10;
            }
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-2, -2);
            rl.setMargins((int) tagBean.x * screenX / 100, (int) tagBean.y * screenY / 100, 0, 0);
            tagView.setLayoutParams(rl);
            addView(tagView);
        }*/
    }

    private Handler mHandler = new Handler();

    //用来处理是否为长按的Runnable
    private Runnable mLongClickRunnable = new Runnable() {

        @Override
        public void run() {
            //根据我们按下的点显示镜像
            removeDragImage();
            createDragImage(mDragBitmap, mDownX, mDownY);
            isDrag = true; //设置可以拖拽
            mVibrator.vibrate(50); //震动一下
            mTragView.setVisibility(View.INVISIBLE);//隐藏view
            if (mTragView != null) {
                lastTragView = mTragView;
            }
        }
    };

    /**
     * 创建拖动的镜像
     *
     * @param bitmap
     * @param downX  按下的点相对父控件的X坐标
     * @param downY  按下的点相对父控件的X坐标
     */
    private void createDragImage(Bitmap bitmap, int downX, int downY) {
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; //图片之外的其他地方透明
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowLayoutParams.x = downX - mPoint2ItemLeft;
        mWindowLayoutParams.y = downY - mPoint2ItemTop;
        mWindowLayoutParams.alpha = 0.55f; //透明度
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(bitmap);
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                if (!isCanTrag) {
                    return super.dispatchTouchEvent(ev);
                }
                mTragView = getTragView(mDownX, mDownY);

                if (mTragView == null) {
                    return super.dispatchTouchEvent(ev);
                }

                itemWidth = mTragView.getWidth();
                itemHeight = mTragView.getHeight();
                //使用Handler延迟dragResponseMS执行mLongClickRunnable
                mHandler.postDelayed(mLongClickRunnable, dragResponseMS);

                mPoint2ItemTop = mDownY - mTragView.getTop();
                //+((MarginLayoutParams)mTragView.getLayoutParams()).topMargin
                mPoint2ItemLeft = mDownX - mTragView.getLeft();
                //+((MarginLayoutParams)mTragView.getLayoutParams()).leftMargin

                mOffset2Top = (int) (ev.getRawY() - mDownY);
                mOffset2Left = (int) (ev.getRawX() - mDownX);

                mTragView.setDrawingCacheEnabled(true);
                //获取缓存中的Bitmap对象
                mDragBitmap = Bitmap.createBitmap(mTragView.getDrawingCache());
                //释放绘图缓存
                mTragView.destroyDrawingCache();
                return super.dispatchTouchEvent(ev) || true;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(mLongClickRunnable);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isDrag && mDragImageView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) ev.getX();
                    moveY = (int) ev.getY();
                    //拖动
                    onDragItem(moveX, moveY);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    onStopDrag();
                    isDrag = false;
                    break;
            }
            return super.onTouchEvent(ev) || true;
        }
        return super.onTouchEvent(ev);
    }

    public void onStopDrag() {
        removeDragImage();
        if (mTragView == null) return;
        int X = moveX - mPoint2ItemLeft;
        int Y = moveY - mPoint2ItemTop;
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X + itemWidth > screenX) {
            X = screenX - itemWidth;
        }
        if (Y + itemHeight > screenY) {
            Y = screenY - itemHeight;
        }
        /*mTragView.mTag.x = X * 100 / screenX;
        mTragView.mTag.y = Y * 100 / screenY;*/
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) mTragView.getLayoutParams();
        rl.setMargins(X, Y, 0, 0);
        mTragView.setLayoutParams(rl);
        mTragView.setVisibility(View.VISIBLE);
        if (changeListener != null) {
            changeListener.update();
        }
    }

    @Override
    public void close(TagView view) {
        removeView(view);
        if (changeListener != null) {
            changeListener.update();
        }
    }

    @Override
    public void addCart(long id) {
        Intent intent = new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }

    public interface ChangeListener {
        void update();
    }

    private ChangeListener changeListener;

    public void setListener(ChangeListener listener) {
        changeListener = listener;
    }

    /**
     * 从界面上面移动拖动镜像
     */
    private void removeDragImage() {
        if (mDragImageView != null) {
            if (lastTragView != null) {
                lastTragView.setVisibility(VISIBLE);
            }
            isDrag = false;
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
    }

    public void setDragResponseMS(long time) {
        if (time < 500) return;
        this.dragResponseMS = time;
    }


    public void onDragItem(int moveX, int moveY) {
        mWindowLayoutParams.x = moveX - mPoint2ItemLeft;
        mWindowLayoutParams.y = moveY - mPoint2ItemTop;
        if (mWindowLayoutParams.y + itemHeight > screenY) {
            mWindowLayoutParams.y = screenY - itemHeight;
        }
        if (mWindowLayoutParams.x + itemWidth > screenX) {
            mWindowLayoutParams.x = screenX - itemWidth;
        }
        mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams); //更新镜像的位置
    }

    private TagView getTragView(int x, int y) {
        if (getChildCount() > 0) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                TagView tagView = (TagView) getChildAt(i);
                if (x > tagView.getLeft() && x < tagView.getRight() && y > tagView.getTop() && y < tagView.getBottom()) {
                    return tagView;
                }
            }
        }
        return null;
    }
}
