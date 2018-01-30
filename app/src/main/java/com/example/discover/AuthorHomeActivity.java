package com.example.discover;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.discover.adapter.MyFragmentPagerAdapter;
import com.example.discover.databinding.ActivityAuthorHomeBinding;
import com.example.discover.ui.DiscoverFragment;
import com.example.discover.ui.Search.Author.ItemFragment;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.LitePalUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZVideoPlayer;
import io.reactivex.functions.Consumer;

public class AuthorHomeActivity extends AppCompatActivity {

    private ActivityAuthorHomeBinding binding;
    private int authorId;
    private List<android.support.v4.app.Fragment> fragmentList;
    private List<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_author_home);

        init();
        initFragmentList();
        loadViewPager();
    }

    private void init() {
        authorId = getIntent().getIntExtra("AuthorId", 0);
        boolean isFollowing = getIntent().getBooleanExtra("Following", false);
        final String authorName = getIntent().getStringExtra("AuthorName");
        final String authorDesc = getIntent().getStringExtra("AuthorDesc");
        final String authorIcon = getIntent().getStringExtra("AuthorIcon");
        final String authorBack = getIntent().getStringExtra("AuthorBack");
        final int color = getIntent().getIntExtra("Color", 0);

        //填充基本数据
        binding.tvName.setText(authorName);
        binding.toolbarId.setText(authorName);
        binding.tvDesc.setText(authorDesc);
        Glide.with(this).load(authorIcon).into(binding.ivHead);
        Glide.with(this).load(authorBack).into(binding.ivBackground);
        binding.blurView.setOverlayColor(color);
        binding.tabItem.setBackgroundColor(color);
        binding.supView.setBackgroundColor(color);
        binding.ivHead.setBorderColor(color);
        binding.llText.setBackgroundColor(color);
        if (isFollowing) {
            binding.attention.setVisibility(View.GONE);
        }
        //渐变
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                DebugUtil.debug("appbartest",appBarLayout.getY() + "//" + appBarLayout.getTotalScrollRange() + "//" + verticalOffset);

                float offsetAlpha = (appBarLayout.getY() / appBarLayout.getTotalScrollRange());
                binding.blurView.setAlpha( 2 * (offsetAlpha * -1));
                binding.ivHead.setAlpha(1 - 3 * (offsetAlpha * - 1));
            }
        });
        //点击事件
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RxView.clicks(binding.attention)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //点击关注
                        binding.attention.setVisibility(View.GONE);
                        LitePalUtil.addToFollow(authorId, authorName, authorDesc, authorIcon, color, authorBack);
                        Toast.makeText(AuthorHomeActivity.this, "你关注了作者", Toast.LENGTH_SHORT).show();
                        //待完善
                    }
                });
    }


    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(ItemFragment.newInstance("date", authorId)); //按日期排序
        fragmentList.add(ItemFragment.newInstance("shareCount", authorId)); //按时间排序

        titleList = new ArrayList<>();
        titleList.add("按时间");
        titleList.add("按热度");
    }

    private void loadViewPager() {

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        binding.vpContent.setAdapter(adapter);
        binding.vpContent.setOffscreenPageLimit(2);
        binding.tabItem.setTabMode(TabLayout.MODE_FIXED);
        binding.tabItem.setupWithViewPager(binding.vpContent);
    }

    @Override
    protected void onDestroy() {
        JZVideoPlayer.releaseAllVideos();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        //全屏播放时退出全屏
        if (JZVideoPlayer.backPress()) {
            return;
        }

        super.onBackPressed();
    }

}
