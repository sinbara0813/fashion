package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.GuideViewAdapter;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.SharePrefConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/6/29 13:37
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class GuideViewActivity extends Activity {
    private ViewPager viewPage;
    private int[] imageView = { R.mipmap.ic_page1, R.mipmap.ic_page2,
            R.mipmap.ic_page3, R.mipmap.ic_page4 };
    private List<View> list;
    private LinearLayout llPoint;
    private TextView textView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_view_activity);
        initview();
        initoper();
        addView();
        addPoint();

    }

    private void initoper() {

        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                monitorPoint(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    private void initview() {
        viewPage = (ViewPager) findViewById(R.id.viewpage);
        llPoint = (LinearLayout) findViewById(R.id.llPoint);
        textView = (TextView) findViewById(R.id.guideTv);
    }

    /**
     * 娣诲姞鍥剧墖鍒皏iew
     */
    private void addView() {
        list = new ArrayList<View>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imageView.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setImageResource(imageView[i]);
            list.add(iv);
        }
        viewPage.setAdapter(new GuideViewAdapter(list));

    }

    /**
     * 娣诲姞灏忓渾鐐?
     */
    private void addPoint() {
        for (int i = 0; i < imageView.length; i++) {
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(
                    ScreenUtil.dip2px(16), ScreenUtil.dip2px(2));
            if (i < 1) {
                pointParams.setMargins(0, 0, 0, 0);
            } else {
                pointParams.setMargins(ScreenUtil.dip2px(4), 0, 0, 0);
            }
            View iv = new View(this);
            iv.setLayoutParams(pointParams);
            iv.setBackgroundColor(getResources().getColor(R.color.gray_color));
            llPoint.addView(iv);
        }
        llPoint.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.color_black87));

    }

    /**
     * 鍒ゆ柇灏忓渾鐐?
     *
     * @param position
     */
    private void monitorPoint(int position) {
        for (int i = 0; i < imageView.length; i++) {
            if (i == position) {
                llPoint.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.color_black87));
            } else {
                llPoint.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.gray_color));
            }
        }
        if (position == imageView.length - 1) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public void onStart(View view) {
        view.setEnabled(false);
        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_FIRST, false);
        Intent intent = new Intent(GuideViewActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (count>=1){
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_FIRST, false);
                Intent intent = new Intent(GuideViewActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            count++;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                if (list.get(i) instanceof ImageView){
                    ImageView imageView=(ImageView) list.get(i);
                    Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    if (bitmap!=null){
                        bitmap.recycle();
                        bitmap=null;
                    }
                    imageView=null;
                }
                list.clear();
            }
        }
        super.onDestroy();
    }
}
