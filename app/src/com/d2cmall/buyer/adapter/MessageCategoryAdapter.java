package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.MessageListActivity;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.MessageCategoryBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/08 10:17
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageCategoryAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private List<MessageCategoryBean.DataBean.MessagesBean> messages;
    private int MESSAGE_CUSTOM_TYPE = 1;
    String messageType = null;
    private long mUserId;

    public MessageCategoryAdapter(Context context, List<MessageCategoryBean.DataBean.MessagesBean> messages) {
        this.context = context;
        this.messages = messages;
        mUserId = Session.getInstance().getUserFromFile(context).getId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_CUSTOM_TYPE) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_message_custom_item, parent, false);
            return new CustomViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_message_list_item, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position==0){//客服
            final CustomViewHolder customViewHolder = (CustomViewHolder) holder;
            customViewHolder.mTvCustom.setText("客服");
            String recentCustomMsgContent = D2CApplication.mSharePref.getSharePrefString("recentCustomMsgContent", "");
            if(Util.isEmpty(recentCustomMsgContent)){
                customViewHolder.mTvCustomTime.setText("在线咨询时间为 9:00-24:00");
            }else{
                customViewHolder.mTvCustomTime.setText(recentCustomMsgContent);
            }
            boolean hasQiYuUnreadMsg = D2CApplication.mSharePref.getSharePrefBoolean("hasQiYuUnreadMsg", false);
            if(hasQiYuUnreadMsg){
                customViewHolder.messageUnreadCount.setVisibility(View.VISIBLE);
            }else{
                customViewHolder.messageUnreadCount.setVisibility(View.GONE);
            }
            customViewHolder.mRlCustomService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customViewHolder.messageUnreadCount.setVisibility(View.GONE);
                    D2CApplication.mSharePref.putSharePrefBoolean("hasQiYuUnreadMsg",false);
                    toChat();
                }
            });
        }else {//其它
            if (position <= messages.size()) {
                final MessageCategoryBean.DataBean.MessagesBean messagesBean = messages.get(position - 1);
                final ViewHolder messageCategoryHolder = (ViewHolder) holder;
                int type = messagesBean.getMajorType();
                switch (type) {
                    case 1:
                        //物流通知
                        messageType = SharePrefConstant.GLOBAL_LOGISTICS_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_logistics);
                        break;
                    case 2:
                        //系统通知
                        messageType = SharePrefConstant.GLOBAL_SYSTEM_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_system);
                        break;
                    case 3:
                        //我的资产
                        messageType = SharePrefConstant.GLOBAL_ASSET_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_asset);
                        break;
                    case 4:
                        //广场动态
                        messageType = SharePrefConstant.GLOBAL_SQURE_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_dynamic);
                        break;
                    case 5:
                        //商品提醒
                        messageType = SharePrefConstant.GLOBAL_PRODUCT_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_goods);
                        break;
                    case 6:
                        //活动精选
                        messageType = SharePrefConstant.GLOBAL_ACTIVITY_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_activity);
                        break;
                    case 7:
                    //品牌推荐
                        messageType = SharePrefConstant.GLOBAL_BRAND_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_brand);
                        break;
                    case 8:
                        //品牌推荐
                        messageType = SharePrefConstant.GLOBAL_PARTNER_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_buyer);
                        break;
                    default:
                        //默认
                        messageType = SharePrefConstant.GLOBAL_OTHER_READED_NUM;
                        messageCategoryHolder.mServiceIv.setImageResource(R.mipmap.icon_message_other);
                            break;
                }
                if (messageType != null) {

                    int unReadCount = messagesBean.getUnReadCount();
                    if (unReadCount < 1) {
                        messageCategoryHolder.mMessageCount.setVisibility(View.GONE);
                    } else {
                        messageCategoryHolder.mMessageCount.setVisibility(View.VISIBLE);
                        if (unReadCount > 9) {
                            messageCategoryHolder.mMessageCount.setText("9+");
                            messageCategoryHolder.mMessageCount.setBackgroundResource(R.drawable.message_double_back);
                        } else {
                            messageCategoryHolder.mMessageCount.setText(unReadCount + "");
                            messageCategoryHolder.mMessageCount.setBackgroundResource(R.drawable.message_single_back);
                        }
                    }

                }
                messageCategoryHolder.mMessageName.setText(messagesBean.getTypeName() + "");
                messageCategoryHolder.mMessageItemContent.setText(messagesBean.getTitle() + "");
                messageCategoryHolder.mMessageItemTime.setText(DateUtil.getFriendlyTime5(messagesBean.getCreateDate()));
                messageCategoryHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int majorType = messagesBean.getMajorType();
                        switch (majorType) {
                            case 1:
                                //物流通知
                                messageType = SharePrefConstant.GLOBAL_LOGISTICS_READED_NUM;
                                break;
                            case 2:
                                //系统通知
                                messageType = SharePrefConstant.GLOBAL_SYSTEM_READED_NUM;
                                break;
                            case 3:
                                //我的资产
                                messageType = SharePrefConstant.GLOBAL_ASSET_READED_NUM;
                                break;
                            case 4:
                                //广场动态
                                messageType = SharePrefConstant.GLOBAL_SQURE_READED_NUM;
                                break;
                            case 5:
                                //商品提醒
                                messageType = SharePrefConstant.GLOBAL_PRODUCT_READED_NUM;
                                break;
                            case 6:
                                //活动精选
                                D2CApplication.mSharePref.putSharePrefLong(SharePrefConstant.MESSAGE_YYPE6,System.currentTimeMillis());
                                messageType = SharePrefConstant.GLOBAL_ACTIVITY_READED_NUM;
                                break;
                            case 7:
                                //品牌推荐
                                D2CApplication.mSharePref.putSharePrefLong(SharePrefConstant.MESSAGE_YYPE7,System.currentTimeMillis());
                                messageType = SharePrefConstant.GLOBAL_BRAND_READED_NUM;
                                break;
                            case 8:
                                //买手中心
                                messageType = SharePrefConstant.GLOBAL_PARTNER_READED_NUM;
                                break;
                            default:
                                //其它
                                messageType = SharePrefConstant.GLOBAL_OTHER_READED_NUM;
                                break;
                        }
                        D2CApplication.mSharePref.putSharePrefInteger(messageType+mUserId, messagesBean.getUnReadCount());
                        //存储全部未读消息数
                        countAllReadedMessageNum();
                        //隐藏未读消息数
                        messageCategoryHolder.mMessageCount.setVisibility(View.GONE);
                        ActionBean actionBean = new ActionBean(Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE);
                        actionBean.put("readCount",messagesBean.getUnReadCount());
                        EventBus.getDefault().post(actionBean);
                        SimpleApi api=new SimpleApi();
                        api.setInterPath(String.format(Constants.MESSAGE_READ,majorType));
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                Intent intent = new Intent(context,
                                        MessageListActivity.class);
                                intent.putExtra("majorType", messagesBean.getMajorType());
                                intent.putExtra("typeName", messagesBean.getTypeName());
                                context.startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Intent intent = new Intent(context, MessageListActivity.class);
                                intent.putExtra("majorType", messagesBean.getMajorType());
                                intent.putExtra("typeName", messagesBean.getTypeName());
                                context.startActivity(intent);
                            }
                        });

                    }
                });
            }
        }
    }
    private void countAllReadedMessageNum(){
        int sharePrefLong=0;
        String[] messageTypes=new String[]{SharePrefConstant.GLOBAL_LOGISTICS_READED_NUM,SharePrefConstant.GLOBAL_SYSTEM_READED_NUM,SharePrefConstant.GLOBAL_ASSET_READED_NUM,
                SharePrefConstant.GLOBAL_SQURE_READED_NUM,SharePrefConstant.GLOBAL_PRODUCT_READED_NUM,SharePrefConstant.GLOBAL_ACTIVITY_READED_NUM,
                SharePrefConstant.GLOBAL_BRAND_READED_NUM};
        for(int i=0; i< messageTypes.length ; i++){
            sharePrefLong+= D2CApplication.mSharePref.getSharePrefInteger(messageTypes[i], 0);
        }
        D2CApplication.mSharePref.putSharePrefInteger(SharePrefConstant.GLOBAL_ALL_READED_NUM, sharePrefLong);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MESSAGE_CUSTOM_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return messages.size() + 1;
    }

    private void toChat() {
        String title = "线上客服";
        String url = "http://www.d2cmall.com";
        ConsultSource source = new ConsultSource(url, title, "消息中心");
        source.groupId = Constants.QIYU_AF_GROUP_ID;
        source.robotFirst = true;
        Unicorn.openServiceActivity(context, "D2C客服", source);
        //合力亿捷
//        Intent intent = new Intent(context,CustomServiceActivity.class);
//        intent.putExtra("skillGroupId",Constants.HLYJ_BF_AF_GROUP_ID);
//        context.startActivity(intent);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.service_iv)
        ImageView mServiceIv;
        @Bind(R.id.message_count)
        TextView mMessageCount;
        @Bind(R.id.image_layout)
        RelativeLayout mImageLayout;
        @Bind(R.id.message_name)
        TextView mMessageName;
        @Bind(R.id.message_item_time)
        TextView mMessageItemTime;
        @Bind(R.id.message_item_content)
        TextView mMessageItemContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.service_iv)
        ImageView mServiceIv;
        @Bind(R.id.tv_custom)
        TextView mTvCustom;
        @Bind(R.id.tv_custom_time)
        TextView mTvCustomTime;
        @Bind(R.id.rl_custom_service)
        RelativeLayout mRlCustomService;
        @Bind(R.id.message_count)
        TextView messageUnreadCount;
        CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
