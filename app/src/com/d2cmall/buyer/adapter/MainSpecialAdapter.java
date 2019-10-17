package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BigCardActivity;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.LiveAudienceActivity;
import com.d2cmall.buyer.activity.MainInformationActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.SameInterestActivity;
import com.d2cmall.buyer.activity.TopRecommendActivity;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.MainBrandBean;
import com.d2cmall.buyer.bean.MainSpecailBean2;
import com.d2cmall.buyer.holder.MainBrandHolder;
import com.d2cmall.buyer.holder.MainSpecailHolder;
import com.d2cmall.buyer.holder.MainSpecialLiveHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/11 11:24
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainSpecialAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> data1; //情报站
    private List<MainSpecailBean2.DataBean.RecentlySalesProductBean> data2; //同兴趣购买
    private List<ProductDetailBean.DataBean.RecommendProductsBean> data3; //榜单推荐
    private LiveListBean.DataBean.LivesBean.ListBean data4; //直播
    private MainBrandBean data5;

    public MainSpecialAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case 21: //请保站
                view =LayoutInflater.from(mContext).inflate(R.layout.layout_main_promotion_top,parent,false);
                return new MainSpecailHolder(view);
            case 22: //同兴趣购买
                view =LayoutInflater.from(mContext).inflate(R.layout.layout_main_promotion_top,parent,false);
                return new MainSpecailHolder(view);
            case 23: //榜单推荐
                view =LayoutInflater.from(mContext).inflate(R.layout.layout_main_promotion_top,parent,false);
                return new MainSpecailHolder(view);
            case 24: //直播
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_special_live,parent,false);
                return new MainSpecialLiveHolder(view);
            case 26: //大牌推荐
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_main_brand,parent,false);
                return new MainBrandHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case 21:
                MainSpecailHolder specailHolder1= (MainSpecailHolder) holder;
                if (data1!=null&&data1.size()>0){
                    if (specailHolder1.contentLl!=null&&specailHolder1.contentLl.getChildCount()>0){
                        return;
                        //specailHolder1.contentLl.removeAllViews();
                    }
                    specailHolder1.titleLl.getLayoutParams().height=ScreenUtil.dip2px(46);
                    specailHolder1.titleLl.setPadding(0,ScreenUtil.dip2px(6),ScreenUtil.dip2px(16),ScreenUtil.dip2px(16));
                    specailHolder1.titleLl.setVisibility(View.VISIBLE);
                    specailHolder1.titleTv.setText("情报站");
                    specailHolder1.recycleView.setVisibility(View.GONE);
                    specailHolder1.scrollView.setVisibility(View.VISIBLE);
                    int size=data1.size();
                    int itemWidth= (ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/3;
                    for (int i=0;i<size;i++){
                        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(itemWidth,-2);
                        layoutParams.setMargins(0,0,ScreenUtil.dip2px(16),0);
                        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_recommend_product_item,new LinearLayout(mContext),false);
                        final int finalI = i;
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转商品详情
                                ProductDetailBean.DataBean.RecommendProductsBean recommendProductsBean = data1.get(finalI);
                                Intent intent=new Intent(mContext, ProductDetailActivity.class);
                                Long productId = Long.valueOf(recommendProductsBean.getId());
                                intent.putExtra("id",productId);
                                mContext.startActivity(intent);
                            }
                        });
                        ImageView imageView= (ImageView) view.findViewById(R.id.image);
                        RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(itemWidth,itemWidth*1558/1000);
                        imageView.setLayoutParams(layoutParams1);
                        LinearLayout llPromotionTags = (LinearLayout) view.findViewById(R.id.ll_promotion_type);
                        llPromotionTags.setVisibility(View.GONE);
                        UniversalImageLoader.displayImage(mContext,data1.get(i).getImg(),imageView);
                        TextView designName= (TextView) view.findViewById(R.id.design_name);
                        TextView info= (TextView) view.findViewById(R.id.product_name);
                        TextView price= (TextView) view.findViewById(R.id.product_price);
                        TextView orgPrice= (TextView) view.findViewById(R.id.product_drop_price);
                        designName.setText(data1.get(i).getBrand());
                        info.setText(data1.get(i).getName());
                        //显示价格()
                        int promotionId = data1.get(i).getPromotionId();
                        ProductDetailBean.DataBean.RecommendProductsBean recommendProductsBean = data1.get(i);

                        price.setText("¥"+Util.getNumberFormat(recommendProductsBean.getPrice()));
                        if(promotionId>0) {
                            orgPrice.setVisibility(View.VISIBLE);
                            orgPrice.setText("¥"+Util.getNumberFormat(recommendProductsBean.getSalePrice()));
                            orgPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                        }else{
                            if(recommendProductsBean.getPrice()==recommendProductsBean.getOriginalPrice()) {
                                orgPrice.setVisibility(View.GONE);
                            }else{
                               orgPrice.setVisibility(View.VISIBLE);
                               orgPrice.setText("¥"+Util.getNumberFormat(recommendProductsBean.getOriginalPrice()));
                               orgPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                            }
                        }

                        if (data1.get(i).getOriginalPrice() > data1.get(i).getPrice()) {
                            orgPrice.setText(mContext.getString(R.string.label_price, Util.getNumberFormat(data1.get(i).getOriginalPrice())));
                            orgPrice.getPaint().setAntiAlias(true);
                            orgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                                    .ANTI_ALIAS_FLAG);
                        }
                        specailHolder1.contentLl.addView(view,layoutParams);
                    }
                }
                specailHolder1.titleAll.setVisibility(View.VISIBLE);
                specailHolder1.titleAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, MainInformationActivity.class));
                    }
                });
                break;
            case 22:
                MainSpecailHolder specailHolder2= (MainSpecailHolder) holder;
                if (data2!=null&&data2.size()>0){
                    specailHolder2.titleLl.setVisibility(View.VISIBLE);
                    specailHolder2.titleTv.setText("同兴趣购买");
                    specailHolder2.recycleView.setVisibility(View.VISIBLE);
                    MainInterestAdapter adapter=new MainInterestAdapter(mContext,data2);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    specailHolder2.recycleView.setLayoutManager(layoutManager);
                    specailHolder2.recycleView.setAdapter(adapter);
                }
                specailHolder2.titleAll.setVisibility(View.VISIBLE);
                specailHolder2.titleAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, SameInterestActivity.class));
                    }
                });
                break;
            case 23:
                MainSpecailHolder specailHolder3= (MainSpecailHolder) holder;
                if (data3!=null&&data3.size()>0){
                    specailHolder3.titleLl.setVisibility(View.VISIBLE);
                    specailHolder3.titleTv.setText("榜单推荐");
                    specailHolder3.recycleView.setVisibility(View.VISIBLE);
                    MainHotSaleAdapter adapter2=new MainHotSaleAdapter(mContext,data3);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    specailHolder3.recycleView.setLayoutManager(layoutManager);
                    specailHolder3.recycleView.setAdapter(adapter2);
                }

                specailHolder3.titleAll.setVisibility(View.VISIBLE);
                specailHolder3.titleAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, TopRecommendActivity.class));
                    }
                });
                break;
            case 24:
                MainSpecialLiveHolder specialLiveHolder= (MainSpecialLiveHolder) holder;
                if (data4!=null){
                    specialLiveHolder.titleLl.setVisibility(View.VISIBLE);
                    specialLiveHolder.titleTv.setText("直播");
                    RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) specialLiveHolder.image.getLayoutParams();
                    rl.height=ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32);
                    UniversalImageLoader.displayImage(mContext, data4.getCover(), specialLiveHolder.image);
                    UniversalImageLoader.displayRoundImage(mContext,data4.getHeadPic(),specialLiveHolder.imgAvatar,R.mipmap.ic_default_avatar);
                    specialLiveHolder.name.setText(data4.getNickname());
                    specialLiveHolder.tagTv.setBackgroundResource(R.mipmap.icon_boardcast_mark);

                    specialLiveHolder.watchCount.setText(String.valueOf(data4.getVcount()+data4.getVfans())+"人观看");
                    specialLiveHolder.des.setText(data4.getTitle());
                    specialLiveHolder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Util.loginChecked((Activity) mContext,100)){
                                if (GuideLayout.getInstance(mContext).isAddView()) {
                                    GuideLayout.getInstance(mContext).hide();
                                } else {
                                    Intent intent=new Intent(mContext, LiveAudienceActivity.class);
                                    intent.putExtra("bean",data4);
                                    mContext.startActivity(intent);
                                }
                            }
                        }
                    });
                }
                break;
            case 26:
                MainBrandHolder mainBrandHolder= (MainBrandHolder) holder;
                if (data5!=null){
                    mainBrandHolder.titleLl.setVisibility(View.VISIBLE);
                    mainBrandHolder.titleTv.setText("大牌推荐");
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mainBrandHolder.brandRecycleView.setLayoutManager(layoutManager);
                    BrandAdapter brandAdapter=new BrandAdapter(mContext,data5.getData().getRecommendBrandPic());
                    mainBrandHolder.brandRecycleView.setAdapter(brandAdapter);
                    if (data5.getData().getUpMarketBrands()!=null&&data5.getData().getUpMarketBrands().size()>0){
                        UniversalImageLoader.displayImage(mContext,data5.getData().getUpMarketBrands().get(0).getBrand().getIntroPic(),mainBrandHolder.firstImage);
                        UniversalImageLoader.displayImage(mContext,data5.getData().getUpMarketBrands().get(0).getBrand().getHeadPic(),mainBrandHolder.firstBrandHead);
                        mainBrandHolder.firstBrandName.setText(data5.getData().getUpMarketBrands().get(0).getBrand().getName());

                        MainBrandProductAdapter mainBrandProductAdapter=new MainBrandProductAdapter(mContext,data5.getData().getUpMarketBrands().get(0).getProducts());
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                        mainBrandHolder.firstBrandRecycleView.setLayoutManager(layoutManager1);
                        mainBrandHolder.firstBrandRecycleView.setAdapter(mainBrandProductAdapter);

                        mainBrandHolder.firstImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toDesignActivity(data5.getData().getUpMarketBrands().get(0).getBrand().getId());
                            }
                        });
                        mainBrandHolder.firstBrandHead.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toDesignActivity(data5.getData().getUpMarketBrands().get(0).getBrand().getId());
                            }
                        });
                        mainBrandHolder.firstBrandName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toDesignActivity(data5.getData().getUpMarketBrands().get(0).getBrand().getId());
                            }
                        });
                        if (data5.getData().getUpMarketBrands().size()>1){
                            UniversalImageLoader.displayImage(mContext,data5.getData().getUpMarketBrands().get(1).getBrand().getIntroPic(),mainBrandHolder.secondImage);
                            UniversalImageLoader.displayImage(mContext,data5.getData().getUpMarketBrands().get(1).getBrand().getHeadPic(),mainBrandHolder.secondBrandHead);
                            mainBrandHolder.secondBrandName.setText(data5.getData().getUpMarketBrands().get(1).getBrand().getName());

                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(mContext);
                            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                            MainBrandProductAdapter mainBrandProductAdapter1=new MainBrandProductAdapter(mContext,data5.getData().getUpMarketBrands().get(1).getProducts());
                            mainBrandHolder.secondBrandRecycleView.setLayoutManager(layoutManager2);
                            mainBrandHolder.secondBrandRecycleView.setAdapter(mainBrandProductAdapter1);

                            mainBrandHolder.secondImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toDesignActivity(data5.getData().getUpMarketBrands().get(1).getBrand().getId());
                                }
                            });
                            mainBrandHolder.secondBrandHead.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toDesignActivity(data5.getData().getUpMarketBrands().get(1).getBrand().getId());
                                }
                            });
                            mainBrandHolder.secondBrandName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toDesignActivity(data5.getData().getUpMarketBrands().get(1).getBrand().getId());
                                }
                            });
                        }else {
                            mainBrandHolder.secondBrandHead.setVisibility(View.GONE);
                            mainBrandHolder.secondBrandName.setVisibility(View.GONE);
                            mainBrandHolder.secondImage.setVisibility(View.GONE);
                            mainBrandHolder.secondBrandRecycleView.setVisibility(View.GONE);
                        }
                    }
                }
                mainBrandHolder.titleAll.setVisibility(View.VISIBLE);
                mainBrandHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, BigCardActivity.class));
                    }
                });
                break;
        }
    }

    private void toDesignActivity(int id){
        Intent intent=new Intent(mContext, BrandDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        int count=0;
        if (data1!=null){
            count++;
        }
        if (data2!=null){
            count++;
        }
        if (data3!=null){
            count++;
        }
        if (data4!=null){
            count++;
        }
        if (data5!=null){
            count++;
        }
        return count;
        /*if (data4==null){
            return 4;
        }else {
            return 5;
        }*/
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 21;
        }else if (position==1){
            return 22;
        }else if (position==2){
            return 23;
        }else if (position==3&&data4!=null){
            return 24;
        }else if (position==3&&data4==null){
          return 26;
        } else if (position==4){
            return 26;
        }
        return super.getItemViewType(position);
    }

    public void setData1(List<ProductDetailBean.DataBean.RecommendProductsBean> data1) {
        this.data1 = data1;
    }

    public void setData2(List<MainSpecailBean2.DataBean.RecentlySalesProductBean> data2) {
        this.data2 = data2;
    }

    public void setData3(List<ProductDetailBean.DataBean.RecommendProductsBean> data3) {
        this.data3 = data3;
    }

    public void setData4(LiveListBean.DataBean.LivesBean.ListBean data4) {
        this.data4 = data4;
    }

    public void setData5(MainBrandBean data5) {
        this.data5 = data5;
    }

    public boolean hasData(){
        return data1!=null;
    }
}
