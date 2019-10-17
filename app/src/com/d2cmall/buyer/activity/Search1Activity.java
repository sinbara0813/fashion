package com.d2cmall.buyer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.AnalyzeHotSearchApi;
import com.d2cmall.buyer.api.HotSearchApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.HotSearchBean;
import com.d2cmall.buyer.bean.KindTagBean;
import com.d2cmall.buyer.bean.SearchBean;
import com.d2cmall.buyer.fragment.SmartSearchFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/18.
 * 搜索页面
 */

public class Search1Activity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.iv_reset)
    ImageView ivReset;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.iv_clear_history)
    ImageView ivClearHistory;
    @Bind(R.id.flow_history)
    FlowLayout flowHistory;
    @Bind(R.id.rl_history_search)
    RelativeLayout rlHistorySearch;
    @Bind(R.id.tv_hot_search)
    TextView tvHotSearch;
    @Bind(R.id.flow_hot)
    FlowLayout flowHot;
    @Bind(R.id.rl_hot_search)
    RelativeLayout rlHotSearch;
    @Bind(R.id.tv_recommend)
    TextView tvRecommend;
    @Bind(R.id.recommend_layout)
    LinearLayout recommendLayout;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.ll_smart_search)
    LinearLayout llSmartSearch;
    private String keyword;
    private ArrayList<SearchBean> histories;
    private SmartSearchFragment smartSearchFragment;
    private long defaultHotSearchId = -1;
    private int subModuleId=-1;
    private String hint;
    private HotSearchBean.DataBean.MemberSearchSumListBean hintSearchBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1search);
        ButterKnife.bind(this);
        if (Util.isLowThanAndroid5()){
            tag.setVisibility(View.VISIBLE);
        }
        defaultHotSearchId = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MAIN_SEARCH_ID, -1);
        hint=getIntent().getStringExtra("name");
        subModuleId=getIntent().getIntExtra("id",0);
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Util.isEmpty(etAccount.getText().toString())) {
                    scrollView.setVisibility(View.VISIBLE);
                    llSmartSearch.setVisibility(View.GONE);
                } else {
                    smartSearchFragment.requestNet(etAccount.getText().toString());
                }
                smartSearchFragment.setSmartSearchListener(new SmartSearchFragment.SmartSearchListener() {
                    @Override
                    public void hasContent(boolean is) {
                        if (is) {
                            scrollView.setVisibility(View.GONE);
                            llSmartSearch.setVisibility(View.VISIBLE);
                        } else {
                            scrollView.setVisibility(View.VISIBLE);
                            llSmartSearch.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void clickItemContent(String content) {
                        keyword = content;
                        etAccount.setText(content);
                        if (!Util.isEmpty(content)) {
                            etAccount.setSelection(content.length());
                        }
                        performSearch();
                    }
                });
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        hideKeyboard(null);
                        break;
                }
                if (scrollView.getScrollY() > 0) {
                    hideKeyboard(null);
                }
                return false;
            }
        });
        etAccount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etAccount.getText().toString().length() > 0) {
                        keyword = etAccount.getText().toString();
                        performSearch();
                    } else if (etAccount.getText().toString().length() == 0 && !Util.isEmpty(hint)) {
                        performSearch();
                        if (defaultHotSearchId > -1) {//统计默认热门搜索字
                            analyzeHotSearchTask(defaultHotSearchId);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        etAccount.setFocusable(true);
        etAccount.setFocusableInTouchMode(true);
        etAccount.requestFocus();
        showKeyboard();
        //热门搜索
        getHotSearchFile();
        //历史搜索
        getHistorySearchFile();
        //获取推荐分类
        getGoodTagFile();
        initSmartFragment();
    }

    private void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(etAccount, 0);
    }

    private void initSmartFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        smartSearchFragment = new SmartSearchFragment();
        ft.add(R.id.ll_smart_search, smartSearchFragment);
        ft.commitAllowingStateLoss();
    }

    private void resetCommendSortFlowLayout(final KindTagBean.DataEntity.NavigationsEntity navigationsEntity) {
        LinearLayout llHorizontal = (LinearLayout) View.inflate(this, R.layout.layout_item_flow, null);
        llHorizontal.setPadding(0,ScreenUtil.dip2px(32),0,0);
        TextView tvName = (TextView) llHorizontal.findViewById(R.id.tv_commend_sort_name);
        tvName.setText(navigationsEntity.getName());
        FlowLayout fl = (FlowLayout) llHorizontal.findViewById(R.id.fl_commend_sort);

        for (final KindTagBean.DataEntity.NavigationsEntity.ItemsEntity itemsEntity : navigationsEntity.getItems()) {
            TextView commendSortView = (TextView) View.inflate(Search1Activity.this, R.layout.flow_textview_commend_sort, null);
            commendSortView.setText(itemsEntity.getTitle());
            commendSortView.setTag(itemsEntity.getTitle());
            commendSortView.setPadding(ScreenUtil.dip2px(24),0,0,0);
            commendSortView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.urlAction(Search1Activity.this, itemsEntity.getUrl());
                }
            });
            fl.addView(commendSortView);
        }
        recommendLayout.addView(llHorizontal);
    }

    private void getGoodTagFile() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GOOD_TAG_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<KindTagBean>() {
            @Override
            public void onResponse(KindTagBean kindTagBean) {
                if (kindTagBean.getData()!=null&&kindTagBean.getData().getNavigations()!=null&&kindTagBean.getData().getNavigations().size()>0){
                    List<KindTagBean.DataEntity.NavigationsEntity> navigationsEntities = kindTagBean.getData().getNavigations();
                    tvRecommend.setVisibility(View.VISIBLE);
                    recommendLayout.setVisibility(View.VISIBLE);
                    recommendLayout.removeAllViews();
                    for (KindTagBean.DataEntity.NavigationsEntity navigationsEntity : navigationsEntities) {
                        if (navigationsEntity.getItems() == null || navigationsEntity.getItems().size() == 0) {
                            continue;
                        }
                        resetCommendSortFlowLayout(navigationsEntity);
                    }
                }else {
                    tvRecommend.setVisibility(View.GONE);
                    recommendLayout.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(Search1Activity.this,Util.checkErrorType(error));
            }
        });

    }

    private void getHistorySearchFile() {
        histories = new ArrayList<>();
        try {
            if (getFileStreamPath(Constants.HISTORY_SEARCH_FILE) != null && getFileStreamPath
                    (Constants.HISTORY_SEARCH_FILE).exists()) {
                rlHistorySearch.setVisibility(View.VISIBLE);
                InputStream in = openFileInput(Constants.HISTORY_SEARCH_FILE);
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                histories = gson.fromJson(jsonStr, new TypeToken<List<SearchBean>>() {
                }.getType());
                resetHistorySearchFlowLayout();
            } else {
                rlHistorySearch.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getHotSearchFile() {
        HotSearchApi api=new HotSearchApi();
        api.setInterPath(Constants.HOT_SEARCH_URL);
        api.setSubModuleId(subModuleId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<HotSearchBean>() {
            @Override
            public void onResponse(HotSearchBean response) {
                List<HotSearchBean.DataBean.MemberSearchSumListBean> hots=response.getData().getMemberSearchSumList();
                if(hots!=null&&hots.size()>0){
                    hintSearchBean=hots.get(0);
                    etAccount.setHint(hots.get(0).getDisplayName());
                    hots.remove(0);
                    for (HotSearchBean.DataBean.MemberSearchSumListBean memberSearchSumListEntity : hots) {
                        TextView hotView = (TextView) View.inflate(Search1Activity.this, R.layout.flow_search_hot, null);
                        if (memberSearchSumListEntity.getStatus() == 1) {
                            hotView.setTextColor(Color.parseColor("#FFF23365"));
                            hotView.setBackgroundResource(R.drawable.border_search_dim);
                        } else {
                            hotView.setTextColor(Color.parseColor("#8A000000"));
                            hotView.setBackgroundResource(R.drawable.border_search_dim);
                        }
                        hotView.setText(memberSearchSumListEntity.getKeyword());
                        hotView.setTag(memberSearchSumListEntity);
                        hotView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onHotViewClick((HotSearchBean.DataBean.MemberSearchSumListBean) view.getTag());
                            }
                        });
                        flowHot.addView(hotView);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void onHotViewClick(HotSearchBean.DataBean.MemberSearchSumListBean memberSearchSumListEntity) {
        if (memberSearchSumListEntity == null) {
            return;
        }
        analyzeHotSearchTask(memberSearchSumListEntity.getId());
        if (!Util.isEmpty(memberSearchSumListEntity.getUrl())) {
            Util.urlAction(this, memberSearchSumListEntity.getUrl());
        } else {
            keyword = memberSearchSumListEntity.getKeyword();
            etAccount.setText(memberSearchSumListEntity.getKeyword());
            etAccount.setSelection(memberSearchSumListEntity.getKeyword().length());
            performSearch();
        }
    }

    private void analyzeHotSearchTask(long hotSearchId) {
        AnalyzeHotSearchApi api = new AnalyzeHotSearchApi();
        api.setId(hotSearchId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    private void performSearch() {
        if (!Util.isEmpty(keyword)) {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("keyword", keyword);
            intent.putExtra("type", 1);
            startActivity(intent);
            overridePendingTransition(0, 0);
            rlHistorySearch.setVisibility(View.VISIBLE);
            putHistorySearchFile(keyword);
        }else {
            if(hintSearchBean!=null&&!Util.isEmpty(hintSearchBean.getUrl())){
                Util.urlAction(this,hintSearchBean.getUrl());
            }else {
                Intent intent = new Intent(this, ProductListActivity.class);
                intent.putExtra("keyword", hint);
                intent.putExtra("type", 1);
                startActivity(intent);
                overridePendingTransition(0, 0);
                rlHistorySearch.setVisibility(View.VISIBLE);
            }
        }
    }

    private void putHistorySearchFile(String keyword) {
        if (Util.isEmpty(keyword)) {
            return;
        }
        SearchBean searchBean = new SearchBean();
        searchBean.setKey(keyword);
        searchBean.setWord(keyword);
        if (!histories.isEmpty()) {
            boolean exist = false;
            int index = 0;
            for (; index < histories.size(); index++) {
                SearchBean sb = histories.get(index);
                if (sb.getKey().equals(keyword)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                histories.remove(index);
                histories.add(0, searchBean);
            } else {
                histories.add(0, searchBean);
                int size = histories.size();
                if (size > 6) {
                    for (int i = 6; i < size; i++) {
                        histories.remove(6);
                    }
                }
            }
        } else {
            histories.add(0, searchBean);
        }
        resetHistorySearchFlowLayout();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(histories, new TypeToken<List<SearchBean>>() {
        }.getType());
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = openFileOutput(Constants.HISTORY_SEARCH_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(jsonStr);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSearchViewClick(SearchBean searchBean) {
        if (searchBean == null) {
            return;
        }
        keyword = searchBean.getKey();
        etAccount.setText(searchBean.getKey());
        etAccount.setSelection(searchBean.getKey().length());
        performSearch();
    }

    private void resetHistorySearchFlowLayout() {
        flowHistory.removeAllViews();
        for (SearchBean searchBean : histories) {
            TextView historyView = (TextView) View.inflate(Search1Activity.this, R.layout.flow_item_search, null);
            historyView.setText(searchBean.getKey());
            historyView.setTag(searchBean);
            historyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSearchViewClick((SearchBean) view.getTag());
                }
            });
            flowHistory.addView(historyView);
        }
    }


    @OnClick({R.id.back_iv, R.id.iv_reset, R.id.title_right, R.id.iv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.iv_reset:
                etAccount.setText("");
                ivReset.setVisibility(View.GONE);
                break;
            case R.id.title_right:
                if (etAccount.getText().toString().length() > 0) {
                    keyword = etAccount.getText().toString();
                    performSearch();
                } else if (etAccount.getText().toString().length() == 0 && !Util.isEmpty(hint)) {
                    performSearch();
                    if (defaultHotSearchId > -1) {//统计默认热门搜索字
                        analyzeHotSearchTask(defaultHotSearchId);
                    }
                }
                break;
            case R.id.iv_clear_history:
                rlHistorySearch.setVisibility(View.GONE);
                histories.clear();
                resetHistorySearchFlowLayout();
                deleteFile(Constants.HISTORY_SEARCH_FILE);
                break;
        }
    }
}
