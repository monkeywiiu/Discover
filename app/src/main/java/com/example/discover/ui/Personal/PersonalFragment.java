package com.example.discover.ui.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.discover.R;
import com.example.discover.adapter.MyFragmentPagerAdapter;
import com.example.discover.base.BaseFragment;
import com.example.discover.databinding.FragmentPersonalBinding;
import com.example.discover.ui.Personal.child.LikeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeyWiiu on 2017/12/30.
 */

public class PersonalFragment extends BaseFragment<FragmentPersonalBinding> {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragmentList();
        loadViewPager();
        showContentView();
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_personal;
    }
    public void initFragmentList() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new LikeFragment());
        fragmentList.add(new LikeFragment());

        titleList = new ArrayList<>();
        titleList.add("喜欢的作品");
        titleList.add("关注");
    }

    public void loadViewPager() {
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        bindingView.vpCollect.setAdapter(adapter);
        bindingView.vpCollect.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();
        bindingView.tabCollect.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabCollect.setupWithViewPager(bindingView.vpCollect);
    }
}
