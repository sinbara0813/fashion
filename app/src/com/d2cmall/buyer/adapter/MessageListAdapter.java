package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.MessageListBean;
import com.d2cmall.buyer.holder.ActivityViewHolder;
import com.d2cmall.buyer.holder.AppointmentViewHolder;
import com.d2cmall.buyer.holder.BrandHolder;
import com.d2cmall.buyer.holder.LogisticsViewHolder;
import com.d2cmall.buyer.holder.MessageFeedBackHolder;
import com.d2cmall.buyer.holder.MessagePropertyViewHolder;
import com.d2cmall.buyer.holder.MessageSquareViewHolder;
import com.d2cmall.buyer.holder.OrderViewHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.igexin.push.core.a.i;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/24 10:16
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 二级消息适配器
 */
public class MessageListAdapter extends DelegateAdapter.Adapter {
    private static final int FEEDBACK_TYPE = 26;
    private int count = 3;
    private Context context;
    private int majorType;
    //    private MessageListBean.DataBean.MessagesBean.ListBean messages;
    private List<MessageListBean.DataBean.MessagesBean.ListBean> mMessageList;
    //全局消息已删除
    private ArrayList<Long> globalDelete;
    //全局消息已读
    private ArrayList<Long> globalRead;
    private final int LOGISTICS_TYPE = 10; //物流通知(发货到货)
    private final int ORDER_TYPE = 21; //订单,退款退货,换货,直播,调拨单
    private final int APPOINTMENT_TYPE = 27; //预约
    private final int OTHER_TYPE = 29; //其他
    private final int WALLET_TYPE = 31; //icon_message_wallet,优惠券,资产
    private final int SQURE_TYPE = 44; //关注,回复,评论
    private final int GOODS_NOTIFY_TYPE = 51; //咨询回复,开抢,到货
    private final int ACTIVITY_YYPE = 61; //活动精选
    private final int DESIGNER_NEW = 71; //设计师上新
    private final int BRAND_RECOMMEN = 72; //品牌推荐
    private final int PARTNER_TYPE = 80; //品牌推荐

    public MessageListAdapter(Context context, int majorType, List<MessageListBean.DataBean.MessagesBean.ListBean> mMessageList) {
        this.context = context;
        this.majorType = majorType;
        this.mMessageList = mMessageList;

        globalDelete = new ArrayList<>();
        globalRead = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case FEEDBACK_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_complain_back_item, parent, false);
                return new MessageFeedBackHolder(view);

