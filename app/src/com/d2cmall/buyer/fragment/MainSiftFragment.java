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
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.MainManAdapter;
import com.d2cmall.buyer.adapter.MainLikeAdapter;
import com.d2cmall.buyer.adapter.MainSpecialAdapter;
import com.d2cmall.buyer.api.MainLiveApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.bean.MainBrandBean;
import com.d2cmall.buyer.bean.MainSpecailBean2;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.binder.MainTitleBinder;
import com.d2cmall.buyer.holder.MainItemTitleHolder;
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
 * 我的精选
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 15:29
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainSiftFragment extends BaseFragment implements ExpandListener, UpListener {

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

    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;

    private List<MainBean.DataEntity.ContentEntity> dataList;
    private MainManAdapter adapter;

    private boolean isSetSpecialAdapter;
    private MainSpecialAdapter specialAdapter;

    private List<ProductDetailBean.DataBean.RecommendProductsBean> recommendProductList;
    private BaseVirtualAdapter<MainItemTitleHolder> recommendTitleAdapter;
    private MainLikeAdapter recommendProductAdapter;

    private int lastPosition = -1; //页面location
    private int lastOffer;

    private boolean hasSetAdapter=true;
    private boolean isExpand = true;
    private boolean canRefresh;
    private int id; //页面id
    private boolean isRefresh;
    private boolean pageMore; //是否超過一屏

    public static MainSiftFragment newInstance(int id) {
        MainSiftFragment siftFragment = new MainSiftFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        siftFragment.setArguments(bundle);
        return siftFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
        dataList = new ArrayList<>();
        recommendProductList = new ArrayList<>();
        EventBus.getDefault().register(this);
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
                reFresh();
            }
        });
        layoutManager = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(1, 0);
        viewPool.setMaxRecycledViews(21, 0);
        viewPool.setMaxRecycledViews(22, 0);
        viewPool.setMaxRecycledViews(23, 0);
        viewPool.setMaxRecycledViews(24, 0);
        viewPool.setMaxRecycledViews(25, 0);
        viewPool.setMaxRecycledViews(29, 0);
        viewPool.setMaxRecycledViews(30, 5);
        recycleView.setRecycledViewPool(viewPool);
        recycleView.setAdapter(delegateAdapter);
        if (specialAdapter == null) {
            specialAdapter = new MainSpecialAdapter(getActivity());
        }
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
                isExpand = layoutManager.getOffsetToStart() == 0;
                if (recommendProductAdapter != null) {
                    if (recommendProductAdapter.getLongClickPosition() != -1) {
                        recommendProductList.get(recommendProductAdapter.getLongClickPosition()).setLongClick(false);
                        recommendProductAdapter.setLongClickPosition(-1);
                        recommendProductAdapter.notifyDataSetChanged();
                    }
                }
                if (layoutManager.getOffsetToStart() > ScreenUtil.getDisplayHeight() * 2) {
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
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                loadData();
                loadPromotionTop();
            }
        });
    }

    @Override
    public void doBusiness() {
        if (dataList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
            loadPromotionTop();
        } else {

        }
    }

    private void addAdapter(){
        if (!hasSetAdapter&&adapter!=null&&specialAdapter!=null) {
            delegateAdapter.addAdapter(adapter);
            delegateAdapter.addAdapter(specialAdapter);
            if (recommendProductAdapter != null) {
                delegateAdapter.addAdapter(recommendTitleAdapter);
                delegateAdapter.addAdapter(recommendProductAdapter);
            }
            if (lastPosition >= 0) {
                layoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
            }
            hasSetAdapter = true;
        }
    }

    public void reFresh() {
        if (isSetSpecialAdapter) {
            delegateAdapter.removeAdapter(specialAdapter);
        }
        if (recommendProductAdapter != null) {
            delegateAdapter.removeAdapter(recommendTitleAdapter);
            delegateAdapter.removeAdapter(recommendProductAdapter);
            recommendProductAdapter = null;
        }
        loadData();
        loadPromotionTop();
        isRefresh = true;
    }

    private void getLastLocation() {
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setP(1);
        api.setPageSize(10);
        api.setInterPath(String.format(Constants.MAINS_URL, "" + id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainBean>() {
            @Override
            public void onResponse(MainBean mainBean) {
                progressBar.setVisibility(View.GONE);
                if (isRefresh) {
                    ptr.refreshComplete();
                }
                dataList.clear();
                dataList.addAll(mainBean.getData().getContent());
                setMainAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (isRefresh) {
                    ptr.refreshComplete();
                }
                imgHint.setVisibility(View.VISIBLE);
                btnReload.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        });
    }

    private void setMainAdapter() {
        if (adapter == null) {
            adapter = new MainManAdapter(getActivity(), dataList);
            adapter.setTag("我的精选");
            delegateAdapter.addAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 情报站
     */
    private void loadPromotionTop() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_MAIN_PROMOTION_TOP, 15));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    specialAdapter.setData1(response.getList());
                }
                loadLiked();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Util.checkErrorType(error);
                loadLiked();
            }
        });
    }

    /**
     * 榜单推荐
     */
    private void loadTopRecommend() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_TOP_RECOMMEND, 6));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    specialAdapter.setData3(response.getList());
                }
                loadLive();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadLive();
            }
        });
    }

    /**
     * 同兴趣购买
     */
    private void loadLiked() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/page/recently/sales");
        api.setP(1);
        api.setPageSize(5);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainSpecailBean2>() {
            @Override
            public void onResponse(MainSpecailBean2 response) {
                if (response.getData().getRecentlySalesProduct() != null && response.getData().getRecentlySalesProduct().size() > 0) {
                    int size = response.getData().getRecentlySalesProduct().size() > 5 ? 5 : response.getData().getRecentlySalesProduct().size();
                    List<MainSpecailBean2.DataBean.RecentlySalesProductBean> list = new ArrayList<MainSpecailBean2.DataBean.RecentlySalesProductBean>();
                    for (int i = 0; i < size; i++) {
                        list.add(response.getData().getRecentlySalesProduct().get(i));
                    }
                    specialAdapter.setData2(list);
                }
                loadTopRecommend();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadTopRecommend();
            }
        });
    }

    /**
     * 直播
     */
    private void loadLive() {
        MainLiveApi api = new MainLiveApi();
        api.status = 4;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveListBean>() {
            @Override
            public void onResponse(LiveListBean response) {
                if (response.getData().getLives() != null && response.getData().getLives().getList() != null && response.getData().getLives().getList().size() > 0) {
                    specialAdapter.setData4(response.getData().getLives().getList().get(0));
                }
                loadBrand();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadBrand();
            }
        });
    }

    /**
     * 大牌
     */
    private void loadBrand() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/page/mybrand/recommend");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainBrandBean>() {
            @Override
            public void onResponse(MainBrandBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                if (response.getData().getRecommendBrandPic() != null && response.getData().getRecommendBrandPic().size() > 0) {
                    specialAdapter.setData5(response);
                }
                delegateAdapter.addAdapter(specialAdapter);
                isSetSpecialAdapter = true;
                loadRecommend();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                delegateAdapter.addAdapter(specialAdapter);
                isSetSpecialAdapter = true;
                loadRecommend();
            }
        });
    }

    /**
     * 猜你喜欢
     */
    private void loadRecommend() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_MAIN_LIKE_LIST, 50));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                recommendProductList.clear();
                if (response.getList() != null && response.getList().size() > 0) {
                    recommendProductList.addAll(response.getList());
                }
                if (recommendProductAdapter == null) {
                    MainTitleBinder titleBinder = new MainTitleBinder(getActivity(), "猜你喜欢");
                    recommendTitleAdapter = new BaseVirtualAdapter<MainItemTitleHolder>(titleBinder, 29);
                    delegateAdapter.addAdapter(recommendTitleAdapter);
                    int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
                    recommendProductAdapter = new MainLikeAdapter(getActivity(), recommendProductList, itemWidth, "猜你喜欢", "我的精选");
                    delegateAdapter.addAdapter(recommendProductAdapter);
                } else {
                    recommendProductAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void releaseOnInVisible() {
        /*if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(adapter);
            if (isSetSpecialAdapter) {
                delegateAdapter.removeAdapter(specialAdapter);
            }
            if (recommendProductAdapter != null) {
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
        layoutManager.smoothScrollToPosition(recycleView, null, 0);
        //layoutManager.scrollToPositionWithOffset(0,0);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        hasSetAdapter=false;
        super.onDestroyView();
    }
}
