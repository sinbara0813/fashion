package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheilpengtai.supertshirt16library.communicator.ble.RequestError;
import com.cheilpengtai.supertshirt16library.ledprotocol.ResultListener;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.GIFRecyclerAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GIFActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.img_preview)
    GifImageView imgPreview;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.btn_ok)
    Button btnOk;
    private Dialog loadingDialog;
    private String currentItem = "C01";
    private GifDrawable gifFromAssets = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
        initTitle();
        Point point = Util.getDeviceSize(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int previewHeight = Math.round(point.x - 30 * dm.density);
        imgPreview.getLayoutParams().height = previewHeight;
        int itemWidth = Math.round((point.x - 15 * dm.density - 3 * 10 * dm.density) * 5 / 16);
        GIFRecyclerAdapter adapter = new GIFRecyclerAdapter(itemWidth, itemWidth);
        adapter.setOnItemClickListener(new GIFRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                currentItem = item;
                try {
                    gifFromAssets = new GifDrawable(getAssets(), "tshirt/" + item.toLowerCase() + ".gif");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgPreview.setImageDrawable(gifFromAssets);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        try {
            gifFromAssets = new GifDrawable(getAssets(), "tshirt/c01.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgPreview.setImageDrawable(gifFromAssets);

    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_gif);
    }


    @OnClick(R.id.btn_ok)
    public void onClick() {
        if (TshirtActivity.isConnected) {
            showLoadingDialog();
            TshirtActivity.bTMessenger.displayGif(currentItem, 80, new ResultListener<Boolean>() {
                @Override
                public void onResponse(final Boolean response) {
                    dismissLoadingDialog();
                    showToastMessage("请求" + (response ? "成功" : "失败"));
                }

                @Override
                public void onError(RequestError requestError) {
                    dismissLoadingDialog();
                    showToastMessage(Util.getBlueteethMessagerError(requestError));
                }
            });
        } else {
            Util.showToast(this, R.string.msg_blue_connect_first);
        }
    }

    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Util.showToast(GIFActivity.this, message, 3);
            }
        });
    }

    private void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.show();
            }
        });
    }

    private void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }
}
