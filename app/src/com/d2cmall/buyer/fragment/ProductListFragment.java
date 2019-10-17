package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.adapter.BrandProductAdapter;
import com.d2cmall.buyer.api.BrandProductApi;
import com.d2cmall.buyer.api.SeriesApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.BrandProductBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.SerieBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ScreenNewPop;
import com.d2cmall.buyer.widget.SeriesPop;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.BRAND_PRODUCT_URL;

/**
 * Created by rookie on 2017/8/3.
 * 店铺的商品列表
 */

public class ProductListFragment extends BaseFragment {
    @Bind(R.id.tv_comprehensive)
    TextView tvComprehensive;
    @Bind(R.id.tv_near)
    TextView tvNear;
    @Bind(R.id.tv_hot)
    TextView tvHot;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_screen)
    TextView tvScreen;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.iv_price_state)
    ImageView ivPriceState;
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.nest_scroll)
    NestedScrollView nest_scroll;
    @Bind(R.id.ll_all)
    LinearLayout linearLayout;
    @Bind(R.id.topView)
    View topView;
    @Bind(R.id.btn_cart)
    FloatingActionButton btnCart;
    @Bind(R.id.rl_list)
    RelativeLayout rlList;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> list;
    private DelegateAdapter adapter;
    private String keyword = null;
    private int id;
    private BrandProductAdapter brandProductAdapter;
    private VirtualLayoutManager layoutManager;
    PopupWindow popupWindow;
    private boolean hasNext;
    private boolean isafter;
    private int p = 1;
    private String sortValue = "";
    private ScreenNewPop pop;
    Integer subscribe;
    String productSellType = "";
    private Boolean hasPromotion;
    String categoryIds = "";
    String seriesIds = "";
    int topId = -1;
    private int t = -1;
    private int c = -1;
    private int min = -1;
    private int max = -1;
    private List<SerieBean.DataBean.SeriesBean.ListBean> seriesList;
    private SeriesPop seriesPop;
    private String selectType;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private enum ComprehensiveState {
        NONE, SELECTED
    }

    private ComprehensiveState currentComprehensiveState = ComprehensiveState.NONE;

    private enum NearState {
        NONE, SELECTED
    }

    private NearState currentNearState = NearState.NONE;

    private enum HotState {
        NONE, SELECTED
    }

    private HotState currentHotState = HotState.NONE;

    private enum PriceState {
        NONE, DESC, ASC
    }

    private PriceState currentPriceState = PriceState.NONE;

    public static ProductListFragment newInstance(int id) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        productListFragment.setArguments(bundle);
        return productListFragment;
    }
    public static ProductListFragment newInstance(int id,String selectType) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("selectType", selectType);
        productListFragment.setArguments(bundle);
        return productListFragment;
    }
    public static ProductListFragment newInstance(String keyword) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        productListFragment.setArguments(bundle);
        return productListFragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            keyword = getArguments().getString("keyword");
            selectType = getArguments().getString("selectType");
            id = getArguments().getInt("id");
        }
        return inflater.inflate(R.layout.fragment_product1_list, container, false);
    }

    @Override
    public void prepareView() {
        list = new ArrayList<>();
        seriesList = new ArrayList<>();
        SerieBean.DataBean.SeriesBean.ListBean first = new SerieBean.DataBean.SeriesBean.ListBean();
        first.setName("全部");
        seriesList.add(first);
        seriesPop = new SeriesPop(getActivity(), 88);
        seriesPop.setOnClick(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                if (Util.isEmpty(seriesList.get(position).getName())) {
                    return;
                }
                if (seriesList.get(position).getName().equals("全部")) {
                    //seriesId = -2;
                } else {
                    //seriesId = (long) seriesList.get(position).getId();
                }
                p = 1;
                loadData();
            }
        });
        seriesPop.setOnDissmissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((BrandDetailActivity) getActivity()).popIsShow = false;
                ((BrandDetailActivity) getActivity()).onDismiss();
            }
        });
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        initRecyclerView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int position = brandProductAdapter.getLongClickPosition();
                if (position != -1) {
                    list.get(position).setLongClick(false);
                    brandProductAdapter.setLongClickPosition(-1);
                    brandProductAdapter.notifyDataSetChanged();
                }
                int last = layoutManager.findLastVisibleItemPosition();
                if (last > adapter.getItemCount() - 3 && hasNext) {
                    p++;
                    loadData();
                }
            }
        });

    }

    public void showPop() {
        ((BrandDetailActivity) getActivity()).popIsShow = true;
        if (seriesPop != null) {
            seriesPop.show(topView);
        } else {
            seriesPop = new SeriesPop(getActivity(), 88);
            seriesPop.setOnClick(new OnItemClickListener() {
                @Override
                public void itemClick(View v, int position) {
                    Util.showToast(getActivity(), list.get(position).getName());
                }
            });
            seriesPop.setOnDissmissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ((BrandDetailActivity) getActivity()).popIsShow = false;
                    ((BrandDetailActivity) getActivity()).onDismiss();
                }
            });
        }
    }

    public void closePop() {
        ((BrandDetailActivity) getActivity()).popIsShow = false;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void doBusiness() {
        if(!Util.isEmpty(selectType) && "update".equals(selectType) &&list != null && list.size() == 0){
            selectNear();
        }else if (list != null && list.size() == 0) {
            selectComprehensive();
        }

        if (seriesList != null && seriesList.size() == 1) {
            loadSeries();
        }
    }

    private void loadSeries() {
        SeriesApi api = new SeriesApi();
        api.setPageSize(50);
        api.setBrandId((long) id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SerieBean>() {
            @Override
            public void onResponse(SerieBean response) {
                if (response.getData().getSeries().getList() != null) {
                    seriesList.addAll(response.getData().getSeries().getList());
                }
                if (seriesPop != null) {
                    seriesPop.setData(seriesList, 0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void loadData() {
        if (keyword != null) {

        } else {
            BrandProductApi api = new BrandProductApi();
            api.setP(p);
            api.setO(sortValue);
            api.setPageSize(20);
            if (!Util.isEmpty(productSellType)) {
                api.setProductSellType(productSellType);
            }
            if (!Util.isEmpty(categoryIds)) {
                api.setCategoryIds(categoryIds);
            }
            if (!Util.isEmpty(seriesIds)) {
                api.setSeriesId(seriesIds);
            }
            if (isafter) {
                api.setAfter(isafter);
            }
            if (hasPromotion != null && hasPromotion) {
                api.setHasPromotion(hasPromotion);
            }
            if (subscribe != null && subscribe != 0) {
                api.setSubscribe(subscribe);
            }
            if (min > -1) {
                api.setMin(min);
            }
            if (max > -1) {
                api.setMax(max);
            }
            api.setInterPath(String.format(BRAND_PRODUCT_URL, id));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandProductBean>() {
                @Override
                public void onResponse(BrandProductBean response) {
                    if (errorImage == null || nest_scroll == null) {
                        return;
                    }
                    if (p == 1) {
                        list.clear();
                    }
                    ((BrandDetailActivity) getActivity()).changeTabNum(response.getData().getProducts().getTotal());
                    hasNext = response.getData().getProducts().isNext();
                    if (response.getData().getProducts().getList() != null && response.getData().getProducts().getList().size() > 0) {
                        list.addAll(response.getData().getProducts().getList());
                    }
                    if (list.size() == 0) {
                        nest_scroll.setVisibility(View.VISIBLE);
                        errorImage.setVisibility(View.VISIBLE);
                        errorImage.setImageResource(R.mipmap.ic_empty_work);
                        recyclerView.setVisibility(View.GONE);
                        rlList.setVisibility(View.GONE);
                    } else {
                        nest_scroll.setVisibility(View.GONE);
                        errorImage.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        rlList.setVisibility(View.VISIBLE);
                    }
                    brandProductAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    nest_scroll.setVisibility(View.VISIBLE);
                    errorImage.setVisibility(View.VISIBLE);
                    errorImage.setImageResource(R.mipmap.ic_no_net);
                    recyclerView.setVisibility(View.GONE);
                    rlList.setVisibility(View.GONE);
                    Util.showToast(getActivity(), Util.checkErrorType(error));
                }
            });
        }
    }


    private void initRecyclerView() {
        layoutManager = new VirtualLayoutManager(getContext());
        adapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setLayoutManager(layoutManager);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        brandProductAdapter = new BrandProductAdapter(getActivity(), list, gridLayoutHelper, itemWidth);
        adapter.addAdapter(brandProductAdapter);
        recyclerView.setAdapter(adapter);
    }


    @OnClick({R.id.tv_comprehensive, R.id.tv_near, R.id.tv_hot, R.id.tv_price, R.id.tv_screen, R.id.btn_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_comprehensive:
                selectComprehensive();
                break;
            case R.id.tv_near:
                selectNear();
                break;
            case R.id.tv_hot:
                selectHot();
                break;
            case R.id.tv_price:
                selectPrice();
                break;
            case R.id.btn_cart:
                if (Util.loginChecked(getActivity(), 100)) {
                    stat("V3购物车入口", "店铺购物车");
                    Intent intent = new Intent(getActivity(), CartActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.tv_screen:
                if (pop == null) {
                    pop = new ScreenNewPop(mContext, 1, "", "", (long) id,-1,false);
                    pop.setOnBrandConFirmClickListner(new ScreenNewPop.OnBrandConfirmClickListener() {
                        @Override
                        public void callBack(Boolean backHasPromotion, Integer backSubscribe, Boolean backAfter, String backProductSellType, String backCategoryId, String backSeriesId, Integer minPrice, Integer maxPrice) {
                            hasPromotion = backHasPromotion;
                            subscribe = backSubscribe;
                            isafter = backAfter;
                            productSellType = backProductSellType;
                            categoryIds = backCategoryId;
                            seriesIds = backSeriesId;
                            min = minPrice;
                            max = maxPrice;
                            p = 1;
                            loadData();
                        }
                    });
                }
                pop.show(getActivity().getWindow().getDecorView());
                break;
        }
    }

    private void stat(String event, String label) {
        StatService.onEvent(getActivity(), event, label);
        TCAgent.onEvent(getActivity(), event, label);
    }

    private void selectPrice() {
        tvPrice.setTextColor(getResources().getColor(R.color.color_black));
        switch (currentPriceState) {
            case NONE:
                ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_up);
                currentPriceState = PriceState.ASC;
                sortValue = "pa";
                break;
            case ASC:
                ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_down);
                currentPriceState = PriceState.DESC;
                sortValue = "pd";
                break;
            case DESC:
                ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_up);
                currentPriceState = PriceState.ASC;
                sortValue = "pa";
                break;
        }

        tvHot.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentHotState = HotState.NONE;
        tvNear.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentNearState = NearState.NONE;
        tvComprehensive.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        p = 1;
        loadData();
    }

    private void selectHot() {
        tvHot.setTextColor(getResources().getColor(R.color.color_black));
        currentHotState = HotState.SELECTED;

        tvComprehensive.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentComprehensiveState = ComprehensiveState.NONE;
        tvNear.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentNearState = NearState.NONE;
        tvPrice.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = PriceState.NONE;
        sortValue = "sd";
        p = 1;
        loadData();
    }

    private void selectNear() {
        tvNear.setTextColor(getResources().getColor(R.color.color_black));
        currentNearState = NearState.SELECTED;

        tvHot.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentHotState = HotState.NONE;
        tvComprehensive.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentComprehensiveState = ComprehensiveState.NONE;
        tvPrice.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = PriceState.NONE;
        if(getActivity() instanceof BrandDetailActivity){
            sortValue = "cd";
        }else{
            sortValue = "dd";
        }
        p = 1;
        loadData();
    }

    private void selectComprehensive() {
        tvComprehensive.setTextColor(getResources().getColor(R.color.color_black));
        currentComprehensiveState = ComprehensiveState.SELECTED;
        tvHot.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentHotState = HotState.NONE;
        tvNear.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        currentNearState = NearState.NONE;
        tvPrice.setTextColor(getResources().getColor(R.color.ysf_grey_76838F));
        ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = PriceState.NONE;
        sortValue = "bd";
        p = 1;
        loadData();
    }
}
