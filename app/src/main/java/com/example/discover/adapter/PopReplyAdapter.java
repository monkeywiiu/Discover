package com.example.discover.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.discover.R;
import com.example.discover.bean.DetailBean.ReplyList;
import com.example.discover.databinding.ItemReplyBinding;

import java.util.List;

/**
 * Created by monkeyWiiu on 2018/2/1.
 */

public class PopReplyAdapter extends RecyclerView.Adapter<PopReplyAdapter.Holder> {

    private List<ReplyList> datas;

    public PopReplyAdapter(List<ReplyList> datas) {
        this.datas = datas;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(parent, R.layout.item_reply);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.replyBinding.setReply(datas.get(position));
        holder.replyBinding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ItemReplyBinding replyBinding;
        public Holder(ViewGroup parent, int layoutId) {
            super(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId, parent, false).getRoot());
            replyBinding = DataBindingUtil.getBinding(itemView);
        }


    }
}
