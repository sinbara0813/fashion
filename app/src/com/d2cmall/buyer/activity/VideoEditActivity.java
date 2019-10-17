package com.d2cmall.buyer.activity;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.VideoEditAdapter;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.VideoEditInfo;
import com.d2cmall.buyer.util.BackgroundExecutor;
import com.d2cmall.buyer.util.EditSpacingItemDecoration;
import com.d2cmall.buyer.util.ExtractFrameWorkThread;
import com.d2cmall.buyer.util.OnTrimVideoListener;
import com.d2cmall.buyer.util.PictureUtils;
import com.d2cmall.buyer.util.TrimVideoUtils;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RangeSeekBar;

import java.io.File;
import java.lang.ref.WeakReference;

public class VideoEditActivity extends AppCompatActivity implements OnTrimVideoListener {
    private static final String TAG = VideoEditActivity.class.getSimpleName();
    private static final long MIN_CUT_DURATION = 3 * 1000L;// 最小剪辑时间3s
    private static final long MAX_CUT_DURATION = 90 * 1000L;//视频最多剪切多长时间
    private static final int MAX_COUNT_RANGE = 10;//seekBar的区域内一共有多少张图片
    private LinearLayout seekBarLayout;
    private int mMaxWidth;

    private long duration;
    private RangeSeekBar seekBar;
    private VideoView mVideoView;
    private RecyclerView mRecyclerView;
    private TextView cancel;
    private TextView save;
    private ImageView positionIcon;
    private VideoEditAdapter videoEditAdapter;
    private float averageMsPx;//每毫秒所占的px
    private float averagePxMs;//每px所占用的ms毫秒
    private String outPutFileDirPath;
    private String secondOutPutFileDirPath;
    private String videoFileName;
    private ExtractFrameWorkThread mExtractFrameWorkThread;
    private String path;
    private long leftProgress, rightProgress;
    private long scrollPos = 0;
    private int mScaledTouchSlop;
    private int lastScrollX;
    private boolean isSeeking;
    public static String mFinalPath;
    private int thumbnailsCount;
    private ProgressDialog mProgressDialog;
    private TopicBean.DataBean.TopicsBean.ListBean topicBean;
    private int isReview;//1,是评价商品视频,其它是发布买家秀
    private String channel;
    private String transactionTime;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);
        topicBean = (TopicBean.DataBean.TopicsBean.ListBean) getIntent().getSerializableExtra("topic");
        isReview = getIntent().getIntExtra("isReview", -1);
        channel = getIntent().getStringExtra("channel");
        transactionTime = getIntent().getStringExtra("transactionTime");
        position = getIntent().getIntExtra("position",0);
        initData();
        initView();
        initEditVideo();
        initPlay();
    }

    private void initData() {
        path = getIntent().getStringExtra("path");
        int lastSepara = path.lastIndexOf(File.separator);
        int lastPoint = path.lastIndexOf(".");
        if (lastPoint > lastSepara) {
            videoFileName = path.substring(lastSepara + 1, lastPoint);
        }
        duration = getIntent().getLongExtra("duration", 0);


        mMaxWidth = Util.getDeviceSize(this).x - Util.dip2px(this, 70);
        mScaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
    }

    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("正在保存,请稍等...");
        seekBarLayout = (LinearLayout) findViewById(R.id.id_seekBarLayout);
        mVideoView = (VideoView) findViewById(R.id.uVideoView);
        positionIcon = (ImageView) findViewById(R.id.positionIcon);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_rv_id);
        cancel = (TextView) findViewById(R.id.cancel);
        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoEditAdapter = new VideoEditAdapter(this,
                (Util.getDeviceSize(this).x - Util.dip2px(this, 70)) / 10);
        mRecyclerView.setAdapter(videoEditAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private void save() {
        if (leftProgress <= 0 && rightProgress >= duration) {
            getResult(Uri.parse(path), true);
        } else {
            //暂停播放
            videoPause();

            final File file = new File(path);

            //提示正在保存视频
            onTrimStarted();

            BackgroundExecutor.execute(
                    new BackgroundExecutor.Task("", 0L, "") {
                        @Override
                        public void execute() {
                            try {
                                TrimVideoUtils.startTrim(file, getDestinationPath(), leftProgress, rightProgress, VideoEditActivity.this);
                            } catch (final Throwable e) {
                                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                            }
                        }
                    }
            );
        }
    }

    private String getDestinationPath() {
        if (mFinalPath == null) {
            mFinalPath = PictureUtils.getEditVideoDir(this);
            mFinalPath += "/";
        }
        return mFinalPath;
    }

    ;

    private void initEditVideo() {
        //for video edit
        long startPosition = 0;
        long endPosition = duration;
        int rangeWidth;
        boolean isOver_60_s;
        if (endPosition <= MAX_CUT_DURATION) {
            isOver_60_s = false;
            thumbnailsCount = MAX_COUNT_RANGE;
            rangeWidth = mMaxWidth;
        } else {
            isOver_60_s = true;
            thumbnailsCount = (int) (endPosition * 1.0f / (MAX_CUT_DURATION * 1.0f) * MAX_COUNT_RANGE);
            rangeWidth = mMaxWidth / MAX_COUNT_RANGE * thumbnailsCount;
        }
        mRecyclerView.addItemDecoration(new EditSpacingItemDecoration(Util.dip2px(this, 35), thumbnailsCount));

        //init seekBar
        if (isOver_60_s) {
            seekBar = new RangeSeekBar(this, 0L, MAX_CUT_DURATION);
            seekBar.setSelectedMinValue(0L);
            seekBar.setSelectedMaxValue(MAX_CUT_DURATION);
        } else {
            seekBar = new RangeSeekBar(this, 0L, endPosition);
            seekBar.setSelectedMinValue(0L);
            seekBar.setSelectedMaxValue(endPosition);
        }
        seekBar.setMin_cut_time(MIN_CUT_DURATION);//设置最小裁剪时间
        seekBar.setNotifyWhileDragging(true);
        seekBar.setOnRangeSeekBarChangeListener(mOnRangeSeekBarChangeListener);
        seekBarLayout.addView(seekBar);

        Log.d(TAG, "-------thumbnailsCount--->>>>" + thumbnailsCount);
        averageMsPx = duration * 1.0f / rangeWidth * 1.0f;
        Log.d(TAG, "-------rangeWidth--->>>>" + rangeWidth);
        Log.d(TAG, "-------localMedia.getDuration()--->>>>" + duration);
        Log.d(TAG, "-------averageMsPx--->>>>" + averageMsPx);
        outPutFileDirPath = PictureUtils.getSaveEditThumbnailDir(this);
        secondOutPutFileDirPath = outPutFileDirPath + "/" + videoFileName + "/";
        int extractW = (Util.getDeviceSize(this).x - Util.dip2px(this, 70)) / MAX_COUNT_RANGE;
        int extractH = Util.dip2px(this, 55);
        mExtractFrameWorkThread = new ExtractFrameWorkThread(extractW, extractH, mUIHandler, path, secondOutPutFileDirPath, startPosition, endPosition, thumbnailsCount);
        mExtractFrameWorkThread.start();

        //init pos icon start
        leftProgress = 0;
        if (isOver_60_s) {
            rightProgress = MAX_CUT_DURATION;
        } else {
            rightProgress = endPosition;
        }
        averagePxMs = (mMaxWidth * 1.0f / (rightProgress - leftProgress));
        Log.d(TAG, "------averagePxMs----:>>>>>" + averagePxMs);


    }


    private void initPlay() {
        mVideoView.setVideoPath(path);
        //设置videoview的OnPrepared监听
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //设置MediaPlayer的OnSeekComplete监听
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        Log.d(TAG, "------ok----real---start-----");
                        Log.d(TAG, "------isSeeking-----" + isSeeking);
                        if (!isSeeking) {
                            videoStart();
                        }
                    }
                });
            }
        });
        //first
        videoStart();
    }

    private boolean isOverScaledTouchSlop;

    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "-------newState:>>>>>" + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                isSeeking = false;
