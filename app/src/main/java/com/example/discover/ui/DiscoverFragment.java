package com.example.discover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.discover.R;
import com.example.discover.TestActivity;
import com.example.discover.app.Constant;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.HotEyeBean;
import com.example.discover.databinding.FragmentDiscoverBinding;
import com.example.discover.http.cahe.ACache;
import com.example.discover.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class DiscoverFragment extends BaseFragment<FragmentDiscoverBinding> {

    private boolean isPrepare = false;

    private HotEyeBean hotEyeBean;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        isPrepare = true;
        loadData();

        bindingView.testText.setText(Test());

    }

    public StringBuffer Test() {
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < 2000; i++) {

            text.append("你好你好你好");
        }
        return text;
    }
    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }
        /*ACache cache = ACache.get(getContext());
        hotEyeBean = (HotEyeBean) cache.getAsObject(Constant.EYE_VIDEO);
        if (hotEyeBean == null) {
            DebugUtil.debug("test111", "null");
        }
        bindingView.text.setText("bu");*/


    }

    @Override
    public int setContentView() {
        return R.layout.fragment_discover;
    }

}
