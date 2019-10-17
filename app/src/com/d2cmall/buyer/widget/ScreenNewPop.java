package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ScreenNewBrandAdapter;
import com.d2cmall.buyer.adapter.ScreenNewPriceAdapter;
import com.d2cmall.buyer.adapter.ScreenNewTopAdapter;
import com.d2cmall.buyer.api.ScreenApi;
import com.d2cmall.buyer.api.SeriesApi;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.bean.SerieBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.TransparentPop.InvokeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.SCREEN_PRODUCT_URL;

/**
 * Created by rookie on 2018/3/26.
 */

public class ScreenNewPop implements InvokeListener {
    @Bind(R.id.tv_sale)
    TextView tvSale;
    @Bind(R.id.iv_sale_mark)
    ImageView ivSaleMark;
    @Bind(R.id.tv_try)
    TextView tvTry;
    @Bind(R.id.iv_try_mark)
    ImageView ivTryMark;
    @Bind(R.id.tv_no_reason_change)
    TextView tvNoReasonChange;
    @Bind(R.id.iv_no_reason_change_mark)
    ImageView ivNoReasonChangeMark;
    @Bind(R.id.tv_cash)
    TextView tvCash;
    @Bind(R.id.iv_cash_mark)
    ImageView ivCashMark;
    @Bind(R.id.tv_pre_sell)
    TextView tvPreSell;
    @Bind(R.id.iv_pre_sell_mark)
    ImageView ivPreSellMark;
    @Bind(R.id.tv_made)
    TextView tvMade;
    @Bind(R.id.iv_made_mark)
    ImageView ivMadeMark;
    @Bind(R.id.tv_selected_first)
    TextView tvSelectedFirst;
    @Bind(R.id.rl_first_select)
    RelativeLayout rlFirstSelect;
    @Bind(R.id.screen_type_grid)
    LineGridView screenTypeGrid;
    @Bind(R.id.iv_open)
    ImageView ivOpen;
    @Bind(R.id.screen_designer_grid)
    LineGridView screenDesignerGrid;
    @Bind(R.id.tv_look_all)
    TextView tvLookAll;
    @Bind(R.id.screen_price_grid)
    LineGridView screenPriceGrid;
    @Bind(R.id.tv_reset)
    TextView tvReset;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.select_layout)
    LinearLayout selectLayout;
    @Bind(R.id.tv_selected_label)
    TextView tvSelectedLabel;
    @Bind(R.id.ll_service)
    LinearLayout llService;
    @Bind(R.id.ll_type)
    LinearLayout llType;
    @Bind(R.id.rl_sale)
    RelativeLayout rlSale;
    @Bind(R.id.rl_try)
    RelativeLayout rlTry;
    @Bind(R.id.rl_after)
    RelativeLayout rlAfter;
    @Bind(R.id.rl_spot)
    RelativeLayout rlSpot;
    @Bind(R.id.rl_presell)
    RelativeLayout rlPresell;
    @Bind(R.id.rl_custom)
    RelativeLayout rlCustom;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.tv_type_label)
    TextView tv_type;
    @Bind(R.id.tv_selected_class)
    TextView tvSelectedClass;
    @Bind(R.id.select_class_layout)
    LinearLayout selectClassLayout;
    @Bind(R.id.flow_series)
    LinearLayout flowSeries;
    private boolean isOpen = false;
    private View rootView;
    private Context context;
    private String url, keyword;//外界的参数,商品列表的url和搜索关键字
    private boolean isafter;//是否支持售后
    private int min = -1;
    private int max = -1;
    private Boolean[] bool = new Boolean[]{false, false, false, false, false, false};//顶部6个选项的值
    private String sale, tryClothe, noReason, cash, presell, made;
    private String productSellType;//商品类型
    private Integer subscribe = 0;//是否试衣
    private Boolean hasPromotion = false;//是否促销
    private boolean isSpot = false, isCustom = false, isPresell = false;
    private LetterPop pop;//所有品牌的pop
    private ScreenNewTopAdapter screenNewTopAdapter;//一级分类的网格适配器
    private ArrayList<ScreenNewBean.DataBean.FilterBean.TopsBean> topList;//一级分类数据源
    private ArrayList<ScreenNewBean.DataBean.FilterBean.DesignersBean> brandList;//品牌数据源
    private ArrayList<String> priceList = new ArrayList<>();//价格数据源
    private List<ScreenNewBean.DataBean.FilterBean.DesignersBean> designerSelectedList = new ArrayList<>();//已选择的品牌
    private List<ScreenNewBean.DataBean.FilterBean.CategorysBean> classSelectedList = new ArrayList<>();//已选择的分类
    private TransparentPop mPop;
    private int width;
    private int flag = 0;//店铺筛选flag为1,普通为0
    private ScreenNewBrandAdapter screenNewBrandAdapter;//品牌网格的适配器
    private OnConFirmClickListner onConFirmClickListner;//普通筛选的确认回调
    private OnBrandConfirmClickListener onBrandConfirmClickListener;//店铺筛选的确认回调
    private ScreenNewPriceAdapter screenNewPriceAdapter;//价格网格的适配器
    private List<SerieBean.DataBean.SeriesBean.ListBean> seriesList = new ArrayList<>();//店铺系列的数据源
    private List<SerieBean.DataBean.SeriesBean.ListBean> selectedSeries = new ArrayList<>();//已选择的系列
    private ClassifyScreenPop classifyScreenPop;//选取三级分类的pop
    private HashMap<SerieBean.DataBean.SeriesBean.ListBean, RelativeLayout> serieMap = new HashMap<>();
    private long designerId, promotionId;//品牌id以及活动id,活动id可能为商品活动和订单活动
    private boolean isOrderPromotion;//是否为订单活动

    public interface OnConFirmClickListner {
        void callBack(Boolean hasPromotion, Integer subscribe, Boolean after, String productSellType, String categoryId, String designerIds, Integer minPrice, Integer maxPrice);
    }

    public interface OnBrandConfirmClickListener {
        void callBack(Boolean hasPromotion, Integer subscribe, Boolean after, String productSellType, String categoryId, String seriesId, Integer minPrice, Integer maxPrice);
    }

    public void setOnConFirmClickListner(OnConFirmClickListner onConFirmClickListner) {
        this.onConFirmClickListner = onConFirmClickListner;
    }

    public void setOnBrandConFirmClickListner(OnBrandConfirmClickListener onBrandConFirmClickListner) {
        this.onBrandConfirmClickListener = onBrandConFirmClickListner;
    }


    public ScreenNewPop(Context context, int flag, String url, String keyWord, long designerId, long promotionId, boolean isOrderPromotion) {
        this.context = context;
        this.flag = flag;
        this.url = url;
        this.keyword = keyWord;
        this.designerId = designerId;
        this.promotionId = promotionId;
        this.isOrderPromotion = isOrderPromotion;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_screen_layout, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        init();
    }

    private void init() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (flag == 0) {
            tvType.setText("设计师品牌");
            ivOpen.setVisibility(View.VISIBLE);
        } else {
            tvType.setText("系列");
            ivOpen.setVisibility(View.VISIBLE);
        }
        topList = new ArrayList<>();
        brandList = new ArrayList<>();
        screenNewBrandAdapter=new ScreenNewBrandAdapter(context,brandList);
        String[] array = context.getResources().getStringArray(R.array.label_filtration_prices);
        for (int i = 0; i < array.length; i++) {
            priceList.add(array[i]);
        }
        screenNewPriceAdapter = new ScreenNewPriceAdapter(context, priceList);
        screenNewPriceAdapter.setOnSingleItemClickListener(new ScreenNewPriceAdapter.OnSingleItemClickListener() {
            @Override
            public void onSingleItemClick(int position) {
                switch (position) {
                    case -1:
                        min = -1;
                        max = -1;
                        break;
                    case 0:
                        min = 0;
                        max = 500;
                        break;
                    case 1:
                        min = 500;
                        max = 1000;
                        break;
                    case 2:
                        min = 1000;
                        max = 1500;
                        break;
                    case 3:
                        min = 1500;
                        max = 2000;
                        break;
                    case 4:
                        min = 2000;
                        max = 3000;
                        break;
                    case 5:
                        min = 3000;
                        max = -1;
                        break;
                }
            }
        });
        screenPriceGrid.setAdapter(screenNewPriceAdapter);
        screenNewTopAdapter = new ScreenNewTopAdapter(topList, context);
        screenNewTopAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                topList.get(position).setSelected(true);
                classifyScreenPop = new ClassifyScreenPop(context, topList);

