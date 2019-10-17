package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.MessageActivity;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.MessageListApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.MessageListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.NoSlideViewPager;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import q.rorbin.badgeview.QBadgeView;

/**
 * Name: D2CNEW
 * Anthor: hrb
 * Date: 2017/6/5 15:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionFragment extends BaseTabFragment implements View.OnClickListener {

    @Bind(R.id.title_ll)
    LinearLayout titleLl;
    @Bind(R.id.message_iv)
    ImageView messageIv;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.view_pager)
    NoSlideViewPager viewPager;
    @Bind(R.id.tag)
    View tag;
    private List<String> list;
    private List<Fragment> fragmentList;
    private FashionShowFragment fashionShowFragment;
    private BaseFragment fashionFocusFragment;
    private FashionLiveFragment fashionLiveFragment;
    private int initPage;
    private boolean isAnimation;
    private String messageType=null;
    private long mUserId;
    private QBadgeView mBadgeView;
    private int unreadCount;

    public static FashionFragment newInstance(int page){
        FashionFragment fashionFragment=new FashionFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("page",page);
        fashionFragment.setArguments(bundle);
        return fashionFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fashion, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            initPage=getArguments().getInt("page");
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void doBusiness() {
        list=new ArrayList<>();
        fragmentList=new ArrayList<>();
        fashionShowFragment=new FashionShowFragment();
        fragmentList.add(fashionShowFragment);
        /*fashionFocusFragment=new FashionUnFocusFragment();
        fragmentList.add(fashionFocusFragment);
        fashionLiveFragment=new FashionLiveFragment();
        fragmentList.add(fashionLiveFragment);
        viewPager.setOffscreenPageLimit(2);*/
        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(getChildFragmentManager(),fragmentList,null);
        viewPager.setAdapter(tabPagerAdapter);
        initPage(initPage);
        initListener();
        if (Session.getInstance().getUserFromFile(getActivity())!=null&&Session.getInstance().getUserFromFile(getActivity()).getMemberId()>0){
            requestUnreadNumTak(false);
        }
    }

    private void requestUnreadNumTak(final boolean isReadAction) {
        MessageListApi api = new MessageListApi();
        long type6=D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6,System.currentTimeMillis());
        long type7=D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE7 ,System.currentTimeMillis());
        api.majorTypeTime6=type6;
        api.majorTypeTime7=type7;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MessageListBean>() {
            @Override
            public void onResponse(MessageListBean response) {
                if(mBadgeView==null) {
                    mBadgeView = (QBadgeView) new QBadgeView(getActivity()).bindTarget(messageIv).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(0, 3, true);
                    messageIv.setId(R.id.message_iv);
                }
                if(unreadCount < response.getData().getUnreadCount() && isReadAction){
                    return;
                }
                unreadCount = response.getData().getUnreadCount();
                if (response.getData().getUnreadCount()>9){
                    mBadgeView.setVisibility(View.VISIBLE);
                    mBadgeView.setBadgeText("9+");
                }else if (response.getData().getUnreadCount()>0){
                    mBadgeView.setVisibility(View.VISIBLE);
                    mBadgeView.setBadgeText(String.valueOf(response.getData().getUnreadCount()));
                }else if (response.getData().getUnreadCount()==0){
                    mBadgeView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initPage(int page){
        String[] strings=new String[]{"SHOW"};
        titleLl.removeAllViews();
        for (int i=0;i<1;i++){
            LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,-1);
            TextView textView=new TextView(getActivity());
            if (i!=2){
                textView.setPadding(0,0,ScreenUtil.dip2px(16),0);
            }
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(strings[(page+i)%1]);
            textView.setOnClickListener(FashionFragment.this);
            if (i==0){
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.color_black87));
                textView.setTypeface(null,Typeface.BOLD);
            }else {
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setTypeface(null,Typeface.NORMAL);
                textView.setTextSize(14);
            }
            titleLl.addView(textView,ll);
        }
        viewPager.setCurrentItem(page);
    }

    public void setPage(int page){
        if (page>0)return;
        String text = null;
        switch (page){
            case 0:
                text="SHOW";
                break;
            case 1:
                text="关注";
                break;
            case 2:
                text="直播";
        }
        for (int i=0;i<3;i++){
            TextView textView= (TextView) titleLl.getChildAt(i);
            if (textView.getText().toString().equals(text)){
                onClick(textView);
                break;
            }
        }
    }

    private void initListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (Util.isLowThanAndroid5()){
                    if (!(fragmentList.get(position) instanceof FashionShowFragment)){
                        tag.setVisibility(View.VISIBLE);
                    }else {
                        tag.setVisibility(View.GONE);
                    }
                }
                if (position>1){
                    ViewCompat.setElevation(titleLayout, ScreenUtil.dip2px(4));
                }else {
                    ViewCompat.setElevation(titleLayout,0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setElevation(){
        ViewCompat.setElevation(titleLayout,ScreenUtil.dip2px(4));
    }

    public void startAnimation(final TextView view){
        List<Animator> resultList = new LinkedList<Animator>();
        TextView firstView= (TextView) titleLl.getChildAt(0);
        if (firstView==view){
            view.setEnabled(true);
            return;
        }
        int titleWidth=titleLl.getWidth();
        int childCount=titleLl.getChildCount();
        firstView.setTextSize(14);
        firstView.setTextColor(getResources().getColor(R.color.color_black38));
        firstView.setTypeface(null, Typeface.NORMAL);
        view.setTextSize(20);
        view.setTypeface(null, Typeface.BOLD);
        int left=view.getLeft();
        int offer1=getOffer(firstView.getText().toString());
        int offer2=getOffer(view.getText().toString());
        for (int i=0;i<childCount;i++){
            TextView child= (TextView) titleLl.getChildAt(i);
            if (child.getLeft()>=left){
                if (child.getLeft()==left){
                    resultList.add(createTranslationAnimations(child,-0,-left+offer1));
                }else {
                    resultList.add(createTranslationAnimations(child,0,-left+offer1));
                }
                list.add(child.getText().toString());
            }else if (child.getLeft()<left){
                resultList.add(createTranslationAnimations(child,titleWidth-offer1+offer2+ScreenUtil.dip2px(16), titleWidth-offer1+offer2+ScreenUtil.dip2px(16)-left+offer1));
            }
        }
        for (int i=0;i<childCount;i++){
            TextView child= (TextView) titleLl.getChildAt(i);
            if (child.getLeft()<left){
                list.add(child.getText().toString());
            }else {
                break;
            }
        }
        AnimatorSet resultSet = new AnimatorSet();
        resultSet.playTogether(resultList);
        resultSet.setDuration(300);
        resultSet.setInterpolator(new AccelerateDecelerateInterpolator());
        resultSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                    isAnimation=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation=false;
                animationEnd();
                view.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation=false;
                view.setEnabled(true);
                super.onAnimationCancel(animation);
            }
        });
        resultSet.start();
    }

    private void animationEnd(){
        titleLl.removeAllViews();
        for (int i=0;i<3;i++){
            LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,-1);
            TextView textView=new TextView(getActivity());
            if (i!=2){
                textView.setPadding(0,0,ScreenUtil.dip2px(16),0);
            }
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(list.get(i));
            textView.setOnClickListener(FashionFragment.this);
            if (i==0){
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.color_black87));
                textView.setTypeface(null,Typeface.BOLD);
            }else {
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setTypeface(null,Typeface.NORMAL);
                textView.setTextSize(14);
            }
            titleLl.addView(textView,ll);
        }
        list.clear();
    }

    private Animator createTranslationAnimations(View view, float startX, float endX) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX",
                startX, endX);
        return animX;
    }

    public int getOffer(String text){
        int offer=0;
        if (text.equals("SHOW")){
            offer=ScreenUtil.dip2px(6);
        }
        Paint paint1=new Paint();
        paint1.setTextSize(ScreenUtil.sp2px(14));
        Rect rect1=new Rect();
        paint1.getTextBounds(text,0,text.length(),rect1);
        Paint paint2=new Paint();
        paint2.setTextSize(ScreenUtil.sp2px(20));
        Rect rect2=new Rect();
        paint2.getTextBounds(text,0,text.length(),rect2);
        return rect2.width()-rect1.width()+0;
    }

    @Override
    public void onClick(View v) {
        if (isAnimation){
            return;
        }
        v.setEnabled(false);
        ((TextView)v).setTextColor(getResources().getColor(R.color.color_black87));
        if (v instanceof TextView){
            TextView textView= (TextView) v;
            startAnimation((TextView) v);
            if (textView.getText().toString().equals(getString(R.string.label_fashion_show))){
                viewPager.setCurrentItem(0);
            }else if (textView.getText().toString().equals(getString(R.string.label_focus))){
                viewPager.setCurrentItem(1);
            }else if (textView.getText().toString().equals(getString(R.string.label_live))){
                viewPager.setCurrentItem(2);
            }
        }
    }

    @OnClick({R.id.message_iv})
    public void click(View view){
        if (!isVisibleToUser){
            return;
        }
        switch (view.getId()){
            case R.id.message_iv:
                boolean isLogin = Util.loginChecked(getActivity(), 1);
                if (isLogin){
                       //消息界面
                    getActivity().startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
        }
    }

    @Subscribe
    public void onEvent(ActionBean bean){
        if (bean.type==Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE){
            int readCount= (int) bean.get("readCount");
            if(readCount>0 && mBadgeView!=null){
                unreadCount -= readCount;
                if (unreadCount > 9) {
                    mBadgeView.setVisibility(View.VISIBLE);
                    mBadgeView.setBadgeText("9+");
                } else if (unreadCount > 0) {
                    mBadgeView.setVisibility(View.VISIBLE);
                    mBadgeView.setBadgeText(String.valueOf(unreadCount));
                } else if (unreadCount <= 0) {
                    mBadgeView.setVisibility(View.INVISIBLE);
                }
                requestUnreadNumTak(true);
            }else{
                requestUnreadNumTak(false);
            }
        }
    }

    @Override
    public void releaseOnInVisible() {
        fragmentList.get(viewPager.getCurrentItem()).setUserVisibleHint(false);
    }

    @Override
    public void show() {
        if (fragmentList!=null&&viewPager!=null){
            fragmentList.get(viewPager.getCurrentItem()).setUserVisibleHint(true);
        }
        super.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
