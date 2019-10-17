package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ActionBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/11/21 13:23
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class GuidePop implements TransparentPop.InvokeListener {

    @Bind(R.id.lead_iv)
    ImageView leadIv;
    @Bind(R.id.close_iv)
    ImageView closeIv;
    private TransparentPop pop;
    private View rootView;

    public GuidePop(final Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_guide_image, new LinearLayout(context),false);
        ButterKnife.bind(this,rootView);
        pop = new TransparentPop(context,false,this);
        pop.setBackGroundResource(R.color.trans_60_color_black);
        pop.setFocusable(false);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop!=null){
                    pop.dismiss(false);
                }
            }
        });
        leadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop!=null){
                    pop.dismiss(false);
                }
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.XINREN));
            }
        });
    }

    public void show(View view) {
        pop.show(view, false);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }
}
