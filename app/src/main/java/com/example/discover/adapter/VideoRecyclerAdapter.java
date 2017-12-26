package com.example.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.EyeBean;
import com.example.discover.databinding.FooterItemVideoBinding;
import com.example.discover.databinding.VideoCardBinding;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.zip.Inflater;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class VideoRecyclerAdapter extends BaseRecyclerAdapter<EyeBean.ItemListBean> {
    private int i; //随机过渡图序号
    private int LOAD_MORE = 1;
    private int NO_MORE = 0;
    private int STATE_NORMAL = -1;
    private int TYPE_FOOTER = 3;
    private int TYPE_CONTENT = 2;
    private int mState = STATE_NORMAL;

    private int width = DensityUtil.getScreenWidth(mContext);

    public VideoRecyclerAdapter(Context context) {
        super(context);
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
    public class VideoHolder extends BaseViewHolder<EyeBean.ItemListBean, VideoCardBinding> {
        public VideoHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }
        @Override
        public void fillHolder(EyeBean.ItemListBean list) {
            if (list != null && list.getData().getPlayInfo().size() > 0) {

                int x = list.getData().getPlayInfo().get(0).getWidth();//获取视频的width
                int y = list.getData().getPlayInfo().get(0).getHeight();//获取视频的height
                int height = width * y / x; //根据视频比列获得视频控件的高
                LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                itemViewBinding.jzVideoPlayer.setLayoutParams(layoutParams);
                if (list.getData().getPlayInfo().size() == 1) { //标清||高清
                    itemViewBinding.jzVideoPlayer.setUp(list.getData().getPlayInfo().get(0).getUrlList().get(2).getUrl(),
                            JZVideoPlayer.SCREEN_WINDOW_NORMAL, list.getData().getTitle());
                    itemViewBinding.videoTitle.setText(list.getData().getTitle());
                    itemViewBinding.videoDesc.setText(list.getData().getDescription());
                } else if (list.getData().getPlayInfo().size() > 1) { //标清&&高清
                    LinkedHashMap map = new LinkedHashMap();
                    Object[] objects = new Object[1];
                    for (int i = 0; i < list.getData().getPlayInfo().size(); i ++) {
                        map.put(list.getData().getPlayInfo().get(i).getName(),
                                list.getData().getPlayInfo().get(i).getUrlList().get(2).getUrl());
                    }
                    objects[0] = map;
                    itemViewBinding.jzVideoPlayer.setUp(objects , 0, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                            list.getData().getTitle());
                    itemViewBinding.videoTitle.setText(list.getData().getTitle());
                    itemViewBinding.videoDesc.setText(list.getData().getDescription());
                }
                Glide.with(mContext).load(list.getData().getCover().getDetail())
                        .crossFade(800)
                        .placeholder(R.drawable.cross_image)
                        .error(R.drawable.cross_image)
                        .into(itemViewBinding.jzVideoPlayer.thumbImageView);
            }
        }

    }

    public class FooterHolder extends BaseViewHolder<Object, FooterItemVideoBinding> {
        public FooterHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object) {

            itemViewBinding.loading.show();
        }
    }

}
