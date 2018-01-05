package com.example.discover.ui.Personal.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.discover.R;
import com.example.discover.adapter.LikeVideoRecyclerAdapter;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.LitePalBean.Video;
import com.example.discover.databinding.FragmentPersonalLikeBinding;
import com.example.discover.utils.DebugUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by monkeyWiiu on 2017/12/30.
 */

public class LikeFragment extends BaseFragment<FragmentPersonalLikeBinding> {
    private boolean isPrepare = false;
    private List<Video> likeVideoList;
    private boolean isFirst = true;
    private LikeVideoRecyclerAdapter mLikeVideoAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mPage = 1;
    private int mNum = 15;
    private int currentStart;
    private int currentEnd;
    private int total;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
        likeVideoList = DataSupport.order("id desc").find(Video.class);
        if (DataSupport.findLast(Video.class) != null) {
            total = DataSupport.findLast(Video.class).getId();
        }
        initRecyclerView();
        loadData();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile || !isFirst) {
            return;
        }
        //隐藏loading
        //stopLoading();

        //test();
        setAdapter();

        //避免重复加载
        isFirst = false;

    }


    public void initRecyclerView() {

        mLikeVideoAdapter = new LikeVideoRecyclerAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.rvLikeVideo.setLayoutManager(mLayoutManager);
        bindingView.srlRefresh.setColorSchemeResources(R.color.background5, R.color.background2, R.color.background4);
        bindingView.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JZVideoPlayer.goOnPlayOnPause();
                bindingView.rvLikeVideo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeVideoList = DataSupport.order("id desc").limit(15).find(Video.class);
                        DebugUtil.debug("refreshTest", likeVideoList.size() + "");
                        setAdapter();
                        mLikeVideoAdapter.notifyDataSetChanged();
                        bindingView.srlRefresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        //上拉加载更多
        bindingView.rvLikeVideo.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                DebugUtil.debug("position1", lastVisibleItem + "  " + totalItemCount);
                if (lastVisibleItem == totalItemCount - 1 && !mLikeVideoAdapter.isLoading()) {
                    mLikeVideoAdapter.updateStateLoad(true);
                    mPage++;
                    currentStart = (mPage - 1) * mNum;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLikeVideoAdapter.updateStateLoad(false);
                            likeVideoList = DataSupport.order("id desc").limit(mNum).offset(currentStart).find(Video.class);
                            mLikeVideoAdapter.addAll(likeVideoList);
                            mLikeVideoAdapter.notifyDataSetChanged();
                        }
                    }, 500);

                }
            }
        });
    }

    public void setAdapter() {
        stopLoading();
        mLikeVideoAdapter.clear();
        mLikeVideoAdapter.addAll(likeVideoList);
        bindingView.rvLikeVideo.setAdapter(mLikeVideoAdapter);
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_personal_like;
    }
}
