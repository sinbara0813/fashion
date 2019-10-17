package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.d2cmall.buyer.adapter.BrandSpecificAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.BrandKindBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by rookie on 2017/7/29.
 */

public class BrandSpecificFragment extends BaseFragment {
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager layoutManager;
    private GridLayoutHelper gridLayoutHelper;
    private BrandSpecificAdapter gridAdapter;

    private List<BrandKindBean.DataBean.DatasBean> list = new ArrayList<>();
    private String typec;
    private String name;
    private List<DelegateAdapter.Adapter> adapters;
    private boolean hasRemoveAdapter = false;
    private long animationStartTime;
    private LayoutAnimationController controller;
    public ChangeDataListener changeDataListener = new ChangeDataListener() {
        @Override
        public void changeData(String type) {
            reloadData(type);
        }
    };

    private void reloadData(String type) {
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        typec = type;
        if (list != null) {
            list.clear();
            if (gridAdapter != null) {
                gridAdapter.notifyDataSetChanged();
            }
        }
        if (!isHidden()) {
            initData(type);
        }
        //initData(type);
    }

    public static BrandSpecificFragment newInstance(String type) {
        BrandSpecificFragment fragment = new BrandSpecificFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            typec = getArguments().getString("type");
            if (typec.equals("style")) {
                name = "按风格";
            } else if (typec.equals("country")) {
                name = "按地区";
            } else {
                name = "按分类";
            }
        }
        return inflater.inflate(R.layout.fragment_specific_brand, container, false);
    }

    @Override
    public void prepareView() {
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_layout_category);
        layoutManager= new VirtualLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setMarginLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setMarginRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        gridLayoutHelper.setMarginBottom(ScreenUtil.dip2px(16));
        gridLayoutHelper.setGap(ScreenUtil.dip2px(8));
        gridAdapter = new BrandSpecificAdapter(gridLayoutHelper, list, getActivity(), name);
        delegateAdapter.addAdapter(gridAdapter);
    }


    @Override
    public void doBusiness() {
        if (list != null && list.size() == 0) {
            if(hasRemoveAdapter){
                if (delegateAdapter != null) {
                    delegateAdapter.addAdapter(gridAdapter);
                    hasRemoveAdapter = false;
                }
            }
            initData(typec);
        }
        if (list != null && list.size() > 0 && hasRemoveAdapter) {
            if (delegateAdapter != null) {
                delegateAdapter.addAdapter(gridAdapter);
                hasRemoveAdapter = false;
            }
            //runLayoutAnimation(recycleView);
        }
    }

    private void initData(String type) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.DESIGNER_KIND_URL, type));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandKindBean>() {
            @Override
            public void onResponse(BrandKindBean brandKindBean) {
                list.addAll(brandKindBean.getData().getDatas());
                if (gridAdapter != null) {
                    if (!isHidden()) {
                        runLayoutAnimation(recycleView);
                    }
                    gridAdapter.notifyDataSetChanged();
                }
                recycleView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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

    private void runLayoutAnimation(RecyclerView recyclerView) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - animationStartTime > 1000) {
            recyclerView.setLayoutAnimation(controller);
            animationStartTime = currentTime;
            recyclerView.invalidate();
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(gridAdapter);
            hasRemoveAdapter = true;
        }
    }

    @Override
    public void onDestroyView() {
        layoutManager=null;
        delegateAdapter=null;
        super.onDestroyView();
    }

    public interface ChangeDataListener {
        void changeData(String type);
    }

}
