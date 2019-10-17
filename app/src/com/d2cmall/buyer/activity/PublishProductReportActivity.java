package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.adapter.RelationProductAdapter;
import com.d2cmall.buyer.api.PublishProductReportApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.RelationProductBean;
import com.d2cmall.buyer.bean.SelectListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.LookProductReportPop;
import com.d2cmall.buyer.widget.MyGridView;
import com.d2cmall.buyer.widget.TransparentPop;
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
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/10 11:35
 * 发布商品报告页面
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PublishProductReportActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, ObjectBindAdapter.ViewBinder<JsonPic> ,TransparentPop.DismissListener{
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.edit_description)
    EditText editDescription;
    @Bind(R.id.tv_topic)
    TextView tvTopic;
    @Bind(R.id.gridView)
    MyGridView gridView;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.empty_view)
    RelativeLayout emptyView;
    @Bind(R.id.images_layout)
    RelativeLayout imagesLayout;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private ArrayList<JsonPic> photos;
    private ArrayList<JsonPic> jsonPics;
    private int imageSize;
    private ArrayList<String> imgUpyunPaths;
    private ObjectBindAdapter<JsonPic> adapter;
    private int upLoadIndex;
    private UserBean.DataEntity.MemberEntity user;
    private Dialog loadingDialog;
    private JsonPic emptyPic;
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> selectList;
    private RelationProductAdapter relationProductAdapter;
    private long productId;
    private String productImg;
    private String productName;
    private LookProductReportPop mLookProductReportPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_product_report);
        ButterKnife.bind(this);
        productId = getIntent().getLongExtra("productId",0);
        productImg = getIntent().getStringExtra("productImg");
        productName = getIntent().getStringExtra("productName");
        doBusiness();
    }

    public void doBusiness() {
        titleRight.setText("发布");
        photos = new ArrayList<>();
        jsonPics = new ArrayList<>();
        imgUpyunPaths = new ArrayList<>();
        emptyPic = new JsonPic();
        selectList=new ArrayList<>();
        loadingDialog = DialogUtil.createLoadingDialog(this);
        photos = (ArrayList<JsonPic>) getIntent().getSerializableExtra("photos");
        initJsonPics();
        if(photos==null) {
            photos=new ArrayList<>();
        }
        if (photos!=null && photos.size() < 9) {
            photos.add(emptyPic);
        }
        relationProductAdapter=new RelationProductAdapter(this,selectList,1);
        adapter = new ObjectBindAdapter<>(this, photos, R.layout.list_item_post_image, this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Point point = ScreenUtil.getDeviceSize(this);
        imageSize = Math.round((point.x - 56 * dm.density) / 4);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        if (!photos.isEmpty()) {
            imagesLayout.setVisibility(View.VISIBLE);
        }
        user = Session.getInstance().getUserFromFile(this);
        setBtnSendStatus();
        editDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0 && !jsonPics.isEmpty() ) {
                    titleRight.setEnabled(true);
                    titleRight.setTextColor(getResources().getColor(R.color.trans_87_color_black));
                }else{
                    titleRight.setEnabled(false);
                    titleRight.setTextColor(getResources().getColor(R.color.enable_color));
                }
                if(1000-s.length()>=0 ) {
                    tvTopic.setText(1000-s.length()+"");
                }else{
                    tvTopic.setText(0+"");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onSend(View v) {
        String des = editDescription.getText().toString();
        if (des.length() > 1000) {
            Util.showToast(this, R.string.msg_product_publish_error);
            return;
        }
        upLoadIndex = 0;
        loadingDialog.show();
        uploadFile();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(null);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


    private void uploadFile() {
        if (jsonPics==null||jsonPics.isEmpty()){
            return;
        }
        JsonPic jsonPic = jsonPics.get(upLoadIndex);
        File file = new File(jsonPic.getMediaPath());
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(100, Constants.TYPE_FRIEND));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    imgUpyunPaths.add(jsonObject.optString("url"));

                } catch (JSONException e) {
                }
                upLoadIndex++;
                if (upLoadIndex < jsonPics.size()) {
                    uploadFile();
                } else {
                    publisthShow();
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

    private void setBtnSendStatus() {
        if (jsonPics.isEmpty() || editDescription.getText().toString().trim().length()==0) {
            titleRight.setEnabled(false);
            titleRight.setTextColor(getResources().getColor(R.color.enable_color));
        } else {
            titleRight.setTextColor(getResources().getColor(R.color.trans_87_color_black));
            titleRight.setEnabled(true);
        }
    }



    private void initJsonPics() {
        if(photos!=null) {
            for (JsonPic jsonPic : photos) {
                jsonPics.add(jsonPic);
            }
        }

    }


    private void publisthShow() {
        PublishProductReportApi api = new PublishProductReportApi();
        String oldContent = editDescription.getText().toString();
        api.setProductId(productId);
//        api.setProductImg(productImg);
//        api.setProductName(productName);

        if (selectList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selectList.size(); i++) {
                sb.append(selectList.get(i).getId() + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            api.setProductIds(sb.toString());
        }
        int firstIndex = oldContent.indexOf("#");
        int secondIndex = oldContent.indexOf("#", firstIndex + 1);
        String deleteContent = null;
        if (firstIndex != -1 && secondIndex != -1) {
            deleteContent = oldContent.replace(oldContent.substring(firstIndex, secondIndex + 1), "");
        } else {
            deleteContent = oldContent;
        }
        api.setDescription(deleteContent);
        api.setContent(deleteContent);
        api.setType(1);//普通类型传1，体验分享传3
        api.setPic(Util.join(imgUpyunPaths.toArray(), ","));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean bean) {
                loadingDialog.dismiss();
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                if( mLookProductReportPop==null) {
                    mLookProductReportPop = new LookProductReportPop(PublishProductReportActivity.this);
                    mLookProductReportPop.mCloseIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLookProductReportPop.dismiss();
                            finish();
                        }
                    });
                    mLookProductReportPop.mTvLook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLookProductReportPop.dismiss();
                            startActivity(new Intent(PublishProductReportActivity.this,ProductReportActivity
                            .class));
                            finish();
                        }
                    });
                }
                mLookProductReportPop.setDismissListener(PublishProductReportActivity.this);
                    mLookProductReportPop.show(getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                imgUpyunPaths.clear();
                Util.showToast(PublishProductReportActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void setViewValue(View view, JsonPic jsonPic, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.addView = view.findViewById(R.id.add_btn);
            holder.deleteView = view.findViewById(R.id.delete);
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            view.getLayoutParams().width = imageSize;
            view.getLayoutParams().height = imageSize;
            view.setTag(holder);
        }
        if (Util.isEmpty(jsonPic.getMediaPath())) {
            holder.imageView.setVisibility(View.GONE);
            holder.deleteView.setVisibility(View.GONE);
            holder.addView.setVisibility(View.VISIBLE);
        } else {
            holder.addView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.deleteView.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(Uri.fromFile(new File(jsonPic.getMediaPath())))
                    .error(R.mipmap.ic_default_pic)
                    .into(holder.imageView);
            holder.deleteView.setOnClickListener(new OnPhotoDeleteClickListener(jsonPic));
        }
    }

    @Override
    public void dismissStart() {

    }

    @Override
    public void dismissEnd() {
        finish();
    }

    private class OnPhotoDeleteClickListener implements View.OnClickListener {
        private JsonPic jsonPic;

        private OnPhotoDeleteClickListener(JsonPic jsonPic) {
            this.jsonPic = jsonPic;
        }

        @Override
        public void onClick(View v) {
            photos.remove(jsonPic);
            jsonPics.remove(jsonPic);
            maxSelected = 9 - jsonPics.size();
            if (!photos.contains(emptyPic)) {
                photos.add(emptyPic);
            }
            setBtnSendStatus();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        if (editDescription.getText().toString().length() > 0 || !jsonPics.isEmpty()) {
            hideKeyboard(null);
            PublishProductReportActivity.super.onBackPressed();
        } else {
            PublishProductReportActivity.super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 999:
                if (data != null) {
                    SelectListBean bean = (SelectListBean) data.getSerializableExtra("selectList");
                    selectList.addAll(bean.getList());
                    relationProductAdapter.notifyDataSetChanged();
                }
                break;
            case 456:
                if (resultCode == RESULT_OK) {
                    for (String string : Matisse.obtainPathResult(data)) {
                        JsonPic jsonPic = new JsonPic();
                        jsonPic.setMediaPath(string);
                        photos.add(jsonPic);
                        jsonPics.add(jsonPic);
                    }
                    if (!photos.contains(emptyPic) && jsonPics.size() < 9) {
                        photos.add(emptyPic);
                    }
                    if (jsonPics.size() >= 9 && photos.contains(emptyPic)) {
                        photos.remove(emptyPic);
                    }
                    maxSelected = 9 - jsonPics.size();
                    setBtnSendStatus();
                    adapter.notifyDataSetChanged();
                } else {
                    if (!photos.contains(emptyPic) && jsonPics.size() < 9) {
                        photos.add(emptyPic);
                    }
                }
                break;

        }
    }

    @OnClick({R.id.back_iv, R.id.title_right,  R.id.add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                onSend(view);
                break;
            case R.id.add_btn:
                break;
        }
    }


    private class ViewHolder {
        View addView;
        View deleteView;
        ImageView imageView;
    }


    public void showPop() {
        if (photos.contains(emptyPic)) {
            photos.remove(emptyPic);
        }
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
            choosePic();
        }
    }

    private int maxSelected=9;

    private void choosePic() {
        Matisse.from(PublishProductReportActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectable(maxSelected)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(456);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                choosePic();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JsonPic jsonPic = (JsonPic) parent.getAdapter().getItem(position);
        if (jsonPic != null) {
            if (Util.isEmpty(jsonPic.getMediaPath())) {
                showPop();
            }

        }
    }
}
