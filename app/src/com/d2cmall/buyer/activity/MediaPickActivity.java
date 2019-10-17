package com.d2cmall.buyer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.GalleryAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.Photo;
import com.d2cmall.buyer.bean.PhotoDirectory;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.util.MediaManager;
import com.d2cmall.buyer.util.MediaStoreHelper;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/7/7 17:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MediaPickActivity extends BaseActivity implements MediaManager.OnCheckchangeListener {
    RecyclerView imageRecyclerView;
    GalleryAdapter galleryAdapter;
    ImageButton backBtn;
    TextView sureTv;
    Photo selectPhoto;
    private TopicBean.DataBean.TopicsBean.ListBean topicBean;
    private boolean hasInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        checkPermission();
        topicBean= (TopicBean.DataBean.TopicsBean.ListBean) getIntent().getSerializableExtra("topic");
    }

    private void checkPermission(){
//        PackageManager pkgManager = getPackageManager();
//        boolean phoneSatePermission =
//                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
//        if (Build.VERSION.SDK_INT >= 23&&!phoneSatePermission){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//        }else {
//            initView();
//            readIntentParam();
//        }
        handPermission();
    }

    public void handPermission() {
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
            initView();
            readIntentParam();
        }
    }

    private void readIntentParam() {
        Intent intent=getIntent();
        int maxMedia = intent.getIntExtra("maxSum", 9);
        MediaManager.getInstance().setMaxMediaSum(maxMedia);
    }

    private void initView() {
        //初始化title
        backBtn= (ImageButton) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.slide_out_right);
            }
        });
        sureTv= (TextView) findViewById(R.id.title_sure);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhoto!=null){//跳去编辑视频
                    Intent intent=new Intent(MediaPickActivity.this,VideoEditActivity.class);
                    intent.putExtra("path",selectPhoto.getPath());
                    intent.putExtra("duration",selectPhoto.getDuration());
                    if(topicBean!=null){
                        intent.putExtra("topic",topicBean);
                    }
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }else {
                    Toast.makeText(MediaPickActivity.this,"请选择视频",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageRecyclerView.addItemDecoration(new SpaceItemDecoration(this, 1));
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        MediaManager.getInstance().init();
        MediaStoreHelper.getPhotoDirs(this, MediaStoreHelper.TYPE_VIDEO, new MediaStoreHelper.PhotosResultCallback() {
            @Override
            public void onResultCallback(List<PhotoDirectory> dirs) {
                if (dirs.size() > 0)
                    dirs.get(0).setSelected(true);
                MediaManager.getInstance().setPhotoDirectorys(dirs);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        directoryChanged();
                    }
                });
            }
        });
        hasInit=true;
    }

    private void directoryChanged() {
        if (MediaManager.getInstance().getPhotoDirectorys().isEmpty()) {
            ViewStub emptyStub = ((ViewStub) findViewById(R.id.stub_empty));
            emptyStub.inflate();
            return;
        }
        if (galleryAdapter == null) {
            galleryAdapter = new GalleryAdapter(MediaPickActivity.this, MediaManager.getInstance().getSelectDirectory());
            galleryAdapter.setImageRecyclerView(imageRecyclerView);
            imageRecyclerView.setAdapter(galleryAdapter);
            MediaManager.getInstance().addOnCheckchangeListener(MediaPickActivity.this);
        } else {
            galleryAdapter.setImages(MediaManager.getInstance().getSelectDirectory());
            imageRecyclerView.getLayoutManager().scrollToPosition(0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100&&permissions.length==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            initView();
            readIntentParam();
        }else{
            Util.showToast(MediaPickActivity.this,"权限被拒绝");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCheckedChanged(Map<Integer, Photo> checkStaus, int changedId, boolean uiUpdated) {
        final int checkSize = checkStaus.size();//选中数
        if (checkSize>=1){
            selectPhoto=checkStaus.get(changedId);
            if (selectPhoto!=null){
                sureTv.setTextColor(Color.WHITE);
            }
        }else {
            sureTv.setTextColor(getResources().getColor(R.color.trans_50_color_white));
        }
    }

    @Override
    protected void onDestroy() {
        if (hasInit){
            MediaManager.getInstance().removeOnCheckchangeListener(this);
            MediaManager.getInstance().clear();
        }
        super.onDestroy();
    }
}
