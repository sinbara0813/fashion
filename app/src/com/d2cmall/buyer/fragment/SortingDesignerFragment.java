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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.LettreAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.DesignerLettreBean;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.SideBar;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersListView;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/7/29.
 */

public class SortingDesignerFragment extends BaseFragment {

    private ArrayList<SectionBean> sectionBeans;
    private LettreAdapter adapter;
    private ProgressBar progressBar;
    private StickyListHeadersListView listView;
    private boolean isPrepared;
    private SideBar sideBar;
    private static boolean isP;
    private LayoutAnimationController controller;

    public static SortingDesignerFragment newInstance(boolean isP) {
        SortingDesignerFragment fragment = new SortingDesignerFragment();
        isP = isP;
        return fragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort_desiner, container, false);
    }

    @Override
    public void prepareView() {
        sectionBeans = new ArrayList<>();
        adapter = new LettreAdapter(getActivity());
        controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.sort_animation);
        listView = (StickyListHeadersListView) rootView.findViewById(R.id.listView);
        listView.setLayoutAnimation(controller);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        sideBar = (SideBar) rootView.findViewById(R.id.sideBar);
        sideBar.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        TextView dialogText = (TextView) rootView.findViewById(R.id.dialog_text);
        sideBar.setTextView(dialogText);
        listView.setAdapter(adapter);
        adapter.setKeyword(isP);
        sideBar.setListView(listView);
        isPrepared = true;
        initData();
    }


    private void initData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.DESIGNER_LETTRE_URL));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DesignerLettreBean>() {
            @Override
            public void onResponse(DesignerLettreBean designerLettreBean) {
                int size = designerLettreBean.getData().getLetter().size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        if (designerLettreBean.getData().getLetter().get(i).getList() != null && designerLettreBean.getData().getLetter().get(i).getList().size() > 0) {
                            for (int j = 0; j < designerLettreBean.getData().getLetter().get(i).getList().size(); j++) {
                                DesignerLettreBean.DataBean.LetterBean.ListBean listEntity = designerLettreBean.getData().getLetter().get(i).getList().get(j);
                                SectionBean sectionBean = new SectionBean();
                                sectionBean.setId(listEntity.getId());
                                sectionBean.setName(listEntity.getDesigner());
                                sectionBean.setBrand(listEntity.getName());
                                sectionBean.setLettre(designerLettreBean.getData().getLetter().get(i).getLetter());
                                sectionBean.setGroupId(i);
                                sectionBean.setRecommend(listEntity.getRecommend());
                                sectionBeans.add(sectionBean);
                            }
                        }
                    }
                }
                adapter.refresh(sectionBeans);
                runLayoutAnimation(listView);
                progressBar.setVisibility(View.GONE);
                sideBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
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

    private void runLayoutAnimation(ListView recyclerView) {
        adapter.refresh(sectionBeans);
        recyclerView.setLayoutAnimation(controller);
    }

    @Override
    public void doBusiness() {
        runLayoutAnimation(listView);
    }
}
