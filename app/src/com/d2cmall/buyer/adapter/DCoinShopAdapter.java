package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.DCoinRecordConversionActivity;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.bean.DCionProductListBean;
import com.d2cmall.buyer.bean.DCionShopBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.DCoinShopActionHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

import static com.d2cmall.buyer.util.UniversalImageLoader.displayImage;

/**
 * Created by Administrator on 2018/8/6.
 * Description : DCoinShopAdapter
 */

public class DCoinShopAdapter extends DelegateAdapter.Adapter {
    private final UserBean.DataEntity.MemberEntity user;
    private Context mContext;
    private ArrayList<DCionProductListBean.DataBean.ListBeanX.ListBean> listBeans;
    private int point;


    private DCionShopBean dCionShopBean;
    public DCoinShopAdapter(Context context) {
        super();
        this.mContext = context;
        user = Session.getInstance().getUserFromFile(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 1: //banner
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_loop_banner, parent, false);
                return new BannerHolder(view);
            case 2: //D币明细,兑换记录
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_dcoin_integration, parent, false);
                return new DCoinShopActionHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_dcoin_integration, parent, false);
                return new DCoinShopActionHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        switch (getItemViewType(position)){
            case 1:
                BannerHolder bannerHolder = (BannerHolder) holder;
                if(dCionShopBean==null || dCionShopBean.getData().getAdResource().getPics()==null ||dCionShopBean.getData().getAdResource().getPics().size()==0){
                    return;
                }
                int size = dCionShopBean.getData().getAdResource().getPics().size();
                List<String> bannerItems = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    bannerItems.add(dCionShopBean.getData().getAdResource().getPics().get(i));
                }
                int height = ScreenUtil.dip2px(154);
                VirtualLayoutManager.LayoutParams Ll = new VirtualLayoutManager.LayoutParams(-1, -2);
                Ll.mAspectRatio = (float) ScreenUtil.getDisplayWidth() / height;
                bannerHolder.itemView.setLayoutParams(Ll);

                bannerHolder.banner.setIndicatorType(1).setBottomPadding(8).setScaleType(true).setLoadingPic(R.mipmap.ic_logo_empty7).setSource(bannerItems).setAutoScrollEnable(true).startScroll();
                if (size==1){
                    bannerHolder.banner.pauseScroll();
                }
                bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                    @Override
                    public void onItemClick(int position) {
                        if(!Util.isEmpty(dCionShopBean.getData().getAdResource().getPicsUrl().get(position))){
                            Util.urlAction(mContext,dCionShopBean.getData().getAdResource().getPicsUrl().get(position));
                        }
                    }
                });
                break;
            case 2:
                DCoinShopActionHolder dCoinShopActionHolder = (DCoinShopActionHolder) holder;
                if( point>=0 ){
                    dCoinShopActionHolder.tvDcoinAmount.setText("D币 "+point+"");
                }else  {
                    dCoinShopActionHolder.tvDcoinAmount.setText("D币 ");
                 }
                 if( Util.loginChecked((Activity) mContext,999)){
                     dCoinShopActionHolder.tvDcoinAmount.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             Util.urlAction(mContext, "/page/mudb", true);
                         }
                     });
                 }

                dCoinShopActionHolder.tvRecordConversion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext,DCoinRecordConversionActivity.class));
                    }
                });
                break;
        }
    }
    public void setdCionShopBean(DCionShopBean dCionShopBean) {
        this.dCionShopBean = dCionShopBean;
    }


    @Override
    public int getItemViewType(int position) {
        if (position==0 && dCionShopBean!=null && dCionShopBean.getData().getAdResource().getPics()!=null && dCionShopBean.getData().getAdResource().getPics().size()>0){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        if(dCionShopBean==null || dCionShopBean.getData().getAdResource().getPics()==null ||dCionShopBean.getData().getAdResource().getPics().size()==0){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    public void setPoint(int point) {
        this.point=point;
    }
}
