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
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
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
import com.d2cmall.buyer.api.ExplorePublishApi;
import com.d2cmall.buyer.api.SelectProductApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.AddressEntity;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.RelationProductBean;
import com.d2cmall.buyer.bean.SelectListBean;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnAddressDeleteClickListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.MyGridView;
import com.d2cmall.buyer.widget.OpenMsgPushPop;
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
import de.greenrobot.event.EventBus;

import static com.d2cmall.buyer.Constants.RELATION_PRODUCT_URL;
import static com.d2cmall.buyer.Constants.RequestCode.REQUEST_TOPIC;
import static com.d2cmall.buyer.Constants.RequestCode.SEARCHADDRESS_ACTIVITY_REQUESTCODE;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/10 11:35
 * 发布买家秀页面
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Explore1PublishActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, ObjectBindAdapter.ViewBinder<JsonPic> {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
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
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.mydress)
    LinearLayout mydress;
    @Bind(R.id.tv_relevance)
    TextView tvRelevance;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.relevance_product)
    LinearLayout relevanceProduct;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
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
    private TopicBean.DataBean.TopicsBean.ListBean topicBean;
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> selectList;
    private LinearLayoutManager virtualLayoutManager;
    private RelationProductAdapter relationProductAdapter;
    private int maxSelected = 9;
    private String sn;
    private ArrayList<String> photoPaths;//拍照传过来的图片地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_show);
        ButterKnife.bind(this);
        photoPaths = getIntent().getStringArrayListExtra("photoPaths");
        doBusiness();
    }

    public void showPop(boolean empty) {
        if (photos.contains(emptyPic) && empty) {
            photos.remove(emptyPic);
        }
        handPermission(empty);
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
            if(empty){//拍照传过来的图片地址不为空跳转选择照片
                choosePic();
            }else{
                if (photoPaths != null) {
                    for (int i = 0; i < photoPaths.size(); i++) {
                        JsonPic jsonPic = new JsonPic();
                        jsonPic.setMediaPath(photoPaths.get(i));
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
                }
            }
        }
    }

    private void choosePic() {
        Matisse.from(Explore1PublishActivity.this)
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

    public void doBusiness() {
        titleRight.setText("发布");
        tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), 0));
        photos = new ArrayList<>();
        jsonPics = new ArrayList<>();
        imgUpyunPaths = new ArrayList<>();
        emptyPic = new JsonPic();
        selectList = new ArrayList<>();
        recycleView.setVisibility(View.VISIBLE);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        //photos = (ArrayList<JsonPic>) getIntent().getSerializableExtra("photos");
        topicBean = (TopicBean.DataBean.TopicsBean.ListBean) getIntent().getSerializableExtra("topic");
        if (topicBean != null) {
            editDescription.setText("#" + topicBean.getTitle() + "#");
            editDescription.setSelection(editDescription.length());
        }