            case LOGISTICS_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_logistics_item, parent, false);
                return new LogisticsViewHolder(view);

            case ORDER_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_notification_item, parent, false);
                return new OrderViewHolder(view);

            case WALLET_TYPE://钱包自产
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_property_item, parent, false);
                return new MessagePropertyViewHolder(view);

            case SQURE_TYPE: //关注,回复,评论:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_square_item, parent, false);
                return new MessageSquareViewHolder(view);

            case GOODS_NOTIFY_TYPE://咨询回复,开抢,到货
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_logistics_item, parent, false);
                return new LogisticsViewHolder(view);

            case ACTIVITY_YYPE://活动精选
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_activities_item, parent, false);
                return new ActivityViewHolder(view);

            case DESIGNER_NEW://设计师上新
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_brand_item, parent, false);
                return new BrandHolder(view);

            case BRAND_RECOMMEN:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_activities_item, parent, false);
                return new ActivityViewHolder(view);

            case APPOINTMENT_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_notification_shop_item, parent, false);
                return new AppointmentViewHolder(view);

            case OTHER_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_property_item, parent, false);
                return new MessagePropertyViewHolder(view);

            case PARTNER_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_property_item, parent, false);
                return new MessagePropertyViewHolder(view);

            default:
                view = LayoutInflater.from(context).inflate(R.layout.layout_message_logistics_item, parent, false);
                return new LogisticsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MessageListBean.DataBean.MessagesBean.ListBean listBean = mMessageList.get(position);
        int type = listBean.getType();
        MessagePropertyViewHolder messagePropertyViewHolder;
        switch (type) {
            case 11://发货提醒
            case 12://到货提醒
                LogisticsViewHolder logisticsViewHolder= (LogisticsViewHolder) holder;
                logisticsViewHolder.mTextView.setText(listBean.getTitle());
                logisticsViewHolder.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), logisticsViewHolder.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 13://到货提醒
                AppointmentViewHolder appointmentViewHolder = (AppointmentViewHolder) holder;
                appointmentViewHolder.mTextView.setText(listBean.getTitle());
                appointmentViewHolder.mDetailtextView.setText(listBean.getContent());
                appointmentViewHolder.mTextSeemore.setText("点击进入-查看详情");
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                OrderViewHolder orderViewHolder2= (OrderViewHolder) holder;
                orderViewHolder2.mTextView.setText(listBean.getTitle());
                orderViewHolder2.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), orderViewHolder2.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 26:
                MessageFeedBackHolder messageFeedBackHolder= (MessageFeedBackHolder) holder;
                messageFeedBackHolder.mTextView.setText(listBean.getTitle());
                messageFeedBackHolder.mDetailtextView.setText(listBean.getContent());
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 27:
                AppointmentViewHolder appointmentViewHolder1 = (AppointmentViewHolder) holder;
                appointmentViewHolder1.mTextView.setText(listBean.getTitle());
                appointmentViewHolder1.mDetailtextView.setText(listBean.getContent());
                appointmentViewHolder1.mTextSeemore.setText("点击进入-我的预约");
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 28:
                OrderViewHolder orderViewHolder= (OrderViewHolder) holder;
                orderViewHolder.mTextView.setText(listBean.getTitle());
                orderViewHolder.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), orderViewHolder.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 29:
                //系统通知
                LogisticsViewHolder logisticsViewHolder1= (LogisticsViewHolder) holder;
                logisticsViewHolder1.mTextView.setText(listBean.getTitle());
                logisticsViewHolder1.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), logisticsViewHolder1.mImageView,R.mipmap.ic_logo_empty5);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 31:
            case 32:
                //优惠券提醒
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_mesage_coupon));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 33:
            case 34:
                //钱包消费
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_wallet));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 35:
                //红包
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_mesage_redpacket));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 41:
            case 42:
            case 43:
                MessageSquareViewHolder messageSquareHolder = (MessageSquareViewHolder) holder;
                messageSquareHolder.tvActionType.setText(listBean.getOther().getActive());
                messageSquareHolder.mContenttextView.setVisibility(View.GONE);
                if (listBean.getOther()!=null){
                    messageSquareHolder.mTitletextView.setText(listBean.getOther().getNickName());
                    UniversalImageLoader.displayImage(context, listBean.getOther().getHeadPic(), messageSquareHolder.mImageHead);
                    if(listBean.getOther().getSharePic()!=null&&listBean.getOther().getSharePic().contains(",")) {
                        String[] split = listBean.getOther().getSharePic().split(",");
                        UniversalImageLoader.displayImage(context, split[0], messageSquareHolder.mImagePic);
                    }else{
                        UniversalImageLoader.displayImage(context, listBean.getOther().getSharePic(), messageSquareHolder.mImagePic);
                    }

                }
                messageSquareHolder.mTimetextView.setText(DateUtil.getFriendlyTime7(listBean.getCreateDate()));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());

                break;
            case 44:
                //广场动态
                MessageSquareViewHolder messageSquareHolder1 = (MessageSquareViewHolder) holder;
                messageSquareHolder1.tvActionType.setText(listBean.getOther().getActive());
                messageSquareHolder1.mContenttextView.setText(listBean.getOther().getContent());
                messageSquareHolder1.mContenttextView.setVisibility(View.VISIBLE);
                if (listBean.getOther()!=null){
                    messageSquareHolder1.mTitletextView.setText(listBean.getOther().getNickName());
                    UniversalImageLoader.displayImage(context, listBean.getOther().getHeadPic(), messageSquareHolder1.mImageHead);
                    if(listBean.getOther().getSharePic()!=null&&listBean.getOther().getSharePic().contains(",")) {
                        String[] split = listBean.getOther().getSharePic().split(",");
                        UniversalImageLoader.displayImage(context, split[0], messageSquareHolder1.mImagePic);
                    }else{
                        UniversalImageLoader.displayImage(context, listBean.getOther().getSharePic(), messageSquareHolder1.mImagePic);
                    }

                }
                messageSquareHolder1.mTimetextView.setText(DateUtil.getFriendlyTime7(listBean.getCreateDate()));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 45://直播
                LogisticsViewHolder logisticsViewHolder4= (LogisticsViewHolder) holder;
                logisticsViewHolder4.mTextView.setText(listBean.getTitle());
                logisticsViewHolder4.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), logisticsViewHolder4.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 46://咨询回复
                LogisticsViewHolder logisticsViewHolder3= (LogisticsViewHolder) holder;
                logisticsViewHolder3.mTextView.setText(listBean.getTitle());
                logisticsViewHolder3.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), logisticsViewHolder3.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 51:
            case 52:
                //商品提醒
                LogisticsViewHolder logisticsViewHolder2= (LogisticsViewHolder) holder;
                logisticsViewHolder2.mTextView.setText(listBean.getTitle());
                logisticsViewHolder2.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), logisticsViewHolder2.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 61:
                //活动精选
                ActivityViewHolder activityViewHolder1= (ActivityViewHolder) holder;
                activityViewHolder1.mTextView.setText(listBean.getTitle());
                if(TextUtils.isEmpty(listBean.getContent())){
                    activityViewHolder1.mDetailtextView.setVisibility(View.GONE);
                }else{
                    activityViewHolder1.mDetailtextView.setVisibility(View.VISIBLE);
                    activityViewHolder1.mDetailtextView.setText(listBean.getContent());
                }
                UniversalImageLoader.displayImage(context, listBean.getPic(), activityViewHolder1.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 71:
                BrandHolder brandHolder = (BrandHolder) holder;
                String picUrl = listBean.getPic();
                //避免重复添加图片
                if (brandHolder.mLinearLayout.getChildCount() > 0) {
                    brandHolder.mLinearLayout.removeAllViews();
                }
                //动态计算条目的图片数目,多张图片的url是以","分割的
                if (picUrl != null && picUrl.contains(",")) {
                    String[] picUrls = picUrl.split(",");
                    initImage(picUrls.length, brandHolder.mLinearLayout);
                    for (int i = 0; i < 3; i++) {
                        UniversalImageLoader.displayImage(context, picUrls[i], (ImageView) brandHolder.mLinearLayout.getChildAt(i));
                    }
                    brandHolder.mTextSeemore.setText(R.string.label_see_detial);
                } else if (picUrl != null) {
                    initImage(1, brandHolder.mLinearLayout);
                    UniversalImageLoader.displayImage(context, picUrl, (ImageView) brandHolder.mLinearLayout.getChildAt(i));
                    brandHolder.mMessageBrandArrow.setVisibility(View.GONE);
                    brandHolder.mTextSeemore.setText(listBean.getContent());
                }
                brandHolder.mTextView.setText(listBean.getTitle());
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 72:
                ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;
                activityViewHolder.mTextView.setText(listBean.getTitle());

                if(TextUtils.isEmpty(listBean.getContent())){
                    activityViewHolder.mDetailtextView.setVisibility(View.GONE);
                }else{
                    activityViewHolder.mDetailtextView.setVisibility(View.VISIBLE);
                    activityViewHolder.mDetailtextView.setText(listBean.getContent());
                }
                UniversalImageLoader.displayImage(context, listBean.getPic(), activityViewHolder.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 80://买手中心的消息类型
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_withdrawsuccess));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
            case 81:   //  81.提现单提醒
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                if(listBean.getOther()!=null && listBean.getOther().getSuccess()==1){
                    messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_withdrawsuccess));
                }else{
                    messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_withdrawfailure));
                }
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 82:    //82.返利单提醒
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_rebate));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 83:    // 83.账户流水
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_accountdata));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 84:    //84.访客提醒
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_visitor));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 85:    //84.邀请注册提醒
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_invitesign));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 86://关店
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_closedshop));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            case 87:
            case 88:
            case 89:    //89.其他
                messagePropertyViewHolder = (MessagePropertyViewHolder) holder;
                messagePropertyViewHolder.mTextView.setText(listBean.getTitle());
                messagePropertyViewHolder.mDetailtextView.setText(listBean.getContent());
                messagePropertyViewHolder.mTvMoneyNum.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_message_buyerother));
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
            default:
                //其它
                LogisticsViewHolder logisticsViewHolder5= (LogisticsViewHolder) holder;
                logisticsViewHolder5.mTextView.setText(listBean.getTitle());
                logisticsViewHolder5.mDetailtextView.setText(listBean.getContent());
                UniversalImageLoader.displayImage(context, listBean.getPic(), logisticsViewHolder5.mImageView);
                holder.itemView.setTag(R.id.item_time, listBean.getCreateDate());
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.urlAction(context, listBean.getUrl());
            }
        });
    }



    @Override
    public int getItemViewType(int position) {
        MessageListBean.DataBean.MessagesBean.ListBean bean = mMessageList.get(position);
        int type = bean.getType();
        switch (type) {
            case 11:
            case 12:
                //物流通知
                return LOGISTICS_TYPE;
            case 13:        //采购单
                return APPOINTMENT_TYPE;
            case 21://订单
            case 22:
            case 23:
            case 24:
            case 25:
                return ORDER_TYPE;
            case 26:
                return FEEDBACK_TYPE;
            case 27:    //门店预约
                return APPOINTMENT_TYPE;
            case 28:    //调拨单
                return ORDER_TYPE;
            case 29:   //后台定义的其它(新增的一些消息类型可能就是29)
                return LOGISTICS_TYPE;
            case 31:
            case 32:
            case 33://icon_message_wallet,优惠券,资产
            case 34:
            case 35:
                //我的资产
                return WALLET_TYPE;
            case 41:
            case 42:
            case 43:
            case 44:    //41.点赞提醒 42.关注 43.关注用户发布买家秀提醒 44.买家秀评论和回复提醒
                return SQURE_TYPE;
            case 45://直播
                return LOGISTICS_TYPE;
            case 46://咨询回复
                return LOGISTICS_TYPE;
            case 51://开抢
            case 52://货到
                return GOODS_NOTIFY_TYPE;
            case 61: //活动精选
                return ACTIVITY_YYPE;
            case 71://设计师上新
                return DESIGNER_NEW;
            case 72://品牌推荐
                return BRAND_RECOMMEN;
//            类型 买手服务消息 80~89
//            81.提现单提醒 82.返利单提醒 83.账户流水 84.访客提醒 85.邀请注册提醒 89.其他
//
//                    跳转
//            我的提现/to/partner/cash 我的团队/to/partner/team 售货明细 /to/partner/sales
//            账号明细 /to/partner/account/item 买手店铺 /to/partner/store
//            我的买手 /to/partner/mine
            case 80://买手中心的消息类型
            case 81:   //  81.提现单提醒
            case 82:    //82.返利单提醒
            case 83:    // 83.账户流水
            case 84:    //84.访客提醒
            case 85:    //84.邀请注册提醒
            case 86:
            case 87:
            case 88:
            case 89:    //89.其他
                return PARTNER_TYPE;
            default:
                return LOGISTICS_TYPE;
        }

    }


    private void initImage(int count, LinearLayout layout) {
        for (int i = 0; i < 3; i++) {
            ImageView iv1 = new ImageView(context);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, ScreenUtil.dip2px(144), 1.0f);
            if (i < count - 1) {
                params1.rightMargin = ScreenUtil.dip2px(4);
            }
            iv1.setLayoutParams(params1);
            iv1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv1.setImageResource(R.mipmap.ic_page1);
            layout.addView(iv1);
        }
    }

    @Override
    public int getItemCount() {
        return   mMessageList==null?0:mMessageList.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMarginBottom(32);
        return linearLayoutHelper;
    }
}
