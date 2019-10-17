package com.d2cmall.buyer.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ChoiceWearAdapter;
import com.d2cmall.buyer.adapter.WardrobeTopVpAdapter;
import com.d2cmall.buyer.adapter.WearWeatherAdapter;
import com.d2cmall.buyer.api.FashionListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MyWearCollocationBean;
import com.d2cmall.buyer.bean.RecommendWearListBean;
import com.d2cmall.buyer.bean.WeatherBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.holder.WearChioceHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ViewPagerCard.CardPageTransformer;
import com.d2cmall.buyer.widget.ViewPagerCard.OnPageTransformerListener;
import com.d2cmall.buyer.widget.ViewPagerCard.PageTransformerConfig;
import com.d2cmall.buyer.widget.calendarview.listener.OnPagerChangeListener;
import com.d2cmall.buyer.widget.calendarview.utils.CalendarUtil;
import com.d2cmall.buyer.widget.calendarview.weiget.CalendarView;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 作者:Created by sinbara on 2018/11/13.
 * 邮箱:hrb940258169@163.com
 * 每日搭配
 */

public class FashionLooksFragment extends BaseTabFragment {
    @Bind(R.id.iv_background)
    ImageView ivBackground;
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    @Bind(R.id.tv_date_month)
    TextView tvDateMonth;
    @Bind(R.id.iv_next)
    ImageView ivNext;
    @Bind(R.id.calendar)
    CalendarView calendar;
    @Bind(R.id.ll_calendar)
    LinearLayout llCalendar;
    @Bind(R.id.iv_back_first)
    ImageView ivBackFirst;
    @Bind(R.id.iv_title_first)
    ImageView ivTitleFirst;
    @Bind(R.id.iv_calendar_first)
    ImageView ivCalendarFirst;
    @Bind(R.id.ll_title_first)
    LinearLayout llTitleFirst;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.iv_back_second)
    ImageView ivBackSecond;
    @Bind(R.id.iv_title_second)
    ImageView ivTitleSecond;
    @Bind(R.id.iv_calendar_second)
    ImageView ivCalendarSecond;
    @Bind(R.id.ll_title_second)
    LinearLayout llTitleSecond;
    @Bind(R.id.rl_head)
    RelativeLayout rlHead;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private int EXPANDED = 1995;
    private int COLLAPSED = 11;
    private int INTERNEDIATE = 23;
    private int state;

    private int[] cDate;
    private boolean hasInitCalendar;
    private boolean hasInitVp;
    private WardrobeTopVpAdapter wardrobeTopVpAdapter;
    private boolean isLoadingVp;//是否正在加载Vp数据
    private String lastBeginDate = DateUtil.getFutureDate(DateUtil.stampToDate(System.currentTimeMillis() + ""), -20);

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private int currentViewType = -1; //当前展示的UI样式 -1是卡片层叠,1是日历
    private String temp;
    private String wind;
    private String sc;
    private String drsg;
    private String cond;
    private List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> recommendList;
    private List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> myWearList;
    private ChoiceWearAdapter choiceWearAdapter;
    private int currentPage = 1;
    private boolean hasNext = true;
    private WearWeatherAdapter wearWeatherAdapter;
    private WeatherBean weatherBean;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_wear, container, false);
    }

    @Override
    public void doBusiness() {
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        recommendList = new ArrayList<>();
        myWearList = new ArrayList<>();
        wardrobeTopVpAdapter = new WardrobeTopVpAdapter(mContext, myWearList);
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(24)) / 2;
        wearWeatherAdapter=new WearWeatherAdapter(mContext);
        choiceWearAdapter = new ChoiceWearAdapter(mContext, itemWidth, recommendList);
        delegateAdapter.addAdapter(wearWeatherAdapter);
        delegateAdapter.addAdapter(choiceWearAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        initListener();
        loadRecommendData();
        initPager();
    }

    private void loadRecommendData() {
        SimpleApi api = new SimpleApi();
        api.setPageSize(20);
        api.setP(currentPage);
        api.setInterPath(Constants.WARDROBE_RECOMMEND_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RecommendWearListBean>() {
            @Override
            public void onResponse(RecommendWearListBean recommendWearListBean) {
                if (mContext == null) {
                    return;
                }
                if (currentPage == 1) {
                    recommendList.clear();
                }
                recommendList.addAll(recommendWearListBean.getData().getRecommends().getList());
                hasNext = recommendWearListBean.getData().getRecommends().isNext();
                choiceWearAdapter.notifyDataSetChanged();
            }
        });
    }

    public void loadWeather(String cityName) {
        if(weatherBean!=null){
            return;
        }
        HttpUtils.doGetAsyn(String.format(Constants.WEATHER_URL, cityName), new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                Gson gson = new Gson();
                try {
                    weatherBean = gson.fromJson(result, WeatherBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (weatherBean != null && weatherBean.getHeWeather5() != null) {

                    temp = weatherBean.getHeWeather5().get(0).getNow().getTmp();
                    wind = weatherBean.getHeWeather5().get(0).getNow().getWind().getDir();
                    sc = weatherBean.getHeWeather5().get(0).getNow().getWind().getSc();
                    cond = weatherBean.getHeWeather5().get(0).getNow().getCond().getTxt();
                    if (weatherBean.getHeWeather5().get(0).getSuggestion() != null && weatherBean.getHeWeather5().get(0).getSuggestion().getDrsg() != null) {
                        drsg = weatherBean.getHeWeather5().get(0).getSuggestion().getDrsg().getTxt();
                    }
                    if(wearWeatherAdapter!=null){
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                wearWeatherAdapter.setWeather(weatherBean);
                            }
                        });

                    }

                }
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appBarLayout.invalidate();
                    }
                });

            }
        });
    }

    private void initListener() {
        recycleView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if(holder instanceof WearChioceHolder){
                    WearChioceHolder wearChioceHolder = (WearChioceHolder) holder;
                    NiceVideoPlayer niceVideoPlayer = wearChioceHolder.niceVideoPlayer;
                    if (niceVideoPlayer == com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }

            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisPosition = virtualLayoutManager.findLastVisibleItemPosition();
                int itemCount = virtualLayoutManager.getItemCount();
                if (lastVisPosition >= itemCount - 3 && !hasNext && currentPage > 1) {
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    if (endAdapter != null) {
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter = null;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if(Util.getAPNType(mContext)==1){
                            autoPlayVideo(recyclerView);
                        }
                        int last = virtualLayoutManager.findLastVisibleItemPosition();
                        if (last > choiceWearAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            loadRecommendData();
                        }
                }
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (verticalOffset == 0) {
                        if (state != EXPANDED) {
                            state = EXPANDED;//修改状态标记为展开
                        }
                    } else if (state != COLLAPSED) {
                        llTitleFirst.setVisibility(View.GONE);
                        llTitleSecond.setVisibility(View.VISIBLE);
                        state = COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != INTERNEDIATE) {
                        if (state == COLLAPSED) {
                            llTitleFirst.setVisibility(View.VISIBLE);
                            llTitleSecond.setVisibility(View.GONE);//由折叠变为中间状态时隐藏标题栏
                        }
                        state = INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    private void autoPlayVideo(RecyclerView view){
        for (int i = 0; i < 6; i++) {
            if (view!=null&&view.getChildAt(i)!=null&&view.getChildAt(i).findViewById(R.id.nice_video_player) != null) {
                NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) view.getChildAt(i).findViewById(R.id.nice_video_player);
                Rect rect = new Rect();
                niceVideoPlayer.getGlobalVisibleRect(rect);
                if (rect.top>=ScreenUtil.getDisplayHeight()/6 && rect.bottom<=ScreenUtil.getDisplayHeight()*5/6) {
                    if(niceVideoPlayer.isPlaying() || niceVideoPlayer.isBufferingPlaying() || niceVideoPlayer.isPreparing()){
                        break;
                    }
                    if(niceVideoPlayer.isCompleted()){
                        niceVideoPlayer.restart();
                        break;
                    }
                    if(niceVideoPlayer.isIdle()){
                        niceVideoPlayer.start();
                        break;
                    }
                }else{
                    if(niceVideoPlayer.isPlaying()){
                        niceVideoPlayer.stop();
                    }
                }

            }
        }
    }


    private void fillCalendar() {
        if (mContext == null) {
            return;
        }
        if (cDate == null || cDate.length == 0) {
            cDate = CalendarUtil.getCurrentDate();
        }
        if (!hasInitCalendar) {
            calendar.setStartEndDate("2018.10", "2031.12")
                    .setDisableStartEndDate("2018.1", cDate[0] + "." + cDate[1] + "." + cDate[2])
                    .setInitDate(cDate[0] + "." + cDate[1])
                    .init();

            tvDateMonth.setText(cDate[0] + "年" + cDate[1] + "月");
            ivPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar.lastMonth();
                }
            });
            ivNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar.nextMonth();
                }
            });
            calendar.setOnPagerChangeListener(new OnPagerChangeListener() {
                @Override
                public void onPagerChanged(int[] date) {
                    tvDateMonth.setText(date[0] + "年" + date[1] + "月");
//                    loadCalendarData(date);
                }
            });
            hasInitCalendar = true;
        }
        ivBackground.setImageResource(R.mipmap.topbg);
        llCalendar.setVisibility(View.VISIBLE);
        vp.setVisibility(View.GONE);
    }

    private void initPager() {
        if (mContext == null) {
            return;
        }
        if (!hasInitVp) {
            myWearList.clear();
            loadMyWearData();
            vp.setOffscreenPageLimit(20);
            vp.setPageTransformer(true, CardPageTransformer.getBuild()//建造者模式
                    .setOnPageTransformerListener(new OnPageTransformerListener() {
                        @Override
                        public void onPageTransformerListener(View page, float position) {

                        }
                    })
                    .setViewType(PageTransformerConfig.LEFT)
                    .setTranslationOffset(100)
                    .setScaleOffset(120)
                    .create(vp));
            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (wardrobeTopVpAdapter != null && wardrobeTopVpAdapter.getCount() - 5 <= position) {
                        loadMyWearData();
                    }
                    setBackGroundBlur(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (vp.getAdapter() == null) {
                vp.setAdapter(wardrobeTopVpAdapter);
            }
            hasInitVp = true;
        }

        llCalendar.setVisibility(View.GONE);
        vp.setVisibility(View.VISIBLE);
    }

    private void loadMyWearData() {
        if (isLoadingVp) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        isLoadingVp = true;
        FashionListApi api = new FashionListApi();
        api.beginDate = lastBeginDate;
        api.endDate = DateUtil.stampToDate(System.currentTimeMillis() + "");
        api.setInterPath(Constants.WARDROBE_COLLOCATION_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyWearCollocationBean>() {
            @Override
            public void onResponse(MyWearCollocationBean myWearCollocationBean) {
                //创建20个对象,赋值日期,如果拉到的数据的日期与对象日期相同则赋值
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < 20; i++) {
                    MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean listBean = new MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean();
                    Date futureDate = DateUtil.toDate(DateUtil.getFutureDate(lastBeginDate, 20 - i));
                    //记录当天有多少条图片
                    int curDayTotal = 0;
                    String curDayPic = null;
                    for (int j = 0; j < myWearCollocationBean.getData().getMyWardrobeCollocations().getList().size(); j++) {
                        Date transactionTime = DateUtil.toDate(myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j).getTransactionTime());
                        if (myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j) != null && DateUtil.isSameDate(futureDate, transactionTime)) {
                            //是同一天切没有赋值过listBean
                            if (listBean.getCreateDate() == null) {
                                listBean = myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j);
                            }
                            if (myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j).getPics() != null && myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j).getPics().size() > 0) {
                                if (Util.isEmpty(curDayPic)) {
                                    curDayPic = myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j).getPics().get(0);
                                }
                                curDayTotal += myWearCollocationBean.getData().getMyWardrobeCollocations().getList().get(j).getPics().size();
                            }
                            listBean.setCurDayTotal(curDayTotal);
                        }
                    }
                    if (listBean.getPics() == null || listBean.getPics().size() == 0 && !Util.isEmpty(curDayPic)) {
                        ArrayList<String> pics = new ArrayList<>();
                        pics.add(curDayPic);
                        listBean.setPics(pics);
                    }
                    if (listBean.getPics() != null && listBean.getPics().size() == 1 && Util.isEmpty(listBean.getPics().get(0))) {
                        listBean.getPics().clear();
                    }
                    if (listBean.getTransactionTime() == null) {
                        listBean.setTransactionTime(DateUtil.getFutureDate(lastBeginDate, 20 - i));
                    }
                    myWearList.add(listBean);
                }
                if (wardrobeTopVpAdapter != null) {
                    wardrobeTopVpAdapter.notifyDataSetChanged();
                }
                //第一次加载数据虚化背景
                if(vp.getCurrentItem()==0){
                    setBackGroundBlur(0);
                }
                //将下次获取数据的时间修正20天
                lastBeginDate = DateUtil.getFutureDate(lastBeginDate, -20);
                isLoadingVp = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                isLoadingVp = false;
            }
        });
    }


    //背景模糊
    private void setBackGroundBlur(int position) {
        if (myWearList.size() <= position || myWearList.get(position).getPics() == null || myWearList.get(position).getPics().size() == 0) {
            ivBackground.setImageResource(R.mipmap.topbg);
            return;
        }
        if (mContext != null) {
            Glide.with(mContext).load(Util.getD2cPicUrl(myWearList.get(position).getPics().get(0))).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int width = (int) Math.round(resource.getWidth() * 0.125);
                    int height = (int) Math.round(resource.getHeight() * 0.125);

                    Bitmap inputBmp = Bitmap.createScaledBitmap(resource, width, height, false);

                    RenderScript renderScript = RenderScript.create(mContext);
                    // Allocate memory for Renderscript to work with
                    final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
                    final Allocation output = Allocation.createTyped(renderScript, input.getType());

                    // Load up an instance of the specific script that we want to use.
                    ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
                    scriptIntrinsicBlur.setInput(input);

                    // Set the blur radius
                    scriptIntrinsicBlur.setRadius(10);

                    // Start the ScriptIntrinisicBlur
                    scriptIntrinsicBlur.forEach(output);

                    // Copy the output to the blurred bitmap
                    output.copyTo(inputBmp);

                    renderScript.destroy();
                    ivBackground.setImageBitmap(inputBmp);
                }
            });


        }
    }

    @OnClick({R.id.iv_back_first, R.id.iv_calendar_first, R.id.iv_back_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_first:
            case R.id.iv_back_second:
                getActivity().finish();
                break;
            case R.id.iv_calendar_first:
                switchViewType();
                break;
        }
    }

    //切换视图
    private void switchViewType() {
        currentViewType = -currentViewType;
        if (currentViewType == -1) {
            initPager();
            ivCalendarFirst.setImageResource(R.mipmap.icon_rili_white);
        } else {
            fillCalendar();
            ivCalendarFirst.setImageResource(R.mipmap.icon_card_white);
        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.PUBLISH_WEAR_SUCCESS) {
            int position = (int) bean.getValue("position");
            int total = (int) bean.getValue("total");
            String videoUrl = bean.getStringValue("videoUrl");
            String pic = (String) bean.getValue("pic");
            List<String> pics = myWearList.get(position).getPics();
            MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean listBean = myWearList.get(position);
            if(pics!=null){
                pics.add(pic);
            }
            if(!Util.isEmpty(videoUrl)){
                listBean.setVideo(videoUrl);
            }
            myWearList.get(position).setCurDayTotal(total);
            if(wardrobeTopVpAdapter!=null){
                wardrobeTopVpAdapter.notifyDataSetChanged();
            }
            if(hasInitCalendar){
                calendar.upDataCur();
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
