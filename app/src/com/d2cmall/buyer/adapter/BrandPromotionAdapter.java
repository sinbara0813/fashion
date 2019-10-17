package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PromotionsActivity;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.BrandDetailPromotionBean;
import com.d2cmall.buyer.bean.CouponSuccessBean;
import com.d2cmall.buyer.holder.BrandCouponHolder;
import com.d2cmall.buyer.holder.BrandPromotionArticleHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;


/**
 * Created by Administrator on 2018/8/6.
 * Description : DCoinShopAdapter
 */

public class BrandPromotionAdapter extends DelegateAdapter.Adapter {
    private  List<BrandDetailPromotionBean.DataBean.PromotionsBean> mPromotions ;
    private Context mContext;
    private  int promotionSize;
    private  int couponSize;
    private  int artticleSize;
    private  List<BrandDetailPromotionBean.DataBean.CouponDefsBean> mCouponDefs;
    private  List<BrandDetailPromotionBean.DataBean.ArticlesBean> mArticles;
    int ariticaleOffer = 0;
    int pomotionOffer = 0;

    public void setNoData(boolean noData) {
        this.noData = noData;
        if(noData && pomotionOffer==0){
            pomotionOffer+=1;
        }
    }

    private boolean noData;//当前店铺没活动,优惠券,文章,展示的为推荐其它活动

    public BrandPromotionAdapter(Context context, List<BrandDetailPromotionBean.DataBean.ArticlesBean> articles, List<BrandDetailPromotionBean.DataBean.CouponDefsBean> couponDefs, List<BrandDetailPromotionBean.DataBean.PromotionsBean>  promotions) {
        super();
        this.mContext = context;
        this.mArticles = articles;
        this.mCouponDefs = couponDefs;
        this.mPromotions = promotions;
        promotionSize = promotions==null?0:promotions.size();
        if(promotionSize >0){
            ariticaleOffer+=1;
        }
        couponSize = couponDefs ==null?0: couponDefs.size();
        artticleSize = articles==null?0:articles.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 1: //优惠券
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_coupon, parent, false);
                return new BrandCouponHolder(view);
            case 2: //活动
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_promotion_article, parent, false);
                return new BrandPromotionArticleHolder(view);
            case 3: //文章
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_promotion_article, parent, false);
                return new BrandPromotionArticleHolder(view);
            case 4: //当前店铺没活动,优惠券,文章,展示的为推荐其它活动
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_no_promotion, parent, false);
                return new BrandPromotionArticleHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_promotion_article, parent, false);
                return new BrandPromotionArticleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case 1:
                BrandCouponHolder couponHolder = (BrandCouponHolder) holder;
                if (mCouponDefs.get(position).getType().equals("DISCOUNT")) {
                    double amount = (double) mCouponDefs.get(position).getAmount() / 10;
                    couponHolder.tvCouponAmount.setText(mContext.getString(R.string.label_discount, Util.getNumberFormat(amount, false)));
                } else {
                    couponHolder.tvCouponAmount.setText(mContext.getString(R.string.label_price,String.valueOf(mCouponDefs.get(position).getAmount())));
                }
                couponHolder.tvLimitAmount.setText(String.format(mContext.getString(R.string.label_need_amount1), mCouponDefs.get(position).getNeedAmount()));
                couponHolder.tvCouponName.setText(mCouponDefs.get(position).getName());
                String enableDate = mCouponDefs.get(position).getEnableDate().substring(mCouponDefs.get(position).getEnableDate().indexOf("/")+1,mCouponDefs.get(position).getEnableDate().length());
                String expireDate = mCouponDefs.get(position).getExpireDate().substring(mCouponDefs.get(position).getExpireDate().indexOf("/") + 1, mCouponDefs.get(position).getExpireDate().length());
                couponHolder.tvCouponDate.setText(String.format(mContext.getString(R.string.label_pozhe),enableDate , expireDate));
                couponHolder.ivReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCoupon(mCouponDefs.get(position).getId(),v);
                    }
                });
                break;
            case 2:
                BrandPromotionArticleHolder activityViewHolder1= (BrandPromotionArticleHolder) holder;
                UniversalImageLoader.displayImage(mContext,mPromotions.get(position-couponSize-pomotionOffer).getBrandPic(), activityViewHolder1.mImageView,R.mipmap.ic_logo_empty2);
                activityViewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, PromotionsActivity.class).putExtra("id", (long) mPromotions.get(position-couponSize-pomotionOffer).getId()));
                    }
                });
                break;
            case 3:
                BrandPromotionArticleHolder activityViewHolder= (BrandPromotionArticleHolder) holder;
                UniversalImageLoader.displayImage(mContext,mArticles.get(position- promotionSize-couponSize -pomotionOffer).getBrandPic(), activityViewHolder.mImageView,R.mipmap.ic_logo_empty2);
                activityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Util.urlAction(mContext,"page/"+mArticles.get(position- promotionSize-couponSize -pomotionOffer).getName());
                    }
                });
                break;
        }
    }


    private void getCoupon(long id, View v) {
        v.setEnabled(false);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.LEAD_COUPON_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponSuccessBean>() {
            @Override
            public void onResponse(CouponSuccessBean response) {
                v.setEnabled(true);
                Util.showToast(mContext, "领取成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                v.setEnabled(true);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0 && noData){
            return 4;
        }else{
            int offer=0;
            if(noData){
                offer+=1;
            }
            if(position<couponSize+offer){
                return 1;
            }else if(position>=couponSize+offer && position<couponSize + promotionSize+offer){
                return 2;
            }else{
                return 3;
            }

        }

    }

    @Override
    public int getItemCount() {
        int count=0;
        if(noData){
            count+=1;
        }
        count+=couponSize;
        count+= promotionSize;
        count+=artticleSize;
        return count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPadding(0,0,0,ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }
}
