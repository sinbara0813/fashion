package com.d2cmall.buyer.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.fragment.WardrobeListFragment;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


//我的衣橱二级列表页
public class WardrobeListActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.sliding_tab_title)
    TabLayout slidingTabTitle;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.tv_make_choice)
    TextView tvMakeChoice;
    @Bind(R.id.top_ll)
    RelativeLayout topLl;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private Map<String, ArrayList<Map>> categoryMap;//衣橱分类
    private String type;
    private List<WardrobeListFragment> fragments;
    private List<String> titles;
    private WardrobeVpAdapter tabPagerAdapter;
    private List<String> categoryFirst;
    private boolean hasInitCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe_list);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if (Util.isEmpty(type)) {
            type = "上装";
        }
        init();
        loadCategory();
    }


    private void init() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        tabPagerAdapter = new WardrobeVpAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabPagerAdapter);
        slidingTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (hasInitCategory) {
                    int tabCount = slidingTabTitle.getTabCount();
                    for (int i = 0; i < tabCount; i++) {
                        if (slidingTabTitle.getTabAt(i) == tab) {
                            refreshSecondTabVp(i);
                        }

                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void refreshSecondTabVp(int i) {
        titles.clear();
        fragments.clear();
        String selectFirstCategory = categoryFirst.get(i);
        ArrayList<Map> maps = categoryMap.get(selectFirstCategory);
        for (int j = 0; j < maps.size()+1; j++) {
            if(j==0){
                titles.add("全部");
                fragments.add(WardrobeListFragment.newInstance("全部",(String) maps.get(0).get("topName"), false, false));
            }else {
                titles.add((String) maps.get(j-1).get("name"));
                fragments.add(WardrobeListFragment.newInstance((String) maps.get(j-1).get("name"),(String) maps.get(j-1).get("topName"),false, false));
            }
        }
        slidingTab.setViewPager(viewPager);
        tabPagerAdapter.notifyDataSetChanged();
        //切换一级分类,将二级分类切换到第一个
        viewPager.setCurrentItem(0);

    }

    private void loadCategory() {
        HttpUtils.doGetAsyn(Constants.API_URL + Constants.WARDROBE_CATEGORY, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject topCategorys = data.getJSONObject("topCategorys");
                    Gson gson = new Gson();
                    categoryMap = gson.fromJson(topCategorys.toString(), Map.class);
                    //初始化一级分类
                    categoryFirst = new ArrayList<>();
                    for (Map.Entry<String, ArrayList<Map>> entry : categoryMap.entrySet()) {
                        categoryFirst.add(entry.getKey());
                        entry.getValue();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < categoryFirst.size(); i++) {
                                //填充tab数据
                                slidingTabTitle.addTab(slidingTabTitle.newTab().setText(categoryFirst.get(i)));
                                setIndicator(slidingTabTitle, 40, 40);
                            }

                            //初始化Vp数据
                            ArrayList<Map> maps = categoryMap.get(categoryFirst.get(0));
                            titles.clear();
                            for (int j = 0; j < maps.size()+1; j++) {
                                if(j==0){//本地在二级分类里面加全部分类
                                    titles.add("全部");
                                    fragments.add(WardrobeListFragment.newInstance("全部",(String) maps.get(0).get("topName"), false, false));
                                }else {
                                    titles.add((String) maps.get(j-1).get("name"));
                                    fragments.add(WardrobeListFragment.newInstance((String) maps.get(j-1).get("name"),(String) maps.get(j-1).get("topName"),false, false));
                                }

                            }
                            slidingTab.setViewPager(viewPager);
                            tabPagerAdapter.notifyDataSetChanged();
                            //
                            for (int k = 0; k < categoryFirst.size(); k++) {
                                if (categoryFirst.get(k).equals(type)) {
                                    hasInitCategory = true;
                                    slidingTabTitle.getTabAt(k).select();
                                    int finalK = k;
                                    //匹配到对应的分类,顶部的tab自动滚动到对应位置,延时去掉滚动效果可能会失效
                                    slidingTabTitle.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            slidingTabTitle.setScrollPosition(finalK,0,true);
                                        }
                                    },500);

                                }

                            }
                            hasInitCategory = true;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }

    public static class WardrobeVpAdapter extends FragmentStatePagerAdapter {

        private List<WardrobeListFragment> fragments;
        private List<String> titles;

        public WardrobeVpAdapter(FragmentManager fm, List<WardrobeListFragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (titles != null && titles.size() > position) {
                return titles.get(position);
            } else {
                return "";
            }
        }
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
