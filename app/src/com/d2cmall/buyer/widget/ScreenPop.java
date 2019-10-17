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
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.MultipleGridAdapter;
import com.d2cmall.buyer.adapter.SingleGridAdapter;
import com.d2cmall.buyer.api.ScreenApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.ScreenBean;
import com.d2cmall.buyer.bean.ScreenBrandBean;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.SCREEN_PRODUCT_URL;

/**
 * Created by rookie on 2017/8/21.
 */

public class ScreenPop implements TransparentPop.InvokeListener {
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
    private Context context;
    private int width;
    private boolean isOpen = false;
    private TransparentPop mPop;
    private View rootView;
    private LetterPop pop;
    private Boolean[] bool = new Boolean[]{false, false, false, false, false, false};
    private TextView[] textViews = new TextView[]{tvSale, tvTry, tvNoReasonChange, tvCash, tvPreSell, tvMade};
    private String sale, tryClothe, noReason, cash, presell, made;
    private SingleGridAdapter singleGridAdapter, singleGridAdapter2;
    private MultipleGridAdapter multipleGridAdapter;
    private List<ScreenBrandBean.DataBean.PagerBean.ListBean> designerSelectedList = new ArrayList<>();
    private List<ScreenBean.DataBean.CategoryBean> screenFirstData = new ArrayList<>();
    private List<Object> screenSecondData = new ArrayList<>();
    private List<ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean.ChildrenBean2> screenThirdData = new ArrayList<>();
    private List<Object> firstList = new ArrayList<>();
    private List<Object> secondList = new ArrayList<>();
    private List<ScreenBrandBean.DataBean.PagerBean.ListBean> thirdList = new ArrayList<>();
    ScreenBean.DataBean.CategoryBean currentFirst;
    private int t_sourceId = -1;
    private int c_sourceId = -1;
    private int min = -1;
    private int max = -1;
    private String productSellType;
    private Integer subscribe;
    private Integer hasPromotion;
    private boolean isSpot = false, isCustom = false, isPresell = false;
    ScreenBean.DataBean.CategoryBean.ProductCategoryBean currentSecond;
    private String desiners;
    private int currentTier = 1;
    private OnConFirmClickListner onConFirmClickListner;
    private int topId = -1;
    private long seriesId;
    private int type, type2;
    private String url, keyword;
    private boolean isafter;
    private ClassifyScreenPop testPop;


    public void setOnConFirmClickListner(OnConFirmClickListner onConFirmClickListner) {
        this.onConFirmClickListner = onConFirmClickListner;
    }

    public ScreenPop(Context context, int type, int type2, String url, String keyword) {
        this.context = context;
        this.type = type;
        this.keyword = keyword;
        this.type2 = type2;
        this.url = url;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_screen_layout, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        init();
    }

