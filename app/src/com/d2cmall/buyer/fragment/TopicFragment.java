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
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TopicRecyclerViewAdapter;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/18.
 */

public class TopicFragment extends BaseFragment {
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
    @Bind(R.id.btn_back_top)
    ImageView btnBackTop;
    private TopicRecyclerViewAdapter topicAdapter;
    private List<TopicBean> list = new ArrayList<>();
    private String keyword;

    public static TopicFragment newInstance(String keyword) {
        TopicFragment topicFragment = new TopicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        topicFragment.setArguments(bundle);
        return topicFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            keyword = getArguments().getString("keyword");
        }
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void prepareView() {
        loadData();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        LinkedList<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        //topicAdapter=new TopicRecyclerViewAdapter(getContext(),gridLayoutHelper,list);
        adapters.add(topicAdapter);
        delegateAdapter.addAdapters(adapters);
    }

    private void loadData() {
        for (int i = 0; i < 15; i++) {
            list.add(new TopicBean());
        }
    }

    @Override
    public void doBusiness() {

    }


    @OnClick({R.id.btn_reload, R.id.btn_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reload:
                break;
            case R.id.btn_back_top:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
