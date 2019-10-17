package com.d2cmall.buyer.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.SwitchTypePop;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/9/25.
 * Description : PartnerCertificateActivity
 * 买手证书页面
 */

public class PartnerCertificateActivity extends BaseActivity {
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
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.iv_mini_code)
    ImageView ivMiniCode;
    private SwitchTypePop switchTypePop;
    private PartnerMemberBean.DataBean.PartnerBean partner;
    private Handler mHandle;
    private boolean downing;
    private SharePop sharePop1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_certificate);
        ButterKnife.bind(this);
        nameTv.setText("买手证书");
        loadPartnerInfo();
    }
    private void loadPartnerInfo() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MEMBER_MINE);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean response) {
                partner = response.getData().getPartner();
                getBgIv();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PartnerCertificateActivity.this,Util.checkErrorType(error));
            }
        });
        }
    private void getBgIv() {
        if(partner==null){
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.API_URL+Constants.BUYER_CERTIFICATE);
        stringBuilder.append("?appId=wx58eb0484ce91f38f");
        stringBuilder.append("&partnerId=").append(partner.getId());
        stringBuilder.append("&content=");
        stringBuilder.append(Util.toURLEncode("parent_id="+partner.getId()));
        stringBuilder.append("&page=");
        stringBuilder.append( Util.toURLEncode("pages/index/index"));
        stringBuilder.append(Constants.MY_SUFFIX);
        Glide.with(this).load(Util.getD2cPicUrl(stringBuilder.toString())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(PartnerCertificateActivity.this.getResources(), resource);
                ivBg.setBackground(bitmapDrawable);
                ivBg.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        switchTypePop = new SwitchTypePop(PartnerCertificateActivity.this, true, false,false);
                        //分享暂时取消
                        switchTypePop.setShareGone();
                        switchTypePop.setClickBack(new SwitchTypePop.ClickBack() {
                            @Override
                            public void clickBack(int type) {
                                switch (type) {
                                    case 1: //保存图片
                                        // downLoadPic(info.bigImageUrl);
                                        downLoadPic(stringBuilder.toString());
                                        break;
                                    case 2: //分享图片
                                        if (ivBg.getDrawable() != null) {
                                    /*if (((BitmapDrawable) imageView.getDrawable()).getBitmap().getByteCount()>8485760){
                                        Util.showToast(context,"图片太大不能分享!");
                                        return;
                                    }*/
                                            sharePop1=new SharePop(PartnerCertificateActivity.this);
                                            sharePop1.setPic(true);
                                            sharePop1.setBigImageUrl(stringBuilder.toString());
                                            sharePop1.setBigBitmap(resource);
                                            sharePop1.show(ivBg);
                                        }
                                        break;
                                    case 3: //添加标签
                                        break;
                                }
                            }
                        });
                        switchTypePop.show(v);
                        return true;
                    }
                });
            }
        });


    }


    private void createHandle() {
        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    case 1:
                        Util.showToast(PartnerCertificateActivity.this, "没有内存卡");
                        break;
                    case 2:
                        Util.showToast(PartnerCertificateActivity.this, "保存失败");
                        downing = false;
                        break;
                    case 3:
                        Util.showToast(PartnerCertificateActivity.this, "已经保存");
                        downing = false;
                        break;
                    case 4:
                        Util.showToast(PartnerCertificateActivity.this, "保存成功");
                        downing = false;
                        break;
                    case 5:
                        Util.showToast(PartnerCertificateActivity.this, "读取失败");
                        break;
                    case 6:
                        Util.showToast(PartnerCertificateActivity.this, "正在下载中");
                        break;
                }
            }
        };
    }

    private void downLoadPic(final String url) {
        createHandle();
        if (downing) {
            Util.showToast(PartnerCertificateActivity.this, "正在下载中");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                downFile(url);
            }
        }).start();
    }
    private void downFile(String bigImageUrl) {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            mHandle.sendEmptyMessage(1);
            return;
        }
        int index = bigImageUrl.lastIndexOf("/");
        if (index > 0) {
            String name = bigImageUrl.substring(index + 1);
            if (name.indexOf(".") <= 0) {
                name = name + ".png";
            }
            URL url = null;
            try {
                url = new URL(bigImageUrl);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConn.getInputStream();
                downing = true;
                int state = FileUtil.save2File(PartnerCertificateActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2C", name, inputStream);
                if (state == -1) {
                    mHandle.sendEmptyMessage(2);
                } else if (state == 1) {
                    mHandle.sendEmptyMessage(3);
                } else {
                    mHandle.sendEmptyMessage(4);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mHandle.sendEmptyMessage(5);
        }
    }

    @OnClick({R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
