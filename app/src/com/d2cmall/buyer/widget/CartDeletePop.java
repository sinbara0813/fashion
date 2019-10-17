package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CartDeletePop implements TransparentPop.InvokeListener {

    TextView tvContent;
    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    @Bind(R.id.btn_sure)
    TextView btnSure;
    private TransparentPop mPop;
    private Context mContext;
    private View rootView;

    public CartDeletePop(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.cartdelete_pop, new LinearLayout(mContext), false);
        tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        ButterKnife.bind(this, rootView);

        mPop = new TransparentPop(mContext, this);
        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();

    }

    public void initListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) back.cancel();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) back.sure();
            }
        });
    }

    public void setContent(String s) {
        if (Util.isEmpty(s)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(s);
        }
    }

    public void setContent(int id) {
        tvContent.setText(id);
    }

    public void setBtnSure(int id) {
        btnSure.setText(id);
    }

    public void setBtnCancel(int id){
        btnCancel.setText(id);
    }

    public void show(View parent) {
        mPop.show(parent, false);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(false);
        }
    }

    public boolean isShow() {
        return mPop.isShowing();
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView=null;
    }

    public CallBack back;

    public void setBack(CallBack callBack) {
        this.back = callBack;
    }

    public interface CallBack {
        void sure();

        void cancel();
    }
}
