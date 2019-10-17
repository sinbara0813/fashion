package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CustomTabAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.HeaderViewPagerFragment;
import com.d2cmall.buyer.bean.UpTopBean;
import com.d2cmall.buyer.bean.VideoSource;
import com.d2cmall.buyer.fragment.BrandProductFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ReboundScrollView;
import com.d2cmall.buyer.widget.VideoBanner1;
import com.d2cmall.buyer.widget.headerViewPager.HeaderViewPager;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

import static com.d2cmall.buyer.util.Util.INFINITE;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/26 16:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandUpdateActivity extends BaseActivity implements HeaderViewPager.OnScrollListener{

    @Bind(R.id.banner)
    VideoBanner1 banner;
    @Bind(R.id.sub_name)
    TextView subName;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.page_tag)
    TextView pageTag;
    @Bind(R.id.brand_more_iv)
    ImageView brandMoreIv;
    @Bind(R.id.brand_content_ll)
    LinearLayout brandContentLl;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.cart_iv)
    ImageView cartIv;
    @Bind(R.id.back_fl)
    FrameLayout backFl;
    @Bind(R.id.back_iv1)
    ImageView backIv1;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.cart_iv1)
    ImageView cartIv1;
    @Bind(R.id.title_fl)
    FrameLayout titleFl;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.scrollableLayout)
    HeaderViewPager scrollableLayout;

    private CustomTabAdapter tabAdapter;
    private boolean isPlayed;

    private QBadgeView cartNum;
    private QBadgeView cartNum1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_update);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        loadData();
    }

    private void loadData() {
        loadTop();
    }

    private void loadTop() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/page/newup/other");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UpTopBean>() {
            @Override
            public void onResponse(UpTopBean response) {
                if (response != null) {
                    if (response.getData().getAdResource() != null && response.getData().getAdResource().getPics().length > 0) {
                        setBanner(response.getData().getAdResource());
                    }
                    setTopData(response);
                }
            }
        });
    }

    private void setBanner(final UpTopBean.DataBean.AdResourceBean adResource) {
        if (!Util.isEmpty(adResource.getVideo())&&!Util.isEmpty(adResource.getVideoPic())){
            String[] pics=new String[adResource.getPics().length+1];
            String[] picUrl=new String[adResource.getPics().length+1];
            pics[0]=adResource.getVideoPic();
            picUrl[0]="";
            for (int i=0;i<adResource.getPics().length;i++){
                pics[i+1]=adResource.getPics()[i];
                picUrl[i+1]=adResource.getPicsUrl()[i];
            }
            adResource.setPics(pics);
            adResource.setPicsUrl(picUrl);
        }
        final int size = adResource.getPics().length;
        final List<VideoSource> source = new ArrayList<VideoSource>();
        for (int i = 0; i < size; i++) {
            VideoSource videoSource = new VideoSource();
            if (i == 0 && !Util.isEmpty(adResource.getVideo())) {
                videoSource.videoUrl = adResource.getVideo();
            }
            videoSource.picUrl = adResource.getPics()[i];
            videoSource.loading = R.mipmap.ic_default_pic;
            source.add(videoSource);
        }
        if (size > 0) {
            setPageTag(1, size, pageTag);
            if (adResource.getShotTitles().length > 0 && !Util.isEmpty(adResource.getShotTitles()[0])) {
                name.setText(adResource.getShotTitles()[0]);
            }
            if (adResource.getDescriptions().length > 0 && !Util.isEmpty(adResource.getDescriptions()[0])) {
                subName.setText(adResource.getDescriptions()[0]);
            }
        }
        banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!Util.isEmpty(source.get(position).videoUrl) && positionOffset > 0.8) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (adResource.getShotTitles().length > position) {
                    if (Util.isEmpty(adResource.getShotTitles()[position])) {
                        name.setText("");
                    } else {
                        name.setText(adResource.getShotTitles()[position]);
                    }
                }
                if (adResource.getDescriptions().length > position) {
                    if (Util.isEmpty(adResource.getDescriptions()[position])) {
                        subName.setText("");
                    } else {
                        subName.setText(adResource.getDescriptions()[position]);
                    }
                }
                if (size > 0) {
                    setPageTag(position + 1, size, pageTag);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setSource(source).setIndicatorHeight(0).setAutoScrollEnable(false).startScroll();
        banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                Util.urlAction(BrandUpdateActivity.this, adResource.getPicsUrl()[position]);
            }
        });
    }

    private void setPageTag(int index, int size, TextView tv) {
        StringBuilder builder = new StringBuilder();
        builder.append(index).append(" /").append(size);

        SpannableString sb = new SpannableString(builder.toString());

        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
        sb.setSpan(sizeSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        sb.setSpan(styleSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tv.setText(sb);
    }

    private void setTopData(UpTopBean topData) {
        setBrandData(topData.getData().getBrandArray());
        setTabData(topData.getData().getTopCateArray());
    }

    private void setBrandData(final List<UpTopBean.DataBean.BrandArrayBean> brandArray) {
        int size = Math.min(brandArray.size(), 6);
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_brand_top_item, new LinearLayout(this), false);
            ImageView imageView = view.findViewById(R.id.image);
            TextView num = view.findViewById(R.id.num);
            UniversalImageLoader.displayImage(this, brandArray.get(i).getHeadPic(), imageView);
            num.setText("上新" + brandArray.get(i).getCount() + "件");
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toBrandDetailActivity(brandArray.get(finalI).getId());
                }
            });
            /*if (i<=6&&i==size-1){
                LinearLayout.LayoutParams ll= (LinearLayout.LayoutParams) view.getLayoutParams();
                ll.setMargins(0,0,ScreenUtil.dip2px(8),0);
            }*/
            brandContentLl.addView(view);
        }
        if (true) {
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(R.mipmap.pic_card);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ScreenUtil.dip2px(116), ScreenUtil.dip2px(142));
            ll.setMargins(0, 0, ScreenUtil.dip2px(8), 0);
            TextView textView=new TextView(this);
            textView.setTextColor(getResources().getColor(R.color.color_black85));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
            textView.setText("查看更多");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(textView,new LinearLayout.LayoutParams(-1,-2));

            textView=new TextView(this);
            textView.setTextColor(getResources().getColor(R.color.color_black30));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
            textView.setText("MORE");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(textView,new LinearLayout.LayoutParams(-1,-2));

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BrandUpdateActivity.this,BrandUpdateListActivity.class));
                }
            });

            brandContentLl.addView(linearLayout, ll);
        }
    }

    private void toBrandDetailActivity(int id) {
        Intent intent = new Intent(this, BrandDetailActivity.class);
        intent.putExtra("selectType","update");
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setTabData(List<UpTopBean.DataBean.TopCateArrayBean> cateList) {
        int size = cateList.size();
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fragments.add(BrandProductFragment.newInstance(cateList.get(i).getId()));
        }
        tabAdapter = new CustomTabAdapter(this, getSupportFragmentManager(), fragments, cateList);
        viewPager.setAdapter(tabAdapter);
        scrollableLayout.setCurrentScrollableContainer((HeaderViewPagerFragment)fragments.get(0));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                scrollableLayout.setCurrentScrollableContainer((HeaderViewPagerFragment)fragments.get(position));
            }
        });
        slidingTab.setViewPager(viewPager);
    }

    private void initView() {
        nameTv.setText("每日上新");
        scrollableLayout.setOnScrollListener(this);
        setCartNum();
    }

    private void setCartNum() {
        if (HomeActivity.count>0){
            if (cartNum==null){
                cartNum = (QBadgeView) new QBadgeView(this).bindTarget(cartIv).setBadgeTextSize(8, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(3, 3, true);
            }
            if (HomeActivity.count > 9) {
                cartNum.setBadgeText("9+");
            } else {
                cartNum.setBadgeNumber(HomeActivity.count);
            }
            if (cartNum1==null){
                cartNum1 = (QBadgeView) new QBadgeView(this).bindTarget(cartIv1).setBadgeTextSize(8, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(3, 3, true);
            }
            if (HomeActivity.count > 9) {
                cartNum1.setBadgeText("9+");
            } else {
                cartNum1.setBadgeNumber(HomeActivity.count);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if (Util.getNetWorkType(this) == INFINITE) {
            if (!isPlayed) {
                banner.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        banner.start();
                    }
                }, 1000);
                isPlayed = true;
            }
        }
        super.onResume();
    }

    @Override
    public void onScroll(int currentY, int maxY) {
        float alpha = 1.0f * currentY / maxY;
        titleFl.setAlpha(alpha);
    }

    @OnClick({R.id.back_iv,R.id.back_iv1,R.id.cart_iv,R.id.cart_iv1,R.id.brand_more_iv})
    public void click(View view){
       switch (view.getId()){
           case R.id.back_iv:
           case R.id.back_iv1:
               finish();
               break;
           case R.id.cart_iv:
           case R.id.cart_iv1:
               startActivity(new Intent(this,CartActivity.class));
               break;
           case R.id.brand_more_iv:
               startActivity(new Intent(this,BrandUpdateListActivity.class));
               break;
       }
    }
}
