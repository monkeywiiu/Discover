package com.example.discover.ui.Personal.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.LitePalBean.Video;
import com.example.discover.databinding.FragmentPersonalLikeBinding;
import com.example.discover.utils.DebugUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by monkeyWiiu on 2017/12/30.
 */

public class LikeFragment extends BaseFragment<FragmentPersonalLikeBinding> {
    private boolean isPrepare = false;
    private List<Video> videoList;
    private boolean isFirst = true;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
        videoList = DataSupport.findAll(Video.class);
        test();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile || !isFirst) {
            return;
        }
        //隐藏loading
        stopLoading();

        test();

        //避免重复加载
        isFirst = false;

    }

    public void test() {
        for (Video video:videoList) {
            DebugUtil.debug("likeVideo", video.getDescription());
            DebugUtil.debug("likeVideo", video.getPlayUrl());
            DebugUtil.debug("likeVideo", video.getTitle());
            DebugUtil.debug("likeVideo", video.getSize() + "");
            DebugUtil.debug("likeVideo", video.getVideoId() + "");
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_personal_like;
    }
}
