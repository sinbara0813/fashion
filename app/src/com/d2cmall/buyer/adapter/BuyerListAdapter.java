package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PartnerPersonCenterActivity;
import com.d2cmall.buyer.bean.BuyerListBean;
import com.d2cmall.buyer.holder.BuyerListItemHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2018/4/17.
 */

public class BuyerListAdapter extends DelegateAdapter.Adapter<BuyerListItemHolder> {
    private Context context;
    private List<BuyerListBean.ListBean> list;
    private boolean isJump;

    public BuyerListAdapter(Context context, List<BuyerListBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //linearLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        linearLayoutHelper.setMarginLeft(ScreenUtil.dip2px(16));
        linearLayoutHelper.setMarginRight(ScreenUtil.dip2px(16));
        linearLayoutHelper.setMarginBottom(ScreenUtil.dip2px(16));
        linearLayoutHelper.setDividerHeight(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    public void setIsJump(boolean isJump) {
        this.isJump = isJump;
    }

    @Override
    public BuyerListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_buyer_item, parent, false);
        return new BuyerListItemHolder(itemView);
    }

    private void performanceFirstConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        int dan = builder.toString().indexOf("单");

        SpannableString spannableString = new SpannableString(builder.toString());

        if (yuan > 0) {
            RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.0f);
            spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.5f);
            spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (dan > 0) {
            RelativeSizeSpan sizeSpan22 = new RelativeSizeSpan(0.5f);
            spannableString.setSpan(sizeSpan22, dan, dan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (dan > 0 && yuan > 0) {
            RelativeSizeSpan sizeSpan11 = new RelativeSizeSpan(1.0f);
            spannableString.setSpan(sizeSpan11, yuan + 1, dan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannableString);
    }

    private String wipePoint(double amount) {
        String s = String.valueOf(amount);
        int index = s.indexOf(".");
        if (index > 0 && s.length() - index > 3) {
            int a = (int) (amount * 100 + 0.5);
            return String.valueOf((double) a / 100);
        } else {
            return String.valueOf(amount);
        }
    }


    @Override
    public void onBindViewHolder(BuyerListItemHolder holder, int position) {
        final BuyerListBean.ListBean bean = list.get(position);
        UniversalImageLoader.displayRoundImage(context, bean.getHeadPic(), holder.headIv, R.mipmap.ic_default_avatar);
        holder.tvName.setText(bean.getName());
        if (bean.getLevel() == 1) {//为DM
            holder.ivIdentity.setImageResource(R.mipmap.icon_dm);
            holder.tvNoticeLeft.setText("零售订单");
            holder.tvNoticeRight.setText("客户订单");

            performanceFirstConver(holder.tvLeftData, wipePoint(bean.getSaleStat().getPayAmount()) + "元 " + bean.getSaleStat().getCount() + "单");
            performanceFirstConver(holder.tvSellRight, wipePoint((bean.getSaleStatDM().getPayAmount() + bean.getSaleStatSDM().getPayAmount())) + "元 " + (bean.getSaleStatDM().getCount() + bean.getSaleStatSDM().getCount()) + "单");
        } else {//买手
            holder.ivIdentity.setImageResource(R.mipmap.icon_buyer);
            holder.tvNoticeLeft.setText("零售订单");
            performanceFirstConver(holder.tvLeftData, bean.getSaleStat().getCount() + "单");
            holder.tvNoticeRight.setText("零售金额");
            performanceFirstConver(holder.tvSellRight, wipePoint(bean.getSaleStat().getPayAmount()) + "元");
        }
        if (bean.getStatusX() == 0) {
            holder.ivTry.setVisibility(View.VISIBLE);
        } else {
            holder.ivTry.setVisibility(View.GONE);
        }

        if (bean.getStatusX() == -1) {
            holder.ivClose.setVisibility(View.VISIBLE);
        } else {
            holder.ivClose.setVisibility(View.GONE);
        }
        if (!Util.isEmpty(bean.getCreateDate())) {
            holder.tvJoinTime.setText(String.format(context.getString(R.string.buyer_join_time), DateUtil.stampToDate(bean.getCreateDate())));
        }
        if (!Util.isEmpty(bean.getConsumeDate())) {
            holder.tvSellTime.setText(String.format(context.getString(R.string.buyer_last_sell_time), DateUtil.stampToDate(bean.getConsumeDate())));
        }
        if (bean.getPayAmount() > 0) {
            holder.tvSign.setVisibility(View.VISIBLE);
        } else {
            holder.tvSign.setVisibility(View.GONE);
        }
        StringBuilder builder;
        SpannableString spannableString;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#f1951c"));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
        if (bean.getInviteDM() > 0) {
            builder = new StringBuilder(String.format(context.getString(R.string.buyer_invite_num_has_dm), String.valueOf(bean.getInviteNum()), String.valueOf(bean.getInviteDM())));
            int firstNumSize = String.valueOf(bean.getInviteBuyer()).length();
            int secondNumSize = String.valueOf(bean.getInviteDM()).length();
            spannableString = new SpannableString(builder.toString());

            spannableString.setSpan(colorSpan, 3, 3 + firstNumSize, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(sizeSpan, 3, 3 + firstNumSize, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#f1951c"));
            RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(1.5f);

            spannableString.setSpan(colorSpan2, 12 + firstNumSize, 12 + firstNumSize + secondNumSize, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(sizeSpan2, 12 + firstNumSize, 12 + firstNumSize + secondNumSize, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            builder = new StringBuilder(String.format(context.getString(R.string.buyer_invite_num), String.valueOf(bean.getInviteNum())));
            spannableString = new SpannableString(builder.toString());

            int firstNumSize = String.valueOf(bean.getInviteBuyer()).length();
            spannableString.setSpan(colorSpan, 3, 3 + firstNumSize, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(sizeSpan, 3, 3 + firstNumSize, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        holder.tvGrade.setText(spannableString);

        if (isJump) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PartnerPersonCenterActivity.class);
                    intent.putExtra("id", Long.valueOf(bean.getPartnerId()));
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
