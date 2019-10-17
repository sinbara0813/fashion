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
import android.text.Editable;
import android.text.TextWatcher;
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
import com.d2cmall.buyer.api.AddCertificationApi;
import com.d2cmall.buyer.api.UploadIDCardPicsApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCertificationActivity extends BaseActivity {

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
    @Bind(R.id.et_name)
    ClearEditText etName;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.et_id_number)
    ClearEditText etIdNumber;
    @Bind(R.id.id_card_front)
    ImageView ivIdCardFront;
    @Bind(R.id.id_card_opposite)
    ImageView ivIdCardOpposite;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.tv_front_cover)
    TextView tvFrontCover;
    @Bind(R.id.iv_delete_front)
    ImageView ivDeleteFront;
    @Bind(R.id.tv_opposite_cover)
    TextView tvOppositeCover;
    @Bind(R.id.iv_delete_opposite)
    ImageView ivDeleteOpposite;
    @Bind(R.id.ll_example)
    LinearLayout llExample;
    @Bind(R.id.iv_example)
    ImageView ivExample;
    @Bind(R.id.tv_reupload)
    TextView tvReupload;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private List<Uri> uriList;
    private List<String> obtainPathResult;

    private String idCardFrontLocalPath = null;//本地身份证正面
    private String idCardOppositeLocalPath = null;//本地身份证反面
    private String idCardFrontRemotePath = null;//又拍云身份证正面
    private String idCardOppositeRemotePath = null;//又拍云身份证反面
    private boolean hasIdCardFront = false;
    private long certificationId;
    private String idNumber;
    private String name;
    private int isUpload;
    private int isDefault;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_certification);
        ButterKnife.bind(this);
        nameTv.setText(getString(R.string.label_certification));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("保存");
        titleRight.setTextSize(16);
        titleRight.setTextColor(getResources().getColor(R.color.color_black38));
        titleRight.setEnabled(false);
        //实名信息Id
        certificationId = getIntent().getLongExtra("certificationId", 0);
        //身份证号
        idNumber = getIntent().getStringExtra("idNumber");
        //姓名
        name = getIntent().getStringExtra("name");
        //是否上传身份证照片
        isUpload = getIntent().getIntExtra("isUpload", 0);
        isDefault = getIntent().getIntExtra("isDefault", 0);
        user = Session.getInstance().getUserFromFile(this);
        if(certificationId==0){ //新增
            initTextWatch();
        }else{         //查看编辑
            initDataToView();
        }
    }

    private void initDataToView() {
        etName.setText(name);
        etIdNumber.setText(idNumber);
        etName.setFocusable(false);
        etName.setEnabled(false);
        etIdNumber.setFocusable(false);
        etIdNumber.setEnabled(false);
        if(isUpload==1){//已经上传过身份证
            ivIdCardFront.setClickable(false);
            ivIdCardOpposite.setClickable(false);
            tvFrontCover.setVisibility(View.VISIBLE);
            tvOppositeCover.setVisibility(View.VISIBLE);
            UniversalImageLoader.displayImage(this,ivIdCardFront,R.mipmap.idcard_front);
            UniversalImageLoader.displayImage(this,ivIdCardOpposite,R.mipmap.idcard_back);
            tvReupload.setVisibility(View.VISIBLE);
            llExample.setVisibility(View.GONE);
            ivExample.setVisibility(View.GONE);
        }
    }

    private void initTextWatch() {
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkSaveEnable();
            }
        };
        etName.addTextChangedListener(textWatcher);
        etIdNumber.addTextChangedListener(textWatcher);
    }

    private void checkSaveEnable() {
        String name = etName.getText().toString().trim();
        String idnumber = etIdNumber.getText().toString().trim();
        if (Util.isEmpty(name) || Util.isEmpty(idnumber)) {
            titleRight.setTextColor(getResources().getColor(R.color.color_black38));
            titleRight.setEnabled(false);
        } else {
            titleRight.setTextColor(getResources().getColor(R.color.color_black85));
            titleRight.setEnabled(true);
        }
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.id_card_front, R.id.id_card_opposite,R.id.iv_delete_front, R.id.iv_delete_opposite, R.id.tv_reupload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                if(certificationId>0){//重新上传
                    checkPics();
                }else{  //新增认证
                    if (Util.isEmpty(etName.getText().toString().trim())) {
                        Util.showToast(this, "请填写您的姓名");
                        return;
                    }
                    if (Util.isEmpty(etIdNumber.getText().toString().trim())) {
                        Util.showToast(this, "请填写您的身份证号");
                        return;
                    }
                    if (!Util.isIdCardNumber(etIdNumber.getText().toString().trim())) {
                        Util.showToast(this, "请输入正确的身份证号");
                        return;
                    }
                    if (Util.isEmpty(idCardFrontLocalPath) && Util.isEmpty(idCardOppositeLocalPath)) {
                        requestCertification();//不包含身份证照片请求认证
                    } else if (!Util.isEmpty(idCardFrontLocalPath) && !Util.isEmpty(idCardOppositeLocalPath)) {
                        uploadFile(idCardFrontLocalPath,false);//包含身份证照片请求认证
                    } else {
                        Util.showToast(this, "请补全身份信息");
                        return;
                    }
                }

                break;
            case R.id.id_card_front://身份证正面
                showPop(Constants.RequestCode.ID_CARD_FRONT);
                break;
            case R.id.id_card_opposite://身份证反面
                showPop(Constants.RequestCode.ID_CARD_OPPOSITE);
                break;
            case R.id.iv_delete_front://删除身份证正面
                if(certificationId>0){
                    titleRight.setTextColor(getResources().getColor(R.color.color_black38));
                    titleRight.setEnabled(false);
                }
                ivDeleteFront.setVisibility(View.GONE);
                UniversalImageLoader.displayImage(this,ivIdCardFront,R.mipmap.btn_renxiang);
                idCardFrontLocalPath=null;
                idCardFrontRemotePath=null;
                break;
            case R.id.iv_delete_opposite://删除身份证反面
                if(certificationId>0){
                    titleRight.setTextColor(getResources().getColor(R.color.color_black38));
                    titleRight.setEnabled(false);
                }
                ivDeleteOpposite.setVisibility(View.GONE);
                UniversalImageLoader.displayImage(this,ivIdCardOpposite,R.mipmap.btn_guohui);
                idCardOppositeLocalPath=null;
                idCardOppositeRemotePath=null;
                break;
            case R.id.tv_reupload:      //重新上传身份证照片
                UniversalImageLoader.displayImage(this,ivIdCardFront,R.mipmap.btn_renxiang);
                UniversalImageLoader.displayImage(this,ivIdCardOpposite,R.mipmap.btn_guohui);
                ivIdCardFront.setClickable(true);
                ivIdCardOpposite.setClickable(true);
                tvFrontCover.setVisibility(View.GONE);
                tvOppositeCover.setVisibility(View.GONE);
                llExample.setVisibility(View.VISIBLE);
                ivExample.setVisibility(View.VISIBLE);
                tvReupload.setVisibility(View.GONE);
                break;
        }
    }

    private void checkPics() {
        if(Util.isEmpty(idCardFrontLocalPath) || Util.isEmpty(idCardOppositeLocalPath) ){
            Util.showToast(this, "请选择要上传的身份证照片");
            return;
        }
        //重新上传身份证照片之前将图片上传到又拍云
        uploadFile(idCardFrontLocalPath,true);
    }
        //重新上传身份证照片
    private void requestUploadIDCardPic() {
        progressBar.setVisibility(View.VISIBLE);
        UploadIDCardPicsApi api = new UploadIDCardPicsApi();
        if (!Util.isEmpty(idCardFrontRemotePath)) {
            api.setFrontPic(idCardFrontRemotePath);
        }
        if (!Util.isEmpty(idCardOppositeRemotePath)) {
            api.setBehindPic(idCardOppositeRemotePath);
        }
        api.setId(certificationId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if(isFinishing() || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Util.showToast(AddCertificationActivity.this, "上传成功");
                if(user!=null && user.isCertification()==false){
                    user.setCertification(true);
                    Session.getInstance().saveUserToFile(AddCertificationActivity.this,user);
                }
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(isFinishing() || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Util.showToast(AddCertificationActivity.this, Util.checkErrorType(error));
            }
        });
    }
        //创建一个认证信息
    private void requestCertification() {
        progressBar.setVisibility(View.VISIBLE);
        AddCertificationApi addCertificationApi = new AddCertificationApi();
        addCertificationApi.setRealName(etName.getText().toString().trim());
        addCertificationApi.setIdentityCard(String.valueOf(etIdNumber.getText().toString().trim()));
        if (!Util.isEmpty(idCardFrontRemotePath)) {
            addCertificationApi.setFrontPic(idCardFrontRemotePath);
        }
        if (!Util.isEmpty(idCardOppositeRemotePath)) {
            addCertificationApi.setBehindPic(idCardOppositeRemotePath);
        }
        addCertificationApi.setIsdefault(isDefault);
        D2CApplication.httpClient.loadingRequest(addCertificationApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if(isFinishing() || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                hasIdCardFront=false;
                Util.showToast(AddCertificationActivity.this, "添加成功");

                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(AddCertificationActivity.this, Util.checkErrorType(error));
            }
        });
    }
        //检查权限
    public void showPop(int type) {//
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !cameraPermission) {
            requestPermission(type);
        } else {
            choosePic(type);
        }
    }

    private void requestPermission(int type) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                type);
    }

    private void choosePic(int type) {
        Matisse.from(AddCertificationActivity.this)
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
                .forResult(type);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                choosePic(requestCode);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
            Intent intent = null;
            switch (requestCode) {
                case Constants.RequestCode.ID_CARD_FRONT:
                    uriList = Matisse.obtainResult(data);
                    obtainPathResult = Matisse.obtainPathResult(data);
                    intent = new Intent(this, CropActivity.class);
                    intent.setData(uriList.get(0));
                    intent.putExtra("mode", 6);
                    intent.putExtra("path", obtainPathResult.get(0));
                    startActivityForResult(intent, 111);
                    break;
                case Constants.RequestCode.ID_CARD_OPPOSITE:
                    uriList = Matisse.obtainResult(data);
                    obtainPathResult = Matisse.obtainPathResult(data);
                    intent = new Intent(this, CropActivity.class);
                    intent.setData(uriList.get(0));
                    intent.putExtra("mode", 6);
                    intent.putExtra("path", obtainPathResult.get(0));
                    startActivityForResult(intent, 112);
                    break;
                case 111:
                    if (data != null) {
                        Uri uri = data.getData();
                        String cropPath = data.getStringExtra("cropPath");
                        Glide.with(this)
                                .load(uri)
                                .error(R.mipmap.btn_renxiang)
                                .crossFade()
                                .into(ivIdCardFront);
                        tvFrontCover.setVisibility(View.GONE);
                        idCardFrontLocalPath = cropPath;
                        ivDeleteFront.setVisibility(View.VISIBLE);
                        if(certificationId>0 && !Util.isEmpty(idCardFrontLocalPath) && !Util.isEmpty(idCardOppositeLocalPath)){
                            titleRight.setTextColor(getResources().getColor(R.color.color_black85));
                            titleRight.setEnabled(true);
                        }else{
                            titleRight.setTextColor(getResources().getColor(R.color.color_black38));
                            titleRight.setEnabled(false);
                            if(certificationId==0){
                                checkSaveEnable();
                            }
                        }

                    }
                    break;
                case 112:
                    if (data != null) {
                        Uri uri = data.getData();
                        String cropPath = data.getStringExtra("cropPath");
                        Glide.with(this)
                                .load(uri)
                                .error(R.mipmap.btn_guohui)
                                .crossFade()
                                .into(ivIdCardOpposite);
                        idCardOppositeLocalPath = cropPath;
                        tvOppositeCover.setVisibility(View.GONE);
                        ivDeleteOpposite.setVisibility(View.VISIBLE);
                        if(certificationId>0 && !Util.isEmpty(idCardFrontLocalPath) && !Util.isEmpty(idCardOppositeLocalPath)){
                            titleRight.setTextColor(getResources().getColor(R.color.color_black85));
                            titleRight.setEnabled(true);
                        }else{
                            titleRight.setTextColor(getResources().getColor(R.color.color_black38));
                            titleRight.setEnabled(false);
                            if(certificationId==0){
                                checkSaveEnable();
                            }
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(String filePath, final boolean isReupload) {
        progressBar.setVisibility(View.VISIBLE);
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_CUSTOMER));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String url = jsonObject.optString("url");
                    if (!hasIdCardFront) {
                        hasIdCardFront = true;
                        idCardFrontRemotePath = url;
                        uploadFile(idCardOppositeLocalPath,isReupload);
                    } else {
                        idCardOppositeRemotePath = url;
                        //上传认证信息
                        if(isReupload){//重新上传身份证照片
                            requestUploadIDCardPic();
                        }else{
                            requestCertification();
                        }
                    }
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

}
