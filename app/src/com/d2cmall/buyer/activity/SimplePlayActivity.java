package com.d2cmall.buyer.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.PictureUtils;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.universalvideoview.UniversalMediaController;
import com.d2cmall.buyer.widget.universalvideoview.UniversalVideoView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.d2cmall.buyer.Constants.RequestCode.EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE;
import static com.d2cmall.buyer.R.id.videoView;

/**
 * 视频播放
 * Anthor: hrb
 * Date: 2017/6/16 17:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SimplePlayActivity extends BaseActivity implements UniversalVideoView.VideoViewCallback {
    @Bind(videoView)
    UniversalVideoView mVideoView;
    @Bind(R.id.media_controller)
    UniversalMediaController mediaController;
    @Bind(R.id.video_layout)
    FrameLayout videoLayout;
    @Bind(R.id.preview_layout)
    RelativeLayout previewLayout;
    @Bind(R.id.ll_head)
    LinearLayout mLinearLayout;
    private boolean isSource = true;
    private String url;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION";
    private static final String URL_KEY = "URL_KEY";
    private int mSeekPosition;
    private int ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private Context mContext;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mediaController != null) {
                        mediaController.setProgress();
                    }
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    break;
                case 2:
                    if (!isSource){
                        Util.showToast(mContext, "视频已删除");
                    }
                    setResult(EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_play);
        ButterKnife.bind(this);
        findViewById(R.id.img_buy).setVisibility(View.GONE);
        initPlayer();
    }

    private void initPlayer() {
        mContext=this;
        url = getIntent().getStringExtra("url");
        if (url.startsWith("/storage")) {
            isSource = getIntent().getBooleanExtra("isSource", true);
            mLinearLayout.setVisibility(View.VISIBLE);
            mediaController.setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVideoView.pause();
                    mediaController.updatePausePlay(R.mipmap.ic_simple_play, R.mipmap.ic_simple_pause);

                    if (mOrientationListener != null) {
                        mOrientationListener.disable();
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            if (!isSource) {
                                PictureUtils.deleteFile(new File(url));
                            }
                            mHandler.sendEmptyMessage(2);
                        }
                    }.start();

                }
            });
        } else if (!url.startsWith("http")) {
            url = Constants.IMG_HOST + url;
        }
        mVideoView.setVideoPath(url);
        mVideoView.setKeepScreenOn(true);
        mVideoView.requestFocus();
        mVideoView.setMediaController(mediaController);
        mediaController.setSeekBarStyle(getResources().getDrawable(R.drawable.seekbar_video_white),
                getResources().getDrawable(R.mipmap.thumb_white));
        mediaController.getView(R.id.has_played).setVisibility(View.GONE);
        mediaController.getView(R.id.duration).setVisibility(View.GONE);
        //mediaController.getView(R.id.xx).setVisibility(View.GONE);
        mediaController.getView(R.id.currenttime).setVisibility(View.VISIBLE);
        mediaController.getView(R.id.remaindertime).setVisibility(View.VISIBLE);
        mediaController.getView(R.id.iv_changescreen).setVisibility(View.VISIBLE);
        View playButton = mediaController.getView(R.id.rl_seekbar);
        LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) playButton.getLayoutParams();
        params0.topMargin = 0;
        playButton.setLayoutParams(params0);
        LinearLayout loadinglayout = (LinearLayout) mediaController.getView(R.id.loading_layout);
        loadinglayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        loadinglayout.removeAllViews();
        loadinglayout.addView(View.inflate(this, R.layout.layout_loading, null));
        loadinglayout.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loadinglayout.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        loadinglayout.setLayoutParams(params);
        startListener();
        mediaController.getView(R.id.iv_changescreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrientationListener == null) {
                    startListener();
                }
                mClick = true;
                if (!mIsLand) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    mIsLand = true;
                    mClickLand = false;
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    mIsLand = false;
                    mClickPort = false;
                }
            }

        });
        mediaController.setSeekChangeBack(new UniversalMediaController.SeekChangeBack() {
            @Override
            public void seekChangeStart() {
                mHandler.removeMessages(1);
            }

            @Override
            public void seekChangeEnd() {
                mHandler.sendEmptyMessage(1);
            }
        });

        mVideoView.setVideoViewCallback(this);

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaController.updatePausePlay(R.mipmap.ic_simple_play, R.mipmap.ic_simple_pause);
            }
        });
        mVideoView.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSeekPosition = mVideoView.getCurrentPosition() / 1000;
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
        outState.putString(URL_KEY, url);
    }

    private int savedPosition;

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        savedPosition = outState.getInt(SEEK_POSITION_KEY);
        url = outState.getString(URL_KEY);
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {

    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        mHandler.removeMessages(1);
        mSeekPosition = mVideoView.getCurrentPosition() / 1000;
        mediaController.updatePausePlay(R.mipmap.ic_simple_play, R.mipmap.ic_simple_pause);
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        previewLayout.setVisibility(View.GONE);
        mediaController.updatePausePlay(R.mipmap.ic_simple_play, R.mipmap.ic_simple_pause);
        mediaController.show();
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ORIENTATION = newConfig.orientation;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
            fl.width = -2;
            fl.height = -1;
            if (mediaController != null) {
                ((ImageView) mediaController.getView(R.id.iv_changescreen)).setImageResource(R.mipmap.ic_lessen);
            }
        } else {
            FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
            fl.width = -1;
            fl.height = -1;
            if (mediaController != null) {
                ((ImageView) mediaController.getView(R.id.iv_changescreen)).setImageResource(R.mipmap.ic_enlarge);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
        mediaController.updatePausePlay(R.mipmap.ic_simple_play, R.mipmap.ic_simple_pause);
        mHandler.removeMessages(1);
        if (mOrientationListener != null) {
            mOrientationListener.disable();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (savedPosition > 0) {
            mVideoView.seekTo(savedPosition);
        } else {
            mVideoView.start();
            mHandler.sendEmptyMessage(1);
        }
        mediaController.updatePausePlay(R.mipmap.ic_simple_play, R.mipmap.ic_simple_pause);
        if (mOrientationListener != null) {
            mOrientationListener.enable();
        }
    }

    private OrientationEventListener mOrientationListener; // 屏幕方向改变监听器
    private boolean mIsLand = false; // 是否是横屏
    private boolean mClick = false; // 是否点击
    private boolean mClickLand = true; // 点击进入横屏
    private boolean mClickPort = true; // 点击进入竖屏

    /**
     * 开启监听器
     */
    private final void startListener() {
        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int rotation) {
                // 设置竖屏
                if (((rotation >= 0) && (rotation <= 30)) || (rotation >= 330)) {
                    if (mClick) {
                        if (mIsLand && !mClickLand) {
                            return;
                        } else {
                            mClickPort = true;
                            mClick = false;
                            mIsLand = false;
                        }
                    } else {
                        if (mIsLand) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            mIsLand = false;
                            mClick = false;
                        }
                    }
                }
                // 设置横屏
                else if (((rotation >= 230) && (rotation <= 310))) {
                    if (mClick) {
                        if (!mIsLand && !mClickPort) {
                            return;
                        } else {
                            mClickLand = true;
                            mClick = false;
                            mIsLand = true;
                        }
                    } else {
                        if (!mIsLand) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            mIsLand = true;
                            mClick = false;
                        }
                    }
                }
            }
        };
        mOrientationListener.enable();
    }
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(new ContextWrapper(newBase)
        {
            @Override
            public Object getSystemService(String name)
            {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }
}
