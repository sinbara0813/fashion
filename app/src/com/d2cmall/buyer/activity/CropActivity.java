package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.LoadCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/11/23.
 */

public class CropActivity extends BaseActivity {


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
    @Bind(R.id.CropImageView)
    com.isseiaoki.simplecropview.CropImageView mCropView;
    @Bind(R.id.image_view)
    ImageView imageView;
    @Bind(R.id.crop)
    TextView crop;
    @Bind(R.id.rotate)
    TextView rotate;
    private Uri firstUri;
    private boolean cropEnable = false;
    private String path, savePath;
    private Dialog progressDialog;
    private int mode=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_layout);
        ButterKnife.bind(this);
        nameTv.setText("编辑");
        titleRight.setText("完成");
        firstUri = getIntent().getData();
        path = getIntent().getStringExtra("path");
        mode = getIntent().getIntExtra("mode",-1);
        progressDialog = DialogUtil.createLoadingDialog(this);
        mCropView.setInitialFrameScale(0.8f);
        mCropView.setCropMode(CropImageView.CropMode.SQUARE);
        if(mode==6){
            mCropView.setCropMode(CropImageView.CropMode.FREE);
        }
        mCropView.startLoad(firstUri, new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Util.showToast(CropActivity.this, "图片加载失败");
                finish();
            }
        });
        mCropView.setCropEnabled(false);
    }

    private void save(Bitmap bigBitmap) {
        progressDialog.show();
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Util.showToast(this, "请确保要内存卡！");
            progressDialog.dismiss();
            return;
        }
        if (bigBitmap == null) {
            progressDialog.dismiss();
            Util.showToast(this, "图片保存失败！");
            finish();
            return;
        }
        String fileName = String.valueOf(System.currentTimeMillis());
        String foldStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2CHead";
        File folder = new File(foldStr);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, fileName + ".png");
        if (file.exists()) {
            progressDialog.dismiss();
            Util.showToast(this, "图片保存失败！");
            return;
        }
        if (bigBitmap.getByteCount() > 8485760) {
            bigBitmap = bigBitmap.copy(Bitmap.Config.RGB_565, true);
            float scale = 1;
            while (bigBitmap.getByteCount() > 8485760) {
                scale -= 0.2;
                bigBitmap = BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bigBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            Util.showToast(this, "保存出错！");
            progressDialog.dismiss();
            file = null;
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
            }
        }
        if (file != null) {
            progressDialog.dismiss();
            Intent intent = new Intent();
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            intent.putExtra("cropPath", file.getAbsolutePath());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void rotateImage() {
        mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.crop, R.id.rotate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                save(mCropView.getCroppedBitmap());
                break;
            case R.id.crop:
                if (cropEnable) {
                    mCropView.setCropEnabled(false);
                    cropEnable = false;
                } else {
                    mCropView.setCropEnabled(true);
                    cropEnable = true;
                }
                break;
            case R.id.rotate:
                rotateImage();
                break;
        }
    }

}
