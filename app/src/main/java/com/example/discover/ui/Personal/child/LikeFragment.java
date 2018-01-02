package com.example.discover.ui.Personal.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.databinding.FragmentPersonalLikeBinding;
import com.example.discover.utils.DebugUtil;

/**
 * Created by monkeyWiiu on 2017/12/30.
 */

public class LikeFragment extends BaseFragment<FragmentPersonalLikeBinding> {
    private boolean isPrepare = false;
    private StringBuilder test;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;

        test = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            test.append("nihao");
        }
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }
        stopLoading();

        DebugUtil.debug("likefrag", "11");
        bindingView.tvTest.setText(test);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_personal_like;
    }
}
