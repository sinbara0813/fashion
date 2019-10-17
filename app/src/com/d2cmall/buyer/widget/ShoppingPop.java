package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.GlobalOrderBalanceActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.activity.OrderBalanceActivity;
import com.d2cmall.buyer.activity.ShowSkuColorActivity;
import com.d2cmall.buyer.api.InsertCartApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.SkuInfoApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.SkuInfoBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.flowLayout.FlowLayout;
import com.d2cmall.buyer.widget.flowLayout.TagAdapter;
import com.d2cmall.buyer.widget.flowLayout.TagFlowLayout;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 11:10
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ShoppingPop implements TransparentPop.InvokeListener {

    @Bind(R.id.product_iv)
    ImageView productIv;
    @Bind(R.id.product_price)
    TextView productPrice;
    @Bind(R.id.product_drop_price)
    TextView productDropPrice;
    @Bind(R.id.select_size)
    TextView selectSize;
    @Bind(R.id.select_color)
    TextView selectColor;
    @Bind(R.id.color_layout)
    TagFlowLayout colorLayout;
    @Bind(R.id.size_layout)
    TagFlowLayout sizeLayout;
    @Bind(R.id.minus)
    ImageView minus;
    @Bind(R.id.cart_num)
    TextView cartNum;
    @Bind(R.id.add)
    ImageView add;
    @Bind(R.id.close_iv)
    ImageView closeIv;
    @Bind(R.id.sure_btn)
    TextView sureBtn;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.num_tv)
    TextView numTv;
    @Bind(R.id.num_ll)
    LinearLayout numLl;
    @Bind(R.id.remark)
    TextView remark;
    @Bind(R.id.surplus)
    TextView surplusStore;
    @Bind(R.id.color_tv)
    TextView colorTv;
    @Bind(R.id.size_tv)
    TextView sizeTv;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.add_cart_tv)
    TextView addCartTv;
    @Bind(R.id.buy_tv)
    TextView buyTv;
    @Bind(R.id.sure_ll)
    LinearLayout sureLl;

    private Context context;
    private View shoppingView;
    private TransparentPop mPop;

    private long productId;
    private int collageId;
    private long crowItemId;
    private List<ProductDetailBean.DataBean.ProductBean.ColorsBean> colors;
    private String selectColorStr;
    private int selectColorPosition;
    private long selectColorId;
    private List<ProductDetailBean.DataBean.ProductBean.SizesBean> sizes;
    private String selectSizeStr;
    private int selectSizePosition;
    private long selectSizeId;
    private long skuId;
    private int currentNum = 1;

    private int mNum;
    private double mPrice;
    private double mDropPrice;
    private int actionType; //1加购物车 //2门店试衣 //3更改sku 4立即购买 5拼单
    private int parentId;
    public boolean isLive;
    private LiveBuyListener liveBuyListener;
    private CallBackListener backListener;
    public boolean isSecKill;
    private int limitStore;
    private boolean isGlobal;
    private boolean isCollage;
    private boolean isFlash;

    public ShoppingPop(Context context) {
        this.context = context;
        shoppingView = LayoutInflater.from(context).inflate(R.layout.layout_shopping_pop1, new LinearLayout(context), false);
        ButterKnife.bind(this, shoppingView);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(false);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        inAnimation.setInterpolator(new DecelerateInterpolator());
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        outAnimation.setInterpolator(new AccelerateInterpolator());
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();
    }

    private void initListener() {
        shoppingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法拦截事件
            }
        });
    }

    @OnClick({R.id.minus, R.id.add, R.id.sure_btn, R.id.close_iv,R.id.add_cart_tv,R.id.buy_tv,R.id.product_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.minus:
                if (("1").equals(cartNum.getText().toString())) {
                    Util.showToast(context, "不能再减哦！");
                } else {
                    changeNum(0);
                }
                break;
            case R.id.add:
                if (isSecKill) {
                    Util.showToast(context, "秒杀商品只能抢一件哦!");
                    return;
                }
                if (actionType==5) {
                    Util.showToast(context, "拼团只能买一件哦!");
                    return;
                }
                changeNum(1);
                break;
            case R.id.sure_btn:
                if (!checkSelectSku()){
                    return;
                }
                if (actionType == 1) {
                    addCart();
                } else if (actionType == 2) {
                    dismiss();
                    String url = "/o2oSubscribe/edit?skuId=" + skuId + "&quantity=1&productId=" + productId;
                    Util.urlAction(context, url);
                } else if (actionType == 3) {
                    if (backListener!=null){
                        backListener.sure(skuId);
                    }
                    dismiss();
                } else if (actionType == 4) {
                    buy();
                }else if (actionType==5){
                    packBuy();
                }
                break;
            case R.id.close_iv:
                dismiss();
                break;
            case R.id.add_cart_tv:
                if (!checkSelectSku()){
                    return;
                }
                addCart();
                break;
            case R.id.buy_tv:
                if (!checkSelectSku()){
                    return;
                }
                buy();
                break;
            case R.id.product_iv:
                Intent intent=new Intent(context, ShowSkuColorActivity.class);
                intent.putExtra("position",selectColorPosition);
                intent.putStringArrayListExtra("pics",getPic());
                intent.putStringArrayListExtra("colors",getName());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.pic_in,R.anim.activity_anim_default);
                break;
        }
    }

    public ArrayList<String> getName(){
        ArrayList<String> result=new ArrayList<>();
        int size=colors.size();
        for (int i=0;i<size;i++){
            result.add(colors.get(i).getValue());
        }
        return result;
    }

    public ArrayList<String> getPic(){
        ArrayList<String> result=new ArrayList<>();
        int size=colors.size();
        for (int i=0;i<size;i++){
            result.add(colors.get(i).getImg());
        }
        return result;
    }

    public void addCart() {
        if (Util.loginChecked((Activity) context, Constants.Login.MAIN_LOGIN)) {
            sureBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            InsertCartApi api = new InsertCartApi();
            api.setNum(Integer.valueOf(cartNum.getText().toString().trim()));
            api.setCrowdItemId(crowItemId);
            api.setSkuId(skuId);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (sureBtn == null) {
                                return;
                            }
                            progressBar.setVisibility(View.GONE);
                            sureBtn.setEnabled(true);
                            pulishThird();
                            if (isLive && liveBuyListener != null) {
                                liveBuyListener.liveAddCart(productId);
                            }
                            Util.showToast(context, "加入购物车成功");
                            getCartNum();
                            EventBus.getDefault().post(new ActionBean(Constants.ActionType.ADD_CART_SUCCESS));
                            dismiss();
                        }
                    }, 1000);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    sureBtn.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    Util.showToast(context, Util.checkErrorType(error));
                    if (error instanceof AuthFailureError) {  //以防万一代码
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        dismiss();
                    } else {
                        dismiss();
                    }
                }
            });
        }
        D2CApplication.mSharePref.putSharePrefInteger(SharePrefConstant.PARENT_ID, parentId);
    }

    public void buy() {
        if (Util.loginChecked((Activity) context, Constants.Login.MAIN_LOGIN)) {
            selectColorStr = null;
            selectSizeStr = null;
            Intent intent=null;
            if (isGlobal){
                intent = new Intent(context, GlobalOrderBalanceActivity.class);
            }else {
                intent = new Intent(context, OrderBalanceActivity.class);
            }
            if (crowItemId != 0) intent.putExtra("crowId", crowItemId);
            intent.putExtra("skuId", skuId);
            intent.putExtra("num", currentNum);
            intent.putExtra("source", OrderBalanceActivity.BUYNOW);
            context.startActivity(intent);
            selectColor.setText("请选择颜色");
            selectSize.setText("请选择尺码");
            setColors(colors);
            setSize(sizes);
            mPop.dismiss(false);
        }
        D2CApplication.mSharePref.putSharePrefInteger(SharePrefConstant.PARENT_ID, parentId);
    }

    /**
     * 拼团购买
     */
    public void packBuy(){
        if (Util.loginChecked((Activity) context, Constants.Login.MAIN_LOGIN)) {
            selectColorStr = null;
            selectSizeStr = null;
            Intent intent=null;
            if (isGlobal){
                intent = new Intent(context, GlobalOrderBalanceActivity.class);
            }else {
                intent = new Intent(context, OrderBalanceActivity.class);
            }
            if (crowItemId != 0) intent.putExtra("crowId", crowItemId);
            intent.putExtra("skuId", skuId);
            intent.putExtra("num", 1);
            intent.putExtra("packBuy",true);
            intent.putExtra("collageId",collageId);
            context.startActivity(intent);
            selectColor.setText("请选择颜色");
            selectSize.setText("请选择尺码");
            setColors(colors);
            setSize(sizes);
            mPop.dismiss(false);
        }
        D2CApplication.mSharePref.putSharePrefInteger(SharePrefConstant.PARENT_ID, parentId);
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

    private void changeNum(int type) {
        if (!checkSelectSku()){
            return;
        }
        int start = Integer.valueOf(cartNum.getText().toString());
        int end;

        if (type == 1) {
            end = start + 1;
        } else {
            end = start - 1;
        }
        if (end > mNum) {
            cartNum.setText(start + "");
            Util.showToast(context, "超出库存");
            return;
        }
        cartNum.setText(end + "");
        currentNum = end;
    }

    private void pulishThird() {
        TalkingDataAppCpa.onAddItemToShoppingCart(String.valueOf(productId), "",
                "", (int) mPrice * 100, Integer.parseInt(cartNum.getText().toString().trim()));
        ZampAppAnalytics.onCart(context, productId + "", Integer.valueOf(cartNum.getText().toString().trim()), false);
        HashMap<String, String> params = new HashMap<>();
        params.put("stock", "1");
        params.put("productId_list", productId + ""); //商品ID列表
        params.put("totalNum", cartNum.getText().toString().trim()); //商品数量
        params.put("totalPrice", mPrice + ""); //商品总金额
        ZampAppAnalytics.onRemarketingEvent(context, "ad-cart ", params);
    }

    public void isSingle(boolean is) {
        if (is){
            scrollView.getLayoutParams().height=ScreenUtil.dip2px(250);
        }else {
            scrollView.getLayoutParams().height=ScreenUtil.dip2px(340);
        }
    }

    public void isSelectStandard(boolean is,boolean hasAddCart) {
        if (is) {
            sureBtn.setVisibility(View.GONE);
            sureLl.setVisibility(View.VISIBLE);
            if (hasAddCart){
                addCartTv.setVisibility(View.VISIBLE);
            }else {
                addCartTv.setVisibility(View.GONE);
            }
        }else {
            sureBtn.setVisibility(View.VISIBLE);
            sureLl.setVisibility(View.GONE);
        }
    }

    public void show(View parent) {
        mPop.show(parent, true);
    }

    public void show(int actionType, View parent) {
        this.actionType = actionType;
        mPop.show(parent, true);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    public boolean isShow() {
        if (mPop != null) {
            return mPop.isShowing();
        }
        return false;
    }

    public void setData(ProductDetailBean.DataBean.ProductBean productBean) {
        this.productId = productBean.getId();
        this.collageId=productBean.getCollageId();
        if ("CROSS".equals(productBean.getProductTradeType())){
            isGlobal=true;
        }else {
            isGlobal=false;
        }
        isFlash=productBean.isFlash();
        if (productBean.getColors().size()==0||productBean.getSizes().size()==0){
            isSingle(true);
        }else {
            isSingle(false);
        }
        setPrice(productBean.getMinPrice());
        setColors(productBean.getColors());
        setSize(productBean.getSizes());
        setReMark(productBean.getRemark());
    }

    public void setReMark(String reMark) {
        if (!Util.isEmpty(reMark)) {
            remark.setText(reMark);
        }
    }

    public void setProductId(long id) {
        this.productId = id;
    }

    public void setColors(final List<ProductDetailBean.DataBean.ProductBean.ColorsBean> colors) {
        if (colors == null || colors.size() == 0){
            colorTv.setVisibility(View.GONE);
            colorLayout.setVisibility(View.GONE);
            selectColor.setVisibility(View.GONE);
            return;
        }
        colorTv.setText(colors.get(0).getName());
        this.colors = colors;
        int colorSize = colors.size();
        boolean isNeedPreCheck = false;
        colorLayout.setMaxSelectCount(1);
        List<String> colorlist = new ArrayList<>();
        for (int i = 0; i < colorSize; i++) {
            colorlist.add(colors.get(i).getValue());
        }
        if (colorlist.size() == 1) {
            isNeedPreCheck = true;
            selectColor(0);
        } else {
            UniversalImageLoader.displayImage(context, Util.getD2cPicUrl(colors.get(0).getImg()), productIv);  //取第一张图显示
        }
        TagAdapter<String> tagAdapter = new TagAdapter<String>(colorlist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_tag,
                        colorLayout, false);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                if ((position + 1) % 3 == 0) {
                    layoutParams.rightMargin = 0;
                } else {
                    layoutParams.rightMargin = ScreenUtil.dip2px(16);
                }
                layoutParams.width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(64)) / 3;
                tv.setText(s);
                tv.setTag(colors.get(position).getId());
                return tv;
            }
        };
        colorLayout.setAdapter(tagAdapter);
        if (isNeedPreCheck) {
            tagAdapter.setSelectedList(0);
        }
        colorLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Object[] array = selectPosSet.toArray();
                if (array.length >= 1) {
                    int position = (int) array[0];
                    TextView v = (TextView) ((ViewGroup) colorLayout.getChildAt(position)).getChildAt(0);
                    v.setTextColor(context.getResources().getColor(R.color.color_black87));
                    selectColor(position);
                    int colorLength = colorLayout.getChildCount();
                    for (int i = 0; i < colorLength; i++) {
                        if (i != position) {
                            TextView view = (TextView) ((ViewGroup) colorLayout.getChildAt(i)).getChildAt(0);
                            if (view.isEnabled()) {
                                view.setTextColor(context.getResources().getColor(R.color.color_black60));
                            }else {
                                view.setTextColor(context.getResources().getColor(R.color.color_black38));
                                view.setBackgroundResource(R.color.enable_color);
                            }
                        }
                    }
                } else {
                    selectColorId = -1;
                    selectColorPosition = -1;
                    selectColorStr = null;
                    surplusStore.setText("");

                    skuId = 0;
                    selectColor.setText("请选择"+colorTv.getText().toString());
                    selectColor.setTextColor(context.getResources().getColor(R.color.color_black60));

                    int length = sizeLayout.getChildCount();
                    for (int i = 0; i < length; i++) {
                        ((ViewGroup) sizeLayout.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
                    }
                    sizeLayout.clearEnable();

                    int colorLength = colorLayout.getChildCount();
                    for (int i = 0; i < colorLength; i++) {
                        TextView v = (TextView) ((ViewGroup) colorLayout.getChildAt(i)).getChildAt(0);
                        if (v.isEnabled()) {
                            v.setTextColor(context.getResources().getColor(R.color.color_black60));
                        }
                    }
                }
            }
        });
    }

    public void setSize(final List<ProductDetailBean.DataBean.ProductBean.SizesBean> sizes) {
        if (sizes == null || sizes.size() == 0){
            sizeTv.setVisibility(View.GONE);
            sizeLayout.setVisibility(View.GONE);
            selectSize.setVisibility(View.GONE);
            return;
        };
        sizeTv.setText(sizes.get(0).getName());
        this.sizes = sizes;
        int size = sizes.size();
        boolean isNeedPreCheck = false;
        sizeLayout.setMaxSelectCount(1);
        List<String> sizelist = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizelist.add(sizes.get(i).getValue());
        }
        if (sizelist.size() == 1) {
            isNeedPreCheck = true;
            selectSize(0);
        }
        TagAdapter<String> tagAdapter = new TagAdapter<String>(sizelist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_tag,
                        sizeLayout, false);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                if ((position + 1) % 3 == 0) {
                    layoutParams.rightMargin = 0;
                } else {
                    layoutParams.rightMargin = ScreenUtil.dip2px(16);
                }
                layoutParams.width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(64)) / 3;
                tv.setText(s);
                tv.setTag(sizes.get(position).getId());
                return tv;
            }
        };
        sizeLayout.setAdapter(tagAdapter);
        if (isNeedPreCheck) {
            tagAdapter.setSelectedList(0);
        }
        sizeLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Object[] array = selectPosSet.toArray();
                if (array.length >= 1) {
                    int position = (int) array[0];
                    TextView v = (TextView) ((ViewGroup) sizeLayout.getChildAt(position)).getChildAt(0);
                    v.setTextColor(context.getResources().getColor(R.color.color_black87));
                    selectSize(position);
                    int sizeLength = sizeLayout.getChildCount();
                    for (int i = 0; i < sizeLength; i++) {
                        if (i != position) {
                            TextView view = (TextView) ((ViewGroup) sizeLayout.getChildAt(i)).getChildAt(0);
                            if (view.isEnabled()) {
                                view.setTextColor(context.getResources().getColor(R.color.color_black60));
                            }
                        }
                    }
                } else {
                    selectSizeId = -1;
                    selectSizePosition = -1;
                    selectSizeStr = null;
                    skuId = 0;
                    surplusStore.setText("");

                    selectSize.setTextColor(context.getResources().getColor(R.color.color_black60));
                    selectSize.setText("请选择"+sizeTv.getText().toString());

                    int length = colorLayout.getChildCount();
                    for (int i = 0; i < length; i++) {
                        ((ViewGroup) colorLayout.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
                    }
                    colorLayout.clearEnable();

                    int sizeLength = sizeLayout.getChildCount();
                    for (int i = 0; i < sizeLength; i++) {
                        TextView v = (TextView) ((ViewGroup) sizeLayout.getChildAt(i)).getChildAt(0);
                        if (v.isEnabled()) {
                            v.setTextColor(context.getResources().getColor(R.color.color_black60));
                        }
                    }
                }
            }
        });
    }

    public void getSkuInfo(final long color, final long sizeid) {
        SkuInfoApi api = new SkuInfoApi();
        api.setProductId(productId);
        if (color != -1) api.setColor(color);
        if (sizeid != -1) api.setSize(sizeid);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SkuInfoBean>() {
            @Override
            public void onResponse(SkuInfoBean response) {
                if (!response.isLogin()) {
                    Util.showToast(context, "请先登录");
                }
                limitStore = response.getData().getCompareStore();
                if (response.getData().getSkuInfo().getColor() != null && response.getData().getSkuInfo().getColor().size() > 0) {
                    int size = response.getData().getSkuInfo().getColor().size();
                    StringBuilder sizeIds = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        int store;
                        if (isPromotionProduct()){
                            store=response.getData().getSkuInfo().getColor().get(i).getFlashStore();
                        }else {
                            store=response.getData().getSkuInfo().getColor().get(i).getStore();
                        }
                        if (store > 0) {
                            sizeIds.append(response.getData().getSkuInfo().getColor().get(i).getSize_id());
                            if (i != size - 1) sizeIds.append(",");
                        }
                        if (response.getData().getSkuInfo().getColor().get(i).getColor_id() == selectColorId &&
                                response.getData().getSkuInfo().getColor().get(i).getSize_id() == selectSizeId) {
                            cartNum.setText("1");
                            currentNum=1;
                            skuId = response.getData().getSkuInfo().getColor().get(i).getSkuId();

                            if (isPromotionProduct()){
                                mNum = response.getData().getSkuInfo().getColor().get(i).getFlashStore();
                                if (isCollageProduct()){
                                    mPrice = response.getData().getSkuInfo().getColor().get(i).getCollagePrice();
                                }else {
                                    mPrice = response.getData().getSkuInfo().getColor().get(i).getPrice();
                                }
                            }else {
                                mNum = response.getData().getSkuInfo().getColor().get(i).getStore();
                                mPrice = response.getData().getSkuInfo().getColor().get(i).getPrice();
                            }
                            setPrice(mPrice);
                            if (mNum <= 0) {
                                colorLayout.setEnable(selectColorPosition, true);
                                ((ViewGroup) colorLayout.getChildAt(selectColorPosition)).getChildAt(0).setEnabled(false);
                                ((TextView)((ViewGroup) colorLayout.getChildAt(selectColorPosition)).getChildAt(0)).setTextColor(context.getResources().getColor(R.color.color_black38));
                                ((ViewGroup) colorLayout.getChildAt(selectColorPosition)).getChildAt(0).setBackgroundResource(R.color.enable_color);
                                selectColorStr = null;
                                selectColorPosition = -1;
                                selectColorId = -1;
                            } else {
                                StringBuilder builder = new StringBuilder();
                                if (mNum <= limitStore) {
                                    builder.append("仅剩" + (mNum) + "件");
                                }
                                if (response.getData().getSkuInfo().getColor().get(i).getStock() > 0) {
                                    builder.append(" 急速发货");
                                }
                                surplusStore.setText(builder.toString());
                            }
                        }
                    }
                    if (sizeid == -1)
                        checkViewForColor(sizeIds.toString());
                }
                if (response.getData().getSkuInfo().getSize() != null && response.getData().getSkuInfo().getSize().size() > 0) {
                    int length = response.getData().getSkuInfo().getSize().size();
                    StringBuilder colorIds = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        int store;
                        if (isPromotionProduct()){
                            store=response.getData().getSkuInfo().getSize().get(i).getFlashStore();
                        }else {
                            store=response.getData().getSkuInfo().getSize().get(i).getStore();
                        }
                        if (store > 0) {
                            colorIds.append(response.getData().getSkuInfo().getSize().get(i).getColor_id());
                            if (i != length - 1) colorIds.append(",");
                        }
                        if (response.getData().getSkuInfo().getSize().get(i).getSize_id() == selectSizeId &&
                                response.getData().getSkuInfo().getSize().get(i).getColor_id() == selectColorId) {
                            cartNum.setText("1");
                            currentNum=1;
                            skuId = response.getData().getSkuInfo().getSize().get(i).getSkuId();

                            if (isPromotionProduct()){
                                mNum = response.getData().getSkuInfo().getSize().get(i).getFlashStore();
                                if (isCollageProduct()){
                                    mPrice = response.getData().getSkuInfo().getSize().get(i).getCollagePrice();
                                }else {
                                    mPrice = response.getData().getSkuInfo().getSize().get(i).getPrice();
                                }
                            }else {
                                mNum = response.getData().getSkuInfo().getSize().get(i).getStore();
                                mPrice = response.getData().getSkuInfo().getSize().get(i).getPrice();
                            }
                            setPrice(mPrice);
                            if (mNum <= 0) {
                                selectSizeStr = null;
                                selectSizePosition = -1;
                                selectSizeId = -1;
                            } else {
                                StringBuilder builder = new StringBuilder();
                                if (mNum <= limitStore) {
                                    builder.append("仅剩" + (mNum) + "件");
                                }
                                if (response.getData().getSkuInfo().getSize().get(i).getStock() > 0) {
                                    builder.append(" 极速发货");
                                }
                                surplusStore.setText(builder.toString());
                            }
                        }
                    }
                    if (color == -1)
                        checkViewForSize(colorIds.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /**
     * 是否取活动库存
     * @return
     */
    private boolean isPromotionProduct(){
        return actionType==5||isCollage||isFlash;
    }

    private boolean isCollageProduct(){
        return actionType==5||isCollage;
    }

    public void shortCut(){
        if (isSingleSku()&&checkSelectSku()){
            switch (actionType){
                case 1:
                    addCart();
                    break;
                case 4:
                    buy();
                    break;
                case 5:
                    packBuy();
                    break;
            }
        }
    }

    private boolean checkSelectSku(){
        if (selectColorStr == null&&colorTv.getVisibility()==View.VISIBLE) {
            Util.showToast(context, "请选择"+colorTv.getText().toString());
            return false;
        }
        if (selectSizeStr == null&&sizeTv.getVisibility()==View.VISIBLE) {
            Util.showToast(context, "请选择"+sizeTv.getText().toString());
            return false;
        }
        if (skuId == 0) {
            Util.showToast(context, "网络有点延时,请重新选择！");
            return false;
        }
        return true;
    }

    private boolean isSingleSku(){
        boolean result=false;
        if (colors==null&&sizes.size()==1){
            return true;
        }
        if (sizes==null&&colors.size()==1){
            return true;
        }
        if (sizes!=null&&sizes.size()==1&&colors!=null&&colors.size()==1){
            return true;
        }
        return result;
    }

    private void selectColor(int position) {
        selectColorStr = colors.get(position).getValue();
        selectColorPosition = position;
        selectColorId = colors.get(position).getId();
        UniversalImageLoader.displayImage(context, Util.getD2cPicUrl(colors.get(position).getImg()), productIv);
        selectColor.setTextColor(context.getResources().getColor(R.color.color_black87));
        selectColor.setText("已选:" + selectColorStr);
        getSkuInfo(selectColorId, -1);
    }

    private void checkViewForColor(String sizeIds) {
        String[] ids = sizeIds.split(",");
        int idLength = ids.length;
        int viewLength = sizeLayout.getChildCount();
        for (int i = 0; i < viewLength; i++) {
            TextView v = (TextView) ((ViewGroup) sizeLayout.getChildAt(i)).getChildAt(0);
            boolean isContain = false;
            for (int j = 0; j < idLength; j++) {
                if (Util.isEmpty(ids[j])) continue;
                if ((long) v.getTag() == Integer.valueOf(ids[j])) {
                    isContain = true;
                    break;
                }
            }
            if (isContain) {
                sizeLayout.setEnable(i, false);
                if (selectSizePosition >= 0 && selectColorPosition == i) {
                    v.setTextColor(context.getResources().getColor(R.color.color_black87));
                } else {
                    v.setTextColor(context.getResources().getColor(R.color.color_black60));
                }
                v.setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
            } else {
                sizeLayout.setEnable(i, true);
                v.setTextColor(context.getResources().getColor(R.color.color_black38));
                v.setBackgroundResource(R.color.enable_color);
            }
        }
    }

    private void selectSize(int position) {
        selectSizeStr = sizes.get(position).getValue();
        selectSizePosition = position;
        selectSizeId = sizes.get(position).getId();
        selectSize.setTextColor(context.getResources().getColor(R.color.color_black60));
        selectSize.setText("已选:" + selectSizeStr);
        getSkuInfo(-1, selectSizeId);
    }

    private void checkViewForSize(String colorIds) {
        String[] ids = colorIds.split(",");
        int idLength = ids.length;
        int viewLength = colorLayout.getChildCount();
        for (int i = 0; i < viewLength; i++) {
            TextView v = (TextView) ((ViewGroup) colorLayout.getChildAt(i)).getChildAt(0);
            boolean isContain = false;
            for (int j = 0; j < idLength; j++) {
                if (Util.isEmpty(ids[j])) continue;
                if ((long) v.getTag() == Integer.valueOf(ids[j])) {
                    isContain = true;
                    break;
                }
            }
            if (isContain) {
                colorLayout.setEnable(i, false);
                if (selectColorPosition >= 0 && selectColorPosition == i) {
                    v.setTextColor(context.getResources().getColor(R.color.color_black87));
                } else {
                    v.setTextColor(context.getResources().getColor(R.color.color_black60));
                }
                v.setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
            } else {
                colorLayout.setEnable(i, true);
                v.setTextColor(context.getResources().getColor(R.color.color_black38));
                v.setBackgroundResource(R.color.enable_color);
            }
        }
    }

    public void setPrice(double price) {
        this.mPrice = price;
        productPrice.setText(context.getString(R.string.label_price, Util.getNumberFormat(price)));
    }

    public void showNumLayout(boolean is) {
        if (!is) {
            numTv.setVisibility(View.GONE);
            numLl.setVisibility(View.GONE);
        }
    }

    public void setDismissListener(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }

    public long getSkuId() {
        return skuId;
    }

    public void setCrowItemId(long crowItemId) {
        this.crowItemId = crowItemId;
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(shoppingView);
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setCollage(boolean collage) {
        isCollage = collage;
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void setLiveBuyListener(LiveBuyListener liveBuyListener) {
        this.liveBuyListener = liveBuyListener;
    }

    public void setBackListener(CallBackListener backListener) {
        this.backListener = backListener;
    }

    public interface LiveBuyListener {
        void liveBuyBack(long id, String productName);

        void liveAddCart(long id);
    }

    public interface CallBackListener{
        void sure(long skuId);
    }

    public String getSelectColorStr() {
        return selectColorStr;
    }

    public String getSelectSizeStr() {
        return selectSizeStr;
    }
}
