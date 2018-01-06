package com.example.discover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.discover.R;
import com.example.discover.base.BaseFragment;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.LitePalBean.Video;
import com.example.discover.databinding.FooterItemVideoBinding;
import com.example.discover.databinding.LikeVideoCardBinding;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.ShareUtil;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by monkeyWiiu on 2018/1/5.
 */

public class LikeVideoRecyclerAdapter extends BaseRecyclerAdapter<Video> {


    private final static int TYPE_CONTENT = 2;
    private final static int TYPE_FOOTER = 3;
    private final static int LOAD_MORE = 1;
    private final static int NO_MORE = 0;
    private final static int STATE_NORMAL = -1;
    private int mState = STATE_NORMAL;
    private FooterItemVideoBinding mFooterBinding;

    public MyDeleteClickListener deleteListener;

    public interface MyDeleteClickListener {
        void onDelete(int position, int id);
    }

    public void setOnClickListener(MyDeleteClickListener listener){
        this.deleteListener = listener;
    }

    public LikeVideoRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new LikeVideoHolder(parent, R.layout.like_video_card);
        } else {
            return  new FooterHolder(parent, R.layout.footer_item_video);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }



    public class LikeVideoHolder extends BaseViewHolder<Video, LikeVideoCardBinding> {
        public LikeVideoHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Video likeVideo, int position) {
            if (likeVideo != null) {
                itemViewBinding.tvDesc.setText(likeVideo.getDescription());
                itemViewBinding.jzVideoPlayer.setUp(likeVideo.getPlayUrl(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, likeVideo.getTitle());
                itemViewBinding.lvType.setText(likeVideo.getLabelText());
                itemViewBinding.lvType.setBackground(likeVideo.getLabelColor());
                Glide.with(mContext).load(likeVideo.getImageUrl())
                        .crossFade(800)
                        .placeholder(R.drawable.cross_image)
                        .error(R.drawable.cross_image)
                        .into(itemViewBinding.jzVideoPlayer.thumbImageView);
                if (likeVideo.getSize() > 0) {
                    itemViewBinding.tvVideoSize.setText("视频大小" + likeVideo.getSize() + "MB");
                }
            }
            //设置点击事件
            setOnClick(itemViewBinding, likeVideo, position, likeVideo.getId());
        }
    }

    public class FooterHolder extends BaseViewHolder<Object, FooterItemVideoBinding> {
        public FooterHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object, int position) {
            mFooterBinding = itemViewBinding;
            DebugUtil.debug("loading11", "yun");
            //itemViewBinding.loading.show();
        }
    }

    public void setOnClick(LikeVideoCardBinding binding, Video likeVideo, final int positon, final int videoId) {
        //点击分享
        final String shareText = likeVideo.getTitle() + likeVideo.getPlayUrl() + mContext.getString(R.string.share_from);
        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.share(mContext, shareText);
            }
        });
        //点击删除item
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onDelete(positon, videoId);
            }
        });
        //点击下载
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
}
