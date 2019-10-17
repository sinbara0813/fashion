package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.d2cmall.buyer.api.UpdateAccountApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.BirthdayPop;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

//个人信息页面
public class AccountInfoActivity extends BaseActivity implements BirthdayPop.CallBack {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.img_avatar)
    RoundedImageView imgAvatar;
    @Bind(R.id.avatar_layout)
    LinearLayout avatarLayout;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.phone_layout)
    LinearLayout phoneLayout;
    @Bind(R.id.tv_nick)
    TextView tvNick;
    @Bind(R.id.nick_layout)
    LinearLayout nickLayout;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.sex_layout)
    LinearLayout sexLayout;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.birthday_layout)
    LinearLayout birthdayLayout;
    BirthdayPop birthdayPopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info1);
        ButterKnife.bind(this);
        nameTv.setText("个人信息");
        setUserInfo();
    }

    private void setUserInfo() {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(user.getHead()),
                imgAvatar, R.mipmap.ic_default_avatar);
        tvNick.setText(user.getNickname());
        tvSex.setText(user.getSex());
        if (!Util.isEmpty(user.getLoginCode())) {
            phoneLayout.setVisibility(View.VISIBLE);
            tvAccount.setText(user.getLoginCode());
        } else {
            phoneLayout.setVisibility(View.GONE);
        }
        if (!Util.isEmpty(user.getBirthDay())) {
            Date birthDate = Util.getDate(user.getBirthDay());
            DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SHORT);
            tvBirthday.setText(dateFormat.format(birthDate));
            String[] dates = dateFormat.format(birthDate).split("/");
            if (dates.length == 3) {
                birthdayPopView = new BirthdayPop(this, dates[0], dates[1], dates[2]);
            } else {
                birthdayPopView = new BirthdayPop(this);
            }
        } else {
            birthdayPopView = new BirthdayPop(this);
        }
        birthdayPopView.setCallBack(this);
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
                    String newHeadPic = jsonObject.optString("url");
                    requestUpdateAccountTask(newHeadPic, null, null);
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

    public void showPop() {
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        boolean readPermission =
                pkgManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && (!cameraPermission || !readPermission)) {
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
        Matisse.from(AccountInfoActivity.this)
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

    @OnClick({R.id.back_iv, R.id.avatar_layout, R.id.phone_layout, R.id.nick_layout, R.id.sex_layout, R.id.birthday_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.avatar_layout:
                showPop();
                break;
            case R.id.phone_layout:
                break;
            case R.id.nick_layout:
                intent = new Intent(this, EditNickNameActivity.class);
                intent.putExtra("content", tvNick.getText().toString());
                intent.putExtra("type", 0);
                startActivityForResult(intent, Constants.RequestCode.EDIT_NICK);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.sex_layout:
                showPopupMenu(tvSex);
                break;
            case R.id.birthday_layout:
                birthdayPopView.show(getWindow().getDecorView(), tvBirthday);
                break;
        }
    }

    private void requestUpdateAccountTask(final String newHeadPic, final String newSex, final String newBirthday) {
        UpdateAccountApi api = new UpdateAccountApi();
        api.setHeadPic(newHeadPic);
        api.setSex(newSex);
        api.setBirthDay(newBirthday);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(AccountInfoActivity.this);
                if (!Util.isEmpty(newHeadPic)) {
                    user.setHead(newHeadPic);
                }
                if (!Util.isEmpty(newSex)) {
                    user.setSex(newSex);
                    tvSex.setText(newSex);
                }
                if (!Util.isEmpty(newBirthday)) {
                    user.setBirthDay(newBirthday);
                }
                Session.getInstance().saveUserToFile(AccountInfoActivity.this, user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(AccountInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void showPopupMenu(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] gender = {"男", "女"};
        //    设置一个下拉的列表选择项
        builder.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestUpdateAccountTask(null, gender[which], null);
            }
        });
        AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        params.x = width - ScreenUtil.dip2px(16);//设置x坐标
        params.y = ScreenUtil.dip2px(-84);//设置y坐标
        window.setAttributes(params);
        alertDialog.setCanceledOnTouchOutside(true);//设

        alertDialog.show();
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = ScreenUtil.dip2px(160);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    List<Uri> mSelected = new ArrayList<>();
    List<String> pathSelected = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
            switch (requestCode) {
                case Constants.RequestCode.EDIT_NICK:
                    tvNick.setText(user.getNickname());
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
                                .into(imgAvatar);
                        uploadFile(cropPath);
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String addZero(String str) {
        String result = str;
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    @Override
    public void callback(View trigger, int[] index, String[] value) {
        String year = value[0];
        String month = value[1];
        String day = value[2];
        Calendar calendar1 = Calendar.getInstance();
        if (!Util.isEmpty(year) && !Util.isEmpty(month) && !Util.isEmpty(day)) {
            calendar1.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
            Calendar calendar = Calendar.getInstance();
            if (calendar1.compareTo(calendar) > 0) {
                Util.showToast(this, "不能设置当日之后的时间");
                return;
            }
        }
        String birthStr = String.format(getString(R.string.label_year_month_day), year, addZero(month), addZero(day));
        tvBirthday.setText(birthStr);
        requestUpdateAccountTask(null, null, birthStr);
    }
}
