package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

/**
 * Created by rookie on 2017/9/4.
 */

public class TopicTextHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public TopicTextHolder(View itemView) {
        super(itemView);
        textView= (TextView) itemView.findViewById(R.id.text);
    }
}
