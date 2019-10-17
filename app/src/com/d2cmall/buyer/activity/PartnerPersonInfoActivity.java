package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UpdateAccountApi;
import com.d2cmall.buyer.api.UpdatePartnerInfoApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.PartnerInfoBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AddressPop;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SingleSelectPop;
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

//买手个人信息界面
public class PartnerPersonInfoActivity extends BaseActivity  {

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
    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.ll_partner_today_data)
    LinearLayout llPartnerTodayData;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.ll_partner_account)
    LinearLayout llPartnerAccount;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.ll_partner_nickname)
    LinearLayout llPartnerNickname;
    @Bind(R.id.ll_more_info)
    LinearLayout llMoreInfo;
    @Bind(R.id.ll_cash_info)
    LinearLayout llCashInfo;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private PartnerInfoBean mPartnerInfoBean;
    private String newPartnerHeadPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_person_info);
        ButterKnife.bind(this);
        loadData();
        nameTv.setText("个人信息");
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerInfoBean>() {
            @Override
            public void onResponse(PartnerInfoBean partnerInfoBean) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                mPartnerInfoBean = partnerInfoBean;
                setUserInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(PartnerPersonInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private void setUserInfo() {

        if (mPartnerInfoBean == null) {
            return;
        }
        UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(mPartnerInfoBean.getData().getPartner().getHeadPic()),
                ivHead, R.mipmap.ic_default_avatar);
        tvNickname.setText(mPartnerInfoBean.getData().getPartner().getName());
        tvAccount.setText(mPartnerInfoBean.getData().getPartner().getLoginCode());
    }

    @OnClick({R.id.back_iv, R.id.iv_head, R.id.tv_nickname,  R.id.ll_more_info, R.id.ll_cash_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.iv_head:
                showPop();
                break;
            case R.id.tv_nickname:
                Intent intent = new Intent(this, EditNickNameActivity.class);
                intent.putExtra("content", tvNickname.getText().toString());
                intent.putExtra("type", 2); //      修改买手昵称
                if(mPartnerInfoBean!=null){
                    intent.putExtra("partnerId", mPartnerInfoBean.getData().getPartner().getId());
                }
                startActivityForResult(intent, Constants.RequestCode.EDIT_NICK);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.ll_more_info:
                startActivity(new Intent(PartnerPersonInfoActivity.this, PartnerMoreInfoActivity.class));
                break;
            case R.id.ll_cash_info:
                startActivity(new Intent(PartnerPersonInfoActivity.this, PartnerCashIdentificationActivity.class));
                break;
        }
    }

    private void showPop() {
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


    List<Uri> mSelected = new ArrayList<>();
    List<String> pathSelected = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
            switch (requestCode) {
                case Constants.RequestCode.EDIT_NICK:
                    tvNickname.setText(user.getNickname());
                    break;
                case 456:
                    mSelected = Matisse.obtainResult(data);
                    pathSelected = Matisse.obtainPathResult(data);
                    Intent intent = new Intent(this, CropActivity.class);
                    intent.setData(mSelected.get(0));
                    intent.putExtra("path", pathSelected.get(0));
                    startActivityForResult(intent, 1123);
                    break;
                case 1123:
                    if (data != null) {
                        Uri uri = data.getData();
                        String cropPath = data.getStringExtra("cropPath");
                        Glide.with(this)
                                .load(uri)
                                .bitmapTransform(new CropCircleTransformation(this))
                                .error(R.mipmap.ic_default_avatar)
                                .crossFade()
                                .into(ivHead);
                        uploadFile(cropPath);
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(String filePath) {
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
                    newPartnerHeadPic = jsonObject.optString("url");
                    requestUpdatePartnerTask();
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

    private void choosePic() {
        Matisse.from(PartnerPersonInfoActivity.this)
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


    private void requestUpdatePartnerTask() {
        UpdatePartnerInfoApi api = new UpdatePartnerInfoApi();
        if (mPartnerInfoBean.getData() != null) {
            api.setId(mPartnerInfoBean.getData().getPartner().getId());
        }
        api.setHeadPic(newPartnerHeadPic);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(PartnerPersonInfoActivity.this, "更新完成");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PartnerPersonInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private String addZero(String str) {
        String result = str;
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }



    private void setEmptyView(int type) {
        scrollView.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
        } else {
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

}
