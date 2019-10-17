package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FlashBrandDetailActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.FlashBrandListBean;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.holder.FlashFirstProductHolder;
import com.d2cmall.buyer.holder.FlashNewProductHolder;
import com.d2cmall.buyer.holder.FlashRobBrandHolder;
import com.d2cmall.buyer.holder.RobBrandHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by rookie on 2018/5/2.
 * 限时购,抢大牌以及抢大牌详情页的adapter
 */

public class FlashBuyAdapter extends DelegateAdapter.Adapter {

    private List<Object> list;//数据源,由于涉及到多布局,这里用Object作为泛型
    private Context context;//上下文对象
    private boolean isBrand;//是否抢大牌详情页的表示,传进来的
    private boolean isStart;//该场次是否已经开始
    private final int UNKNOWN = -1;//未识别的返回未知类型
    private final int NORMAL_PRODUCT_TYPE = 0;//正常的商品类型
    private final int NORMAL_BRAND_TYPE = 1;//插入的品牌类型
    private final int FIRST_PRODUCT_TYPE = 2;//第一个商品类型
    private final int FIRST_ROB_BRAND_TYPE = 3;//第一个抢大牌类型
    private final int NORMAL_ROB_BRAND_TYPE = 4;//正常的抢大牌类型

    private FlashProductRemindCallBack callBack;//提醒我回调

    private RecyclerView.RecycledViewPool recycledViewPool;//内部recyclerview共用一个pool

    public interface FlashProductRemindCallBack {
        void notice(long id);
    }

    public void setCallBack(FlashProductRemindCallBack callBack) {
        this.callBack = callBack;
    }


    public FlashBuyAdapter(List<Object> list, Context context) {
        this(list, context, false);
    }

    public FlashBuyAdapter(List<Object> list, Context context, boolean isBrand) {
        this.list = list;
        this.context = context;
        this.isBrand = isBrand;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (viewType) {
            case UNKNOWN://Something for nothing
                break;
            case NORMAL_PRODUCT_TYPE://正常的商品类型
                view = layoutInflater.inflate(R.layout.layout_flash_product_item, parent, false);
                return new FlashNewProductHolder(view);
            case NORMAL_BRAND_TYPE://插入商品中的品牌类型
                view = layoutInflater.inflate(R.layout.layout_flash_rob_brand_item, parent, false);
                return new FlashRobBrandHolder(view);
            case FIRST_PRODUCT_TYPE://位置为一的商品类型
                view = layoutInflater.inflate(R.layout.layout_first_flash_product, parent, false);
                return new FlashFirstProductHolder(view);
            case FIRST_ROB_BRAND_TYPE://第一个抢大牌类型
            case NORMAL_ROB_BRAND_TYPE://正常的抢大牌类型
                view = layoutInflater.inflate(R.layout.layout_rob_brand, parent, false);
                return new RobBrandHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case UNKNOWN:
                break;
            case NORMAL_PRODUCT_TYPE:
                FlashNewProductHolder flashNewProductHolder = (FlashNewProductHolder) holder;
                FlashProductListBean.DataBean.ProductsBean.ListBean data = (FlashProductListBean.DataBean.ProductsBean.ListBean) list.get(position);
                setNormalProduct(flashNewProductHolder, data);//正常商品View
                break;
            case NORMAL_BRAND_TYPE:
                FlashRobBrandHolder flashRobBrandHolder = (FlashRobBrandHolder) holder;
                FlashProductListBean.DataBean.BrandFlashPromotionsBean brandData = (FlashProductListBean.DataBean.BrandFlashPromotionsBean) list.get(position);
                setNormalBrand(flashRobBrandHolder, brandData);//插入商品中的品牌View
                break;
            case FIRST_PRODUCT_TYPE:
                FlashFirstProductHolder flashFirstProductHolder = (FlashFirstProductHolder) holder;
                FlashProductListBean.DataBean.ProductsBean.ListBean data2 = (FlashProductListBean.DataBean.ProductsBean.ListBean) list.get(position);
                setFirstProduct(flashFirstProductHolder, data2);//第一个商品View
                break;
            case FIRST_ROB_BRAND_TYPE:
                RobBrandHolder robBrandHolder = (RobBrandHolder) holder;
                FlashBrandListBean.DataBean.BrandsBean brandsBean = (FlashBrandListBean.DataBean.BrandsBean) list.get(position);
                setBrand(robBrandHolder, brandsBean, FIRST_ROB_BRAND_TYPE);//第一个抢大牌View
                break;
            case NORMAL_ROB_BRAND_TYPE:
                RobBrandHolder robBrandHolder2 = (RobBrandHolder) holder;
                FlashBrandListBean.DataBean.BrandsBean brandsBean2 = (FlashBrandListBean.DataBean.BrandsBean) list.get(position);
                setBrand(robBrandHolder2, brandsBean2, NORMAL_ROB_BRAND_TYPE);//正常的抢大牌View
                break;
        }
    }

