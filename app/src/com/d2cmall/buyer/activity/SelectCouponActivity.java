package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.OrderConfirmBean;
import com.d2cmall.buyer.util.AnimUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectCouponActivity extends BaseActivity implements ObjectBindAdapter.ViewBinder<OrderConfirmBean.DataEntity.CouponsEntity> {

    @Bind(R.id.valid_coupon)
    TextView validCoupon;
    @Bind(R.id.un_valid_coupon)
    TextView unValidCoupon;
    @Bind(R.id.m_list_view)
    ListView mListView;
    @Bind(R.id.img_cursor)
    ImageView imgCursor;
    @Bind(R.id.reason)
    TextView reasonTv;
    private List<OrderConfirmBean.DataEntity.CouponsEntity> listEntities;
    private List<OrderConfirmBean.DataEntity.CouponsEntity> dislistEntities;
    private ObjectBindAdapter<OrderConfirmBean.DataEntity.CouponsEntity> adapter;
    private String checkCode,reason;
    boolean isDis;
    private int offer;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_select_coupon);
        listEntities = (List<OrderConfirmBean.DataEntity.CouponsEntity>) getIntent().getSerializableExtra("coupon");
        dislistEntities = (List<OrderConfirmBean.DataEntity.CouponsEntity>) getIntent().getSerializableExtra("discoupon");
        checkCode = getIntent().getStringExtra("code");
        reason=getIntent().getStringExtra("reason");
        if (listEntities != null && listEntities.size() > 0) {
            validCoupon.setText(String.format(getString(R.string.label_valid_coupon), listEntities.size()));
        } else {
            listEntities = new ArrayList<>();
            validCoupon.setText(String.format(getString(R.string.label_valid_coupon), 0));
        }
        setAdapter(listEntities);
        if (dislistEntities != null && dislistEntities.size() > 0) {
            unValidCoupon.setText(String.format(getString(R.string.label_unvalid_coupon), dislistEntities.size()));
        } else {
            dislistEntities = new ArrayList<>();
            unValidCoupon.setText(String.format(getString(R.string.label_unvalid_coupon), 0));
        }
        initCursor();
        initListener();
    }

    private void initCursor() {
        Rect rect = new Rect();
        validCoupon.getPaint().getTextBounds(validCoupon.getText().toString(), 0, validCoupon.getText().toString().length(), rect);
        Point outSize = Util.getDeviceSize(this);
        offer = outSize.x / 4 - rect.width() / 2;
        RectShape shape = new RectShape();
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(getResources().getColor(R.color.color_black_bg));
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.setIntrinsicWidth(rect.width());
        drawable.setIntrinsicHeight(Util.dip2px(this, 1));
        imgCursor.setImageDrawable(drawable);
        Matrix matrix = new Matrix();
        matrix.postTranslate(offer, 0);
        imgCursor.setImageMatrix(matrix);
    }

    private void selectCursor(int index) {
        Animation animation = new TranslateAnimation(currentIndex * (Util.getDeviceSize(this).x / 2), index * (Util.getDeviceSize(this).x / 2), 0, 0);
        currentIndex = index;
        animation.setFillAfter(true);
        animation.setDuration(200);
        imgCursor.startAnimation(animation);
    }

    private void initListener() {
        validCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonTv.setVisibility(View.GONE);
                isDis = false;
                validCoupon.setTextColor(getResources().getColor(R.color.color_black87));
                unValidCoupon.setTextColor(getResources().getColor(R.color.color_black38));
                setAdapter(listEntities);
                selectCursor(0);
            }
        });
        unValidCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isEmpty(reason)){
                    reasonTv.setVisibility(View.VISIBLE);
                    reasonTv.setText(reason);
                }
                isDis = true;
                unValidCoupon.setTextColor(getResources().getColor(R.color.color_black87));
                validCoupon.setTextColor(getResources().getColor(R.color.color_black38));
                setAdapter(dislistEntities);
                selectCursor(1);
            }
        });
    }

    private void setAdapter(List<OrderConfirmBean.DataEntity.CouponsEntity> list) {
        if (isDis) {
            adapter = new ObjectBindAdapter<>(this, list, R.layout.list_item_coupon_g, this);
        } else {
            adapter = new ObjectBindAdapter<>(this, list, R.layout.list_item_coupon, this);
        }
        mListView.setAdapter(adapter);
    }

    @Override
    public void setViewValue(View view, final OrderConfirmBean.DataEntity.CouponsEntity listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        if ("DISCOUNT".equals(listEntity.getType())) {
            holder.priceLayout.setVisibility(View.GONE);
            holder.tvDiscount.setVisibility(View.VISIBLE);
            double amount = (double) listEntity.getAmount() / 10;
            holder.tvDiscount.setText(getString(R.string.label_discount, Util.getNumberFormat(amount, false)));
        } else {
            holder.priceLayout.setVisibility(View.VISIBLE);
            holder.tvDiscount.setVisibility(View.GONE);
            holder.tvPrice.setText(String.valueOf(listEntity.getAmount()));
        }
        holder.tvLimit.setText(String.format(getString(R.string.label_need_amount), listEntity.getNeedAmount()));
        holder.tvTitle.setText(listEntity.getName());
        holder.tvDate.setText(String.format(getString(R.string.label_pozhe), listEntity.getEnabledate(), listEntity.getExpiredate()));
        holder.tvDescribe.setText(listEntity.getRemark());
        holder.imgArrow.clearAnimation();
        holder.describeLayout.setVisibility(View.GONE);
        holder.tvLabel.setText(R.string.label_view_detail);
        holder.imgArrow.setRotation(0);
        holder.viewDetailLayout.setOnClickListener(new DescribeClickListener(listEntity, holder.tvLabel,
                holder.imgArrow, holder.describeLayout));
        if (checkCode != null && listEntity.getCode().equals(checkCode)) {
            ImageView tagiv = new ImageView(this);
            tagiv.setTag("tag");
            tagiv.setImageResource(R.mipmap.ic_checked);
            tagiv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-2, -2);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rl.addRule(RelativeLayout.CENTER_VERTICAL);
            rl.setMargins(0, 0, 20, 0);
            holder.contentRl.addView(tagiv, rl);
        } else {
            View v = holder.contentRl.findViewWithTag("tag");
            if (v != null) holder.contentRl.removeView(v);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDis) {
                    if (checkCode != null && listEntity.getCode().equals(checkCode)) {
                        Intent intent = new Intent();
                        intent.putExtra("used", false);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("used", true);
                        intent.putExtra("type", listEntity.getType());
                        intent.putExtra("amount", listEntity.getAmount());
                        intent.putExtra("code", listEntity.getCode());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }

    private class DescribeClickListener implements View.OnClickListener {

        private OrderConfirmBean.DataEntity.CouponsEntity listEntity;
        private TextView tvLabel;
        private ImageView imgArrow;
        private View describeLayout;

        public DescribeClickListener(OrderConfirmBean.DataEntity.CouponsEntity listEntity, TextView tvLabel,
                                     ImageView imgArrow, View describeLayout) {
            super();
            this.listEntity = listEntity;
            this.tvLabel = tvLabel;
            this.imgArrow = imgArrow;
            this.describeLayout = describeLayout;
        }

        @Override
        public void onClick(View v) {
            imgArrow.setRotation(0);
            if (imgArrow.getAnimation() != null && !imgArrow.getAnimation().hasEnded()) {
                return;
            }
            if (describeLayout.getVisibility() == View.VISIBLE) {
                tvLabel.setText(R.string.label_view_detail);
                imgArrow.startAnimation(AnimUtil.getAnimArrowDown(SelectCouponActivity.this));
                describeLayout.setVisibility(View.GONE);
            } else {
                tvLabel.setText(R.string.label_retract_detail);
                imgArrow.startAnimation(AnimUtil.getAnimArrowUp(SelectCouponActivity.this));
                describeLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    static class ViewHolder {
        @Bind(R.id.content_rl)
        RelativeLayout contentRl;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.price_layout)
        LinearLayout priceLayout;
        @Bind(R.id.tv_discount)
        TextView tvDiscount;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_limit)
        TextView tvLimit;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.view_detail_layout)
        LinearLayout viewDetailLayout;
        @Bind(R.id.tv_describe)
        TextView tvDescribe;
        @Bind(R.id.describe_layout)
        LinearLayout describeLayout;
        @Bind(R.id.img_arrow)
        ImageView imgArrow;
        @Bind(R.id.tv_label)
        TextView tvLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }
}
