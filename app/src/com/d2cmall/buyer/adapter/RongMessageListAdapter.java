package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LiveAnchorActivity;
import com.d2cmall.buyer.bean.PresentMessage;
import com.d2cmall.buyer.bean.WebMessage;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.rong.imlib.model.Message;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;

/**
 * 融云消息适配器
 * Author: hrb
 * Date: 2016/12/12 15:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RongMessageListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Message> messages;

    public RongMessageListAdapter(Context context, List<Message> messages) {
        this.mContext = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages == null ? 0 : messages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            TextView textView = new TextView(mContext);
            textView.setShadowLayer((float) 2.75, 0, 0, R.color.trans_50_color_black);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            AbsListView.LayoutParams al = new AbsListView.LayoutParams(-2, -2);
            textView.setLayoutParams(al);
            convertView = textView;
        }
        Message message = messages.get(position);
        if (message.getContent() instanceof TextMessage) {
            ((TextView) convertView).setTextColor(Color.WHITE);
            TextMessage textMessage = (TextMessage) message.getContent();
            StringBuilder builder = new StringBuilder();
            if (textMessage.getUserInfo() != null && !Util.isEmpty(textMessage.getUserInfo().getName())) {
                builder.append(textMessage.getUserInfo().getName());
                builder.append(":");
            } else {
                builder.append("匿名用户:");
            }
            builder.append(textMessage.getContent());
            SpannableString sb = new SpannableString(builder.toString());
            ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
            int index = builder.toString().lastIndexOf(":");
            sb.setSpan(span, 0, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
            ((TextView) convertView).setText(sb);
        } else if (message.getContent() instanceof InformationNotificationMessage) {
            InformationNotificationMessage notificationMessage = (InformationNotificationMessage) message.getContent();
            StringBuilder builder = new StringBuilder();
            if (LiveAnchorActivity.JOINCHARROOM.equals(notificationMessage.getMessage())) {
                ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                if (notificationMessage.getUserInfo() != null && !Util.isEmpty(notificationMessage.getUserInfo().getName())) {
                    int index;
                    index = notificationMessage.getUserInfo().getName().length();
                    builder.append(notificationMessage.getUserInfo().getName());
                    builder.append("进入了直播间");
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    SpannableString sb = new SpannableString(builder.toString());
                    ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                    sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) convertView).setText(sb);
                } else {
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    builder.append("围观群众入场啦");
                    ((TextView) convertView).setText(builder.toString());
                }
            } else if (LiveAnchorActivity.BUY.equals(notificationMessage.getMessage())) {
                ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                if (notificationMessage.getUserInfo() != null && !Util.isEmpty(notificationMessage.getUserInfo().getName()) && !Util.isEmpty(notificationMessage.getExtra())) {
                    int index;
                    index = notificationMessage.getUserInfo().getName().length();
                    String reg = "[\\u4e00-\\u9fa5]+";
                    int max=8;
                    if (!notificationMessage.getUserInfo().getName().matches(reg)){
                        max=12;
                    }
                    if (index>max){
                        String name=notificationMessage.getUserInfo().getName();
                        name=name.substring(0,8);
                        name+="...";
                        index=name.length();
                        builder.append(name);
                    }else {
                        builder.append(notificationMessage.getUserInfo().getName());
                    }
                    builder.append("购买了");
                    String extra = notificationMessage.getExtra();
                    String productName;
                    long productId;
                    try {
                        JSONObject jsonObject = new JSONObject(extra);
                        productName=jsonObject.optString("productName");
                        productId=jsonObject.optLong("productId");
                    } catch (JSONException e) {
                        productName="xxx";
                        e.printStackTrace();
                    }

                    if (productName.length()>17){
                        productName=productName.substring(0,17);
                        productName+="...";
                    }
                    builder.append(productName);
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    SpannableString sb = new SpannableString(builder.toString());
                    ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#fffdc33e"));
                    sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ForegroundColorSpan span1=new ForegroundColorSpan(Color.parseColor("#ef5285"));
                    sb.setSpan(span1,index+3,builder.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) convertView).setText(sb);
                }
            } else if (LiveAnchorActivity.ADDCART.equals(notificationMessage.getMessage())&& !Util.isEmpty(notificationMessage.getExtra())) {
                ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                if (notificationMessage.getUserInfo() != null && !Util.isEmpty(notificationMessage.getUserInfo().getName())) {
                    int index;
                    int index1;
                    index = notificationMessage.getUserInfo().getName().length();
                    String reg = "[\\u4e00-\\u9fa5]+";
                    int max=8;
                    if (!notificationMessage.getUserInfo().getName().matches(reg)){
                        max=12;
                    }
                    if (index>max){
                        String name=notificationMessage.getUserInfo().getName();
                        name=name.substring(0,8);
                        name+="...";
                        index=name.length();
                        builder.append(name);
                    }else {
                        builder.append(notificationMessage.getUserInfo().getName());
                    }
                    builder.append("将");
                    String extra = notificationMessage.getExtra();
                    String productName;
                    long productId;
                    try {
                        JSONObject jsonObject = new JSONObject(extra);
                        productName=jsonObject.optString("productName");
                        productId=jsonObject.optLong("productId");
                    } catch (JSONException e) {
                        productName="xxx";
                        e.printStackTrace();
                    }
                    if (productName.length()>20){
                        productName=productName.substring(0,20);
                        productName+="...";
                    }
                    builder.append(productName);
                    index1=builder.toString().length();
                    builder.append("加入购物车");
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    SpannableString sb = new SpannableString(builder.toString());
                    ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                    sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ForegroundColorSpan span1=new ForegroundColorSpan(Color.parseColor("#fffdc33e"));
                    sb.setSpan(span1,index+1,index1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) convertView).setText(sb);
                }
            } else if (LiveAnchorActivity.SHARE.equals(notificationMessage.getMessage())) {
                if (notificationMessage.getUserInfo() != null && !Util.isEmpty(notificationMessage.getUserInfo().getName())) {
                    ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                    int index;
                    index = notificationMessage.getUserInfo().getName().length();
                    builder.append(notificationMessage.getUserInfo().getName());
                    builder.append("分享了直播");
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    SpannableString sb = new SpannableString(builder.toString());
                    ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                    sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) convertView).setText(sb);
                }
            } else if (LiveAnchorActivity.FOLLOW.equals(notificationMessage.getMessage())) {
                if (notificationMessage.getUserInfo() != null && !Util.isEmpty(notificationMessage.getUserInfo().getName())) {
                    ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                    int index;
                    index = notificationMessage.getUserInfo().getName().length();
                    builder.append(notificationMessage.getUserInfo().getName());
                    builder.append("关注了主播");
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    SpannableString sb = new SpannableString(builder.toString());
                    ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                    sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) convertView).setText(sb);
                }
            }
        } else if (message.getContent() instanceof PresentMessage) {
            ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
            final PresentMessage presentMessage = (PresentMessage) message.getContent();
            final StringBuilder builder = new StringBuilder();
            if (presentMessage.getUserInfo() != null && !Util.isEmpty(presentMessage.getUserInfo().getName())) {
                builder.append(presentMessage.getUserInfo().getName());
                builder.append(":");
            } else {
                builder.append("匿名用户:");
            }
            builder.append(mContext.getString(R.string.label_present_content, presentMessage.getPresentName()));
            //builder.append("1");
            final SpannableString sb = new SpannableString(builder.toString());
            ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
            int index = builder.toString().lastIndexOf(":");
            sb.setSpan(span, 0, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ImageSpan imageSpan=new ImageSpan(mContext,resource);
//            sb.setSpan(imageSpan, builder.toString().length()-1, builder.toString().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
            ((TextView) convertView).setText(sb);
            Glide.with(mContext).load(Util.getD2cPicUrl(presentMessage.getPresentUrl(), ScreenUtil.dip2px(10),ScreenUtil.dip2px(10))).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {


                }
            });

        } else if (message.getContent() instanceof WebMessage) {
            WebMessage webMessage = (WebMessage) message.getContent();
            StringBuilder builder = new StringBuilder();
            int index;
            SpannableString sb;
            ForegroundColorSpan span;
            switch (webMessage.getType()) {
                case 1: //文本
                    if (!Util.isEmpty(webMessage.getUserName())) {
                        builder.append(webMessage.getUserName());
                        builder.append(":");
                    } else {
                        builder.append("匿名用户:");
                    }
                    ((TextView) convertView).setTextColor(Color.WHITE);
                    builder.append(webMessage.getContent());
                    sb = new SpannableString(builder.toString());
                    span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                    index = builder.toString().lastIndexOf(":");
                    sb.setSpan(span, 0, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                    ((TextView) convertView).setText(sb);
                    break;
                case 2: //关注
                    if (!Util.isEmpty(webMessage.getUserName())) {
                        ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                        index = webMessage.getUserName().length();
                        builder.append(webMessage.getUserName());
                        builder.append("关注了主播");
                        convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                        sb = new SpannableString(builder.toString());
                        span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                        sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ((TextView) convertView).setText(sb);
                    }
                    break;
                case 3: //进入
                    if (!Util.isEmpty(webMessage.getUserName())) {
                        ((TextView) convertView).setTextColor(Color.parseColor("#fffdc33e"));
                        index = webMessage.getUserName().length();
                        builder.append(webMessage.getUserName());
                        builder.append("进入了直播间");
                        convertView.setBackgroundResource(R.drawable.sp_round4_trans_black3_h20);
                        sb = new SpannableString(builder.toString());
                        span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.trans_70_color_white));
                        sb.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ((TextView) convertView).setText(sb);
                    }
                    break;
                case 4: //退出
                    break;
            }
        }
        return convertView;
    }
}
