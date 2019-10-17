package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.FashionFragment;
import com.d2cmall.buyer.fragment.MainFragment;
import com.d2cmall.buyer.fragment.MineNewFragment;
import com.d2cmall.buyer.fragment.MineSuperFragment;
import com.d2cmall.buyer.fragment.PartnerMineFragment;
import com.d2cmall.buyer.fragment.ShoppingFragment;
import com.d2cmall.buyer.fragment.SpecialFragment;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Name: D2CNEW
 * Anthor: hrb
 * Date: 2017/7/3 15:48
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HomeTabLayout extends LinearLayout implements PartnerMineFragment.ChangeFragmentListener {

    private UserBean.DataEntity.MemberEntity user;
    @Bind(R.id.main_iv)
    ImageView mainIv;
    @Bind(R.id.special_iv)
    ImageView specialIv;
    @Bind(R.id.fashion_iv)
    ImageView fashionIv;
    @Bind(R.id.shopping_iv)
    ImageView shoppingIv;
    @Bind(R.id.mine_iv)
    ImageView mineIv;

    private Fragment mainFragment;
    private Fragment specialFragment;
    private Fragment fashionFragment;
    private Fragment shoppingFragment;
    private Fragment mineFragment;
    private Fragment partnerMineFragment;

    private int lastShowIndex;
    private Fragment lastShowFragment;
    private Fragment lastMineShowFragment;
    private TabSelectListener selectListener;
    private FragmentManager fragmentManager;
    private String[] mainColor;
    private String[] specialColor;
    private String[] fashionColor;
    private String[] shopColor;
    private String[] mineColor;
    private boolean isInit;
    private boolean isFirstClickMine;

    public HomeTabLayout(Context context) {
        this(context, null);
    }

    public HomeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.BOTTOM);
        setBackgroundColor(getResources().getColor(R.color.transparent));
        LayoutInflater.from(context).inflate(R.layout.layout_home_tab_layout, this);
        ButterKnife.bind(this, this);
        if (context instanceof FragmentActivity) {
            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        } else {
            throw new NullPointerException("context must be FragmentActivity");
        }
        if (user == null) {
            user = Session.getInstance().getUserFromFile(context);
        }
    }

    @OnClick({R.id.main_iv, R.id.special_iv, R.id.fashion_iv, R.id.shopping_iv, R.id.mine_iv})
    public void onClick(View view) {
        int index = 1;
        switch (view.getId()) {
            case R.id.main_iv:
                index = 1;
                break;
            case R.id.special_iv:
                index = 2;
                break;
            case R.id.fashion_iv:
                index = 3;
                break;
            case R.id.shopping_iv:
                index = 4;
                break;
            case R.id.mine_iv:
                index = 5;
                break;
        }
        if (selectListener != null) {
            if (lastShowIndex == index) {
                selectListener.onTabReselected(index);
            }
            if (selectListener.onTabSelected(index)) {
                switchFragment(false,index);
            }
        } else {
            switchFragment(false,index);
        }
    }

    public void init(int... position) {
        isInit = true;
        switchFragment(false,position);
    }

    private void switchFragment(boolean isPullDown,int... position) {
        if (position.length == 0) {
            return;
        }
        int index = position[0];
        if (index == lastShowIndex) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (lastShowFragment != null) {
            ((BaseTabFragment)lastShowFragment).hide();
            ft.hide(lastShowFragment);
        }
        checkSelect(index);
        switch (index) {
            case 1://首页
                mainFragment= fragmentManager.findFragmentByTag("main");
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    ft.add(R.id.fragment_container, mainFragment,"main");
                } else {
                    ft.show(mainFragment);
                }
                lastShowFragment = mainFragment;
                //Log.d("han","switchFragment"+">>mainFragment");
                break;
            case 2://专题
                specialFragment=fragmentManager.findFragmentByTag("special");
                if (specialFragment == null) {
                    specialFragment = new SpecialFragment();
                    ft.add(R.id.fragment_container, specialFragment,"special");
                } else {
                    ft.show(specialFragment);
                }
                lastShowFragment = specialFragment;
                //Log.d("han","switchFragment"+">>specialFragment");
                break;
            case 3://时尚圈
                fashionFragment=fragmentManager.findFragmentByTag("fashion");
                if (fashionFragment == null) {
                    if (position.length == 2) {
                        int secondIndex = position[1];
                        fashionFragment = FashionFragment.newInstance(secondIndex);
                    } else {
                        fashionFragment = new FashionFragment();
                    }
                    ft.add(R.id.fragment_container, fashionFragment,"fashion");
                } else {
                    ft.show(fashionFragment);
                    if (position.length == 2) {
                        int secondIndex = position[1];
                        ((FashionFragment) fashionFragment).setPage(secondIndex);
                    }
                }
                lastShowFragment = fashionFragment;
                //Log.d("han","switchFragment"+">>fashionFragment");
                break;
            case 4://商品
                shoppingFragment=fragmentManager.findFragmentByTag("shop");
                if (shoppingFragment == null) {
                    shoppingFragment = new ShoppingFragment();
                    ft.add(R.id.fragment_container, shoppingFragment,"shop");
                } else {
                    ft.show(shoppingFragment);
                }
                lastShowFragment = shoppingFragment;
                //Log.d("han","switchFragment"+">>shoppingFragment");
                break;
            case 5://我的
                if(!isFirstClickMine && user!=null && user.getPartnerId()>0){
                    switchToPartnerFragment(ft);
                }else{
                    if(lastMineShowFragment==null){
                        switchToMineFragment(ft);
                        break;
                    }
                    if(isPullDown){ //我的界面是下拉切换
                        ft.setCustomAnimations(R.anim.mine_fragment_anim_in, R.anim.anim_out);
                        if(lastMineShowFragment == partnerMineFragment || user==null || user.getPartnerId()<=0){
                            switchToMineFragment(ft);
                        }else{
                            switchToPartnerFragment(ft);
                        }
                    }else{//点击主页tab回到我的界面
                        if(lastMineShowFragment == partnerMineFragment){
                            switchToPartnerFragment(ft);
                        }else{
                            switchToMineFragment(ft);
                        }
                    }
                }
                isFirstClickMine=true;
                //Log.d("han","switchFragment"+">>mineFragment");
                break;
            case 6://买手我的
                ft.setCustomAnimations(R.anim.mine_fragment_anim_in, R.anim.anim_out);
                switchToPartnerFragment(ft);
                //Log.d("han","switchFragment"+">>partnerMineFragment");
                break;
        }
        ((BaseTabFragment)lastShowFragment).show();
        ft.commitAllowingStateLoss();
        if(index==5 && lastMineShowFragment==partnerMineFragment){
            lastShowIndex = 6;
        }else{
            lastShowIndex = index;
        }

    }

    private void switchToMineFragment(FragmentTransaction ft) {
        mineFragment=fragmentManager.findFragmentByTag("mine");
        if (mineFragment == null) {
            mineFragment=new MineNewFragment();
            ft.add(R.id.fragment_container, mineFragment,"mine");
        } else {
            ft.show(mineFragment);
        }
        if(((MineSuperFragment)mineFragment).getChangeFragmentListener()==null){
            ((MineSuperFragment)mineFragment).setChangeFragmentListener(this);
        }
        lastShowFragment = mineFragment;
        lastMineShowFragment=mineFragment;
    }

    private void switchToPartnerFragment(FragmentTransaction ft) {
        partnerMineFragment=fragmentManager.findFragmentByTag("partnerMine");
        if (partnerMineFragment == null) {
            partnerMineFragment=new PartnerMineFragment();
            ft.add(R.id.fragment_container, partnerMineFragment,"partnerMine");
        } else {
            ft.show(partnerMineFragment);
        }
        if(((MineSuperFragment)partnerMineFragment).getChangeFragmentListener()==null){
            ((MineSuperFragment)partnerMineFragment).setChangeFragmentListener(this);
        }
        lastShowFragment = partnerMineFragment;
        lastMineShowFragment=partnerMineFragment;
    }

    public void checkSelect(int index) {
        setUnSelect();
        GifDrawable gifDrawable;
        switch (index) {
            case 1:
                if (mainColor!=null) {
                    displayImage(getContext(), Util.getD2cPicUrl(mainColor[1])+Constants.MY_SUFFIX, mainIv, 0, 0);
                } else {
                    mainIv.setImageResource(R.mipmap.icon_tab_home_per);
                }
                mainIv.setSelected(true);
                break;
            case 2:
                if (specialColor!=null) {
                    displayImage(getContext(), Util.getD2cPicUrl(specialColor[1])+Constants.MY_SUFFIX, specialIv,0,0);
                } else {
                    specialIv.setImageResource(R.mipmap.icon_tab_activity_per);
                }
                specialIv.setSelected(true);
                break;
            case 3:
                if (fashionColor!=null) {
                    displayImage(getContext(), Util.getD2cPicUrl(fashionColor[1])+Constants.MY_SUFFIX, fashionIv,0,0);
                } else {
                    fashionIv.setImageResource(R.mipmap.icon_tab_show_per);
                }
                fashionIv.setSelected(true);
                break;
            case 4:
                if (shopColor!=null) {
                    displayImage(getContext(), Util.getD2cPicUrl(shopColor[1])+Constants.MY_SUFFIX, shoppingIv,0,0);
                } else {
                    shoppingIv.setImageResource(R.mipmap.icon_tab_classify_per);
                }
                shoppingIv.setSelected(true);
                break;
            case 5:
            case 6:
                if (mineColor!=null) {
                    displayImage(getContext(), Util.getD2cPicUrl(mineColor[1])+Constants.MY_SUFFIX, mineIv,0,0);
                } else {
                    mineIv.setImageResource(R.mipmap.icon_tab_mine_per);
                }
                mineIv.setSelected(true);
                break;
        }
    }

    private void setUnSelect() {
        if (mainColor!=null){
            displayImage(getContext(),Util.getD2cPicUrl(mainColor[0])+Constants.MY_SUFFIX, mainIv,0,0);
        }else {
            mainIv.setImageResource(R.mipmap.icon_tab_home_nor);
        }
        if (specialColor!=null){
            displayImage(getContext(), Util.getD2cPicUrl(specialColor[0])+ Constants.MY_SUFFIX, specialIv, R.mipmap.icon_tab_activity_nor, R.mipmap.icon_tab_activity_nor);
        }else {
            specialIv.setImageResource(R.mipmap.icon_tab_activity_nor);
        }
        if (fashionColor!=null){
            displayImage(getContext(), Util.getD2cPicUrl(fashionColor[0])+Constants.MY_SUFFIX, fashionIv, R.mipmap.icon_tab_show_nor, R.mipmap.icon_tab_show_nor);
        }else {
            fashionIv.setImageResource(R.mipmap.icon_tab_show_nor);
        }
        if (shopColor!=null){
            displayImage(getContext(), Util.getD2cPicUrl(shopColor[0])+Constants.MY_SUFFIX, shoppingIv, R.mipmap.icon_tab_classify_nor, R.mipmap.icon_tab_classify_nor);
        }else {
            shoppingIv.setImageResource(R.mipmap.icon_tab_classify_nor);
        }
        if (mineColor!=null){
            displayImage(getContext(), Util.getD2cPicUrl(mineColor[0])+Constants.MY_SUFFIX, mineIv, R.mipmap.icon_tab_mine_nor, R.mipmap.icon_tab_mine_nor);
        }else {
            mineIv.setImageResource(R.mipmap.icon_tab_mine_nor);
        }
        mainIv.setSelected(false);
        specialIv.setSelected(false);
        fashionIv.setSelected(false);
        shoppingIv.setSelected(false);
        mineIv.setSelected(false);
    }

    private void displayImage(Context context,String url,ImageView iv,int loadingResId, int failOrEmptyResId){
        if (isGif(url)){
            Glide.with(context)
                    .load(url.substring(0,url.length()-6))
                    .asBitmap()
                    .placeholder(loadingResId)
                    .error(failOrEmptyResId)
                    .dontAnimate()
                    .into(iv);
        }else {
            Glide.with(context).load(url).asBitmap().placeholder(loadingResId).error(failOrEmptyResId).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    iv.getLayoutParams().height=resource.getHeight()* (ScreenUtil.getDisplayWidth()/5)/resource.getWidth();
                    iv.setImageBitmap(resource);
                }
            });
        }
    }

    private boolean isGif(String url){
        if (Util.isEmpty(url)){
            return false;
        }
        boolean is = false;
        int index = url.lastIndexOf(".");
        int length = url.length();
        if (index > 0&&(index+4)<=length) {
            String suffix = url.substring((index + 1), (index + 4));
            if (suffix.equals("gif")) {
                return true;
            }
        }
        return is;
    }

    @Override
    public void chaneFragment(int type) {
        if(type==0){ //切换为买手
            switchFragment(true,6);
        }else{ //切换为普通
            switchFragment(true,5);
        }
    }

    public interface TabSelectListener {
        boolean onTabSelected(int index);

        void onTabReselected(int index);
    }

    public void setSelectListener(TabSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public int getLastShowIndex() {
        return lastShowIndex;
    }

    public void setMainColor(String[] mainColor) {
        this.mainColor = mainColor;
    }

    public void setSpecialColor(String[] specialColor) {
        this.specialColor = specialColor;
    }

    public void setFashionColor(String[] fashionColor) {
        this.fashionColor = fashionColor;
    }

    public void setShopColor(String[] shopColor) {
        this.shopColor = shopColor;
    }

    public void setMineColor(String[] mineColor) {
        this.mineColor = mineColor;
    }
}
