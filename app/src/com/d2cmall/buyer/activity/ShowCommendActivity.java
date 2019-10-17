package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ShowCommendAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.CommendDetailBean;
import com.d2cmall.buyer.bean.ProductCommentListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RatingBar;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowCommendActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.send_post_layout)
    LinearLayout sendPostLayout;
    @Bind(R.id.item_iv)
    ImageView itemIv;
    @Bind(R.id.item_info)
    TextView itemInfo;
    @Bind(R.id.tv_style)
    TextView tvStyle;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.comment_content)
    TextView commentContent;
    @Bind(R.id.nineGrid)
    RecyclerView nineGrid;
    @Bind(R.id.product)
    TextView product;
    @Bind(R.id.product_satisfaction)
    RatingBar productSatisfaction;
    @Bind(R.id.ship_satisfaction)
    RatingBar shipSatisfaction;
    @Bind(R.id.deliver)
    TextView deliver;
    @Bind(R.id.pack)
    TextView pack;
    @Bind(R.id.pack_satisfaction)
    RatingBar packSatisfaction;
    @Bind(R.id.delievy_satisfaction)
    RatingBar delievySatisfaction;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.replyLl)
    LinearLayout replyLl;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.video_layout)
    RelativeLayout videoLayout;

    private long id;
    private String price;
    private long productId;
    private CommendDetailBean commendDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_comment);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this,R.string.label_show_comment);
        id = getIntent().getLongExtra("id", 0);
        price = getIntent().getStringExtra("price");
        productId = getIntent().getLongExtra("productId", 0);
        if (id != 0) {
            loadData();
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMMEND_DETAIL_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CommendDetailBean>() {
            @Override
            public void onResponse(CommendDetailBean response) {
                showData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ShowCommendActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void showData(final CommendDetailBean detail) {
        itemIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCommendActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", productId);
                startActivity(intent);
            }
        });
        itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowCommendActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", productId);
                startActivity(intent);
            }
        });
        if (!Util.isEmpty(detail.getData().getComment().getProductImg())) {
            UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(detail.getData().getComment().getProductImg()), itemIv);
        }
        tvStyle.setText(detail.getData().getComment().getSkuProperty());
        tvDate.setText(detail.getData().getComment().getCreateDate());
        tvPrice.setText(String.format(getString(R.string.label_price), price));
        itemInfo.setText(detail.getData().getComment().getTitle());
        productSatisfaction.setStar(detail.getData().getComment().getProductScore());
        shipSatisfaction.setStar(detail.getData().getComment().getDeliverySpeedScore());
        packSatisfaction.setStar(detail.getData().getComment().getPackageScore());
        delievySatisfaction.setStar(detail.getData().getComment().getDeliveryServiceScore());
        if (!Util.isEmpty(detail.getData().getComment().getContent())) {
            commentContent.setText(detail.getData().getComment().getContent());
        } else {
            commentContent.setVisibility(View.GONE);
        }


        String videoUrl1 = detail.getData().getComment().getVideo();
        List<String> imgList = detail.getData().getComment().getPics();
        if (Util.isEmpty(videoUrl1)) {
            nineGrid.setVisibility(View.VISIBLE);
            if (detail.getData().getComment().getPics() != null && detail.getData().getComment().getPics().size() > 0) {
                nineGrid.setVisibility(View.VISIBLE);
                List<ImageInfo> imageInfoList=new ArrayList<>();
                int size=imgList.size();
                for (int i=0;i<size;i++){
                    ImageInfo imageInfo=new ImageInfo();
                    imageInfo.bigImageUrl=Util.getD2cPicUrl(detail.getData().getComment().getPics().get(i));
                    imageInfoList.add(imageInfo);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                ShowCommendAdapter showCommendAdapter = new ShowCommendAdapter(this, imageInfoList);
                nineGrid.setLayoutManager(layoutManager);
                nineGrid.setAdapter(showCommendAdapter);
            } else {
                nineGrid.setVisibility(View.GONE);
            }
        } else { //视频
            nineGrid.setVisibility(View.GONE);
            videoLayout.setVisibility(View.VISIBLE);
            if (detail.getData().getComment().getPics()!=null&&!Util.isEmpty(detail.getData().getComment().getPics().get(0))){
                Log.e("123",detail.getData().getComment().getPics().get(0));
                UniversalImageLoader.displayImage(this,detail.getData().getComment().getPics().get(0),image);
            }
            videoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShowCommendActivity.this, SimplePlayActivity.class);
                    intent.putExtra("url", detail.getData().getComment().getVideo());
                    startActivity(intent);
                }
            });
        }
        if (detail.getData().getComment().getReplys() != null && detail.getData().getComment().getReplys().size() > 0) {
            replyLl.removeAllViews();
            replyLl.setVisibility(View.VISIBLE);
            //添加追评和客服回复
            List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity> replys = sortReplys(detail.getData().getComment().getReplys());
            for (int i = 0; i < replys.size(); i++) {
                ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity replysEntity = replys.get(i);
                if (replysEntity.getType().equals("CUSTOMER")) {
                    sendPostLayout.setVisibility(View.GONE);
                    View customLL = LayoutInflater.from(this).inflate(R.layout.layout_reply_custom, null);
                    TextView customllTime = (TextView) customLL.findViewById(R.id.tv_date);
                    TextView customllContent = (TextView) customLL.findViewById(R.id.custom_charse_content);
                    RecyclerView recyclerView= (RecyclerView) customLL.findViewById(R.id.nineGrid);
                    customllTime.setText(replysEntity.getCreateDate());
                    StringBuilder builder=new StringBuilder();
                    builder.append("追评:").append(replysEntity.getContent());
                    SpannableString sb=new SpannableString(builder.toString());
                    ForegroundColorSpan colorSpan=new ForegroundColorSpan(getResources().getColor(R.color.color_red));
                    sb.setSpan(colorSpan,0,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    customllContent.setText(sb);

                    List<String> pic = replysEntity.getPic();
                    if (pic!=null&&pic.size()>0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        List<ImageInfo> imageInfoList=new ArrayList<>();
                        int size=pic.size();
                        for (int j=0;j<size;j++){
                            ImageInfo imageInfo=new ImageInfo();
                            imageInfo.bigImageUrl=Util.getD2cPicUrl(pic.get(j));
                            imageInfoList.add(imageInfo);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                        ShowCommendAdapter showCommendAdapter = new ShowCommendAdapter(this, imageInfoList);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(showCommendAdapter);
                    }

                    replyLl.addView(customLL);
                } else if (replysEntity.getType().equals("SYSTEM")) {
                    View systemLL = LayoutInflater.from(this).inflate(R.layout.layout_reply_system, null);
                    TextView customllTime = (TextView) systemLL.findViewById(R.id.service_charse_time);
                    TextView customllContent = (TextView) systemLL.findViewById(R.id.service_charse_content);
                    customllTime.setText(replysEntity.getCreateDate());
                    customllContent.setText(replysEntity.getContent());
                    replyLl.addView(systemLL);
                }
            }
        } else {
            sendPostLayout.setVisibility(View.VISIBLE);
            replyLl.setVisibility(View.GONE);
        }
    }

    private List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity> sortReplys(List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity> replys) {
        Collections.sort(replys, new Comparator<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity>() {
            @Override
            public int compare(ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity lhs, ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity rhs) {
                return DateUtil.strToDateLong(lhs.getCreateDate()).getTime() < DateUtil.strToDateLong(rhs.getCreateDate()).getTime() ? 1 : 0;
            }
        });
        return replys;
    }

    @OnClick(R.id.send_post_layout)
    public void onClick() {
        Intent intent = new Intent(this, CommentInsertActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("price",price);
        intent.putExtra("productId",productId);
        startActivityForResult(intent, Constants.RequestCode.ADD_COMMEND);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RequestCode.ADD_COMMEND && resultCode == RESULT_OK) {
            sendPostLayout.setVisibility(View.GONE);
            loadData();
        }
    }

}
