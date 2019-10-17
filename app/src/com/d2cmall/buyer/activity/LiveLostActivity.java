package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 直播丢失页面
 * Author: hrb
 * Date: 2017/03/24 10:46
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveLostActivity extends BaseActivity {

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
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.empty_hint_layout)
    RelativeLayout emptyHintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_lost);
        ButterKnife.bind(this);
        nameTv.setText("主播走丢了");
        int status = getIntent().getIntExtra("status", 0);
        int flag = getIntent().getIntExtra("flag", 0);
        if (status == -3) {
            if (flag == 1) {
                Util.showToast(this, "您的直播预告已被下架");
            } else {
                Util.showToast(this, "直播不存在!");
            }
        } else if (status == -9) {
            Util.showToast(this, "直播不存在!");
        }
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
