package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.widget.PLVideoView;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/02/18 15:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class GuideLayout extends FrameLayout {

    private static GuideLayout instance;
    private WindowManager wManager;
    private WindowManager.LayoutParams wmParams;
    private View addView;

    private PLVideoView surfaceView;
    private ImageView closeBtn;

    private boolean isAddView;
    private AnimatorSet mShowAnimatorSet;
    private Point p;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    showAnimator();
                    break;

                default:
                    break;
            }
        }

        ;
    };
    private float downX;
    private float downY;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private OnClickListener mClickListener;
    private int startX;
    private int startY;
    private String videoPath;

    public static GuideLayout getInstance(Context context) {
        if (instance == null) {
            synchronized (GuideLayout.class) {
                instance = new GuideLayout(context);
            }
        }
        return instance;
    }

    public GuideLayout(Context context) {
        this(context, null);
    }

    public GuideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addView = LayoutInflater.from(context).inflate(R.layout.drag_view_layout,
                this);
        //addView.setBackground(getResources().getDrawable(R.drawable.bg_small_window));
        surfaceView = (PLVideoView) findViewById(R.id.VideoView);
        closeBtn = (ImageView) findViewById(R.id.close_btn);
        p = Util.getDeviceSize(context);
        setAnimator(context);
        getWindowManager(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        this.x = event.getRawX();
        this.y = event.getRawY() - 25;   //25是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = this.x;
                downY = this.y;
                mTouchStartX = x;
                mTouchStartY = y;
                break;
            case MotionEvent.ACTION_MOVE:         //计算移动的距离
                /*int offX = x - lastX;
                int offY = y - lastY;         //调用layout方法来重新放置它的位置
                layout(getLeft() + offX, getTop() + offY, getRight() + offX, getBottom() + offY);*/
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if (checkClickAction(downX, downY, this.x, this.y)) {
                    if (mClickListener != null) {
                        mClickListener.onClick(null);
                    }
                    return true;
                }
                updateViewPosition();
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return true;
    }

    public void setVideoUrl(String url) {
        videoPath = url;
    }

    private boolean checkClickAction(float startx, float starty, float endx, float endy) {
        if (Math.abs(endx - startx) < 5 && Math.abs(endy - starty) < 5) {
            return true;
        }
        return false;
    }

    private void updateViewPosition() {
        //更新浮动窗口位置参数,x是鼠标在屏幕的位置，mTouchStartX是鼠标在图片的位置
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wManager.updateViewLayout(this, wmParams);
    }

    public void setClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    /**
     * @param context
     * @category 实例化WindowManager 初次模拟位置时候使用
     */
    private void getWindowManager(final Context context) {
        wManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 26) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= 24) {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        wmParams.format = PixelFormat.RGBA_8888; //图片之外的其他地方透明
        wmParams.gravity = Gravity.TOP | Gravity.LEFT;
        wmParams.alpha = 1.0f;
        wmParams.width = Util.dip2px(context, 120);
        wmParams.height = Util.dip2px(context, 200);
        /*wmParams.x=p.x-Util.dip2px(context,120);
        wmParams.y=p.y-Util.getStatusHeight(context)-Util.dip2px(context,200);*/
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        startX = p.x - Util.dip2px(context, 120);
        startY = (p.y - Util.getStatusHeight(context) - Util.dip2px(context, 200)) / 2;
    }

    private void setAnimator(Context context) {
        mShowAnimatorSet = new AnimatorSet();
        /*ObjectAnimator animator1 = ObjectAnimator.ofFloat(surfaceView, "translationX", 0,p.x-Util.dip2px(context,120));
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(surfaceView, "translationY", 0,p.y-Util.dip2px(context,200)-Util.getStatusHeight(context));*/
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(surfaceView, "translationX", 0, p.x - Util.dip2px(context, 120));
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(surfaceView, "translationY", 0, p.y - Util.dip2px(context, 200) - Util.getStatusHeight(context));
        mShowAnimatorSet.playTogether(animator1, animator2);
        mShowAnimatorSet.setDuration(1000);
        mShowAnimatorSet.setInterpolator(new LinearInterpolator());
    }

    public void showAnimator() {
        surfaceView.setVisibility(View.VISIBLE);
        if (!Util.isEmpty(videoPath)) {
            AVOptions options = new AVOptions();
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
            surfaceView.setAVOptions(options);
            surfaceView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
            surfaceView.setVideoPath(videoPath);
            surfaceView.start();
        }
        //mShowAnimatorSet.start();
        isAddView = true;
    }

    public void hide() {
        wManager.removeView(this);
        mHandler.removeMessages(1);
        isAddView = false;
        surfaceView.stopPlayback();
    }

    public void sendMessage() {
        if (isAddView) {
            wManager.removeView(this);
            mHandler.removeMessages(1);
            isAddView = false;
        }
        mHandler.sendEmptyMessage(1);
        wmParams.x = startX;
        wmParams.y = startY;
        wManager.addView(this, wmParams);
    }

//    public SurfaceView getSurfaceView() {
//        return surfaceView;
//    }


    public boolean isAddView() {
        return isAddView;
    }

    public void setClostListener(OnClickListener listener) {
        closeBtn.setOnClickListener(listener);
    }

    public void canClose(boolean is) {
        if (closeBtn != null) {
            if (is) {
                closeBtn.setVisibility(VISIBLE);
            } else {
                closeBtn.setVisibility(View.GONE);
            }
        }
    }

    public static void back() {
        if (instance != null && instance.isAddView()) {
            instance.hide();
        }
    }
}
