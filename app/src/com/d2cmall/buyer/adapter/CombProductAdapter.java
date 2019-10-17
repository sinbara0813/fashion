package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.SkuInfoApi;
import com.d2cmall.buyer.bean.CombProductBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.SkuInfoBean;
import com.d2cmall.buyer.holder.CombProductPopBottomHolder;
import com.d2cmall.buyer.holder.CombProductPopItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.flowLayout.FlowLayout;
import com.d2cmall.buyer.widget.flowLayout.TagAdapter;
import com.d2cmall.buyer.widget.flowLayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Author: LWJ
 * desc:   组合商品Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private CombProductBean mCombProductBean;
    private List<ProductDetailBean.DataBean.ProductBean.SizesBean> sizes;
    private List<ProductDetailBean.DataBean.ProductBean.ColorsBean> colors;
    private long productCombId;
    private int currentNum = 1;
    private long skuId;
    private int mSkuIndex;
    public int getState() {
        return state;
    }

    private int state; //-1未选择 1 库存不足 0正常
    private double orgPrice;
    private double price;
    private List<Map<String,Long>> skuCache;
    private List<Map<Long,String>> colorCache;
    private List<Map<Long,String>> sizeCache;
    private Map<Integer,List<TextView>> colorTvs;
    private Map<Integer,List<TextView>> sizeTvs;
    private long selectColorId;
    private long selectSizeId;
    private SkuInfoApi api;
    private int mNum = -1;
    public long[] getSkuIds() {
        return skuIds;
    }
    private int selectSizePosition;
    private int selectColorPosition;
    private String selectColorStr;
    private String selectSizeStr;
    private long[] skuIds;

    public void setChangeNumListener(ChangeNumListener changeNumListener) {
        this.changeNumListener = changeNumListener;
    }

    private ChangeNumListener changeNumListener;
    public CombProductAdapter(Context context, CombProductBean mCombProductBean){
        this.context=context;
        this.mCombProductBean=mCombProductBean;
        api = new SkuInfoApi();
        api.setProductId(mCombProductBean.getData().getProductComb().getId());
        skuIds =new long[mCombProductBean.getData().getProductComb().getProducts().size()];
        skuCache = new ArrayList<>();
        colorCache = new ArrayList<>();
        sizeCache = new ArrayList<>();
        colorTvs = new HashMap<>();
        sizeTvs = new HashMap<>();
        for(int i=0;i<mCombProductBean.getData().getProductComb().getProducts().size();i++){
            skuCache.add(new HashMap<String,Long>());
            colorCache.add(new HashMap<Long,String>());
            sizeCache.add(new HashMap<Long,String>());
            colorTvs.put(i,new ArrayList());
            sizeTvs.put(i,new ArrayList());
        }

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        linearLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        if(viewType==1) {
            view= LayoutInflater.from(context).inflate(R.layout.layout_comb_pop_item,parent,false);
            return new CombProductPopItemHolder(view);
        }else{
            view= LayoutInflater.from(context).inflate(R.layout.layout_comb_pop_bottom,parent,false);
            return new CombProductPopBottomHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mSkuIndex = position;
        if(position!=mCombProductBean.getData().getProductComb().getProducts().size()) {
            CombProductPopItemHolder itemHolder = (CombProductPopItemHolder) holder;
            ProductDetailBean.DataBean.ProductBean productBean = mCombProductBean.getData().getProductComb().getProducts().get(position);
            UniversalImageLoader.displayImage(context,Util.getD2cProductPicUrl(productBean.getImg(),ScreenUtil.dip2px(66),ScreenUtil.dip2px(99)), itemHolder.ivComb);
            itemHolder.tvCombName.setText(productBean.getName());
            itemHolder.tvCombSubtitle.setText(Util.isEmpty(productBean.getSubTitle())?"":productBean.getSubTitle());
            //字体大小不一样
            String priceStr = "¥"+ Util.getNumberFormat(productBean.getMinPrice());
            int length = priceStr.length();
            SpannableString textSpan = new SpannableString(priceStr);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(16)), 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            itemHolder.tvCombPrice.setText(textSpan);
            if(productBean.getOriginalCost()>productBean.getMinPrice()) {
                itemHolder.tvCombOldPrice.setVisibility(View.VISIBLE);
                itemHolder.tvCombOldPrice.setText("¥"+ Util.getNumberFormat(productBean.getOriginalCost()));
                itemHolder.tvCombOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }else{
                itemHolder.tvCombOldPrice.setVisibility(View.GONE);
            }
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("id", mCombProductBean.getData().getProductComb().getProducts().get(position).getId())); ;
                }
            });
            setColors(productBean.getColors(),itemHolder,position);
            setSize(productBean.getSizes(),itemHolder,position);
        }else{
            final CombProductPopBottomHolder bottomHolder = (CombProductPopBottomHolder) holder;
            bottomHolder.mCartNum.setText(1+"");
            bottomHolder.mMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(bottomHolder.mCartNum.getText().toString())<=1) {
                        Util.showToast(context, "不能再减哦！");
                    } else {
                        if(hasColorAndSize() ) {
                            changeNum(0,bottomHolder);
                        }else{
                            Util.showToast(context, "请选择颜色和尺码");
                        }

                    }

                }
            });
            bottomHolder.mAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hasColorAndSize() ) {
                        changeNum(1,bottomHolder);
                    }else{
                        Util.showToast(context, "请选择颜色和尺码");
                    }

                }
            });
        }


    }



    @Override
    public int getItemCount() {
            return mCombProductBean.getData().getProductComb().getProducts()==null?0:mCombProductBean.getData().getProductComb().getProducts().size()+1;
        }

    public void setColors(final List<ProductDetailBean.DataBean.ProductBean.ColorsBean> colors, final CombProductPopItemHolder itemHolder, final int skuIndex) {
        if (colors == null || colors.size() == 0) {
            itemHolder.colorTv.setVisibility(View.GONE);
            return;
        }
        this.colors = colors;
        int colorSize = colors.size();
        boolean isNeedPreCheck = false;
        itemHolder.colorLayout.setMaxSelectCount(1);
        List<String> colorlist = new ArrayList<>();
        for (int i = 0; i < colorSize; i++) {
            colorlist.add(colors.get(i).getValue());
        }
        if (colorlist.size() == 1) {
            isNeedPreCheck = true;
//            selectCsolor(0,itemHolder,skuIndex);
        } else {
            UniversalImageLoader.displayImage(context,Util.getD2cProductPicUrl(colors.get(0).getImg(),ScreenUtil.dip2px(66),ScreenUtil.dip2px(99)), itemHolder.ivComb);  //取第一张图显示
        }
        TagAdapter<String> tagAdapter = new TagAdapter<String>(colorlist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_tag,
                        itemHolder.colorLayout, false);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                if ((position + 1) % 3 == 0) {
                    layoutParams.rightMargin = 0;
                } else {
                    layoutParams.rightMargin = ScreenUtil.dip2px(16);
                }
                layoutParams.width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(64)) / 3;
                tv.setText(s);
                tv.setTag(colors.get(position).getId());
                if (!colorTvs.get(skuIndex).contains(tv)) {
                    colorTvs.get(skuIndex).add(tv);
                }
                return tv;
            }
        };
        itemHolder.colorLayout.setAdapter(tagAdapter);
            tagAdapter.clearSelectdList();
        itemHolder.colorLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Object[] array = selectPosSet.toArray();
                if (array.length >= 1) {
                    int position = (int) array[0];
                    TextView v = (TextView) ((ViewGroup) itemHolder.colorLayout.getChildAt(position)).getChildAt(0);
                    v.setTextColor(context.getResources().getColor(R.color.color_black87));
                    mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).setHasSelectColor(true);
                    if(mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getSizes() == null || mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getSizes().size()==0){
                        mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).setHasSelectSize(true);
                    }
                    selectColor(position,itemHolder,skuIndex);
                    int colorLength=itemHolder.colorLayout.getChildCount();
                    for (int i=0;i<colorLength;i++){
                        if (i!=position){
                            TextView view = (TextView) ((ViewGroup) itemHolder.colorLayout.getChildAt(i)).getChildAt(0);
                            if (view.isEnabled()){
                                view.setTextColor(context.getResources().getColor(R.color.color_black60));
                            }
                        }
                    }
                } else {
                    mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).setHasSelectColor(false);
                    selectColorId = -1;
                    selectColorPosition=-1;
