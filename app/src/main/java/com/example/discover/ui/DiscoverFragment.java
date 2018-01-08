package com.example.discover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.discover.R;
import com.example.discover.TestActivity;
import com.example.discover.app.Constant;
import com.example.discover.base.BaseFragment;
import com.example.discover.bean.EyeBean;
import com.example.discover.databinding.FragmentDiscoverBinding;
import com.example.discover.http.cahe.ACache;
import com.example.discover.utils.DebugUtil;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class DiscoverFragment extends BaseFragment<FragmentDiscoverBinding> {

    private boolean isPrepare = false;

    private EyeBean eyeBean;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
        loadData();

        bindingView.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {
        if (!isPrepare || !isVisibile) {
            return;
        }
        ACache cache = ACache.get(getContext());
        eyeBean = (EyeBean) cache.getAsObject(Constant.EYE_VIDEO);
        if (eyeBean == null) {
            DebugUtil.debug("test111", "null");
        }
        bindingView.text.setText("bu");


    }

    @Override
    public int setContentView() {
        return R.layout.fragment_discover;
    }

}
