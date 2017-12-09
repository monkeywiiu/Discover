package com.example.discover.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.databinding.FragmentDiscoverBinding;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class DiscoverFragment extends BaseFragment<FragmentDiscoverBinding> {

    public boolean isPrepare = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
        loadData();
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }
        bindingView.text.setText("baiofhuidf");
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_discover;
    }

}
