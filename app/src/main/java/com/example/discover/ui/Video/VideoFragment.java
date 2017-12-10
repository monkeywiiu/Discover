package com.example.discover.ui.Video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.databinding.FragmentVideoBinding;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class VideoFragment extends BaseFragment<FragmentVideoBinding> {

    public boolean isPrepare = false;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;

    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }

        loadVideo();
    }

    public void loadVideo() {

    }
    @Override
    public int setContentView() {
        return R.layout.fragment_video;
    }
}
