package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FlashPromotionActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.StarStyleActivity;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.FlashBean;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.MainFlashHolder;
import com.d2cmall.buyer.holder.MainGridHolder;
import com.d2cmall.buyer.holder.MainPromotionHolder;
import com.d2cmall.buyer.holder.MainShowHolder;
import com.d2cmall.buyer.holder.MainShowTimeHolder;
import com.d2cmall.buyer.holder.MainType11Holder;
import com.d2cmall.buyer.holder.MainType7ViewHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.MainStar.CarouselLayoutManager;
import com.d2cmall.buyer.widget.MainStar.CarouselZoomPostLayoutListener;
import com.d2cmall.buyer.widget.MainStar.CenterScrollListener;
import com.d2cmall.buyer.widget.MainStar.DefaultChildSelectionListener;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.d2cmall.buyer.util.UniversalImageLoader.displayImage;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/22 13:46
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainManAdapter extends DelegateAdapter.Adapter{

    private Context mContext;
    private RequestManager requestManager;
    private List<MainBean.DataEntity.ContentEntity> list;
    private int currentPosition = 0;
    private int mOriginSize;
    private String tag = "";
    private Handler handler;
    private long endTime;
    private FlashBean flashBean;
    private float downX ;    //按下时 的X坐标
    private float downY ;    //按下时 的Y坐标
    private int promotionIndex6;
    private int promotionIndex5;
    private int promotionIndex7;
    private int promotionIndex8;
    private int promotionIndex12;

    public MainManAdapter(Context context,List<MainBean.DataEntity.ContentEntity> list) {
        this.mContext = context;
        this.list = list;
    }

    public MainManAdapter(Context context, RequestManager requestManager,List<MainBean.DataEntity.ContentEntity> list){
        this.mContext = context;
        this.requestManager=requestManager;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1: //广告位
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_loop_banner, parent, false);
                return new BannerHolder(view);
            case 3: //图片排列
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_grid, parent, false);
                return new MainGridHolder(view);
            case 4: //图文排列
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_grid, parent, false);
                return new MainGridHolder(view);
            case 6:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_promotion_item, parent, false);
                return new MainPromotionHolder(view);
            case 5: //图文层叠排列
            case 7:
            case 8:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_type7_item, parent, false);
                return new MainType7ViewHolder(view);
            case 11:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_type11_item, parent, false);
                return new MainType11Holder(view);
            case 12:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_type7_item, parent, false);
                return new MainType7ViewHolder(view);
            case 13:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_star, parent, false);
                return new MainShowHolder(view);
            case 14:
            case 15:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_time_item, parent, false);
                return new MainShowTimeHolder(view);
            case 16:
                view =LayoutInflater.from(mContext).inflate(R.layout.layout_main_flash,parent,false);
                return new MainFlashHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_promotion_item, parent, false);
                return new MainPromotionHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MainBean.DataEntity.ContentEntity contentBean = list.get(position);
        switch (getItemViewType(position)) {
            case 1:
                BannerHolder bannerHolder = (BannerHolder) holder;
                int size = contentBean.getSectionValues().size();
                List<String> bannerItems = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    bannerItems.add(contentBean.getSectionValues().get(i).getFrontPic());
                }
                int height = ScreenUtil.dip2px(192);
                VirtualLayoutManager.LayoutParams Ll = new VirtualLayoutManager.LayoutParams(-1, -2);
                Ll.mAspectRatio = (float) ScreenUtil.getDisplayWidth() / height;
                Ll.setMargins(contentBean.getProperties().getLeft(), contentBean.getProperties().getTop(), contentBean.getProperties().getRight(), contentBean.getProperties().getBottom());
                bannerHolder.itemView.setLayoutParams(Ll);

                bannerHolder.banner.setIndicatorType(1).setBottomPadding(8).setScaleType(true).setLoadingPic(R.mipmap.ic_logo_empty7).setRequestManager(requestManager).setSource(bannerItems).setAutoScrollEnable(true).startScroll();
                if (size==1){
                    bannerHolder.banner.pauseScroll();
                }
                bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                    @Override
                    public void onItemClick(int position) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(position).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position+1)+contentBean.getSectionValues().get(position).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                        Util.urlAction(mContext, contentBean.getSectionValues().get(position).getUrl());
                    }
                });
                break;
            case 3:
                MainGridHolder mainGridHolder = (MainGridHolder) holder;
                if (contentBean.getVisible() == 1) {
                    mainGridHolder.titleLl.setVisibility(View.VISIBLE);
                    mainGridHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    mainGridHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainGridHolder.titleAll.setVisibility(View.VISIBLE);
                    mainGridHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainGridHolder.titleAll.setVisibility(View.GONE);
                }
                GridLayout gridLayout = mainGridHolder.gridLayout;
                LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) gridLayout.getLayoutParams();
                ll.setMargins(contentBean.getProperties().getLeft(), contentBean.getProperties().getTop(), contentBean.getProperties().getRight(), contentBean.getProperties().getBottom());
                int columnNum3 = contentBean.getProperties().getNum();
                int rowNum3 = (int) Math.ceil((double) contentBean.getSectionValues().size() / columnNum3);
                int paramWidth3 = Math.round(ScreenUtil.getDisplayWidth() / columnNum3);
                int paramHeight3 = ScreenUtil.getDisplayWidth() * contentBean.getProperties().getHeight() / contentBean.getProperties().getWidth();
                int index3 = 0;
                for (int i3 = 0; i3 < rowNum3; i3++) {
                    for (int j3 = 0; j3 < columnNum3; j3++) {
                        if (index3 >= contentBean.getSectionValues().size()) {
                            break;
                        }
                        GridLayout.Spec rowSpec = GridLayout.spec(i3);
                        GridLayout.Spec columnSpec = GridLayout.spec(j3);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = paramWidth3;
                        params.height = paramHeight3;
                        final MainBean.DataEntity.ContentEntity.SectionValuesEntity sectionValuesBean = contentBean.getSectionValues().get(index3);
                        ImageView imgItemPic = new ImageView(mContext);
                        if (requestManager!=null){
                            requestManager.load(sectionValuesBean.getFrontPic()).into(imgItemPic);
                        }else {
                            displayImage(mContext, sectionValuesBean.getFrontPic(), imgItemPic);
                        }
                        final int index=index3;
                        imgItemPic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String,String> map=new HashMap<>();
                                map.put("位置","位置"+(index+1));
                                map.put(contentBean.getTitle()+"_url",sectionValuesBean.getUrl());
                                map.put(contentBean.getTitle()+"_综合",contentBean.getTitle()+"_位置"+(index+1)+sectionValuesBean.getUrl());
                                stat(tag,contentBean.getTitle(),map);
                                Util.urlAction(mContext, sectionValuesBean.getUrl());
                            }
                        });
                        index3++;
                        gridLayout.addView(imgItemPic, params);
                    }
                }
                break;
            case 4:
                MainGridHolder mainGridHolder1 = (MainGridHolder) holder;
                if (contentBean.getVisible() == 1) {
                    mainGridHolder1.titleLl.setVisibility(View.VISIBLE);
                    mainGridHolder1.titleTv.setText(contentBean.getTitle());
                } else {
                    mainGridHolder1.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainGridHolder1.titleAll.setVisibility(View.VISIBLE);
                    mainGridHolder1.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainGridHolder1.titleAll.setVisibility(View.GONE);
                }
                GridLayout gridLayout4 = mainGridHolder1.gridLayout;
                LinearLayout.LayoutParams ll4 = (LinearLayout.LayoutParams) gridLayout4.getLayoutParams();
                int columnNum4 = contentBean.getProperties().getNum();
                int rowNum4 = (int) Math.ceil((double) contentBean.getSectionValues().size() / columnNum4);
                int screenWidth = ScreenUtil.getDisplayWidth();
                //int marginLeft=(screenWidth-ScreenUtil.dip2px(56)*columnNum4-ScreenUtil.dip2px(28)*(columnNum4-1))/2;
                ll4.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16));
                int paramWidth4 = screenWidth / columnNum4;
                int index4 = 0;
                for (int i3 = 0; i3 < rowNum4; i3++) {
                    for (int j3 = 0; j3 < columnNum4; j3++) {
                        if (index4 >= contentBean.getSectionValues().size()) {
                            break;
                        }
                        GridLayout.Spec rowSpec = GridLayout.spec(i3);
                        GridLayout.Spec columnSpec = GridLayout.spec(j3);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = paramWidth4;
                        params.height = ScreenUtil.dip2px(60);
                        /*if (j3!=columnNum4-1){
                            params.rightMargin=ScreenUtil.dip2px(28);
                        }*/
                        if (i3 != rowNum4 - 1) {
                            params.bottomMargin = ScreenUtil.dip2px(16);
                        }
                        final MainBean.DataEntity.ContentEntity.SectionValuesEntity sectionValuesBean = contentBean.getSectionValues().get(index4);
                        View type3 = LayoutInflater.from(mContext).inflate(R.layout.layout_main_type4_item, new LinearLayout(mContext), false);
                        ImageView imgItemPic = (ImageView) type3.findViewById(R.id.img_type4_item_pic);
                        TextView textItem = (TextView) type3.findViewById(R.id.tv_type4_item_name);
                        UniversalImageLoader.displayImage(mContext, sectionValuesBean.getFrontPic(), imgItemPic);
                        if (!Util.isEmpty(sectionValuesBean.getShortTitle())) {
                            textItem.setText(sectionValuesBean.getShortTitle());
                        }
                        final int index=index4;
                        imgItemPic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String,String> map=new HashMap<>();
                                map.put(contentBean.getTitle()+"_位置","位置"+(index+1));
                                map.put(contentBean.getTitle()+"_url",sectionValuesBean.getUrl());
                                map.put("综合","位置"+(index+1)+sectionValuesBean.getUrl());
                                stat(tag,contentBean.getTitle(),map);
                                Util.urlAction(mContext, sectionValuesBean.getUrl());
                            }
                        });
                        index4++;
                        gridLayout4.addView(type3, params);
                    }
                }
                break;
            case 6:
                MainPromotionHolder mainPromotionHolder = (MainPromotionHolder) holder;
                VirtualLayoutManager.LayoutParams layoutParams = (VirtualLayoutManager.LayoutParams) mainPromotionHolder.itemView.getLayoutParams();
                layoutParams.setMargins(contentBean.getProperties().getLeft(), contentBean.getProperties().getTop(), contentBean.getProperties().getRight(), contentBean.getProperties().getBottom());
                LinearLayout.LayoutParams L1= (LinearLayout.LayoutParams) mainPromotionHolder.imageRl.getLayoutParams();
                L1.height=contentBean.getProperties().getHeight()*ScreenUtil.getDisplayWidth()/contentBean.getProperties().getWidth();
                if (contentBean.getVisible() == 1) {
                    promotionIndex6 = position;
                    mainPromotionHolder.titleLl.setVisibility(View.VISIBLE);
                    mainPromotionHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    mainPromotionHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainPromotionHolder.titleAll.setVisibility(View.VISIBLE);
                    mainPromotionHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainPromotionHolder.titleAll.setVisibility(View.GONE);
                }
                displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), mainPromotionHolder.ivMainImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex6+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex6+1)+contentBean.getSectionValues().get(0).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                        Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                    }
                });
                break;
            case 5:
                MainType7ViewHolder mainType5ViewHolder = (MainType7ViewHolder) holder;
                if (contentBean.getVisible() == 1) {
                    promotionIndex5 = position;
                    mainType5ViewHolder.titleLl.setVisibility(View.VISIBLE);
                    mainType5ViewHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    mainType5ViewHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainType5ViewHolder.titleAll.setVisibility(View.VISIBLE);
                    mainType5ViewHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainType5ViewHolder.titleAll.setVisibility(View.GONE);
                }
                VirtualLayoutManager.LayoutParams v5= (VirtualLayoutManager.LayoutParams) mainType5ViewHolder.itemView.getLayoutParams();
                v5.setMargins(contentBean.getProperties().getLeft(),ScreenUtil.dip2px(contentBean.getProperties().getTop()),contentBean.getProperties().getRight(),ScreenUtil.dip2px(contentBean.getProperties().getBottom()));
                mainType5ViewHolder.type7ItemPriceLayout.setVisibility(View.GONE);
                mainType5ViewHolder.imgType7ItemPic.getLayoutParams().height = ScreenUtil.getDisplayWidth() *
                        contentBean.getProperties().getHeight() / contentBean.getProperties().getWidth();
                displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), mainType5ViewHolder.imgType7ItemPic);
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getShortTitle())) {
                    mainType5ViewHolder.tvType7ItemTitle.setVisibility(View.VISIBLE);
                    mainType5ViewHolder.tvType7ItemTitle.setText(contentBean.getSectionValues().get(0).getShortTitle());
                }
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getLongTitle())) {
                    mainType5ViewHolder.tvType7ItemContent.setVisibility(View.VISIBLE);
                    mainType5ViewHolder.tvType7ItemContent.setText(contentBean.getSectionValues().get(0).getLongTitle());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex5+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex5+1)+contentBean.getSectionValues().get(0).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                        Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                    }
                });
                break;
            case 7:
                MainType7ViewHolder mainType7ViewHolder = (MainType7ViewHolder) holder;
                if (contentBean.getVisible() == 1) {
                    promotionIndex7 = position;
                    mainType7ViewHolder.titleLl.setVisibility(View.VISIBLE);
                    mainType7ViewHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    mainType7ViewHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainType7ViewHolder.titleAll.setVisibility(View.VISIBLE);
                    mainType7ViewHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainType7ViewHolder.titleAll.setVisibility(View.GONE);
                }
                VirtualLayoutManager.LayoutParams vvl= (VirtualLayoutManager.LayoutParams) mainType7ViewHolder.itemView.getLayoutParams();
                vvl.setMargins(contentBean.getProperties().getLeft(),ScreenUtil.dip2px(contentBean.getProperties().getTop()),contentBean.getProperties().getRight(),ScreenUtil.dip2px(contentBean.getProperties().getBottom()));
                mainType7ViewHolder.imgType7ItemPic.getLayoutParams().height = ScreenUtil.getDisplayWidth() *
                        contentBean.getProperties().getHeight() / contentBean.getProperties().getWidth();
                mainType7ViewHolder.type7ItemPriceLayout.setVisibility(View.VISIBLE);
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getPrice())) {
                    mainType7ViewHolder.tvType7ItemPrice.setVisibility(View.VISIBLE);
                    mainType7ViewHolder.tvType7ItemPrice.setText(String.format(mContext.getString(R.string.label_price),
                            Util.getNumberFormat(Double.parseDouble(contentBean.getSectionValues().get(0).getPrice()))));
                } else {
                    mainType7ViewHolder.tvType7ItemPrice.setVisibility(View.GONE);
                }
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getOriginalPrice())) {
                    mainType7ViewHolder.tvType7ItemOriginPrice.setVisibility(View.VISIBLE);
                    mainType7ViewHolder.tvType7ItemOriginPrice.getPaint().setAntiAlias(true);
                    mainType7ViewHolder.tvType7ItemOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    mainType7ViewHolder.tvType7ItemOriginPrice.setText(String.format(mContext.getString(R.string.label_price),
                            Util.getNumberFormat(Double.parseDouble(contentBean.getSectionValues().get(0).getOriginalPrice()))));
                } else {
                    mainType7ViewHolder.tvType7ItemOriginPrice.setVisibility(View.GONE);
                }
                displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), mainType7ViewHolder.imgType7ItemPic);
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getShortTitle())) {
                    mainType7ViewHolder.tvType7ItemTitle.setVisibility(View.VISIBLE);
                    mainType7ViewHolder.tvType7ItemTitle.setText(contentBean.getSectionValues().get(0).getShortTitle());
                }
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getLongTitle())) {
                    mainType7ViewHolder.tvType7ItemContent.setVisibility(View.VISIBLE);
                    mainType7ViewHolder.tvType7ItemContent.setText(contentBean.getSectionValues().get(0).getLongTitle());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex7+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex7+1)+contentBean.getSectionValues().get(0).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                        Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                    }
                });
                break;
            case 8:
                MainType7ViewHolder mainType8ViewHolder = (MainType7ViewHolder) holder;
                if (contentBean.getVisible() == 1) {
                    promotionIndex8 = position;
                    mainType8ViewHolder.titleLl.setVisibility(View.VISIBLE);
                    mainType8ViewHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    mainType8ViewHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainType8ViewHolder.titleAll.setVisibility(View.VISIBLE);
                    mainType8ViewHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainType8ViewHolder.titleAll.setVisibility(View.GONE);
                }
                VirtualLayoutManager.LayoutParams vvvl= (VirtualLayoutManager.LayoutParams) mainType8ViewHolder.itemView.getLayoutParams();
                vvvl.setMargins(contentBean.getProperties().getLeft(),ScreenUtil.dip2px(contentBean.getProperties().getTop()),contentBean.getProperties().getRight(),ScreenUtil.dip2px(contentBean.getProperties().getBottom()));
                mainType8ViewHolder.imgType7ItemPic.getLayoutParams().height = ScreenUtil.getDisplayWidth() *
                        contentBean.getProperties().getHeight() / contentBean.getProperties().getWidth();
                mainType8ViewHolder.type7ItemPriceLayout.setVisibility(View.GONE);
                displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), mainType8ViewHolder.imgType7ItemPic);
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getShortTitle())) {
                    mainType8ViewHolder.tvType7ItemTitle.setVisibility(View.VISIBLE);
                    mainType8ViewHolder.tvType7ItemTitle.setText(contentBean.getSectionValues().get(0).getShortTitle());
                }
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getLongTitle())) {
                    mainType8ViewHolder.tvType7ItemContent.setVisibility(View.VISIBLE);
                    mainType8ViewHolder.tvType7ItemContent.setText(contentBean.getSectionValues().get(0).getLongTitle());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex8+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex8+1)+contentBean.getSectionValues().get(0).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                        Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                    }
                });
                mainType8ViewHolder.rvType7Item.setVisibility(View.VISIBLE);
                int itemWidth = Math.round((ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(29)) * 5 / 16);
                int itemHeight = Math.round(itemWidth * 1558 / 1000);
                mainType8ViewHolder.rvType7Item.getLayoutParams().height = itemHeight;
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mainType8ViewHolder.rvType7Item.setLayoutManager(layoutManager);
                if (contentBean.getSectionValues().get(0).getList() == null || contentBean.getSectionValues().get(0).getList().size() == 0) {
                    mainType8ViewHolder.rvType7Item.setVisibility(View.GONE);
                } else {
                    Type8RecyclerAdapter horizonAdapter = new Type8RecyclerAdapter(mContext, itemWidth, itemHeight, contentBean.getSectionValues().get(0).getList());
                    mainType8ViewHolder.rvType7Item.setAdapter(horizonAdapter);
                    horizonAdapter.setOnItemClickListener(new Type8RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Object item) {
                            //这儿固定跳转商品详情页面
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex8+1));
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex8+1)+contentBean.getSectionValues().get(0).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            long id = ((MainBean.DataEntity.ContentEntity.SectionValuesEntity.SectionValuesListEntity) item).getId();
                            Intent intent = new Intent(mContext, ProductDetailActivity.class);
                            intent.putExtra("id", id);
                            mContext.startActivity(intent);
                        }
                    });
                }
                break;
            case 11:
                MainType11Holder type11Holder = (MainType11Holder) holder;
                if (contentBean.getSectionValues().size() >0) {
                    VirtualLayoutManager.LayoutParams v11= (VirtualLayoutManager.LayoutParams) type11Holder.itemView.getLayoutParams();
                    v11.setMargins(contentBean.getProperties().getLeft(),ScreenUtil.dip2px(contentBean.getProperties().getTop()),contentBean.getProperties().getRight(),ScreenUtil.dip2px(contentBean.getProperties().getBottom()));
                    type11Holder.topIv.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams vl = (LinearLayout.LayoutParams) type11Holder.topIv.getLayoutParams();
                    vl.height = contentBean.getProperties().getHeight() * ScreenUtil.getDisplayWidth()/ contentBean.getProperties().getWidth();
                    displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), type11Holder.topIv);
                    type11Holder.topIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                        }
                    });
                    if (contentBean.getSectionValues().size()>1){
                        type11Holder.bottomRv.setVisibility(View.VISIBLE);
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                        type11Holder.bottomRv.setLayoutManager(layoutManager1);
                        Type11Adapter horizonAdapter1 = new Type11Adapter(mContext, contentBean.getSectionValues());
                        type11Holder.bottomRv.setAdapter(horizonAdapter1);
                    }
                }
                break;
            case 12:
                MainType7ViewHolder mainType12ViewHolder = (MainType7ViewHolder) holder;
                if (contentBean.getVisible() == 1) {
                    promotionIndex12 = position;
                    mainType12ViewHolder.titleLl.setVisibility(View.VISIBLE);
                    mainType12ViewHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    mainType12ViewHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    mainType12ViewHolder.titleAll.setVisibility(View.VISIBLE);
                    mainType12ViewHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    mainType12ViewHolder.titleAll.setVisibility(View.GONE);
                }
                VirtualLayoutManager.LayoutParams v2= (VirtualLayoutManager.LayoutParams) mainType12ViewHolder.itemView.getLayoutParams();
                v2.setMargins(contentBean.getProperties().getLeft(),ScreenUtil.dip2px(contentBean.getProperties().getTop()),contentBean.getProperties().getRight(),ScreenUtil.dip2px(contentBean.getProperties().getBottom()));
                mainType12ViewHolder.imgType7ItemPic.getLayoutParams().height = ScreenUtil.getDisplayWidth() *
                        contentBean.getProperties().getHeight() / contentBean.getProperties().getWidth();
                mainType12ViewHolder.type7ItemPriceLayout.setVisibility(View.GONE);
                displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), mainType12ViewHolder.imgType7ItemPic);
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getShortTitle())) {
                    mainType12ViewHolder.tvType7ItemTitle.setVisibility(View.VISIBLE);
                    mainType12ViewHolder.tvType7ItemTitle.setText(contentBean.getSectionValues().get(0).getShortTitle());
                }
                if (!Util.isEmpty(contentBean.getSectionValues().get(0).getLongTitle())) {
                    mainType12ViewHolder.tvType7ItemContent.setVisibility(View.VISIBLE);
                    mainType12ViewHolder.tvType7ItemContent.setText(contentBean.getSectionValues().get(0).getLongTitle());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex12+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex12+1)+contentBean.getSectionValues().get(0).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                        Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                    }
                });
                mainType12ViewHolder.rvType7Item.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                mainType12ViewHolder.rvType7Item.setLayoutManager(layoutManager1);
                Type12Adapter horizonAdapter1 = new Type12Adapter(mContext, contentBean.getSectionValues().get(0).getList(), contentBean.getTitle(), tag + "_" + getTitle(contentBean.getTitle()));
                mainType12ViewHolder.rvType7Item.setAdapter(horizonAdapter1);
                horizonAdapter1.setOnItemClickListener(new Type12Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put(contentBean.getTitle()+"_位置","位置"+(position-promotionIndex12+1));
                        map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                        map.put(contentBean.getTitle()+"_综合","位置"+(position-promotionIndex12+1)+contentBean.getSectionValues().get(0).getUrl());
                        stat(tag,contentBean.getTitle(),map);
                    }
                });
                break;
            case 13:        //明星风范 Gallery
                //  RecyclerView自定义LayoutManager实现,为解决滑动冲突自定义了Recycler
                final MainShowHolder mainStarHolder = (MainShowHolder) holder;
                mainStarHolder.titleTv.setText("明星风范");
                mainStarHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, StarStyleActivity.class));
                    }
                });
                ViewGroup.LayoutParams layoutParams1 = mainStarHolder.recycleView.getLayoutParams();
                layoutParams1.height=ScreenUtil.dip2px(320);
                mainStarHolder.recycleView.setLayoutParams(layoutParams1);
                StarAdapter starAdapter = new StarAdapter(list.get(position).getSectionValues(), mContext);
                CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
                //调整Item放大效果
                carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
                carouselLayoutManager.setMaxVisibleItems(1);
                mainStarHolder.recycleView.setLayoutManager(carouselLayoutManager);
                mainStarHolder.recycleView.setHasFixedSize(true);
                mainStarHolder.recycleView.setAdapter(starAdapter);
                //手指松开调整item居中效果
                mainStarHolder.recycleView.addOnScrollListener(new CenterScrollListener());
                DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
                    @Override
                    public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                        final int recyclerPosition = recyclerView.getChildLayoutPosition(v);
                        HashMap<String,String> map=new HashMap<>();
                        map.put("位置","位置"+(recyclerPosition+1));
                        map.put("url",list.get(position).getSectionValues().get(recyclerPosition%list.get(position).getSectionValues().size()).getUrl());
                        map.put("综合","位置"+(recyclerPosition+1)+list.get(position).getSectionValues().get(recyclerPosition%list.get(position).getSectionValues().size()).getUrl());
                        stat(tag,"明星风范",map);
                        Util.urlAction(mContext, list.get(position).getSectionValues().get(recyclerPosition%list.get(position).getSectionValues().size()).getUrl());
                    }
                }, mainStarHolder.recycleView, carouselLayoutManager);

                carouselLayoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

                    @Override
                    public void onCenterItemChanged( int adapterPosition) {
                        if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                            if (adapterPosition == 0) {
                                mainStarHolder.recycleView.scrollToPosition(list.get(position).getSectionValues().size());
                            } else if (adapterPosition == (list.get(position).getSectionValues().size()+1)) {
                                mainStarHolder.recycleView.scrollToPosition(1);
                            }
                            starAdapter.dissmissCover(adapterPosition);
                            mainStarHolder.tvStarTitle.setText(list.get(position).getSectionValues().get(adapterPosition % list.get(position).getSectionValues().size()).getShortTitle());
                        }
                    }
                });
                break;
            case 14:
                MainShowTimeHolder showTimeHolder = (MainShowTimeHolder) holder;
                if (contentBean.getVisible() == 1) {
                    showTimeHolder.titleLl.setVisibility(View.VISIBLE);
                    showTimeHolder.titleTv.setText(contentBean.getTitle());
                } else {
                    showTimeHolder.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    showTimeHolder.titleAll.setVisibility(View.VISIBLE);
                    showTimeHolder.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    showTimeHolder.titleAll.setVisibility(View.GONE);
                }
                VirtualLayoutManager.LayoutParams showll = (VirtualLayoutManager.LayoutParams) showTimeHolder.itemView.getLayoutParams();
                showll.setMargins(contentBean.getProperties().getLeft(), contentBean.getProperties().getTop(), contentBean.getProperties().getRight(), contentBean.getProperties().getBottom());
                if (contentBean.getSectionValues().size() == 2) {
                    showTimeHolder.smallRelativeRl.setVisibility(View.GONE);
                    displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), showTimeHolder.ivBigImage);
                    displayImage(mContext, contentBean.getSectionValues().get(1).getFrontPic(), showTimeHolder.ivSmall1Image);
                    showTimeHolder.ivBigImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+1);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+1+contentBean.getSectionValues().get(0).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                        }
                    });
                    showTimeHolder.ivSmall1Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+2);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(1).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+2+contentBean.getSectionValues().get(1).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(1).getUrl());
                        }
                    });
                } else if (contentBean.getSectionValues().size() == 3) {
                    showTimeHolder.smallRelativeRl.setVisibility(View.VISIBLE);
                    displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), showTimeHolder.ivBigImage);
                    displayImage(mContext, contentBean.getSectionValues().get(1).getFrontPic(), showTimeHolder.ivSmall1Image);
                    displayImage(mContext, contentBean.getSectionValues().get(2).getFrontPic(), showTimeHolder.ivSmall2Image);
                    showTimeHolder.ivBigImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+1);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+1+contentBean.getSectionValues().get(0).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                        }
                    });
                    showTimeHolder.ivSmall1Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+2);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(1).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+2+contentBean.getSectionValues().get(1).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(1).getUrl());
                        }
                    });
                    showTimeHolder.ivSmall2Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+3);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(2).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+3+contentBean.getSectionValues().get(2).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(2).getUrl());
                        }
                    });
                }
                break;
            case 15:
                MainShowTimeHolder showTimeHolder1 = (MainShowTimeHolder) holder;
                if (contentBean.getVisible() == 1) {
                    showTimeHolder1.titleLl.setVisibility(View.VISIBLE);
                    showTimeHolder1.titleTv.setText(contentBean.getTitle());
                } else {
                    showTimeHolder1.titleLl.setVisibility(View.GONE);
                }
                if (contentBean.getMore() == 1) {
                    showTimeHolder1.titleAll.setVisibility(View.VISIBLE);
                    showTimeHolder1.titleAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util.urlAction(mContext, contentBean.getMoreUrl());
                        }
                    });
                } else {
                    showTimeHolder1.titleAll.setVisibility(View.GONE);
                }
                VirtualLayoutManager.LayoutParams showll1 = (VirtualLayoutManager.LayoutParams) showTimeHolder1.itemView.getLayoutParams();
                showll1.setMargins(contentBean.getProperties().getLeft(), contentBean.getProperties().getTop(), contentBean.getProperties().getRight(), contentBean.getProperties().getBottom());
                if (contentBean.getSectionValues().size() == 2) {
                    showTimeHolder1.smallRelativeRl.setVisibility(View.GONE);
                    displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), showTimeHolder1.ivBigImage);
                    displayImage(mContext, contentBean.getSectionValues().get(1).getFrontPic(), showTimeHolder1.ivSmall1Image);
                    showTimeHolder1.ivBigImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+1);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+1+contentBean.getSectionValues().get(0).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                        }
                    });
                    showTimeHolder1.ivSmall1Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+2);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(1).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+2+contentBean.getSectionValues().get(1).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(1).getUrl());
                        }
                    });
                } else if (contentBean.getSectionValues().size() == 3) {
                    showTimeHolder1.smallRelativeRl.setVisibility(View.VISIBLE);
                    displayImage(mContext, contentBean.getSectionValues().get(0).getFrontPic(), showTimeHolder1.ivBigImage);
                    displayImage(mContext, contentBean.getSectionValues().get(1).getFrontPic(), showTimeHolder1.ivSmall1Image);
                    displayImage(mContext, contentBean.getSectionValues().get(2).getFrontPic(), showTimeHolder1.ivSmall2Image);
                    showTimeHolder1.ivBigImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+1);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(0).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+1+contentBean.getSectionValues().get(0).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(0).getUrl());
                        }
                    });
                    showTimeHolder1.ivSmall1Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+2);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(1).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+2+contentBean.getSectionValues().get(1).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(1).getUrl());
                        }
                    });
                    showTimeHolder1.ivSmall2Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,String> map=new HashMap<>();
                            map.put(contentBean.getTitle()+"_位置","位置"+3);
                            map.put(contentBean.getTitle()+"_url",contentBean.getSectionValues().get(2).getUrl());
                            map.put(contentBean.getTitle()+"_综合","位置"+3+contentBean.getSectionValues().get(2).getUrl());
                            stat(tag,contentBean.getTitle(),map);
                            Util.urlAction(mContext, contentBean.getSectionValues().get(2).getUrl());
                        }
                    });
                }
                break;
            case 16:
                final MainFlashHolder flashHolder= (MainFlashHolder) holder;
                if (flashBean!=null){
                    VirtualLayoutManager.LayoutParams vl= (VirtualLayoutManager.LayoutParams) flashHolder.flashRl.getLayoutParams();
                    vl.height=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32))*180/395;
                    //vl.height=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32))*120/345;
                    if (contentBean.getSectionValues().size()>0){
                        UniversalImageLoader.displayImage(mContext,contentBean.getSectionValues().get(0).getFrontPic(),flashHolder.flashIv);
                    }
                    if (flashBean.getData().getFlashPromotion().getStartTimeStamp()>System.currentTimeMillis()){
                        endTime=flashBean.getData().getFlashPromotion().getStartTimeStamp();
                        flashHolder.tag.setText("距开始");
                        setPromotionTime(flashBean.getData().getFlashPromotion().getStartTimeStamp(),flashHolder.flashHourTv,flashHolder.flashMinuteTv,flashHolder.flashMouseTv);
                    }else if (flashBean.getData().getFlashPromotion().getEndTimeStamp()>System.currentTimeMillis()){
                        endTime=flashBean.getData().getFlashPromotion().getEndTimeStamp();
                        flashHolder.tag.setText("距结束");
                        setPromotionTime(flashBean.getData().getFlashPromotion().getEndTimeStamp(),flashHolder.flashHourTv,flashHolder.flashMinuteTv,flashHolder.flashMouseTv);
                    }else {
                        flashHolder.timeLl.setVisibility(View.GONE);
                    }
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                        handler = null;
                    }
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            //更改时间
                            setPromotionTime(endTime,flashHolder.flashHourTv,flashHolder.flashMinuteTv,flashHolder.flashMouseTv);
                            handler.sendEmptyMessageDelayed(1, 1000);
                        }
                    };
                    handler.sendEmptyMessageDelayed(1, 1000);
                    flashHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(mContext, FlashPromotionActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                }else {
                    VirtualLayoutManager.LayoutParams vl= (VirtualLayoutManager.LayoutParams) flashHolder.itemView.getLayoutParams();
                    vl.setMargins(0,0,0,0);
                    vl.height=0;
                }
                break;
        }
    }


    private void setPromotionTime(long startTime,TextView hourTv, TextView minuteTv, TextView mouseTv){
        long offer = startTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }



    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    private void stat(String event, String label, Map map){
        if (map==null){
            StatService.onEvent(mContext, event, label);
            TCAgent.onEvent(mContext, event, label);
        }else {
            TCAgent.onEvent(mContext,"V3"+event,label,map);
        }
    }

    private String getTitle(String title) {
        if (Util.isEmpty(title)) {
            return "固定频道";
        } else {
            return title;
        }
    }

    public void recycle(){
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void setFlashBean(FlashBean flashBean) {
        this.flashBean = flashBean;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
