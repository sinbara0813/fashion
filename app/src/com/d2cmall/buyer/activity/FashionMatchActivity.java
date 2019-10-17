package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.util.LoctionUtil;
import com.d2cmall.buyer.widget.FashionBottomPop;
import com.d2cmall.buyer.widget.FashionMatchTabLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 作者:Created by sinbara on 2018/11/12.
 * 邮箱:hrb940258169@163.com
 * 穿衣搭配
 */

public class FashionMatchActivity extends BaseActivity {

    @Bind(R.id.tab_layout)
    FashionMatchTabLayout tabLayout;

    private int position=1;
    private AlertDialog alertDialog;
    private LoctionUtil mLoctionUtil;
    public static String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_match);
        ButterKnife.bind(this);
        position = getIntent().getIntExtra("position", 2);
        init();
    }

    private void init(){
        tabLayout.init(position);
    }

    @OnClick(R.id.take_photo)
    public void click(View view) {
        boolean hasShowTakePhotoGuide = D2CApplication.mSharePref.getSharePrefBoolean("hasShowTakePhotoGuide", false);
        if(!hasShowTakePhotoGuide){
            showDialog();
            D2CApplication.mSharePref.putSharePrefBoolean("hasShowTakePhotoGuide", true);
        }else{
            FashionBottomPop pop=new FashionBottomPop(this);
            pop.show(tabLayout);
        }

    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_guide_take_photo, null);
        TextView tvType = view.findViewById(R.id.tv_type);
        ImageView ivGuide = view.findViewById(R.id.iv_guide);
        TextView tvStep = view.findViewById(R.id.tv_step);
        TextView tvDismiss = view.findViewById(R.id.tv_dismiss);
        TextView tvNext = view.findViewById(R.id.tv_next);
        LinearLayout llAction = view.findViewById(R.id.ll_action);
        TextView tvTakePhoto = view.findViewById(R.id.tv_take_photo);
        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAction.setVisibility(View.GONE);
                tvTakePhoto.setVisibility(View.VISIBLE);
                ivGuide.setImageResource(R.mipmap.pic_pszn02);
                tvStep.setText("2/2");
                tvType.setText("背景整洁");
            }
        });
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FashionBottomPop pop=new FashionBottomPop(FashionMatchActivity.this);
                pop.show(tabLayout);
                if (alertDialog != null ) {
                    alertDialog.dismiss();
                }
            }
        });
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onStart() {
        LoctionUtil.checklocationpermiss((Activity) this);
        mLoctionUtil = LoctionUtil.getLoctionUtil(this);
        mLoctionUtil.setIhasCity(new LoctionUtil.IhasCity() {
            @Override
            public String getAddress(String province, String cityName, String street) {
                String encode = null;
                try {
                    encode= URLEncoder.encode(cityName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("han","cityName=="+encode);
                if (tabLayout!=null){
                    tabLayout.loadWeather(encode);
                }
                FashionMatchActivity.city=cityName;
                return null;
            }
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mLoctionUtil!=null){
            mLoctionUtil.stopLocation();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mLoctionUtil!=null){
            mLoctionUtil.destroyLocation();
        }
        super.onDestroy();
    }
}
