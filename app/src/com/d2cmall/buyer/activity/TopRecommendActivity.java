package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.TopRecommendBean;
import com.d2cmall.buyer.fragment.TopRecommendFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopRecommendActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_right)
    TextView mTitleRight;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.sliding_tab)
    public SlidingTabLayout mSlidingTab;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabPagerAdapter tabPagerAdapter;
    public List<Integer> categoryIds;
    private int currentCategoryId;
    private int currentPosition;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_recommend);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        categoryIds =new ArrayList<>();
        prepareView();
        doBusiness();
    }

    private void doBusiness() {
        currentPosition = getIntent().getIntExtra("position", -1);
        currentCategoryId=getIntent().getIntExtra("currentCategoryId", -1);
        categoryName=getIntent().getStringExtra("categoryName");
        lazyLoad();
    }

    private void lazyLoad() {
        if (titles.size() == 0) {
            requestDataTask();
        }
    }

    private void prepareView() {
        mNameTv.setText(R.string.label_top_recommend);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(tabPagerAdapter);
        mSlidingTab.setViewPager(mViewPager);
    }

    private void requestDataTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_TOP_RECOMMEND, 15));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TopRecommendBean>() {
            @Override
            public void onResponse(TopRecommendBean topRecommendBean) {
                if (topRecommendBean != null && topRecommendBean.getList() != null && topRecommendBean.getList().size() > 0) {
                    titles.clear();
                    categoryIds.clear();
                    fragments.clear();
                    int size = topRecommendBean.getList().size();
                    for (int i = 0; i < size; i++) {
                        titles.add(topRecommendBean.getList().get(i).getCategoryName());
                        categoryIds.add(topRecommendBean.getList().get(i).getCategoryId());
                        fragments.add(TopRecommendFragment.newInstance(topRecommendBean.getList().get(i).getCategoryId(), false, false));
                    }
                    mSlidingTab.getChildAt(0).setSelected(true);
                    if(currentPosition!=-1){
                        tabPagerAdapter.notifyDataSetChanged();
                        mViewPager.setCurrentItem(currentPosition);
                    }
                    if(currentCategoryId!=-1 && categoryIds.contains(currentCategoryId)){//有跳转的cateGoryId,直接跳到对应界面
                        int categoryIdIndex = categoryIds.indexOf(currentCategoryId);
                        tabPagerAdapter.notifyDataSetChanged();
                        mViewPager.setCurrentItem(categoryIdIndex);
                    }else if(currentCategoryId!=-1){    //没有需要跳转的跳cateGoryId,手动添加数据到集合(手动加了一个分类tab)
                        if(!Util.isEmpty(categoryName)){
                            titles.add(categoryName);
                        }
                        categoryIds.add(currentCategoryId);
                        fragments.add(TopRecommendFragment.newInstance(currentCategoryId, false, false));
                        tabPagerAdapter.notifyDataSetChanged();
                        mViewPager.setCurrentItem(fragments.size()-1);
                    }
                    tabPagerAdapter.notifyDataSetChanged();
                    mSlidingTab.setViewPager(mViewPager);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.checkErrorType(error);
            }
        });
    }


    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