//        initJsonPics();
//        if (!photos.isEmpty() && photos.size() < 9) {
//            photos.add(emptyPic);
//        }
        virtualLayoutManager = new LinearLayoutManager(this);
        relationProductAdapter = new RelationProductAdapter(this, selectList, 1);
        relationProductAdapter.setOnDeleteListener(new OnAddressDeleteClickListener() {
            @Override
            public void clickDelete(View v, int position) {
                tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), selectList.size()));
            }
        });
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(relationProductAdapter);
        sn=getIntent().getStringExtra("sn");
        if (!Util.isEmpty(sn)){
            getSelectProduct();
        }
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
        editDescription.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!Util.isEmpty(editDescription.getText().toString())) {
                        int firstIndex = String.valueOf(editDescription.getText()).indexOf("#");
                        int secondIndex = String.valueOf(editDescription.getText()).indexOf("#", firstIndex + 1);
                        int deletePosition = editDescription.getSelectionStart();
                        String oldContent = editDescription.getText().toString();
                        String newContent = "";
                        if ((secondIndex + 1) == deletePosition) {
                            newContent = oldContent.replace(oldContent.substring(firstIndex, secondIndex + 1), "");
                            editDescription.setText(newContent);
                        }
                        topicBean = null;
                    }
                }
                return false;
            }
        });

        showPop(photoPaths==null);
    }

    private void getSelectProduct(){
        SelectProductApi api = new SelectProductApi();
        api.setPage(1);
        api.setSn(sn);
        api.setInterPath(RELATION_PRODUCT_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RelationProductBean>() {

            @Override
            public void onResponse(RelationProductBean response) {
                if (response.getData().getProducts()!=null&&response.getData().getProducts().getList().size()>0){
                    selectList.clear();
                    selectList.addAll(response.getData().getProducts().getList());
                    tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), selectList.size()));
                    relationProductAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onSend(View v) {
        String des = editDescription.getText().toString();
        if (des.length() > 140) {
            Util.showToast(this, R.string.msg_explore_publish_error);
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
        JsonPic jsonPic = jsonPics.get(upLoadIndex);
        File file = new File(jsonPic.getMediaPath());
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
        if (jsonPics.isEmpty()) {
            titleRight.setEnabled(false);
        } else {
            titleRight.setEnabled(true);
        }
    }

    private void initJsonPics() {
        for (JsonPic jsonPic : photos) {
            jsonPics.add(jsonPic);
        }
    }


    private void publisthShow() {
        ExplorePublishApi api = new ExplorePublishApi();
        api.setInterPath(Constants.EXPLORE_PUBLISH_URL);
        String oldContent = editDescription.getText().toString();
        String topic = "";
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
        if (topicBean != null) {
            api.setTopicId((long) topicBean.getId());
        }
        api.setDescription(deleteContent);
        api.setType(1);//普通类型传1，体验分享传3
        if (address != null && address.cityName != null) {
            api.setX("" + address.lat);
            api.setY("" + address.lon);
            api.setCity(address.cityName);
            api.setStreet(address.cityName + address.addressName);
        }
        api.setPic(Util.join(imgUpyunPaths.toArray(), ","));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean bean) {
                loadingDialog.dismiss();
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.PUBLISHBACK));
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                //开启消息推送行为节点
                Boolean isMsgPushOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG, false);
                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                if(!isMsgPushOpen && !isOpened){
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG,true);
                    OpenMsgPushPop openMsgPushPop = new OpenMsgPushPop(Explore1PublishActivity.this);
                    openMsgPushPop.setDismissListener(new TransparentPop.DismissListener() {
                        @Override
                        public void dismissStart() {

                        }

                        @Override
                        public void dismissEnd() {
                            Explore1PublishActivity.super.onBackPressed();
                        }
                    });
                    openMsgPushPop.setContent(getString(R.string.label_pop_focus_show));
                    openMsgPushPop.show(getWindow().getDecorView());
                }else{
                    Explore1PublishActivity.super.onBackPressed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                imgUpyunPaths.clear();
                Util.showToast(Explore1PublishActivity.this, Util.checkErrorType(error));
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
            Explore1PublishActivity.super.onBackPressed();
        } else {
            Explore1PublishActivity.super.onBackPressed();
        }
    }

    private AddressEntity address;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SEARCHADDRESS_ACTIVITY_REQUESTCODE:
                if (resultCode == SEARCHADDRESS_ACTIVITY_REQUESTCODE && data != null) {
                    address = (AddressEntity) data.getSerializableExtra(SearchAdressActivity.ADDRESS);
                    if (address != null) {
                        tvAddress.setText(address.cityName + address.addressName);
                    } else {

                    }
                }
                break;
            case REQUEST_TOPIC:
                if (data != null) {
                    topicBean = (TopicBean.DataBean.TopicsBean.ListBean) data.getSerializableExtra("topic");
                    String topicName = "#" + topicBean.getTitle() + "#";
                    String oldContent = editDescription.getText().toString();
                    StringBuilder sb = new StringBuilder();
                    String deleteContent = null;
                    if (oldContent.contains("#") && oldContent.indexOf("#", oldContent.indexOf("#") + 1) != -1) {
                        int firstIndex = oldContent.indexOf("#");
                        int secondIndex = oldContent.indexOf("#", firstIndex + 1);
                        if (firstIndex != -1 && secondIndex != -1) {
                            deleteContent = oldContent.replace(oldContent.substring(firstIndex, secondIndex + 1), "");
                        }
                    }
                    sb.append(topicName);
                    if (deleteContent != null) {
                        sb.append(deleteContent);
                    } else {
                        sb.append(oldContent);
                    }
                    editDescription.setText(sb.toString());
                    editDescription.setSelection(sb.length());
                }
                break;
            case 999:
                if (data != null) {
                    SelectListBean bean = (SelectListBean) data.getSerializableExtra("selectList");
                    selectList.clear();
                    selectList.addAll(bean.getList());
                    tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), selectList.size()));
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

    @OnClick({R.id.back_iv, R.id.title_right, R.id.tv_topic, R.id.add_btn, R.id.mydress, R.id.relevance_product})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                onSend(view);
                break;
            case R.id.tv_topic:
                intent = new Intent(this, TopicListActivity.class);
                startActivityForResult(intent, REQUEST_TOPIC);
                break;
            case R.id.add_btn:
                break;
            case R.id.mydress:
                intent = new Intent(this, SearchAdressActivity.class);
                intent.putExtra(SearchAdressActivity.ADDRESS, tvAddress.getText().toString());
                startActivityForResult(intent, SEARCHADDRESS_ACTIVITY_REQUESTCODE);
                break;
            case R.id.relevance_product:
                intent = new Intent(Explore1PublishActivity.this, RelationSelectProductActivity.class);
                intent.putExtra("size", selectList.size());
                SelectListBean bean = new SelectListBean();
                bean.setList(selectList);
                intent.putExtra("selectedList", bean);
                startActivityForResult(intent, 999);
                break;
        }
    }


    private class ViewHolder {
        View addView;
        View deleteView;
        ImageView imageView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JsonPic jsonPic = (JsonPic) parent.getAdapter().getItem(position);
        if (jsonPic != null) {
            if (Util.isEmpty(jsonPic.getMediaPath())) {
                showPop(true);
            }
        }
    }
}
