package com.d2cmall.buyer.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.QuestionApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MyGridView;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by rookie on 2017/10/16.
 */

public class Comlaint2Fragment extends BaseFragment implements
        AdapterView.OnItemClickListener, ObjectBindAdapter.ViewBinder<JsonPic> {
    @Bind(R.id.tv_question_type)
    TextView tvQuestionType;
    @Bind(R.id.tv_rl_send_slow)
    TextView tvRlSendSlow;
    @Bind(R.id.iv_rl_send_slow_mark)
    ImageView ivRlSendSlowMark;
    @Bind(R.id.rl_send_slow)
    RelativeLayout rlSendSlow;
    @Bind(R.id.tv_product_quality)
    TextView tvProductQuality;
    @Bind(R.id.iv_product_quality_mark)
    ImageView ivProductQualityMark;
    @Bind(R.id.rl_product_quality)
    RelativeLayout rlProductQuality;
    @Bind(R.id.tv_people_attitude)
    TextView tvPeopleAttitude;
    @Bind(R.id.iv_people_attitude_mark)
    ImageView ivPeopleAttitudeMark;
    @Bind(R.id.rl_people_attitude)
    RelativeLayout rlPeopleAttitude;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_other_mark)
    ImageView ivOtherMark;
    @Bind(R.id.rl_other)
    RelativeLayout rlOther;
    @Bind(R.id.edit_description)
    EditText editDescription;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.gridView)
    MyGridView gridView;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.empty_view)
    RelativeLayout emptyView;
    @Bind(R.id.tv_image_number)
    TextView tvImageNumber;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.images_layout)
    RelativeLayout imagesLayout;
    @Bind(R.id.edit_man_name)
    ClearEditText editManName;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.iv_price_mark)
    ImageView ivPriceMark;
    @Bind(R.id.rl_price)
    RelativeLayout rlPrice;
    private String questionType = "";
    private ObjectBindAdapter<JsonPic> adapter;
    private ArrayList<JsonPic> photos;
    private ArrayList<JsonPic> jsonPics;
    private ArrayList<String> imgUpyunPaths;
    private JsonPic emptyPic;
    private int imageSize;
    private Dialog dialog;
    private boolean hasPermission;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint2, container, false);
        return view;
    }

    public void hideKeyboard(View v) {
        if (getActivity().getCurrentFocus() != null) {
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void prepareView() {
        photos = new ArrayList<>();
        jsonPics = new ArrayList<>();
        imgUpyunPaths = new ArrayList<>();
        emptyPic = new JsonPic();
        dialog = DialogUtil.createLoadingDialog(getActivity());
        user = Session.getInstance().getUser();
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/feedBack");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (user != null) {
            editManName.setText(user.getLoginCode());
        }
        selected(0);
//        if (!photos.isEmpty() && photos.size() < 3) {
//            photos.add(emptyPic);
//        }
        if (photos != null && photos.size() == 0) {
            photos.add(emptyPic);
        }
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Point point = ScreenUtil.getDeviceSize(getActivity());
        imageSize = Math.round((point.x - 56 * dm.density) / 4);
        adapter = new ObjectBindAdapter<>(getActivity(), photos, R.layout.list_item_post_image, this);
        gridView.setAdapter(adapter);
        //gridView.setOnItemClickListener(this);
        editDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNumber.setText(editDescription.getText().toString().length() + "/500");
                setButtonState();
            }
        });
        editManName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonState();
            }
        });
        if (!photos.isEmpty()) {
            //imagesLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void doBusiness() {

    }

    private void selected(int position) {
        hideKeyboard(null);
        switch (position) {
            case 0:
                questionType = "SLOW";
                ivRlSendSlowMark.setVisibility(View.VISIBLE);
                ivPriceMark.setVisibility(View.GONE);
                ivProductQualityMark.setVisibility(View.GONE);
                ivOtherMark.setVisibility(View.GONE);
                ivPeopleAttitudeMark.setVisibility(View.GONE);
                tvRlSendSlow.setTextColor(Color.parseColor("#DE000000"));
                tvPeopleAttitude.setTextColor(Color.parseColor("#8A000000"));
                tvOther.setTextColor(Color.parseColor("#8A000000"));
                tvPrice.setTextColor(Color.parseColor("#8A000000"));
                tvProductQuality.setTextColor(Color.parseColor("#8A000000"));
                tvRlSendSlow.setBackgroundResource(R.drawable.border_selected_feedback);
                tvOther.setBackgroundResource(R.drawable.border_unselect);
                tvPrice.setBackgroundResource(R.drawable.border_unselect);
                tvProductQuality.setBackgroundResource(R.drawable.border_unselect);
                tvPeopleAttitude.setBackgroundResource(R.drawable.border_unselect);
                editDescription.setHint(R.string.hint_complaint_slow);
                break;
            case 1:
                questionType = "QUALITY";
                ivRlSendSlowMark.setVisibility(View.GONE);
                ivPriceMark.setVisibility(View.GONE);
                ivProductQualityMark.setVisibility(View.VISIBLE);
                ivOtherMark.setVisibility(View.GONE);
                ivPeopleAttitudeMark.setVisibility(View.GONE);
                tvRlSendSlow.setTextColor(Color.parseColor("#8A000000"));
                tvPeopleAttitude.setTextColor(Color.parseColor("#8A000000"));
                tvPrice.setTextColor(Color.parseColor("#8A000000"));
                tvOther.setTextColor(Color.parseColor("#8A000000"));
                tvProductQuality.setTextColor(Color.parseColor("#DE000000"));
                tvRlSendSlow.setBackgroundResource(R.drawable.border_unselect);
                tvOther.setBackgroundResource(R.drawable.border_unselect);
                tvPrice.setBackgroundResource(R.drawable.border_unselect);
                tvProductQuality.setBackgroundResource(R.drawable.border_selected_feedback);
                tvPeopleAttitude.setBackgroundResource(R.drawable.border_unselect);
                editDescription.setHint(R.string.hint_complaint_bad);
                break;
            case 2:
                questionType = "SERVICEATTITUDE";
                ivRlSendSlowMark.setVisibility(View.GONE);
                ivProductQualityMark.setVisibility(View.GONE);
                ivPriceMark.setVisibility(View.GONE);
                ivOtherMark.setVisibility(View.GONE);
                ivPeopleAttitudeMark.setVisibility(View.VISIBLE);
                tvRlSendSlow.setTextColor(Color.parseColor("#8A000000"));
                tvPeopleAttitude.setTextColor(Color.parseColor("#DE000000"));
                tvPrice.setTextColor(Color.parseColor("#8A000000"));
                tvOther.setTextColor(Color.parseColor("#8A000000"));
                tvProductQuality.setTextColor(Color.parseColor("#8A000000"));
                tvRlSendSlow.setBackgroundResource(R.drawable.border_unselect);
                tvOther.setBackgroundResource(R.drawable.border_unselect);
                tvPrice.setBackgroundResource(R.drawable.border_unselect);
                tvProductQuality.setBackgroundResource(R.drawable.border_unselect);
                tvPeopleAttitude.setBackgroundResource(R.drawable.border_selected_feedback);
                editDescription.setHint(R.string.hint_complaint_attitude);
                break;
            case 3:
                questionType = "PRICEDIFFERENCE";
                ivRlSendSlowMark.setVisibility(View.GONE);
                ivProductQualityMark.setVisibility(View.GONE);
                ivOtherMark.setVisibility(View.GONE);
                ivPriceMark.setVisibility(View.VISIBLE);
                ivPeopleAttitudeMark.setVisibility(View.GONE);
                tvRlSendSlow.setTextColor(Color.parseColor("#8A000000"));
                tvPeopleAttitude.setTextColor(Color.parseColor("#8A000000"));
                tvPrice.setTextColor(Color.parseColor("#DE000000"));
                tvOther.setTextColor(Color.parseColor("#8A000000"));
                tvProductQuality.setTextColor(Color.parseColor("#8A000000"));
                tvRlSendSlow.setBackgroundResource(R.drawable.border_unselect);
                tvPrice.setBackgroundResource(R.drawable.border_selected_feedback);
                tvOther.setBackgroundResource(R.drawable.border_unselect);
                tvProductQuality.setBackgroundResource(R.drawable.border_unselect);
                tvPeopleAttitude.setBackgroundResource(R.drawable.border_unselect);
                editDescription.setHint(R.string.hint_question_price);
                break;
            case 4:
                questionType = "OTHER";
                ivRlSendSlowMark.setVisibility(View.GONE);
                ivPriceMark.setVisibility(View.GONE);
                ivProductQualityMark.setVisibility(View.GONE);
                ivOtherMark.setVisibility(View.VISIBLE);
                ivPeopleAttitudeMark.setVisibility(View.GONE);
                tvRlSendSlow.setTextColor(Color.parseColor("#8A000000"));
                tvPrice.setTextColor(Color.parseColor("#8A000000"));
                tvPeopleAttitude.setTextColor(Color.parseColor("#8A000000"));
                tvOther.setTextColor(Color.parseColor("#DE000000"));
                tvProductQuality.setTextColor(Color.parseColor("#8A000000"));
                tvRlSendSlow.setBackgroundResource(R.drawable.border_unselect);
                tvOther.setBackgroundResource(R.drawable.border_selected_feedback);
                tvProductQuality.setBackgroundResource(R.drawable.border_unselect);
                tvPeopleAttitude.setBackgroundResource(R.drawable.border_unselect);
                tvPrice.setBackgroundResource(R.drawable.border_unselect);
                editDescription.setHint(R.string.hint_question_other);
                break;
        }
    }


    @OnClick({R.id.rl_send_slow, R.id.rl_product_quality, R.id.rl_people_attitude, R.id.rl_other, R.id.button, R.id.rl_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_send_slow:
                selected(0);
                break;
            case R.id.rl_product_quality:
                selected(1);
                break;
            case R.id.rl_people_attitude:
                selected(2);
                break;
            case R.id.rl_price:
                selected(3);
                break;
            case R.id.rl_other:
                selected(4);
                break;
            case R.id.button:
                over();
                break;
        }
    }

    private int upLoadIndex;

    private void uploadFile() {
        JsonPic jsonPic = jsonPics.get(upLoadIndex);
        File file = new File(jsonPic.getMediaPath());
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(0000, Constants.TYPE_FRIEND));
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
                    publisth();
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

    private void publisth() {
        QuestionApi api = new QuestionApi();
        if (!Util.isEmpty(questionType)) {
            api.setType(questionType);
        }
        if (!Util.isEmpty(editManName.getText().toString())) {
            api.setMobile(editManName.getText().toString());
        }
        if (!Util.isEmpty(editDescription.getText().toString())) {
            api.setContent(editDescription.getText().toString());
        }
        if (imgUpyunPaths.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < imgUpyunPaths.size(); i++) {
                sb.append(imgUpyunPaths.get(i) + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            api.setPic(sb.toString());
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                dialog.dismiss();
                Util.showToast(mContext, "感谢您的反馈!");
                getActivity().finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void over() {
        if (Util.isEmpty(questionType)) {
            Util.showToast(mContext, "请完善信息");
            return;
        }
        if (questionType.equals("PRICEDIFFERENCE") && jsonPics.size() == 0) {
            Util.showToast(mContext, "请上传凭证");
            return;
        }
        if (Util.isEmpty(editManName.getText().toString())) {
            Util.showToast(mContext, "请输入手机号");
            return;
        }
        if (Util.isEmpty(editDescription.getText().toString())) {
            Util.showToast(mContext, "请输入内容");
            return;
        }
//        if (!Util.isCnMobileNo(editManName.getText().toString())) {
//            Util.showToast(getActivity(), R.string.msg_phone_error);
//            return;
//        }
        dialog.show();
        upLoadIndex = 0;
        if (jsonPics.size() > 0) {
            uploadFile();
        } else {
            publisth();
        }
    }

    public void showPop() {
        if (photos.contains(emptyPic)) {
            photos.remove(emptyPic);
        }
        PackageManager pkgManager = mContext.getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, mContext.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        boolean readPermission =
                pkgManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, mContext.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && (!cameraPermission || !readPermission)) {
            boolean isAll = (!cameraPermission && !readPermission);
            if (isAll) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.RequestCode.REQUEST_PERMISSION);
            } else {
                if (!readPermission) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            0);
                }
                if (!cameraPermission) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            1);
                }
            }
            //requestPermission();
            //handPermission();
        } else {
            hasPermission = true;
            choosePic();
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
            if ((ContextCompat.checkSelfPermission(mContext, permission)
                    != PackageManager.PERMISSION_GRANTED)) {
                mRequestList.add(permission);
            }
        }

        // 申请未持有的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !mRequestList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), mRequestList.toArray(
                    new String[mRequestList.size()]), Constants.RequestCode.REQUEST_PERMISSION);
        } else {
            // 权限都有了，就可以继续后面的操作
            choosePic();
        }
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                Constants.RequestCode.REQUEST_PERMISSION);
    }

    private int maxSelected = 3;

    private void choosePic() {
        Matisse.from(this)
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

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent(Context context) {

        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
//        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
//        if(appIntent != null){
//            context.startActivity(appIntent);
//            floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 0);
//            floatingView.createFloatingView();
//            return;
//        }

        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
        //      点击权限隐私>自启动管理>我的app
//        appIntent = context.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
//        if(appIntent != null){
//            context.startActivity(appIntent);
//            floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 1);
//            floatingView.createFloatingView();
//            return;
//        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.RequestCode.REQUEST_PERMISSION:
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    hasPermission = true;
                    choosePic();
                } else {
                    hasPermission = false;
                    getAppDetailSettingIntent(mContext);
                    Util.showToast(mContext, "请给权限~");
                }
                break;
            case 0:
            case 1:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasPermission = true;
                    choosePic();
                } else {
                    hasPermission = false;
                    getAppDetailSettingIntent(mContext);
                    Util.showToast(mContext, "请给权限~");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456) {
            if (resultCode == RESULT_OK) {
                for (String string : Matisse.obtainPathResult(data)) {
                    JsonPic jsonPic = new JsonPic();
                    jsonPic.setMediaPath(string);
                    photos.add(jsonPic);
                    jsonPics.add(jsonPic);
                }
                if (!photos.contains(emptyPic) && jsonPics.size() < 3) {
                    photos.add(emptyPic);
                }
                if (jsonPics.size() >= 3 && photos.contains(emptyPic)) {
                    photos.remove(emptyPic);
                }
                tvImageNumber.setText("(" + jsonPics.size() + "/3)");
                maxSelected = 3 - jsonPics.size();
                adapter.notifyDataSetChanged();
            } else {
                if (!photos.contains(emptyPic) && jsonPics.size() < 3) {
                    photos.add(emptyPic);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class ViewHolder {
        View addView;
        View deleteView;
        ImageView imageView;
        View item;
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
            maxSelected = 3 - jsonPics.size();
            if (!photos.contains(emptyPic)) {
                photos.add(emptyPic);
            }
            tvImageNumber.setText("(" + jsonPics.size() + "/3)");
            adapter.notifyDataSetChanged();
        }
    }

    private void setButtonState() {
        if (Util.isEmpty(questionType) || Util.isEmpty(editDescription.getText().toString()) || Util.isEmpty(editManName.getText().toString())) {
            button.setBackgroundColor(Color.parseColor("#61232427"));
            button.setTextColor(Color.parseColor("#FFFFFFFF"));
            return;
        }
        button.setBackgroundColor(Color.parseColor("#DE232427"));
        button.setTextColor(Color.parseColor("#FFFFFFFF"));
    }

    @Override
    public void setViewValue(View view, final JsonPic jsonPic, final int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.addView = view.findViewById(R.id.add_btn);
            holder.deleteView = view.findViewById(R.id.delete);
            holder.item = view.findViewById(R.id.item);
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            view.getLayoutParams().width = ScreenUtil.dip2px(56);
            view.getLayoutParams().height = ScreenUtil.dip2px(56);
            view.setTag(holder);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jsonPic != null) {
                    if (Util.isEmpty(jsonPic.getMediaPath())) {
                        showPop();
                    }
                }
            }
        });
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
}
