package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.d2cmall.buyer.api.AddCommendApi;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CommentInsertApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CommendDetailBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ProcessImageView;
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
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.Constants.RequestCode.EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE;

//追评
public class CommentInsertActivity extends BaseActivity {

    @Bind(R.id.item_iv)
    ImageView itemIv;
    @Bind(R.id.item_info)
    TextView itemInfo;
    @Bind(R.id.tv_style)
    TextView tvStyle;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.pic_layout)
    LinearLayout picLayout;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.content)
    EditText contentEt;

    private long id;
    private String price;
    private long productId;
    private ArrayList<JsonPic> jsonPics;
    private JsonPic emptyPic;
    private HashMap<String, String> imgUpyunPaths;
    private List<String> deletePaths=new ArrayList<>();
    private ArrayList<View> picView;
    private int maxSelected = 9;
    private View emptyView;
    private int uploadedNum;
    private int imageSize;
    private String url;
    private long duration;
    private InputMethodManager im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_insert);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_publish_review);
        titleRight.setText("发布");
        id = getIntent().getLongExtra("id", 0);
        price = getIntent().getStringExtra("price");
        productId = getIntent().getLongExtra("productId", 0);
        imageSize =(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(47))/4;
        jsonPics = new ArrayList<>();
        imgUpyunPaths = new HashMap<>();
        picView = new ArrayList<>();
        emptyPic = new JsonPic();
        jsonPics.add(emptyPic);
        addPic(jsonPics);
        if (id != 0) {
            loadData();
        }
        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content =contentEt.getText().toString().trim();
                if (Util.isEmpty(content)) {
                    Util.showToast(CommentInsertActivity.this, R.string.msg_comment_error);
                    return;
                }
                if (content.length() > 100) {
                    Util.showToast(CommentInsertActivity.this, R.string.msg_comment_error2);
                    return;
                }
                im.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                AddCommendApi api = new AddCommendApi();
                api.setInterPath(String.format(Constants.COMMEND_ADD_URL, id));
                api.setContent(content);
                final StringBuilder pic = new StringBuilder();
                for (int i = 0; i < jsonPics.size(); i++) {
                    if (Util.isEmpty(jsonPics.get(i).getMediaPath())) {
                        continue;
                    }
                    pic.append(imgUpyunPaths.get(jsonPics.get(i).getMediaPath()));
                    if (i != jsonPics.size() - 1) {
                        pic.append(",");
                    }
                }
                if (!Util.isEmpty(pic.toString())) {
                    api.setPic(pic.toString());
                }
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean bean) {
                        Intent intent = getIntent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(CommentInsertActivity.this, Util.checkErrorType(error));
                    }
                });
            }
        });
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMMEND_DETAIL_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CommendDetailBean>() {
            @Override
            public void onResponse(CommendDetailBean response) {
                showData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(CommentInsertActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void showData(CommendDetailBean detail) {
        itemIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentInsertActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", productId);
                startActivity(intent);
            }
        });
        itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentInsertActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", productId);
                startActivity(intent);
            }
        });
        if (!Util.isEmpty(detail.getData().getComment().getProductImg())) {
            UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(detail.getData().getComment().getProductImg()), itemIv);
        }
        tvStyle.setText(detail.getData().getComment().getSkuProperty());
        tvPrice.setText(String.format(getString(R.string.label_price), price));
        itemInfo.setText(detail.getData().getComment().getTitle());
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

    @Subscribe
    public void onEvent(JsonPic pic) {
        if (!imgUpyunPaths.containsKey(pic.getMediaPath())) {
            imgUpyunPaths.put(pic.getMediaPath(), pic.getUploadPath());
            uploadedNum++;
            changeTvState();
        }
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

    private void addPic(ArrayList<JsonPic> pics) {
        if (pics == null || pics.size() == 0) return;
        int picSize = pics.size();
        View view;
        for (int i = 0; i < picSize; i++) {
            view = LayoutInflater.from(this).inflate(R.layout.list_item_post_image2, new RelativeLayout(this), false);
            ProcessImageView image = (ProcessImageView) view.findViewById(R.id.image);
            ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
            ImageView addBtn = (ImageView) view.findViewById(R.id.add_btn);
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
                delete.setOnClickListener(new OnPhotoDeleteClickListener(pics.get(i)));
                Glide.with(this)
                        .load(Uri.fromFile(new File(Util.getD2cPicUrl(pics.get(i).getMediaPath()))))
                        .error(R.mipmap.ic_default_pic)
                        .into(image);
                image.userId = 1000;
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
        Matisse.from(CommentInsertActivity.this)
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

    @Override
    public void onBackPressed() {
        if (contentEt.getText().toString().length() > 0 || jsonPics.size() > 1) {
            hideKeyboard(null);
            new AlertDialog.Builder(this)
                    .setMessage(R.string.msg_exit_again_judge)
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
}
