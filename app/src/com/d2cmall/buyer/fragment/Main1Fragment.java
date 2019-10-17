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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.MessageActivity;
import com.d2cmall.buyer.activity.ScanQrCodeActivity;
import com.d2cmall.buyer.activity.Search1Activity;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.HotSearchApi;
import com.d2cmall.buyer.api.MessageListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.HotSearchBean;
import com.d2cmall.buyer.bean.MainTagBean;
import com.d2cmall.buyer.bean.MessageListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.UpListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ExpandViewPager;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import q.rorbin.badgeview.QBadgeView;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 17:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Main1Fragment extends BaseTabFragment implements View.OnClickListener {

    @Bind(R.id.title_ll)
    LinearLayout titleLl;
    @Bind(R.id.cart_iv1)
    ImageView cartIv1;
    @Bind(R.id.cart_iv)
    ImageView cartIv;
    @Bind(R.id.search_iv)
    ImageView searchIv;
    @Bind(R.id.search_tv)
    TextView searchTv;
    @Bind(R.id.message_iv)
    ImageView messageIv;
    @Bind(R.id.scan_iv)
    ImageView scanIv;
    @Bind(R.id.search_iv1)
    LinearLayout searchIv1;
    @Bind(R.id.search_ll)
    FrameLayout searchLl;
    @Bind(R.id.title_fl)
    FrameLayout titleFl;
    @Bind(R.id.title_fl_tv)
    TextView titleFlTv;
    @Bind(R.id.main_view_pager)
    ExpandViewPager viewPager;

    private List<Fragment> fragmentList;
    private List<MainTagBean.DataEntity.SubModulesEntity> headList;
    private List<String> headNameList;
    private int currentPosition;
    private HashMap<Integer, String> searchHash;
    private boolean isAnimation;
    private QBadgeView mBadgeView;
    private QBadgeView cartBade;
    private QBadgeView cartBade1;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main1, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void doBusiness() {
        fragmentList = new ArrayList<>();
        headList = new ArrayList<>();
        headNameList = new ArrayList<>();
        searchHash = new HashMap<>();
        layoutLocalData();
        loadMainHead();
        initListener();
        if (Session.getInstance().getUserFromFile(getActivity()) != null && Session.getInstance().getUserFromFile(getActivity()).getMemberId() > 0) {
            getCartNum();
            requestUnreadNumTak();
        }
    }

    private void layoutLocalData() {
        String tags = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.MAIN_BIG_TAG, "");
        if (!Util.isEmpty(tags)) {
            String[] tag = tags.split(",");
            int length = tag.length;
            for (int i = 0; i < length; i++) {
                headNameList.add(tag[i]);
            }
            animationEnd();
        }
    }

    private void loadMainHead() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_MAIN_TAG_LIST, "HOME"));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainTagBean>() {
            @Override
            public void onResponse(MainTagBean mainTagBean) {
                if (mainTagBean.getData().getSubModules() != null && mainTagBean.getData().getSubModules().size() > 0) {
                    headList.addAll(mainTagBean.getData().getSubModules());
                    int size = headList.size();
                    for (int i = 0; i < size; i++) {
                        if (headList.get(i).getTitle().equals("女士")) {
                            getHotSearch(headList.get(i).getId(), true);
                            break;
                        }
                    }
                }
                setMainHead();
                setContent();
            }
        });
    }

    private void requestUnreadNumTak() {
        MessageListApi api = new MessageListApi();
        long type6 = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6, System.currentTimeMillis());
        long type7 = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6, System.currentTimeMillis());
        api.majorTypeTime6 = type6;
        api.majorTypeTime7 = type7;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MessageListBean>() {
            @Override
            public void onResponse(MessageListBean response) {
                if (mBadgeView == null) {
                    mBadgeView = (QBadgeView) new QBadgeView(getActivity()).bindTarget(messageIv).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(0, -2, true);
                    messageIv.setId(R.id.message_iv);
                }
                if (response.getData().getUnreadCount() > 9) {
                    mBadgeView.setVisibility(View.VISIBLE);
                    mBadgeView.setBadgeText("9+");
                } else if (response.getData().getUnreadCount() > 0) {
                    mBadgeView.setVisibility(View.VISIBLE);
                    mBadgeView.setBadgeText(String.valueOf(response.getData().getUnreadCount()));
                } else if (response.getData().getUnreadCount() == 0) {
                    mBadgeView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getHotSearch(final int id, final boolean isDefault) {
        HotSearchApi api = new HotSearchApi();
        api.setInterPath(Constants.HOT_SEARCH_URL);
        api.setSubModuleId(id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<HotSearchBean>() {
            @Override
            public void onResponse(HotSearchBean response) {
                List<HotSearchBean.DataBean.MemberSearchSumListBean> hots = response.getData().getMemberSearchSumList();
                String name;
                if (hots != null && hots.size() > 0) {
                    name = hots.get(0).getDisplayName();
                } else {
                    name = "";
                }
                searchTv.setText(name);
                if (isDefault) {
                    D2CApplication.mSharePref.putSharePrefString("defaultHint", name);
                    D2CApplication.mSharePref.putSharePrefInteger("defaultId", id);
                }
                searchHash.put(id, name);
            }
        });
    }

    private void getCartNum() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CART_COUNT_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartCountBean>() {
            @Override
            public void onResponse(CartCountBean response) {
                HomeActivity.count = response.getData().getCartItemCount();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART_COUNT));
            }
        });
    }

    @OnClick({R.id.message_iv, R.id.search_iv1, R.id.scan_iv, R.id.cart_iv1, R.id.cart_iv, R.id.search_iv})
    public void click(View view) {
        if (!isVisibleToUser) {
            return;
        }
        Intent intent = null;
        switch (view.getId()) {
            case R.id.message_iv:
                boolean isLogin = Util.loginChecked(getActivity(), 1);
                if (isLogin) {
                    stat("消息", "首页-消息");
                    getActivity().startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.search_iv1:
            case R.id.search_iv:
                if (headList.size() > 0&&view.getVisibility()==View.VISIBLE) {
                    stat("搜索", "首页-" + headList.get(currentPosition).getTitle() + "-搜索");
                    intent = new Intent(getActivity(), Search1Activity.class);
                    intent.putExtra("name", searchTv.getText().toString());
                    intent.putExtra("id", headList.get(currentPosition).getId());
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.cart_iv1:
            case R.id.cart_iv:
                if (Util.loginChecked(getActivity(), 100)) {
                    stat("购物车", "首页购物车");
                    intent = new Intent(getActivity(), CartActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.scan_iv:
                stat("扫一扫", "首页-扫一扫");
                intent = new Intent(getActivity(), ScanQrCodeActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    private void setContent() {
        int size = headList.size();
        for (int i = 0; i < size; i++) {
            MainTagBean.DataEntity.SubModulesEntity subModulBean = headList.get(i);
            if (subModulBean.getIsCategory() == 1) { //有子fragment;
                if (i == 0) {
                    ViewCompat.setElevation(titleFl, 0);
                }
                fragmentList.add(MainWomenFragment.newInstance(Integer.valueOf(headList.get(i).getId()), headList.get(i).getTitle()).setStatName(headList.get(i).getTitle()));
            } else if (!Util.isEmpty(headList.get(i).getWebUrl())) {
                if (headList.get(i).getWebUrl().contains("isSpecial")) {
                    fragmentList.add(MainSiftFragment.newInstance(headList.get(i).getId()));
                } else {
                    fragmentList.add(WebFragment.newInstance(headList.get(i).getWebUrl()));
                }
            } else {
                fragmentList.add(MainManFragment.newInstance(headList.get(i).getId(), hasLive(headList.get(i).getTitle()), hasLive(headList.get(i).getTitle()), true, headList.get(i).getTitle()).setStatName("首页_" + headList.get(i).getTitle()));
            }
        }
        viewPager.setOffscreenPageLimit(size - 1);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), fragmentList, null);
        viewPager.setAdapter(tabPagerAdapter);
        if (headList.size() > 0) {
            stat("频道", "首页-" + headList.get(0).getTitle());
            titleFlTv.setText(headList.get(0).getTitle());
        }
    }

    private boolean hasLive(String name) {
        return "男士".equals(name) || "女士".equals(name);
    }

    private void setMainHead() {
        StringBuilder build = new StringBuilder();
        int size = headList.size();
        if (titleLl!=null){
            titleLl.removeAllViews();
        }
        for (int i = 0; i < size; i++) {
            build.append(headList.get(i).getTitle());
            if (i != size - 1) {
                build.append(",");
            }
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(-2, -1);
            TextView textView = new TextView(getActivity());
            if (i != size - 1) {
                textView.setPadding(0, 0, ScreenUtil.dip2px(16), 0);
            }
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(headList.get(i).getTitle());
            if (i == 0) {
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.color_black50));
                textView.setTypeface(null, Typeface.BOLD);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_black90));
                textView.setTypeface(null, Typeface.NORMAL);
                textView.setTextSize(14);
            }
            textView.setOnClickListener(this);
            titleLl.addView(textView, ll);
        }
        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.MAIN_BIG_TAG, build.toString());
    }

    public void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (!searchHash.containsKey(headList.get(position).getId())) {
                    getHotSearch(headList.get(position).getId(), false);
                } else {
                    searchTv.setText(searchHash.get(headList.get(position).getId()));
                }
                currentPosition = position;
                titleFlTv.setText(headList.get(position).getTitle());
                if (fragmentList.get(position) instanceof MainWomenFragment) {
                    ViewCompat.setElevation(titleFl, 0);
                } else {
                    ViewCompat.setElevation(titleFl, ScreenUtil.dip2px(4));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void startAnimation(final TextView view) {
        List<Animator> resultList = new LinkedList<Animator>();
        TextView firstView = (TextView) titleLl.getChildAt(0);
        if (firstView == view) {
            view.setEnabled(true);
            return;
        }
        int titleWidth = titleLl.getWidth();
        int childCount = titleLl.getChildCount();
        firstView.setTextSize(14);
        firstView.setTextColor(getResources().getColor(R.color.color_black90));
        firstView.setTypeface(null, Typeface.NORMAL);
        view.setTextSize(20);
        view.setTypeface(null, Typeface.BOLD);
        int left = view.getLeft();
        int offer1 = getOffer(firstView.getText().toString());
        int offer2 = getOffer(view.getText().toString());
        for (int i = 0; i < childCount; i++) {
            TextView child = (TextView) titleLl.getChildAt(i);
            if (child.getLeft() >= left) {
                if (child.getLeft() == left) {
                    resultList.add(createTranslationAnimations(child, -0, -left + offer1));
                } else {
                    resultList.add(createTranslationAnimations(child, 0, -left + offer1));
                }
                headNameList.add(child.getText().toString());
            } else if (child.getLeft() < left) {
                resultList.add(createTranslationAnimations(child, titleWidth - offer1 + offer2 + ScreenUtil.dip2px(16), titleWidth - offer1 + offer2 + ScreenUtil.dip2px(16) - left + offer1));
            }
        }
        for (int i = 0; i < childCount; i++) {
            TextView child = (TextView) titleLl.getChildAt(i);
            if (child.getLeft() < left) {
                headNameList.add(child.getText().toString());
            } else {
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
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
                animationEnd();
                view.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation = false;
                super.onAnimationCancel(animation);
                view.setEnabled(true);
            }
        });
        resultSet.start();
    }

    private void animationEnd() {
        titleLl.removeAllViews();
        int size = headNameList.size();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(-2, -1);
            TextView textView = new TextView(getActivity());
            if (i != size - 1) {
                textView.setPadding(0, 0, ScreenUtil.dip2px(16), 0);
            }
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(headNameList.get(i));
            textView.setOnClickListener(Main1Fragment.this);
            if (i == 0) {
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.color_black50));
                textView.setTypeface(null, Typeface.BOLD);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_black90));
                textView.setTypeface(null, Typeface.NORMAL);
                textView.setTextSize(14);
            }
            titleLl.addView(textView, ll);
        }
        headNameList.clear();
    }

    private Animator createTranslationAnimations(View view, float startX, float endX) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX",
                startX, endX);
        return animX;
    }

    public int getOffer(String text) {
        Paint paint1 = new Paint();
        paint1.setTextSize(ScreenUtil.sp2px(14));
        Rect rect1 = new Rect();
        paint1.getTextBounds(text, 0, text.length(), rect1);
        Paint paint2 = new Paint();
        paint2.setTextSize(ScreenUtil.sp2px(20));
        Rect rect2 = new Rect();
        paint2.getTextBounds(text, 0, text.length(), rect2);
        return rect2.width() - rect1.width();
    }

    @Override
    public void onClick(View v) {
        if (titleFl.getAlpha() > 0.5 && titleFl.getVisibility() == View.VISIBLE) {
            return;
        }
        if (isAnimation) {
            return;
        }
        v.setEnabled(false);
        ((TextView) v).setTextColor(getResources().getColor(R.color.color_black50));
        if (v instanceof TextView) {
            TextView textView = (TextView) v;
            startAnimation((TextView) v);
            int index = 0;
            int size = headList.size();
            for (int i = 0; i < size; i++) {
                if (textView.getText().toString().equals(headList.get(i).getTitle())) {
                    index = i;
                    stat("频道", "首页-" + headList.get(index).getTitle());
                    break;
                }
            }
            viewPager.setCurrentItem(index);
        }

    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.REFRESH_CART_COUNT) {
            if (cartBade == null) {
                cartBade = (QBadgeView) new QBadgeView(getActivity()).bindTarget(cartIv).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(5, 5, true);
                cartIv.setId(R.id.cart_iv);
            }
            if (HomeActivity.count > 9) {
                cartBade.setBadgeText("9+");
            } else {
                cartBade.setBadgeNumber(HomeActivity.count);
            }
            if (cartBade1 == null) {
                cartBade1 = (QBadgeView) new QBadgeView(getActivity()).bindTarget(cartIv1).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(5, 5, true);
                cartIv1.setId(R.id.cart_iv1);
            }
            if (HomeActivity.count > 9) {
                cartBade1.setBadgeText("9+");
            } else {
                cartBade1.setBadgeNumber(HomeActivity.count);
            }
        } else if (bean.type == Constants.ActionType.HOME_UP) {
            Fragment fragment = fragmentList.get(viewPager.getCurrentItem());
            if (fragment instanceof UpListener) {
                ((UpListener) fragment).toUp();
            }
        } else if (bean.type == Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE) {
            requestUnreadNumTak();
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (fragmentList != null && viewPager != null && fragmentList.size() > viewPager.getCurrentItem()) {
            fragmentList.get(viewPager.getCurrentItem()).setUserVisibleHint(false);
        }
    }

    @Override
    public void show() {
        super.show();
        if (fragmentList != null && viewPager != null && fragmentList.size() > viewPager.getCurrentItem()) {
            fragmentList.get(viewPager.getCurrentItem()).setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void stat(String event, String label) {
        StatService.onEvent(getActivity(), event, label);
        TCAgent.onEvent(getActivity(), event, label);
    }
}
