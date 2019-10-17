package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CategoryBannerAdapter;
import com.d2cmall.buyer.adapter.HotBrandAdapter;
import com.d2cmall.buyer.adapter.TestAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.AdBean;
import com.d2cmall.buyer.bean.HotBrandBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.d2cmall.buyer.Constants.AD_HOT_BRAND;

/**
 * Created by rookie on 2017/8/2.
 */

public class BrandHotFragment extends BaseFragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private BaseVirtualAdapter<? extends RecyclerView.ViewHolder> gridAdapter;
    private GridLayoutHelper grid;
    private DelegateAdapter delegateAdapter;
    private List<DelegateAdapter.Adapter> adapters;
    List<String> bannerItems;
    List<HotBrandBean.DataBean.DesignersBean.ListBean> navigations;
    CategoryBannerAdapter bannerAdapter;
    AdBean adbean;
    HotBrandAdapter hotBrandAdapter;
    VirtualLayoutManager layoutManager;
    private List<Object> list = new ArrayList<>();
    int itemWith;
    TestAdapter testAdapter;
    private boolean hasRemoveAdapter = false;
    private LayoutAnimationController controller;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_brand, container, false);
    }

    @Override
    public void prepareView() {
        bannerItems = new ArrayList<>();
        navigations = new ArrayList<>();
        controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_layout_category);
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        recycleView.setPadding(ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16), 0);
        layoutManager = new VirtualLayoutManager(mContext);
        itemWith = (recycleView.getWidth() - ScreenUtil.dip2px(32)) / 3;
        testAdapter = new TestAdapter(list, mContext, itemWith);
        recycleView.setLayoutManager(layoutManager);
        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 10);
        recycleView.setRecycledViewPool(viewPool);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        delegateAdapter.addAdapter(testAdapter);
        recycleView.setAdapter(delegateAdapter);
    }

    @Override
    public void doBusiness() {
        if (list != null && list.size() == 0) {
            initAd();
        }
        if (list != null && list.size() > 0 && hasRemoveAdapter) {
            if (delegateAdapter != null) {
                delegateAdapter.addAdapter(testAdapter);
                runLayoutAnimation(recycleView);
            }
        }
    }


    private void initAd() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(AD_HOT_BRAND);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdBean>() {
            @Override
            public void onResponse(AdBean response) {
                adbean = response;
                list.add(adbean.getData().getAdResource());
                bannerItems.add(adbean.getData().getAdResource().getPic());
                initData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                initData();
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        recyclerView.setLayoutAnimation(controller);
    }

    private void initData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.BRAND_HOT);
        api.setMethod(BaseApi.Method.GET);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<HotBrandBean>() {
            @Override
            public void onResponse(HotBrandBean kindBean) {
                navigations.addAll(kindBean.getData().getDesigners().getList());
                list.add(kindBean.getData().getDesigners());
                //list.addAll(navigations);
                if (testAdapter != null) {
                    testAdapter.notifyDataSetChanged();
                    runLayoutAnimation(recycleView);
                }
                initRecycler();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(testAdapter);
            hasRemoveAdapter = true;
        }
    }

    @Override
    public void onDestroyView() {
        layoutManager=null;
        delegateAdapter=null;
        super.onDestroyView();
    }

    private void initRecycler() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (testAdapter.getItemViewType(position) == 0) {
                    return 3;
                }
                return 1;
            }
        });
        if (recycleView != null && progressBar != null) {
            recycleView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }
}
