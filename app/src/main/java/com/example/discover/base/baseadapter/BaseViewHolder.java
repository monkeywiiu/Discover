package com.example.discover.base.baseadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public abstract class BaseViewHolder<T,H extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public H itemViewBinding;
    public BaseViewHolder(ViewGroup parent, int layoutId) {
        super(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId, parent, false).getRoot());
        itemViewBinding = DataBindingUtil.getBinding(itemView);

    }

    public abstract void fillHolder(T object);

    public void baseFillHolder(T object) {
        fillHolder(object);
    }
}
