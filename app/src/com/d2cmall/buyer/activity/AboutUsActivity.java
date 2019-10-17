package com.d2cmall.buyer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.img_scan)
    ImageView imgScan;
    @Bind(R.id.tv_version_name)
    TextView tvVersionName;
    @Bind(R.id.back_iv)
    ImageView mBackIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scanWidth = Math.round(100 * dm.density);
        imgScan.setImageBitmap(BitmapUtils.createQRImage("https://fir.im/8x2m", scanWidth, scanWidth));
        tvVersionName.setText(String.format(getString(R.string.label_android_version), Util.getPageVersionName(this)));
    }


    @OnClick(R.id.tv_share)
    void share() {
        SharePop sharePop1 = new SharePop(this);
        sharePop1.show(getWindow().getDecorView());
    }

    @OnClick(R.id.tv_join_us)
    void joinUs() {
        Util.urlAction(this, "/page/job");
    }

    @OnClick(R.id.tv_phone)
    void clickPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        Constants.RequestCode.PERMISSION);
            } else {
                call();
            }
        } else {
            call();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.RequestCode.PERMISSION) {
            int result = grantResults[0];
            if (result == PackageManager.PERMISSION_GRANTED) {
                call();
            }
        }
    }

    private void call() {
        Intent call = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + getString(R.string.label_green_phone));
        call.setData(data);
        startActivity(call);
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

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
