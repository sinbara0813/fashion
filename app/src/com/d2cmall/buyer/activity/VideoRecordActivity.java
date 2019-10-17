package com.d2cmall.buyer.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.PictureUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CircleProgressView;
import com.d2cmall.buyer.widget.CustomProgressDialog;
import com.d2cmall.buyer.widget.FocusIndicator;
import com.qiniu.pili.droid.shortvideo.PLAudioEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLCameraSetting;
import com.qiniu.pili.droid.shortvideo.PLCaptureFrameListener;
import com.qiniu.pili.droid.shortvideo.PLFaceBeautySetting;
import com.qiniu.pili.droid.shortvideo.PLFocusListener;
import com.qiniu.pili.droid.shortvideo.PLMicrophoneSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordStateListener;
import com.qiniu.pili.droid.shortvideo.PLShortVideoRecorder;
import com.qiniu.pili.droid.shortvideo.PLVideoEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLVideoFrame;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_LOW_MEMORY;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_MULTI_CODEC_WRONG;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_MUXER_START_FAILED;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_NO_VIDEO_TRACK;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SETUP_AUDIO_ENCODER_FAILED;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SETUP_CAMERA_FAILED;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SETUP_MICROPHONE_FAILED;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SETUP_VIDEO_DECODER_FAILED;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SETUP_VIDEO_ENCODER_FAILED;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SRC_DST_SAME_FILE_PATH;

/**
 * 作者:Created by sinbara on 2018/12/10.
 * 邮箱:hrb940258169@163.com
 */

public class VideoRecordActivity extends Activity implements PLRecordStateListener, PLVideoSaveListener, PLFocusListener {
    private static final String TAG = "VideoRecordActivity";
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public static final String PREVIEW_SIZE_RATIO = "PreviewSizeRatio";
    public static final String PREVIEW_SIZE_LEVEL = "PreviewSizeLevel";
    public static final String ENCODING_MODE = "EncodingMode";
    public static final String ENCODING_SIZE_LEVEL = "EncodingSizeLevel";
    public static final String ENCODING_BITRATE_LEVEL = "EncodingBitrateLevel";
    public static final String AUDIO_CHANNEL_NUM = "AudioChannelNum";
    public static final String DRAFT = "draft";

    private PLShortVideoRecorder mShortVideoRecorder;

    private CircleProgressView progressView;
    private CustomProgressDialog mProcessingDialog;
    private View mDeleteBtn,mConcatBtn;
    private View mSwitchCameraBtn,mAlbumBtn;
    private FocusIndicator mFocusIndicator;;

    private GestureDetector mGestureDetector;

    private PLCameraSetting mCameraSetting;
    private PLMicrophoneSetting mMicrophoneSetting;
    private PLRecordSetting mRecordSetting;
    private PLVideoEncodeSetting mVideoEncodeSetting;
    private PLAudioEncodeSetting mAudioEncodeSetting;
    private PLFaceBeautySetting mFaceBeautySetting;

    private int mFocusIndicatorX;
    private int mFocusIndicatorY;

