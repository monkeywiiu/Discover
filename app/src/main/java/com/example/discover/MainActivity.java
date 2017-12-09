package com.example.discover;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.discover.adapter.MyFragmentPagerAdapter;
import com.example.discover.databinding.ActivityMainBinding;
import com.example.discover.ui.DiscoverFragment;
import com.example.zmenu.PUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding mBinding;
    public ViewPager mViewPager;
    public ImageView mBottomItem1, mBottomItem2, mBottomItem3, mBottomItem4;
    public List<Fragment> fragmentList;
    public List<Integer> imageList;//XMenu的图片
    public List<Integer> colorList;//XMenu的颜色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化XMenu
        initXMenu();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initBinding();
        initFragment();
        loadViewPager();
        initBottomBar();
    }

    public void initXMenu(){
        initXMenuResource();
        //设置图片和颜色
        PUtils.getInstance().setImagesAndColors(imageList, colorList);
        //设置xMenu可见
        PUtils.getInstance().setVisible(true);

        //设置悬浮按钮的margin
        PUtils.getInstance().setMargin((int) getResources().getDimension(R.dimen.xMenuMarginRight),
                (int) getResources().getDimension(R.dimen.xMenuMarginBottom));
    }
    public void initXMenuResource() {
        imageList = new ArrayList<>();
        colorList = new ArrayList<>();
        imageList.add(R.drawable.image1);
        imageList.add(R.drawable.image2);
        imageList.add(R.drawable.image3);
        imageList.add(R.drawable.image4);
        imageList.add(R.drawable.image5);
        imageList.add(R.drawable.image6);

        colorList.add(R.color.background1);
        colorList.add(R.color.background2);
        colorList.add(R.color.background3);
        colorList.add(R.color.background4);
        colorList.add(R.color.background5);
        colorList.add(R.color.background6);
    }

    public void initBinding() {
        mViewPager = mBinding.mainViewpager;
        mBottomItem1 = mBinding.bottomBar.one;
        mBottomItem2 = mBinding.bottomBar.two;
        mBottomItem3 = mBinding.bottomBar.three;
        mBottomItem4 = mBinding.bottomBar.four;
    }

    public void initFragment() {
        fragmentList = new ArrayList<>();
      ;
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new DiscoverFragment());
    }

    public void loadViewPager(){
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
    }

    public void initBottomBar() {
        mBottomItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomItem1.setSelected(true);
                mBottomItem2.setSelected(false);
                mBottomItem3.setSelected(false);
                mBottomItem4.setSelected(false);
                mViewPager.setCurrentItem(0);
            }
        });

        mBottomItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomItem2.setSelected(true);
                mBottomItem1.setSelected(false);
                mBottomItem3.setSelected(false);
                mBottomItem4.setSelected(false);
                mViewPager.setCurrentItem(1);
            }
        });

        mBottomItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomItem3.setSelected(true);
                mBottomItem1.setSelected(false);
                mBottomItem2.setSelected(false);
                mBottomItem4.setSelected(false);
                mViewPager.setCurrentItem(2);
            }
        });

        mBottomItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomItem4.setSelected(true);
                mBottomItem1.setSelected(false);
                mBottomItem2.setSelected(false);
                mBottomItem3.setSelected(false);
                mViewPager.setCurrentItem(3);
            }
        });
    }
}
