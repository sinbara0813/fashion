package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 买手中心联系我们pop
 * Copyright (c) 2018 d2c. All rights reserved.
 */
public class PartnerInvitePop implements TransparentPop.InvokeListener {
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private TextView tvInviteBuyer;
    private TextView tvInvitePartner;
    private final PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private Bitmap headBitmap;
    private boolean isLoading;
    public static final int IMAGE_SIZE = 131072;
    private float scale=0.8F;
    private LinearLayout llInvite;
    private TextView tvCancle;

    public PartnerInvitePop(Context context) {
        partnerBean = Session.getInstance().getPartnerFromFile(context);
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_partner_invite_pop, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        inAnimation.setInterpolator(new DecelerateInterpolator());
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        outAnimation.setInterpolator(new AccelerateInterpolator());
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        tvInviteBuyer= (TextView) rootView.findViewById(R.id.tv_invite_buyer);
        tvInvitePartner= (TextView) rootView.findViewById(R.id.tv_invite_dm);
        tvCancle= (TextView) rootView.findViewById(R.id.tv_cancle);
        llInvite = (LinearLayout)rootView.findViewById(R.id.ll_invite);
        //动态设置下布局的宽度
        ViewGroup.LayoutParams layoutParams = llInvite.getLayoutParams();
        layoutParams.width= ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32);
        llInvite.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams1 = tvCancle.getLayoutParams();
        layoutParams1.width= ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32);
        tvCancle.setLayoutParams(layoutParams1);
        tvInvitePartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHeadAndShare("http://img.d2c.cn/app/a/18/05/08/mini.png",1);
                dismiss();
            }
        });
        tvInviteBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHeadAndShare("http://img.d2c.cn/app/a/18/05/08/mini.png",0);
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        llInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法拦截
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(View parent) {
        mPop.show(parent, true);
    }
    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }
    public void setDismissListener(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }



    public void loadHeadAndShare(final String url , final int inviteType){//下载用户头像转Bitmap

        String title;
        if(inviteType==0){
            title= context.getString(R.string.partner_invite_buyer, partnerBean.getName());
        }else{
            title = context.getString(R.string.partner_invite_dm, partnerBean.getName());
        }
        String avatar;
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
        if(user!=null && user.getHead()!=null){
            avatar= Util.toURLEncode(Constants.IMG_HOST+user.getHead());
        }else{
            avatar=Util.toURLEncode("http://d2c-app.b0.upaiyun.com/img/logo/android_default_avatar.png");
        }
        final String tempTitle=title;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    Resources resources = context.getResources();
                    headBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_default_avatar);
                    WxHandle.getInstance(context).shareMiniApps(context, BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(headBitmap, (float) 0.8,(float) 0.8)), tempTitle,tempTitle ,partnerBean.getId(),inviteType,avatar);
                    return;
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    if(bitmap ==null){
                        Resources resources = context.getResources();
                        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_default_avatar);
                    }
                    byte[] bitmapData = BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(bitmap,  scale, scale));
                    while ( bitmapData .length > IMAGE_SIZE) {
                        scale -= 0.1;
                        Bitmap scaleBitmap = BitmapUtils.getScaleBitmap( bitmap, scale, scale);
                        bitmapData = BitmapUtils.getBitmapData(scaleBitmap);
                        scaleBitmap.recycle();
                    }

                    //最后一个参数type=0是买手 type=1是DM
                    WxHandle.getInstance(context).shareMiniApps(context, BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(bitmap, (float) 0.8,(float) 0.8)), tempTitle,tempTitle ,partnerBean.getId(),inviteType,avatar);
                } catch (IOException e) {
                    Resources resources = context.getResources();
                    headBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_default_avatar);
                    WxHandle.getInstance(context).shareMiniApps(context, BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(headBitmap, (float) 0.8,(float) 0.8)), tempTitle,tempTitle ,partnerBean.getId(),inviteType,avatar);
                }
            }
        }).start();
    }


}
