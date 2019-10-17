package com.d2cmall.buyer.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.holder.AddCartImageItemHolder;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/5/18.
 */

public class AddCartDialog extends Dialog {


    @Bind(R.id.tv_add_title)
    TextView tvAddTitle;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.just_dismiss)
    TextView justDismiss;
    @Bind(R.id.just_go_on)
    TextView justGoOn;
    @Bind(R.id.ll_img)
    LinearLayout llImg;

    private List<String> imgUrls;

    private DialogListener dialogListener;

    private Context context;

    private MiniAdapter adapter;

    public AddCartDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
        imgUrls = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_cart);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        justDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogListener != null) {
                    dialogListener.dismissDialog();
                }
                dismiss();
            }
        });
        justGoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogListener != null) {
                    dialogListener.showDialog();
                }
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        if (adapter == null) {
            adapter = new MiniAdapter();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void setImageUrl(List<String> imgUrl) {
        imgUrls.clear();
        imgUrls.addAll(imgUrl);
        llImg.removeAllViews();
        if (imgUrls.size() >= 3) {
            recycleView.setVisibility(View.VISIBLE);
            llImg.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            recycleView.setVisibility(View.GONE);
            llImg.setVisibility(View.VISIBLE);
            for (int i = 0; i < imgUrls.size(); i++) {
                ImageView view = (ImageView) LayoutInflater.from(context).inflate(R.layout.layout_add_cart_item, llImg,false);
                UniversalImageLoader.displayImage(context, imgUrls.get(i), view);
                Log.e("tag3",""+i);
                llImg.addView(view);
            }
        }

    }

    class MiniAdapter extends RecyclerView.Adapter<AddCartImageItemHolder> {

        @Override
        public AddCartImageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_add_cart_item, parent, false);
            return new AddCartImageItemHolder(view);
        }

        @Override
        public void onBindViewHolder(AddCartImageItemHolder holder, int position) {
            UniversalImageLoader.displayImage(context, imgUrls.get(position), holder.image);
        }

        @Override
        public int getItemCount() {
            return imgUrls.size();
        }
    }

}
