package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.Decoration.CartDecoration;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CartAdapter;
import com.d2cmall.buyer.adapter.RecommendProductAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CalculateApi;
import com.d2cmall.buyer.api.CartCollectApi;
import com.d2cmall.buyer.api.CartDeleteApi1;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UpdateSkuApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCalcuteBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.CartListBean;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.binder.RecommendProductTitleBinder;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.CartGroupListener;
import com.d2cmall.buyer.listener.CheckStateChangeListener;
import com.d2cmall.buyer.listener.PowerGroupListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CartDeletePop;
import com.d2cmall.buyer.widget.CartRecycleView;
import com.d2cmall.buyer.widget.CartSelectPop;
import com.d2cmall.buyer.widget.CheckBox;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.SelectPromotionPop;
import com.d2cmall.buyer.widget.ShoppingPop;
import com.d2cmall.buyer.widget.SliderView1;
import com.d2cmall.buyer.widget.TransparentPop;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenu;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenuView;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 18:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 购物车页面
 */
public class CartActivity extends BaseActivity implements SliderView1.CartListener, SwipeMenuView.OnSwipeItemClickListener {

    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.recycle_view)
    CartRecycleView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.select_all)
    CheckBox selectAll;
    @Bind(R.id.all_tv)
    TextView allTv;
    @Bind(R.id.all_num)
    TextView allNum;
    @Bind(R.id.total_money)
    TextView totalMoney;
    @Bind(R.id.total_money_tag)
    TextView totalMoneyTag;
    @Bind(R.id.select_delete)
    TextView selectDelete;
    @Bind(R.id.select_collect)
    TextView selectCollect;
    @Bind(R.id.bottom_ll)
    RelativeLayout bottomLl;
    @Bind(R.id.tip)
    TextView tip;

    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;
    private CartDecoration decoration;

    private List<CartListBean.DataBean.CartBean> list = new ArrayList<>();//所有有项数据
    private List<CartListBean.DataBean.CartBean> crossList = new ArrayList<>();//所有跨境商品
    private List<CartListBean.DataBean.CartBean> commonList = new ArrayList<>();//所有国内商品
    private List<CartListBean.DataBean.CartBean> selectCrossList = new ArrayList<>();//跨境选中项
    private List<CartListBean.DataBean.CartBean> selectCommonList = new ArrayList<>();//非跨境选中项
    private List<CartListBean.DataBean.CartBean> inValidList = new ArrayList<>();//无效项
    private HashMap<Integer, List<CartListBean.DataBean.CartBean>> promotionList = new HashMap<>();
    public HashMap<Integer, Integer> pieceReductionPromotion = new HashMap<>(); //维度是件数
    public HashMap<Integer, Double> fullReductionPromotion = new HashMap<>(); //维度是价格
    public ArrayList<Integer> skuIds;
    private CartAdapter cartAdapter; //购物车适配器

    private List<ProductDetailBean.DataBean.RecommendProductsBean> recommendProductList = new ArrayList<>(); //推荐商品
    private BaseVirtualAdapter<DefaultHolder> titleAdapter; //推荐商品title
    private RecommendProductAdapter recommendAdapter; //推荐商品适配器

    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    private long currentEditSkuId; //当前编辑的skuId
    private boolean isEditState; //是否在编辑状态

    private ShoppingPop shoppingPop; //编辑颜色尺码框
    private int limitStore;

    private int commonSize;
    private int crossSize;

    private boolean commonChecked;
    private boolean crossChecked;

    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        doBusiness();
    }

    public void doBusiness() {
        initTitle();
        skuIds = getIntent().getIntegerArrayListExtra("skuid");
        layoutManager = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(1, 0);
        recycledViewPool.setMaxRecycledViews(2, 0);
        recycledViewPool.setMaxRecycledViews(9, 0);
        recycledViewPool.setMaxRecycledViews(10, 4);
        recycleView.setRecycledViewPool(recycledViewPool);

        decoration = CartDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (list.size() > position) {
                            if (position < commonSize) {
                                return "D2C好设计";
                            } else if (crossSize > 0 && position >= commonSize && position < (commonSize + crossSize)) {
                                return "D2C全球购";
                            }
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (list.size() > position) {
                            View view = getLayoutInflater().inflate(R.layout.layout_cart_group_view, null, false);
                            ((CheckBox) view.findViewById(R.id.checkbox)).setCheckColorId(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
                            TextView textView = (TextView) view.findViewById(R.id.tv);
                            Drawable drawable;
                            if (position < commonSize) {
                                drawable = getResources().getDrawable(R.mipmap.icon_gouwuche_d2cziying);
                            } else {
                                drawable = getResources().getDrawable(R.mipmap.icon_gouwuche_quanqiugou);
                            }
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            textView.setCompoundDrawables(drawable, null, null, null);
                            textView.setText(getGroupName(position));
                            return view;
                        } else {
                            return null;
                        }
                    }
                })   //设置高度
                .setCartGroupListener(new CartGroupListener() {
                    @Override
                    public void selectAllGlobal(boolean is) {
                        CartActivity.this.selectAllGlobal(is);
                    }

                    @Override
                    public void selectAllInland(boolean is) {
                        CartActivity.this.selectAllInland(is);
                    }

                }).setGroupHeight(ScreenUtil.dip2px(50)).build();
        recycleView.addItemDecoration(decoration);

        cartAdapter = new CartAdapter(this, list, selectCrossList, selectCommonList, this, this);
        cartAdapter.setCheckStateChangeListener(new CartAdapter.CheckCallBack() {
            @Override
            public void onStatusChanged(View v, boolean isChecked, CartListBean.DataBean.CartBean cartBean) {
                if (cartBean.getOrderPromotion() != null) {
                    String type = cartBean.getOrderPromotion().getPromotionType();
                    if (type.equals("7") || type.equals("5")
                            || type.equals("6")) {
                        int num = pieceReductionPromotion.get(cartBean.getOrderPromotion().getPromotionId());
                        if (isChecked) {
                            num += cartBean.getQuantity();
                        } else {
                            num -= cartBean.getQuantity();
                        }
                        pieceReductionPromotion.put(cartBean.getOrderPromotion().getPromotionId(), num);
                        cartAdapter.notifyDataSetChanged();
                    }
                    if (type.equals("2") || type.equals("3")) {
                        double price = fullReductionPromotion.get(cartBean.getOrderPromotion().getPromotionId());
                        if (isChecked) {
                            price += (cartBean.getMinPrice() * cartBean.getQuantity());
                        } else {
                            price -= (cartBean.getMinPrice() * cartBean.getQuantity());
                        }
                        fullReductionPromotion.put(cartBean.getOrderPromotion().getPromotionId(), price);
                        cartAdapter.notifyDataSetChanged();
                    }
                }
                checkState();
                updateAmount();
            }
        });
        cartAdapter.setClearListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePop(getInVailId(), inValidList.size());
            }
        });
        delegateAdapter.addAdapter(0, cartAdapter);

        progressBar.setVisibility(View.VISIBLE);
        loadData();
        loadRecommendData();
        initListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadData();
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_main_menu4);
        titleRight.setText("编辑");
        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditState = !isEditState;
                if (isEditState) {
                    titleRight.setText("完成");
                    allNum.setVisibility(View.GONE);
                    totalMoney.setVisibility(View.GONE);
                    totalMoneyTag.setVisibility(View.GONE);
                    selectDelete.setVisibility(View.VISIBLE);
                    selectCollect.setVisibility(View.VISIBLE);
                    if (titleAdapter != null) {
                        delegateAdapter.removeAdapter(titleAdapter);
                        delegateAdapter.removeAdapter(recommendAdapter);
                    }
                } else {
                    titleRight.setText("编辑");
                    allNum.setVisibility(View.VISIBLE);
                    totalMoney.setVisibility(View.VISIBLE);
                    if (!Util.isEmpty(totalMoney.getText().toString())) {
                        totalMoneyTag.setVisibility(View.VISIBLE);
                    }
                    selectDelete.setVisibility(View.GONE);
                    selectCollect.setVisibility(View.GONE);
                    if (titleAdapter != null) {
                        delegateAdapter.addAdapter(titleAdapter);
                        delegateAdapter.addAdapter(recommendAdapter);
                    }
                }
                cartAdapter.setEditState(isEditState);
                cartAdapter.notifyDataSetChanged();
            }
        });
        selectAll.setCheckColorId(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
    }

    private void initListener() {
        selectAll.setOnStatusChangedListener(new CheckStateChangeListener() {
            @Override
            public void onStatusChanged(View v, boolean isChecked) {
                if (isChecked) {//全选
                    calculateAllUnSelect();
                    selectCrossList.clear();
                    selectCommonList.clear();
                    if (crossSize>0){
                        decoration.setGroupSelect("D2C全球购", true);
                    }
                    if (commonSize>0){
                        decoration.setGroupSelect("D2C好设计", true);
                    }
                    if (crossSize>0&&commonSize>0){
                        tip.setVisibility(View.VISIBLE);
                    }
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getAvailableStore() > 0) {
                            if ("CROSS".equals(list.get(i).getProductTradeType())) {
                                selectCrossList.add(list.get(i));
                            } else if ("COMMON".equals(list.get(i).getProductTradeType())) {
                                selectCommonList.add(list.get(i));
                            }
                        }
                    }
                    calculateAllSelect();
                    allTv.setText("取消全选");
                } else {
                    allTv.setText("全选");
                    if (crossSize>0){
                        decoration.setGroupSelect("D2C全球购", false);
                    }
                    if (commonSize>0){
                        decoration.setGroupSelect("D2C好设计", false);
                    }
                    tip.setVisibility(View.GONE);
                    calculateAllUnSelect();
                    selectCrossList.clear();
                    selectCommonList.clear();
                }
                updateAmount();
                if (cartAdapter != null) {
                    cartAdapter.notifyDataSetChanged();
                }
            }
        });
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh=true;
                loadData();
                if (!isEditState) {
                    loadRecommendData();
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = layoutManager.findLastVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();
                if (lastVisPosition >= itemCount - 3 && layoutManager.findFirstVisibleItemPosition() > 1) {
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    if (endAdapter != null) {
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter = null;
                    }
                }
            }
        });
    }

    private void calculateAllSelect() {
        List<CartListBean.DataBean.CartBean> selectList = new ArrayList<>();
        if (selectCrossList.size() > 0) {
            selectList.addAll(selectCrossList);
        }
        if (selectCommonList.size() > 0) {
            selectList.addAll(selectCommonList);
        }
        int size = selectList.size();
        for (int i = 0; i < size; i++) {
            if (selectList.get(i).getOrderPromotion() != null) {
                String type = selectList.get(i).getOrderPromotion().getPromotionType();
                if (type.equals("7") || type.equals("5")
                        || type.equals("6")) {
                    int num = pieceReductionPromotion.get(selectList.get(i).getOrderPromotion().getPromotionId());
                    num += selectList.get(i).getQuantity();
                    pieceReductionPromotion.put(selectList.get(i).getOrderPromotion().getPromotionId(), num);
                }
                if (type.equals("2") || type.equals("3")) {
                    double price = fullReductionPromotion.get(selectList.get(i).getOrderPromotion().getPromotionId());
                    price += selectList.get(i).getMinPrice() * selectList.get(i).getQuantity();
                    fullReductionPromotion.put(selectList.get(i).getOrderPromotion().getPromotionId(), price);
                }
            }
        }
    }

    private void calculateAllUnSelect() {
        List<CartListBean.DataBean.CartBean> selectList = new ArrayList<>();
        if (selectCrossList.size() > 0) {
            selectList.addAll(selectCrossList);
        }
        if (selectCommonList.size() > 0) {
            selectList.addAll(selectCommonList);
        }
        int size = selectList.size();
        for (int i = 0; i < size; i++) {
            if (selectList.get(i).getOrderPromotion() != null) {
                String type = selectList.get(i).getOrderPromotion().getPromotionType();
                if (type.equals("7") || type.equals("5")
                        || type.equals("6")) {
                    pieceReductionPromotion.put(selectList.get(i).getOrderPromotion().getPromotionId(), 0);
                }
                if (type.equals("2") || type.equals("3")) {
                    fullReductionPromotion.put(selectList.get(i).getOrderPromotion().getPromotionId(), 0.0);
                }
            }
        }
    }

    @OnClick({R.id.all_num, R.id.select_delete, R.id.select_collect})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.all_num: //结算
                if (selectCrossList.size() == 0 && selectCommonList.size() == 0) {
                    Toast.makeText(this, "请先选择商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                stat();//talkdata统计
                if (selectCrossList.size() > 0 && selectCommonList.size() > 0) {
                    showSelectPop();
                } else if (selectCrossList.size() > 0) {
                    settle(selectCrossList, true);
                } else if (selectCommonList.size() > 0) {
                    settle(selectCommonList, false);
                }
                //去下单
                break;
            case R.id.select_delete://批量删除
                if (selectCrossList.size() == 0 && selectCommonList.size() == 0) {
                    Toast.makeText(this, "请先选择要删除的商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDeletePop(getSelectId(), selectCrossList.size() + selectCommonList.size());
                break;
            case R.id.select_collect: //批量移入收藏
                if (selectCrossList.size() == 0 && selectCommonList.size() == 0) {
                    Toast.makeText(this, "请先选择要收藏的商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                collectOrDelete(getSelectId(), 1);
                break;
        }
    }

    private void stat(){
        HashMap<String,String> map=new HashMap<>();
        int commonSize=selectCommonList.size();
        for (int i=0;i<commonSize;i++){
            map.put("结算_非跨境商品id",String.valueOf(selectCommonList.get(i).getProductId()));
            stat("V3购物车","结算",map);
            map.clear();
        }
        int size=selectCrossList.size();
        for (int i=0;i<size;i++){
            map.put("结算_跨境商品id",String.valueOf(selectCrossList.get(i).getProductId()));
            stat("V3购物车","结算",map);
            map.clear();
        }
    }

    private void showSelectPop() {
        CartSelectPop selectPop = new CartSelectPop(this, selectCrossList.size(), selectCommonList.size(), new CartSelectPop.SelectListener() {
            @Override
            public void selectGlobal() {
                settle(selectCrossList, true);
            }

            @Override
            public void selectCommon() {
                settle(selectCommonList, false);
            }
        });
        selectPop.show(getWindow().getDecorView());
    }

    private void settle(List<CartListBean.DataBean.CartBean> selectList, boolean isGlobal) {
        int size = selectList.size();
        Intent intent;
        if (isGlobal) { //跨境下单
            intent = new Intent(this, GlobalOrderBalanceActivity.class);
        } else {
            intent = new Intent(this, OrderBalanceActivity.class);
        }
        long[] cartId = new long[size];
        long[] goodPromationId = new long[size];
        long[] orderPromationId = new long[size];
        for (int i = 0; i < size; i++) {
            cartId[i] = selectList.get(i).getId();
            goodPromationId[i] = selectList.get(i).getGoodPromotionId();
            orderPromationId[i] = selectList.get(i).getOrderPromotionId();
        }
        intent.putExtra("cartId", cartId);
        intent.putExtra("goodPromationId", goodPromationId);
        intent.putExtra("orderPromationId", orderPromationId);
        intent.putExtra("source", OrderBalanceActivity.FROMCAR);
        startActivity(intent);
    }

    /**
     * type=0表示删除购物车数据
     * type=1表示收藏购物车数据
     *
     * @param ids
     * @param type
     */
    private void collectOrDelete(String ids, int type) {
        D2CApplication.mSharePref.removeKey(SharePrefConstant.PRODUCT_ID);
        BaseApi api = null;
        if (type == 0) {
            api = new CartDeleteApi1();
            ((CartDeleteApi1) api).ids = ids;
        } else if (type == 1) {
            api = new CartCollectApi();
            ((CartCollectApi) api).ids = ids;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartListBean>() {
            @Override
            public void onResponse(CartListBean response) {
                loadData();
                getCartNum();
            }
        });
    }

    private void getCartNum() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CART_COUNT_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartCountBean>() {
            @Override
            public void onResponse(CartCountBean response) {
                HomeActivity.count = response.getData().getCartItemCount();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART_COUNT));
            }
        });
    }

    private void showDeletePop(final String ids, int count) {
        CartDeletePop cartDeletePop = new CartDeletePop(this);
        cartDeletePop.setBtnSure(R.string.action_delete);
        String hint = getResources().getString(R.string.msg_delete_explore);
        if (count > 0) {
            hint = "确认将这" + count + "件商品删除？";
        }
        cartDeletePop.setContent(hint);
        cartDeletePop.setBack(new CartDeletePop.CallBack() {
            @Override
            public void sure() {
                collectOrDelete(ids, 0);
            }

            @Override
            public void cancel() {

            }
        });
        cartDeletePop.show(ptr);
    }

    public String getSelectId() {
        List<CartListBean.DataBean.CartBean> selectList = new ArrayList<>();
        if (selectCrossList.size() > 0) {
            selectList.addAll(selectCrossList);
        }
        if (selectCommonList.size() > 0) {
            selectList.addAll(selectCommonList);
        }
        if (selectList.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int length = selectList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(selectList.get(i).getId());
            if (i < length - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    public String getInVailId() {
        if (inValidList.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int length = inValidList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(inValidList.get(i).getId());
            if (i < length - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    public void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CART_LIST_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartListBean>() {
            @Override
            public void onResponse(CartListBean cartListBean) {
                if (isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                list.clear();
                crossList.clear();
                commonList.clear();
                promotionList.clear();
                selectCrossList.clear();
                selectCommonList.clear();
                inValidList.clear();
                if (cartListBean.getData().getCart() != null && cartListBean.getData().getCart().size() > 0) {
                    limitStore = cartListBean.getData().getCompareStore();
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) ptr.getLayoutParams();
                    rl.bottomMargin = ScreenUtil.dip2px(48);
                    bottomLl.setVisibility(View.VISIBLE);
                    titleRight.setVisibility(View.VISIBLE);
                    list.addAll(checkTradeType(cartListBean.getData().getCart()));
                    decoration.setLimitPosition(list.size());
                    checkSkuId();
                    updateAmount();
                    checkState();
                    setCartAdapter();
                } else {
                    checkState();
                    setCartAdapter();
                    loadRecommendData();
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) ptr.getLayoutParams();
                    rl.bottomMargin = 0;
                    bottomLl.setVisibility(View.GONE);
                    titleRight.setVisibility(View.GONE);
                    if (isEditState) {
                        isEditState = false;
                        if (titleAdapter!=null){
                            delegateAdapter.addAdapter(titleAdapter);
                            delegateAdapter.addAdapter(recommendAdapter);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) ptr.getLayoutParams();
                rl.bottomMargin = 0;
                bottomLl.setVisibility(View.GONE);
                titleRight.setVisibility(View.GONE);
            }
        });
    }

    private void checkSkuId() {
        if (isRefresh){
            isRefresh=false;
            return;
        }
        String localSkuids=D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.LOCAL_SKUIDS,"");
        if (!Util.isEmpty(localSkuids)){
            String[] ids=localSkuids.split(",");
            if (skuIds==null){
                skuIds=new ArrayList<>();
            }
            int size=ids.length;
            for (int i=0;i<size;i++){
                skuIds.add(Integer.valueOf(ids[i]));
            }
        }
        if (skuIds != null && skuIds.size() > 0) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (skuIds.contains(list.get(i).getSkuId())) {
                    if ("CROSS".equals(list.get(i).getProductTradeType())) {
                        selectCrossList.add(list.get(i));
                    } else if ("COMMON".equals(list.get(i).getProductTradeType())) {
                        selectCommonList.add(list.get(i));
                    }
                }
            }
        }
    }

    /**
     * 推荐商品只有固定数据
     */
    public void loadRecommendData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/similar/cart/top/40");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean recommendListBean) {
                recommendProductList.clear();
                if (recommendListBean.getList() != null && recommendListBean.getList().size() > 0) {
                    recommendProductList.addAll(recommendListBean.getList());
                }
                setRecommendAdapter();
            }
        });
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.REFRESH_CART) {
            loadData();
            getCartNum();
        } else if (bean.type == Constants.ActionType.CALCULTE_CART) {
            updateAmount();
        } else if (bean.type == Constants.ActionType.FINISH_CART) {
            finish();
        }
    }

    /**
     * 设置推荐商品设配器
     */
    private void setRecommendAdapter() {
        if (recommendAdapter == null) {
            int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
            RecommendProductTitleBinder titleBinder = new RecommendProductTitleBinder();
            titleAdapter = new BaseVirtualAdapter<>(titleBinder, 9);
            delegateAdapter.addAdapter(titleAdapter);
            recommendAdapter = new RecommendProductAdapter(this, recommendProductList, itemWidth);
            delegateAdapter.addAdapter(recommendAdapter);
        } else {
            recommendAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置购物车列表适配器
     */
    public void setCartAdapter() {
        recycleView.setVisibility(View.VISIBLE);
        cartAdapter.setHasInvalid(inValidList.size() > 0);
        cartAdapter.setEditState(isEditState);
        cartAdapter.setLimitStore(limitStore);
        if (decoration!=null){
            decoration.reset();
        }
        cartAdapter.notifyDataSetChanged();
    }

    private List<CartListBean.DataBean.CartBean> checkCart(List<CartListBean.DataBean.CartBean> items) {
        List<CartListBean.DataBean.CartBean> result = new ArrayList<>();
        List<CartListBean.DataBean.CartBean> temp=new ArrayList<>();

        int size = items.size();
        for (int i = 0; i < size; i++) {
            if (items.get(i).getOrderPromotion() != null) {
                String type = items.get(i).getOrderPromotion().getPromotionType();
                if (type.equals("7") || type.equals("5") ||
                        type.equals("6")) {
                    pieceReductionPromotion.put(items.get(i).getOrderPromotion().getPromotionId(), 0);
                }
                if (type.equals("2") ||
                        type.equals("3")) {
                    fullReductionPromotion.put(items.get(i).getOrderPromotion().getPromotionId(), 0.0);
                }
                if (promotionList.containsKey(items.get(i).getOrderPromotion().getPromotionId())) {
                    List<CartListBean.DataBean.CartBean> list = promotionList.get(items.get(i).getOrderPromotion().getPromotionId());
                    list.add(items.get(i));
                } else {
                    List<CartListBean.DataBean.CartBean> list = new ArrayList<>();
                    list.add(items.get(i));
                    items.get(i).setShowPromotion(true);
                    promotionList.put(items.get(i).getOrderPromotion().getPromotionId(), list);
                }
            } else {
                temp.add(items.get(i));
            }
        }
        Set<Map.Entry<Integer, List<CartListBean.DataBean.CartBean>>> entrySet = promotionList.entrySet();
        for (Map.Entry<Integer, List<CartListBean.DataBean.CartBean>> entry : entrySet) {
            int length = entry.getValue().size();
            entry.getValue().get(length - 1).setShowbg(true);
            result.addAll(entry.getValue());
        }
        result.addAll(temp);
        return result;
    }

    private List<CartListBean.DataBean.CartBean> checkTradeType(List<CartListBean.DataBean.CartBean> items) {
        List<CartListBean.DataBean.CartBean> result = new ArrayList<>();
        int size = items.size();
        for (int i = 0; i < size; i++) {
            if (items.get(i).getAvailableStore() <= 0) {
                inValidList.add(items.get(i));
            } else {
                if ("CROSS".equals(items.get(i).getProductTradeType())) {
                    crossList.add(items.get(i));
                } else if ("COMMON".equals(items.get(i).getProductTradeType())) {
                    commonList.add(items.get(i));
                }else {
                    commonList.add(items.get(i));
                }
            }
        }
        commonSize = commonList.size();
        crossSize = crossList.size();
        List<CartListBean.DataBean.CartBean> temp = new ArrayList<>();
        if (commonList.size() > 0) {
            temp.addAll(commonList);
        }
        if (crossList.size() > 0) {
            temp.addAll(crossList);
        }
        if (temp.size() > 0) {
            result.addAll(checkCart(temp));
        }
        if (inValidList.size() > 0) {
            inValidList.get(0).setInvaledFirst(true);
        }
        result.addAll(inValidList);
        if (commonList.size() > 0) {
            commonList.get(0).setGroupFirst(true);
            commonList.get(commonSize - 1).setGroupLast(true);
        }
        if (crossList.size() > 0) {
            crossList.get(0).setGroupFirst(true);
            crossList.get(crossSize - 1).setGroupLast(true);
        }
        return result;
    }

    @Override
    public void selectPromotion(final CartListBean.DataBean.CartBean entity, final TextView promotionTv) {
        final SelectPromotionPop selectPromotionPop = new SelectPromotionPop(this, entity.getChoiceOrderPromotions(), promotionTv.getText().toString());
        selectPromotionPop.setDissMissListener(new TransparentPop.DismissListener() {
            @Override
            public void dismissStart() {

            }

            @Override
            public void dismissEnd() {
                if (!Util.isEmpty(selectPromotionPop.getPromotion())) {
                    promotionTv.setText(selectPromotionPop.getPromotion());
                    for (int i = 0; i < entity.getChoiceOrderPromotions().size(); i++) {
                        if (selectPromotionPop.getPromotion().equals(entity.getChoiceOrderPromotions().get(i).getName())) {
                            CartListBean.DataBean.CartBean.OrderPromotionBean choiceOrderPromotionsBean = entity.getChoiceOrderPromotions().remove(i);
                            entity.getChoiceOrderPromotions().add(0, choiceOrderPromotionsBean);
                            entity.setOrderPromotionId(entity.getChoiceOrderPromotions().get(0).getId());
                        }
                    }
                    updateAmount();
                }
            }
        });
        selectPromotionPop.show(promotionTv);
    }

    @Override
    public void selectSize(View view) {
        String[] strings = ((String) view.getTag()).split(",");
        long productId = Long.valueOf(strings[0]);
        final long cartItemId = Long.valueOf(strings[1]);
        checkCurrentSkuId(cartItemId);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_COLOR_SIZE, productId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductDetailBean>() {
            @Override
            public void onResponse(ProductDetailBean response) {
                ProductDetailBean.DataBean.ProductBean productEntity = response.getData().getProduct();

                shoppingPop = new ShoppingPop(CartActivity.this);
                shoppingPop.setData(productEntity);
                shoppingPop.showNumLayout(false);
                shoppingPop.setBackListener(new ShoppingPop.CallBackListener() {
                    @Override
                    public void sure(long skuId) {
                        if (skuId <= 0 || skuId == currentEditSkuId) {
                            return;
                        }
                        UpdateSkuApi updateSkuApi = new UpdateSkuApi();
                        updateSkuApi.cartItemId = cartItemId;
                        updateSkuApi.skuId = skuId;
                        D2CApplication.httpClient.loadingRequest(updateSkuApi, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                loadData();
                            }
                        });
                    }
                });
                shoppingPop.show(3, recycleView);
            }
        });
    }

    //是否选中所有全球购商品
    public void selectAllGlobal(boolean is) {
        if (is) {
            for (int i = 0; i < crossSize; i++) {
                if (!selectCrossList.contains(crossList.get(i))) {
                    selectCrossList.add(crossList.get(i));
                }
            }
        } else {
            for (int i = 0; i < crossSize; i++) {
                if (selectCrossList.contains(crossList.get(i))) {
                    selectCrossList.remove(crossList.get(i));
                }
            }
        }
        checkState();
        updateAmount();
        cartAdapter.notifyDataSetChanged();
    }

    //是否选中所有非全球购商品
    public void selectAllInland(boolean is) {
        if (is) {
            for (int i = 0; i < commonSize; i++) {
                if (!selectCommonList.contains(commonList.get(i))) {
                    selectCommonList.add(commonList.get(i));
                }
            }
        } else {
            for (int i = 0; i < commonSize; i++) {
                if (selectCommonList.contains(commonList.get(i))) {
                    selectCommonList.remove(commonList.get(i));
                }
            }
        }
        checkState();
        updateAmount();
        cartAdapter.notifyDataSetChanged();
    }

    private void checkCurrentSkuId(long cartItemId) {
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if (cartItemId == list.get(i).getId()) {
                currentEditSkuId = list.get(i).getSkuId();
                break;
            }
        }
    }

    private void checkState() {
        int crossSize = crossList.size();
        int selectCrossSize = selectCrossList.size();
        if (selectCrossSize > 0 && crossSize == selectCrossSize) {
            decoration.setGroupSelect("D2C全球购", true);
            if (!crossChecked){
                cartAdapter.notifyDataSetChanged();
            }
            crossChecked=true;
        } else {
            decoration.setGroupSelect("D2C全球购", false);
            if (crossChecked){
                cartAdapter.notifyDataSetChanged();
            }
            crossChecked=false;
        }

        int commonSize = commonList.size();
        int selectCommonSize = selectCommonList.size();
        if (selectCommonSize > 0 && commonSize == selectCommonSize) {
            decoration.setGroupSelect("D2C好设计", true);
            if (!commonChecked){
                cartAdapter.notifyDataSetChanged();
            }
            commonChecked=true;
        } else {
            decoration.setGroupSelect("D2C好设计", false);
            if (commonChecked){
                cartAdapter.notifyDataSetChanged();
            }
            commonChecked=false;
        }

        if ((commonSize + crossSize) > 0 && (commonSize + crossSize) == (selectCommonSize + selectCrossSize)) {
            selectAll.setChecked(true);
            allTv.setText("取消全选");
        } else {
            selectAll.setChecked(false);
            allTv.setText("全选");
        }
        if (selectCrossSize > 0 && selectCommonSize > 0) {
            tip.setVisibility(View.VISIBLE);
        } else {
            tip.setVisibility(View.GONE);
        }
    }

    private void updateAmount() {
        List<CartListBean.DataBean.CartBean> selectList = new ArrayList<>();
        if (selectCrossList.size() > 0) {
            selectList.addAll(selectCrossList);
        }
        if (selectCommonList.size() > 0) {
            selectList.addAll(selectCommonList);
        }
        if (selectList.size() == 0) {
            allNum.setText(String.format(getString(R.string.label_balance), 0));
            totalMoney.setText(getString(R.string.label_total_money, "0"));
            totalMoneyTag.setText("");
            totalMoneyTag.setVisibility(View.GONE);
            if (!isEditState) {
                allNum.setVisibility(View.VISIBLE);
                totalMoney.setVisibility(View.VISIBLE);
            }
            return;
        }
        int size = selectList.size();
        allNum.setVisibility(View.VISIBLE);
        CalculateApi calculateApi = new CalculateApi();
        long[] cartItemIds = new long[size];
        int[] quantity = new int[size];
        long[] goodPromotionId = new long[size];
        long[] orderPromotionId = new long[size];
        int num=0;
        for (int i = 0; i < size; i++) {
            cartItemIds[i] = selectList.get(i).getId();
            quantity[i] = selectList.get(i).getQuantity();
            goodPromotionId[i] = selectList.get(i).getGoodPromotionId();
            orderPromotionId[i] = selectList.get(i).getOrderPromotionId();
            num+=selectList.get(i).getQuantity();
        }
        allNum.setText(String.format(getString(R.string.label_balance), num));
        calculateApi.cartItemIds = cartItemIds;
        calculateApi.quantity = quantity;
        calculateApi.goodPromotionId = goodPromotionId;
        calculateApi.orderPromotionId = orderPromotionId;
        D2CApplication.httpClient.loadingRequest(calculateApi, new BeanRequest.SuccessListener<CartCalcuteBean>() {
            @Override
            public void onResponse(CartCalcuteBean response) {
                //allNum.setText(String.format(getString(R.string.label_balance), response.getData().getCart().getItems().size()));
                totalMoney.setText(getString(R.string.label_total_money, Util.getNumberFormat(response.getData().getCart().getTotalAmount())));
                if (response.getData().getCart().getPromotionAmount() > 0) {
                    totalMoneyTag.setVisibility(View.VISIBLE);
                    totalMoneyTag.setText(getString(R.string.label_total_money_tag, Util.getNumberFormat(response.getData().getCart().getTotalAmount() + response.getData().getCart().getPromotionAmount()), Util.getNumberFormat(response.getData().getCart().getPromotionAmount())));
                } else {
                    totalMoneyTag.setVisibility(View.GONE);
                    totalMoneyTag.setText("");
                }
                if (!isEditState) {
                    allNum.setVisibility(View.VISIBLE);
                    totalMoney.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(CartActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        if (index == 0) { //移入收藏
            int position = view.getPosition();
            collectOrDelete(list.get(position).getId() + "", 1);
        } else if (index == 1) {   //删除
            int position = view.getPosition();
            showDeletePop(list.get(position).getId() + "", 1);
        }
    }

    @Override
    public void finish() {
        if (selectCrossList.size()>0||selectCommonList.size()>0){
            int selectCrossSize=selectCrossList.size();
            int selectCommonSize=selectCommonList.size();
            StringBuilder builder=new StringBuilder();
            for (int i=0;i<selectCrossSize;i++){
                builder.append(selectCrossList.get(i).getSkuId());
                builder.append(",");
            }
            for (int i=0;i<selectCommonSize;i++){
                builder.append(selectCommonList.get(i).getSkuId());
                builder.append(",");
            }
            String localSkuids=builder.toString();
            int index=localSkuids.lastIndexOf(",");
            localSkuids=localSkuids.substring(0,index);
            D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.LOCAL_SKUIDS,localSkuids);
        }else {
            D2CApplication.mSharePref.removeKey(SharePrefConstant.LOCAL_SKUIDS);
        }
        super.finish();
    }
}
