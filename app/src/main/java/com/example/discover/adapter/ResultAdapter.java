package com.example.discover.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.discover.R;
import com.example.discover.VideoDetailActivity;
import com.example.discover.bean.DetailBean.ItemList;
import com.example.discover.databinding.CategoryCardBinding;
import com.example.discover.utils.DebugUtil;
import com.example.discover.utils.DensityUtil;
import com.example.discover.utils.IntentManager;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import io.reactivex.functions.Consumer;

/**
 * Created by monkeyWiiu on 2018/1/29.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.Holder> {

    private List<ItemList> lists ;
    private Activity mContext;

    public ResultAdapter(Activity context, List<ItemList> lists) {
        this.mContext = context;
        this.lists = lists;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(parent, R.layout.category_card);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.itemViewBinding.setItemList(lists.get(position));
        //当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
        holder.itemViewBinding.executePendingBindings();

        RxView.clicks(holder.itemViewBinding.videoAlbum)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        IntentManager.toVideoDetailActivity(mContext, lists.get(position), holder.itemViewBinding.videoAlbum);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

       private CategoryCardBinding itemViewBinding;

       private Holder(ViewGroup parent, int layoutId) {

            super(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId, parent, false).getRoot());
            itemViewBinding = DataBindingUtil.getBinding(itemView);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(200));
            itemViewBinding.videoAlbum.setLayoutParams(layoutParams);

           DebugUtil.debug("rerere", "bbb");
        }
    }



}


