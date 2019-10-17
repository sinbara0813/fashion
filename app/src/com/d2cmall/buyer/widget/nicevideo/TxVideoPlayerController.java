package com.d2cmall.buyer.widget.nicevideo;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.d2cmall.buyer.R;
import com.xiao.nicevideoplayer.LogUtil;
import com.xiao.nicevideoplayer.NiceUtil;


/**
 * 频播放器控制器.
 */
public class TxVideoPlayerController
        extends NiceVideoPlayerController
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private Context mContext;
    private ImageView mImage;
    public ImageView mCenterStart;

    private LinearLayout mTop;
    private ImageView mBack;
    private TextView mTitle;

    private LinearLayout mBottom;
    private ImageView mRestartPause;
    private TextView mPosition;
    private TextView mDuration;
    public TextView mRemaindeDuration;
    private SeekBar mSeek;
    private ImageView mFullScreen;

    private LinearLayout mLoading;
    private TextView mLoadText;

    private LinearLayout mChangePositon;
    private TextView mChangePositionCurrent;
    private ProgressBar mChangePositionProgress;

    private LinearLayout mChangeBrightness;
    private ProgressBar mChangeBrightnessProgress;

    private LinearLayout mChangeVolume;
    private ProgressBar mChangeVolumeProgress;

    private LinearLayout mError;
    private TextView mRetry;

    private LinearLayout mCompleted;
    private ImageView mReplay;
    private TextView mShare;
    private String time = "";

    private boolean topBottomVisible;
    private CountDownTimer mDismissTopBottomCountDownTimer;

    public void setBanFullScreen(boolean banFullScreen) {
        isBanFullScreen = banFullScreen;
        mFullScreen.setClickable(false);
        this.setClickable(false);
    }

    private boolean isBanFullScreen;

    public TxVideoPlayerController(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }


    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_video_player_controller, this, true);

        mCenterStart = (ImageView) findViewById(R.id.center_start);
        mImage = (ImageView) findViewById(R.id.image);

        mTop = (LinearLayout) findViewById(R.id.top);
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);

        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mRestartPause = (ImageView) findViewById(R.id.restart_or_pause);
        mPosition = (TextView) findViewById(R.id.position);
        mDuration = (TextView) findViewById(R.id.duration);
        mRemaindeDuration = (TextView) findViewById(R.id.tv_duration);
        mSeek = (SeekBar) findViewById(R.id.seek);
        //mSeek.setThumb(getResources().getDrawable(R.mipmap.thumb_white));
        mFullScreen = (ImageView) findViewById(R.id.full_screen);

        mLoading = (LinearLayout) findViewById(R.id.loading);
        mLoadText = (TextView) findViewById(R.id.load_text);

        mChangePositon = (LinearLayout) findViewById(R.id.change_position);
        mChangePositionCurrent = (TextView) findViewById(R.id.change_position_current);
        mChangePositionProgress = (ProgressBar) findViewById(R.id.change_position_progress);

        mChangeBrightness = (LinearLayout) findViewById(R.id.change_brightness);
        mChangeBrightnessProgress = (ProgressBar) findViewById(R.id.change_brightness_progress);

        mChangeVolume = (LinearLayout) findViewById(R.id.change_volume);
        mChangeVolumeProgress = (ProgressBar) findViewById(R.id.change_volume_progress);

        mError = (LinearLayout) findViewById(R.id.error);
        mRetry = (TextView) findViewById(R.id.retry);

        mCompleted = (LinearLayout) findViewById(R.id.completed);
        mReplay = (ImageView) findViewById(R.id.replay);
        mShare = (TextView) findViewById(R.id.share);

        mCenterStart.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mRestartPause.setOnClickListener(this);
        mFullScreen.setOnClickListener(this);
        mRetry.setOnClickListener(this);
        mReplay.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mSeek.setOnSeekBarChangeListener(this);
        this.setOnClickListener(this);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public ImageView getImage() {
        return mImage;
    }

    public void setTime(String time) {
        this.time = time;
        mRemaindeDuration.setText(time);
    }

    public void setImage(@DrawableRes int resId) {
        mImage.setImageResource(resId);
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NiceVideoPlayer.STATE_IDLE:
                Log.e("NiceVideoPlayer", "NiceVideoPlayer.STATE_IDLE" + NiceVideoPlayer.STATE_IDLE);
                break;
            case NiceVideoPlayer.STATE_PREPARING:
                mImage.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mLoadText.setText("正在准备...");
                mError.setVisibility(View.GONE);
                mCompleted.setVisibility(View.GONE);
                mTop.setVisibility(View.GONE);
                mCenterStart.setVisibility(View.GONE);
                break;
            case NiceVideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_PLAYING:
                startUpdateProgressTimer();
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.mipmap.ic_simple_pause);
                startDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_PAUSED:
                mCenterStart.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.mipmap.ic_simple_play);
                cancelDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PLAYING:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.mipmap.ic_simple_pause);
                mLoadText.setText("正在缓冲...");
                mCompleted.setVisibility(View.GONE);
                mTop.setVisibility(View.GONE);
                mCenterStart.setVisibility(View.GONE);
                startDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.mipmap.ic_simple_play);
                mLoadText.setText("正在缓冲...");
                mCompleted.setVisibility(View.GONE);
                mTop.setVisibility(View.GONE);
                mCenterStart.setVisibility(View.GONE);
                cancelDismissTopBottomTimer();
                break;
            case NiceVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mTop.setVisibility(View.VISIBLE);
                mError.setVisibility(View.VISIBLE);
                break;
            case NiceVideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mImage.setVisibility(View.VISIBLE);
                mCompleted.setVisibility(View.VISIBLE);
                mRemaindeDuration.setText(time);
                break;
        }
    }

    @Override
    protected void onPlayerStateChanged(int playerState) {
        switch (playerState) {
            case NiceVideoPlayer.PLAYER_NORMAL:
                mBack.setVisibility(View.GONE);
                setTopBottomVisible(false);
                mRemaindeDuration.setVisibility(View.VISIBLE);
                mFullScreen.setImageResource(R.mipmap.ic_enlarge);
                break;
            case NiceVideoPlayer.PLAYER_FULL_SCREEN:
                setTopBottomVisible(true);
                mBack.setVisibility(View.GONE);
                mFullScreen.setImageResource(R.mipmap.ic_lessen);
                mRemaindeDuration.setVisibility(View.GONE);
                break;
            case NiceVideoPlayer.PLAYER_TINY_WINDOW:
                mBack.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void reset() {
        topBottomVisible = false;
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);

        mCenterStart.setVisibility(View.VISIBLE);
        mImage.setVisibility(View.VISIBLE);
        mBottom.setVisibility(View.GONE);
        mRemaindeDuration.setVisibility(View.VISIBLE);
        mFullScreen.setImageResource(R.mipmap.ic_enlarge);

        mTop.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.GONE);

        mLoading.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mCompleted.setVisibility(View.GONE);
    }

    /**
     * 尽量不要在onClick中直接处理控件的隐藏、显示及各种UI逻辑。
     * UI相关的逻辑都尽量到{@link #onPlayStateChanged}和{@link #onPlayerStateChanged}中处理.
     */
    @Override
    public void onClick(View v) {
        if (v == mCenterStart) {
            if (mNiceVideoPlayer.isIdle()) {
                mNiceVideoPlayer.start();
            }
        } else if (v == mBack) {
            if (mNiceVideoPlayer.isFullScreen()) {
                mNiceVideoPlayer.exitFullScreen();
            } else if (mNiceVideoPlayer.isTinyWindow()) {
                mNiceVideoPlayer.exitTinyWindow();
            }
        } else if (v == mRestartPause) {
            if (mNiceVideoPlayer.isPlaying() || mNiceVideoPlayer.isBufferingPlaying()) {
                mNiceVideoPlayer.pause();
            } else if (mNiceVideoPlayer.isPaused() || mNiceVideoPlayer.isBufferingPaused()) {
                mNiceVideoPlayer.restart();
            }
        } else if (v == mFullScreen) {
            if (mNiceVideoPlayer.isNormal() || mNiceVideoPlayer.isTinyWindow()) {
                mNiceVideoPlayer.enterFullScreen();

            } else if (mNiceVideoPlayer.isFullScreen()) {
                mNiceVideoPlayer.exitFullScreen();
//                setTopBottomVisible(!topBottomVisible);
//                mRemaindeDuration.setVisibility(View.GONE);
            }
//            if (mNiceVideoPlayer.isNormal()) {
//                setTopBottomVisible(false);
//                mRemaindeDuration.setVisibility(View.VISIBLE);
//            } else {
//                setTopBottomVisible(!topBottomVisible);
//                mRemaindeDuration.setVisibility(View.GONE);
//            }
        } else if (v == mRetry) {
            mNiceVideoPlayer.restart();
            if (mNiceVideoPlayer.isNormal()) {
                setTopBottomVisible(false);
                mRemaindeDuration.setVisibility(View.VISIBLE);
            } else {
                // setTopBottomVisible(!topBottomVisible);
                setTopBottomVisible(true);
                mRemaindeDuration.setVisibility(View.GONE);
            }
        } else if (v == mReplay) {
            mRetry.performClick();
        } else if (v == mShare) {
            Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == this) {
            if (mNiceVideoPlayer.isPlaying()
                    || mNiceVideoPlayer.isPaused()
                    || mNiceVideoPlayer.isBufferingPlaying()
                    || mNiceVideoPlayer.isBufferingPaused()) {
//                if(mNiceVideoPlayer.isPlaying()){
                if (mNiceVideoPlayer.isNormal() || mNiceVideoPlayer.isTinyWindow()) {
                    mNiceVideoPlayer.enterFullScreen();
                }
//               / }
                if (mNiceVideoPlayer.isNormal()) {
                    setTopBottomVisible(false);
                    mRemaindeDuration.setVisibility(View.VISIBLE);
                } else {
                    // setTopBottomVisible(!topBottomVisible);
                    setTopBottomVisible(true);
                    mRemaindeDuration.setVisibility(View.GONE);
                }

            }
        }
    }


    private void setTopBottomVisible(boolean visible) {
        mTop.setVisibility(visible ? View.VISIBLE : View.GONE);
        LogUtil.d("setTopBottomVisible" + visible);
        mBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
        topBottomVisible = visible;
        if (visible) {
            if (!mNiceVideoPlayer.isPaused() && !mNiceVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }

    private void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(8000, 8000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (mNiceVideoPlayer.isNormal()) {
                        setTopBottomVisible(false);
                    } else {
                        setTopBottomVisible(true);
                    }
                }
            };
        }
        mDismissTopBottomCountDownTimer.start();
    }

    private void cancelDismissTopBottomTimer() {
        if (mDismissTopBottomCountDownTimer != null) {
            mDismissTopBottomCountDownTimer.cancel();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mNiceVideoPlayer.isBufferingPaused() || mNiceVideoPlayer.isPaused()) {
            mNiceVideoPlayer.restart();
        }
        long position = (long) (mNiceVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
        mNiceVideoPlayer.seekTo(position);
        startDismissTopBottomTimer();
    }

    @Override
    protected void updateProgress() {
        long position = mNiceVideoPlayer.getCurrentPosition();
        long duration = mNiceVideoPlayer.getDuration();
        int bufferPercentage = mNiceVideoPlayer.getBufferPercentage();
        mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeek.setProgress(progress);
        mPosition.setText(NiceUtil.formatTime(position));
        mDuration.setText("- " + NiceUtil.formatTime(duration - position));
        mRemaindeDuration.setText(NiceUtil.formatTime(duration - position));
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {
        mChangePositon.setVisibility(View.VISIBLE);
        long newPosition = (long) (duration * newPositionProgress / 100f);
        mChangePositionCurrent.setText(NiceUtil.formatTime(newPosition));
        mChangePositionProgress.setProgress(newPositionProgress);
        mSeek.setProgress(newPositionProgress);
        mPosition.setText(NiceUtil.formatTime(newPosition));
    }

    @Override
    protected void hideChangePosition() {
        mChangePositon.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {
        mChangeVolume.setVisibility(View.VISIBLE);
        mChangeVolumeProgress.setProgress(newVolumeProgress);
    }

    @Override
    protected void hideChangeVolume() {
        mChangeVolume.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
        mChangeBrightness.setVisibility(View.VISIBLE);
        mChangeBrightnessProgress.setProgress(newBrightnessProgress);
    }

    @Override
    protected void hideChangeBrightness() {
        mChangeBrightness.setVisibility(View.GONE);
    }
}