    private OrientationEventListener mOrientationListener;
    private boolean isBegin;
    private long startTime;
    private long endTime;
    private TopicBean.DataBean.TopicsBean.ListBean topBean;
    private ImageView mIvPhoto;
    private String curPhotoPath;
    private long downTimeMillis;
    private String channel;//将图片视频发布到哪里?时尚圈=social,衣橱=wardrobe
    private String transactionTime;
    private int position;//发布穿搭成功对应要刷新的position
    private TextView tvTip;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);
        topBean= (TopicBean.DataBean.TopicsBean.ListBean) getIntent().getSerializableExtra("topic");
        channel = getIntent().getStringExtra("channel");
        position=getIntent().getIntExtra("position",0);
        transactionTime = getIntent().getStringExtra("transactionTime");
        progressView=findViewById(R.id.circle_progress_view);
        GLSurfaceView preview=findViewById(R.id.preview);
        mDeleteBtn = findViewById(R.id.delete);
        mConcatBtn = findViewById(R.id.concat);
        tvTip = findViewById(R.id.tv_tip);
        tvTip.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTip.setVisibility(View.GONE);
            }
        },2000);
        mIvPhoto = findViewById(R.id.iv_photo);
        mSwitchCameraBtn=findViewById(R.id.switch_tv);
        mFocusIndicator=findViewById(R.id.focus_indicator);

        mProcessingDialog = new CustomProgressDialog(this);
        mProcessingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShortVideoRecorder.cancelConcat();
            }
        });

        mShortVideoRecorder = new PLShortVideoRecorder();
        mShortVideoRecorder.setRecordStateListener(this);
        mShortVideoRecorder.setFocusListener(this);

        mCameraSetting = new PLCameraSetting();

        PLCameraSetting.CAMERA_FACING_ID facingId = chooseCameraFacingId();
        mCameraSetting.setCameraId(facingId);
        mCameraSetting.setCameraPreviewSizeRatio(PLCameraSetting.CAMERA_PREVIEW_SIZE_RATIO.RATIO_16_9);
        mCameraSetting.setCameraPreviewSizeLevel(PLCameraSetting.CAMERA_PREVIEW_SIZE_LEVEL.PREVIEW_SIZE_LEVEL_720P);

        mMicrophoneSetting = new PLMicrophoneSetting();
        mMicrophoneSetting.setChannelConfig(AudioFormat.CHANNEL_IN_MONO );

        mVideoEncodeSetting = new PLVideoEncodeSetting(this);
        mVideoEncodeSetting.setEncodingSizeLevel(PLVideoEncodeSetting.VIDEO_ENCODING_SIZE_LEVEL.VIDEO_ENCODING_SIZE_LEVEL_720P_3);
        mVideoEncodeSetting.setEncodingBitrate(4000 * 1000);
        mVideoEncodeSetting.setHWCodecEnabled(true);
        mVideoEncodeSetting.setConstFrameRateEnabled(true);

        mAudioEncodeSetting = new PLAudioEncodeSetting();
        mAudioEncodeSetting.setHWCodecEnabled(true);
        mAudioEncodeSetting.setChannels(1);

        mRecordSetting = new PLRecordSetting();
        mRecordSetting.setMaxRecordDuration(15*1000);
        mRecordSetting.setRecordSpeedVariable(true);
        mRecordSetting.setVideoCacheDir(Constants.VIDEO_STORAGE_DIR);
        mRecordSetting.setVideoFilepath(Constants.RECORD_FILE_PATH);

        mFaceBeautySetting = new PLFaceBeautySetting(1.0f, 0.5f, 0.5f);

        mShortVideoRecorder.prepare(preview, mCameraSetting, mMicrophoneSetting, mVideoEncodeSetting,
                mAudioEncodeSetting, mFaceBeautySetting, mRecordSetting);
        onSectionCountChanged(0, 0);

        mShortVideoRecorder.setRecordSpeed(1);
        progressView.setMaxTime(mRecordSetting.getMaxRecordDuration());
        progressView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                if (action==MotionEvent.ACTION_DOWN){
                    downTimeMillis = System.currentTimeMillis();
                    if (mShortVideoRecorder.beginSection()){
                        isBegin=true;
                        progressView.start();
                    }else {
                        Toast.makeText(VideoRecordActivity.this,"无法开始视频段录制",Toast.LENGTH_SHORT).show();
                    }
                }else if (action==MotionEvent.ACTION_UP){
                    if (isBegin){
                        progressView.stop();
                        mShortVideoRecorder.endSection();
                        isBegin=false;
                        startAnimation();
                    }
                    if(System.currentTimeMillis()-downTimeMillis<=200){
                        takePhoto();
                    }
                }
                return false;
            }
        });
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                mFocusIndicatorX = (int) e.getX() - mFocusIndicator.getWidth() / 2;
                mFocusIndicatorY = (int) e.getY() - mFocusIndicator.getHeight() / 2;
                mShortVideoRecorder.manualFocus(mFocusIndicator.getWidth(), mFocusIndicator.getHeight(), (int) e.getX(), (int) e.getY());
                return false;
            }
        });
        preview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation = getScreenRotation(orientation);
                if (!isBegin) {
                    mVideoEncodeSetting.setRotationInMetadata(rotation);
                }
            }
        };
        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        }
        if(Build.VERSION.SDK_INT>=23){
            checkPermission();
        }

    }

    private void takePhoto() {
        mShortVideoRecorder.captureFrame(new PLCaptureFrameListener() {
            @Override
            public void onFrameCaptured(PLVideoFrame capturedFrame) {
                if (capturedFrame == null) {
                    return;
                }
                FileOutputStream fos = null;
                curPhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2C/"+System.currentTimeMillis()+".jpg";
                try {
                    fos = new FileOutputStream(curPhotoPath);
                    capturedFrame.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    if(capturedFrame.toBitmap()!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mIvPhoto.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams layoutParams = mIvPhoto.getLayoutParams();
                                layoutParams.height=ScreenUtil.getDisplayHeight();
                                layoutParams.width=ScreenUtil.getDisplayWidth();
                                mIvPhoto.setLayoutParams(layoutParams);
                                mIvPhoto.setImageBitmap(capturedFrame.toBitmap());
                                mConcatBtn.setEnabled(true);
                                mDeleteBtn.setEnabled(true);
                            }
                        });

                    }
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermission() {
        boolean ret = true;

        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
            permissionsNeeded.add("CAMERA");
        }
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO)) {
            permissionsNeeded.add("MICROPHONE");
        }
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("Write external storage");
        }

        if (permissionsNeeded.size() > 0) {
            // Need Rationale
            String message = "是否申请以下权限 " + permissionsNeeded.get(0);
            for (int i = 1; i < permissionsNeeded.size(); i++) {
                message = message + ", " + permissionsNeeded.get(i);
            }
            // Check for Rationale Option
            if (!this.shouldShowRequestPermissionRationale(permissionsList.get(0))) {
                VideoRecordActivity.this.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            else {
                this.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            ret = false;
        }

        return ret;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        boolean ret = true;
        if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            ret = false;
        }
        return ret;
    }

    private void startAnimation(){
        mDeleteBtn.setVisibility(View.VISIBLE);
        mConcatBtn.setVisibility(View.VISIBLE);
        List<Animator> list=new ArrayList<>();
        if (!isLand()){
            list.add(createAnimator(mDeleteBtn,1,300,mDeleteBtn.getLeft()+ScreenUtil.dip2px(90),mDeleteBtn.getLeft()));
            list.add(createAnimator(mConcatBtn,1,300,mConcatBtn.getLeft()-ScreenUtil.dip2px(90),mConcatBtn.getLeft()));
        }else {
            list.add(createAnimator(mConcatBtn,2,300,90*3,0));
        }
        AnimatorSet resultSet = new AnimatorSet();
        resultSet.playTogether(list);
        resultSet.setInterpolator(new LinearInterpolator());
        resultSet.start();
        progressView.setVisibility(View.GONE);
    }

    private boolean isLand(){
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return false;
    }

    private Animator createAnimator(View view,int type,int duration, float... offer){
        Animator animator=null;
        if (type==1){
            animator=ObjectAnimator.ofFloat(view,"translationX",offer);
        }else {
            animator=ObjectAnimator.ofFloat(view,"translationY",offer);
        }
        animator.setDuration(duration);
        return animator;
    }

    private int getScreenRotation(int orientation) {
        int screenRotation = 0;
        boolean isPortraitScreen = getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (orientation >= 315 || orientation < 45) {
            screenRotation = isPortraitScreen ? 0 : 90;
        } else if (orientation >= 45 && orientation < 135) {
            screenRotation = isPortraitScreen ? 90 : 180;
        } else if (orientation >= 135 && orientation < 225) {
            screenRotation = isPortraitScreen ? 180 : 270;
        } else if (orientation >= 225 && orientation < 315) {
            screenRotation = isPortraitScreen ? 270 : 0;
        }
        return screenRotation;
    }

    private void onSectionCountChanged(final int count, final long totalTime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean isEnable=totalTime>=3*1000;
                mConcatBtn.setEnabled(isEnable);
                if (!isEnable&&count>0){
                    Toast.makeText(VideoRecordActivity.this,"视频太短了",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private PLCameraSetting.CAMERA_FACING_ID chooseCameraFacingId() {
        if (PLCameraSetting.hasCameraFacing(PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_3RD)) {
            return PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_3RD;
        } else if (PLCameraSetting.hasCameraFacing(PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK)) {
            return PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK;
        } else {
            return PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShortVideoRecorder.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShortVideoRecorder.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShortVideoRecorder.destroy();
        mOrientationListener.disable();
    }

    public void onClickDelete(View v) {
        if(mIvPhoto.getVisibility()==View.VISIBLE){
            mIvPhoto.setVisibility(View.INVISIBLE);
            mDeleteBtn.setVisibility(View.INVISIBLE);
            mConcatBtn.setVisibility(View.INVISIBLE);
            progressView.setVisibility(View.VISIBLE);
            curPhotoPath=null;
        }
        if (mShortVideoRecorder.deleteLastSection()){
            mDeleteBtn.setVisibility(View.INVISIBLE);
            mConcatBtn.setVisibility(View.INVISIBLE);
            progressView.setVisibility(View.VISIBLE);
        }
        mShortVideoRecorder.deleteLastSection();
        mDeleteBtn.setVisibility(View.INVISIBLE);
        mConcatBtn.setVisibility(View.INVISIBLE);
        progressView.setVisibility(View.VISIBLE);
        mConcatBtn.setEnabled(false);
        startTime=0;
        endTime=0;
    }

    public void onClickConcat(View v) {
        if(mIvPhoto.getVisibility()==View.VISIBLE && !Util.isEmpty(curPhotoPath)){
            Intent intent;
            ArrayList<String> photoPaths = new ArrayList<>();
            photoPaths.add(curPhotoPath);
            if ("social".equals(channel)) {
                intent = new Intent(VideoRecordActivity.this, Explore1PublishActivity.class);
                intent.putExtra("photoPaths", photoPaths);
                if (topBean != null) {
                    intent.putExtra("topic", topBean);
                }
            } else {
                intent = new Intent(VideoRecordActivity.this, FashionPublishActivity.class);
                intent.putExtra("transactionTime", transactionTime);
                intent.putExtra("photoPaths", photoPaths);
                intent.putExtra("position", position);
            }
            startActivity(intent);
        }else{
            mProcessingDialog.show();
            mShortVideoRecorder.concatSections(VideoRecordActivity.this);
        }

    }

    public void onClickSwitchCamera(View v) {
        mShortVideoRecorder.switchCamera();
        mFocusIndicator.focusCancel();
    }

    public void onClickBack(View v){
        finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    public void onClickAlbum(View view){
        int maxCount=9;
        if("wardrobe".equals(channel)){
            maxCount=6;
        }
        Matisse.from(VideoRecordActivity.this)
                .choose(MimeType.of(MimeType.JPEG,MimeType.MP4,MimeType.AVI, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .showSingleMediaType(true)
                .maxSelectable(maxCount)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(101);
    }

    @Override
    public void onManualFocusStart(boolean result) {
        if (result) {
            Log.i(TAG, "manual focus begin success");
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFocusIndicator.getLayoutParams();
            lp.leftMargin = mFocusIndicatorX;
            lp.topMargin = mFocusIndicatorY;
            mFocusIndicator.setLayoutParams(lp);
            mFocusIndicator.focus();
        } else {
            mFocusIndicator.focusCancel();
            Log.i(TAG, "manual focus not supported");
        }
    }

    @Override
    public void onManualFocusStop(boolean result) {
        Log.i(TAG, "manual focus end result: " + result);
        if (result) {
            mFocusIndicator.focusSuccess();
        } else {
            mFocusIndicator.focusFail();
        }
    }

    @Override
    public void onManualFocusCancel() {
        Log.i(TAG, "manual focus canceled");
        mFocusIndicator.focusCancel();
    }

    @Override
    public void onAutoFocusStart() {
        Log.i(TAG, "auto focus start");
    }

    @Override
    public void onAutoFocusStop() {
        Log.i(TAG, "auto focus stop");
    }

    @Override
    public void onReady() {
        //拍摄开始
    }

    @Override
    public void onError(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toastErrorCode(VideoRecordActivity.this,i);
            }
        });
    }

    @Override
    public void onDurationTooShort() {
    }

    @Override
    public void onRecordStarted() {
        //录像开始
        startTime=System.currentTimeMillis();
    }

    @Override
    public void onSectionRecording(long l, long l1, int i) {
        //录像中
    }

    @Override
    public void onRecordStopped() {
        //录像结束
        endTime=System.currentTimeMillis();
    }

    @Override
    public void onSectionIncreased(long l, long l1, int i) {
        onSectionCountChanged(i,l1);
    }

    @Override
    public void onSectionDecreased(long l, long l1, int i) {

    }

    @Override
    public void onRecordCompleted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                s(VideoRecordActivity.this, "已达到拍摄总时长");
                if (progressView!=null){
                    progressView.stop();
                }
            }
        });
    }

    @Override
    public void onSaveVideoSuccess(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcessingDialog.dismiss();
                //去编辑视频界面
                int index=s.lastIndexOf(".");
                String suffix="mp4";
                if (s.length()>index+1){
                    suffix=s.substring(index+1,s.length());
                }
                String state = Environment.getExternalStorageState();
                File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? getExternalCacheDir() : getCacheDir();
                File folderDir = new File(rootDir.getAbsolutePath() + PictureUtils.SHORT_VIDEO);
                if (!folderDir.exists() && folderDir.mkdirs()) {

                }
                String newPath=folderDir.getAbsolutePath()+"/"+System.currentTimeMillis()+"."+suffix;
                if (FileUtil.copyFile(s,newPath)){
                    Intent intent=new Intent(VideoRecordActivity.this,VideoEditActivity.class);
                    intent.putExtra("path",s);
                    intent.putExtra("duration",endTime-startTime);
                    intent.putExtra("transactionTime", transactionTime);
                    intent.putExtra("position", position);
                    intent.putExtra("channel", channel);
                    if (topBean!=null){
                        intent.putExtra("topic",topBean);
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onSaveVideoFailed(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcessingDialog.dismiss();
                s(VideoRecordActivity.this, "拼接视频段失败: " + i);
            }
        });
    }

    @Override
    public void onSaveVideoCanceled() {
        mProcessingDialog.dismiss();
    }

    @Override
    public void onProgressUpdate(final float v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProcessingDialog.setProgress((int) (100 * v));
            }
        });
    }

    public void toastErrorCode(Context context, int errorCode) {
        switch (errorCode) {
            case ERROR_SETUP_CAMERA_FAILED:
                s(context, "摄像头配置错误");
                break;
            case ERROR_SETUP_MICROPHONE_FAILED:
                s(context, "麦克风配置错误");
                break;
            case ERROR_NO_VIDEO_TRACK:
                s(context, "该文件没有视频信息！");
                break;
            case ERROR_SRC_DST_SAME_FILE_PATH:
                s(context, "源文件路径和目标路径不能相同！");
                break;
            case ERROR_MULTI_CODEC_WRONG:
                s(context, "当前机型暂不支持该功能");
                break;
            case ERROR_SETUP_VIDEO_ENCODER_FAILED:
                s(context, "视频编码器启动失败");
                break;
            case ERROR_SETUP_VIDEO_DECODER_FAILED:
                s(context, "视频解码器启动失败");
                break;
            case ERROR_SETUP_AUDIO_ENCODER_FAILED:
                s(context, "音频编码器启动失败");
                break;
            case ERROR_LOW_MEMORY:
                s(context, "手机内存不足，无法对该视频进行时光倒流！");
                break;
            case ERROR_MUXER_START_FAILED:
                s(context, "MUXER 启动失败, 请检查视频格式");
                break;
            default:
                s(context, "错误码： " + errorCode);
        }
    }

    public static void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == Activity.RESULT_OK && data != null && requestCode == 101) {
            List<String> strings = Matisse.obtainPathResult(data);
            if(strings!=null && strings.size()>0){
                int index = strings.get(0).lastIndexOf(".");
                String suffix = strings.get(0).substring(index + 1, strings.get(0).length());
                //图片
                if("jpeg".equalsIgnoreCase(suffix) || "jpg".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix)){
                    ArrayList<String> photos = new ArrayList<>();
                    if (strings != null && strings.size() > 0) {
                        photos.addAll(strings);
                    } else {
                        return;
                    }
                    Intent intent;
                    if("social".equals(channel)){
                        intent = new Intent(VideoRecordActivity.this, Explore1PublishActivity.class);
                        intent.putExtra("photoPaths", photos);
                        if (topBean != null) {
                            intent.putExtra("topic", topBean);
                        }
                        startActivity(intent);
                    }else{
                        intent = new Intent(VideoRecordActivity.this, FashionPublishActivity.class);
                        intent.putExtra("transactionTime", transactionTime);
                        intent.putExtra("photoPaths", photos);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                    //视频
                }else if(strings.size()==1 && ("mp4".equalsIgnoreCase(suffix) || "avi".equalsIgnoreCase(suffix) || "m4v".equalsIgnoreCase(suffix))){
                    for (String path : Matisse.obtainPathResult(data)) {
                        Intent intent=new Intent(VideoRecordActivity.this,VideoEditActivity.class);
                        intent.putExtra("path",path);
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(path);
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        long duration = mediaPlayer.getDuration();
                        intent.putExtra("duration", duration);
                        if (topBean!=null){
                            intent.putExtra("topic",topBean);
                        }
                        intent.putExtra("channel", channel);
                        intent.putExtra("transactionTime", transactionTime);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                }
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
