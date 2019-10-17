package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 标签view
 * Author: hrb
 * Date: 2016/07/11 15:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class TagView extends LinearLayout {

    @Bind(R.id.close_iv)
    ImageView closeIv;
    @Bind(R.id.add_cart_ll)
    LinearLayout addCartLl;
    @Bind(R.id.info_tv)
    TextView infoTv;
    @Bind(R.id.price_tv)
    TextView priceTv;

    private Context mContext;
    private ImageInfo mInfo;
    //public ImageInfo.TagBean mTag;
    private long id;

    public TagView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.layout_tag_view, this, true);
        ButterKnife.bind(this, root);
        initListener();
    }

    private void initListener() {
        closeIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭标签
                //closeTag();
                if (changeListener != null) {
                    /*mInfo.getTags().remove(mTag);
                    if (mInfo.getTags().size() == 0) {
                        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.SAVETAG));
                    }*/
                    changeListener.close(TagView.this);
                }
            }
        });
        infoTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情页
                if (changeListener != null) changeListener.addCart(id);
            }
        });
        addCartLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情页
                if (changeListener != null) changeListener.addCart(id);
            }
        });
    }

    public void setData(ImageInfo info, int position) {
        mInfo = info;
        /*mTag = info.getTags().get(position);
        infoTv.setText(mTag.tagName);
        priceTv.setText("￥" + Util.getNumberFormat(mTag.price));
        id = mTag.id;
        if (info.isSelf) {
            closeIv.setVisibility(VISIBLE);
        } else {
            closeIv.setVisibility(GONE);
        }*/
    }

    public interface TagClickBack {

        void close(TagView view);

        void addCart(long id);
    }

    private TagClickBack changeListener;

    public void setListener(TagClickBack listener) {
        changeListener = listener;
    }

    /*public boolean isSaveSussces() {
        return mTag.isSuccess;
    }*/

   /* public ImageInfo.TagBean getMyTag() {
        return mTag;
    }*/

    public long getShareId() {
        if (mInfo != null) {
            //return mInfo.shareId;
        }
        return 0;
    }
}
