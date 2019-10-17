package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ShowDetailAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.PostReviewApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.ShareDetailBean;
import com.d2cmall.buyer.bean.ShowReviewListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DeleteItemClickListner;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.listener.SharePopDownLoadClickListener;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ProgressDialog;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.EXPLORE_COMMENT_URL;
import static com.d2cmall.buyer.Constants.GlobalType.SHOW_DELETE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Created by rookie on 2017/8/14.
 * 买家秀详情页面
 */

public class ShowDetailActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.top_right_iv)
    ImageView topRightIv;
    @Bind(R.id.title_head_image)
    RoundedImageView titleHeadImage;
    @Bind(R.id.title_user_name)
    TextView titleUserName;
    @Bind(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @Bind(R.id.show_detail_title_layout)
    RelativeLayout showDetailTitleLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.iv_like)
    ImageView ivLike;
    @Bind(R.id.tv_like_num)
    TextView tvLikeNum;
    @Bind(R.id.ll_like)
    LinearLayout llLike;
    @Bind(R.id.iv_review)
    ImageView ivReview;
    @Bind(R.id.tv_review_num)
    TextView tvReviewNum;
    @Bind(R.id.ll_review)
    LinearLayout llReview;
    @Bind(R.id.ll_show_info)
    LinearLayout llShowInfo;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_send_post)
    TextView tvSendPost;
    @Bind(R.id.ll_edit)
    LinearLayout llEdit;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.panel_layout)
    KPSwitchPanelLinearLayout panelLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    private long id;
    private ShareBean.DataEntity.MemberSharesEntity.ListEntity topData;
    private ShowReviewListBean reviewData;
    private List<ShowReviewListBean.DataBean.CommentsBean.ListBean> allReviewList = new ArrayList<>();
    private List<Object> dataList;
    private ShowDetailAdapter showDetailAdapter;
    private int page = 1;
    private boolean hasNext;
    Animation mShowAction;
    Animation mHiddenAction;
    private String toComment = "";
    private long commentId;//评论id,0评论作者,>0回复别人
    InputMethodManager imm;
    private String saveContent;
    private long memberId;
    private Map<Long, String> responseMap;//保存别人回复的评论
    private LinearLayoutManager linearLayoutManager;
    private boolean isSelf, isBuyer;
    private UserBean.DataEntity.MemberEntity user;
    private SharePop sharePop;
    private ProgressDialog progressDialog;
    private int IMAGE_SIZE = 0;
    private Dialog loadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 100) {
                progressDialog.setProgress(0);
                progressDialog.dismiss();
                new android.support.v7.app.AlertDialog.Builder(ShowDetailActivity.this)
                        .setMessage(R.string.msg_go_wx)
                        .setPositiveButton("去上传", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PackageManager packageManager = ShowDetailActivity.this.getPackageManager();
                                String packageName = "com.tencent.mm";
                                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
                                if (launchIntentForPackage != null)
                                    startActivity(launchIntentForPackage);
                                else
                                    Util.showToast(ShowDetailActivity.this, "未安装微信");
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            } else {
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                progressDialog.setProgress(msg.arg1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        ButterKnife.bind(this);
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        doBusiness();
    }

    public void doBusiness() {
        id = getIntent().getLongExtra("id", 0);
        dataList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        initAnimation();
        responseMap = new HashMap<>();
        KeyboardUtil.attach(this, panelLayout, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override
            public void onKeyboardShowing(boolean isShowing) {
                if (isShowing) {
                    llShowInfo.setVisibility(View.GONE);
                    llEdit.setVisibility(View.VISIBLE);
                } else {
                    //将文本保存
                    if (commentId == 0) {
                        saveContent = etContent.getText().toString().trim();
                    } else {
                        if (!Util.isEmpty(etContent.getText().toString().trim())) {
                            responseMap.put(memberId, etContent.getText().toString().trim());
                        } else if (!Util.isEmpty(responseMap.get(memberId))) {
                            responseMap.remove(memberId);
                        }
                    }
                    llEdit.setVisibility(View.GONE);
                    llShowInfo.setVisibility(View.VISIBLE);
                }
            }
        });
        initRecyclerView();
        initData(true);
    }


    private void initAnimation() {
        mShowAction = new AlphaAnimation(0f, 1f);
        mShowAction.setDuration(350);
        mHiddenAction = new AlphaAnimation(1f, 0f);
        mHiddenAction.setDuration(350);
    }

    private void setDataToView(boolean isFirst) {
        user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            if (topData.getMemberId() == user.getMemberId()) {
                isSelf = true;
                topRightIv.setImageResource(R.mipmap.icon_all_mineshare);
            }
            if (user.getPartnerId() > 0) {//分销者
                isBuyer = true;
                topRightIv.setImageResource(R.mipmap.icon_all_mineshare);
            }
        }
        if (isFirst) {
            titleUserName.setText(topData.getMemberName());
            UniversalImageLoader.displayRoundImage(this, Constants.IMG_HOST + topData.getMemberHead(), titleHeadImage, R.mipmap.ic_default_avatar);
        }
        tvLikeNum.setText(String.valueOf(topData.getLikeMeCount()));
        tvReviewNum.setText(String.valueOf(topData.getCommentCount()));
        if (topData.getLiked() == 1) {
            ivLike.setImageResource(R.mipmap.icon_fashion_liked);
        } else {
            ivLike.setImageResource(R.mipmap.icon_fashion_like);
        }
        recycleView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    private boolean mIsRefreshing;

    private void initRecyclerView() {
        showDetailAdapter = new ShowDetailAdapter(this, dataList, allReviewList);
        linearLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(showDetailAdapter);
        recycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mIsRefreshing) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollOffset() > ScreenUtil.dip2px(48)) {
                    if (backIv.getVisibility() == View.VISIBLE && llUserInfo.getVisibility() == View.GONE) {
                        backIv.setVisibility(View.GONE);
                        backIv.startAnimation(mHiddenAction);
                        llUserInfo.setVisibility(View.VISIBLE);
                        llUserInfo.startAnimation(mShowAction);
                    }
                } else {
                    if (backIv.getVisibility() == View.GONE && llUserInfo.getVisibility() == View.VISIBLE) {
                        backIv.setVisibility(View.VISIBLE);
                        backIv.startAnimation(mShowAction);
                        llUserInfo.setVisibility(View.GONE);
                        llUserInfo.startAnimation(mHiddenAction);
                    }
                }
                if (!recycleView.canScrollVertically(-1)) {
                    ViewCompat.setElevation(showDetailTitleLayout, 0);
                } else {
                    ViewCompat.setElevation(showDetailTitleLayout, ScreenUtil.dip2px(4));
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = linearLayoutManager.findLastVisibleItemPosition();
                        if (last > showDetailAdapter.getItemCount() - 3 && hasNext) {
                            refresh();
                        }
                }
            }
        });
        showDetailAdapter.setDeleteItemClickListner(new DeleteItemClickListner() {
            @Override
            public void onDelete(int position) {
                if (allReviewList != null) {
                    tvReviewNum.setText(allReviewList.size() + "");
                } else {
                    tvReviewNum.setText("0");
                }
            }
        });
        showDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                if (Util.loginChecked(ShowDetailActivity.this, 999)) {
                    ShowReviewListBean.DataBean.CommentsBean.ListBean reviewBean = (ShowReviewListBean.DataBean.CommentsBean.ListBean)
                            dataList.get(position);
                    if (reviewBean != null) {
                        toComment = reviewBean.getNickName();
                        commentId = reviewBean.getId();
                        if (Util.loginChecked(ShowDetailActivity.this, Constants.Login.SEND_POST_LOGIN)) {
                            //回复别人的评论
                            KeyboardUtil.showKeyboard(etContent);
                            memberId = reviewBean.getMemberId();
                            if (!Util.isEmpty(responseMap.get(memberId))) {//有记录
                                etContent.setText(responseMap.get(memberId));
                                //移动光标
                                etContent.setSelection(etContent.getText().length());
                            } else {
                                etContent.setText("");
                            }
                            etContent.setHint(getString(R.string.label_to_comment2, toComment));
                        }
                    }
                }
            }
        });
    }

    private void refresh() {
        if (hasNext) {
            page++;
            loadReviewData(true, false);
        } else {
            Util.showToast(this, "已经到底了~");
        }
    }

    private void initData(final boolean isFirst) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.EXPLORE_DETAIL_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ShareDetailBean>() {
            @Override
            public void onResponse(ShareDetailBean shareDetailBean) {
                emptyHintLayout.setVisibility(View.GONE);
                topData = shareDetailBean.getData().getMemberShare();
                dataList.add(shareDetailBean.getData().getMemberShare());
                loadReviewData(false, isFirst);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ShowDetailActivity.this, Util.checkErrorType(error));
                emptyHintLayout.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadReviewData(final boolean isLoadMore, final boolean isFirst) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(EXPLORE_COMMENT_URL, topData.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ShowReviewListBean>() {
            @Override
            public void onResponse(ShowReviewListBean shareCommentBean) {
                if (isFinishing()) {
                    return;
                }
                hasNext = shareCommentBean.getData().getComments().isNext();
                if (isLoadMore) {
                    dataList.addAll(shareCommentBean.getData().getComments().getList());
                } else {
                    reviewData = shareCommentBean;
                    allReviewList = reviewData.getData().getComments().getList();
                    if (topData.getComments().size() > 0) {
                        dataList.add("热门评论");
                        dataList.addAll(topData.getComments());
                    }
                    if (allReviewList != null && allReviewList.size() > 0) {
                        dataList.add("最新评论");
                        dataList.addAll(allReviewList);
                    }
                    setDataToView(isFirst);
                }
                mIsRefreshing = false;
                emptyHintLayout.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                showDetailAdapter.notifySetListDataChanged(dataList);
                showDetailAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ShowDetailActivity.this, Util.checkErrorType(error));
                emptyHintLayout.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void shareVideo(ShareBean.DataEntity.MemberSharesEntity.ListEntity topData) {
        String fileFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        String fileName = topData.getId() + ".mp4";
        File file = new File(fileFolder, fileName);
        if (file.exists()) {
            PackageManager packageManager = getPackageManager();
            String packageName = "com.tencent.mm";
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage != null)
                startActivity(launchIntentForPackage);
            else
                Util.showToast(this, "未安装微信");
            Util.showToast(this, "视频已保存在相册下,快去分享吧~");
        } else {
            saveVideo(topData.getVideo(), file);
        }
    }

    private void saveVideo(final String videoUrl, final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveVideoFromNet(videoUrl, file);
            }
        }).start();
    }

    private int lastProgress;

    private void saveVideoFromNet(final String videoUrl, final File file) {
        float totalSize;// 文件总大小
        float downloadCount = 0;// 已经下载好的大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url = null;
        try {
            url = new URL(Constants.IMG_HOST + videoUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setReadTimeout(5 * 1000);
            httpURLConnection.setUseCaches(false);
            // 获取下载文件的size
            if (httpURLConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            totalSize = httpURLConnection.getContentLength();
            inputStream = httpURLConnection.getInputStream();
            outputStream = new FileOutputStream(file);// 文件存在则覆盖掉
            byte buffer[] = new byte[1024];
            int readsize = 0;
            while ((readsize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readsize);
                downloadCount += readsize;// 时时获取下载到的大小
                int progress = (int) ((downloadCount / totalSize) * 100);//获取进度条
                lastProgress = progress;
                Message message = Message.obtain();
                message.arg1 = progress;
                handler.sendMessage(message);
            }
            if (lastProgress == 99) {
                Message message = Message.obtain();
                message.arg1 = 100;
                handler.sendMessage(message);
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            Util.updateVideo(file.getAbsolutePath());
        } catch (Exception e) {
            file.delete();
            Log.e("tag", "错误" + e.getMessage());
        }
    }

    private void sharePics(List<String> pics) {
        if (pics == null || pics.size() == 0) {
            Util.showToast(this, "没有内容可分享");
            return;
        }
        savePics(pics);
    }

    private void savePics(final List<String> pics) {
        IMAGE_SIZE = 0;
        loadingDialog.show();
        int size = pics.size();
        final List<File> files = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int index = pics.get(i).lastIndexOf(".");
            int type = 1;
            if (index + 2 < pics.get(i).length()) {
                String s = pics.get(i).substring(index + 1, index + 2);
                if (s.equals("j")) {
                    type = 2;
                }
            }
            final int finalType = type;
            Glide.with(this).load(Util.getD2cPicUrl(pics.get(i),360,480)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource != null) {
                        IMAGE_SIZE++;
                    }
                    Bitmap bitmap = resource.copy(Bitmap.Config.RGB_565, true);
                    files.add(saveFile(bitmap, finalType));
                    bitmap=null;
                    if (IMAGE_SIZE == pics.size()) {
                        try {
                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");

                            ArrayList<Uri> imageUris = new ArrayList<Uri>();
                            for (File f : files) {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                    imageUris.add(Uri.fromFile(f));
                                } else {
                                    Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(ShowDetailActivity.this.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                                    imageUris.add(uri);
                                }
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            intent.putExtra("Kdescription", topData.getDescription());
                            ShowDetailActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(ShowDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("tag3", e.getMessage());
                            e.printStackTrace();
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }
                }
            });
        }
    }

    private File saveFile(Bitmap bigBitmap, int type) {
        if (bigBitmap.getByteCount() > 8485760) {
            float scale = 1;
            while (bigBitmap.getByteCount() > 8485760) {
                scale -= 0.2;
                bigBitmap = BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            bigBitmap.recycle();
            return null;
        }
        File root = getExternalCacheDir();
        File dir = new File(root, "wx");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = null;
        if (type == 1) {
            file = new File(dir, IMAGE_SIZE + ".png");
        } else {
            file = new File(dir, IMAGE_SIZE + ".jpg");
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (type == 1) {
                bigBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            } else {
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            }

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
                bigBitmap = null;
            }
            canvas = null;
        }
        return file;
    }


    @OnClick({R.id.btn_reload, R.id.tv_send_post, R.id.back_iv, R.id.top_right_iv, R.id.title_head_image, R.id.ll_like, R.id.ll_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reload:
                initData(true);
                break;
            case R.id.tv_send_post:
                sendPost();
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.top_right_iv:
                if (topData == null) {
                    return;
                }
                boolean isBuyer = false;
                if (user != null && user.getPartnerId() > 0) {
                    isBuyer = true;
                }
                sharePop = new SharePop(this, true, isSelf, isBuyer);
                sharePop.setShowId(id);
                if (user != null && user.getNickname() != null) {
                    sharePop.setTitle(String.format(getString(R.string.label_member_share1), topData.getMemberName()));
                }
                if (isBuyer) {
                    sharePop.setSharePopDownLoadClickListener(new SharePopDownLoadClickListener() {
                        @Override
                        public void downLoad() {
                            postShareRequest(topData.getId());
                            if (!Util.isEmpty(topData.getVideo())) {//为视频
                                shareVideo(topData);
                            } else {//图文
                                sharePics(topData.getPics());
                            }
                        }
                    });
                }
                sharePop.setDescription(topData.getDescription());
                if (topData.getPics().size() >= 1) {
                    sharePop.setImage(Util.getD2cPicUrl(topData.getPics().get(0), 100, 100), false);
                    sharePop.setImage(Util.getD2cPicUrl(topData.getPics().get(0), 360, 500), true);
                }
                sharePop.setWebUrl(Constants.SHARE_URL + "/membershare/" + topData.getId());
                sharePop.show(ShowDetailActivity.this.getWindow().getDecorView());
                break;
            case R.id.title_head_image:
                Intent intent = new Intent(ShowDetailActivity.this, PersonInfoActivity.class);
                intent.putExtra("memberId", topData.getMemberId());
                startActivity(intent);
                break;
            case R.id.ll_like:
                if (Util.loginChecked(this, 999)) {
                    likeShow();
                }
                break;
            case R.id.ll_review:
                if (Util.loginChecked(this, 999)) {
                    clickPostReview();
                }
                break;
        }
    }

    private void postDownLoadRequest(long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.DOWN_LOAD_SHOW_URL, id));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    private void postShareRequest(long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.SHARE_SHOW_URL, id));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    private void sendPost() {
        if (Util.loginChecked(this, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
            long sourceId = topData.getId();
            long memberShareUserId = topData.getMemberId();
            String content = etContent.getText().toString().trim();
            if (Util.isEmpty(content)) {
                return;
            }
            if (content.length() > 100) {
                Util.showToast(this, R.string.msg_comemnt_error);
                return;
            }
            PostReviewApi api = new PostReviewApi();
            api.content = content;
            api.sourceId = sourceId;
            api.memberShareUserId = memberShareUserId;
            if (!Util.isEmpty(toComment) && commentId > 0) {
                api.toCommentId = commentId;
            }
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    if (commentId == 0) {
                        saveContent = "";
                    } else {
                        responseMap.remove(memberId);
                    }
                    //清空et框，数据还原
                    resetEtContent();
                    //重新请求页面数据
                    mIsRefreshing = true;
                    dataList.clear();
                    page = 1;
                    Util.showToast(ShowDetailActivity.this, "发表评论成功");
                    initData(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(ShowDetailActivity.this, Util.checkErrorType(error));
                }
            });
            KeyboardUtil.hideKeyboard(etContent);//将软键盘关闭
        }
    }

    private void clickPostReview() {
        if (Util.loginChecked(this, Constants.Login.SEND_POST_LOGIN)) {
            //先清空
            resetEtContent();
            //显示用户之前输入过的
            etContent.setText(saveContent);
            //移动光标
            etContent.setSelection(etContent.getText().length());
            llShowInfo.setVisibility(View.GONE);
            llEdit.setVisibility(View.VISIBLE);
            outKeyBoard(etContent);
        }
    }

    @Subscribe
    public void onEvent(GlobalTypeBean globalTypeBean) {
        if (globalTypeBean.getType() == SHOW_DELETE) {
            finish();
        }
    }

    private void outKeyBoard(View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    private void resetEtContent() {
        etContent.setText("");
        etContent.setHint("");
        toComment = "";
        commentId = 0;
    }

    private void likeShow() {
        if (Util.loginChecked(this, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
            if (topData != null) {
                if (topData.getLiked() > 0) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(DELETE_SHARE_LIKE, id));
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            ivLike.setImageResource(R.mipmap.icon_fashion_like);
                            tvLikeNum.setText(String.valueOf(Integer.valueOf(tvLikeNum.getText().toString()) - 1));
                            topData.setLiked(0);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(ShowDetailActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(LIKE_SHARE_URL, id));
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            ivLike.setImageResource(R.mipmap.icon_fashion_liked);
                            tvLikeNum.setText(String.valueOf(Integer.valueOf(tvLikeNum.getText().toString()) + 1));
                            topData.setLiked(1);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(ShowDetailActivity.this, Util.checkErrorType(error));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        page = 1;
        dataList.clear();
        initData(true);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onWbShareSuccess() {
        super.onWbShareSuccess();
        if (sharePop != null) {
            sharePop.shareOut();
        }
    }
}
