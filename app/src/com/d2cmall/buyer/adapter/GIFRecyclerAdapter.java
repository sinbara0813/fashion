package com.d2cmall.buyer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

public class GIFRecyclerAdapter extends RecyclerView.Adapter
        <GIFRecyclerAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private int width;
    private int height;
    private int[] imgs = {R.mipmap.t_c01, R.mipmap.t_c02, R.mipmap.t_c03, R.mipmap.t_c04,
            R.mipmap.t_c05, R.mipmap.t_c06, R.mipmap.t_c07, R.mipmap.t_c08,
            R.mipmap.t_c09, R.mipmap.t_c10, R.mipmap.t_c11, R.mipmap.t_c12,
            R.mipmap.t_c13, R.mipmap.t_c14, R.mipmap.t_c15, R.mipmap.t_c16,
            R.mipmap.t_c17, R.mipmap.t_c18, R.mipmap.t_c19, R.mipmap.t_c20,
            R.mipmap.t_c21, R.mipmap.t_c22, R.mipmap.t_c23, R.mipmap.t_c24,
            R.mipmap.t_c25, R.mipmap.t_c26, R.mipmap.t_c27};

    private String[] txts = {"C01", "C02", "C03", "C04", "C05", "C06", "C07", "C08", "C09",
            "C10", "C11", "C12", "C13", "C14", "C15", "C16", "C17", "C18", "C19", "C20",
            "C21", "C22", "C23", "C24", "C25", "C26", "C27"};

    private String[] names = {"D2C", "心", "星星", "电子宠物（彩色）", "电子宠物（单色）", "我爱你（彩色）",
            "我爱你（单色）", "wow", "飞吻", "卖萌", "哭", "害羞", "笑脸", "火炬", "心跳", "游泳",
            "带球", "传球", "射门", "倒挂金钩", "双11", "双11", "布", "剪刀", "石头", "大象", "狐狸"};

    private int checkedPosition = 0;

    public GIFRecyclerAdapter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gifview, parent,
                false);
        return new ViewHolder(v, width, height);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0) {
            holder.headView.setVisibility(View.GONE);
        } else {
            holder.headView.setVisibility(View.VISIBLE);
        }
        if (position == imgs.length - 1) {
            holder.footView.setVisibility(View.VISIBLE);
        } else {
            holder.footView.setVisibility(View.GONE);
        }
        holder.imgCover.setImageResource(imgs[position]);
        holder.imgCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(txts[position]);
                checkedPosition = position;
                notifyDataSetChanged();
            }
        });
        if (position == checkedPosition) {
            holder.imgCheck.setImageResource(R.mipmap.ic_checked2);
        } else {
            holder.imgCheck.setImageResource(R.mipmap.ic_unchecked2);
        }
        holder.tvName.setText(names[position]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private ImageView imgCheck;
        private View headView;
        private View footView;
        private TextView tvName;

        public ViewHolder(View itemView, int width, int height) {
            super(itemView);
            imgCover = (ImageView) itemView.findViewById(R.id.img_cover);
            imgCover.getLayoutParams().width = width;
            imgCover.getLayoutParams().height = height;
            imgCheck = (ImageView) itemView.findViewById(R.id.img_check);
            headView = itemView.findViewById(R.id.head_view);
            footView = itemView.findViewById(R.id.foot_view);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    @Override
    public int getItemCount() {
        return txts.length;
    }


    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
