package com.example.discover.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.discover.R;
import com.example.discover.bean.DetailBean.ReplyList;
import com.example.discover.databinding.ItemReplyBinding;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/30.
 */

public class ReplyAdapter extends RecyclerView.Adapter {

    private final static int HEADER = 0;
    private final static int REPLY = 1;

    private View description;
    private List<ReplyList> datas;


    public ReplyAdapter(List<ReplyList> data, View description) {
        datas = data;
        this.description = description;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else
            return REPLY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new SimpleViewHolder(description);

            case REPLY:

                return new Holder(parent, R.layout.item_reply);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == REPLY) {

            Holder replyHolder = (Holder)holder;
            replyHolder.replyBinding.setReply(datas.get(position -1));
            replyHolder.replyBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        int count = 1;
        if (datas.size() > 0) count += datas.size();
        return count;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ItemReplyBinding replyBinding;
        public Holder(ViewGroup parent, int layoutId) {
            super(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId, parent, false).getRoot());
            replyBinding = DataBindingUtil.getBinding(itemView);
        }


    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