//                videoStart();
            } else {
                isSeeking = true;
                if (isOverScaledTouchSlop && mVideoView != null && mVideoView.isPlaying()) {
                    videoPause();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isSeeking = false;
            int scrollX = getScrollXDistance();
            //达不到滑动的距离
            if (Math.abs(lastScrollX - scrollX) < mScaledTouchSlop) {
                isOverScaledTouchSlop = false;
                return;
            }
            isOverScaledTouchSlop = true;
            Log.d(TAG, "-------scrollX:>>>>>" + scrollX);
            //初始状态,why ? 因为默认的时候有35dp的空白！
            if (scrollX == -Util.dip2px(VideoEditActivity.this, 35)) {
                scrollPos = 0;
            } else {
                // why 在这里处理一下,因为onScrollStateChanged早于onScrolled回调
                if (mVideoView != null && mVideoView.isPlaying()) {
                    videoPause();
                }
                isSeeking = true;
                scrollPos = (long) (averageMsPx * (Util.dip2px(VideoEditActivity.this, 35) + scrollX));
                Log.d(TAG, "-------scrollPos:>>>>>" + scrollPos);
                leftProgress = seekBar.getSelectedMinValue() + scrollPos;
                rightProgress = seekBar.getSelectedMaxValue() + scrollPos;
                Log.d(TAG, "-------leftProgress:>>>>>" + leftProgress);
                mVideoView.seekTo((int) leftProgress);
            }
            lastScrollX = scrollX;
        }
    };

    /**
     * 水平滑动了多少px
     *
     * @return int px
     */
    private int getScrollXDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        int itemWidth = firstVisibleChildView.getWidth();
        return (position) * itemWidth - firstVisibleChildView.getLeft();
    }

    private ValueAnimator animator;

    private void anim() {
        Log.d(TAG, "--anim--onProgressUpdate---->>>>>>>" + mVideoView.getCurrentPosition());
        if (positionIcon.getVisibility() == View.GONE) {
            positionIcon.setVisibility(View.VISIBLE);
        }
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) positionIcon.getLayoutParams();
        int start = (int) (Util.dip2px(this, 35) + (leftProgress/*mVideoView.getCurrentPosition()*/ - scrollPos) * averagePxMs);
        int end = (int) (Util.dip2px(this, 35) + (rightProgress - scrollPos) * averagePxMs);
        animator = ValueAnimator
                .ofInt(start, end)
                .setDuration((rightProgress - scrollPos) - (leftProgress/*mVideoView.getCurrentPosition()*/ - scrollPos));
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.leftMargin = (int) animation.getAnimatedValue();
                positionIcon.setLayoutParams(params);
            }
        });
        animator.start();
    }

    private final MainHandler mUIHandler = new MainHandler(this);

    @Override
    public void onTrimStarted() {
        //开始保存
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        getResult(uri, false);
    }

    public void getResult(final Uri uri, final boolean isSource) {
        mProgressDialog.cancel();
        //保存完成
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isReview==1){
                    Intent intent = new Intent();
                    intent.putExtra("url", uri.getPath());
                    intent.putExtra("isSource", isSource);
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(uri.getPath());
                    String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long duration = Long.valueOf(durationStr);
                    intent.putExtra("duration", duration);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    if("wardrobe".equals(channel)){
                        Intent intent = new Intent(VideoEditActivity.this, FashionPublishActivity.class);
                        intent.putExtra("videoUrl", uri.getPath());
                        intent.putExtra("transactionTime", transactionTime);
                        intent.putExtra("position", position);
                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(uri.getPath());
                        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        long duration = Long.valueOf(durationStr);
                        intent.putExtra("duration", duration);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(VideoEditActivity.this, PublishVideoActivity.class);
                        if (topicBean != null) {
                            intent.putExtra("topic", topicBean);
                        }
                        intent.putExtra("url", uri.getPath());
                        intent.putExtra("isSource", isSource);
                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(uri.getPath());
                        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        long duration = Long.valueOf(durationStr);
                        intent.putExtra("duration", duration);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });
    }


    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        BackgroundExecutor.cancelAll("", true);
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoEditActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class MainHandler extends Handler {
        private final WeakReference<VideoEditActivity> mActivity;

        MainHandler(VideoEditActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            VideoEditActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == ExtractFrameWorkThread.MSG_SAVE_SUCCESS) {
                    if (activity.videoEditAdapter != null) {
                        VideoEditInfo info = (VideoEditInfo) msg.obj;
                        activity.videoEditAdapter.addItemVideoInfo(info);
                    }
                }
            }
        }
    }

    private final RangeSeekBar.OnRangeSeekBarChangeListener mOnRangeSeekBarChangeListener = new RangeSeekBar.OnRangeSeekBarChangeListener() {
        @Override
        public void onRangeSeekBarValuesChanged(RangeSeekBar bar, long minValue, long maxValue, int action, boolean isMin, RangeSeekBar.Thumb pressedThumb) {
            Log.d(TAG, "-----minValue----->>>>>>" + minValue);
            Log.d(TAG, "-----maxValue----->>>>>>" + maxValue);
            leftProgress = minValue + scrollPos;
            rightProgress = maxValue + scrollPos;
            Log.d(TAG, "-----leftProgress----->>>>>>" + leftProgress);
            Log.d(TAG, "-----rightProgress----->>>>>>" + rightProgress);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "-----ACTION_DOWN---->>>>>>");
                    isSeeking = false;
                    videoPause();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "-----ACTION_MOVE---->>>>>>");
                    isSeeking = true;
                    mVideoView.seekTo((int) (pressedThumb == RangeSeekBar.Thumb.MIN ?
                            leftProgress : rightProgress));
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "-----ACTION_UP--leftProgress--->>>>>>" + leftProgress);
                    isSeeking = false;
                    //从minValue开始播
                    mVideoView.seekTo((int) leftProgress);
