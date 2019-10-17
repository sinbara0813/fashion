package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/12/12.
 */

public class CartGuidePop implements TransparentPop.InvokeListener {
    @Bind(R.id.lead_iv)
    ImageView leadIv;
    @Bind(R.id.ll_all)
    RelativeLayout all;
    private TransparentPop pop;
    private View rootView;

    public CartGuidePop(final Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_guide_cart, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        pop = new TransparentPop(context, false, this);
        pop.setBackGroundResource(R.color.trans_60_color_black);
        pop.setFocusable(false);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop != null) {
                    pop.dismiss(false);
                }
            }
        });
    }

    public void show(View view) {
        pop.show(view, false);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }
}
