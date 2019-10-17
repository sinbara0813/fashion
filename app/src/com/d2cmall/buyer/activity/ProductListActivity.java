package com.d2cmall.buyer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BeandSearchAdapter;
import com.d2cmall.buyer.adapter.ProductListAdapter;
import com.d2cmall.buyer.adapter.ProductListPromotionFilterAdapter;
import com.d2cmall.buyer.api.GoodsApi;
import com.d2cmall.buyer.api.ProductThemeApi;
import com.d2cmall.buyer.api.SearchBrandApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.ProductThemeBean;
import com.d2cmall.buyer.bean.PushBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.ScreenNewPop;
import com.d2cmall.buyer.widget.SlideBridge;
import com.d2cmall.buyer.widget.SlideRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by rookie on 2017/8/18.
 * 商品列表页面
 */

public class ProductListActivity extends BaseActivity implements ProductListPromotionFilterAdapter.FilterPromiotionListener {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.tv_search)
    EditText tvSearch;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.recycle_brand)
    RecyclerView recycleBrand;
    @Bind(R.id.ll_dot)
    LinearLayout llDot;
    @Bind(R.id.ll_brand)
    LinearLayout llBrand;
    @Bind(R.id.tv_comprehensive)
    TextView tvComprehensive;
    @Bind(R.id.tv_near)
    TextView tvNear;
    @Bind(R.id.tv_hot)
    TextView tvHot;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.iv_price_state)
    ImageView ivPriceState;
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.tv_screen)
    TextView tvScreen;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.nest_scroll)
    NestedScrollView nestScroll;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.activity_main)
    CoordinatorLayout activityMain;
    @Bind(R.id.img_head)
    RoundedImageView imgHead;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.push_toast)
    LinearLayout pushToast;
    @Bind(R.id.drawer)
    RelativeLayout drawer;
    @Bind(R.id.pull_group)
    SlideRefreshView refreshableView;
    @Bind(R.id.right_iv)
    ImageView rightIv;
    @Bind(R.id.arrow_view)
    LinearLayout arrowView;
    @Bind(R.id.iv_footmark)
    ImageView ivFootmark;
    @Bind(R.id.iv_top)
    ImageView ivTop;
    @Bind(R.id.ll_button)
    LinearLayout llButton;
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.ll_condition)
    LinearLayout llCondition;

    private ProductListAdapter productListAdapter;
    private List<Object> list = new ArrayList<>();
    private ScreenNewPop newPop;
    private String sortValue = "";
    private String url;
    private String subName;
    private int type;
    private String categoryId;
    private int min = -1;
    private int max = -1;
    Integer subscribe;
    String productSellType = "";
    int topId = -1;
    String desiners;
    private Boolean hasPromotion;
    private String log;
    private String keyword;
    private boolean isLoad;
    private int currentPage = 1;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private boolean hasNext;
    private boolean isafter;
    private boolean isLoadUrl = true;
    private BeandSearchAdapter beandSearchAdapter;
    private List<DesignerBean.DataEntity.DesignersEntity.ListEntity> brandList;
    private LinearLayoutManager linearLayoutManager;

    private final int NONE = 1995;
    private final int DESC = 11;
    private final int ASC = 23;
    private int currentPriceState = NONE;
    private boolean isRight, isSlidingToLeft, isMoreThanSix, isCanTopVisible;
    private StaggeredGridLayoutHelper staggerLayoutHelper;
    private long promotionId;//筛选商品的活动ID
    private boolean hasSetPromotionFilterAdapter=false;
    private ProductListPromotionFilterAdapter productListPromotionFilterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list2);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        log = getIntent().getStringExtra("log");
        subName = getIntent().getStringExtra("subName");
        type = getIntent().getIntExtra("type", 0);
        keyword = getIntent().getStringExtra("keyword");
        if (type == 1) {
            tvSearch.setHint(keyword);
        }
        progressBar.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.GONE);
        ((SimpleItemAnimator) recycleView.getItemAnimator()).setSupportsChangeAnimations(false);
        recycleView.setHasFixedSize(false);
        brandList = new ArrayList<>();
        beandSearchAdapter = new BeandSearchAdapter(this, brandList);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycleBrand.setLayoutManager(linearLayoutManager);
        new LinearSnapHelper().attachToRecyclerView(recycleBrand);
        recycleBrand.setAdapter(beandSearchAdapter);
        setBrand();
        refreshableView.setHandler(new SlideBridge() {
            @Override
            public boolean checkCanDoRefresh(SlideRefreshView frame, View content, View header) {
                if (!isMoreThanSix) {
                    return false;
                }
                return isRight;
            }

            @Override
            public void scollOver(SlideRefreshView frame, View content, View header) {
                Intent intent = new Intent(ProductListActivity.this, SearchAllBrandActivity.class);
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });
        doBusiness();
    }

    private void initDot() {
        llDot.setVisibility(View.VISIBLE);
        llDot.removeAllViews();
        for (int i = 0; i < brandList.size(); i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.dip2px(20), ScreenUtil.dip2px(1));
            if (i < brandList.size() - 1) {
                params.rightMargin = ScreenUtil.dip2px(4);
            }
            if (i == 0) {
                iv.setSelected(true);
            }
            iv.setLayoutParams(params); //设置布局
            iv.setImageResource(R.drawable.dot_back);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            llDot.addView(iv);
        }
        final int itemCount = llDot.getChildCount();
        recycleBrand.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int itemCounts = linearLayoutManager.getItemCount();

                // 判断是否滑动到了最后一个Item，并且是向左滑动
                if (lastItemPosition == (itemCounts - 1) && isSlidingToLeft) {
                    isRight = true;
                } else {
                    isRight = false;
                }
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                for (int i = 0; i < itemCount; i++) {
                    View view = llDot.getChildAt(i);//提取子View
                    if (i == position) {
                        view.setSelected(true);
                    } else {
                        view.setSelected(false);
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLeft = dx > 0;
            }
        });
    }

    private void setBrand() {
        SearchBrandApi api = new SearchBrandApi();
        if (Util.isEmpty(keyword)) {
            api.setKeyword(tvSearch.getHint().toString());
        } else {
            api.setKeyword(keyword);
        }
        api.setPageNumber(1);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DesignerBean>() {
            @Override
            public void onResponse(DesignerBean response) {
                if (response.getData().getDesigners() != null && response.getData().getDesigners().getList().size() > 0) {
                    if (response.getData().getDesigners().getList().size() > 6) {
                        isMoreThanSix = true;
                        brandList.addAll(response.getData().getDesigners().getList().subList(0, 6));
                    } else {
                        isMoreThanSix = false;
                        brandList.addAll(response.getData().getDesigners().getList());
                    }
                    if (brandList.size() > 1) {
                        initDot();
                    } else {
                        llDot.setVisibility(View.GONE);
                    }
                    isRight = false;
                    beandSearchAdapter.notifyDataSetChanged();
                    llBrand.setVisibility(View.VISIBLE);
                } else {
                    llBrand.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    public void doBusiness() {
        selectComprehensive();
        virtualLayoutManager = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool pool = recycleView.getRecycledViewPool();
        pool.setMaxRecycledViews(0, 6);
        recycleView.setRecycledViewPool(pool);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);
        staggerLayoutHelper = new StaggeredGridLayoutHelper(2);
        staggerLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        staggerLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        staggerLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        staggerLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        productListAdapter = new ProductListAdapter(this, staggerLayoutHelper, list);
        delegateAdapter.addAdapter(productListAdapter);
        recycleView.setAdapter(delegateAdapter);
        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    currentPage = 1;
                    hideKeyboard(null);
                    url = "";
                    desiners = "";
                    topId = -1;
                    categoryId = "";
                    productSellType = "";
                    min = -1;
                    max = -1;
                    subscribe = 0;
                    tvSearch.clearFocus();
                    progressBar.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.GONE);
                    virtualLayoutManager.scrollToPosition(0);
                    keyword = tvSearch.getText().toString();
                    brandList.clear();
                    setBrand();
                    if (Util.isEmpty(tvSearch.getText().toString())) {
                        loadData(tvSearch.getHint().toString(), false);
                    } else {
                        loadData(tvSearch.getText().toString(), false);
                    }
                    tvSearch.setText("");
                    tvSearch.setHint(keyword);
                    return true;
                }
                return false;
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (productListAdapter.getLongClickPosition() != -1) {
                    if (list.get(productListAdapter.getLongClickPosition()) instanceof GoodsBean.DataBean.ProductsBean.ListBean) {
                        ((GoodsBean.DataBean.ProductsBean.ListBean) list.get(productListAdapter.getLongClickPosition())).setLongClick(false);
                        productListAdapter.setLongClickPosition(-1);
                        productListAdapter.notifyDataSetChanged();
                    }
                }
                int last = virtualLayoutManager.findLastVisibleItemPosition();
                if (last > 12) {
                    if (!isCanTopVisible) {
                        llButton.animate().translationY(-ScreenUtil.dip2px(56)).setDuration(350).start();
                        isCanTopVisible = true;
                    }
                } else if (last < 12) {
                    if (isCanTopVisible) {
                        llButton.animate().translationY(ScreenUtil.dip2px(0)).setDuration(350).start();
                        isCanTopVisible = false;
                    }
                }
                if (last > delegateAdapter.getItemCount() - 3 && hasNext&&!isLoad) {
                    currentPage++;
                    loadData(keyword, false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


    private void loadData(final String keyword, boolean isFilter) {
        isLoad=true;
        GoodsApi api = new GoodsApi();
        api.setP(currentPage);
        api.setPageSize(50);
        api.setO(sortValue);
        api.setKeyword(keyword);
        if (!Util.isEmpty(productSellType)) {
            api.setProductSellType(productSellType);
        }
        if (subscribe != null && subscribe != 0) {
            api.setSubscribe(subscribe);
        }
        if (hasPromotion != null && hasPromotion) {
            api.setHasPromotion(hasPromotion);
        }
        if (isafter) {
            api.setAfter(isafter);
        }
        if (type == 0) {
            if (isLoadUrl) {
                if (!Util.isEmpty(url)) {
                    api.setPreParam(url.substring(url.indexOf("?") + 1, url.length()));
                }
            }
        } else if (type == 1) {
            api.setKeyword(keyword);
        }
        if (!Util.isEmpty(categoryId)) {
            api.setCategoryId(categoryId);
        }
        if (min > -1) {
            api.setMin(min);
        }
        if (max > -1) {
            api.setMax(max);
        }
        if (!Util.isEmpty(desiners)) {
            api.setDesigners(desiners);
        }
        if(promotionId>0 && isFilter){
            api.setPromotionId(promotionId);
        }
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<GoodsBean>() {



            @Override
            public void onResponse(GoodsBean goodsBean) {
                isLoad=false;
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    list.clear();
                }
                int size = goodsBean.getData().getProducts().getList().size();
                if (size > 0) {
                    list.addAll(goodsBean.getData().getProducts().getList());
                    nestScroll.setVisibility(View.GONE);
                    errorImage.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                    if(goodsBean.getData().getRecSearch()!=null && goodsBean.getData().getRecSearch().get(0)!=null && goodsBean.getData().getRecSearch().get(0).getName()!=null && goodsBean.getData().getRecSearch().get(0).getPromotionId()>0){
                        promotionId = goodsBean.getData().getRecSearch().get(0).getPromotionId();
                        if(!hasSetPromotionFilterAdapter){
                            hasSetPromotionFilterAdapter=true;
                            productListPromotionFilterAdapter = new ProductListPromotionFilterAdapter(ProductListActivity.this, goodsBean.getData().getRecSearch().get(0));
                            delegateAdapter.addAdapter(0,productListPromotionFilterAdapter);
                            productListPromotionFilterAdapter.setFilterPromiotionListener(ProductListActivity.this);
                        }
                        productListPromotionFilterAdapter.setEmptyVisiable(false);
                        productListPromotionFilterAdapter.notifyDataSetChanged();
                    }
                } else if(hasSetPromotionFilterAdapter && productListPromotionFilterAdapter!=null){
                    productListPromotionFilterAdapter.setEmptyVisiable(true);
                    productListPromotionFilterAdapter.notifyDataSetChanged();
                    errorImage.setVisibility(View.VISIBLE);
                    errorImage.setImageResource(R.mipmap.ic_empty_search);
                    productListAdapter.notifyDataSetChanged();
                }else{
                    nestScroll.setVisibility(View.VISIBLE);
                    errorImage.setVisibility(View.VISIBLE);
                    errorImage.setImageResource(R.mipmap.ic_empty_search);
                    recycleView.setVisibility(View.GONE);
                }
                StringBuilder builder = new StringBuilder();
                int publishSize = size > 10 ? 10 : size;
                for (int i = 0; i < publishSize; i++) {
                    builder.append(goodsBean.getData().getProducts().getList().get(i).getId());
                    if (i != publishSize - 1) {
                        builder.append(",");
                    }
                }
                hasNext = goodsBean.getData().getProducts().isNext();
                if (list.size() > 15 && currentPage == 1) {
                    loadTopicData();
                } else {
                        //只有一个item显示的问题
//                    if (list.size() == 1) {
//                        GoodsBean.DataBean.ProductsBean.ListBean bean = new GoodsBean.DataBean.ProductsBean.ListBean();
//                        bean.isEmpty = true;
//                        list.add(bean);
//                        staggerLayoutHelper.onClear(virtualLayoutManager);
//                    }
                    productListAdapter.notifyDataSetChanged();
                }
                isLoad = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoad=false;
                nestScroll.setVisibility(View.VISIBLE);
                errorImage.setVisibility(View.VISIBLE);
                errorImage.setImageResource(R.mipmap.ic_no_net);
                recycleView.setVisibility(View.GONE);
                Util.showToast(ProductListActivity.this, Util.checkErrorType(error));
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadTopicData() {
        ProductThemeApi api = new ProductThemeApi();
        api.setP(currentPage);
        api.setPageSize(50);
        api.setKeyword(keyword);
        if (type == 0) {
            if (Util.isEmpty(categoryId) && min == -1 && max == -1 && Util.isEmpty(desiners)) {
                if (!Util.isEmpty(url)) {
                    api.setPreParam(url.substring(url.indexOf("?") + 1, url.length()));
                }
            }
        } else if (type == 1) {
            api.setKeyword(keyword);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductThemeBean>() {
            @Override
            public void onResponse(ProductThemeBean response) {
                if (list.size() > 15) {
                    if (response.getData().getThemes().size() > 0) {
                        list.add(15, response.getData().getThemes().get(0));
                    }
                }
                if (list.size() - 1 > 30) {
                    if (response.getData().getThemes().size() > 1) {
                        list.add(30, response.getData().getThemes().get(1));
                    }
                }
                if (list.size() - 2 > 45) {
                    if (response.getData().getThemes().size() > 2) {
                        list.add(45, response.getData().getThemes().get(2));
                    }
                }
                if (list.size() == 1) {
                    GoodsBean.DataBean.ProductsBean.ListBean bean = new GoodsBean.DataBean.ProductsBean.ListBean();
                    bean.isEmpty = true;
                    list.add(bean);
                    staggerLayoutHelper.onClear(virtualLayoutManager);
                }
                productListAdapter.notifyDataSetChanged();
                isLoad = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ProductListActivity.this, Util.checkErrorType(error));
            }
        });
    }


    @OnClick({R.id.iv_top, R.id.iv_footmark, R.id.back_iv, R.id.search_layout, R.id.tv_comprehensive, R.id.tv_near, R.id.tv_hot, R.id.tv_price, R.id.tv_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_footmark:
                if (Util.loginChecked(this, 300)) {
                    Intent intent = new Intent(this, FootMarkActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_top:
                recycleView.smoothScrollToPosition(0);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.search_layout:
                break;
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
            case R.id.tv_screen:
                if (newPop == null) {
                    newPop = new ScreenNewPop(this, 0, url, keyword, -1, -1, false);
                    newPop.setOnConFirmClickListner(new ScreenNewPop.OnConFirmClickListner() {
                        @Override
                        public void callBack(Boolean backHasPromotion, Integer backSubscribe, Boolean backAfter, String backProductSellType, String backCategoryId, String backDesignerIds, Integer minPrice, Integer maxPrice) {
                            subscribe = backSubscribe;
                            min = minPrice;
                            max = maxPrice;
                            hasPromotion = backHasPromotion;
                            categoryId = backCategoryId;
                            productSellType = backProductSellType;
                            desiners = backDesignerIds;
                            isafter = backAfter;
                            currentPage = 1;
                            if (!Util.isEmpty(categoryId)) {
                                isLoadUrl = false;
                            } else {
                                isLoadUrl = true;
                            }
                            loadData(keyword, false);
                        }
                    });
                }
                newPop.show(getWindow().getDecorView());
                break;
        }
    }


    private void selectPrice() {
        tvPrice.setTextColor(getResources().getColor(R.color.color_black));
        switch (currentPriceState) {
            case NONE:
                currentPriceState = ASC;
                ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_up);
                sortValue = "pa";
                break;
            case ASC:
                currentPriceState = DESC;
                ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_down);
                sortValue = "pd";
                break;
            case DESC:
                currentPriceState = ASC;
                ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_up);
                sortValue = "pa";

                break;
        }

        tvHot.setTextColor(getResources().getColor(R.color.color_black60));
        tvNear.setTextColor(getResources().getColor(R.color.color_black60));
        tvComprehensive.setTextColor(getResources().getColor(R.color.color_black60));
        currentPage = 1;
        if(productListPromotionFilterAdapter!=null){
            productListPromotionFilterAdapter.reSetTvStatus();
        }
        loadData(keyword, false);
    }

    private void selectHot() {
        tvHot.setTextColor(getResources().getColor(R.color.color_black));
        sortValue = "sd";
        tvComprehensive.setTextColor(getResources().getColor(R.color.color_black60));
        tvNear.setTextColor(getResources().getColor(R.color.color_black60));
        tvPrice.setTextColor(getResources().getColor(R.color.color_black60));
        ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = NONE;
        currentPage = 1;
        if(productListPromotionFilterAdapter!=null){
            productListPromotionFilterAdapter.reSetTvStatus();
        }
        loadData(keyword, false);
    }

    private void selectNear() {
        tvNear.setTextColor(getResources().getColor(R.color.color_black));
        sortValue = "dd";
        tvHot.setTextColor(getResources().getColor(R.color.color_black60));
        tvComprehensive.setTextColor(getResources().getColor(R.color.color_black60));
        tvPrice.setTextColor(getResources().getColor(R.color.color_black60));
        ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = NONE;
        currentPage = 1;
        if(productListPromotionFilterAdapter!=null){
            productListPromotionFilterAdapter.reSetTvStatus();
        }
        loadData(keyword, false);
    }

    private void selectComprehensive() {
        tvComprehensive.setTextColor(getResources().getColor(R.color.color_black));
        sortValue = null;
        tvHot.setTextColor(getResources().getColor(R.color.color_black60));
        tvNear.setTextColor(getResources().getColor(R.color.color_black60));
        tvPrice.setTextColor(getResources().getColor(R.color.color_black60));
        ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = NONE;
        currentPage = 1;
        if(productListPromotionFilterAdapter!=null){
            productListPromotionFilterAdapter.reSetTvStatus();
        }
        loadData(keyword, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivTop.clearAnimation();
        llButton.clearAnimation();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(PushBean pushBean) {
        Util.showPush(this, pushToast, pushBean);
    }

    @Override
    public void filterPromotion(boolean isFilter) {
        loadData(keyword,isFilter);
    }
}
