package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/7.
 */

public class NewUpdateActivity extends BaseActivity {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.cart_iv)
    ImageView cartIv;
    @Bind(R.id.back_fl)
    FrameLayout backFl;
    @Bind(R.id.back_iv1)
    ImageView backIv1;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.cart_iv1)
    ImageView cartIv1;
    @Bind(R.id.title_fl)
    FrameLayout titleFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
    }
}
