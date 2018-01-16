package com.example.discover.ui.Personal.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.discover.R;
import com.example.discover.adapter.LikeVideoRecyclerAdapter;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.LitePalBean.LikeVideo;
import com.example.discover.databinding.FragmentPersonalLikeBinding;
import com.example.discover.ui.RecyclerViewNoBugLinearLayoutManager;
import com.example.discover.utils.DebugUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by monkeyWiiu on 2017/12/30.
 */

public class LikeFragment extends BaseFragment<FragmentPersonalLikeBinding> {
    private boolean isPrepare = false;
    private List<LikeVideo> likeVideoList;
    private boolean isFirst = true;
    private LikeVideoRecyclerAdapter mLikeVideoAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mNum = 5;
    private int currentId;
    private int largestId;
    private int totalNum;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
        likeVideoList = DataSupport.order("id desc").limit(mNum).find(LikeVideo.class);
        if (DataSupport.findLast(LikeVideo.class) != null) {
            largestId = DataSupport.findLast(LikeVideo.class).getId();
        }
        totalNum = DataSupport.findAll(LikeVideo.class).size();
        initRecyclerView();
        loadData();
        showContentView();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile || !isFirst) {
            return;
        }
        setAdapter();
        //避免重复加载
        isFirst = false;

    }

    public void initRecyclerView() {

        mLikeVideoAdapter = new LikeVideoRecyclerAdapter(getContext());
        mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(getActivity());
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
                        likeVideoList = DataSupport.order("id desc").limit(mNum).find(LikeVideo.class);
                        totalNum = DataSupport.findAll(LikeVideo.class).size();
                        if (likeVideoList != null && likeVideoList.size() > 0) {
                            currentId = likeVideoList.get(likeVideoList.size() - 1).getId();
                            DebugUtil.debug("totalTest", "currentId:" + currentId);
                            largestId = DataSupport.findLast(LikeVideo.class).getId();
                            DebugUtil.debug("totalTest", "largestId" + largestId);
                        }
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
                final int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount - 1 && !mLikeVideoAdapter.isLoading()) {

                    mLikeVideoAdapter.updateStateLoad(true);
                    likeVideoList = DataSupport.order("id desc").limit(mNum).where("id < ?", String.valueOf(currentId)).find(LikeVideo.class);
                    if (likeVideoList != null && likeVideoList.size() > 0) {
                        currentId = likeVideoList.get(likeVideoList.size() - 1).getId();
                        DebugUtil.debug("totalTest", "currentIdLoad" + currentId);
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (totalItemCount - 1 == totalNum) {
                                mLikeVideoAdapter.hideLoading();
                            }
                            mLikeVideoAdapter.updateStateLoad(false);
                            mLikeVideoAdapter.addAll(likeVideoList);
                            mLikeVideoAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
            }
        });

        //删除item
        mLikeVideoAdapter.setOnClickListener(new LikeVideoRecyclerAdapter.MyDeleteClickListener() {
            @Override
            public void onDelete(int position, int id) {

                DataSupport.deleteAll(LikeVideo.class, "id = ?", String.valueOf(id));
                totalNum = DataSupport.findAll(LikeVideo.class).size();
                DebugUtil.debug("totalTest", "largestId" + largestId + "position:" + position);
                mLikeVideoAdapter.delete(position);
            }
        });
    }

    public void setAdapter() {
        mLikeVideoAdapter.clear();
        mLikeVideoAdapter.addAll(likeVideoList);
        bindingView.rvLikeVideo.setAdapter(mLikeVideoAdapter);
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_personal_like;
    }


}
