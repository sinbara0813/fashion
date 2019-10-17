package com.d2cmall.buyer.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/5/23.
 */

public class SimpleDialog extends Dialog {
    @Bind(R.id.tv_add_title)
    TextView tvAddTitle;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.just_dismiss)
    TextView justDismiss;
    @Bind(R.id.just_go_on)
    TextView justGoOn;
    @Bind(R.id.content)
    RoundLayout content;
    private Context context;
    private View.OnClickListener left, right;

    public SimpleDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public SimpleDialog setTitle(String text) {
        tvAddTitle.setText(text);
        return this;
    }

    public SimpleDialog setTvMessage(String text) {
        tvMessage.setText(text);
        return this;
    }

    public SimpleDialog setLeftText(String text) {
        justDismiss.setText(text);
        return this;
    }

    public SimpleDialog setRightText(String text) {
        justGoOn.setText(text);
        return this;
    }

    public SimpleDialog hasRightButtom(boolean hasRight){
        justGoOn.setVisibility(hasRight?View.VISIBLE:View.GONE);
        return this;
    }

    public SimpleDialog setLeftClick(View.OnClickListener onClickListener) {
        left = onClickListener;
        return this;
    }

    public SimpleDialog setRightClick(View.OnClickListener onClickListener) {
        right = onClickListener;
        return this;
    }

    @OnClick({R.id.just_dismiss, R.id.just_go_on})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.just_dismiss:
                if (left != null) {
                    left.onClick(view);
                }
                break;
            case R.id.just_go_on:
                if (right != null) {
                    right.onClick(view);
                }
                break;
        }
    }
}
