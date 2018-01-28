package com.example.discover.ui.Search.Author;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.discover.R;
import com.example.discover.adapter.VideoRecyclerAdapter;
import com.example.discover.app.Constant;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.AuthorDetailBean;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.bean.HotEyeBean;
import com.example.discover.databinding.FragmentVideoBinding;
import com.example.discover.http.RequestListener;
import com.example.discover.http.cahe.ACache;
import com.example.discover.model.AuthorVideoModel;
import com.example.discover.model.HotVideoModel;
import com.example.discover.ui.RecyclerViewNoBugLinearLayoutManager;
import com.example.discover.utils.DebugUtil;
import com.example.zmenu.FloatButton;
import com.example.zmenu.PUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by monkeyWiiu on 2018/1/23.
 */

public class ItemFragment extends BaseFragment<FragmentVideoBinding> {

    private static final String STRATEGY = "Strategy";
    private static final String ID = "AuthorId" ;
    private int id;
    private int start = 0;
    private int num = 10;//一次请求的数目

    private String mStrategy;
    private int mPage = 1;
    private boolean isPrepare = false;
    private boolean isFirst = true;
    private VideoRecyclerAdapter mVideoAdapter;
    private LinearLayoutManager mLayoutManager;

    public static ItemFragment newInstance(String strategy, int id) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(STRATEGY, strategy);
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStrategy = getArguments().getString(STRATEGY);
            id = getArguments().getInt(ID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        //准备就绪
        isPrepare = true;
        //解决懒加载首页不显示，手动loadData()
        if ("date".equals(mStrategy)) {
            loadData();
        }

    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile || !isFirst) {
            return;
        }
        loadVideo();

        //避免重复加载
        isFirst = false;
    }

    public void loadVideo() {
        DebugUtil.debug("loadvideo",  "load");
        AuthorVideoModel.showVideo(this, start, id, mStrategy, new RequestListener() {
            @Override
            public void onSuccess(Object object) {

                showContentView();
                AuthorDetailBean detailBean = (AuthorDetailBean) object;
                List<ItemList> list = detailBean.getItemList();
               // DebugUtil.debug("loadvideo", list.size() + "");




                if (mPage == 1) {
                    if(detailBean != null && detailBean.getItemList() != null&& detailBean.getItemList().size() > 0) {

                        DebugUtil.debug("test123456", "video" + detailBean.getItemList().size());
                        bindingView.srlVideo.setRefreshing(false);
                        setAdapter(detailBean);
                    }
                } else {
                    if(detailBean != null && detailBean.getItemList() != null&& detailBean.getItemList().size() > 0) {
                        mVideoAdapter.updateStateLoad(false);
                        mVideoAdapter.addAll(detailBean.getItemList());
                        mVideoAdapter.notifyDataSetChanged();
                    } else {
                        //数据刷新到底了
                        mVideoAdapter.hideLoading();
                    }
                }

            }

            @Override
            public void onFailed(Throwable t) {

                DebugUtil.debug("loadvideo",  "failed");
                bindingView.srlVideo.setRefreshing(false);
                if (mVideoAdapter.getItemCount() == 1) {
                    Error();
                }
            }
            @Override
            public void onCompleted() {

            }
        });

    }
    @Override
    public int setContentView() {
        return R.layout.fragment_video;
    }

    public void initRecyclerView() {
        mVideoAdapter = new VideoRecyclerAdapter(getContext());
        mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.rvVideo.setLayoutManager(mLayoutManager);

        bindingView.srlVideo.setColorSchemeResources(R.color.background5, R.color.background2, R.color.background4);
        //上拉刷新
        bindingView.srlVideo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JZVideoPlayer.goOnPlayOnPause();
                bindingView.srlVideo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 1;
                        start = 5;
                        loadVideo();
                    }
                }, 1000);
            }
        });
        //设置recycler滚动监听
        bindingView.rvVideo.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //下拉加载更多
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                DebugUtil.debug("position1", lastVisibleItem + "  " + totalItemCount);
                if (lastVisibleItem == totalItemCount - 1 && !mVideoAdapter.isLoading()) {
                    mVideoAdapter.updateStateLoad(true);
                    mPage++;
                    start += num;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadVideo();
                        }
                    }, 1000);

                }
            }

        });

    }
    public void setAdapter(AuthorDetailBean authorDetailBean) {
        mVideoAdapter.clear();
        mVideoAdapter.addAll(authorDetailBean.getItemList());
        bindingView.rvVideo.setAdapter(mVideoAdapter);
    }

    @Override
    public void onPause() {
        JZVideoPlayer.releaseAllVideos();
        super.onPause();
    }

}
