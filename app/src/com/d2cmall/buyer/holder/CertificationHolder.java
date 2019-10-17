package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/4.
 * Description : CertificationHolder
 */

public class CertificationHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.tv_name)
        public TextView tvName;
        @Bind(R.id.tv_id_number)
        public TextView tvIdNumber;
        @Bind(R.id.check_box)
        public CheckBox checkBox;
        @Bind(R.id.iv_delete)
        public ImageView ivDelete;
        @Bind(R.id.ll_top)
        public LinearLayout llTop;
        @Bind(R.id.tv_add_certification)
        public TextView tvAddCertification;


    public CertificationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
}
