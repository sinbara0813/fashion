package com.d2cmall.buyer.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/5/18.
 */

public class ProgressDialog extends Dialog {

    @Bind(R.id.progress_down)
    CustomProgress progressDown;

    public ProgressDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public void setProgress(int progress) {
        progressDown.setProgress(progress);
    }

}
