package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/11/23.
 * Description : MarqueeTextView
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    // 焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}

