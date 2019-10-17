package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FindSimilarActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.TopRecommendActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.RecommendListBean;
import com.d2cmall.buyer.holder.HotSaleHolder;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/1 11:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainChoiceAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private RequestManager requestManager;
    private List<RecommendListBean.DataBean.RecommendsBean.ListBean> datas;
    private int itemWidth;
    private String name;
    private String tag;
    private int longClickPosition = -1;
    private AnimationSet set;
    private Animation alphaAnimation, scale;
    private TextView findSimilarityTipTextView=null;
    private boolean hasLongClick =false;
    public MainChoiceAdapter(Context context, List<RecommendListBean.DataBean.RecommendsBean.ListBean> datas, int itemWidth, String name, String tag) {
        this.context = context;
        this.datas = datas;
        this.itemWidth = itemWidth;
        this.name = name;
        this.tag = tag;
    }

    public MainChoiceAdapter(Context context,RequestManager requestManager, List<RecommendListBean.DataBean.RecommendsBean.ListBean> datas, int itemWidth, String name, String tag) {
        this.context = context;
        this.requestManager=requestManager;
        this.datas = datas;
        this.itemWidth = itemWidth;
        this.name = name;
        this.tag = tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 50) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_hot_sale,parent, false);
            return new HotSaleHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false);
            return new ProductHolder(view, itemWidth);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==50){
            HotSaleHolder hotSaleHolder= (HotSaleHolder) holder;
            hotSaleHolder.itemView.getLayoutParams().width=itemWidth;
            int imgWidth=(itemWidth-3*ScreenUtil.dip2px(8))/2;
            int imgHeight=3*imgWidth/2;
            hotSaleHolder.firstIv.getLayoutParams().width=imgWidth;
            hotSaleHolder.firstIv.getLayoutParams().height=imgHeight;
            hotSaleHolder.secondIv.getLayoutParams().width=imgWidth;
            hotSaleHolder.secondIv.getLayoutParams().height=imgHeight;
            hotSaleHolder.threeIv.getLayoutParams().width=imgWidth;
            hotSaleHolder.threeIv.getLayoutParams().height=imgHeight;
            hotSaleHolder.fourIv.getLayoutParams().width=imgWidth;
            hotSaleHolder.fourIv.getLayoutParams().height=imgHeight;

            hotSaleHolder.hotName.setText("—— "+datas.get(position).getCategoryName()+" ——");
            int size=datas.get(position).getProducts().size();
            if (size>0){
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(datas.get(position).getProducts().get(0).getImg())).into(hotSaleHolder.firstIv);
                }else {
                    UniversalImageLoader.displayImage(context,datas.get(position).getProducts().get(0).getImg(),hotSaleHolder.firstIv);
                }
            }
            if (size>1){
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(datas.get(position).getProducts().get(1).getImg())).into(hotSaleHolder.secondIv);
                }else {
                    UniversalImageLoader.displayImage(context,datas.get(position).getProducts().get(1).getImg(),hotSaleHolder.secondIv);
                }
            }
            if (size>2){
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(datas.get(position).getProducts().get(2).getImg())).into(hotSaleHolder.threeIv);
                }else {
                    UniversalImageLoader.displayImage(context,datas.get(position).getProducts().get(2).getImg(),hotSaleHolder.threeIv);
                }
            }
            if (size>3){
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(datas.get(position).getProducts().get(3).getImg())).into(hotSaleHolder.fourIv);
                }else {
                    UniversalImageLoader.displayImage(context,datas.get(position).getProducts().get(3).getImg(),hotSaleHolder.fourIv);
                }
            }
            hotSaleHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, TopRecommendActivity.class);
                    intent.putExtra("categoryName",datas.get(position).getCategoryName());
                    intent.putExtra("currentCategoryId",datas.get(position).getCategoryId());
                    context.startActivity(intent);
                }
            });
        }else {
            ProductHolder productHolder= (ProductHolder) holder;
            final RecommendListBean.DataBean.RecommendsBean.ListBean listBean = datas.get(position);
            int height = (int) (itemWidth* ((float) 1558 / 1000));
            if (requestManager!=null){
                requestManager.load(Util.getD2cProductPicUrl(listBean.getImg()==null?listBean.getProductImageCover():listBean.getImg(),itemWidth,height)).placeholder(R.mipmap.ic_logo_empty5).error(R.mipmap.ic_logo_empty5).into(productHolder.productImage);
            }else {
                UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(listBean.getImg()==null?listBean.getProductImageCover():listBean.getImg(),itemWidth,height),productHolder.productImage, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
            }
            if (!Util.isEmpty(listBean.getProductTradeType())) {
                if (listBean.getProductTradeType().equals("CROSS")) {//跨境商品
                    productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
                } else {
                    productHolder.tvGlobalTag.setVisibility(View.GONE);
                }
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
            productHolder.productName.setText(listBean.getName());
            String stringId = listBean.getPromotionId();
            int promotionId=0;
            try {
                if (!Util.isEmpty(stringId)){
                    promotionId=Integer.valueOf(stringId);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //显示价格
            if (promotionId > 0) {
                if (listBean.getSalePrice() > listBean.getMinPrice()) {
                    productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice(),listBean.getSalePrice()));
                } else {
                    productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice()));
                }
            } else {
                if (listBean.getOriginalPrice() > listBean.getMinPrice()) {
                    productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice(),listBean.getOriginalPrice()));
                } else {
                    productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice()));
                }
            }
            productHolder.tagLl.removeAllViews();
            if (listBean.getFlashPromotionId() != null && listBean.getFlashPromotionId() > 0) {
                addTag(productHolder.tagLl,1,"限时购");
            } else if (!Util.isEmpty(listBean.getPromotionTypeName())) {
                addTag(productHolder.tagLl,1,listBean.getPromotionTypeName());
            }else if (!Util.isEmpty(listBean.getOrderPromotionTypeName())){
                addTag(productHolder.tagLl,2,listBean.getOrderPromotionTypeName());
            }else {
                productHolder.tagLl.setVisibility(View.GONE);
            }
            //这个长按提示只会出现一次,只在首页和商品列表可能会出现,所以没有写进布局
            if(position==0 ){
                if(findSimilarityTipTextView==null && !D2CApplication.mSharePref.getSharePrefBoolean("findSimilarityTipMain",false) && !hasLongClick){
                    D2CApplication.mSharePref.putSharePrefBoolean("findSimilarityTipMain",true);
                    findSimilarityTipTextView = new TextView(context);
                    findSimilarityTipTextView.setGravity(Gravity.CENTER);
                    findSimilarityTipTextView.setTextSize(14);
                    findSimilarityTipTextView.setTextColor(context.getResources().getColor(R.color.color_white));
                    findSimilarityTipTextView.setText("长按可找相似");
                    findSimilarityTipTextView.setY((itemWidth * (float) 1558 / 2000));
                    findSimilarityTipTextView.setBackgroundResource(R.mipmap.pic_home_popover02);
                    findSimilarityTipTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productHolder.rootRl.removeView(findSimilarityTipTextView);
                            findSimilarityTipTextView=null;
                        }
                    });
                    productHolder.rootRl.addView(findSimilarityTipTextView);
                }
                //长按任意item隐藏提示
                if(hasLongClick && findSimilarityTipTextView!=null){
                    productHolder.rootRl.removeView(findSimilarityTipTextView);
                    findSimilarityTipTextView=null;
                }

            }else if(findSimilarityTipTextView!=null){
                if(position>5){//避免布局复用出现多个提示
                    findSimilarityTipTextView.setVisibility(View.GONE);
                }
            }
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listBean.setLongClick(true);
                    if (longClickPosition != -1 && longClickPosition!=position) {
                        datas.get(longClickPosition).setLongClick(false);
                    }
                    hasLongClick=true;
                    longClickPosition = position;
                    notifyDataSetChanged();
                    return true;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stat(tag,"精选好货",position,datas.get(position).getId());
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", datas.get(position).getId());
                    context.startActivity(intent);
                    if (longClickPosition != -1) {
                        datas.get(longClickPosition).setLongClick(false);
                        longClickPosition = -1;
                        notifyDataSetChanged();
                    }
                }
            });
            productHolder.ivFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FindSimilarActivity.class);
                    intent.putExtra("id", listBean.getId());
                    GoodsBean.DataBean.ProductsBean.ListBean data=new GoodsBean.DataBean.ProductsBean.ListBean();
                    data.setId(listBean.getId());
                    data.setName(listBean.getName());
                    data.setImg(listBean.getImg());
                    int promotionId=0;
                    try {
                        promotionId=Integer.valueOf(listBean.getPromotionId());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    data.setPromotionId(promotionId);
                    data.setMinPrice(listBean.getMinPrice());
                    data.setSalePrice(listBean.getSalePrice());
                    data.setOriginalPrice(listBean.getOriginalPrice());
                    intent.putExtra("data", data);
                    context.startActivity(intent);
                    if (longClickPosition != -1) {
                        datas.get(longClickPosition).setLongClick(false);
                        longClickPosition = -1;
                    }
                    notifyDataSetChanged();
                }
            });
            if (listBean.isLongClick()) {
                initAnimation();
                productHolder.viewFind.getLayoutParams().height=productHolder.rootRl.getHeight();
                productHolder.viewFind.setVisibility(View.VISIBLE);
                productHolder.viewFind.startAnimation(alphaAnimation);
                productHolder.ivFind.setVisibility(View.VISIBLE);
                productHolder.ivFind.startAnimation(set);
            } else {
                productHolder.viewFind.setVisibility(View.GONE);
                productHolder.ivFind.setVisibility(View.GONE);
            }
            if (listBean.getStore() <= 0) {
                productHolder.viewNoStore.setVisibility(View.VISIBLE);
                productHolder.tvNoStore.setVisibility(View.VISIBLE);
            } else {
                productHolder.viewNoStore.setVisibility(View.GONE);
                productHolder.tvNoStore.setVisibility(View.GONE);
            }
            if (listBean.isIsSpot()) {
                if (requestManager!=null){
                    requestManager.load("https://static.d2c.cn/img/promo/icon_mark_bigspring.png").into(productHolder.yearTag);
                }else {
                    UniversalImageLoader.displayImage(context, "https://static.d2c.cn/img/promo/icon_mark_bigspring.png", productHolder.yearTag);
                }
                productHolder.yearTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.yearTag.setVisibility(View.GONE);
            }
        }
    }

    /**
     *
     * @param type 1 是商品活动 2 是订单活动
     * @return
     */
    private TextView getTagTextView(int type, String text){
        TextView textView=new TextView(context);
        textView.setPadding(ScreenUtil.dip2px(3),0,ScreenUtil.dip2px(3),0);
        textView.setGravity(Gravity.CENTER);
        if (type==1){
            textView.setBackgroundColor(context.getResources().getColor(R.color.color_black));
        }else {
            textView.setBackgroundColor(context.getResources().getColor(R.color.color_red));
        }
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        textView.setText(text);
        return textView;
    }

    /**
     * 添加商品标签
     * @param tagLl
     * @param type
     */
    private void addTag(LinearLayout tagLl, int type, String text){
        tagLl.setVisibility(View.VISIBLE);
        TextView textView=getTagTextView(type,text);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,ScreenUtil.dip2px(15));
        ll.setMargins(0,0,ScreenUtil.dip2px(4),0);
        tagLl.addView(textView,ll);
    }

    private void stat(String event, String label, int position,long id){
        Map<String,String> map=new HashMap<>();
        map.put("位置","位置"+String.valueOf(position+1));
        map.put("url","product/"+id);
        map.put("综合","位置"+String.valueOf(position+1)+"/"+"product/"+id);
        TCAgent.onEvent(context,event,label,map);
    }

    private void initAnimation(){
        if (alphaAnimation==null){
            alphaAnimation = new AlphaAnimation(0f, 1.0f);
            alphaAnimation.setDuration(350);
            scale = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scale.setDuration(350);
            set = new AnimationSet(true);
            set.addAnimation(alphaAnimation);
            set.addAnimation(scale);
            set.setDuration(350);
            set.setFillAfter(true);
            set.setInterpolator(new OvershootInterpolator());
        }
    }

    public int getLongClickPosition() {
        return longClickPosition;
    }

    public void setLongClickPosition(int longClickPosition) {
        this.longClickPosition = longClickPosition;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        StaggeredGridLayoutHelper gridLayoutHelper = new StaggeredGridLayoutHelper(2);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).getClazz()!=null){
            return 50;
        } else {
            return 40;
        }
    }
}
