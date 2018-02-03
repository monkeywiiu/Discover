package com.example.discover;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.discover.adapter.MyFragmentPagerAdapter;
import com.example.discover.databinding.ActivityMainBinding;
import com.example.discover.ui.DiscoverFragment;
import com.example.discover.ui.Personal.PersonalFragment;
import com.example.discover.ui.Search.SearchFragment;
import com.example.discover.ui.Video.VideoFragment;
import com.example.discover.ui.Welfare.WelFareFragment;
import com.example.zmenu.PUtils;


import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private ViewPager mViewPager;
    private ImageView mBottomItem1, mBottomItem2, mBottomItem3, mBottomItem4;
    private List<Fragment> fragmentList;
    private List<Integer> imageList;//XMenu的图片
    private List<Integer> colorList;//XMenu的颜色
    private boolean isFullSreen = false;
    private static boolean isExit = false;
    private static Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            isExit = false;
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化XMenu(自己做的一个自定义控件 https://github.com/monkeywiiu/Xmenu)
        initXMenu();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initBinding();
        initFragmentList();
        loadViewPager();
        initBottomBar();

        //创建litepal数据库
        Connector.getDatabase();
    }

    public void initXMenu(){
        initXMenuResource();
        //设置图片和颜色
        if (imageList != null && colorList != null){
           PUtils.getInstance().setImagesAndColors(imageList, colorList);
        }

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

    public void initFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new VideoFragment()); //开眼视频页面
        fragmentList.add(new SearchFragment());
        fragmentList.add(new WelFareFragment());
        fragmentList.add(new PersonalFragment());
    }

    public void loadViewPager(){
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mBottomItem1.setSelected(true);
                    mBottomItem2.setSelected(false);
                    mBottomItem3.setSelected(false);
                    mBottomItem4.setSelected(false);
                } else if (position == 1) {
                    mBottomItem2.setSelected(true);
                    mBottomItem1.setSelected(false);
                    mBottomItem3.setSelected(false);
                    mBottomItem4.setSelected(false);
                } else if (position == 2) {
                    mBottomItem3.setSelected(true);
                    mBottomItem1.setSelected(false);
                    mBottomItem2.setSelected(false);
                    mBottomItem4.setSelected(false);
                } else if (position == 3) {
                    mBottomItem4.setSelected(true);
                    mBottomItem1.setSelected(false);
                    mBottomItem2.setSelected(false);
                    mBottomItem3.setSelected(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initBottomBar() {
        //设置bottom点击选中事件
        mBottomItem1.setSelected(true);
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

    @Override
    public void onBackPressed() {
        //全屏播放时退出全屏
        if (JZVideoPlayer.backPress()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!JZVideoPlayer.backPress()) {
                exit();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //双击退出
    private void exit() {

        if (!isExit) {
            isExit = true;
            Snackbar snackbar = Snackbar.make(mViewPager, "再按一次后退键退出程序", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundResource(R.color.colorAccent);
            snackbar.show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            this.finish();
        }
    }

}
