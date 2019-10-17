package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.swipeLayout.SwipeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/11.
 * Description : PartnerMemberHolder
 */

public class PartnerTeamHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_position)
        public TextView tvPosition;
        @Bind(R.id.iv_person_head)
        public RoundedImageView ivPersonHead;
        @Bind(R.id.tv_fans_nickName)
        public TextView tvFansNickName;
        @Bind(R.id.tv_fans_show)
        public TextView tvFansShow;
        @Bind(R.id.dividing)
        public View dividing;
        @Bind(R.id.bt_remove)
         public View btRemove;
        @Bind(R.id.swipe_menu)
        public SwipeLayout swipeMenu;

   public PartnerTeamHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        }
}
