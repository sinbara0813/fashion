package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.Explore1PublishActivity;
import com.d2cmall.buyer.activity.MediaPickActivity;
import com.d2cmall.buyer.activity.StartLiveActivity;
import com.d2cmall.buyer.activity.VideoRecordActivity;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.FashionSubFragment;
import com.d2cmall.buyer.util.FastBlur;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/29 16:25
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SocialPop implements TransparentPop.InvokeListener {

    @Bind(R.id.live_ll)
    LinearLayout liveLl;
    @Bind(R.id.video_ll)
    LinearLayout videoLl;
    @Bind(R.id.pic_ll)
    LinearLayout picLl;
    @Bind(R.id.cancel)
    ImageView cancel;
    @Bind(R.id.live_pic)
    ImageView livePic;
    @Bind(R.id.live_name)
    TextView liveName;

    private View rootView;
    private TransparentPop mPop;
    private Activity context;
    private TopicBean.DataBean.TopicsBean.ListBean topicBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VideoPop videoPop = new VideoPop(context);
            videoPop.show(context.getWindow().getDecorView());
        }
    };
    private boolean isFirst;
    private boolean isDesigner;

    public SocialPop(Activity context, boolean is) {
        this.context = context;
        isDesigner=is;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_social_bottom, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        if (!is) {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) videoLl.getLayoutParams();
            rl.setMargins(ScreenUtil.dip2px(76), rl.topMargin, rl.rightMargin, 0);
            rl = (RelativeLayout.LayoutParams) liveLl.getLayoutParams();
            rl.setMargins(rl.leftMargin, rl.topMargin, ScreenUtil.dip2px(76), 0);
            liveName.setText("图文");
            livePic.setImageResource(R.mipmap.icon_publish_picture);
        }
        mPop = new TransparentPop(context, this);
        mPop.getParent().setBackgroundResource(R.color.trans_66_color_white);
        mPop.setFocusable(false);
        mPop.setBackgroundDrawable(new BitmapDrawable(context.getResources(),
                FastBlur.getBlurBackgroundDrawer(context)));
        //mPop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
        mPop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
    }


    public void setTopicBean(TopicBean.DataBean.TopicsBean.ListBean topicBean) {
        this.topicBean = topicBean;
    }

    @OnClick({R.id.live_ll, R.id.video_ll, R.id.pic_ll, R.id.cancel})
    public void Click(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.live_ll:
                if (!isDesigner){
                    if (Util.loginChecked(context, Constants.Login.EXPLORE_CAMERA_LOGIN)) {
                        Intent intent1 = new Intent(context, Explore1PublishActivity.class);
                        if (topicBean != null) {
                            intent1.putExtra("topic", topicBean);
                        }
                        context.startActivity(intent1);
                        //showAlbum();
                    }
                    dismiss();
                }else {
                    intent = new Intent(context, StartLiveActivity.class);
                    context.startActivity(intent);
                    dismiss();
                }
                break;
            case R.id.video_ll:
                if (FashionSubFragment.isUpload) {
                    showVideoPop();
                } else {
                    UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
                    if(user!=null && (user.getDesignerId() > 0 || user.getType() == 3)){
                        intent = new Intent(context, MediaPickActivity.class);
                        if (topicBean != null) {
                            intent.putExtra("topic", topicBean);
                        }
                        intent.putExtra("maxSum", 1);
                    }else{
                        intent = new Intent(context, VideoRecordActivity.class);
                        intent.putExtra("channel", "social");
                        if (topicBean != null) {
                            intent.putExtra("topic", topicBean);
                        }
                    }
                    context.startActivity(intent);
                }
                dismiss();
                break;
            case R.id.pic_ll:
                if (Util.loginChecked(context, Constants.Login.EXPLORE_CAMERA_LOGIN)) {
                    Intent intent1 = new Intent(context, Explore1PublishActivity.class);
                    if (topicBean != null) {
                        intent1.putExtra("topic", topicBean);
                    }
                    context.startActivity(intent1);
                    //showAlbum();
                }
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private void showVideoPop() {
        dismiss();
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    public void show(View view) {
        mPop.show(view);
    }

    private Animator createAnimator(View view, int duction, float... offer) {
        return ObjectAnimator.ofFloat(view, "translationY", offer).setDuration(duction);
    }

    public boolean isShow() {
        if (mPop != null) {
            return mPop.isShowing();
        }
        return false;
    }

    public void dismiss() {
        mPop.dismiss(true);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isFirst) {
                    int height = rootView.getHeight();
                    if (height > 0) {
                        isFirst = true;
                        videoLl.setVisibility(View.VISIBLE);
                        liveLl.setVisibility(View.VISIBLE);
                        int startY = height;
                        List<Animator> animators = new ArrayList<>();

                        if (isDesigner) {
                            picLl.setVisibility(View.VISIBLE);
                            animators.add(createAnimator(videoLl, 300, startY, 0));
                            animators.add(createAnimator(picLl, 400, startY, 0));
                            animators.add(createAnimator(liveLl, 450, startY, 0));
                        } else {
                            animators.add(createAnimator(videoLl, 300, startY, 0));
                            animators.add(createAnimator(liveLl, 400, startY, 0));
                        }
                        AnimatorSet resultSet = new AnimatorSet();
                        resultSet.playTogether(animators);
                        resultSet.setInterpolator(new OvershootInterpolator());
                        resultSet.start();
                    }
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }
}