//                ArrayList<ScreenNewBean.DataBean.FilterBean.TopsBean> copyList = new ArrayList<>(topList);
//                //Collections.copy(copyList, topList);
//                Log.e("tag", "before:" + topList.get(0).getName() + topList.get(0).getList().get(0).isSelected());
//                copyList.get(0).setName("火车王");
//                copyList.get(0).getList().get(0).setSelected(true);
//                Log.e("tag", "after:" + topList.get(0).getName() + topList.get(0).getList().get(0).isSelected());
//                Log.e("tag", "after的copyList:" + copyList.get(0).getName() + copyList.get(0).getList().get(0).isSelected());

                classifyScreenPop.setSelectedList(classSelectedList);
                classifyScreenPop.setOnSelectListener(new ClassifyScreenPop.OnSelectOverListener() {
                    @Override
                    public void onSelected(List<ScreenNewBean.DataBean.FilterBean.CategorysBean> selectList) {
                        if (selectList != null && selectList.size() > 0) {
                            for (int i = 0; i < selectList.size(); i++) {
                                for (int j = 0; j < topList.size(); j++) {
                                    if (Integer.valueOf(selectList.get(i).getParentId()) == topList.get(j).getSourceId()) {
                                        topList.get(j).setSelected(true);
                                        for (int k = 0; k < topList.get(j).getList().size(); k++) {
                                            if (topList.get(j).getList().get(k).getSourceId() == selectList.get(i).getSourceId()) {
                                                topList.get(j).getList().get(k).setSelected(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        classSelectedList.clear();
                        classSelectedList.addAll(selectList);
                        setClassSelectedLayout();
                    }
                });
                classifyScreenPop.show(((Activity) context).getWindow().getDecorView());
            }
        });
        screenTypeGrid.setAdapter(screenNewTopAdapter);
        loadData();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = Math.round(((point.x * 4 / 5) - 60 * dm.density) / 3);
        mPop = new TransparentPop(context, -1, point.y - Util.getStatusHeight(context), true, this);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
    }

    private void resetSeriesLinearLayout(int size) {
        flowSeries.removeAllViews();
        if (seriesList.size() == 0) {
            tvType.setVisibility(View.GONE);
            return;
        } else {
            tvType.setVisibility(View.VISIBLE);
        }
        if (seriesList.size() > 3) {
            ivOpen.setVisibility(View.VISIBLE);
        } else {
            ivOpen.setVisibility(View.GONE);
        }
          for (int i = 0; i <size ; i++) {
              SerieBean.DataBean.SeriesBean.ListBean serieBean = seriesList.get(i);
              RelativeLayout relativeLayout = (RelativeLayout) View.inflate(context, R.layout.layout_flow_seris, null);
              TextView text = (TextView) relativeLayout.findViewById(R.id.tv_text);
              ImageView ivCheck = (ImageView) relativeLayout.findViewById(R.id.iv_check);
              text.setText(serieBean.getName());
              relativeLayout.setTag(serieBean);
              relativeLayout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      onSeriesViewClick((SerieBean.DataBean.SeriesBean.ListBean) view.getTag(), (RelativeLayout) view);
                  }
              });
              serieMap.put(serieBean, relativeLayout);
              if(serieBean.isSelected()){
                  ivCheck.setImageResource(R.mipmap.icon_shopcart_bselected);
                  text.setTextColor(context.getResources().getColor(R.color.color_black85));
              }
              flowSeries.addView(relativeLayout);
                      }
    }

    private void onSeriesViewClick(SerieBean.DataBean.SeriesBean.ListBean serieBean, RelativeLayout relativeLayout) {
        if (serieBean == null) {
            return;
        }
        if (selectedSeries != null && selectedSeries.size() > 0) {
            SerieBean.DataBean.SeriesBean.ListBean serieBean2 = selectedSeries.get(0);
            RelativeLayout relativeLayout2 = serieMap.get(serieBean2);
            TextView text2 = (TextView) relativeLayout2.findViewById(R.id.tv_text);
            ImageView ivCheck = (ImageView) relativeLayout2.findViewById(R.id.iv_check);
            ivCheck.setImageResource(R.mipmap.icon_shopcart_unbselected);
            serieBean2.setSelected(false);
            text2.setTextColor(context.getResources().getColor(R.color.color_black38));
            if (selectedSeries.contains(serieBean2)) {
                selectedSeries.remove(serieBean2);
            }
            if (relativeLayout2 == relativeLayout) {
                return;
            }
        }
        TextView text = (TextView) relativeLayout.findViewById(R.id.tv_text);
        ImageView ivCheck = (ImageView) relativeLayout.findViewById(R.id.iv_check);
        ivCheck.setImageResource(R.mipmap.icon_shopcart_unaselected);
        if (serieBean.isSelected()) {
            serieBean.setSelected(false);
            ivCheck.setImageResource(R.mipmap.icon_shopcart_unbselected);
            text.setTextColor(context.getResources().getColor(R.color.color_black38));
        } else {
            if (selectedSeries.size() > 0) {
                Util.showToast(context, "只能选择一个系列哦~");
            } else {
                selectedSeries.add(serieBean);
                text.setTextColor(context.getResources().getColor(R.color.color_black85));
                serieBean.setSelected(true);
                ivCheck.setImageResource(R.mipmap.icon_shopcart_bselected);
            }
        }
    }

    private void loadSeries() {
        SeriesApi api = new SeriesApi();
        api.setPageSize(40);
        api.setBrandId(designerId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SerieBean>() {
            @Override
            public void onResponse(SerieBean response) {
                seriesList.clear();
                flowSeries.setVisibility(View.VISIBLE);
                if (response.getData().getSeries().getList() != null) {
                    seriesList.addAll(response.getData().getSeries().getList());
                }
                resetSeriesLinearLayout(seriesList.size()>3?3:seriesList.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void show(View parent) {
        mPop.show(parent, 0, Util.getStatusHeight(context), true);
    }

    private void loadData() {
        ScreenApi api = new ScreenApi();
        api.setInterPath(SCREEN_PRODUCT_URL);
        if (designerId > 0) {
            api.setDesignerId(designerId);
        }
        if (promotionId > 0) {
            if (isOrderPromotion) {
                api.setOrderpromotionID(promotionId);
            } else {
                api.setPromotionId(promotionId);
            }
        }
        if (!Util.isEmpty(url)) {
            api.setPreParam(url.substring(url.indexOf("?") + 1, url.length()));
        }
        if (!Util.isEmpty(keyword)) {
            api.setKeyword(keyword);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ScreenNewBean>() {
            @Override
            public void onResponse(final ScreenNewBean response) {
                topList.clear();
                brandList.clear();
                ScreenNewBean.DataBean.FilterBean.ServiceBean serviceBean = response.getData().getFilter().getService();
                if (serviceBean.isIsAfter()) {//七天包退
                    llService.setVisibility(View.VISIBLE);
                    rlAfter.setVisibility(View.VISIBLE);
                }
                if (serviceBean.isCustom()) {//定制
                    llType.setVisibility(View.VISIBLE);
                    rlCustom.setVisibility(View.VISIBLE);
                }
                if (serviceBean.isPresell()) {//预售
                    llType.setVisibility(View.VISIBLE);
                    rlPresell.setVisibility(View.VISIBLE);
                }
                if (serviceBean.isIsPromotion()) {//促销
                    llService.setVisibility(View.VISIBLE);
                    rlSale.setVisibility(View.VISIBLE);
                }
                if (serviceBean.isSpot()) {//现货
                    llType.setVisibility(View.VISIBLE);
                    rlSpot.setVisibility(View.VISIBLE);
                }
                if (serviceBean.isIsSubscribe()) {//试衣
                    llService.setVisibility(View.VISIBLE);
                    rlTry.setVisibility(View.VISIBLE);
                }
                if (response.getData().getFilter().getTops() != null && response.getData().getFilter().getTops().size() > 0) {
                    List<ScreenNewBean.DataBean.FilterBean.TopsBean> topsBeanList = response.getData().getFilter().getTops();
                    for (int i = 0; i < topsBeanList.size(); i++) {
                        ScreenNewBean.DataBean.FilterBean.TopsBean top = new ScreenNewBean.DataBean.FilterBean.TopsBean();
                        top.setName(topsBeanList.get(i).getName());
                        top.setParentId(topsBeanList.get(i).getParentId());
                        top.setType(topsBeanList.get(i).getType());
                        top.setSourceId(topsBeanList.get(i).getSourceId());
                        List<ScreenNewBean.DataBean.FilterBean.CategorysBean> childList = new ArrayList<>();
                        List<ScreenNewBean.DataBean.FilterBean.CategorysBean> categorysBeanList = response.getData().getFilter().getCategorys();
                        if (categorysBeanList != null && categorysBeanList.size() > 0) {
                            for (int j = 0; j < categorysBeanList.size(); j++) {
                                ScreenNewBean.DataBean.FilterBean.CategorysBean categorysBean = categorysBeanList.get(j);
                                if (Integer.valueOf(categorysBean.getParentId()) == top.getSourceId()) {
                                    childList.add(categorysBean);
                                }
                            }
                        }
                        top.setList(childList);
                        topList.add(top);
                    }
                }
                if (response.getData().getFilter().getDesigners() != null && response.getData().getFilter().getDesigners().size() > 0) {
                    brandList.addAll(response.getData().getFilter().getDesigners());
                }
                if (flag == 0) {
                    flowSeries.setVisibility(View.GONE);
                    screenNewBrandAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void itemClick(View v, int position) {
                            boolean isExist = false;
                            ScreenNewBean.DataBean.FilterBean.DesignersBean bean = response.getData().getFilter().getDesigners().get(position);
                            if (isExist) {
                                //designerSelectedList.remove(removePosition);
                            } else {
                                if (designerSelectedList.size() < 3) {
                                    designerSelectedList.add(bean);
                                } else {
                                    Util.showToast(context, R.string.msg_three_most);
                                }
                            }
                            setSelectedLayout();
                        }
                    });
                    screenNewBrandAdapter.setOnItemDeleteListener(new ScreenNewBrandAdapter.OnItemDeleteListener() {
                        @Override
                        public void OnItemDelete(View v, int position) {
                            if (designerSelectedList != null && designerSelectedList.size() > 0) {
                                designerSelectedList.remove(response.getData().getFilter().getDesigners().get(position));
                                screenNewBrandAdapter.setSelectedCount(designerSelectedList.size());
                                setSelectedLayout();
                            }
                        }
                    });
                    screenDesignerGrid.setAdapter(screenNewBrandAdapter);
                } else {
                    screenDesignerGrid.setVisibility(View.GONE);
                    loadSeries();
                }
                screenNewBrandAdapter.notifyDataSetChanged();
                screenNewTopAdapter.notifyDataSetChanged();
                scrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    private void setClassSelectedLayout() {
        selectClassLayout.removeAllViews();
        if (classSelectedList == null || classSelectedList.isEmpty()) {
            tvSelectedClass.setVisibility(View.GONE);
            selectClassLayout.setVisibility(View.GONE);
        } else {
            tvSelectedClass.setVisibility(View.VISIBLE);
            selectClassLayout.setVisibility(View.VISIBLE);
        }
        int i = 0;
        for (final ScreenNewBean.DataBean.FilterBean.CategorysBean typedesignerBean : classSelectedList) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_designer_selected, selectLayout, false);
            View headView = view.findViewById(R.id.head_view);
            TextView tvDesignerSelected = (TextView) view.findViewById(R.id.tv_designer_selected);
            ImageView imgDelete = (ImageView) view.findViewById(R.id.img_delete);
            tvDesignerSelected.getLayoutParams().width = width;
            tvDesignerSelected.setText(typedesignerBean.getName());
            imgDelete.setOnClickListener(new DeleteClassClickListener(typedesignerBean));
            if (i == 0) {
                headView.setVisibility(View.GONE);
            } else {
                headView.setVisibility(View.VISIBLE);
            }
            i++;
            selectClassLayout.addView(view);
        }
    }

    private void setSelectedLayout() {
        selectLayout.removeAllViews();
        if (designerSelectedList == null || designerSelectedList.isEmpty()) {
            tvSelectedLabel.setVisibility(View.GONE);
            selectLayout.setVisibility(View.GONE);
        } else {
            tvSelectedLabel.setVisibility(View.VISIBLE);
            selectLayout.setVisibility(View.VISIBLE);
        }
        int i = 0;
        for (final ScreenNewBean.DataBean.FilterBean.DesignersBean typedesignerBean : designerSelectedList) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_designer_selected, selectLayout, false);
            View headView = view.findViewById(R.id.head_view);
            TextView tvDesignerSelected = (TextView) view.findViewById(R.id.tv_designer_selected);
            ImageView imgDelete = (ImageView) view.findViewById(R.id.img_delete);
            tvDesignerSelected.getLayoutParams().width = width;
            tvDesignerSelected.setText(typedesignerBean.getName());
            imgDelete.setOnClickListener(new DeleteClickListener(typedesignerBean));
            if (i == 0) {
                headView.setVisibility(View.GONE);
            } else {
                headView.setVisibility(View.VISIBLE);
            }
            i++;
            selectLayout.addView(view);
        }
    }

    private String selected(int i, TextView textView, ImageView imageView, String str) {
        if (bool[i]) {
            bool[i] = false;
            switch (i) {
                case 0:
                    hasPromotion = false;
                    break;
                case 1:
                    subscribe = 0;
                    break;
                case 2:
                    isafter = false;
                    break;
                case 3:
                    isSpot = false;
                    break;
                case 4:
                    isPresell = false;
                    break;
                case 5:
                    isCustom = false;
                    break;
            }
            textView.setBackgroundResource(R.drawable.border_unselect);
            textView.setTextColor(context.getResources().getColor(R.color.color_black60));
            imageView.setVisibility(View.GONE);
            str = "";
        } else {
            bool[i] = true;
            switch (i) {
                case 0:
                    hasPromotion = true;
                    break;
                case 1:
                    subscribe = 1;
                    break;
                case 2:
                    isafter = true;
                    break;
                case 3:
                    isSpot = true;
                    break;
                case 4:
                    isPresell = true;
                    break;
                case 5:
                    isCustom = true;
                    break;
            }
            textView.setBackgroundResource(R.drawable.border_selected);
            textView.setTextColor(context.getResources().getColor(R.color.color_black87));
            imageView.setVisibility(View.VISIBLE);
            str = textView.getText().toString().trim();
        }
        return str;
    }

    @OnClick({R.id.tv_reset, R.id.tv_confirm, R.id.tv_sale, R.id.tv_try, R.id.tv_no_reason_change, R.id.tv_cash, R.id.tv_pre_sell, R.id.tv_made, R.id.rl_first_select, R.id.iv_open, R.id.tv_look_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sale:
                sale = selected(0, tvSale, ivSaleMark, sale);
                break;
            case R.id.tv_try:
                tryClothe = selected(1, tvTry, ivTryMark, tryClothe);
                break;
            case R.id.tv_no_reason_change:
                noReason = selected(2, tvNoReasonChange, ivNoReasonChangeMark, noReason);
                break;
            case R.id.tv_cash:
                cash = selected(3, tvCash, ivCashMark, cash);
                productSellType = "spot";
                break;
            case R.id.tv_pre_sell:
                presell = selected(4, tvPreSell, ivPreSellMark, presell);
                productSellType = "PRESELL";
                break;
            case R.id.tv_made:
                made = selected(5, tvMade, ivMadeMark, made);
                productSellType = "CUSTOM";
                break;
            case R.id.iv_open:
                if (isOpen) {
                    isOpen = false;
                    ivOpen.clearAnimation();
                    ivOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.arrow_up));
                    if(flag==0){
                        screenNewBrandAdapter.cutLessList();
                    }else{
                        resetSeriesLinearLayout(seriesList.size()>3?3:seriesList.size());
                    }
                    tvLookAll.setVisibility(View.GONE);
                } else {
                    isOpen = true;
                    ivOpen.clearAnimation();
                    ivOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.arrow_down));
                    if(flag==0){
                        screenNewBrandAdapter.addMoreList();
                    }else{
                        resetSeriesLinearLayout(seriesList.size());
                    }

                    tvLookAll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_look_all:
                pop = new LetterPop(context);
                pop.show(((Activity) context).getWindow().getDecorView());
                pop.setData(designerSelectedList);
                pop.setOnSelectListener(new LetterPop.OnSelectListener() {
                    @Override
                    public void onSelected(ArrayList<SectionBean> list) {
                        if (list == null || list.isEmpty()) {
                            return;
                        }
                        designerSelectedList.clear();
                        for (SectionBean sectionBean : list) {
                            ScreenNewBean.DataBean.FilterBean.DesignersBean bean = new ScreenNewBean.DataBean.FilterBean.DesignersBean();
                            bean.setSourceId((int) sectionBean.getId());
                            bean.setName(sectionBean.getName());
                            designerSelectedList.add(bean);
                        }
                        screenNewBrandAdapter.setSelectedCount(designerSelectedList.size());
                        setSelectedLayout();
                    }
                });
                break;
            case R.id.tv_reset:
                min = -1;
                max = -1;
                subscribe = 0;
                int size = classSelectedList.size();
                for (int i = 0; i < size; i++) {
                    setDataRight(classSelectedList.get(i));
                }
                classSelectedList.clear();
                screenNewPriceAdapter.setPriceSelectedPosition(-1);
                screenNewPriceAdapter.notifyDataSetChanged();
                topList.clear();
                screenNewTopAdapter.notifyDataSetChanged();
                if (flag == 0) {
                    if(screenNewBrandAdapter!=null){
                        screenNewBrandAdapter.clearAllSelect();
                        screenNewBrandAdapter.notifyDataSetChanged();
                        designerSelectedList.clear();
                    }
                    setSelectedLayout();
                } else {
                    selectedSeries.clear();
                    serieMap.clear();
                    loadSeries();
                }
                setClassSelectedLayout();
                bool = new Boolean[]{true, true, true, true, true, true};
                selected(0, tvSale, ivSaleMark, sale);
                selected(1, tvTry, ivTryMark, tryClothe);
                selected(2, tvNoReasonChange, ivNoReasonChangeMark, noReason);
                selected(3, tvCash, ivCashMark, cash);
                selected(4, tvPreSell, ivPreSellMark, presell);
                selected(5, tvMade, ivMadeMark, made);
                loadData();
                break;
            case R.id.tv_confirm:
                //分类的结果
                StringBuilder sb3 = new StringBuilder();
                if (classSelectedList.size() > 0) {
                    for (int i = 0; i < classSelectedList.size(); i++) {
                        sb3.append(classSelectedList.get(i).getSourceId() + ",");
                    }
                }
                String classSelect = sb3.toString();
                if (!Util.isEmpty(classSelect)) {
                    classSelect = classSelect.substring(0, sb3.length() - 1);
                }
                //顶部商品类型结果
                StringBuilder sb2 = new StringBuilder();
                if (isCustom) {
                    sb2.append("custom,");
                }
                if (isPresell) {
                    sb2.append("presell,");
                }
                if (isSpot) {
                    sb2.append("spot,");
                }
                productSellType = sb2.toString();
                if (!Util.isEmpty(productSellType)) {
                    productSellType = productSellType.substring(0, sb2.length() - 1);
                }

                if (flag == 0) {//普通筛选
                    //品牌的结果
                    StringBuilder sb = new StringBuilder();
                    List<String> selectList = screenNewBrandAdapter.getSelectList();
                    if (selectList.size() == 0) {
                        for (int i = 0; i < designerSelectedList.size(); i++) {
                            sb.append(designerSelectedList.get(i).getSourceId() + ",");
                        }
                    } else {
                        for (int i = 0; i < selectList.size(); i++) {
                            sb.append(selectList.get(i) + ",");
                        }
                    }
                    String resultStr = sb.toString();
                    if (!Util.isEmpty(resultStr)) {
                        resultStr = resultStr.substring(0, sb.length() - 1);
                    }

                    if (onConFirmClickListner != null) {
                        onConFirmClickListner.callBack(hasPromotion, subscribe, isafter, productSellType, classSelect, resultStr, min, max);
                    }
                } else {//店铺筛选

                    //店铺系列的结果
                    StringBuilder sb4 = new StringBuilder();
                    if (selectedSeries.size() > 0) {
                        for (int i = 0; i < selectedSeries.size(); i++) {
                            sb4.append(selectedSeries.get(i).getId() + ",");
                        }
                    }
                    String selectedSeriesRe = sb4.toString();
                    if (!Util.isEmpty(selectedSeriesRe)) {
                        selectedSeriesRe = selectedSeriesRe.substring(0, sb4.length() - 1);
                    }

                    if (onBrandConfirmClickListener != null) {
                        onBrandConfirmClickListener.callBack(hasPromotion, subscribe, isafter, productSellType, classSelect, selectedSeriesRe, min, max);
                    }
                }
                mPop.dismiss(true);
                break;
        }
    }

    private class DeleteClickListener implements View.OnClickListener {
        private ScreenNewBean.DataBean.FilterBean.DesignersBean typedesignerBean;

        private DeleteClickListener(ScreenNewBean.DataBean.FilterBean.DesignersBean typedesignerBean) {
            this.typedesignerBean = typedesignerBean;
        }

        @Override
        public void onClick(View v) {
            if (designerSelectedList != null && designerSelectedList.size() > 0) {
                designerSelectedList.remove(typedesignerBean);
                if (screenNewBrandAdapter != null) {
                    screenNewBrandAdapter.deleteSelectedItem(typedesignerBean);
                    screenNewBrandAdapter.notifyDataSetChanged();
                }
                setSelectedLayout();
            }
        }
    }

    private class DeleteClassClickListener implements View.OnClickListener {
        private ScreenNewBean.DataBean.FilterBean.CategorysBean typedesignerBean;

        private DeleteClassClickListener(ScreenNewBean.DataBean.FilterBean.CategorysBean typedesignerBean) {
            this.typedesignerBean = typedesignerBean;
        }

        @Override
        public void onClick(View v) {
            deleteSelected(typedesignerBean);
        }
    }

    private void deleteSelected(ScreenNewBean.DataBean.FilterBean.CategorysBean typedesignerBean) {
        if (classSelectedList != null && classSelectedList.size() > 0) {
            classSelectedList.remove(typedesignerBean);
            setDataRight(typedesignerBean);
            setClassSelectedLayout();
        }
    }

    private void setDataRight(ScreenNewBean.DataBean.FilterBean.CategorysBean typedesignerBean) {
        for (int j = 0; j < topList.size(); j++) {
            if (Integer.valueOf(typedesignerBean.getParentId()) == topList.get(j).getSourceId()) {
                for (int k = 0; k < topList.get(j).getList().size(); k++) {
                    if (topList.get(j).getList().get(k).getSourceId() == typedesignerBean.getSourceId()) {
                        topList.get(j).getList().get(k).setSelected(false);
                    }
                }
            }
        }
    }


    @Override
    public void invokeView(LinearLayout v) {
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }
}