//                    videoStart();
                    break;
                default:
                    break;
            }
        }
    };


    private void videoStart() {
        Log.d(TAG, "----videoStart----->>>>>>>");
        mVideoView.start();
        positionIcon.clearAnimation();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        anim();
        handler.removeCallbacks(run);
        handler.post(run);
    }

    private void videoProgressUpdate() {
        long currentPosition = mVideoView.getCurrentPosition();
        Log.d(TAG, "----onProgressUpdate-cp---->>>>>>>" + currentPosition);
        if (currentPosition >= (rightProgress)) {
            mVideoView.seekTo((int) leftProgress);
            positionIcon.clearAnimation();
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
            anim();
        }
    }

    private void videoPause() {
        isSeeking = false;
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.pause();
            handler.removeCallbacks(run);
        }
        Log.d(TAG, "----videoPause----->>>>>>>");
        if (positionIcon.getVisibility() == View.VISIBLE) {
            positionIcon.setVisibility(View.GONE);
        }
        positionIcon.clearAnimation();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.seekTo((int) leftProgress);
//            videoStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying()) {
            videoPause();
        }
    }

    private Handler handler = new Handler();
    private Runnable run = new Runnable() {

        @Override
        public void run() {
            videoProgressUpdate();
            handler.postDelayed(run, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator != null) {
            animator.cancel();
        }
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        if (mExtractFrameWorkThread != null) {
            mExtractFrameWorkThread.stopExtract();
        }
        mUIHandler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        if (!TextUtils.isEmpty(outPutFileDirPath)) {
            File firstFile = new File(outPutFileDirPath);
            if (firstFile.exists()) {
                int lenth = firstFile.listFiles().length;
                File second = new File(secondOutPutFileDirPath);
                if (second.exists()) {
                    PictureUtils.deleteFile(second);
                }
                /*if (lenth > 3 || second.listFiles().length < thumbnailsCount) {
                    PictureUtils.deleteFile(second);
                }*/
            }
        }
        /*if (!TextUtils.isEmpty(mFinalPath)){
            PictureUtils.deleteFile(new File(mFinalPath));
        }*/
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }
}
