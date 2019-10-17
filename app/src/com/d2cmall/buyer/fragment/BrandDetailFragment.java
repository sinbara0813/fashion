package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CategoryListAdapter;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.NavigationLeftBean;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by rookie on 2017/7/29.
 */

public class BrandDetailFragment extends BaseFragment {
    @Bind(R.id.list_view)
    ListView listView;
    private String[] strs = {"热门", "A-Z", "按分类", "按风格", "按地区"};
    private List<NavigationLeftBean.DataBean.NavigationsBean> list;
    private FragmentManager fragmentManager;
    private SortingDesignerFragment sortingDesignerFragment;
    private BrandSpecificFragment brandSpecificFragment;
    private BrandHotFragment brandHotFragment;
    private int lastPosition = 0;
    private boolean isExistBrandSpecificFragment = false;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_brand, container, false);
    }

    @Override
    public void prepareView() {

    }

    @Override
    public void doBusiness() {
        if (listView.getAdapter() == null) {
            initFragment();
            initData();
            final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getContext(), list);
            categoryListAdapter.setType(1);
            categoryListAdapter.setDefItem(0);
            listView.setAdapter(categoryListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    categoryListAdapter.setDefItem(position);
                    switch (position) {
                        case 0:
                            showHotFragment();
                            break;
                        case 1:
                            showSortDesinerFragment();
                            break;
                        default:
                            showGridFragment(position);
                            break;
                    }
                    lastPosition = position;
                }
            });
        }
        switch (lastPosition) {
            case 0:
                if (brandHotFragment != null) {
                    brandHotFragment.setUserVisibleHint(true);
                }
                break;
            case 1:
                break;
            default:
                if (brandSpecificFragment != null) {
                    brandSpecificFragment.setUserVisibleHint(true);
                }
                break;
        }
    }

    private void showHotFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (brandHotFragment == null) {
            brandHotFragment = new BrandHotFragment();
            ft.add(R.id.brand_fragment, brandHotFragment);
        }
        if (brandSpecificFragment != null) {
            ft.hide(brandSpecificFragment);
            brandSpecificFragment.setUserVisibleHint(false);
        }
        if (sortingDesignerFragment != null) {
            ft.hide(sortingDesignerFragment);
            sortingDesignerFragment.setUserVisibleHint(false);
        }
        ft.show(brandHotFragment);
        ft.commit();
        if (brandHotFragment != null) {
            brandHotFragment.setUserVisibleHint(true);
        }
    }

    private void showSortDesinerFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (sortingDesignerFragment == null) {
            sortingDesignerFragment = SortingDesignerFragment.newInstance(false);
            ft.add(R.id.brand_fragment, sortingDesignerFragment);
        }
        if (brandSpecificFragment != null) {
            ft.hide(brandSpecificFragment);
            brandSpecificFragment.setUserVisibleHint(false);
        }
        if (brandHotFragment != null) {
            ft.hide(brandHotFragment);
            brandHotFragment.setUserVisibleHint(false);
        }
        ft.show(sortingDesignerFragment);
        ft.commit();
        if (sortingDesignerFragment != null) {
            sortingDesignerFragment.setUserVisibleHint(true);
        }
    }

    private void showGridFragment(int position) {
        String type = null;
        String name = "";
        switch (position) {
            case 2:
                type = "designArea";
                name = "按分类";
                break;
            case 3:
                type = "style";
                name = "按风格";
                break;
            case 4:
                type = "country";
                name = "按地区";
                break;
        }

        if (position != lastPosition && isExistBrandSpecificFragment) {
            //刷新数据
            brandSpecificFragment.changeDataListener.changeData(type);
        }


        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (brandSpecificFragment == null) {
            brandSpecificFragment = BrandSpecificFragment.newInstance(type);
            ft.add(R.id.brand_fragment, brandSpecificFragment);
            isExistBrandSpecificFragment = true;
            ft.show(brandSpecificFragment);
            ft.commit();
            if (brandHotFragment != null) {
                ft.hide(brandHotFragment);
                brandHotFragment.setUserVisibleHint(false);
            }
            if(sortingDesignerFragment!=null){
                ft.hide(sortingDesignerFragment);
                sortingDesignerFragment.setUserVisibleHint(false);
            }
            if (brandSpecificFragment != null) {
                brandSpecificFragment.setUserVisibleHint(true);
            }
        }

        if(isExistBrandSpecificFragment&&brandSpecificFragment.isHidden()){
            ft.show(brandSpecificFragment);
            ft.commit();
            if (brandHotFragment != null) {
                ft.hide(brandHotFragment);
                brandHotFragment.setUserVisibleHint(false);
            }
            if(sortingDesignerFragment!=null){
                ft.hide(sortingDesignerFragment);
                sortingDesignerFragment.setUserVisibleHint(false);
            }
            if (brandSpecificFragment != null) {
                brandSpecificFragment.setUserVisibleHint(true);
            }
        }

    }

    private void initFragment() {
        if (brandHotFragment == null) {
            brandHotFragment = new BrandHotFragment();
            fragmentManager = getChildFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.brand_fragment, brandHotFragment);
            ft.show(brandHotFragment);
            ft.commit();
            brandHotFragment.setUserVisibleHint(true);
        }
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            NavigationLeftBean.DataBean.NavigationsBean cl = new NavigationLeftBean.DataBean.NavigationsBean();
            cl.setName(strs[i]);
            list.add(cl);
        }
    }

    @Override
    public void releaseOnInVisible() {
        switch (lastPosition) {
            case 0:
                if (brandHotFragment != null) {
                    brandHotFragment.setUserVisibleHint(false);
                }
                break;
            case 1:
                if (sortingDesignerFragment != null) {
                    sortingDesignerFragment.setUserVisibleHint(false);
                }
                break;
            default:
                if (brandSpecificFragment != null) {
                    brandSpecificFragment.setUserVisibleHint(false);
                }
                break;
        }
    }
}
