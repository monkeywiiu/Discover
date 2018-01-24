package com.example.discover;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.discover.adapter.MyFragmentPagerAdapter;
import com.example.discover.databinding.ActivityAuthorHomeBinding;
import com.example.discover.ui.DiscoverFragment;
import com.example.discover.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

public class AuthorHomeActivity extends AppCompatActivity {

    private ActivityAuthorHomeBinding binding;
    private int authorId, color;
    private String authorName, authorDesc, authorIcon, authorBack;
    private List<android.support.v4.app.Fragment> fragmentList;
    private List<String> titleList;
    private ViewPager mViewPager;
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
        authorName = getIntent().getStringExtra("AuthorName");
        authorDesc = getIntent().getStringExtra("AuthorDesc");
        authorIcon = getIntent().getStringExtra("AuthorIcon");
        authorBack = getIntent().getStringExtra("AuthorBack");
        color = getIntent().getIntExtra("Color", 0);
        //填充基本数据
        binding.tvName.setText(authorName);
        binding.tvDesc.setText(authorDesc);
        Glide.with(this).load(authorIcon).into(binding.ivHead);
        Glide.with(this).load(authorBack).into(binding.ivBackground);
        binding.blurView.setOverlayColor(color);
        binding.tabItem.setBackgroundColor(color);
        binding.supView.setBackgroundColor(color);
        binding.ivHead.setBorderColor(color);

        //渐变
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                DebugUtil.debug("appbartest",appBarLayout.getY() + "//" + appBarLayout.getTotalScrollRange() + "//" + verticalOffset);

                float offsetAlpha = (appBarLayout.getY() / appBarLayout.getTotalScrollRange());
                binding.blurView.setAlpha( (offsetAlpha * -1));
                binding.ivHead.setAlpha(1 - 3 * (offsetAlpha * - 1));
            }
        });
        //点击
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new DiscoverFragment());

        titleList = new ArrayList<>();
        titleList.add("喜欢");
        titleList.add("待填充");
    }

    private void loadViewPager() {

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        binding.vpContent.setAdapter(adapter);
        binding.vpContent.setOffscreenPageLimit(2);
        binding.tabItem.setTabMode(TabLayout.MODE_FIXED);
        binding.tabItem.setupWithViewPager(binding.vpContent);
    }
}
