package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.TopicDetailApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.TopicDetailBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.FashionSubFragment;
import com.d2cmall.buyer.fragment.TopicShowFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.SocialPop;
import com.d2cmall.buyer.widget.VideoPop;
import com.flyco.tablayout.SlidingTabLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.IMG_HOST;
import static com.d2cmall.buyer.Constants.TOPIC_DETAIL_URL;

/**
 * Created by rookie on 2017/8/11.
 * 话题详情页面
 */

public class TopicDetailActivity extends BaseActivity {

    @Bind(R.id.iv_topic_pic)
    ImageView ivTopicPic;
    @Bind(R.id.tv_topic_name)
    TextView tvTopicName;
    @Bind(R.id.tv_join_people_num)
    TextView tvJoinPeopleNum;
    @Bind(R.id.tv_topic_info)
    TextView tvTopicInfo;
    @Bind(R.id.tv_look_more)
    TextView tvLookMore;
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.ll_open_more)
    LinearLayout llOpenMore;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.tab)
    SlidingTabLayout tab;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    @Bind(R.id.tv_wanna_join)
    TextView tvWannaJoin;
    @Bind(R.id.iv_start_back)
    ImageView ivStartBack;
    @Bind(R.id.iv_start_share)
    ImageView ivStartShare;
    @Bind(R.id.start_title)
    RelativeLayout startTitle;
    private List<BaseFragment> fragmentList;
    private String[] strings = {"热门", "最新"};
    Animation mShowAction, mHiddenAction, botomShowAction, bottomHideAction;
    private boolean isAnimationFinish = true;
    private int state;
    private int EXPANDED = 1995;
    private int COLLAPSED = 11;
    private int INTERNEDIATE = 23;

    private boolean isOpen = false;
    private long id;
    private int lineCount;
    private int deltaValue;
    private int startValue;
    private Animation imgAnimation;
    private boolean canReflash = false;
    private UserBean.DataEntity.MemberEntity user;
    private SocialPop socialPop;
    TopicDetailBean topicDetailBean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        id = getIntent().getLongExtra("id", 0);
        ButterKnife.bind(this);
        doBusiness();
        user = Session.getInstance().getUserFromFile(this);
        tvWannaJoin.setVisibility(View.VISIBLE);
        loadData();
    }

    private void loadData() {
        TopicDetailApi api = new TopicDetailApi();
        api.setInterPath(String.format(TOPIC_DETAIL_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TopicDetailBean>() {
            @Override
            public void onResponse(TopicDetailBean topicDetailBean) {
                topicDetailBean2 = topicDetailBean;
                setDataToView(topicDetailBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(TopicDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.tv_look_more, R.id.tv_wanna_join, R.id.iv_start_back, R.id.iv_start_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_start_back:
                finish();
                break;
            case R.id.iv_start_share:
                share();
                break;
            case R.id.tv_wanna_join:
                if (Util.loginChecked(this, Constants.Login.EXPLORE_CAMERA_LOGIN)) {
                    user = Session.getInstance().getUserFromFile(this);
                    if (user != null && topicDetailBean2 != null) {
                        if (user.getHasNickName() && !Util.isEmpty(user.getRealHead())) {
                            TopicBean.DataBean.TopicsBean.ListBean topicBean = new TopicBean.DataBean.TopicsBean.ListBean();
                            topicBean.setId(topicDetailBean2.getData().getTopic().getId());
                            topicBean.setTitle(topicDetailBean2.getData().getTopic().getTitle());
                            topicBean.setContent(topicDetailBean2.getData().getTopic().getContent());
                            topicBean.setPic(topicDetailBean2.getData().getTopic().getPic());
                            topicBean.setShareCount(topicDetailBean2.getData().getTopic().getShareCount());
                            if (FashionSubFragment.isUpload) {
                                VideoPop videoPop = new VideoPop(TopicDetailActivity.this);
                                videoPop.show(getWindow().getDecorView());
                            }else{
                                Intent intent = new Intent(TopicDetailActivity.this, VideoRecordActivity.class);
                                intent.putExtra("channel","social");
                                intent.putExtra("topic",topicBean);
                                startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(TopicDetailActivity.this, CompleteInfoActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                        }
                    } else {
                        Util.showToast(this, "系统繁忙,请稍后再试!");
                    }
                }
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                share();
                break;
            case R.id.tv_look_more:
                startValue = tvTopicInfo.getHeight();
                if (isOpen) {
                    deltaValue = tvTopicInfo.getLineHeight() * 4 - startValue;
                    tvLookMore.setText(R.string.desc_spread);
                    imgAnimation = AnimationUtils.loadAnimation(TopicDetailActivity.this, R.anim.arrow_down);
                    isOpen = false;
                } else {
                    deltaValue = tvTopicInfo.getLineHeight() * (lineCount) - startValue;
                    tvLookMore.setText(R.string.desc_shrinkup);
                    imgAnimation = AnimationUtils.loadAnimation(TopicDetailActivity.this, R.anim.arrow_up);
                    isOpen = true;
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        tvTopicInfo.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }
                };
                animation.setDuration(350);
                tvTopicInfo.startAnimation(animation);
                ivArrow.startAnimation(imgAnimation);
                break;
        }
    }

    private void share() {
        if (topicDetailBean2 == null) {
            Util.showToast(this, "系统繁忙,请稍后再试!");
            return;
        }
        SharePop sharePop = new SharePop(this, false);
        if (topicDetailBean2 != null && topicDetailBean2.getData().getTopic().getTitle() != null) {
            sharePop.setTitle(topicDetailBean2.getData().getTopic().getTitle());
        }
        if (topicDetailBean2 != null && topicDetailBean2.getData().getTopic().getPic() != null) {
            sharePop.setImage(Util.getD2cPicUrl(topicDetailBean2.getData().getTopic().getPic(), 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(topicDetailBean2.getData().getTopic().getPic(), 360, 500), true);
        }
        if (topicDetailBean2 != null && topicDetailBean2.getData().getTopic() != null) {
            sharePop.setWebUrl(Constants.SHARE_URL + "/membershare/topic/" + topicDetailBean2.getData().getTopic().getId());
        }
        sharePop.setDescription("我在D2C发现了一个好东东，现在分享给你，赶紧打开看看吧.");
        sharePop.show(getWindow().getDecorView());
    }

    private void setDataToView(TopicDetailBean topicDetailBean) {
        TopicDetailBean.DataBean.TopicBean topicInfo = topicDetailBean.getData().getTopic();
        UniversalImageLoader.displayImage(this, IMG_HOST + topicInfo.getPic(), ivTopicPic, R.mipmap.ic_logo_empty8, R.mipmap.ic_logo_empty8);
        tvTopicName.setText("#" + topicInfo.getTitle() + "#");
        nameTv.setText("#" + topicInfo.getTitle() + "#");
        if (!Util.isEmpty(topicInfo.getContent())) {
            tvTopicInfo.setText(Html.fromHtml(topicInfo.getContent()));
            tvTopicInfo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    lineCount = tvTopicInfo.getLineCount();
                    if (lineCount <= 4) {
                        tvLookMore.setVisibility(View.GONE);
                        ivArrow.setVisibility(View.GONE);
                        llOpenMore.setVisibility(View.GONE);
                    }
                    tvTopicInfo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            tvLookMore.setVisibility(View.GONE);
            ivArrow.setVisibility(View.GONE);
            llOpenMore.setVisibility(View.GONE);
        }
        tvJoinPeopleNum.setText(topicInfo.getShareCount() + "人参与");
    }


    public void doBusiness() {
        fragmentList = new ArrayList<>();
        mShowAction = new AlphaAnimation(0f, 1f);
        mShowAction.setDuration(350);
        botomShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        botomShowAction.setDuration(350);
        botomShowAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                isAnimationFinish = false;
            }
        });
        mHiddenAction = new AlphaAnimation(1f, 0f);
        bottomHideAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        bottomHideAction.setDuration(350);
        bottomHideAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                isAnimationFinish = false;
            }
        });
        initFragment();
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tab.setViewPager(viewpager);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    canReflash = true;
                    if (state != EXPANDED) {
                        state = EXPANDED;//修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    canReflash = false;
                    if (state != COLLAPSED) {
                        //title.startAnimation(mShowAction);
                        startTitle.startAnimation(mHiddenAction);
                        startTitle.setVisibility(View.GONE);
                        titleLayout.startAnimation(mShowAction);
                        titleLayout.setVisibility(View.VISIBLE);//隐藏标题栏
                        state = COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    canReflash = false;
                    if (state != INTERNEDIATE) {
                        if (state == COLLAPSED) {
                            //title.startAnimation(mHiddenAction);
                            startTitle.startAnimation(mShowAction);
                            startTitle.setVisibility(View.VISIBLE);
                            titleLayout.startAnimation(mHiddenAction);
                            titleLayout.setVisibility(View.GONE);//由折叠变为中间状态时隐藏标题栏
                        }
                        state = INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    public boolean getCanRefresh() {
        return canReflash;
    }

    public void showView() {
        if (isAnimationFinish) {
            tvWannaJoin.startAnimation(botomShowAction);
        }
        tvWannaJoin.setVisibility(View.VISIBLE);
    }

    public void hideView() {
        if (isAnimationFinish) {
            tvWannaJoin.startAnimation(bottomHideAction);
        }
        tvWannaJoin.setVisibility(View.GONE);
    }

    private void initFragment() {
        fragmentList.add(TopicShowFragment.newInstance(id, false));
        fragmentList.add(TopicShowFragment.newInstance(id, true));
    }


    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
