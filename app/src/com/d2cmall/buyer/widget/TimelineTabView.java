package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by rookie on 2017/10/17.
 */

public class TimelineTabView extends FrameLayout {


    @Bind(R.id.timeline_tab_icon_iv)
    ImageView timelineTabIconIv;
    @Bind(R.id.text)
    TextView textView;
    private Context context;
    public boolean canStartAnimation = false;
    public boolean isThisItem = false;
    public boolean isFirstIn = false;
    private boolean isColor = false;

    public TimelineTabView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public TimelineTabView(Context context, boolean isColor) {
        super(context);
        this.context = context;
        this.isColor = isColor;
        init(context);
    }

    public TimelineTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_tab_item, this);
        ButterKnife.bind(this, this);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            textView.setTextColor(context.getResources().getColor(R.color.color_black87));
            if (canStartAnimation && isThisItem && isFirstIn) {
                timelineTabIconIv.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_up));
            }
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.color_black60));
            if (!canStartAnimation && isThisItem && isFirstIn) {
                timelineTabIconIv.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_down));
            }
        }
    }


    public void startAnimation(int arr) {
        if (arr == 0) {//向上反转
            canStartAnimation = true;
            this.setSelected(true);
        } else {//向下反转
            canStartAnimation = false;
            this.setSelected(false);
        }
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setText(String text) {
        textView.setText(text);
        if (isColor) {
            if(text.length()<=2){
                return;
            }
            SpannableString sb = new SpannableString(text);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#8A000000"));
            sb.setSpan(span,2,sb.length(),SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(sb);
        }
    }

    public void setData(int iconResId) {
        timelineTabIconIv.setImageResource(iconResId);
    }
}

