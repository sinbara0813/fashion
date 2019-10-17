package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CategoryListAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.NavigationLeftBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.d2cmall.buyer.Constants.NAVIGATION_PRODUCT_URL;

/**
 * 分类下商品Fragment
 * Created by rookie on 2017/7/27.
 */

public class ShoppingCommodityFragment extends BaseFragment {
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.frame)
    FrameLayout frame;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private List<NavigationLeftBean.DataBean.NavigationsBean> list;
    //private List<CategoryListBean> list;
    private CategoryDetailFragment categoryDetailFragment;
    private CategoryListAdapter categoryListAdapter;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_commodity, container, false);
    }

    @Override
    public void prepareView() {
        list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
    }

    @Override
    public void doBusiness() {
        if (listView.getAdapter() == null) {
            loadNavigationData();
        }
        if (categoryDetailFragment != null) {
            categoryDetailFragment.setUserVisibleHint(true);
        }
    }

    private void loadNavigationData() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(String.format(NAVIGATION_PRODUCT_URL, 0));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<NavigationLeftBean>() {
            @Override
            public void onResponse(NavigationLeftBean response) {
                if (!isVisibleToUser){
                    return;
                }
                if (response.getData().getNavigations() == null || response.getData().getNavigations().size() == 0) {
                    content.setVisibility(View.GONE);
                    errorImage.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    list.addAll(response.getData().getNavigations());
                    initFragment();
                    categoryListAdapter = new CategoryListAdapter(getContext(), list);
                    categoryListAdapter.setDefItem(0);
                    listView.setAdapter(categoryListAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            categoryDetailFragment.changeDataLisener.changeData(list.get(position).getId(), position, list.get(position).getName());
                            categoryListAdapter.setDefItem(position);
                        }
                    });
                    categoryListAdapter.notifyDataSetChanged();
                    content.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    errorImage.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(getActivity(), Util.checkErrorType(error));
                content.setVisibility(View.GONE);
                errorImage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void initFragment() {
        if (categoryDetailFragment == null) {
            categoryDetailFragment = new CategoryDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", list.get(0).getId());
            bundle.putString("name", list.get(0).getName());
            ArrayList<String> pics = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                pics.add(list.get(i).getPic());
            }
            ArrayList<String> url = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                url.add(list.get(i).getUrl());
            }
            bundle.putStringArrayList("pics", pics);
            bundle.putStringArrayList("url", url);
            categoryDetailFragment.setArguments(bundle);
            if (getActivity() == null) {
                return;
            }
            fragmentManager = getChildFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.frame, categoryDetailFragment);
            ft.show(categoryDetailFragment);
            ft.commitAllowingStateLoss();
            categoryDetailFragment.setUserVisibleHint(true);
        }
    }


    @Override
    public void releaseOnInVisible() {
        if (categoryDetailFragment != null) {
            categoryDetailFragment.setUserVisibleHint(false);
        }
    }

}
