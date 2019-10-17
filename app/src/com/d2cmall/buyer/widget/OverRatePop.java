package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BrandListAdapter;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.bean.KaoLaConfirmBean;
import com.d2cmall.buyer.bean.OrderConfirmBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/6/7.
 */

public class OverRatePop extends PopupWindow {
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.scroll_view)
    MaxHeightScrollView scrollView;
    private Context context;
    private View rootView;
    private View.OnClickListener onClickListener;


    public OverRatePop(Context context) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_over_rate_pop, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ScreenUtil.screenWidth);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable();
        this.setBackgroundDrawable(cd);
        this.setOutsideTouchable(false);
        this.setFocusable(false);
        setAnimationStyle(R.style.showPopupAnimation);
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }


    public void setData(OrderConfirmBean response, List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean> OverRateList) {
        llContent.removeAllViews();
        List<OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity> itemsEntityList = response.getData().getOrder().getItems();
        for (int i = 0; i < OverRateList.size(); i++) {
            View ItemRootView = LayoutInflater.from(context).inflate(R.layout.layout_over_rate_item, null);
            KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean packageListBean = OverRateList.get(i);
            TextView wareName = (TextView) ItemRootView.findViewById(R.id.tv_ware_house_name);
            RecyclerView recyclerView = (RecyclerView) ItemRootView.findViewById(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            wareName.setText(packageListBean.getWarehouse().getWarehouseName());

            List<OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity> data = new ArrayList<>();
            List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.GoodsListBean> goodsListBeanList = packageListBean.getGoodsList();
            for (int j = 0; j < goodsListBeanList.size(); j++) {
                KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.GoodsListBean goodsListBean = goodsListBeanList.get(j);
                for (int k = 0; k < itemsEntityList.size(); k++) {
                    OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity itemsEntity = itemsEntityList.get(k);
                    if (goodsListBean.getGoodsId().equals(itemsEntity.getProductSn())) {
                        data.add(itemsEntity);
                    }
                }
            }
            recyclerView.setAdapter(new MyAdapter(data));
            llContent.addView(ItemRootView);
        }
        scrollView.measure(0, 0);
        this.setHeight(scrollView.getMeasuredHeight() + ScreenUtil.dip2px(106));
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity> list2;

        public MyAdapter(List<OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity> list) {
            this.list2 = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_over_rate__product_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity productsEntity = list2.get(position);
            UniversalImageLoader.displayImage(context, productsEntity.getProductImg(), holder.imageView);
            holder.textView.setText("¥" + Util.getNumberFormat((productsEntity.getTotalPrice() - productsEntity.getPromotionAmount() - productsEntity.getOrderPromotionAmount()) / productsEntity.getQuantity()));
        }

        @Override
        public int getItemCount() {
            return list2.size();
        }
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

    @OnClick({R.id.button, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                if(onClickListener!=null){
                    onClickListener.onClick(view);
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
