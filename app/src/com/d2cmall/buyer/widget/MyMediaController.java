package com.d2cmall.buyer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.pili.pldroid.player.IMediaController;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by rookie on 2018/1/8.
 * 自定义MediaController
 */

public class MyMediaController extends FrameLayout implements IMediaController {

    private OnClickSpeedAdjustListener mOnClickSpeedAdjustListener;
    private View mRoot;
    private AudioManager mAM;
    private View mAnchor;
    private boolean mFromXml = false;
    private boolean mUseFastForward;
    private Context mContext;
    private PopupWindow mWindow;
    private int mAnimStyle;
    private boolean mDragging;
    private boolean mDisableProgress = false;
    private Runnable mLastSeekBarRunnable;
    private static int sDefaultTimeout = 3000;
    private OnHiddenListener mHiddenListener;
    public void setOnHiddenListener(OnHiddenListener l) {
        mHiddenListener = l;
    }
    public interface OnHiddenListener {
        public void onHidden();
    }
    StringBuilder mFormatBuilder;
    private long mDuration;

    Formatter mFormatter;

    private ImageView mTurnButton;// 开启暂停按钮

    private IMediaController.MediaPlayerControl mPlayer;

    private boolean mInstantSeeking = true;


    private ProgressBar mProgress;
    private boolean mShowing;

    private TextView mEndTime, mCurrentTime, currentTime, remaindTime;


    private static final int SEEK_TO_POST_DELAY_MILLIS = 200;

    public interface OnShownListener {
        public void onShown();
    }

    private OnShownListener mShownListener;

    public void setOnShownListener(OnShownListener l) {
        mShownListener = l;
    }

    public interface OnClickSpeedAdjustListener {
        void onClickNormal();

        void onClickFaster();

