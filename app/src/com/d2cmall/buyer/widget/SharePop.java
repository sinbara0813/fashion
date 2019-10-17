package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.ShareOutApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.bean.ShortBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.SharePopDownLoadClickListener;
import com.d2cmall.buyer.shareplatform.SinaHandle;
import com.d2cmall.buyer.shareplatform.TencentHandle;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static com.d2cmall.buyer.Constants.DELETE_SHOW_URL;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/8 16:05
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SharePop implements TransparentPop.InvokeListener,View.OnClickListener{

    @Bind(R.id.line)
    View line;
    @Bind(R.id.ll_download)
    TextView llDownload;
    @Bind(R.id.ll_delete)
    TextView llDelete;
    @Bind(R.id.delete_ll)
    LinearLayout deleteLl;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.share_content)
    LinearLayout shareContent;

    private TransparentPop mPop;
    private Context context;
    private View shareLayout;
    private ArrayList<String> des;
    private ArrayList<Integer> images;
    private String title = "让你欲罢不能的D2C全球好设计";
    private String description = "D2C全球好设计- 汇集全球好设计,寻找您专属的原创新品";
    private String webUrl = Constants.detailWebUrl;
    private String shareUrl;
    private String imageUrl = "http://img.d2c.cn/app/a/16/05/10/fa55b70135c181482ae5c6d39c3277b1";
    private boolean isWebView = false;
    private String func;
    private String channel = "pasteboard";
    public static final int IMAGE_SIZE = 32768;
    public static final int WEIBO_IMAGE_SIZE = 1000 * 1000;
    private boolean isPic; //是否是图片分享
    private boolean isDetail; //是否是商品详情页的大图
    private boolean isSelf;
    private byte[] imageData;
    private byte[] bigImageData;
    private float scale = 1;
    private String bigImageUrl;
    private String productUrl; //商品的地址
    private Bitmap bigBitmap;
    private long showId = -1;
    private boolean isFocus;
    private boolean isPromotionLink;
    private boolean isShareMini;
    private boolean productShare;
    private Poster poster;
    private String bgImageUrl;
    private boolean isRun;
    private boolean isBuyer;
    private SharePopDownLoadClickListener sharePopDownLoadClickListener;
    private String miniPath, miniWebUrl;
    private byte[] miniPicData;
    private boolean appIdIsBuyer;//标示分享到小程序的appId是不是买手小程序的appId

    public SharePop(Context context) {
        this(context, true, false);
    }

    public SharePop(Context context, boolean isFocus) {
        this(context, isFocus, false);
    }

    public SharePop(Context context, boolean isFocus, boolean isSelf) {
        this(context, isFocus, isSelf, false);
    }

    public SharePop(Context context, boolean isFocus, boolean isSelf, boolean isBuyer) {
        this.context = context;
        this.isFocus = isFocus;
        this.isSelf = isSelf;
        this.isBuyer = isBuyer;
        init();
    }

    public void setMiniProjectPath(String path) {
        this.miniPath = path;
    }

    public void setMiniWebUrl(String webUrl) {
        this.miniWebUrl = Constants.SHARE_URL + webUrl;
    }

    public void setMiniProjectDec(String dec) {
        this.description = dec;
    }

    public void setMiniPicData(byte[] data) {
        this.miniPicData = data;
    }

    public void setSharePopDownLoadClickListener(SharePopDownLoadClickListener sharePopDownLoadClickListener) {
        this.sharePopDownLoadClickListener = sharePopDownLoadClickListener;
    }

    public void setShowId(long id) {
        this.showId = id;
    }

    private void init() {
        shareLayout = LayoutInflater.from(context).inflate(R.layout.layout_share_pop, new LinearLayout(context), false);
        ButterKnife.bind(this, shareLayout);
        mPop = new TransparentPop(context, this);
        if (!isFocus) {
            mPop.setFocusable(false);
        }
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        initData(WxHandle.getInstance(context).isAppInstalled());
        int count = 0;
        if (isBuyer) {
            count++;
            llDownload.setVisibility(View.VISIBLE);
        } else {
            llDownload.setVisibility(View.GONE);
        }
        if (isSelf) {
            if (!isBuyer) {
                llDelete.setPadding(0, 0, 0, 0);
            }
            llDelete.setVisibility(View.VISIBLE);
            count++;
        } else {
            llDelete.setVisibility(View.GONE);
        }
        if (count > 0) {
            line.setVisibility(View.VISIBLE);
            deleteLl.setVisibility(View.VISIBLE);
        } else {
            line.setVisibility(View.GONE);
            deleteLl.setVisibility(View.GONE);
        }
        llDownload.setOnClickListener(this);
        llDelete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    private void sharePoster2WxFriend() {
        if (!poster.baseMap || !poster.productMap) {
            Toast.makeText(context, "海报图下载中,请稍后", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRun) {
            Toast.makeText(context, "海报图制作中,请稍后", Toast.LENGTH_SHORT).show();
            return;
        }
        isRun = true;
        poster.posterView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        poster.posterView.layout(0, 0, poster.posterView.getMeasuredWidth(), poster.posterView.getMeasuredHeight());
        poster.posterView.buildDrawingCache();
        Bitmap bmp = poster.posterView.getDrawingCache();
        if (bmp == null) {
            bmp = loadBitmapFromView(poster.posterView);
        }
        if (bmp != null) {
            String fileName = Base64.encode(bigImageUrl.getBytes());
            String foldStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2C";
            File folder = new File(foldStr);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            int index = bigImageUrl.lastIndexOf(".");
            int type = 1;
            if (index > 0 && index + 2 < bigImageUrl.length()) {
                String s = bigImageUrl.substring(index + 1, index + 2);
                if (s.toLowerCase().equals("j")) {
                    type = 2;
                }
            }
            File file = null;
            if (type == 1) {
                file = new File(folder, fileName + ".png");
            } else {
                file = new File(folder, fileName + ".jpg");
            }
            if (file.exists()) {
                openWx(file);
                return;
            }
            try {
                FileOutputStream os = new FileOutputStream(file);
                if (type == 1) {
                    bmp.compress(Bitmap.CompressFormat.PNG, 75, os);
                } else {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 75, os);
                }
                os.flush();
                os.close();
            } catch (Exception e) {
            } finally {
            }
            isRun = false;
            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
            openWx(file);
            dismiss();
        }
    }
    private void openWx(File f) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");

        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUris.add(Uri.fromFile(f));
        } else {
            Uri uri = null;
            try {
                uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageUris.add(uri);
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        intent.putExtra("Kdescription", description);
        context.startActivity(intent);
    }

    private void share2Mini() {
        WxHandle.getInstance(context).shareMiniProject(context, miniPath, description, miniPicData, miniWebUrl,appIdIsBuyer);
        dismiss();
    }

    public void initData(boolean isInstallWx) {
        des = new ArrayList<>();
        images = new ArrayList<>();
        if (isInstallWx) {
            des.add(context.getString(R.string.label_share_wxzone));
            des.add(context.getString(R.string.label_share_wxfriend));
            images.add(R.mipmap.icon_share_pyq);
            images.add(R.mipmap.icon_share_wx);
        }
        des.add(context.getString(R.string.label_share_qq));
        images.add(R.mipmap.icon_share_qq);
        des.add(context.getString(R.string.label_share_qqzone));
        images.add(R.mipmap.icon_share_qqkj);
        des.add(context.getString(R.string.label_share_weibo));
        images.add(R.mipmap.icon_share_wb);
        if (!isPic) {
            des.add(context.getString(R.string.label_share_link));
            images.add(R.mipmap.icon_share_fzlj);
        }
        addContent();
    }

    /**
     * 添加分享渠道
     */
    private void addContent(){
        shareContent.removeAllViews();
        if (des.size()==1){
            View view=getItemView(shareContent,des.get(0),images.get(0));
            shareContent.addView(view);
        }else if (des.size()==2){
            LinearLayout linearLayout=new LinearLayout(context);
            linearLayout.setPadding(ScreenUtil.dip2px(60),0,ScreenUtil.dip2px(60),0);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams childLl=new LinearLayout.LayoutParams(-2,-2);
            childLl.setMargins(0,0,ScreenUtil.dip2px(100),0);
            for (int i=0;i<2;i++){
                View view=getItemView(linearLayout,des.get(i),images.get(i));
                if (i==0){
                    linearLayout.addView(view,childLl);
                }else {
                    linearLayout.addView(view);
                }
            }
            shareContent.addView(linearLayout);
        } else {
            int size=des.size();
            int rowSize=size%3==0?size/3:size/3+1;
            for (int i=0;i<rowSize;i++){
                LinearLayout linearLayout=new LinearLayout(context);
                linearLayout.setPadding(ScreenUtil.dip2px(30),0,ScreenUtil.dip2px(30),0);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams childLl;
                for (int j=0;j<3;j++){
                    if (3*i+j>=size){
                        break;
                    }
                    View view=getItemView(linearLayout,des.get(3*i+j),images.get(3*i+j));
                    childLl=new LinearLayout.LayoutParams(-2,-2);
                    if (j==2){
                        childLl.setMargins(0,0,0,0);
                    }else {
                        childLl.setMargins(0,0,ScreenUtil.dip2px(50),0);
                    }
                    linearLayout.addView(view,childLl);
                }
                shareContent.addView(linearLayout);
                if (i!=rowSize-1){
                    View line=new View(context);
                    line.setBackgroundColor(context.getResources().getColor(R.color.line));
                    LinearLayout.LayoutParams lineLl=new LinearLayout.LayoutParams(-1, ScreenUtil.dip2px(0.5f));
                    shareContent.addView(line,lineLl);
                }
            }
        }
    }

    private View getItemView(LinearLayout parent,String text,int sourceId){
        View view=LayoutInflater.from(context).inflate(R.layout.list_item_share_dialog,parent,false);
        ImageView imageView=view.findViewById(R.id.image);
        TextView textView=view.findViewById(R.id.text);
        imageView.setImageResource(sourceId);
        textView.setText(text);
        view.setId(sourceId);
        view.setOnClickListener(this);
        return view;
    }

    public void addPoster() {
        des.add(context.getString(R.string.label_share_poster));
        images.add(R.mipmap.icon_share_hb);
        addContent();
    }

    public void setBgImageUrl(String bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(shareLayout);
    }

    public void share2Wx() {
        channel = "moments";
        WXEntryActivity.webUrl = webUrl;
        WXEntryActivity.channel = channel;
        dismiss();
        if (isPic) {
            File file = getWaterMarkFile();
            if (file == null) {
                return;
            }
            WXImageObject wxImageObject = new WXImageObject();
            wxImageObject.imagePath = file.getAbsolutePath();
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = wxImageObject;
            WxHandle.getInstance(context).sendPicShare(context, msg, SendMessageToWX.Req.WXSceneSession);
        } else {
            WxHandle.getInstance(context).setTitle(title);
            WxHandle.getInstance(context).setDes(description);
            WxHandle.getInstance(context).setWebUrl(Util.isEmpty(shareUrl) ? webUrl : shareUrl);
            if (imageData == null || imageData.length == 0) {
                imageData = BitmapUtils.getBitmapData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_app));
            }
            WxHandle.getInstance(context).sendShare(context, imageData, SendMessageToWX.Req.WXSceneSession);
        }
    }

    public void share2WxFriend() {
        channel = "Weixin";
        WXEntryActivity.webUrl = webUrl;
        WXEntryActivity.channel = channel;
        dismiss();
        if (isPic) {
            File file = getWaterMarkFile();
            if (file == null) {
                return;
            }
            WXImageObject wxImageObject = new WXImageObject();
            wxImageObject.imagePath = file.getAbsolutePath();
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = wxImageObject;
            WxHandle.getInstance(context).sendPicShare(context, msg, SendMessageToWX.Req.WXSceneTimeline);
        } else {
            WxHandle.getInstance(context).setTitle(title);
            WxHandle.getInstance(context).setDes(description);
            WxHandle.getInstance(context).setWebUrl(Util.isEmpty(shareUrl) ? webUrl : shareUrl);
            if (imageData == null || imageData.length == 0) {
                imageData = BitmapUtils.getBitmapData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_app));
            }
            WxHandle.getInstance(context).sendShare(context, imageData, SendMessageToWX.Req.WXSceneTimeline);
        }
    }

    public void share2QQ() {
        TencentHandle tencentHandle = new TencentHandle();
        tencentHandle.initQQ(context);
        channel = "QQ";
        dismiss();
        Bundle params = new Bundle();
        if (isPic) {
            File file = getWaterMarkFile();
            if (file == null) {
                return;
            }
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.getAbsolutePath());
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "D2Cmall");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                    QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            if (Util.isEmpty(description)) {
                description = "";
            }
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Util.isEmpty(shareUrl) ? webUrl : shareUrl);
            if (Util.isEmpty(bigImageUrl)) {
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
            } else {
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, bigImageUrl);
            }
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "D2Cmall");
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
                    QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        tencentHandle.shareToQQ((Activity) context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                shareOut();
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void share2Zone() {
        TencentHandle tencentHandle = new TencentHandle();
        tencentHandle.initQQ(context);
        channel = "qzone";
        dismiss();
        Bundle bundle = new Bundle();
        if (isPic) {
            File file = getWaterMarkFile();
            if (file == null) {
                return;
            }
            bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
            bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,
                    description);
            bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                    productUrl);
            //bundle.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,file.getAbsolutePath());
            bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            ArrayList<String> imageUrls = new ArrayList<>();
            imageUrls.add(file.getAbsolutePath());
            bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                    imageUrls);
            bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "D2Cmall");
        } else {
            bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                    QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
            bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,
                    description);
            bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                    Util.isEmpty(shareUrl) ? webUrl : shareUrl);
            ArrayList<String> imageUrls = new ArrayList<>();
            if (Util.isEmpty(bigImageUrl)) {
                imageUrls.add(imageUrl);
            } else {
                imageUrls.add(bigImageUrl);
            }
            bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                    imageUrls);
            bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "D2Cmall");
        }
        tencentHandle.shareToQzone((Activity) context, bundle, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                shareOut();
            }

            @Override
            public void onError(UiError uiError) {
                Toast.makeText(context, uiError.errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public File getWaterMarkFile() {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Util.showToast(context, "请确保要内存卡！");
            return null;
        }
        String fileName = Base64.encode(bigImageUrl.getBytes());
        File root = context.getExternalCacheDir();
        File folder = new File(root, "share");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, fileName + ".png");
        if (file.exists()) {
            return file;
        }
        if (bigBitmap.getByteCount() > 8485760) {
            bigBitmap = bigBitmap.copy(Bitmap.Config.RGB_565, true);
            float scale = 1;
            while (bigBitmap.getByteCount() > 8485760) {
                scale -= 0.2;
                bigBitmap = BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        } else {
            bigBitmap = bigBitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        Bitmap tagBitmap = null;
        Bitmap scanBitmap = null;
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            Toast.makeText(context, "分享失败,图片加载中,请稍候再试", Toast.LENGTH_SHORT).show();
            bigBitmap.recycle();
            return null;
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        UserBean.DataEntity.MemberEntity memberEntity = Session.getInstance().getUserFromFile(context);
        if ((memberEntity != null && memberEntity.getDistributorId() > 0) || (memberEntity != null && memberEntity.getType() > 1)) {
            //分销商
        } else {
            if (isDetail) { //商品详情大图
                int scanWidth = canvas.getWidth() / 5;
                scanBitmap = BitmapUtils.createWhiteQRImage(productUrl, scanWidth, scanWidth, 0);
                canvas.drawBitmap(scanBitmap, canvas.getWidth() / 20, canvas.getHeight() - scanBitmap.getHeight() - canvas.getWidth() / 100, p);
                tagBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sharebottom).copy(Bitmap.Config.ARGB_8888, true);
                tagBitmap = BitmapUtils.getScaleBitmap(tagBitmap, (float) canvas.getWidth() / tagBitmap.getWidth(), (float) canvas.getWidth() / tagBitmap.getWidth());
                canvas.drawBitmap(tagBitmap, canvas.getWidth() - tagBitmap.getWidth(), canvas.getHeight() - tagBitmap.getHeight(), p);
            } else {
                tagBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sharebottom2).copy(Bitmap.Config.ARGB_8888, true);
                tagBitmap = BitmapUtils.getScaleBitmap(tagBitmap, (float) canvas.getWidth() / tagBitmap.getWidth(), (float) canvas.getWidth() / tagBitmap.getWidth());
                canvas.drawBitmap(tagBitmap, canvas.getWidth() - tagBitmap.getWidth(), canvas.getHeight() - tagBitmap.getHeight(), p);
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
            file = null;
            Util.showToast(context, "分享失败！");
            return null;
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
            }
            if (tagBitmap != null) {
                tagBitmap.recycle();
            }
            if (scanBitmap != null) {
                scanBitmap.recycle();
            }
            canvas = null;
        }
        return file;
    }

    /**
     * 生成海报
     *
     * @param bigImageUrl
     */
    private void screenshot(String bigImageUrl) {
        if (!poster.baseMap || !poster.productMap) {
            Toast.makeText(context, "海报图下载中,请稍后", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRun) {
            Toast.makeText(context, "海报图制作中,请稍后", Toast.LENGTH_SHORT).show();
            return;
        }
        isRun = true;
        poster.posterView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        poster.posterView.layout(0, 0, poster.posterView.getMeasuredWidth(), poster.posterView.getMeasuredHeight());
        poster.posterView.buildDrawingCache();
        Bitmap bmp = poster.posterView.getDrawingCache();
        if (bmp != null) {
            String fileName = Base64.encode(bigImageUrl.getBytes());
            String foldStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2C";
            File folder = new File(foldStr);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            int index = bigImageUrl.lastIndexOf(".");
            int type = 1;
            if (index > 0 && index + 2 < bigImageUrl.length()) {
                String s = bigImageUrl.substring(index + 1, index + 2);
                if (s.toLowerCase().equals("j")) {
                    type = 2;
                }
            }
            File file = null;
            if (type == 1) {
                file = new File(folder, fileName + ".png");
            } else {
                file = new File(folder, fileName + ".jpg");
            }
            if (file.exists()) {
                Toast.makeText(context, "海报图已存在,请求相册查看", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                FileOutputStream os = new FileOutputStream(file);
                if (type == 1) {
                    bmp.compress(Bitmap.CompressFormat.PNG, 75, os);
                } else {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 75, os);
                }
                os.flush();
                os.close();
            } catch (Exception e) {
            } finally {

            }
            Util.showToast(context, "制作成功,请到相册查看!");
            isRun = false;
            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);
                dismiss();
            }
        }
    }

    public void share2Sina() {
        SinaHandle sinaHandle = new SinaHandle();
        channel = "Weibo";
        dismiss();
        if (isPic) {
            File file = getWaterMarkFile();
            if (file == null) {
                return;
            }
            ImageObject imageObject = new ImageObject();
            imageObject.imagePath = file.getAbsolutePath();
            sinaHandle.setImageObject(imageObject);
            TextObject textObject = new TextObject();
            textObject.text = title + "#D2C全球好设计#";
            //textObject.text=textObject.text+"http://shop.sc.weibo.com/h5/share/index?url="+webUrl;
            textObject.text = textObject.text + (Util.isEmpty(shareUrl) ? webUrl : shareUrl);
            sinaHandle.setTextObject(textObject);
        } else {
            ImageObject imageObject = new ImageObject();
            imageObject.imageData = bigImageData == null || bigImageData.length == 0 ? BitmapUtils.getBitmapData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_app)) : bigImageData;
            sinaHandle.setImageObject(imageObject);
            TextObject textObject = new TextObject();
            textObject.text = title + "#D2C全球好设计#";
            //textObject.text=textObject.text+"http://shop.sc.weibo.com/h5/share/index?url="+webUrl;
            textObject.text = textObject.text + (Util.isEmpty(shareUrl) ? webUrl : shareUrl);
            sinaHandle.setTextObject(textObject);
        }
        sinaHandle.share((Activity) context);
    }

    private void share2Link() {
        if (isWebView) {
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.SHARE_CANCEL));
        }
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("url", Util.isEmpty(shareUrl) ? webUrl : shareUrl));
        Util.showToast(context, R.string.msg_copy_ok);
        dismiss();
    }

    public void shareOut() {
        if (Session.getInstance().getUserFromFile(context) != null) {
            if (Util.isEmpty(webUrl))
                return;
            long memberId = 0;
            if (Session.getInstance().getUserFromFile(context) != null) {
                memberId = Session.getInstance().getUserFromFile(context).getId();
            }
            if (webUrl.equals(Constants.detailWebUrl)) {
                String param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + Constants.detailWebUrl;
                sendShareOut(param);
            } else {
                int index1 = webUrl.indexOf(".com");
                String url = "";
                if (index1 + 4 < webUrl.length()) {
                    url = webUrl.substring(index1 + 4, webUrl.length());
                }
                String param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + url;
                sendShareOut(param);
            }
        }
        if (isWebView) {
            GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.SHARE_OK);
            if (!Util.isEmpty(func))
                globalTypeBean.putValue("func", func);
            EventBus.getDefault().post(globalTypeBean);
        }
    }

    public void shareIn() {
        if (Util.isEmpty(webUrl))
            return;
        long memberId = 0;
        if (Session.getInstance().getUserFromFile(context) != null) {
            memberId = Session.getInstance().getUserFromFile(context).getId();
        }
        if (webUrl.equals(Constants.detailWebUrl)) {
            String param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + Constants.detailWebUrl;
            sendShareIn(param);
        } else {
            int index1 = webUrl.indexOf(".com");
            String url = "";
            if (index1 + 4 < webUrl.length()) {
                url = webUrl.substring(index1 + 4, webUrl.length());
            }
            String param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + url;
            sendShareIn(param);
        }
    }

    private void sendShareOut(String param) {
        ShareOutApi api = new ShareOutApi();
        api.setInterPath(Constants.SHARE_OUT_URL);
        api.setParam(Base64.encode(param.getBytes()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_WEB));
            }
        });
    }

    private void sendShareIn(String param) {
        ShareOutApi api = new ShareOutApi();
        api.setInterPath(Constants.SHARE_IN_URL);
        api.setParam(Base64.encode(param.getBytes()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
            }
        });
    }

    public void show(View view) {
        mPop.show(view, true);
    }

    private void dismiss() {
        mPop.dismiss(true);
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    @Override
    public void releaseView(LinearLayout v) {

        imageData = null;
        bigImageData = null;
        miniPicData = null;
        if (bigBitmap != null) {
            bigBitmap.recycle();
        }

        if (poster != null && poster.posterView != null) {
            poster.posterView = null;
        }
        sharePopDownLoadClickListener=null;
        ((ViewGroup) shareLayout).removeAllViews();
        shareLayout = null;
    }

    public void setWebView(boolean webView) {
        isWebView = webView;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public void setProductUrl(String url) {
        this.productUrl = url;
    }

    public void setPic(boolean pic) {
        isPic = pic;
    }

    public void setDetail(boolean detail) {
        isDetail = detail;
    }

    public void setBigBitmap(Bitmap bigBitmap) {
        this.bigBitmap = bigBitmap.copy(Bitmap.Config.ARGB_8888,true);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
        if (!Util.isEmpty(webUrl)) {
            long memberId = 0;
            long partnerId = 0;
            if (Session.getInstance().getUserFromFile(context) != null) {
                memberId = Session.getInstance().getUserFromFile(context).getId();
                partnerId = Session.getInstance().getUserFromFile(context).getPartnerId();
            }
            String param = null;
            try {
                if (partnerId > 0) {
                    param = "memberId=" + memberId + "&parent_id=" + partnerId + "&device=android&channel=" + channel + "&url=" + URLEncoder.encode(webUrl, "UTF-8");
                } else {
                    param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + URLEncoder.encode(webUrl, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!Util.isEmpty(param)) {
                shareUrl = Constants.SHARE_URL + "/share/in?param=" + Base64.encode(param.getBytes());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String jsonStr = HttpUtils.request("http://api.t.sina.com.cn/short_url/shorten.json", "source=2815391962&url_long="+shareUrl);
                        if (jsonStr != null && jsonStr.contains("url_short")) {
                            Gson gson = new Gson();
                            ShortBean[] shortBean = gson.fromJson(jsonStr, ShortBean[].class);
                            if (shortBean!=null&&shortBean.length>0){
                                shareUrl = shortBean[0].getUrl_short();
                            }
                        }
                    }
                }).start();
            }
        }
    }

    public void setImage(String url, final boolean isBig) {
        if (isBig) {
            bigImageUrl = url;
            bigImageUrl = Util.getD2cPicUrl(bigImageUrl);
        }
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (isBig) {
                    bigImageData = BitmapUtils.getBitmapData(resource);
                    if (bigImageData==null)return;
                    while (bigImageData.length > 1597152) {
                        scale -= 0.2;
                        Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                        bigImageData = BitmapUtils.getBitmapData(scaleBitmap);
                        scaleBitmap.recycle();
                    }
                } else {
                    imageData = BitmapUtils.getBitmapData(resource);
                    if (imageData==null)return;
                    while (imageData.length > IMAGE_SIZE) {
                        scale -= 0.1;
                        Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                        imageData = BitmapUtils.getBitmapData(scaleBitmap);
                        scaleBitmap.recycle();
                    }
                }
            }
        });
    }

    public void setMiniImage(String url, boolean isLocal, int resId) {
        if (isLocal) { //本地图片
            if (miniPicData == null) {
                Bitmap mini = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), resId), 250, 250, true);
                miniPicData = BitmapUtils.getBitmapData(mini);
                if (miniPicData == null) {
                    mini.recycle();
                }
            }
        } else {
            Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (miniPicData == null) {
                        miniPicData = BitmapUtils.getBitmapData(resource);
                        resource.recycle();
                    }
                }
            });
        }
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public boolean isPromotionLink() {
        return isPromotionLink;
    }

    public void setPromotionLink(boolean promotionLink, boolean shareMini) {
        isPromotionLink = promotionLink;
        isShareMini = shareMini;
        if (promotionLink) {
            des = new ArrayList<>();
            des.add(context.getString(R.string.label_share_wxfriend));
            des.add(context.getString(R.string.label_share_wxzone));

            images = new ArrayList<>();
            images.add(R.mipmap.icon_share_wx);
            images.add(R.mipmap.icon_share_pyq);

            if (Session.getInstance().getPosterBean() != null && !shareMini) {
                des.add("生成海报");
                images.add(R.mipmap.icon_share_hb);
            }
            addContent();
        }
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.mipmap.icon_share_qq:
                shareIn();
                share2QQ();
                break;
            case R.mipmap.icon_share_pyq:
                if (isShareMini) {//分享海报图到朋友圈
                    sharePoster2WxFriend();
                } else {
                    shareIn();
                    share2WxFriend();
                }
                break;
            case R.mipmap.icon_share_wx:
                if (isShareMini || productShare) {
                    share2Mini();
                } else {
                    shareIn();
                    share2Wx();
                }
                break;
            case R.mipmap.icon_share_wb:
                shareIn();
                share2Sina();
                break;
            case R.mipmap.icon_share_qqkj:
                shareIn();
                share2Zone();
                break;
            case R.mipmap.icon_share_fzlj:
                share2Link();
                break;
            case R.mipmap.icon_share_hb:
                screenshot(bgImageUrl);
                break;
            case R.id.cancel:
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.SHARE_CANCEL));
                dismiss();
                break;
            case R.id.ll_download:
                if (sharePopDownLoadClickListener != null) {
                    sharePopDownLoadClickListener.downLoad();
                }
                break;
            case R.id.ll_delete:
                delete();
                break;
        }
    }

    private void delete(){
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(String.format(DELETE_SHOW_URL, showId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                dismiss();
                Util.showToast(context, "删除成功");
                GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.SHOW_DELETE);
                EventBus.getDefault().post(globalTypeBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    public void setAppIdIsBuyer(boolean appIdIsBuyer) {
        this.appIdIsBuyer = appIdIsBuyer;
    }

    public void setShareMini(boolean shareMini) {
        isShareMini = shareMini;
    }

    public void setProductShare(boolean productShare) {
        this.productShare = productShare;
    }
}
