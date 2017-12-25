package com.example.discover.ui.Video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.discover.R;
import com.example.discover.adapter.VideoRecyclerAdapter;
import com.example.discover.app.Constant;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.EyeBean;
import com.example.discover.databinding.FragmentVideoBinding;
import com.example.discover.http.RequestListener;
import com.example.discover.http.cahe.ACache;
import com.example.discover.model.VideoModel;
import com.example.discover.utils.DebugUtil;
import com.example.zmenu.FloatButton;
import com.example.zmenu.PUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import rx.Subscription;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class VideoFragment extends BaseFragment<FragmentVideoBinding> {

    private VideoRecyclerAdapter mVideoAdapter;
    private int start = 5; //前几个数据可能没有video，所以从5开始
    private int num = 10;
    private int mPage = 1;
    private boolean isPrepare = false;
    private boolean isFirst = true;
    private EyeBean mEyeBean;
    private ACache mCache;
    private List<FloatButton> floatButtons;//XMenu的悬浮按钮
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCache = ACache.get(getContext());
        floatButtons = PUtils.getInstance().getViewList(); //获取悬浮按钮
        initRecyclerView();

        //准备就绪
        isPrepare = true;
        //解决懒加载首页不显示，手动loadData()
        loadData();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile || !isFirst) {
            return;
        }
        //先从缓存读取数据，如果没有在请求
        mEyeBean = (EyeBean) mCache.getAsObject(Constant.EYE_VIDEO);
        if (mEyeBean != null ) {
            //getAchecaData();
            setAdapter(mEyeBean);
           // DebugUtil.debug("test12", mEyeBean.getItemList().get(0).getType());
        }else {
            loadVideo();
        }
        //避免重复加载
        isFirst = false;
    }

    public void loadVideo() {
        VideoModel.showVideo(start, num, new RequestListener() {
            @Override
            public void onSuccess(Object object) {
                EyeBean eyeBean = (EyeBean) object;
                if (mPage == 1) {
                    if(eyeBean != null && eyeBean.getItemList() != null&& eyeBean.getItemList().size() > 0) {
                        bindingView.rvVideo.refreshComplete();
                        DebugUtil.debug("test1", eyeBean.getItemList().get(0).getData().getPlayInfo().size() + "");
                        setAdapter(eyeBean);
                        //缓存5小时
                        mCache.remove(Constant.EYE_VIDEO);
                        mCache.put(Constant.EYE_VIDEO, eyeBean, 18000);
                    }
                } else {
                    if(eyeBean != null && eyeBean.getItemList() != null&& eyeBean.getItemList().size() > 0) {
                        bindingView.rvVideo.loadMoreComplete();
                        mVideoAdapter.addAll(eyeBean.getItemList());
                        mVideoAdapter.notifyDataSetChanged();
                    } else {
                        //数据刷新到底了
                        bindingView.rvVideo.setNoMore(true);
                    }
                }
            }

            @Override
            public void onFailed() {
                //DebugUtil.toast(getActivity(), "failed");
            }

            @Override
            public void addSubscription(Subscription subscription) {
                addMySubscription(subscription);
            }
        });

    }
    @Override
    public int setContentView() {
        return R.layout.fragment_video;
    }

    public void initRecyclerView() {
        mVideoAdapter = new VideoRecyclerAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.rvVideo.setLayoutManager(layoutManager);
        bindingView.rvVideo.setPullRefreshEnabled(true);
        bindingView.rvVideo.setLoadingMoreEnabled(true);
        //设置recycler上拉加载和下拉刷新监听
        bindingView.rvVideo.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                JZVideoPlayer.goOnPlayOnPause();
                mPage = 1;
                start = 5;
                loadVideo();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                start += num;
                loadVideo();
            }
        });
        //设置recycler滚动监听
        bindingView.rvVideo.setOnScrollListener(new RecyclerView.OnScrollListener() {
            /*@Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                DebugUtil.debug("scrolltest", "" + dy);
                //-2<dy<2视为不滚动
                if (-2 <= dy && dy <= 2) {
                    for (int i = 0; i < floatButtons.size(); i++) {
                        floatButtons.get(i).show();
                    }
                } else {
                    for (int i = 0; i < floatButtons.size(); i++) {
                        floatButtons.get(i).hide();
                    }
                }

            }*/

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == XRecyclerView.SCROLL_STATE_IDLE) {
                    for (int i = 0; i < floatButtons.size(); i++) {
                        floatButtons.get(i).show();
                    }
                } else {
                    for (int i = 0; i < floatButtons.size(); i++) {
                        floatButtons.get(i).hide();
                    }
                }
            }
        });
    }
    public void setAdapter(EyeBean eyeBean) {
        stopLoading();
        mVideoAdapter.clear();
        mVideoAdapter.addAll(eyeBean.getItemList());
        bindingView.rvVideo.setAdapter(mVideoAdapter);
    }

    //取缓存
    public void getAchecaData() {
        mEyeBean = (EyeBean) mCache.getAsObject(Constant.EYE_VIDEO);
        //如果是第一次打开再加载
        if (isFirst) {
            setAdapter(mEyeBean);
            isFirst = false;
        }
    }

    @Override
    public void onPause() {
        JZVideoPlayer.releaseAllVideos();
        super.onPause();
    }

}
