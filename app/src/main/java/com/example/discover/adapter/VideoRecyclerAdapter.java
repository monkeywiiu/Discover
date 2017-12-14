package com.example.discover.adapter;

import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.base.baseadapter.BaseRecyclerAdapter;
import com.example.discover.base.baseadapter.BaseViewHolder;
import com.example.discover.bean.EyeBean;
import com.example.discover.databinding.TestBinding;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class VideoRecyclerAdapter extends BaseRecyclerAdapter<EyeBean.ItemList> {

    public LinkedHashMap map;
    public Object[] objects;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(parent, R.layout.test);
    }

    public class VideoHolder extends BaseViewHolder<EyeBean.ItemList, TestBinding> {



        public VideoHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void fillHolder(EyeBean.ItemList list) {
            /*map = new LinkedHashMap();
            objects = new Object[1];
            if (list != null) {
                map.put("标清", list.getData().getPlayInfo().get(0).getUrlList().get(2).getUrl());
                map.put("高清", list.getData().getPlayInfo().get(1).getUrlList().get(2).getUrl());
                objects[0] = map;
                itemViewBinding.jzVideoPlayer.setUp(objects , 1, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                        list.getData().getTitle());
                itemViewBinding.videoTitle.setText(list.getData().getTitle());
                itemViewBinding.videoDesc.setText(list.getData().getDescription());
            }*/
            itemViewBinding.tvTest.setText(list.getData().getPlayInfo().size() + "");
        }

    }
}
