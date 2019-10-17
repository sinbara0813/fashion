package com.d2cmall.buyer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.CartUpdateApi;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartListBean;
import com.d2cmall.buyer.bean.UpdateCartBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.CheckStateChangeListener;
import com.d2cmall.buyer.util.RoundBgSpan;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/1 15:50
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SliderView1 extends LinearLayout {

    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.cart_iv)
    ImageView cartIv;
    @Bind(R.id.sale_out_tag_tv)
    TextView saleOutTagTv;
    @Bind(R.id.cart_info_tv)
    TextView cartInfoTv;
    @Bind(R.id.cart_info_size_tv)
    TextView cartInfoSizeTv;
    @Bind(R.id.cart_info_price)
    TextView cartInfoPrice;
    @Bind(R.id.cart_info_org_price)
    TextView cartInfoOrgPrice;
    @Bind(R.id.minus)
    ImageView minus;
    @Bind(R.id.cart_info_num)
    TextView cartInfoNum;
    @Bind(R.id.add)
    ImageView add;
    @Bind(R.id.cart_info)
    RelativeLayout cartInfo;
    @Bind(R.id.edit_size_tv)
    TextView editSizeTv;
    @Bind(R.id.edit_size_ll)
    LinearLayout editSizeLl;
    @Bind(R.id.cart_num)
    TextView cartNum;
    @Bind(R.id.cart_info_edit)
    RelativeLayout cartInfoEdit;
    @Bind(R.id.middle_ll)
    RelativeLayout cartContentLl;
    @Bind(R.id.promotion_ll)
    RelativeLayout promotionLl;
    @Bind(R.id.promotion_name)
    TextView promotionName;
    @Bind(R.id.product_discount_tv)
    TextView productDiscountTv;
    @Bind(R.id.flash_end)
    TextView flashEnd;
    @Bind(R.id.spread)
    TextView spread;
    @Bind(R.id.bg)
    View bg;
    @Bind(R.id.line)
    View line;
    @Bind(R.id.disabled)
    LinearLayout disabled;

    private Context context;
    private CartListBean.DataBean.CartBean mEntity;
    private int currentNum;
    private int preNum;
    private boolean isEditState;
    private boolean isSaleOut;
    private CartListener cartListener;
    private boolean showBg = true;
    private int limitStore;

    public SliderView1(Context context, CartListener cartListener) {
        super(context);
        this.context = context;
        this.cartListener = cartListener;
        init();
    }

    public SliderView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        setOrientation(LinearLayout.VERTICAL);
        setClickable(true);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(-1, -2));
        LayoutInflater.from(context).inflate(R.layout.layout_shopping_cart_item, this, true);
        ButterKnife.bind(this, this);
        checkbox.setCheckColorId(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
    }

    @OnClick({R.id.cart_iv, R.id.cart_info_tv, R.id.edit_size_ll, R.id.minus, R.id.add})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cart_iv:
            case R.id.cart_info_tv:
                go2ProductDetail();
                break;
            case R.id.edit_size_ll:
                cartListener.selectSize(view);
                break;
            case R.id.minus:
                if (currentNum <= 1) {
                    Toast.makeText(context, "宝贝不能再减少了", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeNum(0);
                break;
            case R.id.add:
                changeNum(1);
                break;
        }
    }

    private void go2ProductDetail() {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("id", mEntity.getProductId());
        context.startActivity(intent);
    }

    public void setBg(boolean is) {
        showBg = is;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void setData(final CartListBean.DataBean.CartBean entity) {
        mEntity = entity;
        currentNum = mEntity.getQuantity();
        mEntity.setOldQuantity(currentNum);
        if (entity.getAvailableStore() <= 0) {
            isSaleOut = true;
        } else {
            isSaleOut = false;
        }
        Glide.with(context)
                .load(Util.getD2cProductPicUrl(mEntity.getProductImg(),ScreenUtil.dip2px(72),ScreenUtil.dip2px(108)))
                .placeholder(R.mipmap.ic_logo_empty5)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(cartIv);
        if (entity.getFlashPromotion() != null&&!isSaleOut) {
            setPromotionName("限时购");
            flashEnd.setVisibility(VISIBLE);
            long offer = entity.getFlashPromotion().getEndTimeStamp() - System.currentTimeMillis();
            long hour = offer / (60 * 60 * 1000);
            long minute = (offer / (60 * 1000)) % 60;
            flashEnd.setText(hour + "小时" + minute + "分恢复原价");
            cartContentLl.setPadding(0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16), 0);
        } else if (entity.getGoodPromotion() != null) {
            setPromotionName(entity.getGoodPromotion().getPromotionTypeName());
            flashEnd.setVisibility(GONE);
            cartContentLl.setPadding(0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
        } else {
            if (mEntity.getTaxation()==1&&"CROSS".equals(mEntity.getProductTradeType())){
                setPromotionName("包税");
            }else {
                cartInfoTv.setText(Html.fromHtml(mEntity.getProductName()));
            }
            flashEnd.setVisibility(GONE);
            cartContentLl.setPadding(0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
        }
        if (mEntity.getMinPrice() - mEntity.getOldPrice() < 0&&!isSaleOut) {
            spread.setVisibility(VISIBLE);
            spread.setText("已降" + Util.getNumberFormat(mEntity.getOldPrice() - mEntity.getMinPrice()) + "元");
            cartContentLl.setPadding(0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16), 0);
        } else {
            if (flashEnd.getVisibility() == VISIBLE) {

            } else {
                spread.setVisibility(GONE);
                cartContentLl.setPadding(0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
            }
        }
        cartInfoSizeTv.setText(mEntity.getColor()+" "+mEntity.getSize());
        editSizeLl.setTag(mEntity.getProductId() + "," + mEntity.getId());
        editSizeTv.setText(cartInfoSizeTv.getText().toString());
        //价格
        if (mEntity.getMark() == 0) {//已下架
            cartInfoPrice.setText("暂无报价");
        } else {
            cartInfoPrice.setText(context.getString(R.string.label_price,
                    Util.getNumberFormat(mEntity.getMinPrice())));
        }
        if (mEntity.getGoodPromotionId() > 0) {
            if (mEntity.getProductPrice() > mEntity.getMinPrice() && mEntity.getMark() != 0) {
                cartInfoOrgPrice.setVisibility(VISIBLE);
                cartInfoOrgPrice.setText(context.getString(R.string.label_price, Util.getNumberFormat(mEntity.getProductPrice())));
                cartInfoOrgPrice.getPaint().setAntiAlias(true);
                cartInfoOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                        .ANTI_ALIAS_FLAG);
            } else {
                cartInfoOrgPrice.setVisibility(GONE);
            }
        } else {
            if (mEntity.getOriginalPrice() > mEntity.getMinPrice() && mEntity.getMark() != 0) {
                cartInfoOrgPrice.setVisibility(VISIBLE);
                cartInfoOrgPrice.setText(context.getString(R.string.label_price, Util.getNumberFormat(mEntity.getOriginalPrice())));
                cartInfoOrgPrice.getPaint().setAntiAlias(true);
                cartInfoOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                        .ANTI_ALIAS_FLAG);
            } else {
                cartInfoOrgPrice.setVisibility(GONE);
            }
        }
        cartInfoNum.setText("x" + mEntity.getQuantity());
        cartNum.setText("" + mEntity.getQuantity());
        //售空
        if (isSaleOut) {
            saleOutTagTv.setVisibility(VISIBLE);
            if (mEntity.getMark() == 0) {//已下架
                saleOutTagTv.setText("已下架");
            } else {
                saleOutTagTv.setText("已售罄");
            }
            checkbox.setInvalid(true);
            cartInfoTv.setAlpha((float) 0.5);
            cartInfoSizeTv.setAlpha((float) 0.5);
            cartInfoPrice.setAlpha((float) 0.5);
            cartInfoOrgPrice.setAlpha((float) 0.5);
            cartInfoNum.setAlpha((float) 0.5);
            if (mEntity.isInvaledFirst()){
                disabled.setVisibility(VISIBLE);
            }else {
                disabled.setVisibility(GONE);
            }
        } else {
            disabled.setVisibility(GONE);
            checkbox.setInvalid(false);
            cartInfoTv.setAlpha((float) 1.0);
            cartInfoSizeTv.setAlpha((float) 1.0);
            cartInfoPrice.setAlpha((float) 1.0);
            cartInfoOrgPrice.setAlpha((float) 1.0);
            cartInfoNum.setAlpha((float) 1.0);
            if (mEntity.getAvailableStore() <= limitStore) {
                saleOutTagTv.setVisibility(VISIBLE);
                saleOutTagTv.setText("仅剩" + mEntity.getAvailableStore() + "件");
            }else {
                saleOutTagTv.setVisibility(GONE);
            }
        }
        //是否在编辑
        if (isEditState) {
            if (!isSaleOut) {
                cartInfoEdit.setVisibility(VISIBLE);
                cartInfo.setVisibility(GONE);
                cartInfoPrice.setVisibility(GONE);
                cartInfoOrgPrice.setVisibility(GONE);
                cartInfoNum.setVisibility(GONE);
            } else {
                cartInfoEdit.setVisibility(GONE);
                cartInfo.setVisibility(VISIBLE);
                cartInfoPrice.setVisibility(VISIBLE);
                cartInfoOrgPrice.setVisibility(VISIBLE);
                cartInfoNum.setVisibility(VISIBLE);
            }
        } else {
            cartInfoEdit.setVisibility(GONE);
            cartInfo.setVisibility(VISIBLE);
            cartInfoPrice.setVisibility(VISIBLE);
            cartInfoOrgPrice.setVisibility(cartInfoOrgPrice.getVisibility());
            cartInfoNum.setVisibility(VISIBLE);
        }
        //活动
        if (entity.getOrderPromotion() != null && entity.isShowPromotion()) {
            if (!Util.isEmpty(entity.getOrderPromotion().getSolution())) {
                String type = entity.getOrderPromotion().getPromotionType();
                if (type.equals("7") || type.equals("5")
                        || type.equals("6")) {
                    CartActivity cartActivity = (CartActivity) context;
                    int selectCount = cartActivity.pieceReductionPromotion.get(entity.getOrderPromotion().getPromotionId());
                    if (selectCount <= 0) {
                        promotionName.setText(entity.getOrderPromotion().getPromotionSulo());
                    } else {
                        String[] sl = entity.getOrderPromotion().getSolution().split(",");
                        HashMap<Integer, Double> solution = new HashMap<>();
                        for (int i = 0; i < sl.length; i++) {
                            try {
                                if (sl[i].contains("-")) {
                                    int index = sl[i].indexOf("-");
                                    solution.put(Integer.valueOf(sl[i].substring(0, index)), Double.valueOf(sl[i].substring(index + 1, sl[i].length())));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Object[] keys = solution.keySet().toArray();
                        Arrays.sort(keys);
                        StringBuilder builder = new StringBuilder();
                        if (type.equals("7")) {
                            if (selectCount < (int) keys[0]) {
                                builder.append("再购" + ((int) keys[0] - selectCount) + "件立享");
                                builder.append(solution.get(keys[0]) + "元").append("任选").append(keys[0]).append("件");
                            } else {
                                for (int i = keys.length - 1; i >= 0; i--) {
                                    if (selectCount >= (int) keys[i]) {
                                        builder.append("已满足");
                                        builder.append(solution.get(keys[i]) + "元").append("任选").append(keys[i]).append("件");
                                        break;
                                    }
                                }
                            }
                        } else if (type.equals("6")) { //满件减
                            if (selectCount < ((int) keys[0])) {
                                builder.append("再购" + ((int) keys[0] - selectCount) + "件立享");
                                builder.append("满" + keys[0] + "件").append("减").append(doubleToString(solution.get(keys[0]))).append("元");
                            } else {
                                for (int i = keys.length - 1; i >= 0; i--) {
                                    if (selectCount >= (int) keys[i]) {
                                        builder.append("已满足");
                                        builder.append("满" + keys[i] + "件").append("减").append(doubleToString(solution.get(keys[i]))).append("元");
                                        break;
                                    }
                                }
                            }
                        } else if (type.equals("5")) { //满件折
                            if (selectCount < ((int) keys[0])) {
                                builder.append("再购"
                                        + ((int) keys[0] - selectCount) + "件立享");
                                builder.append("满" + keys[0] + "件").append("打").append(Util.getNumberFormat(solution.get(keys[0]) * 10, 1)).append("折");
                            } else {
                                for (int i = keys.length - 1; i >= 0; i--) {
                                    if (selectCount >= (int) keys[i]) {
                                        builder.append("已满足");
                                        builder.append("满" + keys[i] + "件").append("打").append(Util.getNumberFormat(solution.get(keys[i]) * 10, 1)).append("折");
                                        break;
                                    }
                                }
                            }
                        }
                        promotionName.setText(builder.toString());
                    }
                } else if (type.equals("2") || type.equals("3")) {
                    CartActivity cartActivity = (CartActivity) context;
                    double selectPriceSum = cartActivity.fullReductionPromotion.get(entity.getOrderPromotion().getPromotionId());
                    if (selectPriceSum <= 0) {
                        promotionName.setText(entity.getOrderPromotion().getPromotionSulo());
                    } else {
                        String[] sl = entity.getOrderPromotion().getSolution().split(",");
                        HashMap<Integer, Integer> solution = new HashMap<>();
                        for (int i = 0; i < sl.length; i++) {
                            try {
                                if (sl[i].contains("-")) {
                                    int index = sl[i].indexOf("-");
                                    solution.put(Integer.valueOf(sl[i].substring(0, index)), Integer.valueOf(sl[i].substring(index + 1, sl[i].length())));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Object[] keys = solution.keySet().toArray();
                        Arrays.sort(keys);
                        StringBuilder builder = new StringBuilder();
                        if (type.equals("2")) { //满减
                            if (selectPriceSum < (int) keys[0]) {
                                builder.append("购满").append(keys[0]).append(",").append("可减").append(solution.get(keys[0]))
                                        .append(",").append("还差").append(Util.getNumberFormat((int) keys[0] - selectPriceSum)).append("元");
                            } else {
                                for (int i = keys.length - 1; i >= 0; i--) {
                                    if (selectPriceSum >= (int) keys[i]) {
                                        builder.append("已购满").append(keys[i]).append("已减").append(solution.get(keys[i])).append("元");
                                        break;
                                    }
                                }
                            }
                        } else if (type.equals("3")) { //满减上不封顶
                            if (selectPriceSum < (int) keys[0]) {
                                builder.append("购满").append(keys[0]).append(",").append("可减").append(solution.get(keys[0]))
                                        .append(",").append("还差").append(Util.getNumberFormat((int) keys[0] - selectPriceSum)).append("元");
                            } else {
                                int num = (int) selectPriceSum / (int) keys[0];
                                builder.append("已购满").append(num * (int) keys[0]).append(",").append("已减").append(num * solution.get(keys[0])).append("元");
                            }
                        }
                        promotionName.setText(builder.toString());
                    }
                } else {
                    promotionName.setText(entity.getOrderPromotion().getPromotionSulo());
                }
            } else {
                promotionName.setText(entity.getOrderPromotion().getPromotionSulo());
            }
            promotionLl.setVisibility(VISIBLE);
            LayoutParams ll = (LayoutParams) promotionLl.getLayoutParams();
            if (entity.isGroupFirst()) {//如果是组的第一个item要把上间距去掉
                ll.setMargins(0, 0, 0, 0);
            } else {
                ll.setMargins(0, ScreenUtil.dip2px(16), 0, 0);
            }
            productDiscountTv.setVisibility(VISIBLE);
            productDiscountTv.setText(entity.getOrderPromotion().getPromotionTypeName());
            promotionLl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(context, entity.getOrderPromotion().getPromotionUrl());
                }
            });
        } else {
            promotionLl.setVisibility(GONE);
            if (mEntity.isGroupFirst()) {
                cartContentLl.setPadding(0, 0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
            } else {
                cartContentLl.setPadding(0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
            }
        }
        if (showBg) { //最后一条
            if (isSaleOut){
                bg.setVisibility(GONE);
                line.setVisibility(VISIBLE);
                LayoutParams ll = (LayoutParams) line.getLayoutParams();
                ll.setMargins(0, 0, 0, 0);
            }else {
                bg.setVisibility(VISIBLE);
                bg.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                bg.getLayoutParams().height=ScreenUtil.dip2px(16);
                line.setVisibility(GONE);
            }
        } else {
            if (mEntity.isShowbg()){
                bg.setVisibility(VISIBLE);
                line.setVisibility(GONE);
                bg.setBackgroundColor(Color.parseColor("#FAFAFC"));
                bg.getLayoutParams().height=ScreenUtil.dip2px(8);
            }else if (mEntity.isGroupLast()){
                bg.setVisibility(VISIBLE);
                bg.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                bg.getLayoutParams().height=ScreenUtil.dip2px(16);
                line.setVisibility(GONE);
            } else {
                bg.setVisibility(GONE);
                line.setVisibility(VISIBLE);
                LayoutParams ll = (LayoutParams) line.getLayoutParams();
                ll.setMargins(ScreenUtil.dip2px(50), 0, 0, 0);
            }
        }
    }

    private String doubleToString(double result) {
        if (result % 1 == 0) {
            return (int) result + "";
        } else {
            return Util.getNumberFormat(result);
        }
    }

    private String intToStr(int num) {
        String result = null;
        switch (num) {
            case 1:
                result = "一";
                break;
            case 2:
                result = "二";
                break;
            case 3:
                result = "三";
                break;
            case 4:
                result = "四";
                break;
            case 5:
                result = "五";
                break;
            case 6:
                result = "六";
                break;
            case 7:
                result = "七";
                break;
            case 8:
                result = "八";
                break;
            case 9:
                result = "九";
                break;
        }
        return result;
    }

    private void setPromotionName(String promotionName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(promotionName).append(Html.fromHtml(mEntity.getProductName()));
        SpannableString spannableString = new SpannableString(stringBuilder.toString());
        spannableString.setSpan(new RoundBgSpan(), 0, promotionName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cartInfoTv.setText(spannableString);
    }

    public int getNum() {
        return currentNum;
    }

    public void setSelectListener(CheckStateChangeListener changeListener) {
        checkbox.setOnStatusChangedListener(changeListener);
    }

    private void changeNum(int type) {
        String numStr = cartNum.getText().toString();
        if (Util.isEmpty(numStr)) {
            cartNum.setText("1");
            return;
        }
        int start = Integer.valueOf(cartNum.getText().toString());
        preNum = start;
        int end;

        if (type == 1) {
            end = start + 1;
        } else {
            end = start - 1;
        }
        if (end > mEntity.getAvailableStore()) {
            cartNum.setText(String.valueOf(mEntity.getAvailableStore()));
            currentNum = mEntity.getAvailableStore();
            if (start != mEntity.getAvailableStore()) {
                update(mEntity.getAvailableStore());
            }
            Toast.makeText(context, "超出库存", Toast.LENGTH_SHORT).show();
            return;
        }
        cartNum.setText(end + "");
        currentNum = end;
        update(currentNum);
    }

    public void isEdit(boolean is) {
        isEditState = is;
    }

    public void setChecked(boolean is) {
        checkbox.setChecked(is);
    }

    /**
     * 更新库存
     *
     * @param num
     */
    private void update(final int num) {
        CartUpdateApi update = new CartUpdateApi();
        update.setId((int) mEntity.getId());
        update.setQuantity(num);
        update.setGoodPromotionId(mEntity.getGoodPromotionId());
        update.setInterPath(String.format(Constants.CART_UPDATE_URL, mEntity.getId()));
        D2CApplication.httpClient.loadingRequest(update, new BeanRequest.SuccessListener<UpdateCartBean>() {
            @Override
            public void onResponse(UpdateCartBean response) {
                mEntity.setQuantity(num);
                cartInfoNum.setText("x" + num);
                if (checkbox.isChecked()) {
                    calculate();
                    mEntity.setOldQuantity(num);
                }
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.CALCULTE_CART));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cartNum.setText(preNum + "");
                cartInfoNum.setText("x" + preNum);
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    private void calculate() {
        CartActivity cartActivity = (CartActivity) context;
        if (mEntity.getOrderPromotion() != null) {
            String type = mEntity.getOrderPromotion().getPromotionType();
            if (type.equals("7") || type.equals("5")
                    || type.equals("6")) {
                int num = cartActivity.pieceReductionPromotion.get(mEntity.getOrderPromotion().getPromotionId());
                num -= mEntity.getOldQuantity();
                num += mEntity.getQuantity();
                cartActivity.pieceReductionPromotion.put(mEntity.getOrderPromotion().getPromotionId(), num);
            }
            if (type.equals("2") || type.equals("3")) {
                double price = cartActivity.fullReductionPromotion.get(mEntity.getOrderPromotion().getPromotionId());
                price -= mEntity.getMinPrice() * mEntity.getOldQuantity();
                price += mEntity.getMinPrice() * mEntity.getQuantity();
                cartActivity.fullReductionPromotion.put(mEntity.getOrderPromotion().getPromotionId(), price);
            }
        }
    }

    public void setLimitStore(int limitStore) {
        this.limitStore = limitStore;
    }

    public interface CartListener {
        void selectPromotion(CartListBean.DataBean.CartBean entity, TextView promotionTv);

        void selectSize(View view);
    }
}
