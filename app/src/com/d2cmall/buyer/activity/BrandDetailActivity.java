package com.d2cmall.buyer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BrandFollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BrandDetailBean;
import com.d2cmall.buyer.bean.BrandFollowBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.fragment.BrandFashionFragment;
import com.d2cmall.buyer.fragment.BrandPromotionFragment;
import com.d2cmall.buyer.fragment.DesinerWebFragment;
import com.d2cmall.buyer.fragment.ProductListFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.BrandSummeryPop;
import com.d2cmall.buyer.widget.LimitViewPager;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.TimelineTabView;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.BRAND_DETAIL_URL;
import static com.d2cmall.buyer.Constants.BRAND_FOLLOW_URL;
/*
* 品牌详情页面
* */

public class BrandDetailActivity extends BaseActivity {

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    TimelineTabView mainView, workView, showView, promotionView;
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.banner_rl)
    RelativeLayout bannerRl;
    @Bind(R.id.tv_name)
    TextView tvName;
    //开启消息推送行为节点
    @Bind(R.id.img_focus)
    ShowPopImageView imgFocus;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.main_content)
    RelativeLayout mainContent;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    ImageView tvTitle;
    @Bind(R.id.share_button)
    ImageView shareButton;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.coll_layout)
    CollapsingToolbarLayout collLayout;
    @Bind(R.id.brand_list_tabLayout)
    TabLayout brandListTabLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.viewpager)
    LimitViewPager viewpager;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.iv_start_back)
    ImageView ivStartBack;
    @Bind(R.id.iv_start_share)
    ImageView ivStartShare;
    @Bind(R.id.start_title)
    RelativeLayout startTitle;
    @Bind(R.id.iv_top_bg)
    ImageView ivTopBg;
    @Bind(R.id.iv_top_cover)
    ImageView ivTopCover;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.tv_focus_tip)
    TextView tvFocusTip;

    private ArrayList<Fragment> fragmentList;
    private int deltaValue;
    private int startValue;
    private Animation imgAnimation;
    private DesinerWebFragment categoryDetailFragment0;
    private ProductListFragment productListFragment;
    private BrandFashionFragment categoryDetailFragment2;
    private BrandPromotionFragment brandPromotionFragment;
    private ArrayList<String> titles;
    private TabPagerAdapter adapter;
    private BrandDetailBean detailBean;
    private boolean isOpen = false;
    private int id;
    private int lineCount;
    private Context context = this;
    private int state;
    private int EXPANDED = 1995;
    private int COLLAPSED = 11;
    private int INTERNEDIATE = 23;
    private BrandFollowBean brandFollowBean;
    public boolean popIsShow = false;
    private SharePop sharePop1;
    Handler handler = new Handler();
    private boolean hasMain = false;
    private byte[] miniData;
    private BrandSummeryPop brandSummeryPop;
    private String selectType;

    public void changeTabNum(int num) {
        if (workView != null && brandListTabLayout != null) {
            workView.setText("作品" + "(" + num + ")");
            brandListTabLayout.getTabAt(hasMain ? 1 : 0).setCustomView(workView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
        ButterKnife.bind(this);
        selectType = getIntent().getStringExtra("selectType");
        doBusiness();
    }

    public void doBusiness() {
        id = getIntent().getIntExtra("id", 0);
        fragmentList = new ArrayList<>();
        final Animation mShowAction = new AlphaAnimation(0f, 1f);
        mShowAction.setDuration(350);
        final Animation mHiddenAction = new AlphaAnimation(1f, 0f);
        mHiddenAction.setDuration(350);
        titles = new ArrayList<>();
        titles.add("作品");
        titles.add("优惠活动");
        titles.add("买家秀");
        viewpager.setCanScroll(false);
        viewpager.setOffscreenPageLimit(4);
        progressBar.setVisibility(View.VISIBLE);
        initData();
        //监听整个协调者布局的状态，控制标题栏的变化
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != EXPANDED) {
                        state = EXPANDED;//修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != COLLAPSED) {
                        startTitle.startAnimation(mHiddenAction);
                        startTitle.setVisibility(View.GONE);
//                        if (imgPic != null) {
//                            imgPic.pauseScroll();
//                        }
                        title.startAnimation(mShowAction);
                        title.setVisibility(View.VISIBLE);
                        state = COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != INTERNEDIATE) {
                        if (state == COLLAPSED) {
                            startTitle.startAnimation(mShowAction);
                            startTitle.setVisibility(View.VISIBLE);
//                            if (imgPic != null) {
//                                imgPic.goOnScroll();
//                            }
                            title.startAnimation(mHiddenAction);
                            title.setVisibility(View.GONE);//由折叠变为中间状态时隐藏标题栏
                        }
                        state = INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
        //关注品牌得奖励
//        if(!D2CApplication.mSharePref.getSharePrefBoolean("hasShowFocusBrandTip",false)){
//           D2CApplication.mSharePref.putSharePrefBoolean("hasShowFocusBrandTip",true);
//            tvFocusTip.setVisibility(View.VISIBLE);
//            tvFocusTip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tvFocusTip.setVisibility(View.GONE);
//                }
//            });
//        }

    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(BRAND_FOLLOW_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandFollowBean>() {
            @Override
            public void onResponse(BrandFollowBean response) {
                brandFollowBean = response;
                List<BrandFollowBean.DataBean.AttentionsBean.ListBean> list = null;
                setView();
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                setView();
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void upLoadBehaviorEvent(BrandDetailBean brandDetailBean) {
        String url = Constants.API_URL + Constants.POST_BEHAVIOR_EVENT_URL;
        JSONObject tmpObj = null;
        tmpObj = new JSONObject();
        JSONObject data = null;
        data = new JSONObject();
        try {
            tmpObj.put("event", "打开店铺主页");
            data.put("targetId", id);
            data.put("targetImg", brandDetailBean.getData().getBrand().getHeadPic());//0是商品活动,1是订单活动
            data.put("targetName", brandDetailBean.getData().getBrand().getName());
            tmpObj.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String personInfos = tmpObj.toString(); // 将JSONArray转换得到String
        Charset chrutf = Charset.forName("UTF-8");
        String params = new String(personInfos.getBytes(), chrutf);
        try {
            HttpUtils.doPostAsyn(url, params, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(final String result) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    private void initData() {
        //先拉取品牌详情数据
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(BRAND_DETAIL_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandDetailBean>() {
            @Override
            public void onResponse(final BrandDetailBean brandDetailBean) {
                detailBean = brandDetailBean;
                upLoadBehaviorEvent(brandDetailBean);//上传埋点信息
                if (brandDetailBean.getData().getBrand() == null) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    coordinatorLayout.setVisibility(View.GONE);
                    errorImage.setVisibility(View.VISIBLE);
                } else {
                    //拉去成功以及数据不为空继续拉取关注该品牌的人数据
                    loadData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                coordinatorLayout.setVisibility(View.GONE);
                errorImage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setView() {
        //如果activity没有在退出，则设置布局
        if (!isFinishing()) {
            if (detailBean == null) {
                return;
            }
            if (fragmentList.size() == 0) {
                productListFragment = ProductListFragment.newInstance(id);
                if(!Util.isEmpty(selectType)){
                    productListFragment = ProductListFragment.newInstance(id,selectType);
                }else{
                    productListFragment = ProductListFragment.newInstance(id);
                }
                fragmentList.add(productListFragment);
                brandPromotionFragment = BrandPromotionFragment.newInstance((long) id);
                fragmentList.add(brandPromotionFragment);
                categoryDetailFragment2 = BrandFashionFragment.newInstance((long) id);
                fragmentList.add(categoryDetailFragment2);
            }
            if (detailBean.getData().getBrand().getAppIntro() != null && !Util.isEmpty(detailBean.getData().getBrand().getAppIntro())) {
                if (fragmentList.size() < 4) {
                    categoryDetailFragment0 = DesinerWebFragment.newInstance(detailBean.getData().getBrand().getAppIntro());
                    fragmentList.add(0, categoryDetailFragment0);
                    titles.add(0, "首页");
                    hasMain = true;
                }
            }
            if (viewpager.getAdapter() != null) {
                adapter.notifyDataSetChanged();
            } else {
                adapter = new TabPagerAdapter(getSupportFragmentManager(), titles, fragmentList);
                viewpager.setAdapter(adapter);
                brandListTabLayout.setupWithViewPager(viewpager);
                if (fragmentList.size() == 4) {
                    if (brandListTabLayout != null) {
                        if (brandListTabLayout.getTabAt(0) != null) {
                            mainView = new TimelineTabView(BrandDetailActivity.this);
                            mainView.setText("首页");
                            brandListTabLayout.getTabAt(0).setCustomView(mainView);
                        }
                        if (brandListTabLayout.getTabAt(1) != null) {
                            workView = new TimelineTabView(BrandDetailActivity.this, true);
                            //workView.setData(R.mipmap.ic_arrow_down);
                            workView.setText("作品");
                            brandListTabLayout.getTabAt(1).setCustomView(workView);
                        }
                        if (brandListTabLayout.getTabAt(2) != null) {
                            promotionView = new TimelineTabView(BrandDetailActivity.this);
                            promotionView.setText("优惠活动");
                            brandListTabLayout.getTabAt(2).setCustomView(promotionView);
                        }
                        if (brandListTabLayout.getTabAt(3) != null) {
                            showView = new TimelineTabView(BrandDetailActivity.this);
                            showView.setText("买家秀");
                            brandListTabLayout.getTabAt(3).setCustomView(showView);
                        }
                        setIndicator(brandListTabLayout, 10, 10);
                    }
                } else {
                    if (brandListTabLayout != null) {
                        if (brandListTabLayout.getTabAt(0) != null) {
                            workView = new TimelineTabView(BrandDetailActivity.this, true);
                            //workView.setData(R.mipmap.ic_arrow_down);
                            workView.setText("作品");
                            //workView.isThisItem = true;
                            brandListTabLayout.getTabAt(0).setCustomView(workView);
                        }
                        if (brandListTabLayout.getTabAt(1) != null) {
                            promotionView = new TimelineTabView(BrandDetailActivity.this);
                            promotionView.setText("优惠活动");
                            brandListTabLayout.getTabAt(1).setCustomView(promotionView);
                        }
                        if (brandListTabLayout.getTabAt(2) != null) {
                            showView = new TimelineTabView(BrandDetailActivity.this);
                            showView.setText("买家秀");
                            brandListTabLayout.getTabAt(2).setCustomView(showView);
                        }
                        setIndicator(brandListTabLayout, 16, 16);
                    }
                }

            }
            switch (detailBean.getData().getBrand().getAttentioned()) {
                case 0:
                    imgFocus.setVisibility(View.VISIBLE);
                    imgFocus.setImageResource(R.mipmap.button_follow);
                    break;
                case 1:
                    imgFocus.setVisibility(View.VISIBLE);
                    imgFocus.setImageResource(R.mipmap.button_followed);
                    break;
            }
            tvName.setText(detailBean.getData().getBrand().getName() + " >");

            if (!isFinishing()) {
                UniversalImageLoader.displayImage(this, detailBean.getData().getBrand().getHeadPic(), imgHead);
                UniversalImageLoader.displayImage(this, detailBean.getData().getBrand().getSignPics().get(0), ivTopBg);
            }
            if (detailBean.getData().getBrand().getVideo() != null) {
                String videoUrl = detailBean.getData().getBrand().getVideo();
                UniversalImageLoader.displayImage(this, detailBean.getData().getBrand().getVideoPic(), ivTopBg);
                ivPlay.setVisibility(View.VISIBLE);
                ivTopBg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (detailBean != null && detailBean.getData().getBrand().getVideo() != null) {
                           Intent intent = new Intent(BrandDetailActivity.this, SimplePlayActivity.class);
                            intent.putExtra("url", detailBean.getData().getBrand().getVideo());
                            BrandDetailActivity.this.startActivity(intent);
                        }
                    }
                });
                ViewGroup.LayoutParams layoutParams = bannerRl.getLayoutParams();
                layoutParams.height = ScreenUtil.dip2px(248);
                bannerRl.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams1 = ivTopCover.getLayoutParams();
                layoutParams1.height = ScreenUtil.dip2px(248);
                ivTopCover.setLayoutParams(layoutParams1);
                ivTopCover.setBackgroundResource(R.mipmap.pic_shoppage_bgcover_long);
            } else {
                ivTopCover.setBackgroundResource(R.mipmap.pic_shoppage_bgcover);
            }
            if (!isFinishing()) {
                UniversalImageLoader.displayImage(this, Constants.IMG_HOST + detailBean.getData().getBrand().getHeadPic(), tvTitle);
            }
            tvContent.setText(getString(R.string.label_all_focus, detailBean.getData().getBrand().getFans()));
        }
    }

    private void setPopIsShow() {
        //每次弹出pop时延迟.025秒弹出，避免布局还未折叠，pop就弹出
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (popIsShow) {
                    workView.startAnimation(1);
                    productListFragment.closePop();
                } else {
                    workView.isFirstIn = true;
                    workView.startAnimation(0);
                    productListFragment.showPop();
                }
            }
        }, 250);
    }

    public void onDismiss() {
        if (workView != null) {
            //workView.startAnimation(1);
        }
    }


    @OnClick({R.id.iv_start_back, R.id.iv_start_share, R.id.tv_name, R.id.img_focus, R.id.iv_back, R.id.img_head, R.id.share_button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_start_back:
                finish();
                break;
            case R.id.iv_start_share:
                share();
                break;
            case R.id.tv_reset:
                break;
            case R.id.fans_layout:
                intent = new Intent(this, LikeThisActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("brand", brandFollowBean);
                intent.putExtra("brandId", id);
                startActivity(intent);
                break;
            case R.id.tv_name://品牌简介
                if (brandSummeryPop == null) {
                    brandSummeryPop = new BrandSummeryPop(this, detailBean.getData().getBrand().getIntro());
                }
                brandSummeryPop.show(getWindow().getDecorView());
                break;
            case R.id.img_focus:
                if (Util.loginChecked(BrandDetailActivity.this, 123)) {
                    if (detailBean == null) {
                        return;
                    }
                    if(tvFocusTip.getVisibility()==View.VISIBLE){
                        tvFocusTip.setVisibility(View.GONE);
                    }
                    BrandFollowApi api = new BrandFollowApi();
                    if (detailBean.getData().getBrand().getAttentioned() > 0) {//关注了
                        api.setInterPath(Constants.FOLLOW_BRAND_DELETE);
                        api.setBrandId(detailBean.getData().getBrand().getId());
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                imgFocus.setImageResource(R.mipmap.button_follow);
                                detailBean.getData().getBrand().setAttentioned(0);
                                Util.showToast(context, "取消关注成功");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    } else {
                        SimpleApi api1 = new SimpleApi();
                        api1.setMethod(BaseApi.Method.POST);
                        api1.setInterPath(String.format(Constants.FOLLOW_BRAND_URL, detailBean.getData().getBrand().getId()));
                        D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                imgFocus.setImageResource(R.mipmap.button_followed);
                                detailBean.getData().getBrand().setAttentioned(1);
                                Util.showToast(context, "您关注该品牌设计师的动态将会在时尚圈-关注显示");
                                imgFocus.showMsgPop(BrandDetailActivity.this, BrandDetailActivity.this.getString(R.string.label_pop_focus_brand));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });

                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.img_head:
                break;
            case R.id.share_button:
                share();
                break;
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
    public void onWbShareSuccess() {
        super.onWbShareSuccess();
        if (sharePop1 != null) {
            sharePop1.shareOut();
        }
    }

    private void share() {
        if (detailBean == null) {
            return;
        }
        sharePop1 = new SharePop(this);
        sharePop1.setTitle(detailBean.getData().getBrand().getName());
        if (detailBean.getData().getBrand().getSignPics().size() > 0) {
            sharePop1.setImage(Util.getD2cPicUrl(detailBean.getData().getBrand().getSignPics().get(0), 100, 100), false);
            sharePop1.setImage(Util.getD2cPicUrl(detailBean.getData().getBrand().getSignPics().get(0), 360, 500), true);
        }
        sharePop1.setDescription(detailBean.getData().getBrand().getName());
        sharePop1.setProductShare(true);
        if (Session.getInstance().getUserFromFile(this) != null && Session.getInstance().getUserFromFile(this).getPartnerId() != 0) {
            sharePop1.setMiniProjectPath("/pages/brand/detail?id=" + id + "&parent_id=" + Session.getInstance().getUserFromFile(this).getPartnerId());
        } else {
            sharePop1.setMiniProjectPath("/pages/brand/detail?id=" + id);
        }
        sharePop1.setMiniImage(Util.getD2cPicUrl(detailBean.getData().getBrand().getIntroPic(), 250, 250), false, 0);
        sharePop1.setMiniWebUrl(Constants.SHARE_URL + "/showroom/designer/" + id);
        sharePop1.setWebUrl(Constants.SHARE_URL + "/showroom/designer/" + id);
        if (!Util.isEmpty(detailBean.getData().getBrand().getBackgroundUrl())) {
            sharePop1.setPoster(getPosterView());
            sharePop1.setBgImageUrl(detailBean.getData().getBrand().getBackgroundUrl());
            sharePop1.addPoster();
        }
        sharePop1.show(getWindow().getDecorView());
    }

    private Poster getPosterView() {
        final Poster poster = new Poster();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_page_poster_view, new RelativeLayout(this), false);
        final ImageView bgIv = (ImageView) view.findViewById(R.id.bg_iv);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        Glide.with(this).load(Util.getD2cPicUrl(detailBean.getData().getBrand().getBackgroundUrl(), ScreenUtil.getDisplayWidth(), ScreenUtil.getDisplayHeight())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.baseMap = true;
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                rl.width = resource.getWidth();
                bgIv.setImageBitmap(resource);
            }
        });
        poster.productMap = true;
        ImageView rq = (ImageView) view.findViewById(R.id.rq);
        rq.setImageBitmap(BitmapUtils.createWhiteQRImage(Constants.SHARE_URL + "/showroom/designer/" + id, ScreenUtil.dip2px(62), ScreenUtil.dip2px(62), 0));
        poster.posterView = view;
        return poster;
    }

    private static class TabPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> mTitles;
        private List<Fragment> fragmentList;

        public TabPagerAdapter(FragmentManager fm, ArrayList<String> mTitles, List<Fragment> fragmentList) {
            super(fm);
            this.mTitles = mTitles;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        public void setTitles(ArrayList<String> titles) {
            if (titles != null && titles.size() > 0) {
                mTitles.clear();
                mTitles.addAll(titles);
            }
        }
    }
}
