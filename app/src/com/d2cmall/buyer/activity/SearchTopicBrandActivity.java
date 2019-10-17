package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BrandListAdapter;
import com.d2cmall.buyer.adapter.TopicRecyclerViewAdapter;
import com.d2cmall.buyer.api.SearchBrandApi;
import com.d2cmall.buyer.api.SearchThemeApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.bean.ThemeBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/14.
 * 搜索品牌以及专题页面
 */

public class SearchTopicBrandActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.et_account)
    ClearEditText etAccount;
    @Bind(R.id.iv_reset)
    ImageView ivReset;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.empty_image)
    ImageView emptyImage;
    private String type;
    private List<DesignerBean.DataEntity.DesignersEntity.ListEntity> brandList;
    private BrandListAdapter brandListAdapter;
    private VirtualLayoutManager layoutManager;
    private TopicRecyclerViewAdapter topicAdapter;
    private DelegateAdapter delegateAdapter;
    private List<ThemeBean.DataBean.ThemesBean.ListBean> themeList;
    int page = 1;
    boolean hasNext;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_topic_brand);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        brandList = new ArrayList<>();
        themeList = new ArrayList<>();
        layoutManager = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager,true);
        recycleView.setAdapter(delegateAdapter);
        if (type.equals("theme")) {
            etAccount.setHint("搜索专题");
            initThemeRecyclerView();
        } else if (type.equals("brand")) {
            etAccount.setHint("搜索品牌");
            initBrandRecyclerView();
        }
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = layoutManager.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            page++;
                            loadData(string);
                        }
                }
            }
        });
        etAccount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(null);
                    page = 1;
                    string = etAccount.getText().toString().trim();
                    loadData(string);
                    return true;
                }
                return false;
            }

        });
    }

    private void initBrandRecyclerView() {
        brandListAdapter = new BrandListAdapter(this, brandList);
        delegateAdapter.addAdapter(brandListAdapter);
    }

    private void initThemeRecyclerView() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setGap(ScreenUtil.dip2px(8));
        gridLayoutHelper.setAutoExpand(false);
        topicAdapter = new TopicRecyclerViewAdapter(this, gridLayoutHelper, themeList);
        delegateAdapter.addAdapter(topicAdapter);
    }

    private void loadData(String string) {
        recycleView.setVisibility(View.GONE);
        if (type.equals("theme")) {
            SearchThemeApi api = new SearchThemeApi();
            api.setPageNumber(page);
            api.setKeyword(string);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ThemeBean>() {
                @Override
                public void onResponse(ThemeBean response) {
                    if (page == 1) {
                        themeList.clear();
                    }
                    if (response.getData().getThemes().getList().size() > 0) {
                        themeList.addAll(response.getData().getThemes().getList());
                    }
                    if (themeList.size() > 0) {
                        emptyImage.setVisibility(View.GONE);
                        recycleView.setVisibility(View.VISIBLE);
                    } else {
                        emptyImage.setVisibility(View.VISIBLE);
                        emptyImage.setImageResource(R.mipmap.ic_empty_search);
                        recycleView.setVisibility(View.GONE);
                    }
                    hasNext = response.getData().getThemes().isNext();
                    topicAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    emptyImage.setVisibility(View.VISIBLE);
                    emptyImage.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.GONE);
                    Util.showToast(SearchTopicBrandActivity.this, Util.checkErrorType(error));
                }
            });
        } else if (type.equals("brand")) {
            SearchBrandApi api = new SearchBrandApi();
            api.setPageNumber(page);
            api.setKeyword(string);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DesignerBean>() {
                @Override
                public void onResponse(DesignerBean response) {
                    if (page == 1) {
                        brandList.clear();
                    }
                    if (response.getData().getDesigners().getList().size() > 0) {
                        brandList.addAll(response.getData().getDesigners().getList());
                    }
                    if (brandList.size() > 0) {
                        emptyImage.setVisibility(View.GONE);
                        recycleView.setVisibility(View.VISIBLE);
                    } else {
                        emptyImage.setVisibility(View.VISIBLE);
                        emptyImage.setImageResource(R.mipmap.ic_empty_search);
                        recycleView.setVisibility(View.GONE);
                    }
                    hasNext = response.getData().getDesigners().isNext();
                    brandListAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    emptyImage.setVisibility(View.VISIBLE);
                    emptyImage.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.GONE);
                    Util.showToast(SearchTopicBrandActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    @OnClick({R.id.back_iv, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                hideKeyboard(null);
                page = 1;
                string = etAccount.getText().toString().trim();
                loadData(string);
                break;
        }
    }
}
