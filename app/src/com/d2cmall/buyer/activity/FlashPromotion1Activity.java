package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.fragment.AloneFragment;
import com.d2cmall.buyer.fragment.FlashBuyFragment;
import com.d2cmall.buyer.fragment.RobBigCardFragment;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/4/28.
 */

public class FlashPromotion1Activity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.line)
    View line;
    @Bind(R.id.flash_tv)
    ImageView flashTv;
    @Bind(R.id.alone_tv)
    ImageView aloneTv;
    @Bind(R.id.brand_tv)
    ImageView brandTv;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_share)
    ImageView ivShare;

    private RobBigCardFragment robBigCardFragment;
    private FlashBuyFragment flashBuyFragment;
    private FragmentManager fragmentManager;
    private AloneFragment aloneFragment;
    private Fragment lastShowFragment;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_promotion_layout);
        ButterKnife.bind(this);
        initFragment();
        int index=getIntent().getIntExtra("index",0);
        if (index!=0){
            changeFragment(index);
        }
    }

    private void initFragment() {
        //初始化两个fragment,一个限时购,一个抢大牌
        flashBuyFragment = new FlashBuyFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, flashBuyFragment);
        ft.show(flashBuyFragment);
        ft.commit();
        lastShowFragment=flashBuyFragment;
    }

    private void changeFragment(int index) {
        //传入index判断切换哪个fragment
        if (currentIndex == index) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (lastShowFragment!=null){
            ft.hide(lastShowFragment);
        }
        if (index == 0) {
            brandTv.setImageResource(R.mipmap.icon_qiangdapai_unsel);
            aloneTv.setImageResource(R.mipmap.icon_gupin_unsel);
            flashTv.setImageResource(R.mipmap.icon_xianshigou_sel);
            ft.show(flashBuyFragment);
            lastShowFragment=flashBuyFragment;
            tvTitle.setText("限时购");
        }else if (index==1){
            if (aloneFragment==null){
                aloneFragment = new AloneFragment();
                ft.add(R.id.fragment_container, aloneFragment);
            }
            brandTv.setImageResource(R.mipmap.icon_qiangdapai_unsel);
            flashTv.setImageResource(R.mipmap.icon_xianshigou_unsel);
            aloneTv.setImageResource(R.mipmap.icon_gupin_sel);
            ft.show(aloneFragment);
            lastShowFragment=aloneFragment;
            tvTitle.setText("孤品专场");
        } else {
            if(robBigCardFragment==null){
                robBigCardFragment = new RobBigCardFragment();
                ft.add(R.id.fragment_container, robBigCardFragment);
            }
            brandTv.setImageResource(R.mipmap.icon_qiangdapai_sel);
            flashTv.setImageResource(R.mipmap.icon_xianshigou_unsel);
            aloneTv.setImageResource(R.mipmap.icon_gupin_unsel);
            ft.show(robBigCardFragment);
            lastShowFragment=robBigCardFragment;
            tvTitle.setText("抢大牌");
        }
        currentIndex = index;
        ft.commit();
    }


    @OnClick({R.id.iv_back, R.id.flash_tv, R.id.alone_tv,R.id.brand_tv, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.flash_tv:
                changeFragment(0);
                break;
            case R.id.alone_tv:
                changeFragment(1);
                break;
            case R.id.brand_tv:
                changeFragment(2);
                break;
            case R.id.iv_share:
                if (Util.loginChecked(FlashPromotion1Activity.this, 999)) {
                    share();
                }
                break;
        }
    }

    private void share() {
        SharePop sharePop = new SharePop(this);
        sharePop.setTitle("D2C快抢 限时秒杀");
        sharePop.setDescription("全球好设计  整点限时限量秒杀，先到先得！");
        sharePop.setImage(Util.getD2cPicUrl("/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3", 100, 100), false);
        sharePop.setImage(Util.getD2cPicUrl("/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3", 360, 500), true);
        if (currentIndex == 0) {
            sharePop.setWebUrl(Constants.SHARE_URL + "/flashpromotion/product/session");
        } else {
            sharePop.setWebUrl(Constants.SHARE_URL + "/flashpromotion/brand/session");
        }
        sharePop.show(getWindow().getDecorView());
    }
}
