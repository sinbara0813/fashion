package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/12/12.
 */

public class TakePhotoGuidePop implements TransparentPop.InvokeListener {
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.iv_guide)
    ImageView ivGuide;
    @Bind(R.id.tv_step)
    TextView tvStep;
    @Bind(R.id.tv_dismiss)
    TextView tvDismiss;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.ll_action)
    LinearLayout llAction;
    @Bind(R.id.tv_take_photo)
    TextView tvTakePhoto;
    private TransparentPop pop;
    private View rootView;

    public TakePhotoGuidePop(final Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_guide_take_photo, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        pop = new TransparentPop(context, false, this);
        pop.setBackGroundResource(R.color.trans_60_color_black);
        pop.setFocusable(false);
        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAction.setVisibility(View.GONE);
                tvTakePhoto.setVisibility(View.VISIBLE);
                ivGuide.setImageResource(R.mipmap.pic_pszn02);
                tvStep.setText("2/2");
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
