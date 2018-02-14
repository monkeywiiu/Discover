package com.example.discover;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.discover.adapter.ReplyAdapter;
import com.example.discover.app.Constant;
import com.example.discover.app.DiscoverApplication;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.bean.DetailBean.Replies;
import com.example.discover.bean.DetailBean.ReplyList;
import com.example.discover.databinding.ItemMovieDetailHeaderBinding;
import com.example.discover.http.RequestListener;
import com.example.discover.model.ReplyModel;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.IntentManager;
import com.example.discover.utils.LitePalUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.functions.Consumer;

public class VideoDetailActivity extends RxAppCompatActivity {

    private ItemList item;
    private ItemMovieDetailHeaderBinding headerBinding;
    private ReplyAdapter replyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<ReplyList> replyLists = new ArrayList<>();
    private boolean firstRequest = true;
    private boolean loading = false;
    private int lastSequence;
    private RecyclerView replies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        init();
        initJZPlayer();
        loadReply();
    }

    public void init() {

        item = (ItemList) getIntent().getSerializableExtra("item");
        replies = findViewById(R.id.rv_replies);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        replies.setLayoutManager(linearLayoutManager);
        View view =DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_movie_detail_header, replies, false).getRoot();
        headerBinding = DataBindingUtil.getBinding(view);
        headerBinding.setItem(item);

        RxView.clicks(headerBinding.authorContent)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        JZVideoPlayer.releaseAllVideos();
                        IntentManager.fromDetailtoAuthor(headerBinding.authorContent.getContext(), item);
                    }
                });
        replyAdapter = new ReplyAdapter(replyLists, view);
        replies.setAdapter(replyAdapter);

        //上拉加载
        replies.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount - 5 && !loading) {

                    loading = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            loadReply();
                        }
                    }, 1000);

                }
            }
        });

        //点击收藏

        headerBinding.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getTag() == null) {
                    headerBinding.collect.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_full_24dp_pink));
                    //入库
                    LitePalUtil.addVideoToFavor(item.getData().getId(), item.getData().getTitle(), item.getData().getDescription(),
                            item.getData().getPlayUrl(), item.getData().getCover().getDetail(),item.getData().getAuthor().getName(),
                            item.getData().getAuthor().getId(),item.getData().getAuthor().getIcon(),item.getData().getAuthor().getDescription(),
                            (int) Constant.LabelMap.get(item.getData().getCategory()), item.getData().getCategory(), 0);
                    item.setTag("true");
                } else if ("true".equals(item.getTag())){
                    headerBinding.collect.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_hollow_24dp));
                    //出库
                    LitePalUtil.deleteVideoFromFavor(item.getData().getId());
                    item.setTag(null);
                }

            }
        });

    }
    public void initJZPlayer() {
        JZVideoPlayerStandard videoPlayerStandard = findViewById(R.id.jz_player);

        Glide.with(this).load(item.getData().getCover().getDetail())
                .crossFade(800)
                .placeholder(R.drawable.cross_image)
                .error(R.drawable.cross_image)
                .into(videoPlayerStandard.thumbImageView);
        videoPlayerStandard.setUp(item.getData().getPlayUrl(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
        videoPlayerStandard.backButton.setVisibility(View.VISIBLE);

        videoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void loadReply() {
        ReplyModel.showReplies(this, firstRequest, lastSequence, item.getData().getId(), new RequestListener() {
            @Override
            public void onSuccess(Object object) {


                Replies reply = (Replies) object;
                DebugUtil.debug("replylistss", reply.getReplyList().size() + "");
                if (reply.getReplyList().size() > 0) {
                    replyLists.addAll(reply.getReplyList());
                    replyAdapter.notifyDataSetChanged();
                    lastSequence = reply.getReplyList().get(reply.getReplyList().size() - 1).getSequence();
                    firstRequest = false;
                    loading =false;
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        DebugUtil.debug("desss", "1");
        //

        setContentView(R.layout.null_layout);
        RefWatcher refWatcher = DiscoverApplication.getRefWatcher(this);
        refWatcher.watch(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //全屏播放时退出全屏
        if (JZVideoPlayer.backPress()) {
            return;
        }
        toFinish();
        super.onBackPressed();
    }

    public void toFinish() {
        DebugUtil.debug("ffff", "111");
        replyLists = null;
        replyAdapter = null;
        System.gc();
    }


}
