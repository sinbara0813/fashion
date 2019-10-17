package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PhotoBuyAdapter;
import com.d2cmall.buyer.api.BindRecommendApi;
import com.d2cmall.buyer.api.SearchPicApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BindRecommendBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.PhotoProductBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.RGBLuminanceSource;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.HybridBinarizer;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import google.zxing.client.AmbientLightManager;
import google.zxing.client.BeepManager;
import google.zxing.client.InactivityTimer;
import google.zxing.client.IntentSource;
import google.zxing.client.camera.CameraManager;
import google.zxing.client.decode.CaptureActivityHandler;
import google.zxing.client.decode.DecodeFormatManager;
import google.zxing.client.view.ViewfinderView;

public class ScanQrCodeActivity extends BaseActivity implements SurfaceHolder.Callback {

    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;
    private CameraManager cameraManager;
    private ViewfinderView viewfinderView;
    private CaptureActivityHandler handler;
    private Result lastResult;
    private Collection<BarcodeFormat> decodeFormats;
    private String characterSet;
    private Result savedResultToShow;
    private IntentSource source;
    private Button btnLight;
    private ImageView ivScan, ivCamera;
    private boolean isLightOn;
    private Dialog loadingDialog;
    private UserBean.DataEntity.MemberEntity user;
    private MultiFormatReader multiFormatReader;
    private boolean isPhotoBuy = false;
    private ImageView right;
    private ProgressBar progressBar;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> productList;
    private PhotoBuyAdapter photoBuyAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private int page = 1;
    private boolean hasNext;
    private RecyclerView recyclerView;
    private ImageView close;
    private RelativeLayout rl_scan;
    private ImageView ivPhotoButton;
    private boolean isScanMode = true;
    private ImageView iv_result;
    private RelativeLayout rl_result;
    private ImageView iv_animation;
    private Animation animation;
    private long nowTime;
    private PopupWindow popupWindow;
    private RelativeLayout rl_all;
    private View top_view;
    private ImageView ivGoPhoto;
    private boolean safeToTakePicture;
    private LinearLayout llBottom;
    private SurfaceHolder surfaceHolder;
    private RelativeLayout rl_cancle;
    private boolean hasCancle;
    private ImageView ivCancle;
    //获取照片中的接口回调
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            safeToTakePicture = true;
            if (null != data) {
                //直接根据data获得bitmap
                rl_result.setVisibility(View.VISIBLE);
                ivPhotoButton.setVisibility(View.GONE);
                llBottom.setVisibility(View.GONE);
                rl_cancle.setVisibility(View.VISIBLE);
                //if (!realBitmap.isRecycled()) {
                //iv_result.setImageBitmap(realBitmap);
                //}
                iv_animation.startAnimation(animation);
                FileOutputStream fos = null;
                nowTime = System.currentTimeMillis();
                String mFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + nowTime + "D2C.png";
                //文件
                File tempFile = new File(mFilePath);
                try {
                    //
                    fos = new FileOutputStream(tempFile);
                    fos.write(data);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                uploadFile(mFilePath);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_qrcode);
        //((D2CApplication) getApplication()).initTu();
        hasSurface = false;
        productList = new ArrayList<>();
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        iv_animation = (ImageView) findViewById(R.id.iv_animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.scan_animation_top_down);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        ivGoPhoto = (ImageView) findViewById(R.id.iv_go_photo);
        ivCancle = (ImageView) findViewById(R.id.iv_cancle);
        ivScan = (ImageView) findViewById(R.id.iv_scan);
        rl_cancle = (RelativeLayout) findViewById(R.id.rl_cancle);
        top_view = findViewById(R.id.top_view);
        rl_result = (RelativeLayout) findViewById(R.id.rl_result);
        iv_result = (ImageView) findViewById(R.id.iv_result);
        rl_all = (RelativeLayout) findViewById(R.id.rl_all);
        ivPhotoButton = (ImageView) findViewById(R.id.iv_photo_button);
        ivCamera = (ImageView) findViewById(R.id.iv_photo);
        rl_scan = (RelativeLayout) findViewById(R.id.rl_scan);
        right = (ImageView) findViewById(R.id.tv_right);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view); // 预览
        surfaceHolder = surfaceView.getHolder();
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                hasCancle = true;
                rl_result.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                ivPhotoButton.setVisibility(View.VISIBLE);
                rl_cancle.setVisibility(View.GONE);
                iv_result.setImageDrawable(null);
                cameraManager.startPre();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //翻转摄像头
                if (cameraManager != null) {
                    cameraManager.changeCameraId(surfaceHolder);
                }
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ivGoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isScanMode) {
                    onAlbum(456);
                } else {
                    onAlbum(789);
                }
            }
        });
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCamera.setImageResource(R.mipmap.icon_camera_sel);
                ivScan.setImageResource(R.mipmap.icon_scan_unsel);
                ivPhotoButton.setVisibility(View.VISIBLE);
                isScanMode = false;
                rl_scan.setVisibility(View.GONE);
            }
        });
        ivPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraManager != null) {
                    if (safeToTakePicture) {
                        cameraManager.takePhoto(mPictureCallback);
                        safeToTakePicture = false;
                    }

                }
            }
        });
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCamera.setImageResource(R.mipmap.icon_camera_unsel);
                ivScan.setImageResource(R.mipmap.icon_scan_sel);
                ivPhotoButton.setVisibility(View.GONE);
                isScanMode = true;
                rl_scan.setVisibility(View.VISIBLE);
            }
        });
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        photoBuyAdapter = new PhotoBuyAdapter(this, itemWidth, productList);
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(photoBuyAdapter);
        initDialog();
        ambientLightManager = new AmbientLightManager(this);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        user = Session.getInstance().getUserFromFile(ScanQrCodeActivity.this);
    }


    private void initDialog() {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        close = (ImageView) view.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(this);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rl_result.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                rl_cancle.setVisibility(View.GONE);
                iv_result.setImageDrawable(null);
                cameraManager.startPre();
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        recyclerView.setLayoutManager(virtualLayoutManager);
        recyclerView.setAdapter(delegateAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.onResume(this);
        showCamera();
    }

    private void showCamera(){
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        btnLight = (Button) findViewById(R.id.btn_light);
        View bottomLayout = findViewById(R.id.bottom_layout);
        Point point = Util.getDeviceSize(this);
        bottomLayout.getLayoutParams().height = Math.round((point.y - point.x * 7 / 10) * 2 / 3);
        handler = null;
        lastResult = null;
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view); // 预览
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            surfaceHolder.addCallback(this);
        }
        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);
        inactivityTimer.onResume();
        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        if (cameraManager!=null){
            if (handler != null) {
                handler.quitSynchronously();
                handler = null;
            }
            inactivityTimer.onPause();
            ambientLightManager.stop();
            beepManager.close();
            cameraManager.closeDriver();
            if (!hasSurface) {
                SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
                SurfaceHolder surfaceHolder = surfaceView.getHolder();
                surfaceHolder.removeCallback(this);
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        viewfinderView.recycleLineDrawable();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.zoomIn();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.zoomOut();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constants.RequestCode.REQUEST_PERMISSION);
        }else {
            openCamera(holder);
        }
    }

    private void openCamera(SurfaceHolder holder){
        if (!hasSurface) {
            hasSurface = true;
            try {
                initCamera(holder);
            } catch (Exception e) {
                Util.showToast(ScanQrCodeActivity.this, R.string.msg_camera_permission_error);
                finish();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public void handleDecode(Result rawResult, Bitmap bitmap) {
        inactivityTimer.onActivity();
        lastResult = rawResult;
        beepManager.playBeepSoundAndVibrate();
        final String result = ResultParser.parseResult(rawResult).toString();
        if (result.equals(Constants.SCAN_URL)) {
            /*Intent intent = new Intent(ScanQrCodeActivity.this, WebActivity.class);
            intent.putExtra("url", result);
            intent.putExtra("type", 1);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);*/
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Constants.SCAN_URL));
            startActivity(intent);
        } else {
            final Matcher matcher = Pattern.compile("^inviteCode:(\\w+)").matcher(result);
            if (matcher.matches()) {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.msg_bind_recommend_code, matcher.group(1)))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (user != null) {
                                    if (user.isD2c()) {
                                        requestBindRecommend(matcher.group(1));
                                    } else {
                                        Intent intentBind = new Intent(ScanQrCodeActivity.this, BindPhoneActivity.class);
                                        startActivityForResult(intentBind, Constants.RequestCode.BIND_PHONE);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                                    }
                                } else {
                                    Intent intent = new Intent(ScanQrCodeActivity.this, LoginActivity.class);
                                    startActivityForResult(intent, Constants.Login.SCAN_LOGIN);
                                    overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetCapture();
                            }
                        })
                        .show();

            } else if (result.contains(".d2cmall.com")) {
                Matcher m = Pattern.compile("\\S+parent_id=(\\d+)").matcher(result);
                if (m.find()) {
                    try {
                        int parent_id = Integer.parseInt(m.group(1));
                        D2CApplication.mSharePref.putSharePrefInteger(SharePrefConstant.PARENT_ID, parent_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Util.urlAction(this, result);
            } else if (result.startsWith("http://")) {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.label_no_safe_link))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ScanQrCodeActivity.this, WebActivity.class);
                                intent.putExtra("url", result);
                                intent.putExtra("type", 1);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetCapture();
                            }
                        })
                        .show();
            } else {
                Util.showToast(ScanQrCodeActivity.this, result);
                resetCapture();
            }
        }
    }

    private void requestBindRecommend(String code) {
        loadingDialog.show();
        BindRecommendApi api = new BindRecommendApi();
        api.setRecCode(code);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BindRecommendBean>() {
            @Override
            public void onResponse(BindRecommendBean bindRecommendBean) {
                loadingDialog.dismiss();
                Util.showToast(ScanQrCodeActivity.this, R.string.msg_bind_recommend_ok);
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(ScanQrCodeActivity.this);
                user.setRecId(bindRecommendBean.getData().getRecId());
                Session.getInstance().saveUserToFile(ScanQrCodeActivity.this, user);
                Intent intent = new Intent(ScanQrCodeActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.TABCHANGE, 4));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resetCapture();
                loadingDialog.dismiss();
                Util.showToast(ScanQrCodeActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public void resetCapture() {
        if ((source == IntentSource.NONE) && lastResult != null) {
            restartPreviewAfterDelay(0L);
        }
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private void resetStatusView() {
        if (isScanMode) {
            viewfinderView.setVisibility(View.VISIBLE);
        }
        lastResult = null;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            return;
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        null, characterSet, cameraManager);
                safeToTakePicture = true;
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (Exception ex) {
            Util.showToast(ScanQrCodeActivity.this, R.string.msg_camera_framework_bug);
            finish();
        }
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    public void onLight(View v) {
        if (isLightOn) {
            cameraManager.setTorch(false);
            btnLight.setSelected(false);
            btnLight.setText(R.string.label_open_light);
        } else {
            cameraManager.setTorch(true);
            btnLight.setSelected(true);
            btnLight.setText(R.string.label_close_light);
        }
        isLightOn = !isLightOn;
    }

    public void onAlbum(int code) {
        Matisse.from(ScanQrCodeActivity.this)
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
                .forResult(code);
    }

    public void onBackPressed(View v) {
        finish();
    }

    protected Result scanningImage(String bitmapPath, boolean isSelf) {
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        options.inJustDecodeBounds = false;
        Bitmap scaleBitmap = BitmapFactory.decodeFile(bitmapPath, options);
        if (isSelf) {
            options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(bitmapPath, options);
            scaleBitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() - bitmap.getWidth() / 3, bitmap.getWidth() / 3, bitmap.getWidth() / 3);
            scaleBitmap = BitmapUtils.getScaleBitmap(scaleBitmap, 3, 3);
            bitmap.recycle();
            bitmap = null;
        }
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(scaleBitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        //开始解析
        Result result = null;
        try {
            result = getReader().decodeWithState(binaryBitmap);
        } catch (Exception e) {
        } finally {
            if (multiFormatReader != null) {
                multiFormatReader.reset();
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
            if (scaleBitmap != null) {
                scaleBitmap.recycle();
            }
        }
        return result;
    }

    private MultiFormatReader getReader() {
        if (multiFormatReader == null) {
            multiFormatReader = new MultiFormatReader();

            // 解码的参数
            Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(
                    2);
            // 可以解析的编码类型
            Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
            if (decodeFormats == null || decodeFormats.isEmpty()) {
                decodeFormats = new Vector<BarcodeFormat>();

                // 这里设置可扫描的类型，我这里选择了都支持
                decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
                decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
                decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
            }
            hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

            // 设置继续的字符编码格式为UTF8
            hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

            // 设置解析配置参数
            multiFormatReader.setHints(hints);
        }
        return multiFormatReader;
    }

    private void loadPhotoData(String url) {
        SearchPicApi api = new SearchPicApi();
        api.setPicUrl(url);
        api.pageNumber = page;
        Log.e("tag", url);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PhotoProductBean>() {
            @Override
            public void onResponse(PhotoProductBean response) {
                if (isFinishing()) {
                    return;
                }
                if (page == 1) {
                    productList.clear();
                }
                //progressBar.setVisibility(View.GONE);
                productList.addAll(response.getData().getProducts().getList());
                photoBuyAdapter.notifyDataSetChanged();
                iv_animation.clearAnimation();
                ivPhotoButton.setVisibility(View.VISIBLE);
                popupWindow.showAsDropDown(top_view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressBar.setVisibility(View.GONE);
                iv_animation.clearAnimation();
                llBottom.setVisibility(View.VISIBLE);
                ivPhotoButton.setVisibility(View.VISIBLE);
                rl_cancle.setVisibility(View.GONE);
                rl_result.setVisibility(View.GONE);
                iv_result.setImageDrawable(null);
                cameraManager.startPre();
                Util.showToast(ScanQrCodeActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void uploadFile(String localUrl) {
        File file = new File(localUrl);
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(0, "tmp"));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    Log.e("tag", result);
                    JSONObject jsonObject = new JSONObject(result);
                    String url = jsonObject.optString("url");
                    rl_cancle.setVisibility(View.GONE);
                    if (hasCancle) {
                        hasCancle = false;
                        return;
                    }
                    loadPhotoData(Constants.IMG_HOST + url);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.Login.SCAN_LOGIN:
                    finish();
                    break;
                case Constants.RequestCode.BIND_PHONE:
                    user = Session.getInstance().getUserFromFile(this);
                    break;
                case 456://扫一扫
                    String path = Matisse.obtainPathResult(data).get(0);
                    if (!Util.isEmpty(path)) {
                        Result results = scanningImage(path, false);
                        if (results == null) {
                            results = scanningImage(path, true);
                            if (results != null) {
                                handleDecode(results, null);
                            } else {
                                Toast.makeText(ScanQrCodeActivity.this, "图片识别失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            handleDecode(results, null);
                        }
                    }
                    break;
                case 789://拍照购
                    //progressBar.setVisibility(View.VISIBLE);
                    String path2 = Matisse.obtainPathResult(data).get(0);
                    Uri uri = Matisse.obtainResult(data).get(0);
                    rl_result.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                    rl_cancle.setVisibility(View.VISIBLE);
                    ivPhotoButton.setVisibility(View.GONE);
                    Glide.with(this).load(uri).into(iv_result);
                    iv_animation.startAnimation(animation);
                    uploadFile(path2);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!checkGrantResults(grantResults)){
            finish();
        }else {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view); // 预览
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            openCamera(surfaceHolder);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkGrantResults(int[] grantResults) {
        boolean result=true;
        int size=grantResults.length;
        for (int i=0;i<size;i++){
            if (grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return result;
    }
}
