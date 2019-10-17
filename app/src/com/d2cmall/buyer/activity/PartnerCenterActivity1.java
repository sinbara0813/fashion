package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.widget.OpenMsgPushPop;
import com.d2cmall.buyer.widget.PartnerTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dec: 买手中心
 * Author: hrb
 * Date: 2018/4/13 14:00
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PartnerCenterActivity1 extends AppCompatActivity {

    @Bind(R.id.tab_layout)
    PartnerTabLayout tabLayout;
    private int position;//跳转进来选中的fragmnet位置
    private int subPosition;//直接跳转到经营中心的店铺或团队Fragment
    private boolean isTabGone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_center);
        ButterKnife.bind(this);
        position = getIntent().getIntExtra("position", 1);
        subPosition = getIntent().getIntExtra("subPosition", 0);
        isTabGone = getIntent().getBooleanExtra("isTabGone", false);
        if(isTabGone){
            tabLayout.setVisibility(View.GONE);
        }
        if(subPosition>=1){
            tabLayout.setSubPosition(subPosition);
        }
        init();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {//在OonCreate,onResume方法中直接使用popWindow会有异常,activity获得焦点之后，证明加载完成了
        //开启消息推送行为节点
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        boolean isOpened = manager.areNotificationsEnabled();
        if(hasFocus){
            Boolean isMsgPushOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG, false);
            if(!isMsgPushOpen && !isOpened){
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG,true);
                OpenMsgPushPop openMsgPushPop = new OpenMsgPushPop(PartnerCenterActivity1.this);
                openMsgPushPop.setContent(getString(R.string.label_pop_focus_partner));
                openMsgPushPop.show(getWindow().getDecorView());
            }
        }
        super.onWindowFocusChanged(hasFocus);
    }


    private void init() {
        tabLayout.init(position);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
