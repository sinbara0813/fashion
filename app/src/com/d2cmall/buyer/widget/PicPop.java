package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者:Created by sinbara on 2018/10/19.
 * 邮箱:hrb940258169@163.com
 */

public class PicPop implements TransparentPop.InvokeListener {

    @Bind(R.id.content_iv)
    ImageView contentIv;
    @Bind(R.id.close_iv)
    ImageView closeIv;
    private TransparentPop pop;
    private View rootView;

    public PicPop(Context context,String pic,String url) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_pic_pop, new LinearLayout(context),false);
        ButterKnife.bind(this,rootView);
        pop = new TransparentPop(context,false,this);
        pop.setFocusable(false);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop!=null){
                    pop.dismiss(false);
                }
            }
        });
        contentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isEmpty(url)){
                    Util.urlAction(context,url);
                }
                pop.dismiss(false);
            }
        });
        Glide.with(context).load(Util.getD2cPicUrl(pic)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                contentIv.getLayoutParams().height=Math.min(ScreenUtil.dip2px(380),resource.getHeight()* ScreenUtil.dip2px(300)/resource.getWidth());
                contentIv.setImageBitmap(resource);
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
