package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.SwitchTypePop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/8 16:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SimpleImageActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.info)
    TextView info;
    @Bind(R.id.rq)
    ImageView rq;
    @Bind(R.id.bottom_view)
    View bottomView;
    @Bind(R.id.promotion_type_ll)
    LinearLayout promotionTypeLl;

    private String url;
    private ArrayList<String> picUrl;
    private String infoStr;
    private String promotions;
    private String priceStr;
    private SwitchTypePop switchTypePop;
    private SharePop sharePop1;
    private HashMap<Integer,Integer> map;
    private boolean isFirst;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        map=new HashMap<>();
        picUrl = getIntent().getStringArrayListExtra("picUrl");
        url = getIntent().getStringExtra("url");
        infoStr = getIntent().getStringExtra("info");
        promotions = getIntent().getStringExtra("promotion");
        priceStr =  getIntent().getStringExtra("price");
        initView();
    }

    private void initView() {
        if (!Util.isEmpty(promotions)){
            String[] promotionTypes=promotions.split(",");
            int length=promotionTypes.length;
            for (int i=0;i<length;i++){
                TextView textView=new TextView(this);
                textView.setBackgroundResource(R.drawable.sp_round2_red);
                textView.setText(promotionTypes[i]);
                textView.setTextColor(getResources().getColor(R.color.color_white));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(ScreenUtil.dip2px(4),0,ScreenUtil.dip2px(4),0);
                LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,ScreenUtil.dip2px(16));
                if (i!=length-1){
                    ll.setMargins(0,0,ScreenUtil.dip2px(8),0);
                }
                promotionTypeLl.addView(textView,ll);
            }
        }
        UserBean.DataEntity.MemberEntity member = Session.getInstance().getUserFromFile(this);
        adapter=new Adapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (map.containsKey(position)){
                    int offer=map.get(position);
                    updateSpace(offer);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPrice();
        info.setText(infoStr);
        rq.setImageBitmap(BitmapUtils.createWhiteQRImage(url, ScreenUtil.dip2px(69), ScreenUtil.dip2px(69), 0));
    }

    private void updateSpace(int offer){
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams priceRl = (RelativeLayout.LayoutParams) price.getLayoutParams();
                priceRl.setMargins(ScreenUtil.dip2px(16), 0, 0, offer + ScreenUtil.dip2px(16));
                RelativeLayout.LayoutParams qrRl = (RelativeLayout.LayoutParams) rq.getLayoutParams();
                qrRl.setMargins(0, 0, ScreenUtil.dip2px(16), offer + ScreenUtil.dip2px(16));

                RelativeLayout.LayoutParams bottomRl = (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
                bottomRl.setMargins(0, 0, 0, offer);
                bottomRl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                ShapeDrawable bottomShape = new ShapeDrawable(new RectShape());
                bottomShape.getPaint().setShader(new LinearGradient(0, 0, 0, bottomView.getMeasuredHeight(), Color.parseColor("#00000000"), Color.parseColor("#b2000000"), Shader.TileMode.CLAMP));
                bottomView.setBackground(bottomShape);
            }
        });
    }

    private void setPrice(){
        int index=priceStr.indexOf("预");
        if (index>0){
            priceStr=priceStr.substring(0,index-2);
        }
        SpannableString spannableString = new SpannableString(priceStr);
        int firstIndex=priceStr.indexOf("¥");
        int lastIndex=priceStr.lastIndexOf("¥");
        if (firstIndex==lastIndex){ //只有一个价格
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
            spannableString.setSpan(sizeSpan, 1, priceStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, 0, priceStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }else { //两个价格
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
            spannableString.setSpan(sizeSpan, 1, lastIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.6f);
            spannableString.setSpan(sizeSpan1, lastIndex, priceStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, lastIndex, priceStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, 0, lastIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        price.setText(spannableString);
    }

    /**
     * 截屏
     */
    private void screenshot(String bigImageUrl) {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
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
                if (s.equals("j")) {
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
            Util.showToast(this, "保存成功!");
            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
            }
        }
    }

    public void saveWaterMarkFile(String bigImageUrl, Bitmap bigBitmap, String detailUrl) {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Util.showToast(this, "请确保要内存卡！");
            return;
        }
        /*if (bigBitmap.getByteCount()>8485760){
            Util.showToast(context,"图片太大保存失败!");
            return;
        }*/
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
            if (s.equals("j")) {
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
            return;
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
            bigBitmap.recycle();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        int scanWidth = canvas.getWidth() / 5;
        scanBitmap = BitmapUtils.createWhiteQRImage(detailUrl, scanWidth, scanWidth, 0);
        canvas.drawBitmap(scanBitmap, canvas.getWidth() / 20, canvas.getHeight() - scanBitmap.getHeight() - canvas.getWidth() / 100, p);
        tagBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sharebottom).copy(Bitmap.Config.ARGB_8888, true);
        tagBitmap = BitmapUtils.getScaleBitmap(tagBitmap, (float) canvas.getWidth() / tagBitmap.getWidth(), (float) canvas.getWidth() / tagBitmap.getWidth());
        canvas.drawBitmap(tagBitmap, canvas.getWidth() - tagBitmap.getWidth(), canvas.getHeight() - tagBitmap.getHeight(), p);

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bigBitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            file = null;
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
        Util.showToast(this, "保存成功!");
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);
        }
    }

    class Adapter extends PagerAdapter{

        @Override
        public int getCount() {
            return picUrl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView imageView=new PhotoView(SimpleImageActivity.this);
            Glide.with(SimpleImageActivity.this).load(Util.getD2cPicUrl(picUrl.get(position%picUrl.size())) + Constants.MY_SUFFIX).asBitmap().placeholder(R.mipmap.ic_logo_empty5).error(R.mipmap.ic_logo_empty5).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImageBitmap(resource);
                    int screenHeight = ScreenUtil.getDisplayHeight();
                    int offer = screenHeight - ScreenUtil.getStatusBarHeight(SimpleImageActivity.this) - resource.getHeight() * ScreenUtil.getDisplayWidth() / resource.getWidth();
                    offer = offer / 2;
                    map.put(position,offer);
                    if (!isFirst&&position==0){
                        updateSpace(offer);
                        isFirst=false;
                    }
                }
            });
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switchTypePop = new SwitchTypePop(SimpleImageActivity.this, true, false,false);

                    switchTypePop.setClickBack(new SwitchTypePop.ClickBack() {
                        @Override
                        public void clickBack(int type) {
                            switch (type) {
                                case 1: //保存图片
                                    // downLoadPic(info.bigImageUrl);
                                /*saveWaterMarkFile(picUrl,
                                        ((BitmapDrawable) bg.getDrawable()).getBitmap(), url);*/
                                    screenshot(picUrl.get(position));
                                    break;
                                case 2: //分享图片
                                    if (imageView.getDrawable() != null) {
                                        sharePop1 = new SharePop(SimpleImageActivity.this);
                                        sharePop1.setPic(true);
                                        sharePop1.setDetail(true);
                                        sharePop1.setBigImageUrl(picUrl.get(position));
                                        sharePop1.setBigBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                        sharePop1.setProductUrl(url);
                                        sharePop1.show(getWindow().getDecorView());
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
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

}
