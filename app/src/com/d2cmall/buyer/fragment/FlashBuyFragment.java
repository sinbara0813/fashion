package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FragmentViewPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.AdBean;
import com.d2cmall.buyer.bean.FlashSessionListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.StopRefreshListener;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.Banner;
import com.d2cmall.buyer.widget.FlashTabView;
import com.d2cmall.buyer.widget.LimitViewPager;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2018/4/28.
 * 限时购fragment
 */

public class FlashBuyFragment extends Fragment {

    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.tag)
    TextView tag;
    @Bind(R.id.flash_hour_tv)
    TextView flashHourTv;
    @Bind(R.id.flash_minute_tv)
    TextView flashMinuteTv;
    @Bind(R.id.flash_mouse_tv)
    TextView flashMouseTv;
    @Bind(R.id.flash_time_ll)
    RelativeLayout flashTimeLl;
    @Bind(R.id.ll_top_always)
    LinearLayout llTopAlways;
    @Bind(R.id.viewpager)
    LimitViewPager viewpager;
    @Bind(R.id.activity_main)
    CoordinatorLayout activityMain;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.iv_error)
    ImageView ivError;
    private Context mContext;

    private List<FlashSessionListBean.DataBean.ListBean> list;

    //场次的数据源
    private FlashSessionListBean flashSessionListBean;

    //因为各个场次是用viewpager+fragment管理的,这里是fragment的集合
    private List<FlashProductSubFragment> fragments;

    //控制能否刷新的布尔值,通过布局是否完全展开赋值
    private boolean canReflash;

    //倒计时的handler,在onDestroy回收,否则会报错
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setTime();
        }
    };

    //告诉parent fragment刷新结束的接口
    private StopRefreshListener stopRefreshListener = new StopRefreshListener() {
        @Override
        public void stopRefresh() {
//            if (ptr != null) {
//                ptr.refreshComplete();
//            }
        }
    };


    //banner的数据源
    private AdBean productAd;

    //viewpager当前选中的位置
    private int currentPosition = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_flash_buy, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        this.mContext=context;
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        list = new ArrayList<>();
        viewpager.setCanScroll(false);
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return canReflash;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
                loadBanner();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //这里监听布局是否完全展开,给canReflash赋值
                canReflash = (verticalOffset == 0);
            }
        });
        viewpager.setOffscreenPageLimit(4);
        //加载banner数据
        loadBanner();
    }


    private void loadBanner() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/ad/FLASHPROMOTION/FLASHPRODUCT");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdBean>() {
            @Override
            public void onResponse(AdBean adBean) {
                productAd = adBean;
                banner.setVisibility(View.VISIBLE);
                int size = adBean.getData().getAdResource().getPics().length;
                List<String> bannerItems = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    bannerItems.add(adBean.getData().getAdResource().getPics()[i]);
                }

                banner.setIndicatorType(1).setBottomPadding(8).setScaleType(true).setLoadingPic(R.mipmap.ic_logo_empty7).setSource(bannerItems).setAutoScrollEnable(true).startScroll();
                if (size == 1) {
                    banner.pauseScroll();
                }
                banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                    @Override
                    public void onItemClick(int position) {
                        Util.urlAction(mContext, productAd.getData().getAdResource().getPicsUrl()[position]);
                    }
                });
                //加载场次数据
                loadSession();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (productAd == null) {
                    banner.setVisibility(View.GONE);
                }
                //加载场次数据
                loadSession();
            }
        });
    }

    private void loadSession() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/flashpromotion/product/session");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashSessionListBean>() {
            @Override
            public void onResponse(FlashSessionListBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                activityMain.setVisibility(View.VISIBLE);
                ivError.setVisibility(View.GONE);
                flashSessionListBean = response;
                //当只有一个场次的时候,隐藏tabLayout,只有大于1个才会出现
                if (flashSessionListBean.getData().getList().size() == 1) {
                    tabLayout.setVisibility(View.GONE);
                } else {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                if (flashSessionListBean.getData().getList().size() == 0) {
                    flashTimeLl.setVisibility(View.GONE);
                } else {
                    flashTimeLl.setVisibility(View.VISIBLE);
                }
                list.addAll(response.getData().getList());

                //添加相应数量的fragment
                //addFragments();

                //由于tabLayout的tab是自定义的,需要逐个添加tab
                addTab(response.getData().getList());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (flashSessionListBean == null) {
                    activityMain.setVisibility(View.GONE);
                    ivError.setVisibility(View.VISIBLE);
                }
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void addFragments() {
        for (int i = 0; i < list.size(); i++) {
            FlashProductSubFragment flashProductSubFragment = FlashProductSubFragment.newInstance(list.get(i).getId(), 1);
            flashProductSubFragment.setStopRefreshListener(stopRefreshListener);
            fragments.add(flashProductSubFragment);
        }
    }

    private void setPromotionTime(long endTime, TextView hourTv, TextView minuteTv, TextView mouseTv) {
        long offer = endTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    private void addTab(List<FlashSessionListBean.DataBean.ListBean> data) {
        List<FlashProductSubFragment> fragmentList = new ArrayList<>();
        fragmentList.clear();
        for (int i = 0; i < data.size(); i++) {
            FlashProductSubFragment flashProductSubFragment = FlashProductSubFragment.newInstance(data.get(i).getId(), 1);
            flashProductSubFragment.setStopRefreshListener(stopRefreshListener);
            fragmentList.add(flashProductSubFragment);
        }
        viewpager.setAdapter(new MyTabAdapter(getChildFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(viewpager);
        int nowItem = 0;
        //这里是逐个添加tab,因为3个以及3而以上的tab会出现线,所以下边是一些初始化以及设置
        for (int i = 0; i < data.size(); i++) {
            FlashTabView flashTabView = new FlashTabView(mContext);
            if (data.size() > 2) {
                if (i == (data.size() - 1)) {
                    flashTabView.setLineVisible(false, false);
                } else {
                    flashTabView.setLineVisible(false, true);
                }

            }
            if (data.size() == 2) {
                //两个场次的时候tab的间距会宽一些,这里通过设置每个tab的宽度来增加间距
                flashTabView.setWidth(ScreenUtil.dip2px(167));
            }
            String topText = list.get(i).getSession();
            if (flashSessionListBean.getData().getCurrentId() == data.get(i).getId()) {
                nowItem = i;
            }
            String bottom;
            if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) == 0) {
                bottom = "";
            } else if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) < 0) {
                bottom = "昨日";
            } else {
                bottom = "明日";
            }
            if (!Util.isEmpty(list.get(i).getSessionName())) {
                bottom = bottom + list.get(i).getSessionName();
            } else {
                bottom = bottom + list.get(i).getStatusName();
            }
            flashTabView.setText(topText, bottom);

            tabLayout.getTabAt(i).setCustomView(flashTabView);
        }
        viewpager.setCurrentItem(nowItem);
        currentPosition = nowItem;
        setTime();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //每次切换场次的时候同时切换倒计时的时间
                currentPosition = position;
                setTime();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setTime() {
        if (currentPosition < list.size()) {
            long startTime = list.get(currentPosition).getStartTimeStamp();
            long endTime = list.get(currentPosition).getEndTimeStamp();
            if (endTime < System.currentTimeMillis()) {//到时间了,已经结束了
                flashTimeLl.setVisibility(View.GONE);
            } else {
                flashTimeLl.setVisibility(View.VISIBLE);
            }
            String notice;
            if (Util.isEmpty(list.get(currentPosition).getSessionRemark())) {
                notice = "精选大牌，限时快抢";
            } else {
                notice = list.get(currentPosition).getSessionRemark();
            }
            tvNotice.setText(notice);
            if (startTime - System.currentTimeMillis() > 0) {//未开始
                endTime = startTime;
                tag.setText("距开始");
            } else {
                tag.setText("距结束");
            }
            setPromotionTime(endTime, flashHourTv, flashMinuteTv, flashMouseTv);
            Message message = Message.obtain();
            message.what = currentPosition;
            handler.sendMessageDelayed(message, 1000);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    class MyTabAdapter extends FragmentPagerAdapter {

        private List<FlashProductSubFragment> data;


        public MyTabAdapter(FragmentManager fm, List<FlashProductSubFragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public long getItemId(int position) {
            return flashSessionListBean.getData().getList().get(position).getId();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }


}
