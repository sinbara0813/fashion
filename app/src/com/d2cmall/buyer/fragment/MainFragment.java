package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.HotSearchApi;
import com.d2cmall.buyer.api.MessageListApi;
import com.d2cmall.buyer.api.NoticeCancelApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.HotSearchBean;
import com.d2cmall.buyer.bean.MainNoticeBean;
import com.d2cmall.buyer.bean.MainTagBean;
import com.d2cmall.buyer.bean.MessageListBean;
import com.d2cmall.buyer.bean.PopBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.UpListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ExpandViewPager;
import com.d2cmall.buyer.widget.PicPop;
import com.d2cmall.buyer.widget.SwitcherView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import q.rorbin.badgeview.QBadgeView;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 17:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainFragment extends BaseTabFragment implements View.OnClickListener {

    @Bind(R.id.horizontal_scroll)
    HorizontalScrollView horizontalScrollView;
    @Bind(R.id.title_ll)
    LinearLayout titleLl;
    @Bind(R.id.cart_iv1)
    ImageView cartIv1;
    @Bind(R.id.search_tv)
    TextView searchTv;
    @Bind(R.id.message_iv)
    ImageView messageIv;
    @Bind(R.id.scan_iv)
    ImageView scanIv;
    @Bind(R.id.search_iv1)
    LinearLayout searchIv1;
    @Bind(R.id.search_fl)
    FrameLayout searchLl;
    @Bind(R.id.main_view_pager)
    ExpandViewPager viewPager;
    @Bind(R.id.tv_notice)
    SwitcherView tvNotice;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.ll_notice)
    LinearLayout llNotice;
    @Bind(R.id.top_fl)
    FrameLayout topFl;
    @Bind(R.id.search_iv)
    ImageView searchIv;


    private List<Fragment> fragmentList;
    private List<MainTagBean.DataEntity.SubModulesEntity> headList;
    private List<String> headNameList;
    private int lastPosition;
    private HashMap<Integer, String> searchHash;
    private QBadgeView mBadgeView;
    private QBadgeView cartBade;
    private ArrayList<String> titles;
    private MainNoticeBean mainNoticeBean;
    private boolean noticeIsGone;
    private int unreadCount = 0;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
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
        titles = new ArrayList<>();
        boolean scanBuyTip = D2CApplication.mSharePref.getSharePrefBoolean("scanBuyTip", false);
        if (!scanBuyTip) {
            D2CApplication.mSharePref.putSharePrefBoolean("scanBuyTip", true);
            scanIv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextView textView = new TextView(mContext);
                    textView.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    textView.setTextSize(14);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundResource(R.mipmap.pic_home_popover01);
                    textView.setText(mContext.getString(R.string.label_scan_buy_tip));
                    PopupWindow popupWindow = new PopupWindow(textView, ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32), ScreenUtil.dip2px(60));
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.showAsDropDown(messageIv, ScreenUtil.dip2px(-4), ScreenUtil.dip2px(-8));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                        }
                    });
                }
            }, 1000);

        }
        layoutLocalData();
        loadMainHead();
        loadNotice();
        initListener();
        if (Session.getInstance().getUserFromFile(getActivity()) != null && Session.getInstance().getUserFromFile(getActivity()).getMemberId() > 0) {
            getCartNum();
            requestUnreadNumTak(false);
        }
    }

    private void loadNotice() {     //加载公告
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GET_MAIN_NOTICE_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainNoticeBean>() {
            @Override
            public void onResponse(MainNoticeBean mainNoticeBean) {
                if (mainNoticeBean != null) {
                    if (mainNoticeBean.getData() == null || mainNoticeBean.getData().getArticle() == null || Util.isEmpty(mainNoticeBean.getData().getArticle().getTitle())) {
                        return;
                    }
                    MainFragment.this.mainNoticeBean = mainNoticeBean;
                    llNotice.setVisibility(View.VISIBLE);
                    tvNotice.setResource(spiltTitle(mainNoticeBean.getData().getArticle().getTitle(), 23));
                    tvNotice.startRolling();
                }
            }
        });
    }

    public ArrayList<String> spiltTitle(String noticetTitle, int splitLength) {//将展示的文章标题分割为数组
        int length = noticetTitle.length();
        int count = length / splitLength;
        int remainder = length % splitLength;

        if (count > 0) {
            for (int i = 0; i < count; i++) {
                titles.add(noticetTitle.substring(splitLength * i, (splitLength * (i + 1) > length) ? length : splitLength * (i + 1)));
            }
            if (remainder > 0) {
                titles.add(noticetTitle.substring(splitLength * count, length));
            }
        } else {
            titles.add(noticetTitle);
        }
        return titles;
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

    private void requestUnreadNumTak(final boolean isReadAction) {
        MessageListApi api = new MessageListApi();
        long type6 = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6, System.currentTimeMillis());
        long type7 = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE7, System.currentTimeMillis());
        api.majorTypeTime6 = type6;
        api.majorTypeTime7 = type7;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MessageListBean>() {
            @Override
            public void onResponse(MessageListBean response) {
                if (getActivity() == null) {
                    return;
                }
                if (mBadgeView == null) {
                    mBadgeView = (QBadgeView) new QBadgeView(getActivity()).bindTarget(messageIv).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(0, -2, true);
                    messageIv.setId(R.id.message_iv);
                }
                //如果是点击消息标记已读,但是后台返回的未读数大于本地计算之后的未读数就return
                if (unreadCount < response.getData().getUnreadCount() && isReadAction) {
                    return;
                }
                unreadCount = response.getData().getUnreadCount();
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

    @OnClick({R.id.message_iv, R.id.search_iv1, R.id.scan_iv, R.id.cart_iv1, R.id.tv_notice, R.id.iv_close})
    public void click(View view) {
        if (!isVisibleToUser) {
            return;
        }
        Intent intent = null;
        switch (view.getId()) {
            case R.id.message_iv:
                boolean isLogin = Util.loginChecked(getActivity(), 1);
                if (isLogin) {
                    getActivity().startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.search_iv1:
                if (headList.size() > 0 && view.getVisibility() == View.VISIBLE) {
                    stat("V3搜索", "首页" + headList.get(lastPosition).getTitle() + "搜索");
                    intent = new Intent(getActivity(), Search1Activity.class);
                    intent.putExtra("name", searchTv.getText().toString());
                    intent.putExtra("id", headList.get(lastPosition).getId());
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.cart_iv1:
            case R.id.cart_iv:
                if (Util.loginChecked(getActivity(), 100)) {
                    stat("V3购物车入口", "首页购物车");
                    intent = new Intent(getActivity(), CartActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.scan_iv:
                intent = new Intent(getActivity(), ScanQrCodeActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_notice:    //点击公告内容
                if (Util.isEmpty(mainNoticeBean.getData().getArticle().getName()) || Util.isEmpty(mainNoticeBean.getData().getArticle().getContent())) {
                    return;
                }
                intent = new Intent(mContext, WebActivity.class);
                String url = null;
                intent.putExtra("type", 0);
                url = "page/" + mainNoticeBean.getData().getArticle().getName();
                intent.putExtra("url", url);
                intent.putExtra("isShareGone", true);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_close:
                llNotice.setVisibility(View.GONE);
                noticeIsGone = true;
                requestCloseNotice(); //首页公告点X
                break;
        }
    }

    private void setContent() {
        int size = headList.size();
        for (int i = 0; i < size; i++) {
            MainTagBean.DataEntity.SubModulesEntity subModulBean = headList.get(i);
            if (subModulBean.getIsCategory() == 1) { //有子fragment;
                fragmentList.add(MainWomenFragment.newInstance(Integer.valueOf(headList.get(i).getId()), headList.get(i).getTitle()).setStatName(headList.get(i).getTitle()));
            } else if (!Util.isEmpty(headList.get(i).getWebUrl())) {
                if (headList.get(i).getWebUrl().contains("isSpecial")) {
                    fragmentList.add(MainSiftFragment.newInstance(headList.get(i).getId()));
                } else {
                    fragmentList.add(WebFragment.newInstance(headList.get(i).getWebUrl()));
                }
            } else {
                fragmentList.add(MainManFragment.newInstance(headList.get(i).getId(), hasLive(headList.get(i).getTitle()), hasLive(headList.get(i).getTitle()), true, headList.get(i).getTitle()).setStatName("V3首页/" + headList.get(i).getTitle()));
            }
        }
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), fragmentList, null);
        viewPager.setAdapter(tabPagerAdapter);
        if (headList.size() > 0) {
            stat("V3顶部频道导航", headList.get(0).getTitle());
        }
    }

    private boolean hasLive(String name) {
        return "男士".equals(name) || "女士".equals(name);
    }

    private void setMainHead() {
        StringBuilder build = new StringBuilder();
        int size = headList.size();
        if (titleLl != null) {
            titleLl.removeAllViews();
        }
        for (int i = 0; i < size; i++) {
            build.append(headList.get(i).getTitle());
            if (i != size - 1) {
                build.append(",");
            }
            LinearLayout.LayoutParams ll;
            if (!Util.isEmpty(headList.get(i).gettPic()) && !Util.isEmpty(headList.get(i).getTbPic())) {
                ll = new LinearLayout.LayoutParams(ScreenUtil.dip2px(60), ScreenUtil.dip2px(40));
                ll.gravity = Gravity.CENTER_VERTICAL;
                ImageView imageView = new ImageView(mContext);
                if (i != size - 1) {
                    ll.setMargins(0, 0, ScreenUtil.dip2px(16), 0);
                }
                if (i == 0) {
                    UniversalImageLoader.displayImage(mContext, headList.get(i).gettPic(), imageView);
                } else {
                    UniversalImageLoader.displayImage(mContext, headList.get(i).getTbPic(), imageView);
                }
                imageView.setTag(R.id.main_tag, headList.get(i).getTitle());
                imageView.setOnClickListener(this);
                titleLl.addView(imageView, ll);
            } else {
                ll = new LinearLayout.LayoutParams(-2, -1);
                TextView textView = new TextView(mContext);
                if (i != size - 1) {
                    textView.setPadding(0, 0, ScreenUtil.dip2px(16), 0);
                }
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setText(headList.get(i).getTitle());
                textView.setTag(R.id.main_tag, headList.get(i).getTitle());
                if (i == 0) {
                    textView.setTextSize(20);
                    textView.setTextColor(getResources().getColor(R.color.color_black87));
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.color_black38));
                    textView.setTypeface(null, Typeface.NORMAL);
                    textView.setTextSize(14);
                }
                textView.setOnClickListener(this);
                titleLl.addView(textView, ll);
            }
            setColor(headList.get(0).getColor());
        }
        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.MAIN_BIG_TAG, build.toString());
    }

    private void setColor(String color){
        if (Util.isEmpty(color))return;
        int size=titleLl.getChildCount();
        if (color.toLowerCase().equals("000000")||color.toLowerCase().equals("ffffff")){
            topFl.setBackgroundColor(getResources().getColor(R.color.color_white));
            searchLl.setBackgroundColor(getResources().getColor(R.color.color_white));
            cartIv1.setImageResource(R.mipmap.tab_all_shop_b);
            messageIv.setImageResource(R.mipmap.icon_all_message);
            searchIv1.setBackgroundResource(R.drawable.sp_search_bg);
            searchIv.setImageResource(R.mipmap.tab_all_search_b);
            searchTv.setTextColor(getResources().getColor(R.color.color_black38));
            scanIv.setImageResource(R.mipmap.ic_scan);
            horizontalScrollView.setBackgroundResource(R.drawable.bg_gradient_black3);
            cartIv1.setBackgroundResource(R.drawable.bg_gradient_black3);
            for (int i=0;i<size;i++){
                if (titleLl.getChildAt(i) instanceof  TextView){
                    TextView textView= (TextView) titleLl.getChildAt(i);
                    if (i==viewPager.getCurrentItem()){
                        textView.setTextColor(getResources().getColor(R.color.color_black87));
                    }else {
                        textView.setTextColor(getResources().getColor(R.color.color_black38));
                    }
                }
            }
        }else {
            topFl.setBackgroundColor(Color.parseColor(getColorStr(color)));
            searchLl.setBackgroundColor(Color.parseColor(getColorStr(color)));
            cartIv1.setImageResource(R.mipmap.tab_all_shop_w);
            messageIv.setImageResource(R.mipmap.icon_all_message_w);
            searchIv1.setBackgroundResource(R.drawable.bg_search_bg1);
            searchIv.setImageResource(R.mipmap.tab_all_search_w);
            searchTv.setTextColor(getResources().getColor(R.color.trans_50_color_white));
            scanIv.setImageResource(R.mipmap.icon_home_scan_w);
            horizontalScrollView.setBackgroundColor(Color.parseColor(getColorStr(color)));
            cartIv1.setBackgroundResource(0);
            for (int i=0;i<size;i++){
                if (titleLl.getChildAt(i) instanceof  TextView){
                    TextView textView= (TextView) titleLl.getChildAt(i);
                    if (i==viewPager.getCurrentItem()){
                        textView.setTextColor(getResources().getColor(R.color.trans_87_color_white));
                    }else {
                        textView.setTextColor(getResources().getColor(R.color.trans_40_color_white));
                    }
                }
            }
        }
    }

    private String getColorStr(String color){
        String result=null;
        if (color.startsWith("#")){
            if (color.length()==7||color.length()==9){
                return color;
            }else {
                result="#ffffff";
            }
        }else {
            if (color.length()==6||color.length()==8){
                return "#"+color;
            }else {
                result="#ffffff";
            }
        }
        return result;
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

                if (position < titleLl.getChildCount() && lastPosition < titleLl.getChildCount()) {
                    if (!Util.isEmpty(headList.get(lastPosition).gettPic()) && !Util.isEmpty(headList.get(lastPosition).getTbPic())) {
                        ImageView imageView = (ImageView) titleLl.getChildAt(lastPosition);
                        UniversalImageLoader.displayImage(mContext, headList.get(lastPosition).getTbPic(), imageView);
                    } else {
                        TextView lastTextView = (TextView) titleLl.getChildAt(lastPosition);
                        lastTextView.setTextSize(14);
                        lastTextView.setTextColor(getResources().getColor(R.color.color_black38));
                        lastTextView.setTypeface(null, Typeface.NORMAL);
                    }
                    if (!Util.isEmpty(headList.get(position).gettPic()) && !Util.isEmpty(headList.get(position).getTbPic())) {
                        ImageView imageView = (ImageView) titleLl.getChildAt(position);
                        UniversalImageLoader.displayImage(mContext, headList.get(position).gettPic(), imageView);
                    } else {
                        TextView textView = (TextView) titleLl.getChildAt(position);
                        textView.setTextSize(20);
                        textView.setTextColor(getResources().getColor(R.color.color_black87));
                        textView.setTypeface(null, Typeface.BOLD);
                    }
                    setColor(headList.get(position).getColor());
                }
                lastPosition = position;
                if ((position == 0 || position == 1) && horizontalScrollView.getScrollX() != 0) {
                    horizontalScrollView.scrollTo(0, 0);
                }
                Rect outRect = new Rect();
                titleLl.getChildAt(position).getHitRect(outRect);
                if (outRect.right > horizontalScrollView.getWidth()) {
                    horizontalScrollView.scrollTo(titleLl.getWidth() - horizontalScrollView.getWidth(), 0);
                }
                if (headList.get(position).getTitle().equals("女士")) {
                    if (!noticeIsGone && mainNoticeBean != null) {
                        llNotice.setVisibility(View.VISIBLE);
                        tvNotice.setResource(spiltTitle(mainNoticeBean.getData().getArticle().getTitle(), 23));
                        tvNotice.startRolling();
                    }
                } else {
                    hideNotice();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void hideNotice() {
        if (llNotice.getVisibility() == View.GONE) {
            return;
        } else {
            llNotice.setVisibility(View.GONE);
            tvNotice.destroySwitcher();
        }
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
        firstView.setTextColor(getResources().getColor(R.color.color_black38));
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

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationEnd();
                view.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                view.setEnabled(true);
            }
        });
        resultSet.start();
    }

    private void startNoAnimation(int index) {
        int size = headList.size() + index;
        for (int i = index; i < size; i++) {
            headNameList.add(headList.get(i % headList.size()).getTitle());
        }
        animationEnd();
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
            textView.setOnClickListener(MainFragment.this);
            if (i == 0) {
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.color_black87));
                textView.setTypeface(null, Typeface.BOLD);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_black38));
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
        int index = 0;
        int size = headList.size();
        for (int i = 0; i < size; i++) {
            if (v.getTag(R.id.main_tag).equals(headList.get(i).getTitle())) {
                index = i;
                stat("V3顶部频道导航", headList.get(index).getTitle());
                break;
            }
        }
        if (viewPager.getCurrentItem() == index) return;
        viewPager.setCurrentItem(index);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.REFRESH_CART_COUNT) {
            if (HomeActivity.count > 0) {
                if (cartBade == null) {
                    cartBade = (QBadgeView) new QBadgeView(getActivity()).bindTarget(cartIv1).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(5, 5, true);
                    cartIv1.setId(R.id.cart_iv1);
                }
                if (HomeActivity.count > 9) {
                    cartBade.setBadgeText("9+");
                } else {
                    cartBade.setBadgeNumber(HomeActivity.count);
                }
            } else {
                if (cartBade != null) {
                    ViewParent parent = cartBade.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(cartBade);
                        cartBade = null;
                    }
                }
            }
        } else if (bean.type == Constants.ActionType.HOME_UP) {
            Fragment fragment = fragmentList.get(viewPager.getCurrentItem());
            if (fragment instanceof UpListener) {
                ((UpListener) fragment).toUp();
            }
        } else if (bean.type == Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE) {
            int readCount = (int) bean.get("readCount");
            if (readCount > 0 && mBadgeView != null) {
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
            } else {
                requestUnreadNumTak(false);
            }

        } else if (bean.type == Constants.ActionType.POP) {
            if (isVisibleToUser) {
                if (mContext == null) return;
                PopBean popBean = Session.getInstance().getPopFromFile(mContext);
                if (popBean != null && !Util.isEmpty(popBean.getPic())) {
                    PicPop picPop = new PicPop(mContext, popBean.getPic(), popBean.getUrl());
                    picPop.show(horizontalScrollView);
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.HAS_POP, false);
                }
            }
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
        boolean hasPop = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.HAS_POP, false);
        if (hasPop) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mContext == null) return;
                    PopBean popBean = Session.getInstance().getPopFromFile(mContext);
                    if (popBean != null && !Util.isEmpty(popBean.getPic())) {
                        PicPop picPop = new PicPop(mContext, popBean.getPic(), popBean.getUrl());
                        picPop.show(horizontalScrollView);
                    }
                }
            }, 2000);
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.HAS_POP, false);
        }
    }

    @Override
    public void onDestroyView() {
        if (tvNotice != null) {
            tvNotice.destroySwitcher();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void stat(String event, String label) {
        StatService.onEvent(getActivity(), event, label);
        TCAgent.onEvent(getActivity(), event, label);
    }

    private void requestCloseNotice() {
        if (mainNoticeBean == null) {
            return;
        }
        NoticeCancelApi noticeCancelApi = new NoticeCancelApi();
        noticeCancelApi.setArticleId(mainNoticeBean.getData().getArticle().getId());
        D2CApplication.httpClient.loadingRequest(noticeCancelApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
            }
        });
        mainNoticeBean = null;
    }
}
