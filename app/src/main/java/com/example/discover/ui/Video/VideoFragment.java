package com.example.discover.ui.Video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

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

import rx.Subscription;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class VideoFragment extends BaseFragment<FragmentVideoBinding> {

    public VideoRecyclerAdapter videoAdapter;
    public int start = 15;
    public int num = 10;
    public boolean isPrepare = false;
    public EyeBean mEyeBean;
    public ACache mCache;
    public String test;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCache = ACache.get(getContext());
        initRecyclerView();
        isPrepare = true;
        loadData();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }
        //先从缓存读取数据，如果没有在请求
        mEyeBean = (EyeBean) mCache.getAsObject(Constant.EYE_VIDEO);
        if (mEyeBean != null) {

            setAdapter(mEyeBean);
            DebugUtil.debug("test12", mEyeBean.getItemList().get(0).getType());
        }else {
            loadVideo();
        }
    }

    public void loadVideo() {
        VideoModel.showVideo(start, num, new RequestListener() {
            @Override
            public void onSuccess(Object object) {

                EyeBean eyeBean = (EyeBean) object;
                DebugUtil.debug("test1", eyeBean.getItemList().size() + "");
                setAdapter(eyeBean);
                mCache.remove(Constant.EYE_VIDEO);
                mCache.put(Constant.EYE_VIDEO, eyeBean, 30000);
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
        videoAdapter = new VideoRecyclerAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.rvVideo.setLayoutManager(layoutManager);
        bindingView.rvVideo.setPullRefreshEnabled(false);
        bindingView.rvVideo.setLoadingMoreEnabled(false);

    }
    public void setAdapter(EyeBean eyeBean) {
        stopLoading();

        videoAdapter.addAll(eyeBean.getItemList());
        bindingView.rvVideo.setAdapter(videoAdapter);
    }
}
