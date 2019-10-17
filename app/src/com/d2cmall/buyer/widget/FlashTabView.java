package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/4/28.
 */

public class FlashTabView extends FrameLayout {


    @Bind(R.id.tv_top)
    TextView tvTop;
    @Bind(R.id.tv_bottom)
    TextView tvBottom;
    @Bind(R.id.view_left)
    View viewLeft;
    @Bind(R.id.view_right)
    View viewRight;
    @Bind(R.id.rl_all)
    RelativeLayout relativeLayout;
    private Context context;

    public FlashTabView(Context context) {
        this(context, null);
        this.context = context;
    }

    public FlashTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        View.inflate(context, R.layout.layout_flash_tab, this);
        ButterKnife.bind(this, this);
    }

    public void setText(String top, String bottom) {
        tvTop.setText(top);
        tvBottom.setText(bottom);
    }

    public void setWidth(int width) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = width;
        relativeLayout.setLayoutParams(layoutParams);
    }

    public void setLineVisible(boolean left, boolean right) {
        if (left) {
            viewLeft.setVisibility(VISIBLE);
        } else {
            viewLeft.setVisibility(GONE);
        }

        if (right) {
            viewRight.setVisibility(VISIBLE);
        } else {
            viewRight.setVisibility(GONE);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            tvTop.setTextColor(Color.parseColor("#f23365"));
            tvTop.setTextSize(16);
            tvTop.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvBottom.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvBottom.setTextColor(Color.parseColor("#f23365"));
        } else {
            tvTop.setTextColor(context.getResources().getColor(R.color.color_black87));
            tvTop.setTextSize(14);
            tvTop.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvBottom.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvBottom.setTextColor(context.getResources().getColor(R.color.color_black38));
        }
    }
}
