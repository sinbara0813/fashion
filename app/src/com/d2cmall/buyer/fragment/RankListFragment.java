package com.d2cmall.buyer.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.RankRecyclerViewAdapter;
import com.d2cmall.buyer.api.RankApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.RankDesignerBean;
import com.d2cmall.buyer.bean.RankLiveBean;
import com.d2cmall.buyer.bean.RankMemberBean;
import com.d2cmall.buyer.bean.RankPicBean;
import com.d2cmall.buyer.bean.RankVideoBean;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ProgressCallBack;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ProgressDialog;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2017/8/10.
 */

public class RankListFragment extends BaseFragment {
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btn_back_top)
    ImageView btnBackTop;
    @Bind(R.id.rl_layout)
    RelativeLayout rlLayout;
    private RankRecyclerViewAdapter rankRecyclerViewAdapter;
    private List<Object> list = new ArrayList<>();
    private int id;
    private int p = 1;
    private boolean hasNext;
    private ProgressDialog progressDialog;
    private Dialog systemProgress;

    public static RankListFragment newInstance(int id) {
        RankListFragment rank = new RankListFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        rank.setArguments(args);
        return rank;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
        progressDialog = new ProgressDialog(mContext);
        EventBus.getDefault().register(this);
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void prepareView() {
        rlLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        final VirtualLayoutManager linearLayoutManager = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        systemProgress = DialogUtil.createLoadingDialog(mContext);
        rankRecyclerViewAdapter = new RankRecyclerViewAdapter(list, getActivity(), id);
        rankRecyclerViewAdapter.setProgressCallBack(new ProgressCallBack() {
            @Override
            public void setProgress(int progress, boolean isShare) {
                if (progress == 100) {
                    progressDialog.setProgress(0);
                    progressDialog.dismiss();
                    if (isShare) {
                        new android.support.v7.app.AlertDialog.Builder(mContext)
                                .setMessage(R.string.msg_go_wx)
                                .setPositiveButton("去上传", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PackageManager packageManager = mContext.getPackageManager();
                                        String packageName = "com.tencent.mm";
                                        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
                                        if (launchIntentForPackage != null)
                                            startActivity(launchIntentForPackage);
                                        else
                                            Util.showToast(mContext, "未安装微信");
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                } else {
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                    progressDialog.setProgress(progress);
                }
            }

            @Override
            public void letProgressGone() {
                systemProgress.dismiss();
            }

            @Override
            public void letProgressVisible() {
                systemProgress.show();
            }
        });
        DelegateAdapter delegateAdapter = new DelegateAdapter(linearLayoutManager, true);
        delegateAdapter.addAdapter(rankRecyclerViewAdapter);
        recycleView.setAdapter(delegateAdapter);
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                p = 1;
                loadData();
            }
        });
        recycleView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof ShowItemHolder) {
                    ShowItemHolder showItemHolder = (ShowItemHolder) holder;
                    NiceVideoPlayer niceVideoPlayer = showItemHolder.niceVideoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = linearLayoutManager.findLastVisibleItemPosition();
                        if (last > rankRecyclerViewAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            loadData();
                        }
                }
            }
        });
    }

    @Override
    public void doBusiness() {
        if (list.size() == 0) {
            loadData();
        }
        rankRecyclerViewAdapter.notifyDataSetChanged();
        recycleView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void loadData() {
        switch (id) {
            case 0:
                initPersonData();
                break;
            case 1:
                loadDesinerData();
                break;
            case 2:
                loadPicData();
                break;
            case 3:
                loadVideoData();
                break;
            case 4:
                loadLiveData();
                break;
        }
    }

    private void loadLiveData() {
        RankApi api = new RankApi();
        api.setInterPath(String.format(Constants.RANK_URL, "list"));
        api.setPageNumber(p);
        api.setPageSize(10);
        api.setType("live");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RankLiveBean>() {
            @Override
            public void onResponse(RankLiveBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    if (list != null) {
                        list.clear();
                    }
                }
                hasNext = response.getData().getHotLives().isNext();
                if (response.getData().getHotLives() != null && response.getData().getHotLives().getList().size() > 0) {
                    list.addAll(response.getData().getHotLives().getList());
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                } else if ((response.getData().getHotLives() == null || response.getData().getHotLives().getList().size() == 0) && list.size() == 0) {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                }
                rankRecyclerViewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (p > 1) {
                    Util.showToast(getActivity(), Util.checkErrorType(error));
                } else {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                    Util.showToast(getActivity(), Util.checkErrorType(error));
                }
            }
        });
    }

    private void loadVideoData() {
        RankApi api = new RankApi();
        api.setInterPath(String.format(Constants.RANK_URL, "list"));
        api.setPageNumber(p);
        api.setType("video");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RankVideoBean>() {
            @Override
            public void onResponse(RankVideoBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    if (list != null) {
                        list.clear();
                    }
                }
                hasNext = response.getData().getHotVideo().isNext();
                if (response.getData().getHotVideo() != null && response.getData().getHotVideo().getList().size() > 0) {
                    list.addAll(response.getData().getHotVideo().getList());
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                } else if ((response.getData().getHotVideo() == null || response.getData().getHotVideo().getList().size() == 0) && list.size() == 0) {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                }
                if (rankRecyclerViewAdapter != null) {
                    rankRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                recycleView.setVisibility(View.GONE);
                imgHint.setVisibility(View.VISIBLE);
                Util.showToast(getActivity(), Util.checkErrorType(error));
            }
        });
    }

    private void loadPicData() {
        RankApi api = new RankApi();
        api.setInterPath(String.format(Constants.RANK_URL, "list"));
        api.setPageNumber(p);
        api.setType("pic");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RankPicBean>() {
            @Override
            public void onResponse(RankPicBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    if (list != null) {
                        list.clear();
                    }
                }
                hasNext = response.getData().getHotPic().isNext();
                if (response.getData().getHotPic() != null && response.getData().getHotPic().getList().size() > 0) {
                    list.addAll(response.getData().getHotPic().getList());
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                } else if ((response.getData().getHotPic() == null || response.getData().getHotPic().getList().size() == 0) && list.size() == 0) {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                }
                if (rankRecyclerViewAdapter != null) {
                    rankRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                recycleView.setVisibility(View.GONE);
                imgHint.setVisibility(View.VISIBLE);
                Util.showToast(getActivity(), Util.checkErrorType(error));
            }
        });
    }

    private void initPersonData() {
        RankApi api = new RankApi();
        api.setInterPath(String.format(Constants.RANK_URL, "member"));
        api.setPageNumber(p);
        api.setPageSize(50);
        api.setType("member");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RankMemberBean>() {
            @Override
            public void onResponse(RankMemberBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    if (list != null) {
                        list.clear();
                    }
                }
                //hasNext=response.getData().getHotMember().isNext();
                if (response.getData().getHotMember() != null && response.getData().getHotMember().size() > 0) {
                    list.addAll(response.getData().getHotMember());
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                } else if ((response.getData().getHotMember() == null || response.getData().getHotMember().size() == 0) && list.size() == 0) {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                }
                rankRecyclerViewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                recycleView.setVisibility(View.GONE);
                imgHint.setVisibility(View.VISIBLE);
                Util.showToast(getActivity(), Util.checkErrorType(error));
            }
        });
    }


    private void loadDesinerData() {
        RankApi api = new RankApi();
        api.setInterPath(String.format(Constants.RANK_URL, "member"));
        api.setPageNumber(p);
        api.setPageSize(50);
        api.setType("designer");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RankDesignerBean>() {
            @Override
            public void onResponse(RankDesignerBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    if (list != null) {
                        list.clear();
                    }
                }
                //hasNext=response.getData().getHotDesigner().isNext();
                if (response.getData().getHotDesigner() != null && response.getData().getHotDesigner().size() > 0) {
                    list.addAll(response.getData().getHotDesigner());
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                } else if ((response.getData().getHotDesigner() == null || response.getData().getHotDesigner().size() == 0) && list.size() == 0) {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                }
                rankRecyclerViewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                recycleView.setVisibility(View.GONE);
                imgHint.setVisibility(View.VISIBLE);
                Util.showToast(getActivity(), Util.checkErrorType(error));
            }
        });
    }

    //用户详情也点击关注按钮,刷新列表数据
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GlobalTypeBean event) {

        int type = event.getType();
        if (type == Constants.GlobalType.LIKETHIS_LIST_CHANGE) {
            p = 1;
            list.clear();
            if (id == 0) {
                initPersonData();
            } else if (id == 1) {
                loadDesinerData();
            }
        } else if (type == Constants.GlobalType.LOGOUT || type == Constants.GlobalType.LOGIN_OK) {
            if (id == 2 || id == 3) {
                p = 1;
                loadData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_reload)
    public void onViewClicked() {
        p = 1;
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        if (rankRecyclerViewAdapter != null) {
            rankRecyclerViewAdapter.destroy();
        }
        super.onDestroy();
    }
}
