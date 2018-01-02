package com.example.discover.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.EyeBean;
import com.example.discover.bean.LitePalBean.Video;
import com.example.discover.databinding.FooterItemVideoBinding;
import com.example.discover.databinding.VideoCardBinding;
import com.example.discover.model.VideoModel;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.example.discover.utils.ShareUtil;

import org.litepal.crud.DataSupport;

import java.util.LinkedHashMap;
import java.util.List;

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
    private int width = DensityUtil.getScreenWidth(mContext);//屏宽
    private int videoSize; //video大小MB
    public MyCollectClickListener collectListener;

    public interface MyCollectClickListener {
        void onClick(View v);
    }

    public void setCollectClickListener(MyCollectClickListener listener){
        this.collectListener = listener;
    }

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
        private VideoHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }
        @Override
        public void fillHolder(final EyeBean.ItemListBean list, final int position) {
            DebugUtil.debug("nullisa", "12");
            DebugUtil.debug("testttt", list.getData().getTitle());
            //填充基础数据
            itemViewBinding.videoTitle.setText(list.getData().getTitle());
            itemViewBinding.videoDesc.setText(list.getData().getDescription());
            Glide.with(mContext).load(list.getData().getCover().getDetail())
                    .crossFade(800)
                    .placeholder(R.drawable.cross_image)
                    .error(R.drawable.cross_image)
                    .into(itemViewBinding.jzVideoPlayer.thumbImageView);
            itemViewBinding.lvType.setText(list.getData().getCategory());
            DebugUtil.debug("labelColor", getLabelColor(list.getData().getCategory()) + "");
            itemViewBinding.lvType.setBackground(getLabelColor(list.getData().getCategory()));
            //填充播放链接，playinfo有时候没有，playurl常有
            if (list.getData().getPlayInfo().size() > 0) {
                //设置窗口比列
                int x = list.getData().getPlayInfo().get(0).getWidth();//获取视频的width
                int y = list.getData().getPlayInfo().get(0).getHeight();//获取视频的height
                int height = width * y / x; //根据视频比列获得视频控件的高
                LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                itemViewBinding.jzVideoPlayer.setLayoutParams(layoutParams);
                videoSize = list.getData().getPlayInfo().get(0).getUrlList().get(2).getSize()/1024/1024;
                itemViewBinding.tvVideoSize.setText("视频大小约" + videoSize+ "MB");

                if (list.getData().getPlayInfo().size() == 1) { //标清||高清

                    itemViewBinding.jzVideoPlayer.setUp(list.getData().getPlayInfo().get(0).getUrlList().get(2).getUrl(),
                            JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
                } else if (list.getData().getPlayInfo().size() > 1) { //标清&&高清

                    LinkedHashMap map = new LinkedHashMap();
                    Object[] objects = new Object[1];
                    for (int i = 0; i < list.getData().getPlayInfo().size(); i ++) {
                        map.put(list.getData().getPlayInfo().get(i).getName(),
                                list.getData().getPlayInfo().get(i).getUrlList().get(2).getUrl());
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
            DebugUtil.debug("getTagfill", "tt" + position);

            if ("true".equals(list.getTag())) {
                itemViewBinding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collected));
            } else if (list.getTag() == null) {
                itemViewBinding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collect));
            }
            //设置点击事件
            setOnClick(list, itemViewBinding, position, videoSize);

        }
    }

    public class FooterHolder extends BaseViewHolder<Object, FooterItemVideoBinding> {
        public FooterHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(Object object, int position) {

            itemViewBinding.loading.show();
        }
    }

    private void setOnClick(final EyeBean.ItemListBean list, final VideoCardBinding binding, final int position, final int vSize) {

        boolean isCollect = false;
        final String shareText = list.getData().getTitle() + list.getData().getWebUrl().getForWeibo() + mContext.getString(R.string.share_from);
        //点击分享
        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareUtil.share(mContext, shareText);
            }
        });

        binding.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collect));
                if (list.getTag() == null) {
                    DebugUtil.debug("getTag",  list.getData().getTitle() + "|" + list.getData().getDescription());
                    binding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collected));
                    //存入数据库
                    VideoModel.addToFavor(list.getData().getId(), list.getData().getTitle(), list.getData().getDescription(),
                           list.getData().getPlayUrl(), vSize);
                    list.setTag("true");
                } else  if ("true".equals(list.getTag())) {
                    DebugUtil.debug("getTag", list.getTag() + "//" + position);
                    binding.ivCollect.setImageDrawable(mContext.getResources().getDrawable(R.drawable.collect));
                    list.setTag(null);
                    /*List<Video> list1 = DataSupport.findAll(Video.class);
                    DebugUtil.debug("getData", list1.size() + "");
                    for (Video vi : list1) {
                        DebugUtil.debug("getData", vi.getDescription());
                        DebugUtil.debug("getData", vi.getPlayUrl());
                        DebugUtil.debug("getData", vi.getTitle());
                        DebugUtil.debug("getData", vi.getSize() + "");
                        DebugUtil.debug("getData", vi.getVideoId() + "");
                    }*/
                }
            }
        });
    }


    private int getLabelColor(String type) {

        switch (type) {
            case "创意":
                return mContext.getResources().getColor(R.color.colorLabelCre);
            case "音乐":
                return mContext.getResources().getColor(R.color.colorLabelMusic);
            case "旅行":
                return mContext.getResources().getColor(R.color.colorLabelTravel);
            case "科普":
                return mContext.getResources().getColor(R.color.colorLabelScience);
            case "搞笑":
                return mContext.getResources().getColor(R.color.colorLabelFunny);
            case "时尚":
                return mContext.getResources().getColor(R.color.colorLabelFashion);
            case "运动":
                return mContext.getResources().getColor(R.color.colorLabelSport);
            case "动画":
                return mContext.getResources().getColor(R.color.colorLabelAnim);
            case "广告":
                return mContext.getResources().getColor(R.color.colorLabelAd);
            case "开胃":
                return mContext.getResources().getColor(R.color.colorLabelAppetizing);
            case "生活":
                return mContext.getResources().getColor(R.color.colorLabelLife);
            case "剧情":
                return mContext.getResources().getColor(R.color.colorLabelDrama);
            case "预告":
                return mContext.getResources().getColor(R.color.colorLabelNotice);
            case "集锦":
                return mContext.getResources().getColor(R.color.colorLabelVariety);
            case "记录":
                return mContext.getResources().getColor(R.color.colorLabelRecord);
            case "游戏":
                return mContext.getResources().getColor(R.color.colorLabelGame);
            case "萌宠":
                return mContext.getResources().getColor(R.color.colorLabelPet);
            case "综艺":
                return mContext.getResources().getColor(R.color.colorLabelVariety);
            default:
                return mContext.getResources().getColor(R.color.colorLabelDefault);
        }
    }
}