    private void init() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (type == 1) {
            tvType.setText("系列");
        }
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
        singleGridAdapter = new SingleGridAdapter(context, firstList);
        singleGridAdapter.setOnSingleItemClickListener(new SingleGridAdapter.OnSingleItemClickListener() {
            @Override
            public void onSingleItemClick(int position) {
                if (firstList.get(position) instanceof ScreenBean.DataBean.CategoryBean) {
                    ScreenBean.DataBean.CategoryBean data = (ScreenBean.DataBean.CategoryBean) firstList.get(position);
                    topId = data.getTopCatagory().getId();
                    currentTier = 2;
                    currentFirst = data;
                    rlFirstSelect.setVisibility(View.VISIBLE);
                    tvSelectedFirst.setText(data.getTopCatagory().getName());
                    firstList.clear();
                    screenSecondData.clear();
                    for (int i = 0; i < data.getProductCategory().size(); i++) {
                        if (data.getProductCategory().get(i).getChildren().size() > 0) {
                            firstList.add(data.getProductCategory().get(i));
                            screenSecondData.add(data.getProductCategory().get(i));
                        }
                    }
                    singleGridAdapter.notifyDataSetChanged();
                } else if (firstList.get(position) instanceof ScreenBean.DataBean.CategoryBean.ProductCategoryBean) {
                    currentTier = 3;
                    ScreenBean.DataBean.CategoryBean.ProductCategoryBean data = (ScreenBean.DataBean.CategoryBean.ProductCategoryBean) firstList.get(position);
                    if (data.getChildren() != null && data.getChildren().size() > 0) {
                        t_sourceId = data.getChildren().get(0).getId();//默认置为3级的第一个
                    }
                    currentSecond = data;
                    rlFirstSelect.setVisibility(View.VISIBLE);
                    tvSelectedFirst.setText(data.getName());
                    firstList.clear();
                    firstList.addAll(data.getChildren());
                    singleGridAdapter.notifyDataSetChanged();
                } else if (firstList.get(position) instanceof ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean) {
                    ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean data = (ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean) firstList.get(position);
                    t_sourceId = data.getId();
                }

            }
        });
        screenTypeGrid.setAdapter(singleGridAdapter);
        String[] array = context.getResources().getStringArray(R.array.label_filtration_prices);
        for (int i = 0; i < array.length; i++) {
            secondList.add(array[i]);
        }
        singleGridAdapter2 = new SingleGridAdapter(context, secondList);
        singleGridAdapter2.setOnSingleItemClickListener(new SingleGridAdapter.OnSingleItemClickListener() {
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
        screenPriceGrid.setAdapter(singleGridAdapter2);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        loadBrandData();
    }

    private void loadBrandData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.SCREEN_BRAND_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ScreenBrandBean>() {
            @Override
            public void onResponse(final ScreenBrandBean response) {
                if (response.getData().getPager().getList() != null && response.getData().getPager().getList().size() > 0) {
                    thirdList.addAll(response.getData().getPager().getList());
                    tvType.setVisibility(View.VISIBLE);
                } else {
                    tvType.setVisibility(View.GONE);
                }
                if (thirdList.size() <= 9) {
                    ivOpen.setVisibility(View.GONE);
                    tvLookAll.setVisibility(View.VISIBLE);
                }
                multipleGridAdapter = new MultipleGridAdapter(context, thirdList);
                screenDesignerGrid.setAdapter(multipleGridAdapter);
                multipleGridAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void itemClick(View v, int position) {
                        ScreenBrandBean.DataBean.PagerBean.ListBean bean = response.getData().getPager().getList().get(position);
                        boolean isExist = false;
//                        int removePosition = 0;
//                        int i = 0;
//                        for (ScreenBrandBean.DataBean.PagerBean.ListBean bean2 : designerSelectedList) {
//                            if (bean.getId() == bean2.getId()) {
//                                isExist = true;
//                                removePosition = i;
//                                break;
//                            }
//                            i++;
//                        }
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
                multipleGridAdapter.setOnItemDeleteListener(new MultipleGridAdapter.OnItemDeleteListener() {
                    @Override
                    public void OnItemDelete(View v, int position) {
                        if (designerSelectedList != null && designerSelectedList.size() > 0) {
                            designerSelectedList.remove(response.getData().getPager().getList().get(position));
                            multipleGridAdapter.setSelectedCount(designerSelectedList.size());
                            setSelectedLayout();
                        }
                    }
                });
                multipleGridAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    private void loadData() {
        ScreenApi api = new ScreenApi();
        api.setInterPath(SCREEN_PRODUCT_URL);
        if (type2 == 0) {
            if (t_sourceId == -1 && c_sourceId == -1 && min == -1 && max == -1 && Util.isEmpty(desiners)) {
                if (!Util.isEmpty(url)) {
                    api.setPreParam(url.substring(url.indexOf("?") + 1, url.length()));
                }
            }
        } else if (type2 == 1) {
            api.setKeyword(keyword);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ScreenBean>() {
            @Override
            public void onResponse(ScreenBean response) {
                ScreenBean.DataBean.FilterBean filterBean = response.getData().getFilter();
                if (filterBean.isAfter()) {//七天包退
                    llService.setVisibility(View.VISIBLE);
                    rlAfter.setVisibility(View.VISIBLE);
                }
                if (filterBean.isCUSTOM()) {//定制
                    llType.setVisibility(View.VISIBLE);
                    rlCustom.setVisibility(View.VISIBLE);
                }
                if (filterBean.isPRESELL()) {//预售
                    llType.setVisibility(View.VISIBLE);
                    rlPresell.setVisibility(View.VISIBLE);
                }
                if (filterBean.isPromotion()) {//促销
                    llService.setVisibility(View.VISIBLE);
                    rlSale.setVisibility(View.VISIBLE);
                }
                if (filterBean.isSPOT()) {//现货
                    llType.setVisibility(View.VISIBLE);
                    rlSpot.setVisibility(View.VISIBLE);
                }
                if (filterBean.isSubscribe()) {//试衣
                    llService.setVisibility(View.VISIBLE);
                    rlTry.setVisibility(View.VISIBLE);
                }
                if (response.getData().getCategory() != null && response.getData().getCategory().size() > 0) {
                    tv_type.setVisibility(View.VISIBLE);
                    for (int i = 0; i < response.getData().getCategory().size(); i++) {
                        screenFirstData.add(response.getData().getCategory().get(i));
                        firstList.add(response.getData().getCategory().get(i));
                    }
                    singleGridAdapter.notifyDataSetChanged();
                } else {
                    tv_type.setVisibility(View.GONE);
                }
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
        for (final ScreenBrandBean.DataBean.PagerBean.ListBean typedesignerBean : designerSelectedList) {
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

    private class DeleteClickListener implements View.OnClickListener {
        private ScreenBrandBean.DataBean.PagerBean.ListBean typedesignerBean;

        private DeleteClickListener(ScreenBrandBean.DataBean.PagerBean.ListBean typedesignerBean) {
            this.typedesignerBean = typedesignerBean;
        }

        @Override
        public void onClick(View v) {
            if (designerSelectedList != null && designerSelectedList.size() > 0) {
                designerSelectedList.remove(typedesignerBean);
                if (multipleGridAdapter != null) {
                    multipleGridAdapter.deleteSelectedItem(typedesignerBean);
                    multipleGridAdapter.notifyDataSetChanged();
                }
                setSelectedLayout();
            }
        }
    }

    public void show(View parent) {
        mPop.show(parent, 0, Util.getStatusHeight(context), true);
    }


    @Override
    public void invokeView(LinearLayout v) {
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    private String selected(int i, TextView textView, ImageView imageView, String str) {
        if (bool[i]) {
            bool[i] = false;
            switch (i) {
                case 0:
                    //hasPromotion = 0;
                    break;
                case 1:
                    //subscribe = 0;
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
                    hasPromotion = 1;
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
            case R.id.rl_first_select:
                clickrlFirstSelect();
                break;
            case R.id.iv_open:
                if (isOpen) {
                    isOpen = false;
                    ivOpen.clearAnimation();
                    ivOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.arrow_up));
                    multipleGridAdapter.cutLessList();
                    tvLookAll.setVisibility(View.GONE);
                } else {
                    isOpen = true;
                    ivOpen.clearAnimation();
                    ivOpen.startAnimation(AnimationUtils.loadAnimation(context, R.anim.arrow_down));
                    multipleGridAdapter.addMoreList();
                    tvLookAll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_look_all:
//                pop = new LetterPop(context);
//                pop.show(((Activity) context).getWindow().getDecorView());
//                pop.setData(designerSelectedList);
//                pop.setOnSelectListener(new LetterPop.OnSelectListener() {
//                    @Override
//                    public void onSelected(ArrayList<SectionBean> list) {
//                        if (list == null || list.isEmpty()) {
//                            return;
//                        }
//                        designerSelectedList.clear();
//                        for (SectionBean sectionBean : list) {
//                            ScreenBrandBean.DataBean.PagerBean.ListBean bean = new ScreenBrandBean.DataBean.PagerBean.ListBean();
//                            bean.setId((int) sectionBean.getId());
//                            bean.setName(sectionBean.getName());
//                            designerSelectedList.add(bean);
//                        }
//                        multipleGridAdapter.setSelectedCount(designerSelectedList.size());
//                        setSelectedLayout();
//                    }
//                });
//                testPop = new ClassifyScreenPop(context);
//                testPop.show(((Activity) context).getWindow().getDecorView());
                break;
            case R.id.tv_reset:
                c_sourceId = -1;
                topId = -1;
                min = -1;
                max = -1;
                subscribe = 0;
                singleGridAdapter.setSelectedPosition(-1);
                singleGridAdapter.setSecondSelectedPostion(-1);
                singleGridAdapter.setThirdSelectedPosition(-1);
                firstList.clear();
                firstList.addAll(screenFirstData);
                singleGridAdapter.notifyDataSetChanged();
                singleGridAdapter2.setPriceSelectedPosition(-1);
                singleGridAdapter2.notifyDataSetChanged();
                rlFirstSelect.setVisibility(View.GONE);
                multipleGridAdapter.clearAllSelect();
                multipleGridAdapter.notifyDataSetChanged();
                designerSelectedList.clear();
                setSelectedLayout();
                bool = new Boolean[]{true, true, true, true, true, true};
                selected(0, tvSale, ivSaleMark, sale);
                selected(1, tvTry, ivTryMark, tryClothe);
                selected(2, tvNoReasonChange, ivNoReasonChangeMark, noReason);
                selected(3, tvCash, ivCashMark, cash);
                selected(4, tvPreSell, ivPreSellMark, presell);
                selected(5, tvMade, ivMadeMark, made);
                break;
            case R.id.tv_confirm:
                StringBuilder sb = new StringBuilder();
//                for (FiltrationBean.DataBean.TypedesignerBean bean : designerSelectedList) {
//                    sb.append(bean.getSourceId() + ",");
//                }
                List<String> selectList = multipleGridAdapter.getSelectList();
                if (selectList.size() == 0) {
                    for (int i = 0; i < designerSelectedList.size(); i++) {
                        sb.append(designerSelectedList.get(i).getId() + ",");
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
                StringBuilder sb2 = new StringBuilder();
                if (isCustom) {
                    sb2.append("custom,");
                }
                if (isPresell) {
                    sb2.append("presell,");
                }
                if (isSpot) {
                    sb2.append("spot,")
                    ;
                }
                productSellType = sb2.toString();
                if (!Util.isEmpty(productSellType)) {
                    productSellType = productSellType.substring(0, sb2.length() - 1);
                }
                if (onConFirmClickListner != null) {
                    onConFirmClickListner.callBack(subscribe, hasPromotion, min, max, t_sourceId, c_sourceId, productSellType, topId, resultStr, isafter);
                }
                mPop.dismiss(true);
                break;
        }
    }

    public interface OnConFirmClickListner {
        void callBack(Integer subscribe, Integer hasPromotion, int min, int max, int categoryId, int tagId, String productSellType, int topId, String desiners, Boolean isafter);
    }

    public interface OnSelectedListener {
        void onSelectedAndDismiss(int subscribe, String productSellType, int topId, int t_sourceId, int c_sourceId, int min, int max, long seriesId);
    }

    private OnSelectedListener mOnSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        mOnSelectedListener = onSelectedListener;
    }

    private void clickrlFirstSelect() {
        if (currentTier == 2) {//第二级别,换第一级别数据
            currentTier = 1;
            rlFirstSelect.setVisibility(View.GONE);
            firstList.clear();
            firstList.addAll(screenFirstData);
        } else if (currentTier == 3) {
            //第三级别，换第二级别数据
            currentTier = 2;
            rlFirstSelect.setVisibility(View.VISIBLE);
            firstList.clear();
            firstList.addAll(screenSecondData);
        }
        singleGridAdapter.notifyDataSetChanged();
    }
}
