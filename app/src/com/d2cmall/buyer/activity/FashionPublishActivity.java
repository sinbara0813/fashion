package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.ExplorePublishApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.bean.WeatherBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.MyCheckBox;
import com.d2cmall.buyer.widget.ProcessImageView;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.Constants.RequestCode.EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE;

/**
 * 作者:Created by sinbara on 2018/11/22.
 * 邮箱:hrb940258169@163.com
 */

public class FashionPublishActivity extends BaseActivity {

    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.publish_tv)
    TextView publishTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.pic_ll)
    LinearLayout picLl;
    @Bind(R.id.checkbox)
    MyCheckBox checkbox;
    @Bind(R.id.tv_date)
    TextView tvDate;


    private ArrayList<JsonPic> jsonPics;
    private JsonPic emptyPic;
    private HashMap<String, String> imgUpyunPaths;
    private List<String> deletePaths=new ArrayList<>();
    private UserBean.DataEntity.MemberEntity user;
    private int imageSize;
    private Dialog loadingDialog;
    private int uploadedNum;
    private boolean checked;
    private ArrayList<View> picView;
    private View emptyView;
    private InputMethodManager im;
    private int maxSelected=6;
    private String cityName;
    private String temp;
    private String weather;
    private String transactionTime;
    private int position;
    private String url;
    private ArrayList<String> photoPaths;//拍照传过来的图片地址
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_publish);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imageSize = (ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(60))/3;
        transactionTime=getIntent().getStringExtra("transactionTime");
        videoUrl=getIntent().getStringExtra("videoUrl");
        photoPaths = getIntent().getStringArrayListExtra("photoPaths");
        if(!Util.isEmpty(transactionTime) && transactionTime.contains(" ") && transactionTime.contains("-")){
            tvDate.setText(transactionTime.substring(transactionTime.indexOf("-")+1, transactionTime.indexOf(" ")));
        }else{
            long currentTimeMillis = System.currentTimeMillis();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis));
            tvDate.setText(date.substring(date.indexOf("-")+1, date.indexOf(" ")));
        }
        position = getIntent().getIntExtra("position", 0);
        user = Session.getInstance().getUserFromFile(this);
        cityName=FashionMatchActivity.city;
        if (cityName!=null){
            loadWeather(cityName);
        }
        jsonPics = new ArrayList<>();
        imgUpyunPaths = new HashMap<>();
        picView = new ArrayList<>();
        emptyPic = new JsonPic();
        jsonPics.add(emptyPic);
        addPic(jsonPics);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        checkbox.setCheckColorId(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
        checkbox.setChecked(true);
        handPermission(photoPaths==null && videoUrl==null);
    }

    private void loadWeather(String cityName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Util.isEmpty(transactionTime)){
                        String jsonStr=HttpUtils.doGet(String.format(Constants.WEATHER_URL, URLEncoder.encode(cityName,"utf-8")));
                        Log.e("han","weather=="+jsonStr);
                        Gson gson=new Gson();
                        WeatherBean weatherBean=gson.fromJson(jsonStr,WeatherBean.class);
                        if (weatherBean!=null&&weatherBean.getHeWeather5()!=null){
                            temp=weatherBean.getHeWeather5().get(0).getNow().getTmp();
                            weather=weatherBean.getHeWeather5().get(0).getNow().getCond().getTxt();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void handPermission(boolean empty) {
        // 定位权限组
        String[] mPermissionGroup = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // 过滤已持有的权限
        List<String> mRequestList = new ArrayList<>();
        for (String permission : mPermissionGroup) {
            if ((ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)) {
                mRequestList.add(permission);
            }
        }

        // 申请未持有的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !mRequestList.isEmpty()) {
            ActivityCompat.requestPermissions(this, mRequestList.toArray(
                    new String[mRequestList.size()]), Constants.RequestCode.REQUEST_PERMISSION);
        } else {
            // 权限都有了，就可以继续后面的操作
            if(empty){
                choosePic();
            }else{
                ArrayList<JsonPic> selectedPhotos = new ArrayList<>();
                if(photoPaths!=null){
                    for (int i = 0; i <photoPaths.size() ; i++) {
                        JsonPic pic = new JsonPic();
                        pic.setMediaPath(photoPaths.get(i));
                        selectedPhotos.add(pic);
                    }
                    if (!this.jsonPics.isEmpty()) {
                        this.jsonPics.remove(emptyPic);
                    }
                    if (emptyView != null && picView.contains(emptyView)) {
                        picView.remove(emptyView);
                    }
                    this.jsonPics.addAll(selectedPhotos);
                    maxSelected = 6 - this.jsonPics.size();
                    if (!this.jsonPics.isEmpty() && this.jsonPics.size() < 6) {
                        this.jsonPics.add(emptyPic);
                        selectedPhotos.add(emptyPic);
                    }

                }else if(!Util.isEmpty(videoUrl)){
                    if (emptyView != null && picView.contains(emptyView)) {
                        picView.remove(emptyView);
                        jsonPics.remove(emptyPic);
                    }
                    JsonPic pic = new JsonPic();
                    pic.setMediaPath(videoUrl);
                    selectedPhotos.add(pic);
                    this.jsonPics.addAll(selectedPhotos);
                }
                changeTvState();
                addPic(selectedPhotos);
            }
        }
    }

    private void choosePic() {
        Matisse.from(FashionPublishActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG,MimeType.MP4,MimeType.AVI))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectablePerMediaType(6,1)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(456);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if (checkGranted(grantResults)) {
                choosePic();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean checkGranted(int[] result) {
        boolean is = true;
        for (int item : result) {
            if (item != PackageManager.PERMISSION_GRANTED) {
                is = false;
                break;
            }
        }
        return is;
    }

    @Subscribe
    public void onEvent(JsonPic pic) {
        if (!imgUpyunPaths.containsKey(pic.getMediaPath())) {
            imgUpyunPaths.put(pic.getMediaPath(), pic.getUploadPath());
            uploadedNum++;
        }
        changeTvState();
    }

    @OnClick({R.id.cancel_tv,R.id.publish_tv})
    public void click(View view){
        switch (view.getId()){
            case R.id.cancel_tv:
                if (contentEt.getText().toString().length() > 0 || jsonPics.size() > 1) {
                    hideKeyboard(null);
                    new AlertDialog.Builder(this)
                            .setMessage("确定退出吗?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                } else {
                    finish();
                }
                break;
            case R.id.publish_tv:
                if (Util.isEmpty(contentEt.getText().toString().trim())) {
                    Util.showToast(FashionPublishActivity.this, "请输入内容");
                    return;
                }
                if (contentEt.length() > 140) {
                    Util.showToast(FashionPublishActivity.this, "内容超过限制");
                    return;
                }
                if (uploadedNum==0){
                    Util.showToast(FashionPublishActivity.this,"请上传图片/视频");
                    return;
                }
                if (jsonPics.get(0).isVideo() && Util.isEmpty(jsonPics.get(0).getUploadPath())) {
                    Util.showToast(FashionPublishActivity.this, "视频上传中请稍后");
                    return;
                }
                im.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                postExplorePublish();
                break;
        }
    }

    //发布
    private void postExplorePublish(){
        publishTv.setEnabled(false);
        loadingDialog.show();
        ExplorePublishApi api=new ExplorePublishApi();
        api.setInterPath(Constants.FASHION_PUBLISH_URL);
        api.setDescription(contentEt.getText().toString());
        int size=jsonPics.size();
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<size;i++){
            if (Util.isEmpty(jsonPics.get(i).getMediaPath()))continue;
            if (i>0){
                builder.append(",");
            }
            builder.append(imgUpyunPaths.get(jsonPics.get(i).getMediaPath()));
        }
        api.setPic(builder.toString());
        if (jsonPics.get(0).isVideo()) {
            api.video=jsonPics.get(0).getUploadPath();
            api.setPic(jsonPics.get(0).getVideoPic());
        }
        api.setCity(cityName);
        if (!Util.isEmpty(transactionTime)){
            api.transactionTime=transactionTime;
        }
        if (!Util.isEmpty(temp)){
            api.temp=temp;
        }
        if (!Util.isEmpty(weather)){
            api.weather=weather;
        }
        api.camera=android.os.Build.MODEL;
        api.open=checkbox.isChecked()?1:0;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("pic",imgUpyunPaths.get(jsonPics.get(0).getMediaPath()));
                intent.putExtra("total",jsonPics.contains(emptyPic)?jsonPics.size()-1:jsonPics.size());
                GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.PUBLISH_WEAR_SUCCESS);
                globalTypeBean.putValue("position",position);
                if(jsonPics.get(0).isVideo()){
                    globalTypeBean.putValue("pic",jsonPics.get(0).getVideoPic());
                    globalTypeBean.putValue("videoUrl",imgUpyunPaths.get(jsonPics.get(0).getMediaPath()));
                }else{
                    globalTypeBean.putValue("videoUrl",null);
                    globalTypeBean.putValue("pic",imgUpyunPaths.get(jsonPics.get(0).getMediaPath()));
                }
                globalTypeBean.putValue("total",jsonPics.contains(emptyPic)?jsonPics.size()-1:jsonPics.size());
                EventBus.getDefault().post(globalTypeBean);
                setResult(Activity.RESULT_OK,intent);
                if (checkbox.isChecked()) {
                    sharePublish(api);
                } else {
                    loadingDialog.dismiss();
                    publishTv.setEnabled(true);
                    Util.showToast(FashionPublishActivity.this,"发布成功");
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(FashionPublishActivity.this,Util.checkErrorType(error));
                publishTv.setEnabled(true);
            }
        });
    }

    private void sharePublish(ExplorePublishApi api){
        api.temp=null;
        api.weather=null;
        api.camera=null;
        api.open=null;
        api.setInterPath(Constants.EXPLORE_PUBLISH_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                loadingDialog.dismiss();
                Util.showToast(FashionPublishActivity.this,"发布成功");
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(FashionPublishActivity.this,Util.checkErrorType(error));
                publishTv.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK && data!=null){
            if(requestCode==456){
                ArrayList<JsonPic> selectedPhotos = new ArrayList<JsonPic>();
                for (String path : Matisse.obtainPathResult(data)) {
                    JsonPic pic = new JsonPic();
                    pic.setMediaPath(path);
                    selectedPhotos.add(pic);
                }
                if (selectedPhotos.get(0).isVideo()) {
                    try {
                        Intent intent = new Intent(this, VideoEditActivity.class);
                        intent.putExtra("isReview", 1);
                        intent.putExtra("path", selectedPhotos.get(0).getMediaPath());
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(selectedPhotos.get(0).getMediaPath());
                        mediaPlayer.prepare();
                        long duration = mediaPlayer.getDuration();
                        intent.putExtra("duration", duration);
                        startActivityForResult(intent, Constants.RequestCode.VIDEO_FROM_GALLERY);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (!jsonPics.isEmpty()) {
                    jsonPics.remove(emptyPic);
                }
                if (emptyView != null && picView.contains(emptyView)) {
                    picView.remove(emptyView);
                }
                jsonPics.addAll(selectedPhotos);
                maxSelected = 6 - jsonPics.size();
                if (!jsonPics.isEmpty() && jsonPics.size() < 6) {
                    jsonPics.add(emptyPic);
                    selectedPhotos.add(emptyPic);
                }
                changeTvState();
                addPic(selectedPhotos);
            }else if(requestCode==Constants.RequestCode.VIDEO_FROM_GALLERY){
                url = data.getStringExtra("url");
                ArrayList<JsonPic> selectedPhotos2 = new ArrayList<JsonPic>();
                if (!Util.isEmpty(url)) {
                    JsonPic pic = new JsonPic();
                    pic.setMediaPath(url);
                    selectedPhotos2.add(pic);
                    if (!jsonPics.isEmpty()) {
                        jsonPics.remove(emptyPic);
                    }
                    if (emptyView != null && picView.contains(emptyView)) {
                        picView.remove(emptyView);
                    }
                    jsonPics.addAll(selectedPhotos2);
                    maxSelected = 9 - jsonPics.size();
                    changeTvState();
                    addPic(selectedPhotos2);
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addPic(ArrayList<JsonPic> pics) {
        if (pics == null || pics.size() == 0) return;
        int picSize = pics.size();
        View view;
        for (int i = 0; i < picSize; i++) {
            view = LayoutInflater.from(this).inflate(R.layout.list_item_post_image2, new RelativeLayout(this), false);
            ProcessImageView image = (ProcessImageView) view.findViewById(R.id.image);
            ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
            ImageView addBtn = (ImageView) view.findViewById(R.id.add_btn);
            ImageView ivPlay = (ImageView) view.findViewById(R.id.iv_play);
            view.setTag(pics.get(i).getMediaPath());
            if (Util.isEmpty(pics.get(i).getMediaPath())) {
                emptyView = view;
                emptyView.setOnClickListener(new EmptyPicClickListener());
                image.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                addBtn.setVisibility(View.VISIBLE);
            } else {
                addBtn.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                if (pics.get(i).isVideo()) {
                    ivPlay.setVisibility(View.VISIBLE);
                } else {
                    ivPlay.setVisibility(View.GONE);
                }
                ivPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去播放页面
                        Intent intent = new Intent(FashionPublishActivity.this, SimplePlayActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("isSource", true);
                        startActivityForResult(intent, EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE);
                    }
                });
                delete.setOnClickListener(new OnPhotoDeleteClickListener(pics.get(i)));
                Glide.with(this)
                        .load(Uri.fromFile(new File(Util.getD2cPicUrl(pics.get(i).getMediaPath()))))
                        .error(R.mipmap.ic_default_pic)
                        .into(image);
                image.userId = user.getId();
                image.setJsonPic(pics.get(i));
                if (!imgUpyunPaths.containsKey(pics.get(i).getMediaPath())){
                    image.startUpload();
                }else {
                    image.setProgress(100);
                    uploadedNum++;
                    changeTvState();
                    if (deletePaths.contains(pics.get(i).getMediaPath())){
                        deletePaths.remove(pics.get(i).getMediaPath());
                    }
                }
            }
            picView.add(view);
        }
        addView();
    }

    private void addView() {
        picLl.removeAllViews();
        int totalSize = picView.size();
        int lineCount = totalSize % 3 == 0 ? totalSize / 3 : totalSize / 3 + 1;
        LinearLayout childLinear = null;
        LinearLayout.LayoutParams childLL = null;
        for (int i = 0; i < lineCount; i++) {
            childLinear = new LinearLayout(this);
            childLL = new LinearLayout.LayoutParams(-1, -2);
            if (i != lineCount - 1) {
                childLL.setMargins(0, 0, 0, Util.dip2px(this, 4));
            } else {
                childLL.setMargins(0, 0, 0, 0);
            }
            LinearLayout.LayoutParams secondChildLL = null;
            int end = (i + 1) * 3 < totalSize ? (i + 1) * 3 : totalSize;
            for (int j = i * 3; j < end; j++) {
                secondChildLL = new LinearLayout.LayoutParams(imageSize, imageSize);
                if (j != (i + 1) * 3 - 1) {
                    secondChildLL.setMargins(0, 0, Util.dip2px(this, 4), 0);
                } else {
                    secondChildLL.setMargins(0, 0, 0, 0);
                }
                if (picView.get(j).getParent() != null) {
                    ((ViewGroup) picView.get(j).getParent()).removeView(picView.get(j));
                }
                childLinear.addView(picView.get(j), secondChildLL);
            }
            picLl.addView(childLinear, childLL);
        }
    }

    private class EmptyPicClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            handPermission(true);
        }
    }

    private class OnPhotoDeleteClickListener implements View.OnClickListener {
        private JsonPic jsonPic;

        private OnPhotoDeleteClickListener(JsonPic jsonPic) {
            this.jsonPic = jsonPic;
        }

        @Override
        public void onClick(View v) {
            if (jsonPics.contains(jsonPic)) {
                jsonPics.remove(jsonPic);
                if (imgUpyunPaths.containsKey(jsonPic.getMediaPath())) {
                    if (!deletePaths.contains(jsonPic.getMediaPath())){
                        deletePaths.add(jsonPic.getMediaPath());
                    }
                    uploadedNum--;
                }
            }
            maxSelected = 7 - jsonPics.size();
            if (!jsonPics.contains(emptyPic)) {
                maxSelected--;
                jsonPics.add(emptyPic);
                addEmptyPic();
            }
            changeTvState();
            removePic(jsonPic);
        }
    }

    private void removePic(JsonPic pic) {
        int picSize = picView.size();
        for (int i = 0; i < picSize; i++) {
            if (pic.getMediaPath().equals(picView.get(i).getTag())) {
                picView.remove(i);
                break;
            }
        }
        addView();
    }

    private void addEmptyPic() {
        emptyView = LayoutInflater.from(this).inflate(R.layout.list_item_post_image2, new RelativeLayout(this), false);
        ProcessImageView image = (ProcessImageView) emptyView.findViewById(R.id.image);
        ImageButton delete = (ImageButton) emptyView.findViewById(R.id.delete);
        ImageView addBtn = (ImageView) emptyView.findViewById(R.id.add_btn);
        emptyView.setTag("");
        emptyView.setOnClickListener(new EmptyPicClickListener());
        image.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        addBtn.setVisibility(View.VISIBLE);
        picView.add(emptyView);
    }

    private void changeTvState() {
        if (uploadedNum < (jsonPics.contains(emptyPic) ? jsonPics.size() - 1 : jsonPics.size())) {
            //发布按钮不可点
            publishTv.setTextColor(getResources().getColor(R.color.enable_color));
            publishTv.setEnabled(false);
        } else {
            publishTv.setTextColor(Color.WHITE);
            publishTv.setEnabled(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (contentEt.getText().toString().length() > 0 || jsonPics.size() > 1) {
                hideKeyboard(null);
                new AlertDialog.Builder(this)
                        .setMessage("确定退出吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
