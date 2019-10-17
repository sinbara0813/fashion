package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
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
import com.d2cmall.buyer.api.UpdateAccountApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MeasuredLayout;
import com.d2cmall.buyer.widget.RoundedImageView;
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
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by rookie on 2017/10/13.
 * 完善个人信息
 */

public class CompleteInfoActivity extends BaseActivity implements MeasuredLayout.OnKeyboardHideListener {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ll_text)
    LinearLayout llText;
    @Bind(R.id.finish)
    TextView finish;
    @Bind(R.id.tv_men)
    TextView tvMen;
    @Bind(R.id.tv_women)
    TextView tvWomen;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.et_nick_name)
    ClearEditText etNickName;
    @Bind(R.id.nick_name_rl)
    RelativeLayout nickNameRl;
    @Bind(R.id.image_set_head)
    ImageView imageSetHead;
    @Bind(R.id.image_head)
    RoundedImageView imageHead;
    @Bind(R.id.rl_finish)
    RelativeLayout rl_finish;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private boolean hasSetSex = false;
    private boolean hasSetHead = false;
    private boolean hasSetNickName = false;
    private String sex = "";
    private String newHeadPic = "";
    private String imagePath = "";
    UserBean.DataEntity.MemberEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        user = Session.getInstance().getUserFromFile(CompleteInfoActivity.this);
        if(!Util.isEmpty(user.getHead())){
            hasSetHead=true;
            UniversalImageLoader.displayImage(this,Util.getD2cPicUrl(user.getHead()),
                    imageHead, R.mipmap.ic_default_avatar);
            imageSetHead.setVisibility(View.GONE);
            imageHead.setVisibility(View.VISIBLE);
        }
        if(user.getHasNickName()){
            hasSetNickName=true;
            etNickName.setText(user.getNickname());
        }
        if(!Util.isEmpty(user.getSex())){
            if(user.getSex().equals("男")){
                tvMen.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_info_men_s);
                nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                tvMen.setCompoundDrawables(nav_dowm, null, null, null);
                sex = "男";
                tvWomen.setTextColor(Color.parseColor("#FFFFFFFF"));
                Drawable nav_dowm0 = getResources().getDrawable(R.mipmap.icon_info_women_us);
                nav_dowm0.setBounds(0, 0, nav_dowm0.getMinimumWidth(), nav_dowm0.getMinimumHeight());
                tvWomen.setCompoundDrawables(nav_dowm0, null, null, null);
                hasSetSex = true;
            }else {
                tvWomen.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm2 = getResources().getDrawable(R.mipmap.icon_info_women_s);
                nav_dowm2.setBounds(0, 0, nav_dowm2.getMinimumWidth(), nav_dowm2.getMinimumHeight());
                tvWomen.setCompoundDrawables(nav_dowm2, null, null, null);
                sex = "女";
                tvMen.setTextColor(Color.parseColor("#FFFFFFFF"));
                Drawable nav_dowm3 = getResources().getDrawable(R.mipmap.icon_info_men_us);
                nav_dowm3.setBounds(0, 0, nav_dowm3.getMinimumWidth(), nav_dowm3.getMinimumHeight());
                tvMen.setCompoundDrawables(nav_dowm3, null, null, null);
                hasSetSex = true;
            }
        }
    }

    public View getContentView() {
        MeasuredLayout loginLayout = new MeasuredLayout(this, null, R.layout.activity_complete_info);
        loginLayout.setOnKeyboardHideListener(this);
        return loginLayout;
    }

    @Override
    public void onKeyboardHide(boolean hide) {
        if (hide) {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) rl_finish.getLayoutParams();
            rl.setMargins(0, 0, 0, ScreenUtil.dip2px(136));
            RelativeLayout.LayoutParams ll= (RelativeLayout.LayoutParams) llSex.getLayoutParams();
            ll.setMargins(0,0,0,ScreenUtil.dip2px(64));
            llSex.setLayoutParams(ll);
            rl_finish.setLayoutParams(rl);
            llText.setVisibility(View.VISIBLE);
        } else {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) rl_finish.getLayoutParams();
            rl.setMargins(0, ScreenUtil.dip2px(0), 0, ScreenUtil.dip2px(281));
            RelativeLayout.LayoutParams ll= (RelativeLayout.LayoutParams) llSex.getLayoutParams();
            ll.setMargins(0,0,0,ScreenUtil.dip2px(44));
            llSex.setLayoutParams(ll);
            rl_finish.setLayoutParams(rl);
            llText.setVisibility(View.GONE);
        }
    }

    private void requestUpdateAccountTask(final String newHeadPic, final String newSex, final String nickname) {
        UpdateAccountApi api = new UpdateAccountApi();
        if (!Util.isEmpty(newHeadPic)) {
            api.setHeadPic(newHeadPic);
        }
        if (!Util.isEmpty(newSex)) {
            api.setSex(newSex);
        }
        api.setNickname(nickname);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                progressBar.setVisibility(View.GONE);
                if (user != null) {
                    if (!Util.isEmpty(newHeadPic)) {
                        user.setHead(newHeadPic);
                    }
                    if (!Util.isEmpty(newSex)) {
                        user.setSex(newSex);
                    }
                    if (!Util.isEmpty(nickname)) {
                        user.setNickname(nickname);
                    }
                }
                Session.getInstance().saveUserToFile(CompleteInfoActivity.this, user);
                Util.showToast(CompleteInfoActivity.this, "完善信息成功~");
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(CompleteInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    List<Uri> mSelected = new ArrayList<>();
    List<String> pathSelected = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 456:
                    mSelected = Matisse.obtainResult(data);
                    pathSelected=Matisse.obtainPathResult(data);
                    Glide.with(this)
                            .load(mSelected.get(0))
                            .bitmapTransform(new CropCircleTransformation(this))
                            .error(R.mipmap.ic_default_avatar)
                            .crossFade()
                            .into(imageHead);
                    imageSetHead.setVisibility(View.GONE);
                    imageHead.setVisibility(View.VISIBLE);
                    imagePath = pathSelected.get(0);
                    hasSetHead = true;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(String filePath) {
        progressBar.setVisibility(View.VISIBLE);
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_AVATAR));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    newHeadPic = jsonObject.optString("url");
                    requestUpdateAccountTask(newHeadPic, sex, etNickName.getText().toString().trim());
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
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
        Matisse.from(CompleteInfoActivity.this)
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

    @OnClick({R.id.back, R.id.tv_men, R.id.tv_women, R.id.image_set_head, R.id.image_head, R.id.finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish:
                over();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_men:
                tvMen.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_info_men_s);
                nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                tvMen.setCompoundDrawables(nav_dowm, null, null, null);
                sex = "男";
                tvWomen.setTextColor(Color.parseColor("#FFFFFFFF"));
                Drawable nav_dowm0 = getResources().getDrawable(R.mipmap.icon_info_women_us);
                nav_dowm0.setBounds(0, 0, nav_dowm0.getMinimumWidth(), nav_dowm0.getMinimumHeight());
                tvWomen.setCompoundDrawables(nav_dowm0, null, null, null);
                hasSetSex = true;
                break;
            case R.id.tv_women:
                tvWomen.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm2 = getResources().getDrawable(R.mipmap.icon_info_women_s);
                nav_dowm2.setBounds(0, 0, nav_dowm2.getMinimumWidth(), nav_dowm2.getMinimumHeight());
                tvWomen.setCompoundDrawables(nav_dowm2, null, null, null);
                sex = "女";
                tvMen.setTextColor(Color.parseColor("#FFFFFFFF"));
                Drawable nav_dowm3 = getResources().getDrawable(R.mipmap.icon_info_men_us);
                nav_dowm3.setBounds(0, 0, nav_dowm3.getMinimumWidth(), nav_dowm3.getMinimumHeight());
                tvMen.setCompoundDrawables(nav_dowm3, null, null, null);
                hasSetSex = true;
                break;
            case R.id.image_set_head:
                showPop();
                break;
            case R.id.image_head:
                showPop();
                break;
        }
    }

    private void over() {
        if (!Util.isEmpty(etNickName.getText().toString().trim())) {
            hasSetNickName = true;
        } else {
            hasSetNickName = false;
        }
        if (hasSetSex && hasSetHead && hasSetNickName) {
            if(!Util.isEmpty(imagePath)){
                uploadFile(imagePath);
            }else {
                requestUpdateAccountTask(newHeadPic, sex, etNickName.getText().toString().trim());
            }
        } else {
            Util.showToast(this, "请完善您的信息~");
        }
    }
}
