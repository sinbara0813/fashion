package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

/**
 * Created by rookie on 2017/8/1.
 */

public class KindNameHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public KindNameHolder(View itemView) {
        super(itemView);
        textView= (TextView) itemView.findViewById(R.id.type_name);
    }
}
