package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.AdBean;
import com.d2cmall.buyer.bean.HotBrandBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.GridHolder;
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

public class TestAdapter extends DelegateAdapter.Adapter {

    private List<Object> list;
    private Context context;
    private int itemWith;
    GridHolder gridHolder;

    public TestAdapter(List<Object> list, Context context, int itemWith) {
        this.list = list;
        this.context = context;
        this.itemWith = itemWith;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, parent, false);
            return new BannerHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_grid_category, parent, false);
            return new GridHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerHolder) {
            int height = ScreenUtil.dip2px(90);
            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(-1, -2);
            layoutParams.mAspectRatio = (float) (ScreenUtil.getDisplayWidth() * 3 / 4) / height;
            layoutParams.setMargins(ScreenUtil.dip2px(0), ScreenUtil.dip2px(8), ScreenUtil.dip2px(0), ScreenUtil.dip2px(0));
            BannerHolder bannerHolder = (BannerHolder) holder;
            bannerHolder.itemView.setLayoutParams(layoutParams);
            final AdBean.DataBean.AdResourceBean adResourceBean = (AdBean.DataBean.AdResourceBean) list.get(position);
            List<String> bannerItems = new ArrayList<>();
            bannerItems.add(adResourceBean.getPic());
            bannerHolder.banner
                    .setScaleType(true)
                    .setSource(bannerItems)
                    .setIndicatorHeight(0)
                    .startScroll();
            bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    if (!Util.isEmpty(adResourceBean.getUrl())) {
                        Util.urlAction(context, adResourceBean.getUrl());
                    }
                }
            });
        } else if (holder instanceof TestAdapter.ViewHolder) {
            TestAdapter.ViewHolder viewHolder = (ViewHolder) holder;
//            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(itemWith,LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.topMargin=ScreenUtil.dip2px(16);
//            viewHolder.itemView.setLayoutParams(layoutParams);
//            viewHolder.itemView.setPadding(ScreenUtil.dip2px(0.5f),ScreenUtil.dip2px(0.5f),ScreenUtil.dip2px(0.5f),ScreenUtil.dip2px(0.5f));
            if (position % 3 == 0) {
                viewHolder.ll_hot.setGravity(Gravity.RIGHT);
            } else if (position % 3 == 1) {
                viewHolder.ll_hot.setGravity(Gravity.LEFT);
            } else if (position % 3 == 2) {
                viewHolder.ll_hot.setGravity(Gravity.CENTER);
            }
            final HotBrandBean.DataBean.DesignersBean.ListBean listBean = (HotBrandBean.DataBean.DesignersBean.ListBean) list.get(position);
            UniversalImageLoader.displayImage(context, listBean.getHeadPic(), viewHolder.img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("id", (int) listBean.getId());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof GridHolder) {
            gridHolder = (GridHolder) holder;
            HotBrandBean.DataBean.DesignersBean data = (HotBrandBean.DataBean.DesignersBean) list.get(position);
            List<HotBrandBean.DataBean.DesignersBean.ListBean> dataList = data.getList();
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
                final int rightMagin = Math.abs((itemWith - ScreenUtil.dip2px(192)) / 3);
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
                        params.height = ScreenUtil.dip2px(64);
                        /*if (j3!=columnNum4-1){
                            params.rightMargin=ScreenUtil.dip2px(28);
                        }*/
                        if (index4 % 3 == 0) {
                            params.rightMargin = ScreenUtil.dip2px(23f);
                        } else if (index4 % 3 == 1) {
                            params.rightMargin = ScreenUtil.dip2px(23f);
                        } else {
                            params.rightMargin = ScreenUtil.dip2px(0);
                        }
                        if (i3 != rowNum4 - 1) {
                            params.bottomMargin = ScreenUtil.dip2px(16);
                        }
                        final HotBrandBean.DataBean.DesignersBean.ListBean sectionValuesBean = dataList.get(index4);
                        final View type3 = LayoutInflater.from(context).inflate(R.layout.layout_brand_hot, new LinearLayout(context), false);
                        final ImageView imgItemPic = (ImageView) type3.findViewById(R.id.image);
                        UniversalImageLoader.displayImage(context, sectionValuesBean.getHeadPic(), imgItemPic);
                        imgItemPic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StatService.onEvent(context,"V3分类","品牌/热门/"+sectionValuesBean.getId());
                                TCAgent.onEvent(context,"V3分类","品牌/热门/"+sectionValuesBean.getId());
                                Intent intent = new Intent(context, BrandDetailActivity.class);
                                intent.putExtra("id", (int) sectionValuesBean.getId());
                                context.startActivity(intent);
                            }
                        });
                        index4++;
                        gridLayout.addView(type3, params);
                    }
                }
            }
        }

    }

    public void runGridAnimation() {
        if (gridHolder != null) {
            runLayoutAnimation(gridHolder.gridLayout);
        }
    }

    private void runLayoutAnimation(final GridLayout recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.animation_layout_category);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof AdBean.DataBean.AdResourceBean) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        LinearLayout ll_hot;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image);
            ll_hot = (LinearLayout) itemView.findViewById(R.id.ll_hot);
        }
    }
}
