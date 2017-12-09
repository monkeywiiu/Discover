package com.example.discover.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> mFragmentList;
    public List<Integer> mTabList;
    public Context mContext;

   /* private int[] imageResId = {
            R.drawable.figure_normal,
            R.drawable.search_normal,
            R.drawable.smile_normal,
            R.drawable.figure_normal
    };*/

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mFragmentList = list;
    }
    public MyFragmentPagerAdapter(FragmentManager fm, Context context, List<Fragment> list, List<Integer> tabList) {
        super(fm);
        mFragmentList = list;
        mTabList = tabList;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}
