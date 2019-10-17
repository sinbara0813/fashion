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
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
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
import com.d2cmall.buyer.api.ProductCommendApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CommentNumBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.IOnStatusChangedListener;
import com.d2cmall.buyer.widget.MyCheckBox;
import com.d2cmall.buyer.widget.ProcessImageView;
import com.d2cmall.buyer.widget.RatingBar;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.Constants.RequestCode.EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE;

/**
 * Created by rookie on 2017/9/2.
 * 评价商品页面
 */

public class ReviewOrderActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.item_iv)
    ImageView itemIv;
    @Bind(R.id.commend_content)
    EditText commendContent;
    @Bind(R.id.pic_layout)
    LinearLayout picLayout;
    @Bind(R.id.my_check_box)
    MyCheckBox myCheckBox;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.product_satisfaction)
    RatingBar productSatisfaction;
    @Bind(R.id.pack_satisfaction)
    RatingBar packSatisfaction;
    @Bind(R.id.ship_satisfaction)
    RatingBar shipSatisfaction;
    @Bind(R.id.delivery_satisfaction)
    RatingBar deliverySatisfaction;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.text_faction)
    TextView textFaction;
    @Bind(R.id.iv_good)
    ImageView ivGood;
    @Bind(R.id.tv_good)
    TextView tvGood;
    @Bind(R.id.ll_good)
    LinearLayout llGood;
    @Bind(R.id.iv_bad)
    ImageView ivBad;
    @Bind(R.id.tv_bad)
    TextView tvBad;
    @Bind(R.id.ll_bad)
    LinearLayout llBad;
    @Bind(R.id.ll_choice_appraise)
    LinearLayout llChoiceAppraise;
    private ArrayList<JsonPic> jsonPics;
    private JsonPic emptyPic;
    private HashMap<String, String> imgUpyunPaths;
    private List<String> deletePaths=new ArrayList<>();
    private UserBean.DataEntity.MemberEntity user;
    private int imageSize;
    private Dialog loadingDialog;
    private int productSatisfactionValue = 5;
    private int packSatisfactionValue = 5;
    private int shipSatisfactionValue = 5;
    private int deliverySatisfactionValue = 5;
    private long id;
    private String productUrl;
    private String productInfo;
    private int uploadedNum;
    private boolean checked;
    private ArrayList<View> picView;
    private View emptyView;
    private InputMethodManager im;
    private double price;
    private String style;
    private boolean publish = true;
    public boolean judge;
    private int intentFlag;//0是列表，1是详情进入
    private int maxSelected = 9;
    private String url;
    private long duration;
    private int appraise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_product);
        ButterKnife.bind(this);
        init();
    }

    private void choiceAppraise(int type) {
        //0为好评,1为差评
        if (type == 0) {
            tvBad.setTextColor(getResources().getColor(R.color.trans_50_color_black));
            tvGood.setTextColor(Color.parseColor("#FFFDC33E"));
            ivGood.setImageResource(R.mipmap.icon_haoping_pre);
            ivBad.setImageResource(R.mipmap.icon_chaping_nor);
            appraise = 1;
        } else {
            tvBad.setTextColor(Color.parseColor("#FFFDC33E"));
            tvGood.setTextColor(getResources().getColor(R.color.trans_50_color_black));
            ivGood.setImageResource(R.mipmap.icon_haoping_nor);
            ivBad.setImageResource(R.mipmap.icon_chaping_pre);
            appraise = -1;
        }
    }

    private void init() {
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        nameTv.setText("评价晒单");
        titleRight.setText("发布");
        id = getIntent().getLongExtra("id", 0);
        productUrl = getIntent().getStringExtra("productUrl");
        intentFlag = getIntent().getIntExtra("intentFlag", 0);
        price = getIntent().getDoubleExtra("price", 0);
        style = getIntent().getStringExtra("style");
        if (!Util.isEmpty(productUrl)) {
            UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(productUrl), itemIv);
        }
        productInfo = getIntent().getStringExtra("productInfo");
        choiceAppraise(0);
        imageSize = (ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(47))/4;
        user = Session.getInstance().getUserFromFile(this);
        jsonPics = new ArrayList<>();
        imgUpyunPaths = new HashMap<>();
        picView = new ArrayList<>();
        emptyPic = new JsonPic();
        jsonPics.add(emptyPic);
        addPic(jsonPics);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isEmpty(commendContent.getText().toString().trim())) {
                    Util.showToast(ReviewOrderActivity.this, R.string.msg_comment_error);
                    return;
                }
                if (commendContent.length() > 100) {
                    Util.showToast(ReviewOrderActivity.this, R.string.msg_comment_error2);
                    return;
                }
                if (jsonPics.get(0).isVideo() && Util.isEmpty(jsonPics.get(0).getUploadPath())) {
                    Util.showToast(ReviewOrderActivity.this, "视频上传中请稍后");
                    return;
                }
                im.hideSoftInputFromWindow(commendContent.getWindowToken(), 0);
                postExplorePublish();
            }
        });
        initListener();
    }

    private void postExplorePublish() {
        ProductCommendApi api = new ProductCommendApi();
        api.setInterPath(String.format(Constants.PRODUCT_COMMEND_INSERT_URL, id));
        api.score = appraise;
        api.setDeliveryServiceScore(deliverySatisfactionValue);
        api.setDeliverySpeedScore(shipSatisfactionValue);
        api.setPackageScore(packSatisfactionValue);
        api.setProductScore(productSatisfactionValue);
        api.setContent(commendContent.getText().toString());
        final StringBuilder pic = new StringBuilder();
        for (int i = 0; i < jsonPics.size(); i++) {
            if (Util.isEmpty(jsonPics.get(i).getMediaPath())) {
                continue;
            }
            if (jsonPics.get(i).isVideo()) {
                api.video = jsonPics.get(i).getUploadPath();
                if (!Util.isEmpty(jsonPics.get(i).getUploadPath())) {
                    String uploadPath = jsonPics.get(i).getUploadPath();
                    int index = uploadPath.lastIndexOf(".");
                    if (index > 0) {
                        uploadPath = uploadPath.substring(0, index);
                    }
                    api.setPic(uploadPath + "_fpng_n1_oneTrue.png");
                }
                continue;
            } else {
                pic.append(imgUpyunPaths.get(jsonPics.get(i).getMediaPath()));
            }
            if (i != jsonPics.size() - 1) {
                pic.append(",");
            }
        }
        if (!Util.isEmpty(pic.toString())) {
            api.setPic(pic.toString());
        }
        titleRight.setEnabled(false);
        titleRight.setTextColor(getResources().getColor(R.color.enable_color));
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CommentNumBean>() {
            @Override
            public void onResponse(CommentNumBean response) {
                judge = true;
                if (checked && !Util.isEmpty(pic.toString())) {
                    publish = false;
                    final ExplorePublishApi publishApi = new ExplorePublishApi();
                    publishApi.setInterPath(Constants.EXPLORE_PUBLISH_URL);
                    publishApi.setDescription(commendContent.getText().toString());
                    if (jsonPics.get(0).isVideo()) {
                        publishApi.taskIds = jsonPics.get(0).getTaskIds();
                        publishApi.commentId = response.getData().getCommendId();
                        publishApi.video = jsonPics.get(0).getUploadPath();
                        publishApi.setPic(jsonPics.get(0).getUploadPath() + "_fpng_n1_oneTrue.png");
                    } else {
                        publishApi.setPic(pic.toString());
                    }
                    D2CApplication.httpClient.loadingRequest(publishApi, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean bean) {
                            publish = true;
                            successState();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            publish = true;
                            failState();
                            Util.showToast(ReviewOrderActivity.this, Util.checkErrorType(error));
                        }
                    });
                    return;
                }
                successState();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                judge = true;
                failState();
                Util.showToast(ReviewOrderActivity.this, Util.checkErrorType(error));
            }
        });

    }

    private void failState() {
        if (judge && publish) {
            titleRight.setEnabled(true);
            titleRight.setTextColor(Color.parseColor("#DE000000"));
            loadingDialog.dismiss();
        }
    }

    private void successState() {
        if (judge && publish) {
            titleRight.setEnabled(true);
            titleRight.setTextColor(Color.parseColor("#DE000000"));
            loadingDialog.dismiss();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.ORDER_TALK));
            finish();
        }
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
                        Intent intent = new Intent(ReviewOrderActivity.this, SimplePlayActivity.class);
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

    private boolean isContain(JsonPic pic){
        if (jsonPics==null||jsonPics.size()==0){
            return false;
        }else {
            for (int i=0,size=jsonPics.size();i<size;i++){
                if (jsonPics.get(i).getMediaPath().equals(pic.getMediaPath())){
                    return true;
                }
            }
        }
        return false;
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

    private void addView() {
        picLayout.removeAllViews();
        int totalSize = picView.size();
        int lineCount = totalSize % 4 == 0 ? totalSize / 4 : totalSize / 4 + 1;
        LinearLayout childLinear = null;
        LinearLayout.LayoutParams childLL = null;
        for (int i = 0; i < lineCount; i++) {
            childLinear = new LinearLayout(this);
            childLL = new LinearLayout.LayoutParams(-1, -2);
            if (i != lineCount - 1) {
                childLL.setMargins(0, 0, 0, Util.dip2px(this, 5));
            } else {
                childLL.setMargins(0, 0, 0, 0);
            }
            LinearLayout.LayoutParams secondChildLL = null;
            int end = (i + 1) * 4 < totalSize ? (i + 1) * 4 : totalSize;
            for (int j = i * 4; j < end; j++) {
                secondChildLL = new LinearLayout.LayoutParams(imageSize, imageSize);
                if (j != (i + 1) * 4 - 1) {
                    secondChildLL.setMargins(0, 0, Util.dip2px(this, 5), 0);
                } else {
                    secondChildLL.setMargins(0, 0, 0, 0);
                }
                if (picView.get(j).getParent() != null) {
                    ((ViewGroup) picView.get(j).getParent()).removeView(picView.get(j));
                }
                childLinear.addView(picView.get(j), secondChildLL);
            }
            picLayout.addView(childLinear, childLL);
        }
    }

    private void initListener() {
        productSatisfaction.setStar(5);
        productSatisfaction.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                productSatisfactionValue = RatingCount;
            }
        });
        packSatisfaction.setStar(5);
        packSatisfaction.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                packSatisfactionValue = RatingCount;
            }
        });
        shipSatisfaction.setStar(5);
        shipSatisfaction.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                shipSatisfactionValue = RatingCount;
            }
        });
        deliverySatisfaction.setStar(5);
        deliverySatisfaction.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                deliverySatisfactionValue = RatingCount;
            }
        });
        myCheckBox.setCheckColorId(R.mipmap.icon_orderrecommend_chosed, R.mipmap.icon_orderrecommend_unchoose);
        myCheckBox.setOnStatusChangedListener(new IOnStatusChangedListener() {
            @Override
            public void onStatusChanged(View v, boolean isChecked) {
                checked = isChecked;
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.ll_bad, R.id.ll_good})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                break;
            case R.id.ll_bad:
                choiceAppraise(1);
                break;
            case R.id.ll_good:
                choiceAppraise(0);
                break;
        }
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
            maxSelected = 10 - jsonPics.size();
            if (!jsonPics.contains(emptyPic)) {
                maxSelected--;
                jsonPics.add(emptyPic);
                addEmptyPic();
            }
            changeTvState();
            removePic(jsonPic);
        }
    }

    public void showPop() {
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !cameraPermission) {
            requestPermission();
        } else {
            choosePic();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.RequestCode.REQUEST_PERMISSION);
    }

    private void choosePic() {
        Matisse.from(ReviewOrderActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.AVI, MimeType.MP4, MimeType.MKV))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectablePerMediaType(maxSelected, 1)
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

    private class EmptyPicClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            showPop();
        }
    }

    public void onBackPressed(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (commendContent.getText().toString().length() > 0 || jsonPics.size() > 1) {
            hideKeyboard(null);
            new AlertDialog.Builder(this)
                    .setMessage(R.string.msg_exit_current_judge)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            overridePendingTransition(0, R.anim.slide_out_right);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            finish();
            overridePendingTransition(0, R.anim.slide_out_right);
        }
    }

    @Subscribe
    public void onEvent(JsonPic pic) {
        if (!imgUpyunPaths.containsKey(pic.getMediaPath())) {
            imgUpyunPaths.put(pic.getMediaPath(), pic.getUploadPath());
            uploadedNum++;
        }
        changeTvState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Constants.RequestCode.PHOTO_FROM_GALLERY:
                    ArrayList<JsonPic> selectedPhotos1 = (ArrayList<JsonPic>) data
                            .getSerializableExtra("selectedPhotos");
                    if (!jsonPics.isEmpty()) {
                        jsonPics.remove(emptyPic);
                    }
                    if (emptyView != null && picView.contains(emptyView)) {
                        picView.remove(emptyView);
                    }
                    //jsonPics.addAll(selectedPhotos);
                    int selectSize = selectedPhotos1.size();
                    ArrayList<JsonPic> valuePhotos = new ArrayList<>();
                    for (int i = 0; i < selectSize; i++) {
                        JsonPic pic = selectedPhotos1.get(i);
                        File file = new File(pic.getMediaPath());
                        if (file.exists()) {
                            jsonPics.add(pic);
                            valuePhotos.add(pic);
                        }
                    }
                    if (!jsonPics.isEmpty() && jsonPics.size() < 9) {
                        jsonPics.add(emptyPic);
                        valuePhotos.add(emptyPic);
                    }
                    changeTvState();
                    addPic(valuePhotos);
                    break;
                case Constants.RequestCode.VIDEO_FROM_GALLERY:
                    url = data.getStringExtra("url");
                    duration = data.getLongExtra("duration", 0);
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
                    break;

                case 456:
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
                    maxSelected = 9 - jsonPics.size();
                    if (!jsonPics.isEmpty() && jsonPics.size() < 9) {
                        jsonPics.add(emptyPic);
                        selectedPhotos.add(emptyPic);
                    }
                    changeTvState();
                    addPic(selectedPhotos);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeTvState() {
        if (uploadedNum < (jsonPics.contains(emptyPic) ? jsonPics.size() - 1 : jsonPics.size())) {
            //发布按钮不可点
            titleRight.setTextColor(Color.parseColor("#61000000"));
            titleRight.setEnabled(false);
        } else {
            titleRight.setTextColor(Color.parseColor("#DE000000"));
            titleRight.setEnabled(true);
        }
    }
}
