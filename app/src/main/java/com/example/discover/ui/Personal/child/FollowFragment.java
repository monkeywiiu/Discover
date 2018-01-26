package com.example.discover.ui.Personal.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.discover.R;
import com.example.discover.adapter.FollowRecyclerAdapter;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.LitePalBean.Follow;
import com.example.discover.bean.LitePalBean.LikeVideo;
import com.example.discover.databinding.FragmentFollowBinding;
import com.example.discover.utils.DebugUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/26.
 */

public class FollowFragment extends BaseFragment<FragmentFollowBinding>{

    private boolean isPrepare = false;
    private boolean isFirst = true;
    private int mNum = 15;
    private int currentId;
    private int totalNum;
    private List<Follow> followList;
    private FollowRecyclerAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        showContentView();
        totalNum = DataSupport.findAll(Follow.class).size();
        isPrepare = true;
    }

    @Override
    protected void loadData() {
        if (!isVisibile || !isPrepare || !isFirst) {
            return;
        }

        followList = DataSupport.order("id desc").limit(mNum).find(Follow.class);
        if (followList != null) {
            setAdapter(followList);
        }


        //避免重复加载
        isFirst = false;
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        bindingView.rvFollow.setLayoutManager(mLayoutManager);

        //下拉刷新
        bindingView.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.rvFollow.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        followList = DataSupport.order("id desc").limit(mNum).find(Follow.class);
                        totalNum = DataSupport.findAll(Follow.class).size();
                        if (followList != null && followList.size() > 0) {
                            currentId = followList.get(followList.size() - 1).getId();
                        }
                        setAdapter(followList);
                        adapter.notifyDataSetChanged();
                        bindingView.srlRefresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        //上拉加载更多
        bindingView.rvFollow.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                final int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount - 1 && !adapter.isLoading()) {

                    adapter.updateStateLoad(true);
                    followList = DataSupport.order("id desc").limit(mNum).where("id < ?", String.valueOf(currentId)).find(Follow.class);
                    if (followList != null && followList.size() > 0) {
                        currentId = followList.get(followList.size() - 1).getId();
                        DebugUtil.debug("totalTest", "currentIdLoad" + currentId);
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DebugUtil.debug("followtest", totalItemCount +"/" +totalNum);
                            if (totalItemCount - 1 == totalNum) {
                                adapter.hideLoading();
                            }
                            adapter.updateStateLoad(false);
                            adapter.addAll(followList);
                            adapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
            }
        });
    }

    private void setAdapter(List<Follow> list) {

        adapter = new FollowRecyclerAdapter(getContext());
        adapter.clear();
        adapter.addAll(list);

        bindingView.rvFollow.setAdapter(adapter);

    }
    @Override
    public int setContentView() {
        return R.layout.fragment_follow;
    }
}
