package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FlashProductActivity;
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.adapter.FlashBuyAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FlashApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.FlashBrandListBean;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.StopRefreshListener;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FlashNoticePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/4/28.
 * 无论是抢大牌还是商品限时购,每个场次的fragment都是用这个,因为都是列表
 */

public class FlashProductSubFragment extends BaseFragment {


    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.nest_scroll)
    NestedScrollView nestedScrollView;
    @Bind(R.id.iv_error)
    ImageView ivError;
    @Bind(R.id.rl_all)
    RelativeLayout rlAll;

    private int promotionId, type;//type为1是拉取商品,为2是拉取大牌(知道大牌什么意思吗?就是贼牛逼的牌子)

    private int p = 1;

    private FlashBuyAdapter flashBuyAdapter;//列表所用的adapter,抢大牌和商品限时购都是用这个adapter
    private List<Object> list;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private boolean hasNext, hasSetAdapter;
    private StopRefreshListener stopRefreshListener;
    public int j = 0;

    public static FlashProductSubFragment newInstance(int promotionId, int type) {
        //传入promotionId和用来区别抢大牌和限时购的type
        FlashProductSubFragment flashProductSubFragment = new FlashProductSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("promotionId", promotionId);
        bundle.putInt("type", type);
        flashProductSubFragment.setArguments(bundle);
        return flashProductSubFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_flash_layout, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传来的数据
        promotionId = getArguments().getInt("promotionId");
        type = getArguments().getInt("type");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setStopRefreshListener(StopRefreshListener stopRefreshListener) {
        //向外暴露一个方法,设置通知停止刷新的回调
        this.stopRefreshListener = stopRefreshListener;
    }

    private void noticeOn(long id) {
        //点击提醒我的方法,显示一个弹窗
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/flashpromotion/remind/" + id);
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(mContext);
                noticePop.show(getActivity().getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            }
        });
    }

    public void refresh() {
        //向外暴露一个刷新的方法,外界调用,同时将页码等变量初始化
        p = 1;
        j = 0;
        if (type == 1) {
            loadProduct();
        } else {
            loadBrand();
        }
    }

    @Override
    public void prepareView() {
        //初始化变量
        list = new ArrayList<>();
        j = 0;
        p = 1;
        flashBuyAdapter = new FlashBuyAdapter(list, mContext);
        if (type == 1) {
            //只有商品限时购才会有提醒我功能,这里做了下判断
            flashBuyAdapter.setCallBack(new FlashBuyAdapter.FlashProductRemindCallBack() {
                @Override
                public void notice(long id) {
                    noticeOn(id);
                }
            });
        }
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(flashBuyAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualLayoutManager.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            if (type == 1) {
                                loadProduct();
                            } else {
                                loadBrand();
                            }
                        }
                }
            }
        });

    }

    private void loadProduct() {
        //这是拉取商品限时购的数据
        FlashApi api = new FlashApi();
        api.setInterPath("/v3/api/flashpromotion/products/list");
        api.promotionId = promotionId;
        api.setPageNumber(p);
        api.setPageSize(30);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashProductListBean>() {
            @Override
            public void onResponse(FlashProductListBean response) {
                if (!isVisibleToUser||nestedScrollView==null) {
                    return;
                }
                if (p == 1) {
                    if (stopRefreshListener != null) {
                        stopRefreshListener.stopRefresh();
                    }
                    list.clear();
                }
                nestedScrollView.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
                if (response.getData().getFlashPromotion().getStartTimeStamp() < System.currentTimeMillis()) {
                    flashBuyAdapter.setStart(false);
                } else {
                    flashBuyAdapter.setStart(true);
                }
                hasNext = response.getData().getProducts().isNext();
                list.addAll(response.getData().getProducts().getList());
                //这是将部分品牌插入到商品限时购列表中,每四个插一个,由于涉及到了分页,所以用j记录了一下插入位置
                if (response.getData().getBrandFlashPromotions() != null && response.getData().getBrandFlashPromotions().size() > 0) {
                    for (int i = j; i < response.getData().getBrandFlashPromotions().size(); i++, j++) {
                        if (list.size() >= i * 5 + 4 && response.getData().getBrandFlashPromotions().get(i).notAdd) {
                            response.getData().getBrandFlashPromotions().get(i).notAdd = false;
                            list.add(i * 5 + 4, response.getData().getBrandFlashPromotions().get(i));
                        }
                    }
                }
                flashBuyAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                rlAll.setBackgroundColor(Color.parseColor("#fafafc"));
                recycleView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (list.size() == 0) {
                    rlAll.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    nestedScrollView.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void loadBrand() {
        //这是拉取抢大牌的数据
        FlashApi api = new FlashApi();
        api.setInterPath("/v3/api/flashpromotion/brand/list");
        api.promotionId = promotionId;
        api.setPageNumber(p);
        api.setPageSize(30);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashBrandListBean>() {
            @Override
            public void onResponse(FlashBrandListBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                if (p == 1) {
                    if (stopRefreshListener != null) {
                        stopRefreshListener.stopRefresh();
                    }
                    list.clear();
                }
                nestedScrollView.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
                list.addAll(response.getData().getBrands());
                flashBuyAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (list.size() == 0) {
                    nestedScrollView.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.GONE);
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                if (recycleView != null) {
                    recycleView.setVisibility(View.VISIBLE);
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void doBusiness() {
        if (list.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
            if (type == 1) {//type为1拉取商品
                Log.e("tag3", "loadProduct" + promotionId);
                loadProduct();
            } else {//另外就是拉取品牌
                loadBrand();
            }

        }
    }


}