        void onClickSlower();
    }

    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoot = this;
        mFromXml = true;
        initController(context);
    }

    public MyMediaController(Context context) {
        super(context);
        if (!mFromXml && initController(context))
            initFloatingWindow();
    }

    public MyMediaController(Context context, boolean useFastForward, boolean disableProgressBar) {
        this(context);
        mUseFastForward = useFastForward;
        mDisableProgress = disableProgressBar;
    }

    public MyMediaController(Context context, boolean useFastForward) {
        this(context);
        mUseFastForward = useFastForward;
    }

    private void initFloatingWindow() {
        mWindow = new PopupWindow(mContext);
        mWindow.setFocusable(false);
        mWindow.setBackgroundDrawable(null);
        mWindow.setOutsideTouchable(true);
        mAnimStyle = android.R.style.Animation;
    }

    private boolean initController(Context context) {
        mUseFastForward = true;
        mContext = context.getApplicationContext();
        mAM = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        return true;
    }

    @Override
    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        mPlayer = mediaPlayerControl;
        updatePausePlay();
    }

    @Override
    public void show() {
        show(sDefaultTimeout);
    }

    private void disableUnsupportedButtons() {
        try {
            if (mTurnButton != null && !mPlayer.canPause())
                mTurnButton.setEnabled(false);
        } catch (IncompatibleClassChangeError ex) {
        }
    }

    @Override
    public void show(int timeout) {
        if (!mShowing) {
            if (mAnchor != null && mAnchor.getWindowToken() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    mAnchor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
            if (mTurnButton != null)
                mTurnButton.requestFocus();
            disableUnsupportedButtons();

            if (mFromXml) {
                setVisibility(View.VISIBLE);
            } else {
                int[] location = new int[2];

                if (mAnchor != null) {
                    mAnchor.getLocationOnScreen(location);
                    Rect anchorRect = new Rect(location[0], location[1],
                            location[0] + mAnchor.getWidth(), location[1]
                            + mAnchor.getHeight());

                    mWindow.setAnimationStyle(mAnimStyle);
                    mWindow.showAtLocation(mAnchor, Gravity.BOTTOM,
                            anchorRect.left, 0);
                } else {
                    Rect anchorRect = new Rect(location[0], location[1],
                            location[0] + mRoot.getWidth(), location[1]
                            + mRoot.getHeight());

                    mWindow.setAnimationStyle(mAnimStyle);
                    mWindow.showAtLocation(mRoot, Gravity.BOTTOM,
                            anchorRect.left, 0);
                }
            }
            mShowing = true;
            if (mShownListener != null)
                mShownListener.onShown();
        }
        updatePausePlay();
        mHandler.sendEmptyMessage(SHOW_PROGRESS);

        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT),
                    timeout);
        }
    }

    @Override
    public void hide() {
        if (mShowing) {
            if (mAnchor != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    //mAnchor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }
            }
            try {
                mHandler.removeMessages(SHOW_PROGRESS);
                if (mFromXml)
                    setVisibility(View.GONE);
                else
                    mWindow.dismiss();
            } catch (IllegalArgumentException ex) {
                Log.d("live", "MediaController already removed");
            }
            mShowing = false;
            if (mHiddenListener != null)
                mHiddenListener.onHidden();
        }
    }

    @Override
    public boolean isShowing() {
        return mShowing;
    }

    @Override
    public void setAnchorView(View view) {
        mAnchor = view;
        if (mAnchor == null) {
            sDefaultTimeout = 0; // show forever
        }
        if (!mFromXml) {
            removeAllViews();
            mRoot = makeControllerView();
            mWindow.setContentView(mRoot);
            mWindow.setWidth(LayoutParams.MATCH_PARENT);
            mWindow.setHeight(LayoutParams.WRAP_CONTENT);
        }
        initControllerView(mRoot);
    }

    private void doPauseResume() {
        if (mPlayer.isPlaying())
            mPlayer.pause();
        else
            mPlayer.start();
        updatePausePlay();
    }

    private void updatePausePlay() {
        if (mRoot == null || mTurnButton == null)
            return;

        if (mPlayer.isPlaying())
            mTurnButton.setImageResource(R.mipmap.ic_simple_pause);
        else
            mTurnButton.setImageResource(R.mipmap.ic_simple_play);
    }

    private OnClickListener mPauseListener = new OnClickListener() {
        public void onClick(View v) {
            if (mOnClickSpeedAdjustListener != null) {
                mOnClickSpeedAdjustListener.onClickNormal();
            }
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    private void initControllerView(View v) {
        mTurnButton = (ImageView) v.findViewById(R.id.turn_button);
        mCurrentTime= (TextView) v.findViewById(R.id.currenttime);
        mEndTime= (TextView) v.findViewById(R.id.remaindertime);
        if (mTurnButton != null) {
            mTurnButton.requestFocus();
            mTurnButton.setOnClickListener(mPauseListener);
        }

        View bar = v.findViewById(R.id.seekbar);
        mProgress = (ProgressBar) bar;
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                seeker.setOnSeekBarChangeListener(mSeekListener);
            }
            mProgress.setMax(1000);
        }
        currentTime = (TextView) v.findViewById(R.id.currenttime);
        remaindTime = (TextView) v.findViewById(R.id.remaindertime);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case FADE_OUT:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    if (!mPlayer.isPlaying()) {
                        return;
                    }
                    pos = setProgress();
                    if (pos == -1) {
                        return;
                    }
                    if (!mDragging && mShowing) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        updatePausePlay();
                    }
                    break;
            }
        }
    };

    private static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

    private long setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }

        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        mDuration = duration;

        if (mEndTime != null)
            mEndTime.setText(generateTime(mDuration));
        if (mCurrentTime != null)
            mCurrentTime.setText(generateTime(position));

        return position;
    }

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {

        public void onStartTrackingTouch(SeekBar bar) {
            mDragging = true;
            show(3600000);
            mHandler.removeMessages(SHOW_PROGRESS);
            if (mInstantSeeking)
                mAM.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                return;
            }


            final long newposition = (long) (mDuration * progress) / 1000;
            String time = generateTime(newposition);
            if (mInstantSeeking) {
                mHandler.removeCallbacks(mLastSeekBarRunnable);
                mLastSeekBarRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mPlayer.seekTo(newposition);
                    }
                };
                mHandler.postDelayed(mLastSeekBarRunnable, SEEK_TO_POST_DELAY_MILLIS);
            }
            if (mCurrentTime != null)
                mCurrentTime.setText(time);
        }

        public void onStopTrackingTouch(SeekBar bar) {
            if (!mInstantSeeking)
                mPlayer.seekTo(mDuration * bar.getProgress() / 1000);

            show(sDefaultTimeout);
            mHandler.removeMessages(SHOW_PROGRESS);
            mAM.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mDragging = false;
            mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
        }
    };

    private View makeControllerView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRoot = inflater.inflate(R.layout.layout_media_my_controller, this);
        return viewRoot;
    }
}
