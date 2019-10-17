package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.MainChoiceAdapter;
import com.d2cmall.buyer.adapter.MainManAdapter;
import com.d2cmall.buyer.api.MainShowApi;
import com.d2cmall.buyer.api.MainSiftGoodApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.FlashBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.bean.MainShareBean;
import com.d2cmall.buyer.bean.RecommendListBean;
import com.d2cmall.buyer.binder.MainLiveBinder;
import com.d2cmall.buyer.binder.MainShowBinder;
import com.d2cmall.buyer.binder.MainTitleBinder;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.MainItemTitleHolder;
import com.d2cmall.buyer.holder.MainLiveHolder;
import com.d2cmall.buyer.holder.MainShowHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ExpandListener;
import com.d2cmall.buyer.listener.UpListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 15:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainManFragment extends BaseFragment implements ExpandListener, UpListener {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private VirtualLayoutManager layoutManage;
    private DelegateAdapter delegateAdapter;

    private List<MainBean.DataEntity.ContentEntity> dataList;
    private FlashBean mFlashBean;
    private MainManAdapter adapter;

    private List<MainShareBean.DataBean.SharesBean> showList;
    private BaseVirtualAdapter<MainShowHolder> showAdapter;

    private List<LiveListBean.DataBean.LivesBean.ListBean> liveList;
    private BaseVirtualAdapter<MainLiveHolder> liveAdapter;

    private List<RecommendListBean.DataBean.RecommendsBean.ListBean> recommendProductList;
    private BaseVirtualAdapter<MainItemTitleHolder> recommendTitleAdapter;
    private MainChoiceAdapter recommendProductAdapter;


    private int lastPosition = -1; //页面location
    private int lastOffer;

    private int id; //页面的subId;
    private boolean hasShow; //是否有买家秀
    private boolean hasLive; //是否有直播
    private boolean hasProduct = true; //是否有精选好货
    private boolean hasNextProduct = true; //是否有下一页精选好货商品
    private boolean isLoadProduct; //是否在加载精选好货
    private int p = 0; //加载精选好货的页码
    private boolean hasSetAdapter=true; //是否设置了设配器
    private boolean isExpand = true; //当前页是否处于展开状态
    private boolean canRefresh; //是否可以刷新页面
    private String name; //页面的名称
    private boolean isRefresh; //刷新标志
    private boolean pageMore; //是否超過一屏
    private String statName;

    public static MainManFragment newInstance(int id, boolean hasShow, boolean hasLive, boolean hasProduct, String name) {
        MainManFragment mainManFragment = new MainManFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putBoolean("hasShow", hasShow);
        bundle.putBoolean("hasLive", hasLive);
        bundle.putBoolean("hasProduct", hasProduct);
        bundle.putString("name", name);
        mainManFragment.setArguments(bundle);
        return mainManFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
            hasShow = getArguments().getBoolean("hasShow");
            hasLive = getArguments().getBoolean("hasLive");
            hasProduct = getArguments().getBoolean("hasProduct");
            name = getArguments().getString("name");
        }
        dataList = new ArrayList<>();
        if (hasShow) {
            showList = new ArrayList<>();
        }
        if (hasLive) {
            liveList = new ArrayList<>();
        }
        if (hasProduct) {
            recommendProductList = new ArrayList<>();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void prepareView() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        layoutManage = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManage);
        delegateAdapter = new DelegateAdapter(layoutManage, true);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(1, 0);
        viewPool.setMaxRecycledViews(3, 0);
        viewPool.setMaxRecycledViews(4, 0);
        viewPool.setMaxRecycledViews(5, 0);
        viewPool.setMaxRecycledViews(6, 0);
        viewPool.setMaxRecycledViews(7, 0);
        viewPool.setMaxRecycledViews(8, 0);
        viewPool.setMaxRecycledViews(11, 0);
        viewPool.setMaxRecycledViews(12, 0);
        viewPool.setMaxRecycledViews(13, 0);
        viewPool.setMaxRecycledViews(14, 0);
        viewPool.setMaxRecycledViews(15, 0);
        viewPool.setMaxRecycledViews(20, 0);
        viewPool.setMaxRecycledViews(30, 0);
        viewPool.setMaxRecycledViews(39, 0);
        viewPool.setMaxRecycledViews(40, 5);
        recycleView.setRecycledViewPool(viewPool);
        recycleView.setAdapter(delegateAdapter);
        if (!hasSetAdapter){
            addAdapter();
        }
        initListener();
    }


    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isExpand = layoutManage.getOffsetToStart() == 0;
                if (recommendProductAdapter != null) {
                    if (recommendProductAdapter.getLongClickPosition() != -1) {
                        recommendProductList.get(recommendProductAdapter.getLongClickPosition()).setLongClick(false);
                        recommendProductAdapter.setLongClickPosition(-1);
                        recommendProductAdapter.notifyDataSetChanged();
                    }
                }
                if (dataList.size() > 0 && layoutManage.findFirstVisibleItemPosition() > 0 && layoutManage.findLastVisibleItemPosition() > layoutManage.getItemCount() - 4) {
                    loadNextData();
                }
                if (layoutManage.getOffsetToStart() > ScreenUtil.getDisplayHeight() * 2) {
                    if (!pageMore) {
                        ActionBean actionBean = new ActionBean(Constants.ActionType.HOME_UP_VISIABLE);
                        actionBean.put("vis", 1);
                        EventBus.getDefault().post(actionBean);
                        pageMore = true;
                    }
                } else {
                    if (pageMore) {
                        ActionBean actionBean = new ActionBean(Constants.ActionType.HOME_UP_VISIABLE);
                        actionBean.put("vis", 0);
                        EventBus.getDefault().post(actionBean);
                        pageMore = false;
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        recycleView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof BannerHolder) {
                    BannerHolder bannerHolder = (BannerHolder) holder;
                    if (bannerHolder.banner != null) {
                        bannerHolder.banner.pauseScroll();
                    }
                }
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });
    }

    private void loadNextData() {
        if (hasProduct && hasNextProduct && !isLoadProduct) {//加载精选好货
            isLoadProduct = true;
            p++;
            loadRecommend();
        }
    }

    private void refresh() {
        if (showAdapter != null) {
            delegateAdapter.removeAdapter(showAdapter);
            showAdapter = null;
        }
        if (liveAdapter != null) {
            delegateAdapter.removeAdapter(liveAdapter);
            liveAdapter = null;
        }
        if (recommendProductAdapter != null) {
            delegateAdapter.removeAdapter(recommendTitleAdapter);
            delegateAdapter.removeAdapter(recommendProductAdapter);
            recommendTitleAdapter = null;
            recommendProductAdapter = null;
        }
        isRefresh = true;
        isLoadProduct = false;
        p = 0;
        loadData();
    }

    private void loadShow() {
        MainShowApi api = new MainShowApi();
        if (name.equals("女士")) {
            api.subModuleName = "FEMALE";
        } else {
            api.subModuleName = "MALE";
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainShareBean>() {
            @Override
            public void onResponse(MainShareBean shareBean) {
                if (!isVisibleToUser){
                    return;
                }
                if (shareBean.getData().getShares() != null && shareBean.getData().getShares().size() > 0) {
                    showList.clear();
                    showList.addAll(shareBean.getData().getShares());
                    MainShowBinder binder = new MainShowBinder(mContext, showList);
                    if (showAdapter != null) {
                        delegateAdapter.removeAdapter(showAdapter);
                        showAdapter=null;
                    }
                    showAdapter = new BaseVirtualAdapter<MainShowHolder>(binder, 20);
                    delegateAdapter.addAdapter("女士".equals(name) ? 2 : 1, showAdapter);
                }
                loadLive();
            }
        });
    }

    private void loadLive() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GET_MAIN_LIVE_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveListBean>() {
            @Override
            public void onResponse(LiveListBean liveListBean) {
                if (!isVisibleToUser){
                    return;
                }
                if (liveListBean.getData().getLives().getList().size() > 0) {
                    liveList.clear();
                    liveList.addAll(liveListBean.getData().getLives().getList());
                    MainLiveBinder binder = new MainLiveBinder(mContext, liveList);
                    if (liveAdapter != null) {
                        delegateAdapter.removeAdapter(liveAdapter);
                        liveAdapter=null;
                    }
                    liveAdapter = new BaseVirtualAdapter<MainLiveHolder>(binder, 30);
                    delegateAdapter.addAdapter("女士".equals(name) ? 3 : 2, liveAdapter);
                }
                if (hasProduct){
                    isLoadProduct = true;
                    p++;
                    loadRecommend();
                }
            }
        });
    }

    private void loadRecommend() {
        MainSiftGoodApi api = new MainSiftGoodApi();
        api.pageNumber = p;
        if (name.equals("女士")) {
            api.topType = "FEMALE";
        } else if (name.equals("男士")) {
            api.topType = "MALE";
        } else if (name.equals("乐享生活")) {
            api.topType = "FURNITURE";
        } else if (name.equals("鞋包配饰")) {
            api.topType = "BAG";
        } else {
            loadCrossRecommend(p);
            return;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RecommendListBean>() {
            @Override
            public void onResponse(RecommendListBean recommendListBean) {
                if (!isVisibleToUser){
                    return;
                }
                hasNextProduct = recommendListBean.getData().getRecommends().isNext();
                isLoadProduct = false;
                if (recommendListBean.getData().getRecommends().getList() != null && recommendListBean.getData().getRecommends().getList().size() > 0) {
                    if (p == 1) {
                        recommendProductList.clear();
                    }
                    recommendProductList.addAll(recommendListBean.getData().getRecommends().getList());
                    if (recommendProductAdapter == null) {
                        MainTitleBinder titleBinder = new MainTitleBinder(mContext, "精选好货");
                        recommendTitleAdapter = new BaseVirtualAdapter<MainItemTitleHolder>(titleBinder, 39);
                        delegateAdapter.addAdapter(recommendTitleAdapter);
                        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
                        recommendProductAdapter = new MainChoiceAdapter(mContext, getRequestManager(),recommendProductList, itemWidth, "精选好货", statName);
                        delegateAdapter.addAdapter(recommendProductAdapter);
                    } else {
                        recommendProductAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoadProduct = false;
            }
        });
    }

    private void loadCrossRecommend(final int p){
        SimpleApi api=new SimpleApi();
        api.setInterPath(Constants.MAIN_CROSS_GOOD);
        api.setP(p);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RecommendListBean>() {
            @Override
            public void onResponse(RecommendListBean recommendListBean) {
                if (!isVisibleToUser) {
                    return;
                }
                hasNextProduct = recommendListBean.getData().getRecommends().isNext();
                isLoadProduct = false;
                if (recommendListBean.getData().getRecommends().getList() != null && recommendListBean.getData().getRecommends().getList().size() > 0) {
                    if (p == 1) {
                        recommendProductList.clear();
                    }
                    recommendProductList.addAll(recommendListBean.getData().getRecommends().getList());
                    if (recommendProductAdapter == null) {
                        MainTitleBinder titleBinder = new MainTitleBinder(mContext, "精选好货");
                        recommendTitleAdapter = new BaseVirtualAdapter<MainItemTitleHolder>(titleBinder, 39);
                        delegateAdapter.addAdapter(recommendTitleAdapter);
                        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
                        recommendProductAdapter = new MainChoiceAdapter(mContext, recommendProductList, itemWidth, "精选好货", statName);
                        delegateAdapter.addAdapter(recommendProductAdapter);
                    } else {
                        recommendProductAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoadProduct=false;
            }
        });
    }

    private void getLastLocation() {
        View topView = layoutManage.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManage.getPosition(topView);
        }
    }

    @Override
    public void doBusiness() {
        if (dataList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
            loadFlash();
        } else {

        }
    }

    private void addAdapter(){
        if (!hasSetAdapter&&adapter!=null) {
            delegateAdapter.addAdapter(adapter);
            if (hasShow && showAdapter != null) {
                delegateAdapter.addAdapter("女士".equals(name) ? 2 : 1, showAdapter);
            }
            if (hasLive && liveAdapter != null) {
                delegateAdapter.addAdapter("女士".equals(name) ? 3 : 2, liveAdapter);
            }
            if (hasProduct && recommendProductAdapter != null) {
                delegateAdapter.addAdapter(recommendTitleAdapter);
                delegateAdapter.addAdapter(recommendProductAdapter);
            }
            if (lastPosition >= 0) {
                layoutManage.scrollToPositionWithOffset(lastPosition, lastOffer);
            }
            hasSetAdapter = true;
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setP(1);
        api.setPageSize(40);
        api.setInterPath(String.format(Constants.MAINS_URL, "" + id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainBean>() {
            @Override
            public void onResponse(MainBean mainBean) {
                if (!isVisibleToUser || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (isRefresh) {
                    ptr.refreshComplete();
                    dataList.clear();
                    isRefresh = false;
                }
                emptyHintLayout.setVisibility(View.GONE);
                dataList.addAll(mainBean.getData().getContent());
                setMainAdapter();
                if (hasLive){
                    loadShow();
                }else {
                    if (hasProduct){
                        isLoadProduct = true;
                        p++;
                        loadRecommend();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isRefresh) {
                    ptr.refreshComplete();
                }
                if (!(error instanceof ServerError)) {
                    progressBar.setVisibility(View.GONE);
                    emptyHintLayout.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                    btnReload.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void setMainAdapter() {
        if (adapter == null) {
            adapter = new MainManAdapter(mContext,dataList);
            adapter.setFlashBean(mFlashBean);
            adapter.setTag(statName);
            delegateAdapter.addAdapter("女士".equals(name) ? 1 : 0, adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void loadFlash() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/page/recently/flashPromotion");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashBean>() {
            @Override
            public void onResponse(FlashBean flashBean) {
                if (flashBean != null && flashBean.getData().getFlashPromotion() != null) {
                    mFlashBean = flashBean;
                    if (adapter != null) {
                        adapter.setFlashBean(flashBean);
                    }
                }
            }
        });
    }

    @Override
    public void releaseOnInVisible() {
        /*if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(adapter);
            if (hasShow && showAdapter != null) {
                delegateAdapter.removeAdapter(showAdapter);
            }
            if (hasLive && liveAdapter != null) {
                delegateAdapter.removeAdapter(liveAdapter);
            }
            if (hasProduct && recommendProductAdapter != null) {
                delegateAdapter.removeAdapter(recommendTitleAdapter);
                delegateAdapter.removeAdapter(recommendProductAdapter);
            }
            hasSetAdapter = false;
        }*/
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.HOME_UP_VISIABLE) {
            int vis = (int) bean.get("vis");
            if (vis == 0) {
                pageMore = false;
            }
        }
    }

    @Override
    public boolean isExpand() {
        return isExpand;
    }

    @Override
    public void canRefresh(boolean is) {
        canRefresh = is;
    }

    @Override
    public void toUp() {
        int offer=hasShow?2:0;
        if (layoutManage.findFirstVisibleItemPosition()<dataList.size()+offer){
            layoutManage.scrollToPositionWithOffset(dataList.size()+offer ,0);
        }else {
            layoutManage.scrollToPositionWithOffset(0,0);
        }
        //layoutManage.scrollToPositionWithOffset(0,0);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        hasSetAdapter=false;
        super.onDestroyView();
    }

    public MainManFragment setStatName(String statName) {
        this.statName = statName;
        return this;
    }

}
