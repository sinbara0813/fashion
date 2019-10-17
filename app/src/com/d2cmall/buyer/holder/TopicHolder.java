package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

/**
 * Created by rookie on 2017/8/18.
 */

public class TopicHolder extends RecyclerView.ViewHolder {
    public ImageView iv_topic;
    public TopicHolder(View itemView,int picWidth) {
        super(itemView);
        iv_topic= (ImageView) itemView.findViewById(R.id.iv_topic);
        if(Util.isLowThanAndroid5()){
            iv_topic.setMaxHeight(ScreenUtil.dip2px(264));
        }
        LinearLayout.LayoutParams ll= new LinearLayout.LayoutParams(-2,-2);
        ll.width=picWidth;
        ll.height=(int)(ll.width*((float)264/160));
        iv_topic.setLayoutParams(ll);
    }
}
