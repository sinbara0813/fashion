package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.WardrobeDeleteApi;
import com.d2cmall.buyer.api.WardrobeInsertApi;
import com.d2cmall.buyer.api.WardrobeupDataApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.MaLongClassifyBean;
import com.d2cmall.buyer.bean.MaLongTagColorBean;
import com.d2cmall.buyer.bean.WardrobeDetailBean;
import com.d2cmall.buyer.bean.WardrobeListItemBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AutoNewLineLayout;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.FindLikePop;
import com.d2cmall.buyer.widget.SelectTypePop;
import com.d2cmall.buyer.widget.SharePop;
import com.google.gson.Gson;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class WardrobeItemDetialActivity extends BaseActivity implements SelectTypePop.SelectIdListener {

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.anl_tags)
    AutoNewLineLayout anlTags;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.iv_find_like)
    ImageView ivFindLike;
    @Bind(R.id.iv_edit)
    ImageView ivEdit;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.edit_price)
    ClearEditText editPrice;
    @Bind(R.id.anl_occasions)
    AutoNewLineLayout anlOccasions;
    @Bind(R.id.anl_edit_tags)
    AutoNewLineLayout anlEditTags;
    @Bind(R.id.ll_edit)
    LinearLayout llEdit;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.ll_bottom_colors)
    LinearLayout llBottomColors;
    @Bind(R.id.ll_edit_colors)
    LinearLayout llEditColors;
    @Bind(R.id.anl_season)
    AutoNewLineLayout anlSeason;
    private AlertDialog alertDialog;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> list;
    private JsonPic jsonPic;
    private MaLongClassifyBean maLongClassifyBean;
    private MaLongTagColorBean maLongTagColorBean;
    private String picUrl;
    private Map<String, ArrayList<Map>> categoryMap;//衣橱分类
    private int categoryId = 1;
    private String action;
    private WardrobeListItemBean.DataBean.MyWardrobesBean.ListBean wardrobeBean;
    private boolean hasPublishSuccess;
    private WardrobeDetailBean insertWardrobeDetailBean;//发布成功返回的对象
    //衣橱详情,发布&展示 界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe_item_detial);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        loadCategory();
        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拦截
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isEmpty(action) && "publish".equals(action)) {
                    return;
                }
                if (llEdit.getVisibility() == View.VISIBLE) {
                    llEdit.setVisibility(View.GONE);
                    tvSave.setVisibility(View.GONE);
                    return;
                }
                if (llBottom.getVisibility() == View.GONE) {
                    llBottom.setVisibility(View.VISIBLE);
                } else {
                    llBottom.setVisibility(View.GONE);
                }
            }
        });
        //详情
        wardrobeBean = (WardrobeListItemBean.DataBean.MyWardrobesBean.ListBean) getIntent().getSerializableExtra("wardrobeBean");
        addOccasionsSeason();
        if (wardrobeBean != null) {
            setDatatoView();
            llBottom.setVisibility(View.VISIBLE);
            return;
        }

        //发布
        action = getIntent().getStringExtra("action");
        if (!Util.isEmpty(action) && "publish".equals(action)) {
            handPermission();
        }

    }

    private void setDatatoView() {
        progressBar.setVisibility(View.VISIBLE);
        UniversalImageLoader.displayImageByPb(this, Util.getD2cPicUrl(wardrobeBean.getPic(), ScreenUtil.getDisplayWidth()), R.mipmap.ic_logo_empty6, ivImage, progressBar);
        tvTitle.setText(wardrobeBean.getTopName() + "·" + wardrobeBean.getCategoryName());
        tvType.setText(wardrobeBean.getTopName() + "/" + wardrobeBean.getCategoryName());
        String scene = wardrobeBean.getScene();
        if(! Util.isEmpty(scene) && scene.endsWith(",")){
            scene=scene.substring(0,scene.length()-1);
        }
        if (wardrobeBean.getSeason().contains(",")) {
            tvDesc.setText(wardrobeBean.getSeason().replaceAll(",", "·") + scene.replaceAll(",", "·"));
        } else {
            tvDesc.setText(wardrobeBean.getSeason() + "·" + scene.replaceAll(",", "·"));
        }
        addTags(anlTags, null);
        addTags(anlEditTags, null);
        editPrice.setText(wardrobeBean.getPrice());
        tvPrice.setText("¥" + wardrobeBean.getPrice());
        initColorView(llBottomColors, null);
        initColorView(llEditColors, null);

        String[] occasions = scene.split(",");
        for (int k = 0; k < occasions.length; k++) {
            int childCount = anlOccasions.getChildCount();
            for (int m = 0; m < childCount; m++) {
                CheckBox checkBox = (CheckBox) anlOccasions.getChildAt(m);
                if (!Util.isEmpty(checkBox.getText().toString()) && checkBox.getText().toString().equals(occasions[k])) {
                    checkBox.setChecked(true);
                }
            }
        }
        String season = wardrobeBean.getSeason();
        String[] seasons = season.split(",");
        for (int m = 0; m < seasons.length; m++) {
            int childCount = anlSeason.getChildCount();
            for (int n = 0; n < childCount; n++) {
                CheckBox checkBox = (CheckBox) anlSeason.getChildAt(n);
                if (!Util.isEmpty(checkBox.getText().toString()) && checkBox.getText().toString().equals(seasons[m])) {
                    checkBox.setChecked(true);
                }
            }
        }
    }


    private void initColorView(LinearLayout llColors, WardrobeDetailBean.DataBean.WardrobeBean wardrobe) {
        String[] colors;
        if (wardrobe != null) {
            colors = wardrobe.getColor().split(";");
        } else {
            colors = wardrobeBean.getColor().split(";");
        }
        if (colors == null) {
            return;
        }
        for (int j = 0; j < colors.length; j++) {
            String color = colors[j];
            if(Util.isEmpty(color) || !color.contains(",")){
                return;
            }
            String[] colorWeight = color.split(",");
            View view = new View(this);
            if (colorWeight[0].startsWith("#")) {
                view.setBackgroundColor(Color.parseColor(colorWeight[0]));
            } else {
                view.setBackgroundColor(Color.parseColor("#" + colorWeight[0]));
            }
            int width = (int) ((ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32)) * Double.valueOf(colorWeight[1]));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ScreenUtil.dip2px(16));
            view.setLayoutParams(layoutParams);
            llColors.addView(view);
        }
    }

    private void addTags(AutoNewLineLayout autoNewLineLayout, WardrobeDetailBean.DataBean.WardrobeBean wardrobe) {
        String[] tags;
        if (wardrobe != null) {
            if (wardrobe.getTags() == null) {
                return;
            }
            tags = wardrobe.getTags().split(",");
        } else {
            if (wardrobeBean.getTags() == null) {
                return;
            }
            tags = wardrobeBean.getTags().split(",");
        }
        if (tags == null) {
            return;
        }
        for (int i = 0; i < tags.length; i++) {
            if(Util.isEmpty(tags[i])){
                continue;
            }
            CheckBox checkBox = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_checkbox_tag, autoNewLineLayout, false);
            checkBox.setText(tags[i]);
            checkBox.setChecked(true);
            if (autoNewLineLayout == anlTags) {
                checkBox.setEnabled(false);
            }
            autoNewLineLayout.addView(checkBox);
        }
    }

    private void addOccasionsSeason() {
        ArrayList<String> occasions = new ArrayList<>();
        occasions.add("上班");
        occasions.add("约会");
        occasions.add("派对");
        occasions.add("旅行");
        occasions.add("居家");
        for (int i = 0; i < occasions.size(); i++) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_checkbox_tag, anlOccasions, false);
            checkBox.setText(occasions.get(i));
            anlOccasions.addView(checkBox);
        }
        ArrayList<String> seasons = new ArrayList<>();
        seasons.add("春");
        seasons.add("夏");
        seasons.add("秋");
        seasons.add("冬");
        for (int i = 0; i < seasons.size(); i++) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_checkbox_tag, anlSeason, false);
            checkBox.setText(seasons.get(i));
            anlSeason.addView(checkBox);
        }
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

    private void choosePic() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(new GlideEngine())
                .forResult(456);
    }

    private void addEditTags() {
        if (maLongTagColorBean == null || maLongTagColorBean.getLabels() == null || maLongTagColorBean.getLabels().size() == 0) {
            return;
        }
        //去重
        List<String> labels = maLongTagColorBean.getLabels();
        Set set = new HashSet();
        set.addAll(labels);
        labels.clear();
        labels.addAll(set);
        int size = labels.size() > 10 ? 10 : labels.size();

        for (int i = 0; i < size; i++) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_checkbox_tag, anlOccasions, false);
            checkBox.setText(maLongTagColorBean.getLabels().get(i));
            checkBox.setChecked(true);
            anlEditTags.addView(checkBox);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 456:
                if (resultCode == RESULT_OK) {
                    List<String> strings = Matisse.obtainPathResult(data);
                    JsonPic jsonPic = new JsonPic();
                    jsonPic.setMediaPath(strings.get(0));
                    WardrobeItemDetialActivity.this.jsonPic = jsonPic;
                    Glide.with(WardrobeItemDetialActivity.this).load(Uri.fromFile(new File(jsonPic.getMediaPath()))).asBitmap().into(ivImage);
                    progressBar.setVisibility(View.VISIBLE);
                    tvSave.setVisibility(View.VISIBLE);
                    tvSave.setEnabled(false);
                    llEdit.setVisibility(View.VISIBLE);
                    uploadFile(jsonPic);
                } else {
                    finish();
                }
                break;
        }
    }

    private void loadCategory() {
        HttpUtils.doGetAsyn(Constants.API_URL + Constants.WARDROBE_CATEGORY, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject topCategorys = data.getJSONObject("topCategorys");
                    Gson gson = new Gson();
                    categoryMap = gson.fromJson(topCategorys.toString(), Map.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void uploadFile(JsonPic jsonPic) {
        if (jsonPic == null || Util.isEmpty(jsonPic.getMediaPath())) {
            Util.showToast(this, "图品信息不存在,请重试");
            setEditGone();
            return;
        }
        File file = new File(jsonPic.getMediaPath());
        if (!file.exists()) {
            Util.showToast(this, "图品信息不存在,请重试");
            setEditGone();
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
                    picUrl = jsonObject.optString("url");
                    if (!Util.isEmpty(picUrl)) {
                        requsetMaLongClassfiy(Util.getD2cPicUrl(picUrl));
                    } else {
                        setEditGone();
                    }

                } catch (JSONException e) {
                    setEditGone();
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

    private void setEditGone() {
        progressBar.setVisibility(View.GONE);
        tvSave.setVisibility(View.GONE);
        llEdit.setVisibility(View.GONE);
    }

    private void requsetMaLongTagColor(String url) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("url=");
            stringBuilder.append(url);
            HttpUtils.doMaLongPostAsyn("https://api.productai.cn/dressing/_0000111", stringBuilder.toString(), new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    try {
                        Gson gson = new Gson();
                        maLongTagColorBean = gson.fromJson(result, MaLongTagColorBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setEditViewData();
                            }
                        });
                    } catch (Exception e) {
                        setEditGone();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requsetMaLongClassfiy(String url) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("url=");
            stringBuilder.append(url);
            HttpUtils.doMaLongPostAsyn("https://api.productai.cn/custom_training/8ge7xo5q", stringBuilder.toString(), new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    try {
                        Gson gson = new Gson();
                        maLongClassifyBean = gson.fromJson(result, MaLongClassifyBean.class);
                        requsetMaLongTagColor(url);

                    } catch (Exception e) {
                        setEditGone();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setEditViewData() {
        if (isFinishing()) {
            return;
        }
        tvSave.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        addEditTags();
        setEditColors(llEditColors);
        setEditColors(llBottomColors);
    }

    private void setEditColors(LinearLayout llColors) {
        if (maLongTagColorBean == null || maLongTagColorBean.getResults() == null || maLongTagColorBean.getResults().size() == 0 || maLongTagColorBean.getResults().get(0) == null
                || maLongTagColorBean.getResults().get(0).getColors() == null || maLongTagColorBean.getResults().get(0).getColors().size() == 0) {
            return;
        }
        for (int i = 0; i < maLongTagColorBean.getResults().get(0).getColors().size(); i++) {
            MaLongTagColorBean.ResultsBean.ColorsBean colorsBean = maLongTagColorBean.getResults().get(0).getColors().get(i);
            List<Integer> rgb = colorsBean.getRgb();
            String color = null;
            if (rgb != null && rgb.size() > 0) {
                color = rgb2Hex(rgb);
            }
            View view = new View(this);
            view.setBackgroundColor(Color.parseColor(color));
            int width = (int) ((ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32)) * maLongTagColorBean.getResults().get(0).getColors().get(i).getPercent());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ScreenUtil.dip2px(16));
            view.setLayoutParams(layoutParams);
            llColors.addView(view);
        }
    }

    /**
     * rgb数组转Color的16进制颜色值
     * rgb - rgb数组——[63,226,197]
     * return Color的16进制颜色值——#3FE2C5
     *
     * @param rgb
     */
    public String rgb2Hex(List<Integer> rgb) {
        String hexCode = "#";
        for (int i = 0; i < rgb.size(); i++) {
            Integer rgbItem = rgb.get(i);
            if (rgbItem == null) {
                rgbItem = 0;
            }
            if (rgbItem < 0) {
                rgbItem = 0;
            } else if (rgbItem > 255) {
                rgbItem = 255;
            }
            String[] code = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
            int lCode = rgbItem / 16;//先获取商，例如，255 / 16 == 15
            int rCode = rgbItem % 16;//再获取余数，例如，255 % 16 == 15
            hexCode += code[lCode] + code[rCode];//FF
        }
        return hexCode;
    }

    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_find_like, R.id.iv_edit, R.id.iv_delete, R.id.iv_image, R.id.tv_save, R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                share();
                break;
            case R.id.iv_find_like:
                findLike();
                break;
            case R.id.iv_edit:
                edit();
                break;
            case R.id.iv_delete:
                delete();
                break;
            case R.id.iv_image:
                if (llBottom.getVisibility() == View.VISIBLE) {
                    llBottom.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_save:
                if (wardrobeBean != null || hasPublishSuccess) {
                    upDataWardrobe();
                } else {
                    insertWardrobe();
                }
                break;
            case R.id.tv_type://选择品类
                selectType();
                break;
        }
    }

    private void upDataWardrobe() {
        String price = editPrice.getText().toString().trim();
        if (Util.isEmpty(price)) {
            Util.showToast(this, "请填写价格");
            return;
        }
        String type = tvType.getText().toString().trim();
        if (Util.isEmpty(type)) {
            Util.showToast(this, "请选择分类");
            return;
        }
        String session = getSession();
        if (Util.isEmpty(session)) {
            Util.showToast(this, "请选择场合");
            return;
        }
        String season = getSeason();
        if (Util.isEmpty(season)) {
            Util.showToast(this, "请选择季节");
            return;
        }
        tvSave.setVisibility(View.GONE);
        llEdit.setVisibility(View.GONE);
        String editTags = getEditTags();
        WardrobeDetailBean.DataBean.WardrobeBean newWardrobeBean = new WardrobeDetailBean.DataBean.WardrobeBean();
        newWardrobeBean.setPrice(price);
        newWardrobeBean.setCategoryName(type);
        newWardrobeBean.setScene(session);
        newWardrobeBean.setSeason(season);
        newWardrobeBean.setTags(editTags);
        WardrobeupDataApi wardrobeupdataApi = new WardrobeupDataApi();
        wardrobeupdataApi.setPic(picUrl);
        wardrobeupdataApi.setCategoryId(categoryId);
        wardrobeupdataApi.setPrice(price);
        wardrobeupdataApi.setScene(session);
        wardrobeupdataApi.setSeason(season);
        wardrobeupdataApi.setTags(editTags);
        wardrobeupdataApi.setColor(getEditColor());
        if (hasPublishSuccess) {
            wardrobeupdataApi.setId(this.insertWardrobeDetailBean.getData().getWardrobe().getId());
        } else {
            wardrobeupdataApi.setId(this.wardrobeBean.getId());
        }
        D2CApplication.httpClient.loadingRequest(wardrobeupdataApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Util.showToast(WardrobeItemDetialActivity.this, "修改成功");
                //刷新底部View数据
                if (newWardrobeBean != null) {
                    String[] split = newWardrobeBean.getCategoryName().split("/");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < split.length; i++) {
                        if (i != split.length - 1) {
                            stringBuilder.append(split[i]);
                            stringBuilder.append("·");
                        } else {
                            stringBuilder.append(split[i]);
                        }
                    }
                    tvTitle.setText(stringBuilder.toString());
                    tvDesc.setText(newWardrobeBean.getSeason().replaceAll(",", "") + newWardrobeBean.getScene().replaceAll(",", ""));
                    anlTags.removeAllViews();
                    addTags(anlTags, newWardrobeBean);
                    tvPrice.setText("¥" + newWardrobeBean.getPrice());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(WardrobeItemDetialActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void insertWardrobe() {
        String price = editPrice.getText().toString().trim();
        if (Util.isEmpty(price)) {
            Util.showToast(this, "请填写价格");
            return;
        }
        String type = tvType.getText().toString().trim();
        if (Util.isEmpty(type)) {
            Util.showToast(this, "请选择分类");
            return;
        }
        String session = getSession();
        if (Util.isEmpty(session)) {
            Util.showToast(this, "请选择场合");
            return;
        }
        String season = getSeason();
        if (Util.isEmpty(season)) {
            Util.showToast(this, "请选择季节");
            return;
        }
        tvSave.setVisibility(View.GONE);
        llEdit.setVisibility(View.GONE);
        String editTags = getEditTags();
        WardrobeInsertApi wardrobeInsertApi = new WardrobeInsertApi();
        wardrobeInsertApi.setPic(picUrl);
        wardrobeInsertApi.setCategoryId(categoryId);
        wardrobeInsertApi.setPrice(price);
        wardrobeInsertApi.setScene(session);
        wardrobeInsertApi.setSeason(season);
        wardrobeInsertApi.setTags(editTags);
        wardrobeInsertApi.setColor(getEditColor());
        D2CApplication.httpClient.loadingRequest(wardrobeInsertApi, new BeanRequest.SuccessListener<WardrobeDetailBean>() {
            @Override
            public void onResponse(WardrobeDetailBean wardrobeDetailBean) {
                Util.showToast(WardrobeItemDetialActivity.this, "保存成功");
                insertWardrobeDetailBean = wardrobeDetailBean;
                hasPublishSuccess = true;
                //发布成功,更新View数据
                startActivity(new Intent(WardrobeItemDetialActivity.this, WardrobeListActivity.class).putExtra("type",wardrobeDetailBean.getData().getWardrobe().getTopName()));
//                if (wardrobeDetailBean != null) {
//                    WardrobeDetailBean.DataBean.WardrobeBean wardrobe = wardrobeDetailBean.getData().getWardrobe();
//                    tvTitle.setText(wardrobe.getTopName() + "·" + wardrobe.getCategoryName());
//                    String scene = wardrobe.getScene();
//                    if(! Util.isEmpty(scene) && scene.endsWith(",")){
//                        scene=scene.substring(0,scene.length()-1);
//                    }
//                    if (wardrobe.getSeason().contains(",")) {
//                        tvDesc.setText(wardrobe.getSeason().replaceAll(",", "·") + scene.replaceAll(",", "·"));
//                    } else {
//                        tvDesc.setText(wardrobe.getSeason() + "·" + scene.replaceAll(",", "·"));
//                    }
//                    addTags(anlTags, wardrobe);
//                    tvPrice.setText("¥" + wardrobe.getPrice());
//                    initColorView(llBottomColors, wardrobe);
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(WardrobeItemDetialActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private String getEditColor() {
        if (maLongTagColorBean == null || maLongTagColorBean.getResults() == null || maLongTagColorBean.getResults().get(0).getColors() == null || maLongTagColorBean.getResults().get(0).getColors().size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maLongTagColorBean.getResults().get(0).getColors().size(); i++) {
            MaLongTagColorBean.ResultsBean.ColorsBean colorsBean = maLongTagColorBean.getResults().get(0).getColors().get(i);
            String color = rgb2Hex(colorsBean.getRgb());
            color.replace("#", "");
            stringBuilder.append(color);
            stringBuilder.append(",");
            stringBuilder.append(colorsBean.getPercent());
            if (i != maLongTagColorBean.getResults().get(0).getColors().size() - 1) {
                stringBuilder.append(";");
            }

        }
        return stringBuilder.toString();
    }

    private String getEditTags() {
        int childCount = anlEditTags.getChildCount();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < childCount; i++) {
            CheckBox checkBox = (CheckBox) anlEditTags.getChildAt(i);
            if (checkBox.isChecked()) {
                stringBuilder.append(checkBox.getText());
                stringBuilder.append(",");
            }
        }
        String tags = stringBuilder.toString();
        if(! Util.isEmpty(tags) && tags.endsWith(",")){
            return tags.substring(0,tags.length()-1);
        }else{
            return tags;
        }
    }

    private String getSeason() {
        int childCount = anlSeason.getChildCount();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < childCount; i++) {
            CheckBox checkBox = (CheckBox) anlSeason.getChildAt(i);
            if (checkBox.isChecked()) {
                stringBuilder.append(checkBox.getText());
                stringBuilder.append(",");
            }
        }
        String season = stringBuilder.toString();
        if(! Util.isEmpty(season) && season.endsWith(",")){
            return season.substring(0,season.length()-1);
        }else{
            return season;
        }
    }

    private String getSession() {
        int childCount = anlOccasions.getChildCount();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < childCount; i++) {
            CheckBox checkBox = (CheckBox) anlOccasions.getChildAt(i);
            if (checkBox.isChecked()) {
                stringBuilder.append(checkBox.getText());
                stringBuilder.append(",");
            }
        }
        String session = stringBuilder.toString();
        if(! Util.isEmpty(session) && session.endsWith(",")){
            return session.substring(0,session.length()-1);
        }else{
            return session;
        }
    }

    private void selectType() {
        SelectTypePop selectTypePop = new SelectTypePop(this, categoryMap, maLongClassifyBean);
        selectTypePop.show(getWindow().getDecorView());
        selectTypePop.setSelectIdListener(this);
    }

    private void delete() {
        if (alertDialog != null && alertDialog.isShowing()) {
            return;
        }
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.layout_round_dialog, null);
            TextView tvDelete = view.findViewById(R.id.tv_delete);
            TextView tvCancle = view.findViewById(R.id.tv_cancle);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                        if (wardrobeBean != null) {
                            requestDelet();
                        } else {
                            finish();
                        }
                    }
                }
            });
            tvCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
            });
            builder.setView(view);
            alertDialog = builder.create();
        }
        alertDialog.show();
    }

    private void requestDelet() {
        WardrobeDeleteApi wardrobeDeleteApi = new WardrobeDeleteApi();
        wardrobeDeleteApi.setId(wardrobeBean.getId());
        D2CApplication.httpClient.loadingRequest(wardrobeDeleteApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.WARDROBE_DELETE));
                Util.showToast(WardrobeItemDetialActivity.this, "删除成功");
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(WardrobeItemDetialActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void edit() {
        if (llEdit.getVisibility() == View.GONE) {
            llEdit.setVisibility(View.VISIBLE);
            tvSave.setVisibility(View.VISIBLE);
        }

    }

    private void findLike() {
        FindLikePop findLikePop;
        if (wardrobeBean == null) {
            findLikePop = new FindLikePop(this, picUrl);
        } else {
            findLikePop = new FindLikePop(this, wardrobeBean.getPic());
        }
        findLikePop.show(getWindow().getDecorView());
    }

    private void share() {
        SharePop sharePop = new SharePop(this);
        sharePop.setPic(true);
        if (wardrobeBean != null) {
            if (Util.isEmpty(wardrobeBean.getPic())) {
                return;
            }
            sharePop.setBigImageUrl(Util.getD2cPicUrl(wardrobeBean.getPic(), 360, 500));
        } else {
            if (Util.isEmpty(picUrl)) {
                return;
            }
            sharePop.setBigImageUrl(Util.getD2cPicUrl(picUrl, 360, 500));
        }
        sharePop.setBigBitmap(((BitmapDrawable) ivImage.getDrawable()).getBitmap());
        sharePop.show(getWindow().getDecorView());
    }

    @Override
    public void selectId(int id, String typeName) {
        categoryId = id;
        tvType.setText(typeName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!Util.isEmpty(picUrl) && !hasPublishSuccess) {
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setMessage("所选内容尚未保存,是否退出?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
