package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CatagoryNameAdapter;
import com.d2cmall.buyer.adapter.CategoryAdapter;
import com.d2cmall.buyer.adapter.CategoryBannerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.CategoryBannerBean;
import com.d2cmall.buyer.bean.KindBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * 分类页面商品下的右边Fragment,即商品的详细分类
 * Created by rookie on 2017/7/28.
 */

public class CategoryDetailFragment extends BaseFragment {


    @Bind(R.id.recycler)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.error_image)
    ImageView errorImage;
    private Bundle bundle;
    private DelegateAdapter delegateAdapter;
    List<KindBean.DataEntity.NavigationsEntity> navigations;
    private ArrayList<String> pics;
    private ArrayList<String> url;
    private List<Object> list = new ArrayList<>();
    private int id;
    private int position = 0;
    private int lastId;
    private CategoryAdapter categoryAdapter;
    private VirtualLayoutManager layoutManager;
    private boolean hasRemoveAdapter = false;
    private String lastName, name;
    private LayoutAnimationController controller;
    private long animationStartTime;
    ChangeDataListener changeDataLisener = new ChangeDataListener() {
        @Override
        public void changeData(int id, int position2, String name) {
            if (id != lastId) {
                lastId = id;
                lastName = name;
                reloadData(id);
            }
            position = position2;
        }
    };

    private void reloadData(int id) {
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        list.clear();
        if (categoryAdapter != null) {
            categoryAdapter.notifyDataSetChanged();
        }
        if (navigations != null) {
            navigations.clear();
        }
        initData(id);
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_category, container, false);
    }

    @Override
    public void prepareView() {
        controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_layout_category);
        layoutManager = new VirtualLayoutManager(getContext());
        recycleView.setPadding(ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16), 0);
        recycleView.setHasFixedSize(true);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        int itemWith = (ScreenUtil.getDisplayWidth()*3/4 - ScreenUtil.dip2px(32));
        categoryAdapter = new CategoryAdapter(list, getContext(), itemWith);
        delegateAdapter.addAdapter(categoryAdapter);
        recycleView.setAdapter(delegateAdapter);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        id = bundle.getInt("id");
        name = bundle.getString("name");
        pics = bundle.getStringArrayList("pics");
        url = bundle.getStringArrayList("url");
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(categoryAdapter);
            hasRemoveAdapter = true;
        }
    }

    private void initData(int id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GOOD_URL, "" + id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<KindBean>() {
            @Override
            public void onResponse(KindBean kindBean) {
                if (kindBean.getData().getNavigations() != null && kindBean.getData().getNavigations().size() > 0) {
                    initRecycler(kindBean);
                } else {
                    errorImage.setVisibility(View.VISIBLE);
                    errorImage.setImageResource(R.mipmap.ic_empty_work);
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                    errorImage.setImageResource(R.mipmap.ic_no_net);
                    errorImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecycler(KindBean kindBean) {
        navigations = kindBean.getData().getNavigations();
        categoryAdapter.setName(lastName);
        CategoryBannerBean categoryBannerBean = new CategoryBannerBean();
        String urlString = "";
        if (url != null && url.size() > 0) {
            urlString = url.get(position);
        }
        categoryBannerBean.setPic(pics.get(position));
        categoryBannerBean.setUrl(urlString);
        list.add(categoryBannerBean);

        for (int i = 0; i < navigations.size(); i++) {
            final KindBean.DataEntity.NavigationsEntity kn = navigations.get(i);
            list.add(kn.getName()+","+kn.getUrl());
            list.add(kn);
        }
        recycleView.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        if (categoryAdapter != null) {
            runLayoutAnimation(recycleView);
            categoryAdapter.notifyDataSetChanged();
        }
        layoutManager.scrollToPosition(0);
    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - animationStartTime > 1000) {
            recyclerView.setLayoutAnimation(controller);
            animationStartTime = currentTime;
        }
    }

    @Override
    public void doBusiness() {
        if (list != null && list.size() > 0 && hasRemoveAdapter) {
            delegateAdapter.addAdapter(categoryAdapter);
            hasRemoveAdapter = false;
        }
        if (list != null && list.size() == 0) {
            lastId = id;
            lastName = name;
            recycleView.setVisibility(View.GONE);
            errorImage.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            initData(id);
        }
    }

    @Override
    public void onDestroyView() {
        layoutManager=null;
        delegateAdapter=null;
        super.onDestroyView();
    }

    public interface ChangeDataListener {
        void changeData(int id, int position, String name);
    }

}
