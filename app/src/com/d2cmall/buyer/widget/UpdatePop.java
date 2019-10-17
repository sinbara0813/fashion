package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UpdatePop implements TransparentPop.InvokeListener {

    @Bind(R.id.image_head)
    ImageView imageHead;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.sure_btn)
    TextView sureBtn;
    @Bind(R.id.cancel_btn)
    ImageView cancelBtn;
    private TransparentPop mPop;
    private Context mContext;
    private View updateLayout;

    public UpdatePop(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        updateLayout = LayoutInflater.from(mContext).inflate(R.layout.layout_update_new, new LinearLayout(mContext), false);
        ButterKnife.bind(this, updateLayout);
        mPop = new TransparentPop(mContext,false, this);
        mPop.setFocusable(false);
        mPop.setOutsideTouchable(false);
        mPop.setBackgroundDrawable(null);
        initListener();
    }

    public void initListener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) back.cancel();
            }
        });
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) back.sure();
            }
        });
    }

    public void setTitle(String s) {
        if (Util.isEmpty(s)) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(s);
        }
    }

    public void setContent(String s) {
        if (Util.isEmpty(s)) {
            content.setVisibility(View.GONE);
        } else {
            content.setText(s);
        }
    }

    public void setContent(String s, boolean is) {
        if (Util.isEmpty(s)) {
            content.setVisibility(View.GONE);
        } else {
            content.setText(Html.fromHtml(s));
        }
    }

    public void forceUpdate() {
        cancelBtn.setVisibility(View.GONE);
        sureBtn.setWidth(320);
    }

    public void setContent(int id) {
        content.setText(id);
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
        v.setGravity(Gravity.CENTER);
        Point point = Util.getDeviceSize(mContext);
        int width = Math.round(point.x * 4 / 5);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ScreenUtil.dip2px(280), -2);
        v.addView(updateLayout, lp);
    }

    @Override
    public void releaseView(LinearLayout v) {

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
