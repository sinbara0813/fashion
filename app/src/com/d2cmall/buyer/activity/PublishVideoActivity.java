package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.RelationProductAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.AddressEntity;
import com.d2cmall.buyer.bean.RelationProductBean;
import com.d2cmall.buyer.bean.SelectListBean;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.VideoFile;
import com.d2cmall.buyer.listener.OnAddressDeleteClickListener;
import com.d2cmall.buyer.util.InitializeService;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;

import static com.d2cmall.buyer.Constants.RequestCode.EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE;
import static com.d2cmall.buyer.Constants.RequestCode.REQUEST_TOPIC;
import static com.d2cmall.buyer.Constants.RequestCode.SEARCHADDRESS_ACTIVITY_REQUESTCODE;

/**
 * Created by rookie on 2017/9/5.
 * 发布小视屏页面
 */

public class PublishVideoActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.edit_description)
    EditText editDescription;
    @Bind(R.id.tv_topic)
    TextView tvTopic;
    @Bind(R.id.preview)
    ImageView preview;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.play_tag)
    ImageView playTag;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.mydress)
    LinearLayout mydress;
    @Bind(R.id.tv_relevance)
    TextView tvRelevance;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.relevance_product)
    LinearLayout relevanceProduct;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    private String url;
    private long duration;
    private AddressEntity address;
    private TopicBean.DataBean.TopicsBean.ListBean topicBean;
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> selectList;
    private LinearLayoutManager virtualLayoutManager;
    private RelationProductAdapter relationProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_video);
        ButterKnife.bind(this);
        titleRight.setText("发布");
        selectList = new ArrayList<>();
        tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), 0));
        topicBean= (TopicBean.DataBean.TopicsBean.ListBean) getIntent().getSerializableExtra("topic");
        if(topicBean!=null){
            editDescription.setText("#"+topicBean.getTitle()+"#");
            editDescription.setSelection(editDescription.length());
        }
        virtualLayoutManager = new LinearLayoutManager(this);
        relationProductAdapter = new RelationProductAdapter(this, selectList, 1);
        relationProductAdapter.setOnDeleteListener(new OnAddressDeleteClickListener() {
            @Override
            public void clickDelete(View v, int position) {
                tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), selectList.size()));
            }
        });
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(relationProductAdapter);
        init();
        editDescription.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!Util.isEmpty(editDescription.getText().toString())) {
                        int firstIndex = String.valueOf(editDescription.getText()).indexOf("#");
                        int secondIndex = String.valueOf(editDescription.getText()).indexOf("#", firstIndex + 1);
                        int deletePosition = editDescription.getSelectionStart();
                        String oldContent = editDescription.getText().toString();
                        String newContent = "";
                        if ((secondIndex + 1) == deletePosition) {
                            newContent = oldContent.replace(oldContent.substring(firstIndex, secondIndex + 1), "");
                            editDescription.setText(newContent);
                        }
                    }
                    topicBean = null;
                }
                return false;
            }
        });
    }

    private void init() {
        loadDataFromIntent(getIntent());
        playTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PublishVideoActivity.this, MediaPickActivity.class);
                intent2.putExtra("maxSum", 1);
                startActivityForResult(intent2, Constants.REQUEST_CODE_PICK_VIDEO);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(null);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    public void onSend() {
        if (getCurrentFocus() != null) {
            KeyboardUtil.hideKeyboard(getCurrentFocus());
        }
        Intent intent = new Intent(this, InitializeService.class);
        intent.putExtra("url", url);
        String content = editDescription.getText().toString();
        int firstIndex = content.indexOf("#");
        int secondIndex = content.indexOf("#", firstIndex + 1);
        if (firstIndex != -1 && secondIndex != -1) {
            String deleteContent = content.replace(editDescription.getText().toString().substring(firstIndex, secondIndex + 1), "");
            intent.putExtra("content", deleteContent);
        } else {
            intent.putExtra("content", content);
        }
        Log.e("content", intent.getStringExtra("content"));
        intent.putExtra("duration", duration);
        intent.setAction("upload");
        intent.putExtra("topicBean", topicBean);
        if(selectList.size()>0){
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<selectList.size();i++){
                sb.append(selectList.get(i).getId()+",");
            }
            sb.deleteCharAt(sb.length()-1);
            intent.putExtra("selectList", sb.toString());
        }
        intent.putExtra(SearchAdressActivity.ADDRESS, address);
        startService(intent);
        finish();
    }

    public void onBackPressed(View view) {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        loadDataFromIntent(intent);
        super.onNewIntent(intent);
    }

    private void loadDataFromIntent(final Intent data) {
        url = data.getStringExtra("url");
        duration = data.getLongExtra("duration", 0);
        if (!Util.isEmpty(url)) {
            playTag.setVisibility(View.GONE);
            ivPlay.setVisibility(View.VISIBLE);
            preview.setVisibility(View.VISIBLE);
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.MINI_KIND);
            preview.setImageBitmap(bitmap);
            titleRight.setEnabled(true);
            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去播放页面
                    Intent intent = new Intent(PublishVideoActivity.this, SimplePlayActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("isSource", data.getBooleanExtra("isSource", true));
                    startActivityForResult(intent, EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE);
                }
            });
        }
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.tv_topic, R.id.tv_address, R.id.relevance_product})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.relevance_product:
                intent = new Intent(PublishVideoActivity.this, RelationSelectProductActivity.class);
                intent.putExtra("size", selectList.size());
                SelectListBean bean = new SelectListBean();
                bean.setList(selectList);
                intent.putExtra("selectedList", bean);
                startActivityForResult(intent, 999);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                onSend();
                break;
            case R.id.tv_topic:
                intent = new Intent(this, TopicListActivity.class);
                startActivityForResult(intent, REQUEST_TOPIC);
                break;
            case R.id.tv_address:
                intent = new Intent(this, SearchAdressActivity.class);
                intent.putExtra(SearchAdressActivity.ADDRESS, tvAddress.getText().toString());
                startActivityForResult(intent, SEARCHADDRESS_ACTIVITY_REQUESTCODE);
                break;
        }
    }

    private void startTrimActivity(String path, long duration) {
        Intent intent = new Intent(this, VideoEditActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("duration", duration);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE
                && resultCode == EXLOREVIDEO_PUBLISHACTIVITY_REQUESTCODE) {
            url = null;
            playTag.setVisibility(View.VISIBLE);
            ivPlay.setVisibility(View.GONE);
            preview.setVisibility(View.GONE);

        } else if (resultCode == Activity.RESULT_OK && data != null
                && resultCode == Constants.REQUEST_CODE_PICK_VIDEO) {
            ArrayList<VideoFile> list = data.getParcelableArrayListExtra("list");

            if (list != null == list.size() > 0) {
                VideoFile videoFile = list.get(0);
                startTrimActivity(videoFile.getPath(), videoFile.getDuration());
            }

        } else if (resultCode == SEARCHADDRESS_ACTIVITY_REQUESTCODE && data != null) {
            address = (AddressEntity) data.getSerializableExtra(SearchAdressActivity.ADDRESS);
            if (address != null) {
                tvAddress.setText(address.cityName + address.addressName);
            } else {

            }
        } else if (requestCode == REQUEST_TOPIC) {
            if (data != null) {
                topicBean = (TopicBean.DataBean.TopicsBean.ListBean) data.getSerializableExtra("topic");
                String topicName = "#" + topicBean.getTitle() + "#";
                String oldContent = editDescription.getText().toString();
                StringBuilder sb = new StringBuilder();
                String deleteContent = null;
                if (oldContent.contains("#") && oldContent.indexOf("#", oldContent.indexOf("#") + 1) != -1) {
                    int firstIndex = oldContent.indexOf("#");
                    int secondIndex = oldContent.indexOf("#", firstIndex + 1);
                    deleteContent = oldContent.replace(oldContent.substring(firstIndex, secondIndex + 1), "");
                }
                sb.append(topicName);
                if (deleteContent != null) {
                    sb.append(deleteContent);
                } else {
                    sb.append(oldContent);
                }
                editDescription.setText(sb.toString());
                editDescription.setSelection(sb.length());
            }
        } else if (requestCode == 999&&data!=null) {
            SelectListBean bean = (SelectListBean) data.getSerializableExtra("selectList");
            selectList.clear();
            selectList.addAll(bean.getList());
            tvRelevance.setText(String.format(getResources().getString(R.string.label_relevance_product), selectList.size()));
            relationProductAdapter.notifyDataSetChanged();

        }

    }
}
