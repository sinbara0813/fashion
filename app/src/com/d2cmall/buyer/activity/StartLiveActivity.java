package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.CloseLiveApi;
import com.d2cmall.buyer.api.LiveApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CreateLiveBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CheckableLinearLayoutGroup;
import com.d2cmall.buyer.widget.SharePop;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;

import static com.qiniu.pili.droid.streaming.CameraStreamingSetting.FOCUS_MODE_CONTINUOUS_VIDEO;

/**
 * 开始直播页面
 * Author: Blend
 * Date: 16/8/5 9:56
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class StartLiveActivity extends BaseActivity implements StreamingStateChangedListener {

    @Bind(R.id.edit_title)
    EditText editTitle;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.img_set_cover)
    ImageView imgSetCover;
    @Bind(R.id.img_setting)
    ImageButton imgSetting;
    @Bind(R.id.cameraPreview_surfaceView)
    GLSurfaceView cameraPreviewSurfaceView;
    @Bind(R.id.cameraPreview_afl)
    AspectFrameLayout cameraPreviewAfl;
    @Bind(R.id.img_cancel)
    ImageButton imgCancel;
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.tv_upload)
    TextView tvUpload;
    @Bind(R.id.rl_cover)
    RelativeLayout rlCover;
    @Bind(R.id.tv_live_title)
    TextView tvLiveTitle;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.share_friend_circle)
    ImageView shareFriendCircle;
    @Bind(R.id.share_weixin)
    ImageView shareWeixin;
    @Bind(R.id.share_weibo)
    ImageView shareWeibo;
    @Bind(R.id.share_qq)
    ImageView shareQq;
    @Bind(R.id.share_qzone)
    ImageView shareQzone;

    private Dialog loadingDialog;
    private String title, cover, sdCover;
    private PopupWindow popupWindow;

    private Switch tbFront, tbLight;
    private float polishStep, whiteStep, redStep;
    private LiveListBean.DataBean.LivesBean.ListBean postLiveBean = null;
    public static long msgId;
    private CheckableLinearLayoutGroup modeMenu;
    private CameraStreamingSetting setting;
    private int shareChannel = -1;
    private ImageView ivs[];
    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;
    private int videoQuality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_live);
        ButterKnife.bind(this);
        polishStep = 0f;
        whiteStep = 0f;
        redStep = 0f;
        ivs = new ImageView[]{shareFriendCircle, shareWeixin, shareWeibo, shareQq, shareQzone};
        loadingDialog = DialogUtil.createLoadingDialog(this);
        initPopwindow();
        initPreview();
        initManager();
    }


    protected void initManager() {
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        // Decide FULL screen or real size
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
        int netType = Util.getAPNType(this);
        switch (netType) {
            case -1://没有网络
                break;
            case 1://wifi网络
                videoQuality = StreamingProfile.VIDEO_QUALITY_HIGH3;
                break;
            case 2://移动网络
                videoQuality = StreamingProfile.VIDEO_QUALITY_MEDIUM1;
                break;
        }
        try {
            mProfile = new StreamingProfile();
            mProfile.setVideoQuality(videoQuality)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_HIGH2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY);
            // You can invoke this before startStreaming, but not in initialization phase.
            CameraStreamingSetting.FaceBeautySetting beautySetting = new CameraStreamingSetting.FaceBeautySetting(0f, 0f, 0f);
            setting = new CameraStreamingSetting();
            setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
                    .setContinuousFocusModeEnabled(true)
                    .setFocusMode(FOCUS_MODE_CONTINUOUS_VIDEO)
                    .setBuiltInFaceBeautyEnabled(true)
                    .setFaceBeautySetting(beautySetting)
                    .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
            if (afl != null && glSurfaceView != null) {
                mMediaStreamingManager = new MediaStreamingManager(this, afl, glSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
                mMediaStreamingManager.setStreamingStateListener(this);
                mMediaStreamingManager.prepare(setting, mProfile);
            } else {
                Log.e("live", "初始化出错了");
            }
        } catch (Exception e) {
            Log.e("live", "初始化出错了" + e.getMessage());
            e.printStackTrace();
        }
    }


    private void initPopwindow() {
        View settingPopView = getLayoutInflater().inflate(R.layout.layout_live_setting_pop, null, false);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = Math.round(230 * dm.density);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        String[] filterDatas = getResources().getStringArray(R.array.label_live_filters);
        SeekBar seekbarPolish = (SeekBar) settingPopView.findViewById(R.id.seekbar_polish);
        SeekBar seekbarWhite = (SeekBar) settingPopView.findViewById(R.id.seekbar_white);
        SeekBar seekRed = (SeekBar) settingPopView.findViewById(R.id.seekbar_red);
        tbFront = (Switch) settingPopView.findViewById(R.id.tb_front);
        tbLight = (Switch) settingPopView.findViewById(R.id.tb_light);
        modeMenu = (CheckableLinearLayoutGroup) settingPopView.findViewById(R.id.screen_mode_menu);
        modeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = modeMenu.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.screen_mode1_layout://标清

                        break;
                    case R.id.screen_mode2_layout://高清

                        break;
                }
            }
        });
        ArrayList<String> filterList = new ArrayList<>();
        for (int i = 0; i < filterDatas.length; i++) {
            filterList.add(filterDatas[i]);
        }

        seekbarPolish.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //磨皮
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraStreamingSetting.FaceBeautySetting fbSetting = setting.getFaceBeautySetting();
                fbSetting.beautyLevel = (float) (seekBar.getProgress()) / 10;
                polishStep = fbSetting.beautyLevel;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraStreamingSetting.FaceBeautySetting fbSetting = setting.getFaceBeautySetting();
                fbSetting.redden = (float) (seekBar.getProgress()) / 10;
                redStep = fbSetting.redden;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbarWhite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //美白
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                CameraStreamingSetting.FaceBeautySetting fbSetting = setting.getFaceBeautySetting();
                fbSetting.whiten = (float) (seekBar.getProgress()) / 10;
                whiteStep = fbSetting.whiten;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        tbLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mMediaStreamingManager != null) {
                    if (isChecked) {
                        mMediaStreamingManager.turnLightOn();
                    } else {
                        mMediaStreamingManager.turnLightOff();
                    }
                }
            }
        });
        tbFront.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mMediaStreamingManager != null) {
                    mMediaStreamingManager.switchCamera();
                }
            }
        });
        popupWindow = new PopupWindow(settingPopView, width, point.y - Util.getStatusHeightOther(this), true);
        popupWindow.setFocusable(true); //设置点击menu以外其他地方以及返回键退出
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initPreview() {
        // 6.0及以上的系统需要在运行时申请CAMERA RECORD_AUDIO权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(StartLiveActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(StartLiveActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StartLiveActivity.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 101);
            } else {
                startPreview();
            }
        } else {
            startPreview();
        }
    }

    private void startPreview() {
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.resume();
        }
    }


    private void startLive() {
        if (Util.isEmpty(sdCover)) {
            Util.showToast(this, R.string.label_upload_cover);
            return;
        }
        if (Util.isEmpty(editTitle.getText().toString())) {
            Util.showToast(this, "您还没有标题呢");
            return;
        }
        if (Util.loginChecked(this, Constants.Login.START_LIVE_LOGIN)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, Constants.RequestCode.PERMISSION);
                } else {
                    //上传图片后开始直播
                    uploadFile(sdCover);
                }
            } else {
                uploadFile(sdCover);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.RequestCode.PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    uploadFile(sdCover);
                } else {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Util.showToast(StartLiveActivity.this, R.string.msg_allow_camera_permission);
                        break;
                    }
                    if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        Util.showToast(StartLiveActivity.this, R.string.msg_open_recorder_permission);
                        break;
                    }
                }
                break;
            case Constants.RequestCode.REQUEST_PERMISSION:
                if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    choosePic();
                } else {
                    Util.showToast(StartLiveActivity.this, "请开启权限");
                }
                break;
        }
    }


    public void showPop() {
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        boolean readPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && (!cameraPermission || !readPermission)) {
            requestPermission();
        } else {
            choosePic();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                Constants.RequestCode.REQUEST_PERMISSION);
    }

    private void choosePic() {
        Matisse.from(StartLiveActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectable(1)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(456);
    }

    private void uploadFile(String filePath) {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_FRIEND));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                if (!isSuccess) {
                    Util.showToast(StartLiveActivity.this, R.string.label_upload_failed);
                    loadingDialog.dismiss();
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    cover = jsonObject.optString("url");
                    LivePublish();//直播
                } catch (JSONException e) {
                }
            }
        };
        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + Constants.UPYUN_KEY);
            }
        };
        UploadManager.getInstance().blockUpload(file, paramsMap, signatureListener, completeListener, null);
        loadingDialog.show();
    }

    private void share() {
        //分享
        SharePop sharePop = new SharePop(StartLiveActivity.this, false);
        sharePop.setWebView(true);
        String title = postLiveBean.getNickname() + getResources().getString(R.string.live_share_new_txt);
        sharePop.setDescription(title);
        sharePop.setTitle(getString(R.string.live_share_txt,
                postLiveBean.getTitle()));
        if (!Util.isEmpty(postLiveBean.getHeadPic())) {
            sharePop.setImage(Util.getD2cPicUrl(postLiveBean.getHeadPic(), 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(postLiveBean.getCover(), 360, 500), true);
        }
        sharePop.setWebUrl(Constants.SHARE_URL + "/live/" + postLiveBean.getId());
        shareOut(sharePop);
    }

    private void LivePublish() {
        title = editTitle.getText().toString().trim();
        LiveApi api = new LiveApi();
        if (!Util.isEmpty(title)) {
            api.setTitle(title);
        } else {
            title = "Title";
        }
        api.setCover(cover);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CreateLiveBean>() {
            @Override
            public void onResponse(CreateLiveBean postLiveBean) {
                msgId = postLiveBean.getData().getLive().getId();
                loadingDialog.dismiss();
                StartLiveActivity.this.postLiveBean = postLiveBean.getData().getLive();
                share();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    String errorJson = httpError.getErrorBean().getJsonData();
                    try {
                        JSONObject errorInfo = new JSONObject(errorJson);
                        JSONObject dataInfo = errorInfo.getJSONObject("data");
                        JSONObject oldLiveInfo = dataInfo.getJSONObject("old");
                        final LiveListBean.DataBean.LivesBean.ListBean dataBean = buildBean(oldLiveInfo);
                        new AlertDialog.Builder(StartLiveActivity.this)
                                .setMessage(R.string.msg_goon_live)
                                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        postLiveBean = dataBean;
                                        share();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        anchorOrWatcherLogout(dataBean.getId());
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        Util.showToast(StartLiveActivity.this, Util.checkErrorType(error));
                        e.printStackTrace();
                    }
                } else {
                    Util.showToast(StartLiveActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

    private LiveListBean.DataBean.LivesBean.ListBean buildBean(JSONObject oldLiveInfo) throws Exception {
        LiveListBean.DataBean.LivesBean.ListBean dataBean = new LiveListBean.DataBean.LivesBean.ListBean();
        dataBean.setId(oldLiveInfo.getInt("id"));
        dataBean.setStreamId(oldLiveInfo.getString("streamId"));
        dataBean.setPushUrl(oldLiveInfo.getString("pushUrl"));
        dataBean.setTitle(oldLiveInfo.getString("title"));
        dataBean.setSex(oldLiveInfo.getString("sex"));
        dataBean.setRtmpUrl(oldLiveInfo.getString("rtmpUrl"));
        dataBean.setHeadPic(oldLiveInfo.getString("headPic"));
        dataBean.setDesignerName(oldLiveInfo.getString("designerName"));
        dataBean.setCover(oldLiveInfo.getString("cover"));
        dataBean.setHdlUrl(oldLiveInfo.getString("hdlUrl"));
        dataBean.setNickname(oldLiveInfo.getString("nickname"));
        dataBean.setMemberId(oldLiveInfo.getInt("memberId"));
        dataBean.setMemberType(oldLiveInfo.getInt("memberType"));
        dataBean.setStatus(oldLiveInfo.getInt("status"));
        dataBean.setHlsUrl(oldLiveInfo.getString("hlsUrl"));
        dataBean.setReplayUrl(oldLiveInfo.getString("replayUrl"));
        return dataBean;
    }

    protected void anchorOrWatcherLogout(long id) {
        //主播退出直播
        CloseLiveApi api = new CloseLiveApi();
        api.setId(id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(StartLiveActivity.this, "上一次未完成直播已关闭,请重新开启直播");
                //LivePublish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(StartLiveActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void shareOut(SharePop sharePop) {
        switch (shareChannel) {
            case Constants.TYPE_CIRCLE:
                sharePop.shareIn();
                sharePop.share2WxFriend();
                break;
            case Constants.TYPE_WEIXIN:
                sharePop.shareIn();
                sharePop.share2Wx();
                break;
            case Constants.TYPE_WEIBO:
                sharePop.shareIn();
                sharePop.share2Sina();
                break;
            case Constants.TYPE_QQ:
                sharePop.shareIn();
                sharePop.share2QQ();
                break;
            case Constants.TYPE_QZONE:
                sharePop.shareIn();
                sharePop.share2Zone();
                break;
            default:
                LiveAnchorActivity.actionStart(StartLiveActivity.this, title, !tbFront.isChecked(),
                        tbLight.isChecked(), whiteStep, polishStep, redStep,
                        mProfile.getCurrentVideoQuality(),
                        postLiveBean);
                finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.Login.START_LIVE_LOGIN:
                    break;
                case 456:
                    String path = Matisse.obtainPathResult(data).get(0);
                    rlCover.setVisibility(View.INVISIBLE);
                    ivCover.setVisibility(View.VISIBLE);
                    ivCover.setImageBitmap(BitmapUtils.getEqualRatioThumbnailBitmap(
                            path, 200, 200));
                    sdCover = path;
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        Util.onResume(this);
        startPreview();
        if (postLiveBean != null) {
            LiveAnchorActivity.actionStart(StartLiveActivity.this, title, !tbFront.isChecked(),
                    tbLight.isChecked(), whiteStep, polishStep, redStep,
                    mProfile.getCurrentVideoQuality(),
                    postLiveBean);
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaStreamingManager.destroy();
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {
        switch (streamingState) {
            case PREPARING:
                Log.e("123", "PREPARING");
                break;
            case READY:
                // start streaming when READY
                break;
            case CONNECTING:
                Log.e("123", "连接中");
                break;
            case STREAMING:
                Log.e("123", "The av packet had been sent.");
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                Log.e("123", "推流结束");
                finish();
                // The streaming had been finished.
                break;
            case IOERROR:
                Log.e("123", "推流异常");
                finish();
                // Network connect error.
                break;
            case OPEN_CAMERA_FAIL:
                Log.e("123", "相机打开失败");
                // Failed to open camera.
                break;
            case DISCONNECTED:
                Log.e("123", "推流失去连接");
                finish();
                // The socket is broken while streaming
                break;
        }
    }

    @OnClick({R.id.img_cancel, R.id.img_setting, R.id.iv_cover, R.id.rl_cover, R.id.btn_start, R.id.share_friend_circle, R.id.share_weixin, R.id.share_weibo, R.id.share_qq, R.id.share_qzone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                super.onBackPressed();
                break;
            case R.id.img_setting:
                KeyboardUtil.hideKeyboard(editTitle);
                popupWindow.showAtLocation(imgSetting, Gravity.END, 0, Util.getStatusHeightOther(this));
                break;
            case R.id.iv_cover:
                showPop();
                break;
            case R.id.rl_cover:
                showPop();
                break;
            case R.id.btn_start:
                startLive();
                break;
            case R.id.share_friend_circle:
                setState(shareFriendCircle, Constants.TYPE_CIRCLE);
                break;
            case R.id.share_weixin:
                setState(shareWeixin, Constants.TYPE_WEIXIN);
                break;
            case R.id.share_weibo:
                setState(shareWeibo, Constants.TYPE_WEIBO);
                break;
            case R.id.share_qq:
                setState(shareQq, Constants.TYPE_QQ);
                break;
            case R.id.share_qzone:
                setState(shareQzone, Constants.TYPE_QZONE);
                break;
        }
    }

    private void setState(ImageView imageView, int type) {
        if (imageView.isSelected()) {
            shareChannel = -1;
        } else {
            shareChannel = type;
        }
        for (int i = 0; i < ivs.length; i++) {
            ImageView iv = ivs[i];
            if (imageView == iv) {
                iv.setSelected(!iv.isSelected());
            } else {
                iv.setSelected(false);
            }
        }
    }
}
