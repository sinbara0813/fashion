package com.d2cmall.buyer.adapter;

import android.content.Context;
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
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CategoryBannerBean;
import com.d2cmall.buyer.bean.KindBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.CategoryDetailHolder;
import com.d2cmall.buyer.holder.GridHolder;
import com.d2cmall.buyer.holder.KindNameHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2017/10/11.
 */

public class CategoryAdapter extends DelegateAdapter.Adapter {
    private List<Object> list;
    private Context context;
    private int itemWith;
    private String name;

    public CategoryAdapter(List<Object> list, Context context, int itemWith) {
        this.list = list;
        this.context = context;
        this.itemWith = itemWith;
    }

    public void setName(String name){
        this.name=name;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.layout_type_name_item, parent, false);
                return new KindNameHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.layout_grid_category, parent, false);
                return new GridHolder(view);
//                view = LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false);
//                return new CategoryDetailHolder(view);
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.layout_banner, parent, false);
                return new BannerHolder(view);
        }
        return null;
    }

    private void start(String event,String label){
        StatService.onEvent(context,event,label);
        TCAgent.onEvent(context,event,label);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KindNameHolder) {
            KindNameHolder nameHolder = (KindNameHolder) holder;
            String name = (String) list.get(position);
            String[] s=name.split(",");
            nameHolder.textView.setText(s[0]);
            if (!Util.isEmpty(s[1])){
                nameHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.urlAction(context,s[1]);
                    }
                });
            }
        } else if (holder instanceof CategoryDetailHolder) {
            final KindBean.DataEntity.NavigationsEntity.ItemsEntity data = (KindBean.DataEntity.NavigationsEntity.ItemsEntity) list.get(position);
            CategoryDetailHolder categoryDetailHolder = (CategoryDetailHolder) holder;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemWith, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, ScreenUtil.dip2px(16));
            categoryDetailHolder.itemView.setLayoutParams(layoutParams);
            UniversalImageLoader.displayImage(context, Constants.IMG_HOST + data.getPic(), categoryDetailHolder.image);
            categoryDetailHolder.text.setText(data.getTitle());
            categoryDetailHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Util.urlAction(context, data.getUrl());
                }
            });
        } else if (holder instanceof BannerHolder) {
            final CategoryBannerBean categoryBannerBean = (CategoryBannerBean) list.get(position);
            BannerHolder bannerHolder = (BannerHolder) holder;
            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(72));
            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(-1, -2);
            layoutParams.mAspectRatio =3;
            layoutParams.setMargins(ScreenUtil.dip2px(0), ScreenUtil.dip2px(8), ScreenUtil.dip2px(0), ScreenUtil.dip2px(16));
            bannerHolder.itemView.setLayoutParams(layoutParams);
            List<String> bannerItems = new ArrayList<>();
            bannerItems.add(categoryBannerBean.getPic());
            bannerHolder.banner
                    .setScaleType(true)
                    .setSource(bannerItems)
                    .setIndicatorHeight(0)
                    .startScroll();
            bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    if (!Util.isEmpty(categoryBannerBean.getUrl())) {
                        Util.urlAction(context, categoryBannerBean.getUrl());
                    }
                }
            });
        } else if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            KindBean.DataEntity.NavigationsEntity data = (KindBean.DataEntity.NavigationsEntity) list.get(position);
            List<KindBean.DataEntity.NavigationsEntity.ItemsEntity> dataList = data.getItems();
            GridLayout gridLayout = gridHolder.gridLayout;
            gridLayout.removeAllViews();
            if (dataList != null && dataList.size() > 0) {
                LinearLayout.LayoutParams ll4 = (LinearLayout.LayoutParams) gridLayout.getLayoutParams();
                int columnNum4 = 3;
                int rowNum4 = (int) Math.ceil((double) dataList.size() / columnNum4);
                int screenWidth = ScreenUtil.getDisplayWidth();
                //int marginLeft=(screenWidth-ScreenUtil.dip2px(56)*columnNum4-ScreenUtil.dip2px(28)*(columnNum4-1))/2;
                ll4.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(24));
                final int paramWidth4 = (itemWith - ScreenUtil.dip2px(32)) / columnNum4;
                final int rightMagin=Math.abs((itemWith-ScreenUtil.dip2px(192))/3);
                int index4 = 0;
                for (int i3 = 0; i3 < rowNum4; i3++) {
                    for (int j3 = 0; j3 < columnNum4; j3++) {
                        if (index4 >= dataList.size()) {
                            break;
                        }
                        GridLayout.Spec rowSpec = GridLayout.spec(i3);
                        GridLayout.Spec columnSpec = GridLayout.spec(j3);
                        final GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = ScreenUtil.dip2px(64);
                        params.height = params.WRAP_CONTENT;
                        if(index4%3==0){
                            params.rightMargin=ScreenUtil.dip2px(24);
                        }else if(index4%3==1){
                            params.rightMargin=ScreenUtil.dip2px(24);
                        }else {
                            params.rightMargin=ScreenUtil.dip2px(0);
                        }
                        if (i3 != rowNum4 - 1) {
                            params.bottomMargin = ScreenUtil.dip2px(16);
                        }
                        final KindBean.DataEntity.NavigationsEntity.ItemsEntity sectionValuesBean = dataList.get(index4);
                        final View type3 = LayoutInflater.from(context).inflate(R.layout.layout_category_item, new LinearLayout(context), false);
                        final ImageView imgItemPic = (ImageView) type3.findViewById(R.id.image);
                        final TextView textItem = (TextView) type3.findViewById(R.id.text);
                        UniversalImageLoader.displayImage(context, sectionValuesBean.getPic(), imgItemPic);
                        if (!Util.isEmpty(sectionValuesBean.getTitle())) {
                            textItem.setText(sectionValuesBean.getTitle());
                        }
                        imgItemPic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                start("V3分类","商品/"+name+"/"+sectionValuesBean.getTitle());
                                Util.urlAction(context, sectionValuesBean.getUrl());
                            }
                        });
                        index4++;
                        gridLayout.addView(type3, params);
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null) {
            if (list.get(position) instanceof String) {
                return 0;
            } else if (list.get(position) instanceof KindBean.DataEntity.NavigationsEntity) {
                return 1;
            } else if (list.get(position) instanceof CategoryBannerBean) {
                return 2;
            }else if(list.get(position) instanceof KindBean.DataEntity.NavigationsEntity.ItemsEntity){
                return 3;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }
}
