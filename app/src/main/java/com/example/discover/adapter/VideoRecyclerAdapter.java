package com.example.discover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.example.discover.R;
import com.example.discover.app.Constant;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.databinding.FooterItemVideoBinding;
import com.example.discover.databinding.VideoCardBinding;
import com.example.discover.model.HotVideoModel;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.example.discover.utils.IntentManager;
import com.example.discover.utils.LitePalUtil;
import com.example.discover.utils.ShareUtil;
import com.example.discover.view.CustomView.ReplyPopupWindow;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class VideoRecyclerAdapter extends BaseRecyclerAdapter<ItemList> {
    private boolean isAuthor = false;
    private int LOAD_MORE = 1;
    private final static int NO_MORE = 0;
    private final static int STATE_NORMAL = -1;
    private final static int TYPE_FOOTER = 3;
    private final static int TYPE_CONTENT = 2;
    private  int mState = STATE_NORMAL;
    private int width = DensityUtil.getScreenWidth(mContext);//屏宽
    private FooterItemVideoBinding mFooterBinding;

    public VideoRecyclerAdapter(Context context) {
        super(context);
    }

    public void setAuthor(boolean isAuthor) {
        this.isAuthor = isAuthor;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new VideoHolder(parent, R.layout.video_card);
        }else {
            return new FooterHolder(parent, R.layout.footer_item_video);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            DebugUtil.debug("footer", "true");
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }

    }

    public void updateStateLoad(boolean loading) {
        if (loading) {
            this.mState = LOAD_MORE;
        }else {
            this.mState = NO_MORE;
        }
    }

    public boolean isLoading() {
        if (this.mState == LOAD_MORE) {
            return true;
        } else
            return false;
    }

    public void hideLoading() {
        mFooterBinding.loading.hide();
    }

    public class VideoHolder extends BaseViewHolder<ItemList, VideoCardBinding> {
        private VideoHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }
        @Override
        public void fillHolder(final ItemList list, final int position) {
            //填充基础数据
            //int videoSize = 0;
            itemViewBinding.videoTitle.setText(list.getData().getTitle());
            itemViewBinding.videoDesc.setText(list.getData().getDescription());
            Glide.with(mContext).load(list.getData().getCover().getDetail())
                    .crossFade(800)
                    .placeholder(R.drawable.cross_image)
                    .error(R.drawable.cross_image)
                    .into(itemViewBinding.jzVideoPlayer.thumbImageView);
            if (!isAuthor) {
                Glide.with(mContext).load(list.getData().getAuthor().getIcon())
                        .error(R.drawable.cross_image)
                        .into(itemViewBinding.headIcon);
            } else {
                itemViewBinding.headIcon.setVisibility(View.GONE);
            }
            itemViewBinding.tvLabel.setText(list.getData().getCategory());
            itemViewBinding.cvLabel.setCardBackgroundColor((int)Constant.LabelMap.get(list.getData().getCategory()));
            //填充播放链接，playinfo有时候没有，playurl常有
            if (list.getData().getPlayinfo().size() > 0) {
                //设置窗口比列
                int x = list.getData().getPlayinfo().get(0).getWidth();//获取视频的width
                int y = list.getData().getPlayinfo().get(0).getHeight();//获取视频的height
                int height = width * y / x; //根据视频比列获得视频控件的高
                LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                itemViewBinding.jzVideoPlayer.setLayoutParams(layoutParams);

                //videoSize = list.getData().getPlayinfo().get(0).getUrlList().get(2).getSize()/1024/1024;
                //itemViewBinding.tvVideoSize.setText("视频大小约" + videoSize+ "MB");


                if (list.getData().getPlayinfo().size() == 1) { //标清||高清

                    itemViewBinding.jzVideoPlayer.setUp(list.getData().getPlayinfo().get(0).getUrlList().get(2).getUrl(),
                            JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
                } else if (list.getData().getPlayinfo().size() > 1) { //标清&&高清

                    LinkedHashMap map = new LinkedHashMap();
                    Object[] objects = new Object[1];
                    for (int i = 0; i < list.getData().getPlayinfo().size(); i ++) {
                        map.put(list.getData().getPlayinfo().get(i).getName(),
                                list.getData().getPlayinfo().get(i).getUrlList().get(2).getUrl());
                    }
                    objects[0] = map;
                    itemViewBinding.jzVideoPlayer.setUp(objects , 0,
                            JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                }
            } else {    //防止playinfo没有数据时播放地址无效

                if (list.getData().getPlayUrl() != null) {

                    itemViewBinding.jzVideoPlayer.setUp(list.getData().getPlayUrl(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
                }
            }

            if ("true".equals(list.getTag())) {
                itemViewBinding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collected));
            } else if (list.getTag() == null) {
                itemViewBinding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collect));
            }
            //设置点击事件
            setOnClick(list, itemViewBinding, position, 0);

        }
    }

    public class FooterHolder extends BaseViewHolder<Object, FooterItemVideoBinding> {
        public FooterHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object, int position) {
            DebugUtil.debug("loadingm", "11");
            mFooterBinding = itemViewBinding;
            itemViewBinding.loading.show();
        }
    }

    private void setOnClick(final ItemList list, final VideoCardBinding binding, final int position, final int vSize) {

        boolean isCollect = false;
        final String shareText = list.getData().getTitle() + list.getData().getWebUrl().getForWeibo() + mContext.getString(R.string.share_from);
        //点击分享 RXVIEW
        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareUtil.share(mContext, shareText);
            }
        });

        //展开评论
        RxView.clicks(binding.ivComment)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        ReplyPopupWindow replyPopupWindow = new ReplyPopupWindow(mContext, list.getData().getId());
                        replyPopupWindow.showPopupWindow(binding.ivComment);
                    }
                });
        //点击收藏
        binding.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collect));
                if (list.getTag() == null) {

                    binding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collected));
                    //存入数据库
                    LitePalUtil.addVideoToFavor(list.getData().getId(), list.getData().getTitle(), list.getData().getDescription(),
                           list.getData().getPlayUrl(), list.getData().getCover().getDetail(),
                            (int)Constant.LabelMap.get(list.getData().getCategory()), list.getData().getCategory(), vSize);
                    list.setTag("true");
                } else  if ("true".equals(list.getTag())) {

                    binding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collect));
                    LitePalUtil.deleteVideoFromFavor(list.getData().getId());
                    list.setTag(null);
                }
            }
        });
        //跳转主页
        RxView.clicks(binding.headIcon)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        IntentManager.fromDetailtoAuthor(mContext, list);
                    }
                });
    }

}