//                    combShoppingPop.setSelectColorStr(null);
                    selectColorStr = null;
                    skuId = 0;

                    int length = itemHolder.sizeLayout.getChildCount();
                    for (int i = 0; i < length; i++) {
                        ((ViewGroup) itemHolder.sizeLayout.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
                    }
                    itemHolder.sizeLayout.clearEnable();

                    int colorLength=itemHolder.colorLayout.getChildCount();
                    for (int i=0;i<colorLength;i++){
                        TextView v = (TextView) ((ViewGroup) itemHolder.colorLayout.getChildAt(i)).getChildAt(0);
                        if (v.isEnabled()){
                            v.setTextColor(context.getResources().getColor(R.color.color_black60));
                        }
                    }
                }
            }
        });
    }

    public void setSize(final List<ProductDetailBean.DataBean.ProductBean.SizesBean> sizes, final CombProductPopItemHolder itemHolder, final int skuIndex) {
        if (sizes == null || sizes.size() == 0) {
            itemHolder.sizeTv.setVisibility(View.GONE);
            return;
        }
        this.sizes = sizes;
        int size = sizes.size();
        boolean isNeedPreCheck = false;
        itemHolder.sizeLayout.setMaxSelectCount(1);
        List<String> sizelist = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizelist.add(sizes.get(i).getValue());
        }
        if (sizelist.size() == 1) {
            isNeedPreCheck = true;
//            selectSize(0,itemHolder,skuIndex);
        }
        final TagAdapter<String> tagAdapter = new TagAdapter<String>(sizelist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_tag,
                        itemHolder.sizeLayout, false);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                if ((position + 1) % 3 == 0) {
                    layoutParams.rightMargin = 0;
                } else {
                    layoutParams.rightMargin = ScreenUtil.dip2px(16);
                }
                layoutParams.width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(64)) / 3;
                tv.setText(s);
                tv.setTag(sizes.get(position).getId());
                if (!sizeTvs.get(skuIndex).contains(tv)) {
                    sizeTvs.get(skuIndex).add(tv);
                }
                return tv;
            }
        };
        itemHolder.sizeLayout.setAdapter(tagAdapter);
        tagAdapter.clearSelectdList();
        itemHolder.sizeLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Object[] array = selectPosSet.toArray();
                if (array.length >= 1) {
                    int position = (int) array[0];
                    selectSize(position,itemHolder,skuIndex);
                    mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).setHasSelectSize(true);
                    if(mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getColors() == null || mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getColors().size()==0){
                        mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).setHasSelectColor(true);
                    }
                } else {
                    selectSizeId = -1;
                    selectSizePosition =-1;
//                    combShoppingPop.setSelectSizeStr(null);
                    selectSizeStr = null;
                    skuId = 0;
                    mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).setHasSelectSize(false);

                    int length = itemHolder.colorLayout.getChildCount();
                    for (int i = 0; i < length; i++) {
                        ((ViewGroup) itemHolder.colorLayout.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
                    }
                    itemHolder.colorLayout.clearEnable();

                    int sizeLength=itemHolder.sizeLayout.getChildCount();
                    for (int i=0;i<sizeLength;i++){
                        TextView v = (TextView) ((ViewGroup) itemHolder.sizeLayout.getChildAt(i)).getChildAt(0);
                        if (v.isEnabled()){
                            v.setTextColor(context.getResources().getColor(R.color.color_black60));
                        }
                    }
                }
            }
        });
    }
    public void checkState() {
        int size = mCombProductBean.getData().getProductComb().getProducts().size();
        List<ProductDetailBean.DataBean.ProductBean> products = mCombProductBean.getData().getProductComb().getProducts();
        for (int i = 0; i < size; i++) {
//            if (products.get(i).getNum() <= -1) {
//                state = -1;
//                break;
//            }
            if(!hasColorAndSize() ) {
                state = -1;
                break;
            }
            if (products.get(i).getNum() == 0) {
                state = 1;
                break;
            }
            state = 0;
            if (mNum == -1 || products.get(i).getNum() < mNum) {
                mNum = products.get(i).getNum();
            }
//            skuIds[i] = products.get(i).get();
        }
    }
    private void selectColor(int position,final CombProductPopItemHolder itemHolder,int skuIndex) {
        selectColorId = mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getColors().get(position).getId();
        UniversalImageLoader.displayImage(context,Util.getD2cProductPicUrl(mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getColors().get(position).getImg(),ScreenUtil.dip2px(66),ScreenUtil.dip2px(99)), itemHolder.ivComb);
        getSkuInfo(selectColorId, -1,itemHolder,skuIndex);
    }

    private void selectSize(int position,final CombProductPopItemHolder itemHolder,int skuIndex) {
        selectSizeId = mCombProductBean.getData().getProductComb().getProducts().get(skuIndex).getSizes().get(position).getId();
        getSkuInfo(-1, selectSizeId,itemHolder,skuIndex);
    }

    public void getSkuInfo(final long color, final long sizeid, final CombProductPopItemHolder itemHolder, final int position) {
        if (color != -1) api.setColor(color);
        if (sizeid != -1) api.setSize(sizeid);
        api.setProductId(mCombProductBean.getData().getProductComb().getProducts().get(position).getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SkuInfoBean>() {
            @Override
            public void onResponse(SkuInfoBean response) {
                if (skuCache.get(position) == null) return;
                if (response.getData().getSkuInfo().getColor() != null && response.getData().getSkuInfo().getColor().size() > 0) {
                    int size = response.getData().getSkuInfo().getColor().size();
                    StringBuilder sizeIds = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        if (response.getData().getSkuInfo().getColor().get(i).getStore() > 0) {
                            sizeIds.append(response.getData().getSkuInfo().getColor().get(i).getSize_id());
                            if (i != size - 1) sizeIds.append(",");
                        }
                        String skuCacheKey = color + "," + response.getData().getSkuInfo().getColor().get(i).getSize_id();
                        skuCache.get(position).put(skuCacheKey, response.getData().getSkuInfo().getColor().get(i).getSkuId());
                        if (selectSizeId == response.getData().getSkuInfo().getColor().get(i).getSize_id() || mCombProductBean.getData().getProductComb().getProducts().get(position).getSizes()==null ||mCombProductBean.getData().getProductComb().getProducts().get(position).getSizes().size()==0) {//后面的放行条件是单规格的
                            mCombProductBean.getData().getProductComb().getProducts().get(position).setNum(response.getData().getSkuInfo().getColor().get(i).getStore());
                            mNum = response.getData().getSkuInfo().getColor().get(i).getStore();
                            skuId= response.getData().getSkuInfo().getColor().get(i).getSkuId();
                            skuIds[position]=skuId;
                        }
                    }
                    colorCache.get(position).put(color, sizeIds.toString());
                    if (sizeid == -1)
                        checkViewForColor(color,itemHolder,position);
                }
                if (response.getData().getSkuInfo().getSize() != null && response.getData().getSkuInfo().getSize().size() > 0) {
                    int length = response.getData().getSkuInfo().getSize().size();
                    StringBuilder colorIds = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        if (response.getData().getSkuInfo().getSize().get(i).getStore() > 0) {
                            colorIds.append(response.getData().getSkuInfo().getSize().get(i).getColor_id());
                            if (i != length - 1) colorIds.append(",");
                        }
                        String skuCacheKey = response.getData().getSkuInfo().getSize().get(i).getColor_id() + "," + sizeid;
                        skuCache.get(position).put(skuCacheKey, response.getData().getSkuInfo().getSize().get(i).getSkuId());
                        if (selectColorId == response.getData().getSkuInfo().getSize().get(i).getColor_id()|| mCombProductBean.getData().getProductComb().getProducts().get(position).getColors()==null ||mCombProductBean.getData().getProductComb().getProducts().get(position).getColors().size()==0) {//后面的放行条件是单规格的
                            mCombProductBean.getData().getProductComb().getProducts().get(position).setNum(response.getData().getSkuInfo().getSize().get(i).getStore());
                            mNum = response.getData().getSkuInfo().getSize().get(i).getStore();
                            skuId= response.getData().getSkuInfo().getSize().get(i).getSkuId();
                            skuIds[position]=skuId;
                        }
                    }
                    sizeCache.get(position).put(sizeid, colorIds.toString());
                    if (color == -1)
                        checkViewForSize(sizeid, itemHolder,position);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("han", error.toString());
            }
        });
    }

    private void checkViewForSize(long size,final CombProductPopItemHolder itemHolder,int productIndex) {
        String colorIds = sizeCache.get(productIndex).get(size);
        String[] ids = colorIds.split(",");
        int idLength = ids.length;
        int viewLength = colorCache.get(productIndex).size();
        for (int i = 0; i < viewLength; i++) {
            TextView v = colorTvs.get(productIndex).get(i);
            boolean isContain = false;
            for (int j = 0; j < idLength; j++) {
                if (Util.isEmpty(ids[j])) continue;
                if ((long) v.getTag() == Integer.valueOf(ids[j])) {
                    isContain = true;
                    break;
                }
            }
            if (isContain) {
                itemHolder.colorLayout.setEnable(i, false);
                v.setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
            } else {
                itemHolder.colorLayout.setEnable(i, true);
                v.setBackgroundResource(R.color.enable_color);
            }
        }
    }

    private void checkViewForColor(long color,final CombProductPopItemHolder itemHolder,int productIndex) {
        String sizeIds = colorCache.get(productIndex).get(color);
        String[] ids = sizeIds.split(",");
        int idLength = ids.length;
        int viewLength = sizeTvs.get(productIndex).size();
        for (int i = 0; i < viewLength; i++) {
            TextView v = sizeTvs.get(productIndex).get(i);
            boolean isContain = false;
            for (int j = 0; j < idLength; j++) {
                if (Util.isEmpty(ids[j])) continue;
                if ((long) v.getTag() == Integer.valueOf(ids[j])) {
                    isContain = true;
                    break;
                }
            }
            if (isContain) {
                itemHolder.sizeLayout.setEnable(i, false);
                v.setBackgroundResource(R.drawable.sl_stroke_black3_white_red);
            } else {
                itemHolder.sizeLayout.setEnable(i, true);
                v.setBackgroundResource(R.color.enable_color);
            }
        }
    }
    private void changeNum(int type,CombProductPopBottomHolder bottomHolder) {
        checkState();
        if (state == -1) {
            Util.showToast(context, "请选择尺码和颜色");
            return;
        } else if (state == 1) {
            Util.showToast(context, "库存不足");
            return;
        }
        String numStr = bottomHolder.mCartNum.getText().toString();
        if (Util.isEmpty(numStr)) {
            bottomHolder.mCartNum.setText("1");
            return;
        }
        int start = Integer.valueOf(bottomHolder.mCartNum.getText().toString());
        int end;
        if (type == 1) {
            end = start + 1;
        } else {
            end = start - 1;
        }
        if (end > mNum) {
            bottomHolder.mCartNum.setText(String.valueOf(mNum));
            Util.showToast(context, "超出库存");
            return;
        }
        if(changeNumListener!=null){
            changeNumListener.changeNum(end);
        }
        bottomHolder.mCartNum.setText(String.valueOf(end));
        currentNum = end;
        if (end <= 1) {
//            bottomHolder.mMinus.setImageResource(R.mipmap.icon_shopcart_minus);
            bottomHolder.mMinus.setEnabled(false);
        } else {
//            bottomHolder.mMinus.setImageResource(R.mipmap.ic_incard_minus);
            bottomHolder.mMinus.setEnabled(true);
        }
        initCursor(bottomHolder);
    }
    @Override
    public int getItemViewType(int position) {
        if(position==mCombProductBean.getData().getProductComb().getProducts().size()) {
            return 2;
        }else{
            return 1;
        }
    }

    private void initCursor(CombProductPopBottomHolder bottomHolder) {
        CharSequence charSequence = bottomHolder.mCartNum.getText();
        if (charSequence instanceof Spannable) {
            Spannable spannable = (Spannable) charSequence;
            Selection.setSelection(spannable, charSequence.length());
        }
    }
    //检测颜色和尺寸
    public boolean hasColorAndSize(){
        boolean flag=true;
        List<ProductDetailBean.DataBean.ProductBean> products = mCombProductBean.getData().getProductComb().getProducts();
        for (ProductDetailBean.DataBean.ProductBean  product: products) {
                flag=flag&&product.isHasSelectColor()&&product.isHasSelectSize();
        }
        return flag;
    }
    public int getBuyNum(){
        return currentNum;
    }
    public interface ChangeNumListener{
        void changeNum(int num);
    }

}