    private void setBrand(RobBrandHolder robBrandHolder, final FlashBrandListBean.DataBean.BrandsBean brandsBean, int type) {
        VirtualLayoutManager.LayoutParams layoutParams = (VirtualLayoutManager.LayoutParams) robBrandHolder.rlAll.getLayoutParams();
        if (type == FIRST_ROB_BRAND_TYPE) {//是FIRST_ROB_BRAND_TYPE需要将部分控件显示
            robBrandHolder.ivBrandBg.setVisibility(View.VISIBLE);
            robBrandHolder.ivRobBtn.setVisibility(View.VISIBLE);
            robBrandHolder.tvTop.setVisibility(View.VISIBLE);
            robBrandHolder.ivBottomCover.setVisibility(View.VISIBLE);
            layoutParams.bottomMargin = ScreenUtil.dip2px(10);
        } else {//是NORMAL_ROB_BRAND_TYPE需要将部分控件隐藏
            robBrandHolder.ivBrandBg.setVisibility(View.GONE);
            robBrandHolder.ivRobBtn.setVisibility(View.GONE);
            robBrandHolder.tvTop.setVisibility(View.GONE);
            robBrandHolder.ivBottomCover.setVisibility(View.GONE);
            layoutParams.bottomMargin = ScreenUtil.dip2px(30);
        }
        robBrandHolder.rlTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFlashBrandActivity(brandsBean.getName(), brandsBean.getId(), brandsBean.getBrandPic());
            }
        });
        robBrandHolder.rlAll.setLayoutParams(layoutParams);
        UniversalImageLoader.displayImage(context, brandsBean.getBrandPic(), robBrandHolder.ivBrandImg, R.mipmap.ic_logo_empty2);
        robBrandHolder.recycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        robBrandHolder.recycleView.setRecycledViewPool(recycledViewPool);
        robBrandHolder.recycleView.setAdapter(new MyAdapter(brandsBean.getProducts()));
    }

    private void setFirstProduct(FlashFirstProductHolder flashFirstProductHolder, final FlashProductListBean.DataBean.ProductsBean.ListBean data) {
        UniversalImageLoader.displayImage(context, data.getImg(), flashFirstProductHolder.ivProduct, R.mipmap.ic_logo_empty5);
        flashFirstProductHolder.tvName.setText(data.getName());
        if (!Util.isEmpty(data.getSubTitle())) {
            Drawable nav_dowm = context.getResources().getDrawable(R.mipmap.icon_xin);
            nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
            flashFirstProductHolder.tvDescription.setCompoundDrawables(nav_dowm, null, null, null);
            flashFirstProductHolder.tvDescription.setVisibility(View.VISIBLE);
            flashFirstProductHolder.tvDescription.setText(data.getSubTitle());
        } else {
            flashFirstProductHolder.tvDescription.setVisibility(View.GONE);
        }
        Integer flashSellStock = data.getFlashSellStock();
        Integer flashStock = data.getFlashStock();
        int left = flashStock - flashSellStock;
        if (left > 0) {
            flashFirstProductHolder.ivButton.setImageResource(R.mipmap.xianshigou_btn_mashangqiang);
        } else {
            flashFirstProductHolder.ivButton.setImageResource(R.mipmap.big_xianshigou_btn_shouwan);
        }
        if (!Util.isEmpty(data.getOrderPromotionTypeName())) {
            flashFirstProductHolder.tvOrderPromotion.setVisibility(View.VISIBLE);
            flashFirstProductHolder.tvOrderPromotion.setText(data.getOrderPromotionTypeName());
        } else {
            flashFirstProductHolder.tvOrderPromotion.setVisibility(View.GONE);
        }
        if (isStart) {
            flashFirstProductHolder.ivButton.setImageResource(R.mipmap.big_xianshigou_btn_tixing);
        }
        flashFirstProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDetailActivity(data.getId());
            }
        });
        flashFirstProductHolder.ivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    callBack.notice(data.getId());
                } else {
                    toDetailActivity(data.getId());
                }
            }
        });
        StringBuilder builder = new StringBuilder("¥" + data.getFlashPrice());
        SpannableString spannableString = new SpannableString(builder.toString());
        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.4f);
        spannableString.setSpan(sizeSpan1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        flashFirstProductHolder.tvMinPrice.setText(spannableString);
        if (data.getFlashPrice() < data.getSalePrice()) {
            flashFirstProductHolder.tvHighPrice.setVisibility(View.VISIBLE);
            flashFirstProductHolder.tvHighPrice.setText("¥" + Util.getNumberFormat(data.getSalePrice()));
            flashFirstProductHolder.tvHighPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            flashFirstProductHolder.tvHighPrice.setVisibility(View.INVISIBLE);
        }
    }

    private void setNormalBrand(FlashRobBrandHolder flashRobBrandHolder, final FlashProductListBean.DataBean.BrandFlashPromotionsBean brandData) {
        flashRobBrandHolder.ivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFlashBrandActivity(brandData.getName(), brandData.getId(), brandData.getBrandPic());
            }
        });
        flashRobBrandHolder.ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFlashBrandActivity(brandData.getName(), brandData.getId(), brandData.getBrandPic());
            }
        });
        flashRobBrandHolder.tvBrandName.setText(brandData.getName());
        if (brandData.getProducts() != null && brandData.getProducts().size() > 0) {
            flashRobBrandHolder.recycleView.setVisibility(View.VISIBLE);
            flashRobBrandHolder.recycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            flashRobBrandHolder.recycleView.setRecycledViewPool(recycledViewPool);
            flashRobBrandHolder.recycleView.setAdapter(new MyAdapter(brandData.getProducts()));
        } else {
            flashRobBrandHolder.recycleView.setVisibility(View.GONE);
        }
    }

    private void setNormalProduct(FlashNewProductHolder flashNewProductHolder, final FlashProductListBean.DataBean.ProductsBean.ListBean data) {
        UniversalImageLoader.displayImage(context, data.getImg(), flashNewProductHolder.ivProduct, R.mipmap.ic_logo_empty5);
        flashNewProductHolder.tvName.setText(data.getName());
        if (!Util.isEmpty(data.getSubTitle())) {
            flashNewProductHolder.tvDescription.setVisibility(View.VISIBLE);
            flashNewProductHolder.tvDescription.setText(data.getSubTitle());
        } else {
            flashNewProductHolder.tvDescription.setVisibility(View.GONE);
        }

        if (!Util.isEmpty(data.getOrderPromotionTypeName())) {
            flashNewProductHolder.tvOrderPromotion.setText(data.getOrderPromotionTypeName());
            flashNewProductHolder.tvOrderPromotion.setVisibility(View.VISIBLE);
        } else {
            flashNewProductHolder.tvOrderPromotion.setVisibility(View.GONE);
        }

        Integer flashSellStock = data.getFlashSellStock();
        Integer flashStock = data.getFlashStock();
        int left = flashStock - flashSellStock;
        if (left > 0) {
            flashNewProductHolder.ivButton.setImageResource(R.mipmap.icon_mashangqiang);
            int progress;
            if (flashStock == 0) {
                progress = 0;
                flashNewProductHolder.llProgress.setVisibility(View.GONE);
            } else {
                DecimalFormat df = new DecimalFormat("0.00");
                progress = (int) (Double.valueOf(df.format((float) left / flashStock)) * 100);
                flashNewProductHolder.tvSurplus.setText("仅剩" + left + "件");
                flashNewProductHolder.progressBar.setProgress(progress);
                flashNewProductHolder.llProgress.setVisibility(View.VISIBLE);
            }
        } else {
            flashNewProductHolder.ivButton.setImageResource(R.mipmap.icon_shouwan);
            flashNewProductHolder.llProgress.setVisibility(View.GONE);
        }
        if (isStart) {
            flashNewProductHolder.ivButton.setImageResource(R.mipmap.icon_tixingwo);
            flashNewProductHolder.llProgress.setVisibility(View.GONE);
        }
        flashNewProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDetailActivity(data.getId());
            }
        });
        flashNewProductHolder.ivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    callBack.notice(data.getId());
                } else {
                    toDetailActivity(data.getId());
                }
            }
        });
        StringBuilder builder = new StringBuilder("¥" + data.getFlashPrice());
        SpannableString spannableString = new SpannableString(builder.toString());
        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.5f);
        spannableString.setSpan(sizeSpan1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        flashNewProductHolder.tvMinPrice.setText(spannableString);
        if (data.getFlashPrice() < data.getSalePrice()) {
            flashNewProductHolder.tvHighPrice.setVisibility(View.VISIBLE);
            flashNewProductHolder.tvHighPrice.setText("¥" + Util.getNumberFormat(data.getSalePrice()));
            flashNewProductHolder.tvHighPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            flashNewProductHolder.tvHighPrice.setVisibility(View.INVISIBLE);
        }
    }

    private void toDetailActivity(long id) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private void toFlashBrandActivity(String title, int promotionId, String pic) {
        Intent intent = new Intent(context, FlashBrandDetailActivity.class);
        intent.putExtra("promotionId", promotionId);
        intent.putExtra("title", title);
        intent.putExtra("pic", pic);
        context.startActivity(intent);
    }


    @Override
    public int getItemViewType(int position) {
        int type;
        if (list.get(position) instanceof FlashProductListBean.DataBean.ProductsBean.ListBean) {
            if (position == 0 && !isBrand) {
                type = FIRST_PRODUCT_TYPE;
            } else {
                type = NORMAL_PRODUCT_TYPE;
            }
        } else if (list.get(position) instanceof FlashProductListBean.DataBean.BrandFlashPromotionsBean) {
            type = NORMAL_BRAND_TYPE;
        } else if (list.get(position) instanceof FlashBrandListBean.DataBean.BrandsBean) {
            if (position == 0 && !isBrand) {
                type = FIRST_ROB_BRAND_TYPE;
            } else {
                type = NORMAL_ROB_BRAND_TYPE;
            }
        } else {
            type = UNKNOWN;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        if (isBrand) {
            linearLayoutHelper.setMarginTop(ScreenUtil.dip2px(10));
        }
        return linearLayoutHelper;
    }

    class MyAdapter extends RecyclerView.Adapter<FlashBuyAdapter.MyAdapter.MyViewHolder> {
        private List<FlashProductListBean.DataBean.ProductsBean.ListBean> list2;

        public MyAdapter(List<FlashProductListBean.DataBean.ProductsBean.ListBean> list) {
            this.list2 = list;
        }


        @Override
        public FlashBuyAdapter.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_flash_brand_product_item, parent, false);
            return new FlashBuyAdapter.MyAdapter.MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return list2.size();
        }

        @Override
        public void onBindViewHolder(FlashBuyAdapter.MyAdapter.MyViewHolder holder, final int position) {
            FlashProductListBean.DataBean.ProductsBean.ListBean productsEntity = list2.get(position);
            UniversalImageLoader.displayImage(context, productsEntity.getImg(), holder.imageView);
            //显示价格()
            if (productsEntity.getPromotionId() > 0) {
                holder.textView.setText("¥" + Util.getNumberFormat(productsEntity.getMinPrice()));
                if (productsEntity.getSalePrice() > productsEntity.getPrice()) {
                    holder.textView1.setVisibility(View.VISIBLE);
                    holder.textView1.setText("¥" + Util.getNumberFormat(productsEntity.getSalePrice()));
                    holder.textView1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            } else {
                if (productsEntity.getMinPrice() >= productsEntity.getOriginalPrice()) {
                    holder.textView1.setVisibility(View.GONE);
                } else {
                    holder.textView1.setVisibility(View.VISIBLE);
                    holder.textView1.setText("¥" + Util.getNumberFormat(productsEntity.getOriginalPrice()));
                    holder.textView1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                holder.textView.setText("¥" + Util.getNumberFormat(productsEntity.getMinPrice()));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", list2.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;
            public TextView textView1;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                textView = (TextView) itemView.findViewById(R.id.product_price);
                textView1 = (TextView) itemView.findViewById(R.id.product_drop_price);
            }
        }
    }
}
